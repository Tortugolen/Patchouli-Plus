package com.tortugolen.patchouliplus.book.page.recipes;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.base.ERecipeLayout;
import com.tortugolen.patchouliplus.book.page.abstr.PageMultiProcessingSimpleRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import vazkii.patchouli.mixin.AccessorSmithingTransformRecipe;
import vazkii.patchouli.mixin.AccessorSmithingTrimRecipe;

public class PageSmithingPlus extends PageMultiProcessingSimpleRecipe<SmithingRecipe> {
    @SerializedName("bordered")
    protected boolean bordered = false;

    public PageSmithingPlus() {
        super(RecipeType.SMITHING);
    }

    @Override
    protected void drawRecipe(GuiGraphics graphics, SmithingRecipe recipe, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        RenderSystem.enableBlend();
        int x = bordered ? recipeX - 2 : recipeX;
        ERecipeLayout.SIMPLE_RECIPE.draw(graphics, getCraftingTexturePlus(), x, recipeY, 158, 102, 43);

        parent.renderIngredient(graphics, recipeX + 2, recipeY + 4, mouseX, mouseY, getBase(recipe));
        parent.renderIngredient(graphics, recipeX + 2, recipeY + 23, mouseX, mouseY, getAddition(recipe));
        parent.renderIngredient(graphics, recipeX + 41, recipeY + 4, mouseX, mouseY, getTemplate(recipe));
        parent.renderItemStack(graphics, recipeX + 41, recipeY + 23, mouseX, mouseY, recipe.getToastSymbol());
        parent.renderItemStack(graphics, recipeX + 80, recipeY + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
    }

    private Ingredient getBase(SmithingRecipe recipe) {
        if (recipe instanceof SmithingTrimRecipe) {
            return ((AccessorSmithingTrimRecipe) recipe).getBase();
        } else {
            return recipe instanceof SmithingTransformRecipe ? ((AccessorSmithingTransformRecipe) recipe).getBase() : Ingredient.EMPTY;
        }
    }

    private Ingredient getAddition(SmithingRecipe recipe) {
        if (recipe instanceof SmithingTrimRecipe) {
            return ((AccessorSmithingTrimRecipe) recipe).getAddition();
        } else {
            return recipe instanceof SmithingTransformRecipe ? ((AccessorSmithingTransformRecipe) recipe).getAddition() : Ingredient.EMPTY;
        }
    }

    private Ingredient getTemplate(SmithingRecipe recipe) {
        if (recipe instanceof SmithingTrimRecipe) {
            return ((AccessorSmithingTrimRecipe) recipe).getTemplate();
        } else {
            return recipe instanceof SmithingTransformRecipe ? ((AccessorSmithingTransformRecipe) recipe).getTemplate() : Ingredient.EMPTY;
        }
    }

    protected ItemStack getRecipeOutput(Level level, SmithingRecipe recipe) {
        return super.getRecipeOutput(level, recipe);
    }

    @Override
    protected int getRecipeHeight() {
        return 43 + 4;
    }

    @Override
    protected int getMaxComponentNumber() {
        return 2;
    }
}