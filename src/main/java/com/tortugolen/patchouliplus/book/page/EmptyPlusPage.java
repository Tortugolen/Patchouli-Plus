package com.tortugolen.patchouliplus.book.page;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import com.tortugolen.patchouliplus.book.page.Abstract.ParentPage;
import net.minecraft.client.gui.GuiGraphics;

public class EmptyPlusPage extends ParentPage {
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        RenderSystem.enableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (filler) GUIBookPlus.drawPageFiller(graphics, getFillerTexture(0), 58 - 63, 78 - 74, 0, 0, 128, 128);
    }
}
