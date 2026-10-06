package com.tortugolen.patchouliplus.book.page;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.gui.button.GuiButtonBookArrowSmall;

public class PageImagePlus extends PageTextPlus {
    ResourceLocation[] images;
    transient int index;
    protected transient ResourceLocation resolvedBorder;

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

    @SerializedName("border")
    protected boolean border = true;

    @SerializedName("border_type")
    protected String borderTexture = null;

    @SerializedName("arrow_position")
    protected ButtonArrowPosition buttonArrowPosition = ButtonArrowPosition.BOTTOM_RIGHT;

    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);

        resolvedBorder = null;

        drawButtonArrow();
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = 5;
        int y = getY();

        RenderSystem.enableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.pose().scale(0.5F, 0.5F, 0.5F);
        drawImages(graphics, x, y);

        if (border) {
            drawBorder(graphics, x, y);
        }

        if (images.length > 1 && border) {
            drawButtonArrowBackground(graphics, x, y);
        }

        super.render(graphics, mouseX, mouseY, pticks);
    }

    public void handleButtonArrow(Button button) {
        boolean left = ((GuiButtonBookArrowSmall)button).left;
        if (left) {
            --index;
        } else {
            ++index;
        }
    }

    protected void drawImages(GuiGraphics graphics, int x, int y) {
        graphics.blit(images[index], x * 2 + 6, y * 2 + 6, 0, 0, 200, 200);
        graphics.pose().scale(2.0F, 2.0F, 2.0F);
    }

    protected void drawBorder(GuiGraphics graphics, int x, int y) {
        GUIBookPlus.drawCustomBorder(graphics, getBorderTexture(), x, y, 1, 1, 106, 106);
    }

    protected void drawButtonArrow() {
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
        int bgx = x + 83;
        int bgy = y + 92;

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
                bgx = x;
                bgy = y;
            }
        }

        graphics.fill(bgx, bgy, bgx + 20, bgy + 11, 1140850688);
        graphics.fill(bgx - 1, bgy - 1, bgx + 20, bgy + 11, 1140850688);
    }

    @Override
    public int getTextHeight() {
        return super.getTextHeight() + 106 + 4;
    }

    protected ResourceLocation getBorderTexture() {
        if (resolvedBorder != null) return resolvedBorder;

        String bordersFolder = "textures/gui/borders/";
        ResourceLocation defaultBorder = new ResourceLocation(PatchouliPlus.MOD_ID, bordersFolder + "simple_border.png");
        ResourceLocation displayBorder = defaultBorder;

        var resources = Minecraft.getInstance().getResourceManager();

        if (borderTexture != null && !borderTexture.isEmpty()) {
            ResourceLocation named = ResourceLocation.tryParse(PatchouliPlus.MOD_ID + ":" + bordersFolder + borderTexture + "_border.png");
            ResourceLocation full = ResourceLocation.tryParse(borderTexture);

            if (named != null && resources.getResource(named).isPresent()) displayBorder = named;
            else if (full != null && resources.getResource(full).isPresent()) displayBorder = full;
        }

        return resolvedBorder = displayBorder;
    }
}
