package com.tortugolen.patchouliplus.book.page.abstr;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.base.ERecipeLayout;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class PageMultiProcessingSimpleRecipe<T extends Recipe<?>> extends PageMultiSimpleRecipeRegistry<T> {
    public PageMultiProcessingSimpleRecipe(RecipeType<T> recipeType) {
        super(recipeType);
    }

    @Override
    protected void drawRecipe(GuiGraphics graphics, T recipe, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        RenderSystem.enableBlend();
        ERecipeLayout.SIMPLE_RECIPE.draw(graphics, getCraftingTexturePlus(), recipeX, recipeY);
        parent.renderIngredient(graphics, recipeX + 4, recipeY + 4, mouseX, mouseY, recipe.getIngredients().get(0));
        parent.renderItemStack(graphics, recipeX + 40, recipeY + 4, mouseX, mouseY, recipe.getToastSymbol());
        parent.renderItemStack(graphics, recipeX + 76, recipeY + 4, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
    }

    protected ItemStack getRecipeOutput(Level level, T recipe) {
        return recipe != null && level != null ? recipe.getResultItem(level.registryAccess()) : ItemStack.EMPTY;
    }
}