package com.tortugolen.patchouliplus.Book.Pages;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.PageWithFrame;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.gui.button.GuiButtonBookArrowSmall;

public class PageImagePlus extends PageWithFrame {
    protected ResourceLocation[] images; // Hacer que se pueda solo poner el nombre y no toda la ruta
    protected transient float quality = 1;
    @SerializedName("arrow_position")
    protected ArrowButtonPosition arrowButtonPosition = ArrowButtonPosition.BOTTOM_RIGHT;
    protected enum ArrowButtonPosition {
        @SerializedName("top_left")
        TOP_LEFT,
        @SerializedName("top_center")
        TOP_CENTER,
        @SerializedName("top_right")
        TOP_RIGHT,
        @SerializedName("bottom_left")
        BOTTOM_LEFT,
        @SerializedName("bottom_center")
        BOTTOM_CENTER,
        @SerializedName("bottom_right")
        BOTTOM_RIGHT,
        @SerializedName("horizontal")
        HORIZONTAL,
        @SerializedName("vertical")
        VERTICAL;
    }
    protected transient int index;

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        drawButtonArrow();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = START_X;
        int y = getY();

        RenderSystem.enableBlend();
        graphics.setColor(1, 1, 1, 1);

        graphics.pose().pushPose();
        graphics.pose().scale(0.5F, 0.5F, 0.5F);
        drawImages(graphics, x, y);
        graphics.pose().popPose();

        if (images.length > 1 && hasFrame()) drawButtonArrowBackground(graphics, x, y);

        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    protected boolean isQuadruple() {
        return true;
    }

    @Override
    protected boolean hasFeature(int slot) {
        return getImage(slot) != null;
    }

    public void handleButtonArrow(Button button) {
        boolean left = ((GuiButtonBookArrowSmall)button).left;
        if (left) --index;
        else ++index;
    }

    protected void drawImages(GuiGraphics graphics, int x, int y) {
        if (images == null || images.length == 0) return;

        int offset = ((200 - Math.round(200 * Math.max(0.1F, Math.min(1, quality)))) / 2);
        int imageX = x * 2 + 6 + offset;
        int imageY = y * 2 + 6 + offset;

        if (isQuadruple()) {
            if (quadruple) {
                if (getImage(0) != null) drawQuarterImage(graphics, getImage(0), imageX, imageY);
                if (getImage(1) != null) drawQuarterImage(graphics, getImage(1), imageX + SEPARATION * 2, imageY);
                if (getImage(2) != null) drawQuarterImage(graphics, getImage(2), imageX, imageY + SEPARATION * 2);
                if (getImage(3) != null) drawQuarterImage(graphics, getImage(3), imageX + SEPARATION * 2, imageY + SEPARATION * 2);
            } else {
                graphics.blit(images[index], imageX, imageY, 0, 0, 200, 200);
            }
        } else {
            graphics.blit(images[index], imageX, imageY, 0, 0, 200, 200);
        }
    }

    private void drawQuarterImage(GuiGraphics graphics, ResourceLocation image, int x, int y) {
        if (image == null) return;

        graphics.blit(image, x, y, 92, 92, 0, 0, 200, 200, 256, 256);
    }

    private ResourceLocation getImage(int i) {
        if (images == null) return null;
        if (i < 0 || i >= images.length) return null;
        if (images[i].getPath().isEmpty()) return null;
        return images[i];
    }

    protected void drawButtonArrow() {
        if (quadruple) return;
        if (arrowButtonPosition == null) arrowButtonPosition = ArrowButtonPosition.BOTTOM_RIGHT;

        int x = START_X;
        int y = getY();

        if (arrowButtonPosition == ArrowButtonPosition.HORIZONTAL) {
            addButton(new GuiButtonBookArrowSmall(parent, x + 6, y + 49, true, () -> index > 0, this::handleButtonArrow));
            addButton(new GuiButtonBookArrowSmall(parent, x + 85 + 10, y + 49, false, () -> index < images.length - 1, this::handleButtonArrow));
            return;
        }

        switch (arrowButtonPosition) {
            case TOP_LEFT -> {
                x = x + 6;
                y = y + 5;
            }
            case TOP_CENTER -> {
                x = x + 46;
                y = y + 5;
            }
            case TOP_RIGHT -> {
                x = x + 85;
                y = y + 5;
            }
            case BOTTOM_LEFT -> {
                x = x + 6;
                y = y + 93;
            }
            case BOTTOM_CENTER -> {
                x = x + 46;
                y = y + 93;
            }
            default -> {
                x = x + 85;
                y = y + 93;
            }
        }

        addButton(new GuiButtonBookArrowSmall(parent, x, y, true, () -> index > 0, this::handleButtonArrow));
        addButton(new GuiButtonBookArrowSmall(parent, x + 10, y, false, () -> index < images.length - 1, this::handleButtonArrow));
    }

    protected void drawButtonArrowBackground(GuiGraphics graphics, int x, int y) {
        if (quadruple) return;
        if (arrowButtonPosition == null) arrowButtonPosition = ArrowButtonPosition.BOTTOM_RIGHT;

        int color = 1140850688;

        if (arrowButtonPosition == ArrowButtonPosition.HORIZONTAL) {
            graphics.fill(x + 5 - 2, y + 49 - 2, x + 5 + 8, y + 49 + 10, color);
            graphics.fill(x + 5 - 2, y + 49 - 3, x + 5 + 9, y + 49 + 11, color);
            graphics.fill(x + 85 + 10 - 2, y + 49 - 2, x + 85 + 10 + 8, y + 49 + 10, color);
            graphics.fill(x + 85 + 10 - 3, y + 49 - 3, x + 85 + 10 + 8, y + 49 + 11, color);
            return;
        }

        switch (arrowButtonPosition) {
            case TOP_LEFT -> {
                graphics.fill(x + 3, y + 3, x + 4 + 20 - 1, y + 3 + 11, color);
                graphics.fill(x + 3, y + 3, x + 4 + 20 + 1 - 1, y + 3 + 11 + 1, color);
            }
            case TOP_CENTER -> {
                graphics.fill(x + 43, y + 3, x + 44 + 20, y + 3 + 11, color);
                graphics.fill(x + 43 - 1, y + 3, x + 44 + 20 + 1, y + 3 + 11 + 1, color);
            }
            case TOP_RIGHT -> {
                graphics.fill(x + 83, y + 3, x + 83 + 20, y + 3 + 11, color);
                graphics.fill(x + 83 - 1, y + 3, x + 83 + 20, y + 3 + 11 + 1, color);
            }
            case BOTTOM_LEFT -> {
                graphics.fill(x + 3, y + 92, x + 4 + 20 - 1, y + 92 + 11, color);
                graphics.fill(x + 3, y + 92 - 1, x + 4 + 20 + 1 - 1, y + 92 + 11, color);
            }
            case BOTTOM_CENTER -> {
                graphics.fill(x + 43, y + 92, x + 44 + 20, y + 92 + 11, color);
                graphics.fill(x + 43 - 1, y + 92 - 1, x + 44 + 20 + 1, y + 92 + 11, color);
            }
            default -> {
                graphics.fill(x + 83, y + 92, x + 83 + 20, y + 92 + 11, color);
                graphics.fill(x + 83 - 1, y + 92 - 1, x + 83 + 20, y + 92 + 11, color);
            }
        }
    }
}
