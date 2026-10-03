package com.tortugolen.patchouliplus.book.page.entity;

import com.google.gson.annotations.SerializedName;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.base.EEntityPose;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

public class PageEntityProjectionPlus extends PageEntityPlus {
    private static final int X = 29;
    private static final int Y = 30;
    private static final int SEPARATION = 58;
    private static final float SCALE_FACTOR = 0.325F;

    private transient ResourceLocation resolvedBorder2;
    private transient ResourceLocation resolvedBorder3;
    private transient ResourceLocation resolvedBorder4;

    @SerializedName("border_type2")
    protected String borderTexture2 = null;

    @SerializedName("border_type3")
    protected String borderTexture3 = null;

    @SerializedName("border_type4")
    protected String borderTexture4 = null;

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);

        resolvedBorder = getQuarterBorderTexture(borderTexture);
        resolvedBorder2 = borderTexture2 != null ? getQuarterBorderTexture(borderTexture2) : resolvedBorder;
        resolvedBorder3 = borderTexture3 != null ? getQuarterBorderTexture(borderTexture3) : resolvedBorder;
        resolvedBorder4 = borderTexture4 != null ? getQuarterBorderTexture(borderTexture4) : resolvedBorder;
    }

    @Override
    protected void drawBorders(GuiGraphics graphics, int x, int y) {
        GUIBookPlus.drawCustomBorder(graphics, resolvedBorder, x, y, 1, 1, 52, 52);
        GUIBookPlus.drawCustomBorder(graphics, resolvedBorder2, x + SEPARATION, y, 1, 1, 52, 52);
        GUIBookPlus.drawCustomBorder(graphics, resolvedBorder3, x, y + SEPARATION, 1, 1, 52, 52);
        GUIBookPlus.drawCustomBorder(graphics, resolvedBorder4, x + SEPARATION, y + SEPARATION, 1, 1, 52, 52);
    }

    @Override
    protected void drawEntities(GuiGraphics graphics, float rotation) {
//        renderEntity(graphics, entity, X, Y, EEntityPose.FRONT_VIEW.x, EEntityPose.FRONT_VIEW.z, rotation, renderScale * SCALE_FACTOR, offset);
//        renderEntity(graphics, entity, X + SEPARATION, Y, EEntityPose.SIDE_VIEW.x, EEntityPose.SIDE_VIEW.z, rotation, renderScale * SCALE_FACTOR, offset);
//        renderEntity(graphics, entity, X, Y + SEPARATION, EEntityPose.TOP_VIEW.x, EEntityPose.TOP_VIEW.z, rotation, renderScale * SCALE_FACTOR, offset);
//        renderEntity(graphics, entity, X + SEPARATION, Y + SEPARATION, rotation, renderScale * SCALE_FACTOR, offset, EEntityPose.DISPLAY);
//        renderEntity(graphics, entity, X, Y, rotate, rotation, renderScale * SCALE_FACTOR, offset, EEntityPose.FRONT_VIEW);
//        renderEntity(graphics, entity, X + SEPARATION, Y, rotate, rotation, renderScale * SCALE_FACTOR, offset, EEntityPose.SIDE_VIEW);
//        renderEntity(graphics, entity, X, Y + SEPARATION, rotate, rotation, renderScale * SCALE_FACTOR, offset, EEntityPose.TOP_VIEW);
//        renderEntity(graphics, entity, X + SEPARATION, Y + SEPARATION, rotate, rotation, renderScale * SCALE_FACTOR, offset, EEntityPose.DISPLAY);
        renderEntity(graphics, entity, 32, getY() + 27, rotate, rotation, renderScale * SCALE_FACTOR, offset, EEntityPose.FRONT_VIEW);
        renderEntity(graphics, entity, 90, getY() + 27, rotate, rotation, renderScale * SCALE_FACTOR, offset, EEntityPose.SIDE_VIEW);
        renderEntity(graphics, entity, 32, getY() + 85, rotate, rotation, renderScale * SCALE_FACTOR, offset, EEntityPose.TOP_VIEW);
        renderEntity(graphics, entity, 90, getY() + 85, rotate, rotation, renderScale * SCALE_FACTOR, offset, EEntityPose.DISPLAY);
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
