package com.tortugolen.patchouliplus.Base;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum EBrewingRecipeVariant {
    NORMAL("normal", Items.POTION, ""),
    LONG("long", Items.POTION, "long_"),
    STRONG("strong", Items.POTION, "strong_"),
    SPLASH("splash", Items.SPLASH_POTION, ""),
    SPLASH_LONG("splash_long", Items.SPLASH_POTION, "long_"),
    SPLASH_STRONG("splash_strong", Items.SPLASH_POTION, "strong_"),
    LINGERING("lingering", Items.LINGERING_POTION, ""),
    LINGERING_LONG("lingering_long", Items.LINGERING_POTION, "long_"),
    LINGERING_STRONG("lingering_strong", Items.LINGERING_POTION, "strong_");

    public final String id;
    public final Item container;
    public final String prefix;

    EBrewingRecipeVariant(String id, Item container, String prefix) {
        this.id = id;
        this.container = container;
        this.prefix = prefix;
    }

    public static EBrewingRecipeVariant byId(String id) {
        if (id == null) return null;
        for (EBrewingRecipeVariant recipeVariant : values()) if (recipeVariant.id.equalsIgnoreCase(id.trim())) return recipeVariant;
        return null;
    }
}
