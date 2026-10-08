package com.tortugolen.patchouliplus.book.page.not.recipes;

import com.tortugolen.patchouliplus.book.page.not.abstr.PageMultiProcessingSimpleRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class PageSmeltingPlus extends PageMultiProcessingSimpleRecipe<SmeltingRecipe> {
    public PageSmeltingPlus() {
        super(RecipeType.SMELTING);
    }
}
