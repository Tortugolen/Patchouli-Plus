package com.tortugolen.patchouliplus.Book.Pages;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.ParentPage;
import net.minecraft.client.gui.GuiGraphics;

public class EmptyPlusPage extends ParentPage {
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        RenderSystem.enableBlend();
        graphics.setColor(1, 1, 1, 1);
        if (filler) GUIBookPlus.drawPageFiller(graphics, getFillerTexture(), 58 - 63, 78 - 74, 0, 0, 128, 128);
    }
}
