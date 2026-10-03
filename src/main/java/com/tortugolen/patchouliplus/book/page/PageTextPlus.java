package com.tortugolen.patchouliplus.book.page;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import com.tortugolen.patchouliplus.xplat.IBookPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.page.abstr.PageWithText;

public class PageTextPlus extends PageWithText {
    protected String title;
    protected transient ResourceLocation resolvedSeparator;

    protected enum TextPosition {
        LEFT,
        CENTER
    }

    @SerializedName("separator")
    protected boolean separator = false;

    @SerializedName("separator_type")
    protected String separatorTexture = null;

    @SerializedName("text_position")
    protected TextPosition textPosition = TextPosition.LEFT;

    @SerializedName("overflow")
    protected boolean overflow = false;

    public int getTextWidth() {
        return switch (textPosition) {
            case LEFT -> 0;
            case CENTER -> 58 / 2;
        };
    }

    @Override
    public int getTextHeight() {
        if (hasValidTitle() && separator) return 22;
        else if (hasValidTitle() && !separator) return 12;
        else if (!hasValidTitle() && separator) return 12;
        else return 0;
    }

    protected int getY() {
        if (hasValidTitle() && separator) return 22;
        else if (hasValidTitle() && !separator) return 10;
        else if (!hasValidTitle() && separator) return 10;
        else return 0;
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);

        resolvedSeparator = getSeparatorTexture(separatorTexture);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        super.render(graphics, mouseX, mouseY, pticks);

        if (hasValidTitle()) {
            parent.drawCenteredStringNoShadow(graphics, i18n(title), 58, 0, book.headerColor);
        }

        if (separator) {
            RenderSystem.enableBlend();
            graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            GUIBookPlus.drawCustomSeparator(graphics, resolvedSeparator, 3, hasValidTitle() ? 12 : 0, 110, 3);
        }
    }
    
    protected boolean hasValidTitle() {
        return title != null && !title.isEmpty();
    }

    protected ResourceLocation getCraftingTexturePlus() {
        return ((IBookPlus) book).getCraftingTexturePlus();
    }

    protected int getMaxComponentNumber() {
        return 5;
    }

    protected ResourceLocation getSeparatorTexture(String texture) {
        String folder = "textures/gui/separators/";

        if (texture != null && !texture.isEmpty()) {
            var resources = Minecraft.getInstance().getResourceManager();
            ResourceLocation named = ResourceLocation.tryParse(PatchouliPlus.MOD_ID + ":" + folder + texture + "_separator.png");
            ResourceLocation full = ResourceLocation.tryParse(texture);

            if (named != null && resources.getResource(named).isPresent()) return named;
            if (full != null && resources.getResource(full).isPresent()) return full;
        }
        return new ResourceLocation(PatchouliPlus.MOD_ID, folder + "simple_separator.png");
    }
}
