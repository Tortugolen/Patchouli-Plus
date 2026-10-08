package com.tortugolen.patchouliplus.book.page;

import com.google.gson.annotations.SerializedName;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import com.tortugolen.patchouliplus.book.page.Abstract.ParentPage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

public class PageTextPlus extends ParentPage {
    protected IVariable text;
    transient BookTextRenderer textRender;
    @SerializedName("text_position")
    protected TextPosition textPosition = TextPosition.LEFT;
    @SerializedName("overflow")
    protected boolean overflow = false;
    protected enum TextPosition {
        LEFT,
        CENTER
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        if (text == null) text = IVariable.wrap("");
        switch (textPosition) {
            case LEFT -> textRender = new BookTextRenderer(parent, text.as(Component.class), 0, getTextY());
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        super.render(graphics, mouseX, mouseY, pticks);
        if (shouldRenderText()) textRender.render(graphics, mouseX, mouseY);
        if (hasValidTitle()) parent.drawCenteredStringNoShadow(graphics, i18n(title), 58, 0, book.headerColor);
        if (separator) GUIBookPlus.drawSeparator(graphics, getSeparatorTexture(0), 3, hasValidTitle() ? 12 : 0, 110, 3);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return shouldRenderText() && textRender.click(mouseX, mouseY, mouseButton);
    }

    protected boolean shouldRenderText() {
        return true;
    }

    protected int getTextWidth() {
        return switch (textPosition) {
            case LEFT -> 0;
            case CENTER -> 58 / 2;
        };
    }

    protected int getY() {
        if (hasValidTitle() && separator) return 22;
        else if (!hasValidTitle() && !separator) return 0;
        else return 10;
    }

    public int getTextY() {
        if (hasValidTitle() && separator) return 22;
        else if (!hasValidTitle() && !separator) return 0;
        else return 12;
    }
}
