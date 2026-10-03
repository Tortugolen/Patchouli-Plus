package com.tortugolen.patchouliplus.book.page;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.gui.button.GuiButtonBookArrowSmall;

public class PageImagePlus extends PageTextPlus {
    ResourceLocation[] images;
    transient int index;
    private transient ResourceLocation resolvedBorder;

    @SerializedName("border")
    protected boolean border = true;

    @SerializedName("border_type")
    protected String borderTexture = null;

    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);

        resolvedBorder = null;

        int x = 90;
        int y = getY() + 100;

        addButton(new GuiButtonBookArrowSmall(parent, x, y, true, () -> index > 0, this::handleButtonArrow));
        addButton(new GuiButtonBookArrowSmall(parent, x + 10, y, false, () -> index < images.length - 1, this::handleButtonArrow));
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = 5;
        int y = getY();

        RenderSystem.enableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.pose().scale(0.5F, 0.5F, 0.5F);
        graphics.blit(images[index], x * 2 + 6, y * 2 + 6, 0, 0, 200, 200);
        graphics.pose().scale(2.0F, 2.0F, 2.0F);

        if (border) {
            GUIBookPlus.drawCustomBorder(graphics, getBorderTexture(), x, y, 1, 1, 106, 106);
        }

        if (images.length > 1 && border) {
            int xs = x + 83;
            int ys = y + 92;

            graphics.fill(xs, ys, xs + 20, ys + 11, 1140850688);
            graphics.fill(xs - 1, ys - 1, xs + 20, ys + 11, 1140850688);
        }

        super.render(graphics, mouseX, mouseY, pticks);
    }

    public void handleButtonArrow(Button button) {
        boolean left = ((GuiButtonBookArrowSmall)button).left;
        if (left) {
            --index;
        } else {
            ++index;
        }
    }

    @Override
    public int getTextHeight() {
        if (hasValidTitle() && separator) return 132;
        else if (hasValidTitle() && !separator) return 120;
        else if (!hasValidTitle() && separator) return 120;
        else return 0;
    }

    private ResourceLocation getBorderTexture() {
        if (resolvedBorder != null) return resolvedBorder;

        String bordersFolder = "textures/gui/borders/";
        ResourceLocation defaultBorder = new ResourceLocation(PatchouliPlus.MOD_ID, bordersFolder + "simple_border.png");
        ResourceLocation displayBorder = defaultBorder;

        var resources = Minecraft.getInstance().getResourceManager();

        if (borderTexture != null && !borderTexture.isEmpty()) {
            ResourceLocation named = ResourceLocation.tryParse(PatchouliPlus.MOD_ID + ":" + bordersFolder + borderTexture + "_border.png");
            ResourceLocation full = ResourceLocation.tryParse(borderTexture);

            if (named != null && resources.getResource(named).isPresent()) displayBorder = named;
            else if (full != null && resources.getResource(full).isPresent()) displayBorder = full;
        }

        return resolvedBorder = displayBorder;
    }
}
