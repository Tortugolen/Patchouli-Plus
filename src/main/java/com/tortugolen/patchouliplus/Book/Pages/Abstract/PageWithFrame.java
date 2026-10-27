package com.tortugolen.patchouliplus.Book.Pages.Abstract;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.PageTextPlus;
import net.minecraft.client.gui.GuiGraphics;

public abstract class PageWithFrame extends PageTextPlus {
    protected static final int SEPARATION = 54;

    protected boolean quadruple = false;

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = START_X;
        int y = getY();

        RenderSystem.enableBlend();
        graphics.setColor(1, 1, 1, 1);

        drawFrames(graphics, x, y);

        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    public int getTextY() {
        return super.getTextY() + 106 + SLOT_SEPARATION_Y;
    }

    protected void drawFrames(GuiGraphics graphics, int x, int y) {
        if (isQuadruple()) {
            if (quadruple) {
                if (hasFrame(0) && hasFeature(0)) GUIBookPlus.drawBorderQuarter(graphics, getQuarterFrameTexture(0), x, y, 1, 1, 52, 52);
                if (hasFrame(1) && hasFeature(1)) GUIBookPlus.drawBorderQuarter(graphics, getQuarterFrameTexture(1), x + SEPARATION, y, 1, 1, 52, 52);
                if (hasFrame(2) && hasFeature(2)) GUIBookPlus.drawBorderQuarter(graphics, getQuarterFrameTexture(2), x, y + SEPARATION, 1, 1, 52, 52);
                if (hasFrame(3) && hasFeature(3)) GUIBookPlus.drawBorderQuarter(graphics, getQuarterFrameTexture(3), x + SEPARATION, y + SEPARATION, 1, 1, 52, 52);
            } else if (hasFrame()) {
                GUIBookPlus.drawBorder(graphics, getFrameTexture(0), x, y, 1, 1, 106, 106);
            }
        } else if (hasFrame()) {
            GUIBookPlus.drawBorder(graphics, getFrameTexture(0), x, y, 1, 1, 106, 106);
        }
    }

    protected abstract boolean isQuadruple();

    protected boolean hasFeature(int slot) {
        return false;
    }
}
