package com.tortugolen.patchouliplus.book.page.Abstract;

import com.google.gson.annotations.SerializedName;
import com.tortugolen.patchouliplus.PatchouliPlus;
import com.tortugolen.patchouliplus.xplat.IBookPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.book.BookPage;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentPage extends BookPage {
    protected String title;
    @SerializedName("separator")
    protected boolean separator = false;
    @SerializedName("separator2")
    protected boolean separator2 = false;
    @SerializedName("fillers")
    protected String fillerIds = null;
    @SerializedName("separators")
    protected String separatorIds = null;
    @SerializedName("borders")
    protected String borderIds = null;
    protected enum TextureTypes {
        FILLER,
        SEPARATOR,
        BORDER,
        QUARTER_BORDER
    }

    protected ResourceLocation getPath(String id, TextureTypes type) {
        String folder = "";
        if (type == TextureTypes.FILLER) folder = "filler";
        if (type == TextureTypes.SEPARATOR) folder = "separator";
        if (type == TextureTypes.BORDER) folder = "border";
        if (type == TextureTypes.QUARTER_BORDER) folder = "quarter_border";
        folder = folder + "s/";
        return new ResourceLocation(PatchouliPlus.MOD_ID, "textures/gui/" + folder + id + ".png");
    }

    protected ResourceLocation getCraftingTexturePlus() {
        return ((IBookPlus) book).getCraftingTexturePlus();
    }

    protected ResourceLocation getFillerTexture(int slot) {
        return getTextureResourceLocation(getIdAt(fillerIds, slot), "textures/gui/fillers/", "_page_filler.png", "cube_page_filler.png");
    }

    protected ResourceLocation getSeparatorTexture(int slot) {
        return getTextureResourceLocation(getIdAt(separatorIds, slot), "textures/gui/separators/", "_separator.png", "simple_separator.png");
    }

    protected ResourceLocation getBorderTexture(int slot) {
        return getTextureResourceLocation(getIdAt(borderIds, slot), "textures/gui/borders/", "_border.png", "simple_border.png");
    }

    protected ResourceLocation getQuarterBorderTexture(int slot) {
        return getTextureResourceLocation(getIdAt(borderIds, slot), "textures/gui/borders/", "_border_quarter.png", "simple_border_quarter.png");
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

    private static ResourceLocation getTextureResourceLocation(String id, String folder, String suffix, String defaultName) {
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

    protected int getSeparatorCount() {
        return 1;
    }

    protected int getMaxComponentNumber() {
        return 0;
    }
}
