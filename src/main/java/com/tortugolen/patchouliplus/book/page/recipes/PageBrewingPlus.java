package com.tortugolen.patchouliplus.book.page.recipes;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.base.ERecipeLayout;
import com.tortugolen.patchouliplus.book.page.PageTextPlus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.ArrayList;
import java.util.List;

public class PageBrewingPlus extends PageTextPlus {

    public static class BrewingEntry {
        String input;
        String ingredient;
        String output;

        transient ItemStack inputStack = ItemStack.EMPTY;
        transient Ingredient ingredientValue = Ingredient.EMPTY;
        transient ItemStack outputStack = ItemStack.EMPTY;
    }

    @SerializedName("recipes")
    List<BrewingEntry> recipes = new ArrayList<>();

    String input;
    String ingredient;
    String output;

    protected transient List<BrewingEntry> loaded = new ArrayList<>();

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);

        List<BrewingEntry> all = new ArrayList<>(recipes);
        if (input != null && ingredient != null && output != null) {
            BrewingEntry single = new BrewingEntry();
            single.input = input;
            single.ingredient = ingredient;
            single.output = output;
            all.add(0, single);
        }

        for (BrewingEntry e : all) {
            try {
                e.inputStack = ItemStackUtil.loadStackFromString(e.input);
                e.ingredientValue = ItemStackUtil.loadIngredientFromString(e.ingredient);
                e.outputStack = ItemStackUtil.loadStackFromString(e.output);
                entry.addRelevantStack(builder, e.outputStack, pageNum);
                loaded.add(e);
            } catch (Exception ex) {
                PatchouliAPI.LOGGER.warn("Invalid brewing recipe (input={}, ingredient={}, output={})",
                        e.input, e.ingredient, e.output, ex);
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = getX();

        for (int i = 0; i < loaded.size(); i++) {
            BrewingEntry e = loaded.get(i);
            int y = getY() + i * getRecipeHeight();

            RenderSystem.enableBlend();
            ERecipeLayout.SIMPLE_RECIPE.draw(graphics, getCraftingTexturePlus(), x, y);
            parent.renderItemStack(graphics, x + 4, y + 4, mouseX, mouseY, e.inputStack);
            parent.renderIngredient(graphics, x + 40, y + 4, mouseX, mouseY, e.ingredientValue);
            parent.renderItemStack(graphics, x + 76, y + 4, mouseX, mouseY, e.outputStack);
        }

        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    public int getTextHeight() {
        return super.getTextHeight() + getRecipeHeight() * loaded.size();
    }

    protected int getX() {
        return 9;
    }

    protected int getRecipeHeight() {
        return 24 + 4;
    }
}