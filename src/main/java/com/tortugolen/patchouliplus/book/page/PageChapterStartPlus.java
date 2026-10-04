package com.tortugolen.patchouliplus.book.page;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import vazkii.patchouli.client.book.BookPage;

import java.util.List;
import java.util.Objects;

public class PageChapterStartPlus extends BookPage {
    private static final int PAGE_CENTER_X = 58;
    private static final int PAGE_CENTER_Y = 78;
    private static final int MAX_WIDTH = 104;

    protected String title;

    protected boolean hasValidTitle() {
        return title != null && !title.isEmpty();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        if (!hasValidTitle()) return;

        Font font = fontRenderer;
        float scale = 2;

        List<FormattedCharSequence> lines = font.split(Component.literal(i18n(title)), (int) (MAX_WIDTH / scale));

        int lineHeight = font.lineHeight + 2;
        int totalHeight = Objects.equals(title, "Text Values Display") ? lines.size() * lineHeight + 8 : lines.size() * lineHeight;
        int startY = -totalHeight / 2;

        PoseStack ms = graphics.pose();
        ms.pushPose();
        ms.translate(PAGE_CENTER_X, PAGE_CENTER_Y, 0.0F);
        ms.scale(scale, scale, 1.0F);

        for (int i = 0; i < lines.size(); i++) {
            FormattedCharSequence line = lines.get(i);
            graphics.drawString(font, line, -font.width(line) / 2, startY + i * lineHeight, book.headerColor, false);
        }

        ms.popPose();
    }
}