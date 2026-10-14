package com.tortugolen.patchouliplus.Book.Pages.Recipes;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes.PageMultiSimpleRecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class PageCraftingPlus extends PageMultiSimpleRecipeRegistry<Recipe<?>> {
    @SerializedName("crafting_toast_symbol")
    protected boolean drawCraftingToastSymbol = true;

    public PageCraftingPlus() {
        super(RecipeType.CRAFTING);
    }

    @Override
    protected void drawRecipe(GuiGraphics graphics, Recipe<?> recipe, int x, int y, int mouseX, int mouseY, boolean second) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        RenderSystem.enableBlend();

        int width = recipe instanceof ShapedRecipe shaped ? shaped.getWidth() : 3;
        var ingredients = recipe.getIngredients();

        GUIBookPlus.drawRecipesTexturePlus(graphics, book, x, y, 1, 101, 62);
        for (int i = 0; i < ingredients.size(); i++) parent.renderIngredient(graphics, x + (i % width) * 19 + 4, y + (i / width) * 19 + 3, mouseX, mouseY, ingredients.get(i));
        parent.renderItemStack(graphics, x + 81, y + 23, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
        if (drawCraftingToastSymbol) parent.renderItemStack(graphics, x + 80, y + 41, mouseX, mouseY, recipe.getToastSymbol());

        if (!(recipe instanceof ShapedRecipe)) {
            int iconX = x + 63;
            int iconY = y + 1;

            graphics.blit(book.craftingTexture, iconX, iconY, 0, 64, 11, 11, 128, 256);
            if (parent.isMouseInRelativeRange(mouseX, mouseY, iconX, iconY, 11, 11)) parent.setTooltip(Component.translatable("patchouli.gui.lexicon.shapeless"));
        }
    }

    @Override
    protected int getRecipeHeight(Recipe<?> recipe) {
        return 62;
    }
}
