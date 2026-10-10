package com.tortugolen.patchouliplus.Book.Pages;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.ParentPage;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;
import java.util.Objects;

public class ChapterStartPlusPage extends ParentPage {
    private static final int PAGE_CENTER_X = 58;
    private static final int PAGE_CENTER_Y = 78;
    private static final int MAX_WIDTH = 104;
    @SerializedName("title_position")
    protected TitlePosition titlePosition = TitlePosition.TOP;
    protected enum TitlePosition {
        TOP,
        CENTER
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        if (!hasValidTitle()) return;

        Font font = fontRenderer;
        float scale = 2;

        List<FormattedCharSequence> lines = font.split(Component.literal(i18n(title)), (int) (MAX_WIDTH / scale));

        int lineHeight = font.lineHeight + 2;
        int totalHeight = Objects.equals(title, "Text Values Display") ? lines.size() * lineHeight + 8 : lines.size() * lineHeight;

        int startY = 0;
        if (titlePosition == TitlePosition.TOP) startY = -39;
        if (titlePosition == TitlePosition.CENTER) startY = -totalHeight / 2;

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