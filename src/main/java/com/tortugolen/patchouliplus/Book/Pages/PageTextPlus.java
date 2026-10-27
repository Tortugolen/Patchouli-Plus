package com.tortugolen.patchouliplus.Book.Pages;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.ParentPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.text.BookTextParser;
import vazkii.patchouli.client.book.text.Span;

import java.util.ArrayList;
import java.util.List;

public class PageTextPlus extends ParentPage {
    protected static final int TEXT_WIDTH = 116;
    protected static final int LINE_HEIGHT = 9;

    protected IVariable text;
    @SerializedName("text_position")
    protected TextPosition textPosition = null;
    protected enum TextPosition {
        @SerializedName("left")
        LEFT,
        @SerializedName("center")
        CENTER
    }
    protected transient boolean overflow = false;
    protected transient BookTextRenderer textRender;
    protected transient List<BookTextRenderer> centeredLines = new ArrayList<>();

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        if (text == null) text = IVariable.wrap("");
        Component component = text.as(Component.class);
        centeredLines = new ArrayList<>();

        if (textPosition == null) textPosition = TextPosition.LEFT;
        switch (textPosition) {
            case LEFT -> textRender = new BookTextRenderer(parent, component, 0, getTextY());
            case CENTER -> {
                textRender = null;

                Font font = Minecraft.getInstance().font;
                Style base = book.getFontStyle().withColor(TextColor.fromRgb(book.textColor));
                BookTextParser parser = new BookTextParser(parent, book, 0, 0, TEXT_WIDTH, LINE_HEIGHT, base);;

                String raw = parser.expandMacros(component.getString())
                        .replace("<br>", "$(br)")
                        .replace("$(br2)", "$(br)$(br)")
                        .replace("$(2br)", "$(br)$(br)")
                        .replace("$(p)", "$(br)$(br)");

                int y = getTextY();

                for (String segment : raw.split("\\$\\(br\\)|\n", -1)) {
                    MutableComponent result = Component.empty();
                    for (Span span : parser.parse(Component.literal(segment))) result.append(Component.literal(span.text).withStyle(span.style));
                    int width = Math.min(font.width(result), TEXT_WIDTH);
                    int x = (TEXT_WIDTH - width) / 2;

                    if (!segment.isEmpty()) centeredLines.add(new BookTextRenderer(parent, Component.literal(segment), x, y));

                    y += LINE_HEIGHT * Math.max(1, font.split(result, TEXT_WIDTH).size());
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        super.render(graphics, mouseX, mouseY, pticks);

        if (hasValidTitle()) parent.drawCenteredStringNoShadow(graphics, i18n(title), 58, getTitleY(), book.headerColor);

        if (renderText()) {
            if (textPosition == null) textPosition = TextPosition.LEFT;
            switch (textPosition) {
                case LEFT -> {
                    if (textRender != null) textRender.render(graphics, mouseX, mouseY);
                }
                case CENTER -> {
                    for (BookTextRenderer renderer : centeredLines) renderer.render(graphics, mouseX, mouseY);
                }
            }
        }

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);

        if (hasSeparator()) GUIBookPlus.drawSeparator(graphics, getSeparatorTexture(), 3, hasValidTitle() ? 12 : 0, 110, 3);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!renderText()) return false;
        if (textRender != null) return textRender.click(mouseX, mouseY, mouseButton);
        for (BookTextRenderer renderer : centeredLines) if (renderer.click(mouseX, mouseY, mouseButton)) return true;
        return false;
    }

    protected boolean renderText() {
        return true;
    }

    public int getTextY() {
        if (hasValidTitle() && hasSeparator()) return 22;
        else if (!hasValidTitle() && !hasSeparator()) return 0;
        else return 12;
    }
}
