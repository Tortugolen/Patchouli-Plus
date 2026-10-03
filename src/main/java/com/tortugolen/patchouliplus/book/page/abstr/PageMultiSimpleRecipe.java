package com.tortugolen.patchouliplus.book.page.abstr;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;
import com.tortugolen.patchouliplus.book.page.PageTextPlus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class PageMultiSimpleRecipe<T> extends PageTextPlus {
    @SerializedName("recipes")
    List<JsonElement> recipes = new ArrayList<>();
    protected transient List<List<T>> slots = new ArrayList<>();
    @SerializedName("recipe")
    String recipe1;
    @SerializedName("recipe2")
    String recipe2;
    @SerializedName("recipe3")
    String recipe3;
    @SerializedName("recipe4")
    String recipe4;
    @SerializedName("recipe5")
    String recipe5;

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);

        List<JsonElement> all = new ArrayList<>();

        for (String raw : Arrays.asList(recipe1, recipe2, recipe3, recipe4, recipe5)) {
            if (raw != null && !raw.isEmpty()) {
                all.add(new JsonPrimitive(raw));
            }
        }
        if (recipes != null) {
            all.addAll(recipes);
        }

        for (JsonElement element : all) {
            List<T> alternatives = parseSlot(element, level, builder, entry);
            if (!alternatives.isEmpty()) {
                slots.add(alternatives);
            }
        }
    }

    protected List<T> parseSlot(JsonElement element, Level level, BookContentsBuilder builder, BookEntry entry) {
        List<T> result = new ArrayList<>();
        if (element == null || element.isJsonNull()) return result;

        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            for (JsonElement child : array) {
                result.addAll(parseSlot(child, level, builder, entry));
            }
        } else if (element.isJsonObject()) {
            T recipe = loadRecipeObject(element.getAsJsonObject(), level, builder, entry);
            if (recipe != null) result.add(recipe);
        } else if (element.isJsonPrimitive()) {
            String raw = element.getAsString();
            if (raw.isEmpty()) return result;
            for (String part : raw.split(",")) {
                ResourceLocation id = ResourceLocation.tryParse(part.trim());
                if (id == null) {
                    PatchouliAPI.LOGGER.warn("Invalid recipe id '{}'", part);
                    continue;
                }
                T recipe = loadRecipe(level, builder, entry, id);
                if (recipe != null) result.add(recipe);
            }
        }
        return result;
    }

    private int rotationIndex(int size) {
        if (size <= 1) return 0;
        return (int) ((System.currentTimeMillis() / 1000L) % size);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int recipeX = getX();
        int recipeY = getY();

        for (int i = 0; i < slots.size(); i++) {
            List<T> alternatives = slots.get(i);
            int y = recipeY + i * getRecipeHeight();
            T current = alternatives.get(rotationIndex(alternatives.size()));
            drawRecipe(graphics, current, recipeX, y, mouseX, mouseY, i > 0);
        }

        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    public int getTextHeight() {
        return super.getTextHeight() + getRecipeHeight() * getRecipeNumber();
    }

    protected int getRecipeNumber() {
        return slots.size();
    }

    protected int getX() {
        return 9;
    }

    protected int getRecipeHeight() {
        return 24 + 4;
    }

    protected T loadRecipeObject(JsonObject object, Level level, BookContentsBuilder builder, BookEntry entry) {
        PatchouliAPI.LOGGER.warn("This page type does not support object recipes: {}", object);
        return null;
    }

    protected abstract T loadRecipe(Level level, BookContentsBuilder builder, BookEntry entry, ResourceLocation id);

    protected abstract void drawRecipe(GuiGraphics graphics, T recipe, int x, int y, int mouseX, int mouseY, boolean second);
}