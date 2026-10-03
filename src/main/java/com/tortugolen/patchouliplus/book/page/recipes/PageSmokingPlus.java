package com.tortugolen.patchouliplus.book.page.recipes;

import com.tortugolen.patchouliplus.book.page.abstr.PageMultiProcessingSimpleRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;

public class PageSmokingPlus extends PageMultiProcessingSimpleRecipe<SmokingRecipe> {
    public PageSmokingPlus() {
        super(RecipeType.SMOKING);
    }
}
