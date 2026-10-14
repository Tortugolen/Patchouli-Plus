package com.tortugolen.patchouliplus.Book.Pages.Recipes;

import com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes.PageMultiProcessingSimpleRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;

public class PageStonecuttingPlus extends PageMultiProcessingSimpleRecipe<StonecutterRecipe> {
    public PageStonecuttingPlus() {
        super(RecipeType.STONECUTTING);
    }
}
