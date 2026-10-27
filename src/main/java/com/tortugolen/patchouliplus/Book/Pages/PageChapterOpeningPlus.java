package com.tortugolen.patchouliplus.Book.Pages;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.ParentPage;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class PageChapterOpeningPlus extends ParentPage {
    private static final int PAGE_CENTER_X = 58;
    private static final int PAGE_CENTER_Y = 78;
    private static final int MAX_WIDTH = 104;

    @SerializedName("title_position_chapter_opening")
    protected ChapterOpeningTitlePosition chapterOpeningTitlePosition = null;
    protected enum ChapterOpeningTitlePosition {
        @SerializedName("top")
        TOP,
        @SerializedName("center")
        CENTER
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        if (!hasValidTitle()) return;

        Font font = fontRenderer;
        float scale = 1.5F;

        List<FormattedCharSequence> lines = font.split(Component.literal(i18n(title)), (int) (MAX_WIDTH / scale));

        int lineHeight = font.lineHeight + 2;
        int totalHeight = lines.size() * lineHeight;
        int startY = 0;

        if (chapterOpeningTitlePosition == null) chapterOpeningTitlePosition = ChapterOpeningTitlePosition.TOP;
        switch (chapterOpeningTitlePosition) {
            case TOP -> startY = -39;
            case CENTER -> startY = (-totalHeight / 2) - 3;
        }

        PoseStack ms = graphics.pose();
        ms.pushPose();
        ms.translate(PAGE_CENTER_X, PAGE_CENTER_Y, 0);
        ms.scale(scale, scale, 1);

        for (int i = 0; i < lines.size(); i++) {
            FormattedCharSequence line = lines.get(i);
            graphics.drawString(font, line, -font.width(line) / 2, startY + i * lineHeight, book.headerColor, false);
        }

        ms.popPose();
    }
}