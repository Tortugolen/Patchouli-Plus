package com.tortugolen.patchouliplus.book.page.recipes;

import com.tortugolen.patchouliplus.book.page.abstr.PageMultiProcessingSimpleRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class PageSmeltingPlus extends PageMultiProcessingSimpleRecipe<SmeltingRecipe> {
    public PageSmeltingPlus() {
        super(RecipeType.SMELTING);
    }
}
