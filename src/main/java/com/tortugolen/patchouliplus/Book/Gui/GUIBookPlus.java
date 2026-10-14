package com.tortugolen.patchouliplus.Book.Gui;

import com.tortugolen.patchouliplus.Xplat.IBookPlus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.common.book.Book;

public abstract class GUIBookPlus extends GuiBook {
    public static final int RECIPES_TEXTURE_PLUS_WIDTH = 128;
    public static final int RECIPES_TEXTURE_PLUS_HEIGHT = 512;
    public static final int SPOTLIGHT_TEXTURE_PLUS_WIDTH = 256;
    public static final int SPOTLIGHT_TEXTURE_PLUS_HEIGHT = 256;
    public static final int FILLER_TEXTURE_PLUS_WIDTH = 128;
    public static final int FILLER_TEXTURE_PLUS_HEIGHT = 128;
    public static final int SEPARATOR_TEXTURE_PLUS_WIDTH = 128;
    public static final int SEPARATOR_TEXTURE_PLUS_HEIGHT = 16;
    public static final int BORDER_TEXTURE_PLUS_WIDTH = 128;
    public static final int BORDER_TEXTURE_PLUS_HEIGHT = 128;

    public GUIBookPlus(Book book, Component title) {
        super(book, title);
    }

    public static void drawRecipesTexturePlus(GuiGraphics graphics, Book book, int x, int y, int v, int w, int h) {
        graphics.blit(((IBookPlus) book).getRecipesTexturePlus(), x, y, 1, v, w, h, RECIPES_TEXTURE_PLUS_WIDTH, RECIPES_TEXTURE_PLUS_HEIGHT);
    }

    public static void drawSpotlightTexturePlus(GuiGraphics graphics, Book book, int x, int y, int v, int w, int h) {
        graphics.blit(((IBookPlus) book).getSpotlightTexturePlus(), x, y, 1, v, w, h, SPOTLIGHT_TEXTURE_PLUS_WIDTH, SPOTLIGHT_TEXTURE_PLUS_HEIGHT);
    }

    public static void drawPageFiller(GuiGraphics graphics, ResourceLocation image, int x, int y, int u, int v, int w, int h) {
        graphics.blit(image, x, y, (float)u, (float)v, w, h, FILLER_TEXTURE_PLUS_WIDTH, FILLER_TEXTURE_PLUS_HEIGHT);
    }

    public static void drawSeparator(GuiGraphics graphics, ResourceLocation separator, int x, int y, int u, int v) {
        graphics.blit(separator, x, y, 1, 1, u, v, SEPARATOR_TEXTURE_PLUS_WIDTH, SEPARATOR_TEXTURE_PLUS_HEIGHT);
    }

    public static void drawBorder(GuiGraphics graphics, ResourceLocation border, int x, int y, int u, int v, int w, int h) {
        graphics.blit(border, x, y, (float)u, (float)v, w, h, BORDER_TEXTURE_PLUS_WIDTH, BORDER_TEXTURE_PLUS_HEIGHT);
    }

    public static void drawBorderQuarter(GuiGraphics graphics, ResourceLocation border, int x, int y, int u, int v, int w, int h) {
        graphics.blit(border, x, y, (float)u, (float)v, w, h, BORDER_TEXTURE_PLUS_WIDTH, BORDER_TEXTURE_PLUS_HEIGHT);
    }
}
