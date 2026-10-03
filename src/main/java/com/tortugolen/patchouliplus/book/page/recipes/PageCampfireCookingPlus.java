package com.tortugolen.patchouliplus.book.page.recipes;

import com.tortugolen.patchouliplus.book.page.abstr.PageMultiProcessingSimpleRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public class PageCampfireCookingPlus extends PageMultiProcessingSimpleRecipe<CampfireCookingRecipe> {
    public PageCampfireCookingPlus() {
        super(RecipeType.CAMPFIRE_COOKING);
    }
}
