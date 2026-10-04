package com.tortugolen.patchouliplus.book.page.recipes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import com.tortugolen.patchouliplus.book.page.abstr.PageMultiSimpleRecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class PageCraftingPlus extends PageMultiSimpleRecipeRegistry<Recipe<?>> {
    public PageCraftingPlus() {
        super(RecipeType.CRAFTING);
    }

    @Override
    protected void drawRecipe(GuiGraphics graphics, Recipe<?> recipe, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            RenderSystem.enableBlend();
            GUIBookPlus.drawFromTexturePlus(graphics, book, recipeX - 1, recipeY - 2, 101, 62);
            boolean shaped = recipe instanceof ShapedRecipe;
            if (!shaped) {
                int iconX = recipeX + 63;
                int iconY = recipeY + 1;
                graphics.blit(book.craftingTexture, iconX, iconY, 0, 64, 11, 11, 128, 256);
                if (parent.isMouseInRelativeRange((double)mouseX, (double)mouseY, iconX, iconY, 11, 11)) {
                    parent.setTooltip(new Component[]{Component.translatable("patchouli.gui.lexicon.shapeless")});
                }
            }

            parent.renderItemStack(graphics, recipeX + 80, recipeY + 21, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
            NonNullList<Ingredient> ingredients = recipe.getIngredients();
            int wrap = 3;
            if (shaped) {
                wrap = ((ShapedRecipe)recipe).getWidth();
            }

            for(int i = 0; i < ingredients.size(); ++i) {
                parent.renderIngredient(graphics, recipeX + i % wrap * 19 + 3, recipeY + i / wrap * 19 + 2, mouseX, mouseY, (Ingredient)ingredients.get(i));
            }

            parent.renderItemStack(graphics, recipeX + 80, recipeY + 41, mouseX, mouseY, recipe.getToastSymbol());
        }
    }

    @Override
    protected int getRecipeHeight() {
        return 62 + 4;
    }

    protected ItemStack getRecipeOutput(Level level, Recipe<?> recipe) {
        return recipe != null && level != null ? recipe.getResultItem(level.registryAccess()) : ItemStack.EMPTY;
    }
}
