package com.tortugolen.patchouliplus.Book.Pages.Abstract.Recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.PageWithItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;

import java.util.ArrayList;
import java.util.List;

public abstract class PageRecipePlus<T> extends PageWithItem {
    protected static final int RECIPE_START_X = START_X + 4;

    @SerializedName("recipe")
    protected JsonElement recipes;
    @SerializedName("station_border")
    protected JsonElement stationBorder;

    protected transient List<List<T>> slots = new ArrayList<>();

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);

        slots = new ArrayList<>();
        if (recipes == null || recipes.isJsonNull()) return;

        Iterable<JsonElement> topLevel = recipes.isJsonArray() ? recipes.getAsJsonArray() : List.of(recipes);
        for (JsonElement element : topLevel) {
            List<T> alternatives = parseSlot(element, level, builder, entry);
            if (!alternatives.isEmpty()) slots.add(alternatives);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        int x = RECIPE_START_X;
        int y = getY();

        for (int i = 0; i < slots.size(); i++) {
            List<T> alternatives = slots.get(i);
            T current = alternatives.get(rotationIndex(alternatives.size()));
            drawRecipe(graphics, current, x, y, mouseX, mouseY, i > 0);
            y += getSlotHeight(alternatives);
        }

        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    public int getTextY() {
        int total = 0;
        for (List<T> alternatives : slots) total += getSlotHeight(alternatives);
        return super.getTextY() + total + SLOT_SEPARATION_Y;
    }

    protected List<T> parseSlot(JsonElement element, Level level, BookContentsBuilder builder, BookEntry entry) {
        List<T> result = new ArrayList<>();
        if (element == null || element.isJsonNull()) return result;

        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            for (JsonElement child : array) result.addAll(parseSlot(child, level, builder, entry));
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

    protected T loadRecipeObject(JsonObject object, Level level, BookContentsBuilder builder, BookEntry entry) {
        PatchouliAPI.LOGGER.warn("This page type does not support object recipes: {}", object);
        return null;
    }

    protected int getSlotHeight(List<T> alternatives) {
        int max = 24;
        for (T recipe : alternatives) max = Math.max(max, getRecipeHeight(recipe));
        return max + SLOT_SEPARATION_Y;
    }

    protected int getRecipeHeight(T recipe) {
        return 24;
    }

    protected abstract T loadRecipe(Level level, BookContentsBuilder builder, BookEntry entry, ResourceLocation id);

    protected abstract void drawRecipe(GuiGraphics graphics, T recipe, int x, int y, int mouseX, int mouseY, boolean second);
}