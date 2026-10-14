package com.tortugolen.patchouliplus.Book.Pages.not;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.PageWithItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.ArrayList;
import java.util.List;

public class PageBrewingPlus extends PageWithItem {
    protected static final int RECIPE_START_X = START_X + 4;
    @SerializedName("recipe")
    List<BrewingEntry> recipes = new ArrayList<>();
    @SerializedName("station_border")
    protected boolean stationBorder = false;
    @SerializedName("invert_ingredients")
    protected boolean invertIngredients = false;
    protected boolean alternating = false;
    protected String input;
    protected String ingredient;
    protected String output;
    protected transient List<BrewingEntry> loaded = new ArrayList<>();

    public static class BrewingEntry {
        protected String input;
        protected String ingredient;
        protected String output;

        protected transient ItemStack inputStack = ItemStack.EMPTY;
        protected transient Ingredient ingredientStack = Ingredient.EMPTY;
        protected transient ItemStack outputStack = ItemStack.EMPTY;
    }

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);

        List<BrewingEntry> entries = new ArrayList<>(recipes);
        if (input != null && ingredient != null && output != null) {
            BrewingEntry single = new BrewingEntry();
            single.input = input;
            single.ingredient = ingredient;
            single.output = output;
            entries.add(0, single);
        }

        for (BrewingEntry recipes : entries) {
            try {
                recipes.inputStack = ItemStackUtil.loadStackFromString(recipes.input);
                recipes.ingredientStack = ItemStackUtil.loadIngredientFromString(recipes.ingredient);
                recipes.outputStack = ItemStackUtil.loadStackFromString(recipes.output);
                entry.addRelevantStack(builder, recipes.outputStack, pageNum);
                loaded.add(recipes);
            } catch (Exception ex) {
                PatchouliAPI.LOGGER.warn("Invalid brewing recipe (input={}, ingredient={}, output={})", recipes.input, recipes.ingredient, recipes.output, ex);
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = RECIPE_START_X;

        RenderSystem.enableBlend();

        for (int i = 0; i < loaded.size(); i++) {
            int y = getY() + i * getRecipeHeight();
            BrewingEntry entry = loaded.get(i);

            if (stationBorder) {
                GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 3, y, 183, 102, 43);
                parent.renderItemStack(graphics, x + 40, y + 13, mouseX, mouseY, getToastSymbol());
                parent.renderItemStack(graphics, x + 79, y + 13, mouseX, mouseY, entry.outputStack);
                if (invertIngredients) {
                    parent.renderItemStack(graphics, x + 1, y + 23, mouseX, mouseY, entry.inputStack);
                    parent.renderIngredient(graphics, x + 1, y + 4, mouseX, mouseY, entry.ingredientStack);
                } else {
                    parent.renderItemStack(graphics, x + 1, y + 4, mouseX, mouseY, entry.inputStack);
                    parent.renderIngredient(graphics, x + 1, y + 23, mouseX, mouseY, entry.ingredientStack);
                }
            } else {
                GUIBookPlus.drawRecipesTexturePlus(graphics, book, x, y, 139, 96, 43);
                parent.renderItemStack(graphics, x + 40, y + 13, mouseX, mouseY, getToastSymbol());
                parent.renderItemStack(graphics, x + 76, y + 13, mouseX, mouseY, entry.outputStack);
                if (invertIngredients) {
                    parent.renderItemStack(graphics, x + 4, y + 23, mouseX, mouseY, entry.inputStack);
                    parent.renderIngredient(graphics, x + 4, y + 4, mouseX, mouseY, entry.ingredientStack);
                } else {
                    parent.renderItemStack(graphics, x + 4, y + 4, mouseX, mouseY, entry.inputStack);
                    parent.renderIngredient(graphics, x + 4, y + 23, mouseX, mouseY, entry.ingredientStack);
                }
            }
        }

        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    public int getTextY() {
        return super.getTextY() + loaded.size() * getRecipeHeight() + SLOT_SEPARATION_Y;
    }

    protected ItemStack getToastSymbol() {
        return new ItemStack(Items.BREWING_STAND);
    }

    protected int getRecipeHeight() {
        return 43;
    }
}
