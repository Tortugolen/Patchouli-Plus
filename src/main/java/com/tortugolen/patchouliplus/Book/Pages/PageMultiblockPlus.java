package com.tortugolen.patchouliplus.Book.Pages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.PageWithBorder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.client.base.ClientTicker;
import vazkii.patchouli.client.base.PersistentData;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.LiquidBlockVertexConsumer;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.gui.button.GuiButtonBookEye;
import vazkii.patchouli.client.handler.MultiblockVisualizationHandler;
import vazkii.patchouli.common.multiblock.AbstractMultiblock;
import vazkii.patchouli.common.multiblock.MultiblockRegistry;
import vazkii.patchouli.common.multiblock.SerializedMultiblock;
import vazkii.patchouli.xplat.IClientXplatAbstractions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class PageMultiblockPlus extends PageWithBorder {
    private static final RandomSource RAND = RandomSource.createNewThreadLocalInstance();
    String name = "";
    @SerializedName("multiblock_id")
    ResourceLocation multiblockId;
    @SerializedName("multiblock")
    JsonObject serializedMultiblock;
    @SerializedName("enable_visualize")
    boolean showVisualizeButton = true;
    @SerializedName("visualize_button_position")
    protected VisualizeButtonPosition visualizeButtonPosition = VisualizeButtonPosition.BOTTOM_LEFT;
    private transient AbstractMultiblock multiblockObj;
    private final transient Set<Object> erroredTiles = Collections.newSetFromMap(new WeakHashMap<>());
    private static final Gson GSON = new Gson();
    private static final String PREMADE_KEY = "id";
    protected enum VisualizeButtonPosition {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        MIDDLE_LEFT,
        MIDDLE_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT,
    }

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);
        if (multiblockId != null) {
            IMultiblock mb = MultiblockRegistry.MULTIBLOCKS.get(multiblockId);
            if (mb instanceof AbstractMultiblock) multiblockObj = (AbstractMultiblock) mb;
        }
        if (multiblockObj == null && serializedMultiblock != null) multiblockObj = createMultiblock(serializedMultiblock);
        if (multiblockObj == null) throw new IllegalArgumentException("No multiblock located for " + multiblockId);
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);

        if (showVisualizeButton) {
            switch (visualizeButtonPosition) {
                case TOP_LEFT ->        addButton(new GuiButtonBookEye(parent, 12, getY() + 5,  this::handleButtonVisualize));
                case TOP_CENTER ->      addButton(new GuiButtonBookEye(parent, 53, getY() + 5,  this::handleButtonVisualize));
                case TOP_RIGHT ->       addButton(new GuiButtonBookEye(parent, 93, getY() + 5,  this::handleButtonVisualize));
                case MIDDLE_LEFT ->     addButton(new GuiButtonBookEye(parent, 12, getY() + 48, this::handleButtonVisualize));
                case MIDDLE_RIGHT ->    addButton(new GuiButtonBookEye(parent, 93, getY() + 48, this::handleButtonVisualize));
                case BOTTOM_LEFT ->     addButton(new GuiButtonBookEye(parent, 12, getY() + 90, this::handleButtonVisualize));
                case BOTTOM_CENTER ->   addButton(new GuiButtonBookEye(parent, 53, getY() + 90, this::handleButtonVisualize));
                case BOTTOM_RIGHT ->    addButton(new GuiButtonBookEye(parent, 93, getY() + 90, this::handleButtonVisualize));
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = 5;
        int y = getY();

        RenderSystem.enableBlend();
        graphics.setColor(1, 1, 1, 1);

        if (multiblockObj != null) renderMultiblock(graphics);

        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    protected boolean isQuadruple() {
        return false;
    }

    public void handleButtonVisualize(Button button) {
        ResourceLocation entryKey = parent.getEntry().getId();
        PersistentData.Bookmark bookmark = new PersistentData.Bookmark(entryKey, pageNum / 2);
        MultiblockVisualizationHandler.setMultiblock(multiblockObj, i18nText(name), bookmark, true);
        parent.addBookmarkButtons();
        if (!PersistentData.data.clickedVisualize) {
            PersistentData.data.clickedVisualize = true;
            PersistentData.save();
        }

    }

    private void renderMultiblock(GuiGraphics graphics) {
        multiblockObj.setWorld(mc.level);
        Vec3i size = multiblockObj.getSize();
        int sizeX = size.getX();
        int sizeY = size.getY();
        int sizeZ = size.getZ();
        float maxX = 90;
        float maxY = 90;
        float diag = (float)Math.sqrt((sizeX * sizeX + sizeZ * sizeZ));
        float scaleX = maxX / diag;
        float scaleY = maxY / (float)sizeY;
        float scale = -Math.min(scaleX, scaleY);
        int xPos = 58;
        int yPos = getMultiBlockY();
        graphics.pose().pushPose();
        graphics.pose().translate((float)xPos, (float)yPos, 100);
        graphics.pose().scale(scale, scale, scale);
        graphics.pose().translate(-((float)sizeX) / 2, -((float)sizeY) / 2, 0);
        Vector4f eye = new Vector4f(0, 0, -100, 1);
        Matrix4f rotMat = new Matrix4f();
        rotMat.identity();
        graphics.pose().mulPose(Axis.XP.rotationDegrees(-30));
        rotMat.rotation(Axis.XP.rotationDegrees(30));
        float offX = (float)(-sizeX) / 2;
        float offZ = (float)(-sizeZ) / 2 + 1;
        float time = (float)parent.ticksInBook * 0.5F;
        if (!Screen.hasShiftDown()) time += ClientTicker.partialTicks;

        graphics.pose().translate(-offX, 0, -offZ);
        graphics.pose().mulPose(Axis.YP.rotationDegrees(time));
        rotMat.rotation(Axis.YP.rotationDegrees(-time));
        graphics.pose().mulPose(Axis.YP.rotationDegrees(45));
        rotMat.rotation(Axis.YP.rotationDegrees(-45));
        graphics.pose().translate(offX, 0, offZ);
        eye.mul(rotMat);
        renderElements(graphics, multiblockObj, BlockPos.betweenClosed(BlockPos.ZERO, new BlockPos(sizeX - 1, sizeY - 1, sizeZ - 1)));
        graphics.pose().popPose();
    }

    private void renderElements(GuiGraphics graphics, AbstractMultiblock mb, Iterable<? extends BlockPos> blocks) {
        graphics.pose().pushPose();
        graphics.setColor(1, 1, 1, 1);
        graphics.pose().translate(0, 0, -1);
        MultiBufferSource.BufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();
        doWorldRenderPass(graphics, mb, blocks, buffers);
        doTileEntityRenderPass(graphics, mb, blocks, buffers);
        buffers.endBatch();
        graphics.pose().popPose();
    }

    private void doWorldRenderPass(GuiGraphics graphics, AbstractMultiblock mb, Iterable<? extends BlockPos> blocks, MultiBufferSource.@NotNull BufferSource buffers) {
        for(BlockPos pos : blocks) {
            BlockState bs = mb.getBlockState(pos);
            graphics.pose().pushPose();
            graphics.pose().translate((float)pos.getX(), (float)pos.getY(), (float)pos.getZ());
            FluidState fluidState = bs.getFluidState();
            BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
            if (!fluidState.isEmpty()) {
                RenderType layer = ItemBlockRenderTypes.getRenderLayer(fluidState);
                VertexConsumer buffer = buffers.getBuffer(layer);
                blockRenderer.renderLiquid(pos, mb, new LiquidBlockVertexConsumer(buffer, graphics.pose(), pos), bs, fluidState);
            }

            IClientXplatAbstractions.INSTANCE.renderForMultiblock(bs, pos, mb, graphics.pose(), buffers, RAND);
            graphics.pose().popPose();
        }

    }

    private void doTileEntityRenderPass(GuiGraphics graphics, AbstractMultiblock mb, Iterable<? extends BlockPos> blocks, MultiBufferSource buffers) {
        for(BlockPos pos : blocks) {
            BlockEntity te = mb.getBlockEntity(pos);
            if (te != null && !erroredTiles.contains(te)) {
                assert mc.level != null;
                te.setLevel(mc.level);
                te.setBlockState(mb.getBlockState(pos));
                graphics.pose().pushPose();
                graphics.pose().translate((float)pos.getX(), (float)pos.getY(), (float)pos.getZ());

                try {
                    BlockEntityRenderer<BlockEntity> renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(te);
                    if (renderer != null) renderer.render(te, ClientTicker.partialTicks, graphics.pose(), buffers, 15728880, OverlayTexture.NO_OVERLAY);
                } catch (Exception e) {
                    erroredTiles.add(te);
                    PatchouliPlus.LOGGER.error("An exception occured rendering tile entity", e);
                } finally {
                    graphics.pose().popPose();
                }
            }
        }
    }

    private static AbstractMultiblock createMultiblock(JsonObject data) {
        String premade = readPremadeId(data);

        if (premade != null) return loadPremade(premade).toMultiblock();

        return GSON.fromJson(data, SerializedMultiblock.class).toMultiblock();
    }

    private static String readPremadeId(JsonObject data) {
        if (!data.has(PREMADE_KEY)) return null;
        var element = data.get(PREMADE_KEY);
        if (element.isJsonNull() || !element.isJsonPrimitive()) return null;

        String id = element.getAsString().trim();
        return id.isEmpty() ? null : id;
    }

    private static SerializedMultiblock loadPremade(String id) {
        ResourceLocation key = ResourceLocation.tryParse(id.contains(":") ? id : PatchouliPlus.MOD_ID + ":" + id);
        if (key == null) throw new IllegalArgumentException("Invalid id multiblock id: " + id);

        ResourceLocation file = new ResourceLocation(key.getNamespace(), "patchouli_books/multiblocks/" + key.getPath() + ".json");
        var resource = Minecraft.getInstance().getResourceManager().getResource(file);
        if (resource.isEmpty()) throw new IllegalArgumentException("Id multiblock file not found: " + file);

        try (BufferedReader reader = resource.get().openAsReader()) {
            SerializedMultiblock loaded = GSON.fromJson(reader, SerializedMultiblock.class);
            if (loaded == null) throw new IllegalArgumentException("Id multiblock file is empty: " + file);
            return loaded;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read id multiblock file: " + file, e);
        }
    }

    protected int getMultiBlockY() {
        if (hasValidTitle() && hasSeparator()) return 75;
        else if (!hasValidTitle() && !hasSeparator()) return 60;
        else return 75 - 19;
    }
}
