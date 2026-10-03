package com.tortugolen.patchouliplus.mixin;

import com.google.gson.JsonObject;
import com.tortugolen.patchouliplus.xplat.IBookPlus;
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
    private ResourceLocation craftingTexturePlus;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void patchouliPlus$onInit(JsonObject root, XplatModContainer owner, ResourceLocation id, boolean external, CallbackInfo ci) {
        this.craftingTexturePlus = SerializationUtil.getAsResourceLocation(root, "crafting_texture_plus", (ResourceLocation) null);
    }

    @Override
    public ResourceLocation getCraftingTexturePlus() {
        return this.craftingTexturePlus;
    }
}