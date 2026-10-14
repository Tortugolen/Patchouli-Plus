package com.tortugolen.patchouliplus.Book.Pages.Recipes;

import com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes.PageMultiProcessingSimpleRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;

public class PageSmokingPlus extends PageMultiProcessingSimpleRecipe<SmokingRecipe> {
    public PageSmokingPlus() {
        super(RecipeType.SMOKING);
    }
}
