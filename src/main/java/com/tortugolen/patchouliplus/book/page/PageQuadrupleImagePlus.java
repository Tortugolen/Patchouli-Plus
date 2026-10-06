package com.tortugolen.patchouliplus.book.page;

import com.google.gson.annotations.SerializedName;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

public class PageQuadrupleImagePlus extends PageImagePlus {
    private static final int SEPARATION = 58;

    private transient ResourceLocation resolvedBorder2;
    private transient ResourceLocation resolvedBorder3;
    private transient ResourceLocation resolvedBorder4;

    @SerializedName("border_type2")
    protected String borderTexture2 = null;
    @SerializedName("border_type3")
    protected String borderTexture3 = null;
    @SerializedName("border_type4")
    protected String borderTexture4 = null;

    @SerializedName("quadruple")
    protected boolean quadruple = false;

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);

        resolvedBorder = getQuarterBorderTexture(borderTexture);
        resolvedBorder2 = borderTexture2 != null ? getQuarterBorderTexture(borderTexture2) : resolvedBorder;
        resolvedBorder3 = borderTexture3 != null ? getQuarterBorderTexture(borderTexture3) : resolvedBorder;
        resolvedBorder4 = borderTexture4 != null ? getQuarterBorderTexture(borderTexture4) : resolvedBorder;
    }

    @Override
    public void handleButtonArrow(Button button) {
    }

    @Override
    protected void drawImages(GuiGraphics graphics, int x, int y) {
        graphics.blit(images[index], x * 2 + 6, y * 2 + 6, 0, 0, 200, 200);
        graphics.pose().scale(2.0F, 2.0F, 2.0F);
    }

    @Override
    protected void drawBorder(GuiGraphics graphics, int x, int y) {
        GUIBookPlus.drawCustomBorder(graphics, resolvedBorder, x, y, 1, 1, 52, 52);
        GUIBookPlus.drawCustomBorder(graphics, resolvedBorder2, x + SEPARATION, y, 1, 1, 52, 52);
        GUIBookPlus.drawCustomBorder(graphics, resolvedBorder3, x, y + SEPARATION, 1, 1, 52, 52);
        GUIBookPlus.drawCustomBorder(graphics, resolvedBorder4, x + SEPARATION, y + SEPARATION, 1, 1, 52, 52);
    }

    @Override
    protected void drawButtonArrow() {
    }

    @Override
    protected void drawButtonArrowBackground(GuiGraphics graphics, int x, int y) {
    }

    private ResourceLocation getQuarterBorderTexture(String texture) {
        String folder = "textures/gui/borders/";

        if (texture != null && !texture.isEmpty()) {
            var resources = Minecraft.getInstance().getResourceManager();
            ResourceLocation named = ResourceLocation.tryParse(PatchouliPlus.MOD_ID + ":" + folder + texture + "_border_quarter.png");
            ResourceLocation full = ResourceLocation.tryParse(texture);

            if (named != null && resources.getResource(named).isPresent()) return named;
            if (full != null && resources.getResource(full).isPresent()) return full;
        }
        return new ResourceLocation(PatchouliPlus.MOD_ID, folder + "simple_border_quarter.png");
    }
}
