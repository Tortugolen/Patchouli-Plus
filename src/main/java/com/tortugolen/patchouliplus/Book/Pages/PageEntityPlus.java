package com.tortugolen.patchouliplus.Book.Pages;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.Base.EEntityPose;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.PageWithBorder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;
import vazkii.patchouli.client.base.ClientTicker;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.common.util.EntityUtil;

import java.util.function.Function;

public class PageEntityPlus extends PageWithBorder {
    @SerializedName("entity")
    public String entityId;
    float scale = 1;
    @SerializedName("offset")
    float extraOffset = 0;
    boolean rotate = true;
    @SerializedName("default_rotation")
    transient boolean errored;
    transient Entity entity;
    transient Function<Level, Entity> creator;
    transient float renderScale;
    transient float offset;

    @SerializedName("pose")
    protected EEntityPose pose = EEntityPose.DEFAULT;
    @SerializedName("mouse_rotation")
    boolean mouseRotation = false;
    float mouseSensitivity = 1;

    protected transient float userRotation = 0;
    protected transient float lastMouseX;
    protected transient boolean dragging;
    protected transient boolean wasDown;

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);
        creator = EntityUtil.loadEntity(entityId);
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        loadEntity(parent.getMinecraft().level);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = 5;
        int y = getY();
        
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);

        if (entity != null && !hasValidTitle()) parent.drawCenteredStringNoShadow(graphics, entity.getName().getVisualOrderText(), 58, 0, book.headerColor);

        if (errored) graphics.drawString(fontRenderer, I18n.get("patchouli.gui.lexicon.loading_error"), 58, 60, 16711680, true);

        if (entity != null) {
            float rotation;
            rotation = rotate ? ClientTicker.total : pose.y;

            /*
            if (mouseRotation) {
                updateMouseRotation(mouseX, mouseY, y);
                rotation = pose.y + userRotation;
            } else {
            */

            drawEntities(graphics, rotation);
        }
        
        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    protected boolean isQuadruple() {
        return false;
    }

    private void loadEntity(Level world) {
        if (!errored && (entity == null || !entity.isAlive() || entity.level() != world)) {
            try {
                entity = creator.apply(world);
                float width = entity.getBbWidth();
                float height = entity.getBbHeight();
                float entitySize = Math.max(1, Math.max(width, height));
                renderScale = 100 / entitySize * 0.8F * scale;
                offset = Math.max(height, entitySize) * 0.5F + getEntityY() + extraOffset;
            } catch (Exception e) {
                errored = true;
                PatchouliPlus.LOGGER.error("Failed to load entity", e);
            }
        }
    }

    protected float getEntityY() {
        if (hasValidTitle() && hasSeparator()) return ((float) 58 / 188);
        else if (!hasValidTitle() && !hasSeparator()) return 58;
        else return (float) (58 - 12) / 188;
    }

    protected void drawEntities(GuiGraphics graphics, float rotation) {
        renderEntity(graphics, entity, 58, 60, rotate, rotation, renderScale, offset, pose);
    }

    public static void renderEntity(GuiGraphics graphics, Entity entity, float x, float y, boolean rotate, float rotation, float renderScale, float offset, EEntityPose pose) {
        PoseStack ms = graphics.pose();
        ms.pushPose();
        ms.translate(x, y, 50);
        ms.scale(renderScale, renderScale, renderScale);
        ms.translate(0, offset, 0);
        ms.mulPose(Axis.XP.rotationDegrees(pose.x));
        ms.mulPose(Axis.ZP.rotationDegrees(pose.z));
        if (rotate) ms.mulPose(Axis.YP.rotationDegrees(rotation));
        else ms.mulPose(Axis.YP.rotationDegrees(pose.y));
        EntityRenderDispatcher erd = Minecraft.getInstance().getEntityRenderDispatcher();
        MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();
        erd.setRenderShadow(false);
        erd.render(entity, 0, 0, 0, 0, 1, ms, immediate, 15728880);
        erd.setRenderShadow(true);
        immediate.endBatch();
        ms.popPose();
    }

    protected void updateMouseRotation(int mouseX, int mouseY, int areaY) {
        long window = Minecraft.getInstance().getWindow().getWindow();
        boolean down = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        boolean inside = mouseX >= 5 && mouseX <= 111 && mouseY >= areaY && mouseY <= areaY + 106;

        if (down && !wasDown && inside) {
            dragging = true;
            lastMouseX = mouseX;
        }
        if (!down) dragging = false;
        if (dragging) {
            userRotation += (mouseX - lastMouseX) * mouseSensitivity * 2;
            lastMouseX = mouseX;
        }
        wasDown = down;
    }
}
