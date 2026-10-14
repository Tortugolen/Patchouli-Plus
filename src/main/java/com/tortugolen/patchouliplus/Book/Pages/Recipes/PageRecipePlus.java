package com.tortugolen.patchouliplus.Book.Pages.Recipes;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes.PageMultiSimpleRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.mixin.AccessorSmithingTransformRecipe;
import vazkii.patchouli.mixin.AccessorSmithingTrimRecipe;

import javax.swing.*;
import java.util.Optional;
import java.util.Set;

public class PageRecipePlus extends PageMultiSimpleRecipe<Recipe<?>> {
    private static final int CRAFTING_HEIGHT = 62;
    private static final int SMITHING_HEIGHT = 43;

    @SerializedName("crafting_toast_symbol")
    protected boolean drawCraftingToastSymbol = true;
    @SerializedName("invert_ingredients")
    protected boolean invertIngredients = false;
    @SerializedName("ingredient_smithing_template")
    protected boolean smithingTemplateAsIngredient = false;

    private static final Set<RecipeType<?>> ALLOWED = Set.of(
            RecipeType.SMELTING,
            RecipeType.BLASTING,
            RecipeType.SMOKING,
            RecipeType.CAMPFIRE_COOKING,
            RecipeType.STONECUTTING,

            RecipeType.CRAFTING,
            RecipeType.SMITHING
    );

    @Override
    protected Recipe<?> loadRecipe(Level level, BookContentsBuilder builder, BookEntry entry, ResourceLocation id) {
        if (id == null || level == null) return null;

        Optional<? extends Recipe<?>> found = level.getRecipeManager().byKey(id);
        if (found.isEmpty()) {
            PatchouliAPI.LOGGER.warn("Recipe {} not found", id);
            return null;
        }

        Recipe<?> recipe = found.get();
        if (!ALLOWED.contains(recipe.getType())) {
            PatchouliAPI.LOGGER.warn("Recipe {} has an unsupported type: {}", id, recipe.getType());
            return null;
        }

        entry.addRelevantStack(builder, recipe.getResultItem(level.registryAccess()), pageNum);
        return recipe;
    }

    @Override
    protected void drawRecipe(GuiGraphics graphics, Recipe<?> recipe, int x, int y, int mouseX, int mouseY, boolean second) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        RenderSystem.enableBlend();

