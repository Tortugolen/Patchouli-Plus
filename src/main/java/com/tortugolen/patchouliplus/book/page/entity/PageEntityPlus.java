package com.tortugolen.patchouliplus.book.page.entity;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.base.EEntityPose;
import com.tortugolen.patchouliplus.book.gui.GUIBookPlus;
import com.tortugolen.patchouliplus.book.page.PageTextPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.base.ClientTicker;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.common.util.EntityUtil;

import java.util.function.Function;

public class PageEntityPlus extends PageTextPlus {
    @SerializedName("entity")
    public String entityId;
    float scale = 1.0F;
    @SerializedName("offset")
    float extraOffset = 0.0F;
    boolean rotate = true;
    @SerializedName("default_rotation")
    transient boolean errored;
    transient Entity entity;
    transient Function<Level, Entity> creator;
    transient float renderScale;
    transient float offset;

    protected transient ResourceLocation resolvedBorder;

    @SerializedName("border")
    protected boolean border = true;

    @SerializedName("border_type")
    protected String borderTexture = null;

    @SerializedName("pose")
    protected EEntityPose pose = EEntityPose.DEFAULT;

    @SerializedName("mouse_rotation")
    boolean mouseRotation = false;

    float mouseSensitivity = 1.0F;

    protected transient float userRotation = 0.0F;
    protected transient float lastMouseX;
    protected transient boolean dragging;
    protected transient boolean wasDown;

    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);
        creator = EntityUtil.loadEntity(entityId);
    }

    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        loadEntity(parent.getMinecraft().level);
        resolvedBorder = getBorderTexture(borderTexture);
    }

    @Override
    public int getTextHeight() {
        if (hasValidTitle() && separator) return 132;
        else if (hasValidTitle() && !separator) return 120;
        else if (!hasValidTitle() && separator) return 120;
        else return 0;
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = 5;
        int y = getY();
        
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        if (border) {
            drawBorders(graphics, x, y);
        }

        if (entity != null && !hasValidTitle()) {
            parent.drawCenteredStringNoShadow(graphics, entity.getName().getVisualOrderText(), 58, 0, book.headerColor);
        }

        if (errored) {
            graphics.drawString(fontRenderer, I18n.get("patchouli.gui.lexicon.loading_error", new Object[0]), 58, 60, 16711680, true);
        }

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

    public static void renderEntity(GuiGraphics graphics, Entity entity, float x, float y, boolean rotate, float rotation, float renderScale, float offset, EEntityPose pose) {
        PoseStack ms = graphics.pose();
        ms.pushPose();
        ms.translate(x, y, 50.0F);
        ms.scale(renderScale, renderScale, renderScale);
        ms.translate(0.0F, offset, 0.0F);
        ms.mulPose(Axis.XP.rotationDegrees(pose.x));
        ms.mulPose(Axis.ZP.rotationDegrees(pose.z));
        if (rotate) {
            ms.mulPose(Axis.YP.rotationDegrees(rotation));
        } else {
            ms.mulPose(Axis.YP.rotationDegrees(pose.y));
        }
        EntityRenderDispatcher erd = Minecraft.getInstance().getEntityRenderDispatcher();
        MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();
        erd.setRenderShadow(false);
        erd.render(entity, (double)0.0F, (double)0.0F, (double)0.0F, 0.0F, 1.0F, ms, immediate, 15728880);
        erd.setRenderShadow(true);
        immediate.endBatch();
        ms.popPose();
    }

    private void loadEntity(Level world) {
        if (!errored && (entity == null || !entity.isAlive() || entity.level() != world)) {
            try {
                entity = (Entity)creator.apply(world);
                float width = entity.getBbWidth();
                float height = entity.getBbHeight();
                float entitySize = Math.max(1.0F, Math.max(width, height));
                renderScale = 100.0F / entitySize * 0.8F * scale;
                offset = Math.max(height, entitySize) * 0.5F + extraOffset;
            } catch (Exception e) {
                errored = true;
                PatchouliAPI.LOGGER.error("Failed to load entity", e);
            }
        }
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
            userRotation += (mouseX - lastMouseX) * mouseSensitivity * 2.0F;
            lastMouseX = mouseX;
        }
        wasDown = down;
    }

    protected void drawBorders(GuiGraphics graphics, int x, int y) {
        GUIBookPlus.drawCustomBorder(graphics, resolvedBorder, x, y, 1, 1, 106, 106);
    }

    protected void drawEntities(GuiGraphics graphics, float rotation) {
        renderEntity(graphics, entity, 58.0F, 60.0F, rotate, rotation, renderScale, offset, pose);
    }

    protected ResourceLocation getBorderTexture(String texture) {
        String folder = "textures/gui/borders/";

        if (texture != null && !texture.isEmpty()) {
            var resources = Minecraft.getInstance().getResourceManager();
            ResourceLocation named = ResourceLocation.tryParse(PatchouliPlus.MOD_ID + ":" + folder + texture + "_border.png");
            ResourceLocation full = ResourceLocation.tryParse(texture);

            if (named != null && resources.getResource(named).isPresent()) return named;
            if (full != null && resources.getResource(full).isPresent()) return full;
        }
        return new ResourceLocation(PatchouliPlus.MOD_ID, folder + "simple_border.png");
    }
}
