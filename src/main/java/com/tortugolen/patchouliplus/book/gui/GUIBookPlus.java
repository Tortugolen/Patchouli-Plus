package com.tortugolen.patchouliplus.book.gui;

import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.xplat.IBookPlus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.common.book.Book;

public abstract class GUIBookPlus extends GuiBook {
    public GUIBookPlus(Book book, Component title) {
        super(book, title);
    }

    public static void drawFromTexturePlus(GuiGraphics graphics, Book book, int x, int y, int w, int h) {
        graphics.blit(((IBookPlus) book).getCraftingTexturePlus(), x, y, 1, 1, w, h, 128, 512);
    }

    public static void drawDefaultSeparator(GuiGraphics graphics, int x, int y) {
        graphics.blit(new ResourceLocation(PatchouliPlus.MOD_ID, "textures/gui/separators/simple_separator.png"), x, y, 1, 1, 110, 3, 128, 16);
    }

    public static void drawCustomSeparator(GuiGraphics graphics, ResourceLocation separator, int x, int y, int u, int v) {
        graphics.blit(separator, x, y, 1, 1, u, v, 128, 16);
    }

    public static void drawDefaultBorder(GuiGraphics graphics, int x, int y) {
        graphics.blit(new ResourceLocation(PatchouliPlus.MOD_ID, "textures/gui/borders/simple_border.png"), x, y, 1, 1, 106, 106, 128, 128);
    }

    public static void drawCustomBorder(GuiGraphics graphics, ResourceLocation border, int x, int y, int u, int v, int w, int h) {
        graphics.blit(border, x, y, (float)u, (float)v, w, h, 128, 128);
    }

    public static void drawDefaultPageFiller(GuiGraphics graphics) {
        graphics.blit(new ResourceLocation(PatchouliPlus.MOD_ID, "textures/gui/borders/cube_page_filler.png"), 58 - 64, 78 - 74, 0, 0, 128, 128, 128, 128);
    }

    public static void drawCustomPageFiller(GuiGraphics graphics, ResourceLocation image, int x, int y, int u, int v, int w, int h) {
        graphics.blit(image, x, y, (float)u, (float)v, w, h, 128, 128);
    }
}
