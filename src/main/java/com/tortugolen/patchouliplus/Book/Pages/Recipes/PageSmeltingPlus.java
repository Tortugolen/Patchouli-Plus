package com.tortugolen.patchouliplus.Book.Pages.Recipes;

import com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes.PageMultiProcessingSimpleRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class PageSmeltingPlus extends PageMultiProcessingSimpleRecipe<SmeltingRecipe> {
    public PageSmeltingPlus() {
        super(RecipeType.SMELTING);
    }
}
