package com.tortugolen.patchouliplus.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public enum ERecipeLayout {
    SIMPLE_RECIPE(1, 89, 96, 24, 128, 512);

    public final int u;
    public final int v;
    public final int width;
    public final int height;
    public final int textureWidth;
    public final int textureHeight;

    ERecipeLayout(int u, int v, int width, int height, int textureWidth, int textureHeight) {
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void draw(GuiGraphics graphics, ResourceLocation texture, int x, int y) {
        graphics.blit(texture, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public void draw(GuiGraphics graphics, ResourceLocation texture, int x, int y, int v, int pWidth, int pHeight) {
        graphics.blit(texture, x, y, u, v, pWidth, pHeight, textureWidth, textureHeight);
    }
}