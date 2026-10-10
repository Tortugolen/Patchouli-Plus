package com.tortugolen.patchouliplus.book.page.Abstract;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import com.tortugolen.patchouliplus.book.page.PageTextPlus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public abstract class PageWithBorder extends PageTextPlus {
    protected static final int SEPARATION = 54;
    @SerializedName("quadruple")
    protected boolean quadruple = false;

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = START_X;
        int y = getY();

        RenderSystem.enableBlend();
        graphics.setColor(1, 1, 1, 1);

        drawBorders(graphics, x, y);

        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    public int getTextY() {
        return super.getTextY() + 106 + 4;
    }

    protected void drawBorders(GuiGraphics graphics, int x, int y) {
        if (getImagesToDraw() == null) return;
        if (isQuadruple()) {
            if (quadruple) {
                if (hasBorder(0)) GUIBookPlus.drawBorderQuarter(graphics, getQuarterBorderTexture(0), x, y, 1, 1, 52, 52);
                if (hasBorder(1)) GUIBookPlus.drawBorderQuarter(graphics, getQuarterBorderTexture(1), x + SEPARATION, y, 1, 1, 52, 52);
                if (hasBorder(2)) GUIBookPlus.drawBorderQuarter(graphics, getQuarterBorderTexture(2), x, y + SEPARATION, 1, 1, 52, 52);
                if (hasBorder(3)) GUIBookPlus.drawBorderQuarter(graphics, getQuarterBorderTexture(3), x + SEPARATION, y + SEPARATION, 1, 1, 52, 52);
//                if (border2) GUIBookPlus.drawBorderQuarter(graphics, getQuarterBorderTexture(1), x + SEPARATION, y, 1, 1, 52, 52);
//                if (border3) GUIBookPlus.drawBorderQuarter(graphics, getQuarterBorderTexture(2), x, y + SEPARATION, 1, 1, 52, 52);
//                if (border4) GUIBookPlus.drawBorderQuarter(graphics, getQuarterBorderTexture(3), x + SEPARATION, y + SEPARATION, 1, 1, 52, 52);
            } else if (hasBorder(0)) {
                GUIBookPlus.drawBorder(graphics, getBorderTexture(0), x, y, 1, 1, 106, 106);
            }
        } else if (hasBorder(0)) {
            GUIBookPlus.drawBorder(graphics, getBorderTexture(0), x, y, 1, 1, 106, 106);
        }
    }

    protected abstract boolean isQuadruple();

    protected ResourceLocation[] getImagesToDraw() {
        return null;
    }
}
