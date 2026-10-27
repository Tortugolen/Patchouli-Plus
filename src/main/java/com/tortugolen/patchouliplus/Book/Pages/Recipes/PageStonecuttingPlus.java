package com.tortugolen.patchouliplus.Book.Pages.Recipes;

import com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes.PageProcessingPlus;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;

public class PageStonecuttingPlus extends PageProcessingPlus<StonecutterRecipe> {
    public PageStonecuttingPlus() {
        super(RecipeType.STONECUTTING);
    }
}
