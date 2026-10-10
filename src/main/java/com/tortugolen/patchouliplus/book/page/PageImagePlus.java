package com.tortugolen.patchouliplus.book.page;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.book.page.Abstract.PageWithBorder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.gui.button.GuiButtonBookArrowSmall;

public class PageImagePlus extends PageWithBorder {
    ResourceLocation[] images;
    ResourceLocation[] imagesToDraw;
    transient int index;
    @SerializedName("quality")
    protected float quality = 1;
    @SerializedName("arrow_position")
    protected ButtonArrowPosition buttonArrowPosition = ButtonArrowPosition.BOTTOM_RIGHT;
    protected enum ButtonArrowPosition {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
//        MIDDLE_LEFT,
//        MIDDLE_CENTER,
//        MIDDLE_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT,
    }

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

        if (images.length > 1 && hasBorder(0)) drawButtonArrowBackground(graphics, x, y);

        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    protected boolean isQuadruple() {
        return true;
    }

    @Override
    protected ResourceLocation[] getImagesToDraw() {
        return imagesToDraw;
    }

    protected void drawImages(GuiGraphics graphics, int x, int y) {
        if (images == null || images.length == 0) return;

        int offset = ((200 - Math.round(200 * Math.max(0.1F, Math.min(1, quality)))) / 2);
        int imageX = x * 2 + 6 + offset;
        int imageY = y * 2 + 6 + offset;

//        graphics.pose().scale(quality, quality, quality);

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

//        graphics.pose().scale(1 / quality, 1 / quality, 1 / quality);
    }

    private void drawQuarterImage(GuiGraphics graphics, ResourceLocation image, int x, int y) {
        if (image == null) return;

        graphics.blit(image, x, y, 92, 92, 0, 0, 200, 200, 256, 256);
    }

    private ResourceLocation getImage(int i) {
        if (images == null) return null;
        if (i < 0 || i >= images.length) return null;
        if (images[i].getPath().isEmpty()) return null;
//        else getTextureResourceLocation(images[i].toString(), "textures/gui/images/", ".png", "forest.png");
//        imagesToDraw[i] = ResourceLocation.fromNamespaceAndPath(PatchouliPlus.MOD_ID, getImage(i).toString());
        return images[i];
    }

    protected void drawButtonArrow() {
        if (quadruple) return;

        int x = 0;
        int y = getY();

        switch (buttonArrowPosition) {
            case TOP_LEFT -> {
                x = x;
                y = y;
            }
            case TOP_CENTER -> {
                x = x + 45;
                y = y;
            }
            case TOP_RIGHT -> {
                x = x + 90;
                y = y;
            }
            case BOTTOM_LEFT -> {
                x = x;
                y = y + 100 - 6;
            }
            case BOTTOM_CENTER -> {
                x = x + 45;
                y = y + 100 - 6;
            }
            case BOTTOM_RIGHT -> {
                x = x + 90;
                y = y + 100 - 6;
            }
        }

        addButton(new GuiButtonBookArrowSmall(parent, x, y, true, () -> index > 0, this::handleButtonArrow));
        addButton(new GuiButtonBookArrowSmall(parent, x + 10, y, false, () -> index < images.length - 1, this::handleButtonArrow));
    }

    protected void drawButtonArrowBackground(GuiGraphics graphics, int x, int y) {
        if (quadruple) return;

        int bgx = x;
        int bgy = y;

        switch (buttonArrowPosition) {
            case TOP_LEFT -> {
                bgx = x;
                bgy = y;
            }
            case TOP_CENTER -> {
                bgx = x;
                bgy = y;
            }
            case TOP_RIGHT -> {
                bgx = x;
                bgy = y;
            }
            case BOTTOM_LEFT -> {
                bgx = x;
                bgy = y;
            }
            case BOTTOM_CENTER -> {
                bgx = x;
                bgy = y;
            }
            case BOTTOM_RIGHT -> {
                bgx = x + 83;
                bgy = y + 92;
            }
        }

        graphics.fill(bgx, bgy, bgx + 20, bgy + 11, 1140850688);
        graphics.fill(bgx - 1, bgy - 1, bgx + 20, bgy + 11, 1140850688);
    }

    public void handleButtonArrow(Button button) {
        boolean left = ((GuiButtonBookArrowSmall)button).left;
        if (left) --index;
        else ++index;
    }
}
