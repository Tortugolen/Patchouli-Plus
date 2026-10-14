package com.tortugolen.patchouliplus.Mixin;

import com.google.gson.JsonObject;
import com.tortugolen.patchouliplus.Xplat.IBookPlus;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.util.SerializationUtil;
import vazkii.patchouli.xplat.XplatModContainer;

@Mixin(Book.class)
public abstract class BookMixin implements IBookPlus {

    @Unique
    private ResourceLocation patchouliPlus$recipesTexturePlus;

    @Unique
    private ResourceLocation patchouliPlus$spotlightTexturePlus;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void patchouliPlus$onInit(JsonObject root, XplatModContainer owner, ResourceLocation id, boolean external, CallbackInfo ci) {
        patchouliPlus$recipesTexturePlus = SerializationUtil.getAsResourceLocation(root, "recipes_texture_plus", null);
        patchouliPlus$spotlightTexturePlus = SerializationUtil.getAsResourceLocation(root, "spotlight_texture_plus", null);
    }

    @Override
    public ResourceLocation getRecipesTexturePlus() {
        return patchouliPlus$recipesTexturePlus;
    }

    @Override
    public ResourceLocation getSpotlightTexturePlus() {
        return patchouliPlus$spotlightTexturePlus;
    }
}