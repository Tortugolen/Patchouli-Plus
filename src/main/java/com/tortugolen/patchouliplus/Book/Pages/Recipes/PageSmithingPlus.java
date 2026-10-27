package com.tortugolen.patchouliplus.Book.Pages.Recipes;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes.PageProcessingPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import vazkii.patchouli.mixin.AccessorSmithingTransformRecipe;
import vazkii.patchouli.mixin.AccessorSmithingTrimRecipe;

public class PageSmithingPlus extends PageProcessingPlus<SmithingRecipe> {
    @SerializedName("invert_ingredients")
    protected JsonElement invertIngredients;
    @SerializedName("ingredient_smithing_template")
    protected JsonElement smithingTemplateAsIngredient;

    public PageSmithingPlus() {
        super(RecipeType.SMITHING);
    }

    @Override
    protected void drawRecipe(GuiGraphics graphics, SmithingRecipe recipe, int x, int y, int mouseX, int mouseY, boolean second) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        RenderSystem.enableBlend();

        switch (alignment) {
            case LEFT -> {
                if (hasBooleanFeature(stationBorder, false)) {
                    if (hasBooleanFeature(smithingTemplateAsIngredient, false)) {
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
                    if (hasBooleanFeature(smithingTemplateAsIngredient, false)) {
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
                if (hasBooleanFeature(stationBorder, false)) {
                    if (hasBooleanFeature(invertIngredients, false)) {
                        if (hasBooleanFeature(smithingTemplateAsIngredient, false)) {
                            GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 12, y, 315, 121, 43);
                            parent.renderIngredient(graphics, x - 8, y + 23, mouseX, mouseY, getBase(recipe));
                            parent.renderIngredient(graphics, x - 8, y + 4, mouseX, mouseY, getAddition(recipe));
                            parent.renderIngredient(graphics, x + 11, y + 13, mouseX, mouseY, getTemplate(recipe));
                            parent.renderItemStack(graphics, x + 50, y + 13, mouseX, mouseY, recipe.getToastSymbol());
                            parent.renderItemStack(graphics, x + 89, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                        } else {
                            GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 3, y, 227, 102, 43);
                            parent.renderIngredient(graphics, x + 1, y + 23, mouseX, mouseY, getBase(recipe));
                            parent.renderIngredient(graphics, x + 1, y + 4, mouseX, mouseY, getAddition(recipe));
                            parent.renderIngredient(graphics, x + 40, y + 4, mouseX, mouseY, getTemplate(recipe));
                            parent.renderItemStack(graphics, x + 40, y + 23, mouseX, mouseY, recipe.getToastSymbol());
                            parent.renderItemStack(graphics, x + 79, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                        }
                    } else {
                        if (hasBooleanFeature(smithingTemplateAsIngredient, false)) {
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
                    }
                } else {
                    if (hasBooleanFeature(invertIngredients, false)) {
                        if (hasBooleanFeature(smithingTemplateAsIngredient, false)) {
                            GUIBookPlus.drawRecipesTexturePlus(graphics, book, x - 9, y, 271, 115, 43);
                            parent.renderIngredient(graphics, x - 5, y + 23, mouseX, mouseY, getBase(recipe));
                            parent.renderIngredient(graphics, x - 5, y + 4, mouseX, mouseY, getAddition(recipe));
                            parent.renderIngredient(graphics, x + 14, y + 13, mouseX, mouseY, getTemplate(recipe));
                            parent.renderItemStack(graphics, x + 50, y + 13, mouseX, mouseY, recipe.getToastSymbol());
                            parent.renderItemStack(graphics, x + 86, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                        } else {
                            GUIBookPlus.drawRecipesTexturePlus(graphics, book, x, y, 139, 96, 43);
                            parent.renderIngredient(graphics, x + 4, y + 23, mouseX, mouseY, getBase(recipe));
                            parent.renderIngredient(graphics, x + 4, y + 4, mouseX, mouseY, getAddition(recipe));
                            parent.renderIngredient(graphics, x + 40, y + 4, mouseX, mouseY, getTemplate(recipe));
                            parent.renderItemStack(graphics, x + 40, y + 23, mouseX, mouseY, recipe.getToastSymbol());
                            parent.renderItemStack(graphics, x + 76, y + 13, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));
                        }
                    } else {
                        if (hasBooleanFeature(smithingTemplateAsIngredient, false)) {
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
    }

    @Override
    protected int getRecipeHeight(SmithingRecipe recipe) {
        return 43;
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