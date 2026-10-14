package com.tortugolen.patchouliplus.Book.Pages.Recipes;

import com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes.PageMultiProcessingSimpleRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public class PageCampfireCookingPlus extends PageMultiProcessingSimpleRecipe<CampfireCookingRecipe> {
    public PageCampfireCookingPlus() {
        super(RecipeType.CAMPFIRE_COOKING);
    }
}
