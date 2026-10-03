package com.tortugolen.patchouliplus.mixin;

import com.tortugolen.patchouliplus.xplat.IWordPlus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import vazkii.patchouli.client.book.text.Word;

@Mixin(Word.class)
public class WordMixin implements IWordPlus {

    @Mutable
    @Shadow
    public int x;

    @Override
    public void setX(int x) {
        this.x = x;
    }
}