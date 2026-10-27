package com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class PageProcessingPlus<T extends Recipe<?>> extends PageRecipeRegistryPlus<T> {
//    protected Item[] toast = null;

    public PageProcessingPlus(RecipeType<T> recipeType) {
        super(recipeType);
    }

    @Override
    protected void drawRecipe(GuiGraphics graphics, T recipe, int x, int y, int mouseX, int mouseY, boolean second) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        RenderSystem.enableBlend();

        if (hasBooleanFeature(stationBorder, false)) {
            GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 3, y, 114, 102, 24);
            parent.renderIngredient(graphics, x + 1, y + 4, mouseX, mouseY, recipe.getIngredients().get(0));
            parent.renderIngredient(graphics, x + 40, y + 4, mouseX, mouseY, getToastSymbol(recipe));
            parent.renderItemStack(graphics, x + 79, y + 4, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
        } else {
            GUIBookPlus.drawRecipesTexturePlus(graphics, book, x, y, 89, 96, 24);
            parent.renderIngredient(graphics, x + 4, y + 4, mouseX, mouseY, recipe.getIngredients().get(0));
            parent.renderIngredient(graphics, x + 40, y + 4, mouseX, mouseY, getToastSymbol(recipe));
            parent.renderItemStack(graphics, x + 76, y + 4, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
        }
    }

    private Ingredient getToastSymbol(T recipe) {
//        if (toast != null) return Ingredient.of(toast);
        if (recipe instanceof CampfireCookingRecipe) return Ingredient.of(recipe.getToastSymbol().getItem().asItem(), Items.SOUL_CAMPFIRE);
        return Ingredient.of(recipe.getToastSymbol());
    }
}