package com.tortugolen.patchouliplus.mixin;

import com.tortugolen.patchouliplus.xplat.IWordPlus;
import com.tortugolen.patchouliplus.xplat.TextAlign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.client.book.text.TextLayouter;
import vazkii.patchouli.client.book.text.Word;

import java.util.List;

@Mixin(value = TextLayouter.class, remap = false)
public abstract class TextLayouterMixin {

    @Shadow
    private List<Word> words;

    @Shadow
    private int pageX;

    @Shadow
    private int pageWidth;

    @Unique
    private int patchouliPlus$lineStart;

    @Inject(method = "flush", at = @At("HEAD"))
    private void patchouliPlus$flushHead(CallbackInfo ci) {
        this.patchouliPlus$lineStart = this.words.size();
    }

    @Inject(method = "flush", at = @At("RETURN"))
    private void patchouliPlus$flushReturn(CallbackInfo ci) {
        patchouliPlus$centerLine(this.patchouliPlus$lineStart);
    }

    @Inject(method = "breakLine(I)V", at = @At("HEAD"))
    private void patchouliPlus$breakLineHead(int textOffset, CallbackInfo ci) {
        this.patchouliPlus$lineStart = this.words.size();
    }

    @Inject(method = "breakLine(I)V", at = @At("RETURN"))
    private void patchouliPlus$breakLineReturn(int textOffset, CallbackInfo ci) {
        patchouliPlus$centerLine(this.patchouliPlus$lineStart);
    }

    @Unique
    private void patchouliPlus$centerLine(int fromIndex) {
        if (!TextAlign.CENTER.get()) return;
        if (fromIndex >= this.words.size()) return;

        int maxRight = this.pageX;
        for (int i = fromIndex; i < this.words.size(); i++) {
            Word w = this.words.get(i);
            maxRight = Math.max(maxRight, w.x + w.width);
        }

        int contentWidth = maxRight - this.pageX;
        int offset = (this.pageWidth - contentWidth) / 2;

        if (offset > 0) {
            for (int i = fromIndex; i < this.words.size(); i++) {
                Word w = this.words.get(i);
                ((IWordPlus) w).setX(w.x + offset);
            }
        }
    }
}