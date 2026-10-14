package com.tortugolen.patchouliplus.Book.Pages;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.ParentPage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

public class PageTextPlus extends ParentPage {
    protected IVariable text;
    protected transient BookTextRenderer textRender;
    @SerializedName("text_position")
    protected transient TextPosition textPosition = TextPosition.LEFT;
    protected boolean overflow = false;
    protected enum TextPosition {
        @SerializedName("left")
        LEFT,
        @SerializedName("center")
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

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);

        if (hasSeparator()) GUIBookPlus.drawSeparator(graphics, getSeparatorTexture(), 3, hasValidTitle() ? 12 : 0, 110, 3);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return shouldRenderText() && textRender.click(mouseX, mouseY, mouseButton);
    }

    protected boolean shouldRenderText() {
        return true;
    }

    public int getTextY() {
        if (hasValidTitle() && hasSeparator()) return 22;
        else if (!hasValidTitle() && !hasSeparator()) return 0;
        else return 12;
    }
}
