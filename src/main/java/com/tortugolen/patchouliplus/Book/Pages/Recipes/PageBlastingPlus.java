package com.tortugolen.patchouliplus.Book.Pages.Recipes;

import com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes.PageProcessingPlus;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public class PageBlastingPlus extends PageProcessingPlus<BlastingRecipe> {
    public PageBlastingPlus() {
        super(RecipeType.BLASTING);
    }
}
