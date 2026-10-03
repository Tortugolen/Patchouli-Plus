package com.tortugolen.patchouliplus.book.page.recipes;

import com.tortugolen.patchouliplus.book.page.abstr.PageMultiProcessingSimpleRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;

public class PageStonecuttingPlus extends PageMultiProcessingSimpleRecipe<StonecutterRecipe> {
    public PageStonecuttingPlus() {
        super(RecipeType.STONECUTTING);
    }
}
