package com.tortugolen.patchouliplus.Book.Pages.Recipes;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Base.EBrewingRecipeVariant;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.PageWithItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;

import java.util.ArrayList;
import java.util.List;

public class PageBrewingPlus extends PageWithItem {
    protected static final int RECIPE_START_X = START_X + 4;

    @SerializedName("potion")
    protected IVariable effect;
    @SerializedName("variants")
    protected IVariable brewingRecipeTypes;
    @SerializedName("station_border")
    protected boolean stationBorder = false;
    @SerializedName("invert_ingredients")
    protected boolean invertIngredients = false;

    protected static class Step {
        protected ItemStack input = ItemStack.EMPTY;
        protected Ingredient ingredient = Ingredient.EMPTY;
        protected ItemStack output = ItemStack.EMPTY;
    }

    protected transient List<List<Step>> slots = new ArrayList<>();

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);

        slots = new ArrayList<>();
        if (effect == null) {
            PatchouliAPI.LOGGER.warn("Brewing page without 'potion'");
            return;
        }

        List<EBrewingRecipeVariant> selected = parseBrewingRecipeTypes();
        List<ItemStack> validIngredients = collectValidIngredients();

        JsonElement root = effect.unwrap();
        Iterable<JsonElement> topLevel = root.isJsonArray() ? root.getAsJsonArray() : List.of(root);

        for (JsonElement element : topLevel) {
            List<String> ids = new ArrayList<>();
            collectIds(element, ids);

            List<Step> alternatives = buildSteps(ids, selected, validIngredients);
            if (!alternatives.isEmpty()) slots.add(alternatives);
        }

        if (slots.isEmpty()) {
            PatchouliAPI.LOGGER.warn("Invalid brewing page: nothing could be resolved (potion={}, variants={})", effect, brewingRecipeTypes);
            return;
        }

        for (List<Step> alternatives : slots)
            for (Step step : alternatives) entry.addRelevantStack(builder, step.output, pageNum);
    }

    private void collectIds(JsonElement element, List<String> result) {
        if (element == null || element.isJsonNull()) return;
        if (element.isJsonArray()) for (JsonElement child : element.getAsJsonArray()) collectIds(child, result);
        else if (element.isJsonPrimitive()) addParts(result, element.getAsString());
    }

    private List<Step> buildSteps(List<String> effectIds, List<EBrewingRecipeVariant> selected, List<ItemStack> validIngredients) {
        List<Step> result = new ArrayList<>();

        for (String effectId : effectIds) {
            try {
                ResourceLocation base = new ResourceLocation(effectId);

                for (EBrewingRecipeVariant recipeVariant : selected) {
                    ResourceLocation id = new ResourceLocation(base.getNamespace(), recipeVariant.prefix + base.getPath());

                    if (!BuiltInRegistries.POTION.containsKey(id)) {
                        PatchouliAPI.LOGGER.warn("Brewing variant '{}' skipped: potion {} does not exist", recipeVariant.id, id);
                        continue;
                    }

                    Step step = new Step();
                    step.output = PotionUtils.setPotion(new ItemStack(recipeVariant.container), BuiltInRegistries.POTION.get(id));

                    if (findStep(step, validIngredients)) result.add(step);
                    else PatchouliAPI.LOGGER.warn("Brewing variant '{}' skipped: no brewing recipe produces {}", recipeVariant.id, step.output);
                }
            } catch (Exception ex) {
                PatchouliAPI.LOGGER.warn("Invalid brewing effect '{}'", effectId, ex);
            }
        }
        return result;
    }

    private List<EBrewingRecipeVariant> parseBrewingRecipeTypes() {
        List<EBrewingRecipeVariant> result = new ArrayList<>();
        List<String> ids = parseList(brewingRecipeTypes);

        if (ids.isEmpty()) {
            result.add(EBrewingRecipeVariant.NORMAL);
            return result;
        }

        for (String id : ids) {
            EBrewingRecipeVariant recipeType = EBrewingRecipeVariant.byId(id);
            if (recipeType == null) {
                PatchouliAPI.LOGGER.warn("Unknown brewing EBrewingRecipeType '{}'", id);
                continue;
            }
            if (!result.contains(recipeType)) result.add(recipeType);
        }
        return result;
    }

    private List<String> parseList(IVariable var) {
        List<String> result = new ArrayList<>();
        if (var == null) return result;
        if (var.unwrap().isJsonArray()) for (IVariable item : var.asList()) addParts(result, item.asString());
        else addParts(result, var.asString());
        return result;
    }

    private void addParts(List<String> result, String raw) {
        if (raw == null) return;
        for (String part : raw.split(",")) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty() && !result.contains(trimmed)) result.add(trimmed);
        }
    }

    private boolean findStep(Step step, List<ItemStack> validIngredients) {
        Item[] containers = {Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION};

        for (Item container : containers) {
            for (Potion candidate : BuiltInRegistries.POTION) {
                if (candidate == Potions.EMPTY) continue;

                ItemStack in = PotionUtils.setPotion(new ItemStack(container), candidate);
                if (ItemStack.isSameItemSameTags(in, step.output)) continue;

                for (ItemStack ing : validIngredients) {
                    ItemStack result = BrewingRecipeRegistry.getOutput(in, ing);
                    if (!result.isEmpty() && ItemStack.isSameItemSameTags(result, step.output)) {
                        step.input = in;
                        step.ingredient = Ingredient.of(ing);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<ItemStack> collectValidIngredients() {
        List<ItemStack> result = new ArrayList<>();
        for (Item item : BuiltInRegistries.ITEM) {
            ItemStack stack = new ItemStack(item);
            if (BrewingRecipeRegistry.isValidIngredient(stack)) result.add(stack);
        }
        return result;
    }

    private Step currentStep(List<Step> alternatives) {
        return alternatives.get((parent.ticksInBook / 20) % alternatives.size());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        if (slots.isEmpty()) return;

        int x = RECIPE_START_X;
        int y = getY();

        RenderSystem.enableBlend();

        for (List<Step> alternatives : slots) {
            drawStep(graphics, currentStep(alternatives), x, y, mouseX, mouseY);
            y += getRecipeHeight() + SLOT_SEPARATION_Y;
        }

        super.render(graphics, mouseX, mouseY, pticks);
    }

    private void drawStep(GuiGraphics graphics, Step step, int x, int y, int mouseX, int mouseY) {
        if (stationBorder) {
            GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 3, y, 183, 102, 43);
            parent.renderItemStack(graphics, x + 40, y + 13, mouseX, mouseY, getToastSymbol());
            parent.renderItemStack(graphics, x + 79, y + 13, mouseX, mouseY, step.output);
            if (invertIngredients) {
                parent.renderItemStack(graphics, x + 1, y + 23, mouseX, mouseY, step.input);
                parent.renderIngredient(graphics, x + 1, y + 4, mouseX, mouseY, step.ingredient);
            } else {
                parent.renderItemStack(graphics, x + 1, y + 4, mouseX, mouseY, step.input);
                parent.renderIngredient(graphics, x + 1, y + 23, mouseX, mouseY, step.ingredient);
            }
        } else {
            GUIBookPlus.drawRecipesTexturePlus(graphics, book, x, y, 139, 96, 43);
            parent.renderItemStack(graphics, x + 40, y + 13, mouseX, mouseY, getToastSymbol());
            parent.renderItemStack(graphics, x + 76, y + 13, mouseX, mouseY, step.output);
            if (invertIngredients) {
                parent.renderItemStack(graphics, x + 4, y + 23, mouseX, mouseY, step.input);
                parent.renderIngredient(graphics, x + 4, y + 4, mouseX, mouseY, step.ingredient);
            } else {
                parent.renderItemStack(graphics, x + 4, y + 4, mouseX, mouseY, step.input);
                parent.renderIngredient(graphics, x + 4, y + 23, mouseX, mouseY, step.ingredient);
            }
        }
    }

    @Override
    public int getTextY() {
        int total = slots.size() * (getRecipeHeight() + SLOT_SEPARATION_Y);
        return super.getTextY() + (slots.isEmpty() ? SLOT_SEPARATION_Y : total);
    }

    protected ItemStack getToastSymbol() {
        return new ItemStack(Items.BREWING_STAND);
    }

    protected int getRecipeHeight() {
        return 43;
    }
}