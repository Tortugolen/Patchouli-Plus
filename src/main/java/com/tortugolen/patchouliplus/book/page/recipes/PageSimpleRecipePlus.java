package com.tortugolen.patchouliplus.book.page.recipes;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.base.ERecipeLayout;
import com.tortugolen.patchouliplus.base.RecipeJsonUtils;
import com.tortugolen.patchouliplus.book.page.PageTextPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageSimpleRecipePlus extends PageTextPlus {
    @SerializedName("input")
    String input1;
    @SerializedName("input2")
    String input2;
    @SerializedName("input3")
    String input3;
    @SerializedName("input4")
    String input4;
    @SerializedName("input5")
    String input5;

    @SerializedName("output")
    String output1;
    @SerializedName("output2")
    String output2;
    @SerializedName("output3")
    String output3;
    @SerializedName("output4")
    String output4;
    @SerializedName("output5")
    String output5;

    @SerializedName("station")
    String station1;
    @SerializedName("station2")
    String station2;
    @SerializedName("station3")
    String station3;
    @SerializedName("station4")
    String station4;
    @SerializedName("station5")
    String station5;

    protected transient List<Slot> slots = new ArrayList<>();

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);

        List<String> inputIds = Arrays.asList(input1, input2, input3, input4, input5);
        List<String> outputIds = Arrays.asList(output1, output2, output3, output4, output5);
        List<String> stationIds = Arrays.asList(station1, station2, station3, station4, station5);

        for (int i = 0; i < inputIds.size(); i++) {
            Ingredient input = RecipeJsonUtils.toIngredient(inputIds.get(i));
            Ingredient output = RecipeJsonUtils.toIngredient(outputIds.get(i));
            Ingredient station = RecipeJsonUtils.toIngredient(stationIds.get(i));
            if (input == null || output == null || station == null) continue;
            slots.add(new Slot(input, output, station));
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        RenderSystem.enableBlend();

        int x = getX();
        int y = getY();

        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            int rowY = y + i * getRecipeHeight();

            ERecipeLayout.SIMPLE_RECIPE.draw(graphics, getCraftingTexturePlus(), x, rowY);
            parent.renderIngredient(graphics, x + 4, rowY + 4, mouseX, mouseY, slot.input);

            if (slot.station != null) {
                parent.renderIngredient(graphics, x + 40, rowY + 4, mouseX, mouseY, slot.station);
            }

            parent.renderIngredient(graphics, x + 76, rowY + 4, mouseX, mouseY, slot.output);
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

    private static class Slot {
        final Ingredient input;
        final Ingredient output;
        final Ingredient station;

        Slot(Ingredient input, Ingredient output, Ingredient station) {
            this.input = input;
            this.output = output;
            this.station = station;
        }
    }
}