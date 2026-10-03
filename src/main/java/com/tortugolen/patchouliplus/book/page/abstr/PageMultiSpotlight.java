package com.tortugolen.patchouliplus.book.page.abstr;

import com.tortugolen.patchouliplus.book.page.PageTextPlus;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import vazkii.patchouli.api.IVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class PageMultiSpotlight extends PageTextPlus {

    protected Ingredient resolveIngredient(IVariable var) {
        if (var == null) return null;

        String raw = var.asString();
        if (raw == null || !raw.contains(",")) {
            return var.as(Ingredient.class);
        }

        List<ItemStack> stacks = new ArrayList<>();
        for (String part : raw.split(",")) {
            Ingredient single = IVariable.wrap(part.trim()).as(Ingredient.class);
            if (single != null) {
                stacks.addAll(Arrays.asList(single.getItems()));
            }
        }

        return stacks.isEmpty() ? null : Ingredient.of(stacks.toArray(new ItemStack[0]));
    }

    @Override
    public int getTextHeight() {
        return super.getTextHeight() + getRecipeHeight() * getRecipeNumber();
    }

    protected abstract int getRecipeNumber();

    protected int getX() {
        return 26;
    }

    protected int getRecipeHeight() {
        return 24 + 4;
    }
}
