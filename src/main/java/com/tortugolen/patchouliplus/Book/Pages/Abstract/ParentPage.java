package com.tortugolen.patchouliplus.Book.Pages.Abstract;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.Xplat.IBookPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.BookPage;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentPage extends BookPage {
    protected static final int START_X = 5;
    protected static final int SLOT_SEPARATION_Y = 4;

    protected String title;
    @SerializedName("title_position")
    protected TitlePosition titlePosition = null;
    protected enum TitlePosition {
        @SerializedName("top")
        TOP,
        @SerializedName("high")
        HIGH,
    }
    protected boolean filler = true;
    protected JsonElement separator;
    protected JsonElement frame;
    @SerializedName("fillers")
    protected String fillerIds = null;
    @SerializedName("separators")
    protected JsonElement separatorIds = null;
    @SerializedName("frames")
    protected JsonElement frameIds = null;

    protected boolean hasBooleanFeature(JsonElement jsonElement, boolean defaultValue) {
        if (jsonElement == null) return defaultValue;

        if (jsonElement.isJsonPrimitive()) return jsonElement.getAsBoolean();

        if (jsonElement.isJsonArray()) {
            JsonArray array = jsonElement.getAsJsonArray();
            if (0 >= array.size()) return defaultValue;
            JsonElement item = array.get(0);
            return item.isJsonPrimitive() && item.getAsBoolean();
        }
        return defaultValue;
    }

    protected boolean hasSeparator() {
        if (separator == null) return false;

        if (separator.isJsonPrimitive()) return separator.getAsBoolean();

        if (separator.isJsonArray()) {
            JsonArray array = separator.getAsJsonArray();
            if (0 >= array.size()) return false;
            JsonElement item = array.get(0);
            return item.isJsonPrimitive() && item.getAsBoolean();
        }
        return false;
    }

    protected boolean hasSeparator(int slot) {
        if (separator == null) return false;

        if (separator.isJsonPrimitive()) return separator.getAsBoolean();

        if (separator.isJsonArray()) {
            JsonArray array = separator.getAsJsonArray();
            if (slot >= array.size()) return false;
            JsonElement item = array.get(slot);
            return item.isJsonPrimitive() && item.getAsBoolean();
        }
        return false;
    }

    protected boolean hasFrame() {
        if (frame == null) return true;

        if (frame.isJsonPrimitive()) return frame.getAsBoolean();

        if (frame.isJsonArray()) {
            JsonArray array = frame.getAsJsonArray();
            if (0 >= array.size()) return true;
            JsonElement item = array.get(0);
            return item.isJsonPrimitive() && item.getAsBoolean();
        }
        return true;
    }

    protected boolean hasFrame(int slot) {
        if (frame == null) return true;

        if (frame.isJsonPrimitive()) return frame.getAsBoolean();

        if (frame.isJsonArray()) {
            JsonArray array = frame.getAsJsonArray();
            if (slot >= array.size()) return true;
            JsonElement item = array.get(slot);
            return item.isJsonPrimitive() && item.getAsBoolean();
        }
        return true;
    }

    protected ResourceLocation getCraftingTexturePlus() {
        return ((IBookPlus) book).getRecipesTexturePlus();
    }

    protected ResourceLocation getFillerTexture() {
        return getTextureResourceLocation(getIdAt(fillerIds, 0), "textures/gui/fillers/", "_page_filler.png", "cube_page_filler.png");
    }

    protected ResourceLocation getSeparatorTexture() {
        return getTextureResourceLocation(getIdAt(separatorIds, 0), "textures/gui/separators/", "_separator.png", "simple_separator.png");
    }

    protected ResourceLocation getFrameTexture() {
        return getTextureResourceLocation(getIdAt(frameIds, 0), "textures/gui/frames/", "_frame.png", "simple_frame.png");
    }

    protected ResourceLocation getQuarterFrameTexture() {
        return getTextureResourceLocation(getIdAt(frameIds, 0), "textures/gui/frames/quarter/", "_frame_quarter.png", "simple_frame_quarter.png");
    }

    protected ResourceLocation getFillerTexture(int slot) {
        return getTextureResourceLocation(getIdAt(fillerIds, slot), "textures/gui/fillers/", "_page_filler.png", "cube_page_filler.png");
    }

    protected ResourceLocation getSeparatorTexture(int slot) {
        return getTextureResourceLocation(getIdAt(separatorIds, slot), "textures/gui/separators/", "_separator.png", "simple_separator.png");
    }

    protected ResourceLocation getFrameTexture(int slot) {
        return getTextureResourceLocation(getIdAt(frameIds, slot), "textures/gui/frames/", "_frame.png", "simple_frame.png");
    }

    protected ResourceLocation getQuarterFrameTexture(int slot) {
        return getTextureResourceLocation(getIdAt(frameIds, slot), "textures/gui/frames/quarter/", "_frame_quarter.png", "simple_frame_quarter.png");
    }

    protected static List<String> splitIds(String raw) {
        List<String> result = new ArrayList<>();
        if (raw == null || raw.isBlank()) return result;

        for (String part : raw.split(",", -1)) {
            result.add(part.trim());
        }
        return result;
    }

    protected static String getIdAt(String raw, int index) {
        List<String> ids = splitIds(raw);
        if (ids.isEmpty()) return null;
        return ids.get(Math.min(Math.max(index, 0), ids.size() - 1));
    }

    protected static List<String> splitIds(JsonElement raw) {
        List<String> result = new ArrayList<>();
        if (raw == null || raw.isJsonNull()) return result;

        if (raw.isJsonPrimitive()) return splitIds(raw.getAsString());

        if (raw.isJsonArray()) {
            for (JsonElement item : raw.getAsJsonArray()) {
                result.add(item.isJsonPrimitive() ? item.getAsString().trim() : "");
            }
        }
        return result;
    }

    protected static String getIdAt(JsonElement raw, int index) {
        List<String> ids = splitIds(raw);
        if (ids.isEmpty()) return null;
        return ids.get(Math.min(Math.max(index, 0), ids.size() - 1));
    }

    protected static ResourceLocation getTextureResourceLocation(String id, String folder, String suffix, String defaultName) {
        if (id != null && !id.isEmpty()) {
            var resources = Minecraft.getInstance().getResourceManager();
            ResourceLocation named = ResourceLocation.tryParse(PatchouliPlus.MOD_ID + ":" + folder + id + suffix);
            ResourceLocation full = ResourceLocation.tryParse(id);

            if (named != null && resources.getResource(named).isPresent()) return named;
            if (full != null && resources.getResource(full).isPresent()) return full;
        }
        return new ResourceLocation(PatchouliPlus.MOD_ID, folder + defaultName);
    }

    protected boolean hasValidTitle() {
        return title != null && !title.isEmpty();
    }

    protected int getTitleY() {
        if (titlePosition == null) return 0;
        switch (titlePosition) {
            case TOP -> {
                return -6;
            }
            case HIGH -> {
                return -3;
            }
        }
        return 0;
    }

    protected int getY() {
        int y;

        if (hasValidTitle() && hasSeparator(0)) y = 22;
        else if (!hasValidTitle() && !hasSeparator(0)) y = 0;
        else y = 10;

        return titlePosition == TitlePosition.TOP ? y - 3 : y;
    }
}
