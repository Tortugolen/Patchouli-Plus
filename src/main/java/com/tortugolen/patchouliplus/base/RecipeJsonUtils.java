package com.tortugolen.patchouliplus.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public final class RecipeJsonUtils {

    private RecipeJsonUtils() {}

    public static Ingredient toIngredient(String raw) {
        if (raw == null || raw.isEmpty()) return null;

        List<ItemStack> stacks = new ArrayList<>();
        for (String part : raw.split(",")) {
            ResourceLocation id = new ResourceLocation(part.trim());
            Item item = ForgeRegistries.ITEMS.getValue(id);
            if (item != null) {
                stacks.add(new ItemStack(item));
            }
        }

        return stacks.isEmpty() ? null : Ingredient.of(stacks.toArray(new ItemStack[0]));
    }
}