        if (recipe instanceof AbstractCookingRecipe || recipe instanceof StonecutterRecipe) drawProcessing(graphics, recipe, level, x, y, mouseX, mouseY);
        else if (recipe instanceof CraftingRecipe crafting) drawCrafting(graphics, crafting, level, x, y, mouseX, mouseY);
        else if (recipe instanceof SmithingRecipe smithing) drawSmithing(graphics, smithing, level, x, y, mouseX, mouseY);
    }

    @Override
    protected int getRecipeHeight(Recipe<?> recipe) {
        if (recipe instanceof CraftingRecipe) return CRAFTING_HEIGHT;
        if (recipe instanceof SmithingRecipe) return SMITHING_HEIGHT;
        else return 24;
    }

    private void drawProcessing(GuiGraphics graphics, Recipe<?> recipe, Level level, int x, int y, int mouseX, int mouseY) {
        if (recipe.getIngredients().isEmpty()) return;

        if (stationBorder) {
            GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 3, y, 114, 102, 24);
            parent.renderIngredient(graphics, x + 1, y + 4, mouseX, mouseY, recipe.getIngredients().get(0));
            parent.renderItemStack(graphics, x + 40, y + 4, mouseX, mouseY, recipe.getToastSymbol());
            parent.renderItemStack(graphics, x + 79, y + 4, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
        } else {
            GUIBookPlus.drawRecipesTexturePlus(graphics, book, x, y, 89, 96, 24);
            parent.renderIngredient(graphics, x + 4, y + 4, mouseX, mouseY, recipe.getIngredients().get(0));
            parent.renderItemStack(graphics, x + 40, y + 4, mouseX, mouseY, recipe.getToastSymbol());
            parent.renderItemStack(graphics, x + 76, y + 4, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
        }
    }

    private void drawCrafting(GuiGraphics graphics, CraftingRecipe recipe, Level level, int x, int y, int mouseX, int mouseY) {
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

    private void drawSmithing(GuiGraphics graphics, SmithingRecipe recipe, Level level, int x, int y, int mouseX, int mouseY) {
        switch (alignment) {
            case LEFT -> {
                if (stationBorder) {
                    if (smithingTemplateAsIngredient) {
                        GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 12, y, 315, 121, 43);
                        parent.renderIngredient(graphics, x - 8, y + 4, mouseX, mouseY, getBase(recipe));
                        parent.renderIngredient(graphics, x - 8, y + 23, mouseX, mouseY, getAddition(recipe));
                        parent.renderIngredient(graphics, x + 11, y + 13, mouseX, mouseY, getTemplate(recipe));
                        parent.renderItemStack(graphics, x + 50, y + 13, mouseX, mouseY, recipe.getToastSymbol());
                        parent.renderItemStack(graphics, x + 89, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                    } else {
                        GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 3, y, 227, 102, 43);
                        parent.renderIngredient(graphics, x + 1, y + 4, mouseX, mouseY, getBase(recipe));
                        parent.renderIngredient(graphics, x + 1, y + 23, mouseX, mouseY, getAddition(recipe));
                        parent.renderIngredient(graphics, x + 40, y + 4, mouseX, mouseY, getTemplate(recipe));
                        parent.renderItemStack(graphics, x + 40, y + 23, mouseX, mouseY, recipe.getToastSymbol());
                        parent.renderItemStack(graphics, x + 79, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                    }
                } else {
                    if (smithingTemplateAsIngredient) {
                        GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 9, y, 271, 115, 43);
                        parent.renderIngredient(graphics, x - 5, y + 4, mouseX, mouseY, getBase(recipe));
                        parent.renderIngredient(graphics, x - 5, y + 23, mouseX, mouseY, getAddition(recipe));
                        parent.renderIngredient(graphics, x + 14, y + 13, mouseX, mouseY, getTemplate(recipe));
                        parent.renderItemStack(graphics, x + 50, y + 13, mouseX, mouseY, recipe.getToastSymbol());
                        parent.renderItemStack(graphics, x + 86, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                    } else {
                        GUIBookPlus.drawRecipesTexturePlus(graphics, book, x, y, 139, 96, 43);
                        parent.renderIngredient(graphics, x + 4, y + 4, mouseX, mouseY, getBase(recipe));
                        parent.renderIngredient(graphics, x + 4, y + 23, mouseX, mouseY, getAddition(recipe));
                        parent.renderIngredient(graphics, x + 40, y + 4, mouseX, mouseY, getTemplate(recipe));
                        parent.renderItemStack(graphics, x + 40, y + 23, mouseX, mouseY, recipe.getToastSymbol());
                        parent.renderItemStack(graphics, x + 76, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                    }
                }
//                GUIBookPlus.drawRecipesTexturePlus(graphics, book, x, y, 183, 102, 43);
//                parent.renderIngredient(graphics, x + 4, y + 4, mouseX, mouseY, getBase(recipe));
//                parent.renderIngredient(graphics, x + 4, y + 23, mouseX, mouseY, getAddition(recipe));
//                parent.renderIngredient(graphics, x + 43, y + 4, mouseX, mouseY, getTemplate(recipe));
//                parent.renderItemStack(graphics, x + 43, y + 23, mouseX, mouseY, recipe.getToastSymbol());
//                parent.renderItemStack(graphics, x + 82, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
            }
            case CENTER -> {
                if (stationBorder) {
                    if (smithingTemplateAsIngredient) {
                        GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 12, y, 315, 121, 43);
                        parent.renderIngredient(graphics, x - 8, y + 4, mouseX, mouseY, getBase(recipe));
                        parent.renderIngredient(graphics, x - 8, y + 23, mouseX, mouseY, getAddition(recipe));
                        parent.renderIngredient(graphics, x + 11, y + 13, mouseX, mouseY, getTemplate(recipe));
                        parent.renderItemStack(graphics, x + 50, y + 13, mouseX, mouseY, recipe.getToastSymbol());
                        parent.renderItemStack(graphics, x + 89, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                    } else {
                        GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 3, y, 227, 102, 43);
                        parent.renderIngredient(graphics, x + 1, y + 4, mouseX, mouseY, getBase(recipe));
                        parent.renderIngredient(graphics, x + 1, y + 23, mouseX, mouseY, getAddition(recipe));
                        parent.renderIngredient(graphics, x + 40, y + 4, mouseX, mouseY, getTemplate(recipe));
                        parent.renderItemStack(graphics, x + 40, y + 23, mouseX, mouseY, recipe.getToastSymbol());
                        parent.renderItemStack(graphics, x + 79, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                    }
                } else {
                    if (smithingTemplateAsIngredient) {
                        GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 9, y, 271, 115, 43);
                        parent.renderIngredient(graphics, x - 5, y + 4, mouseX, mouseY, getBase(recipe));
                        parent.renderIngredient(graphics, x - 5, y + 23, mouseX, mouseY, getAddition(recipe));
                        parent.renderIngredient(graphics, x + 14, y + 13, mouseX, mouseY, getTemplate(recipe));
                        parent.renderItemStack(graphics, x + 50, y + 13, mouseX, mouseY, recipe.getToastSymbol());
                        parent.renderItemStack(graphics, x + 86, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                    } else {
                        GUIBookPlus.drawRecipesTexturePlus(graphics, book, x, y, 139, 96, 43);
                        parent.renderIngredient(graphics, x + 4, y + 4, mouseX, mouseY, getBase(recipe));
                        parent.renderIngredient(graphics, x + 4, y + 23, mouseX, mouseY, getAddition(recipe));
                        parent.renderIngredient(graphics, x + 40, y + 4, mouseX, mouseY, getTemplate(recipe));
                        parent.renderItemStack(graphics, x + 40, y + 23, mouseX, mouseY, recipe.getToastSymbol());
                        parent.renderItemStack(graphics, x + 76, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                    }
                }
            }
        }
    }

    private Ingredient getBase(SmithingRecipe recipe) {
        if (recipe instanceof SmithingTrimRecipe) return ((AccessorSmithingTrimRecipe) recipe).getBase();
        else return recipe instanceof SmithingTransformRecipe ? ((AccessorSmithingTransformRecipe) recipe).getBase() : Ingredient.EMPTY;
    }

    private Ingredient getAddition(SmithingRecipe recipe) {
        if (recipe instanceof SmithingTrimRecipe) return ((AccessorSmithingTrimRecipe) recipe).getAddition();
        else return recipe instanceof SmithingTransformRecipe ? ((AccessorSmithingTransformRecipe) recipe).getAddition() : Ingredient.EMPTY;
    }

    private Ingredient getTemplate(SmithingRecipe recipe) {
        if (recipe instanceof SmithingTrimRecipe) return ((AccessorSmithingTrimRecipe) recipe).getTemplate();
        else return recipe instanceof SmithingTransformRecipe ? ((AccessorSmithingTransformRecipe) recipe).getTemplate() : Ingredient.EMPTY;
    }
}