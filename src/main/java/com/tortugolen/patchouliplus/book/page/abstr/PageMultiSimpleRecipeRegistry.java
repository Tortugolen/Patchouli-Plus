package com.tortugolen.patchouliplus.book.page.abstr;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;

public abstract class PageMultiSimpleRecipeRegistry<T extends Recipe<?>> extends PageMultiSimpleRecipe<T> {
    private final RecipeType<? extends T> recipeType;

    public PageMultiSimpleRecipeRegistry(RecipeType<? extends T> recipeType) {
        this.recipeType = recipeType;
    }

    private @Nullable T getRecipe(Level level, ResourceLocation id) {
        RecipeManager manager = level.getRecipeManager();
        return (T) manager.byKey(id).filter(recipe -> recipe.getType() == this.recipeType).orElse(null);
    }

    @Override
    protected T loadRecipe(Level level, BookContentsBuilder builder, BookEntry entry, ResourceLocation res) {
        if (res == null || level == null) {
            return null;
        }

        T recipe = getRecipe(level, res);
        if (recipe == null) {
            PatchouliAPI.LOGGER.warn("Recipe {} (of type {}) not found", res, BuiltInRegistries.RECIPE_TYPE.getKey(recipeType));
            return null;
        }

        entry.addRelevantStack(builder, recipe.getResultItem(level.registryAccess()), this.pageNum);
        return recipe;
    }
}