package com.tortugolen.patchouliplus.book.page;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.BookPage;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

public class PageEmptyPlus extends BookPage {

    private transient ResourceLocation resolvedFiller;
    @SerializedName("filler")
    boolean filler = true;
    @SerializedName("filler_texture")
    protected String fillerTexture = null;

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        
        resolvedFiller = null;
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        RenderSystem.enableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        if (filler) {
            GUIBookPlus.drawCustomPageFiller(graphics, getFillerTexture(), 58 - 63, 78 - 74, 0, 0, 128, 128);
        }
    }

    private ResourceLocation getFillerTexture() {
        if (resolvedFiller != null) return resolvedFiller;

        String fillersFolder = "textures/gui/fillers/";
        ResourceLocation defaultFiller = new ResourceLocation(PatchouliPlus.MOD_ID, fillersFolder + "cube_page_filler.png");
        ResourceLocation displayFiller = defaultFiller;

        var resources = Minecraft.getInstance().getResourceManager();

        if (fillerTexture != null && !fillerTexture.isEmpty()) {
            ResourceLocation named = ResourceLocation.tryParse(PatchouliPlus.MOD_ID + ":" + fillersFolder + fillerTexture + "_page_filler.png");
            ResourceLocation full = ResourceLocation.tryParse(fillerTexture);

            if (named != null && resources.getResource(named).isPresent()) displayFiller = named;
            else if (full != null && resources.getResource(full).isPresent()) displayFiller = full;
        }

        return resolvedFiller = displayFiller;
    }
}
