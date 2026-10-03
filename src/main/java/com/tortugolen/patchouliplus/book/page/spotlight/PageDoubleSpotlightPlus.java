package com.tortugolen.patchouliplus.book.page.spotlight;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.base.ERecipeLayout;
import com.tortugolen.patchouliplus.book.page.abstr.PageMultiSpotlight;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageDoubleSpotlightPlus extends PageMultiSpotlight {
    @SerializedName("item")
    IVariable item1;
    @SerializedName("item2")
    IVariable item2;
    @SerializedName("item3")
    IVariable item3;
    @SerializedName("item4")
    IVariable item4;
    @SerializedName("item5")
    IVariable item5;
    @SerializedName("item6")
    IVariable item6;
    @SerializedName("item7")
    IVariable item7;
    @SerializedName("item8")
    IVariable item8;
    @SerializedName("item9")
    IVariable item9;
    @SerializedName("item10")
    IVariable item10;

    @SerializedName("link_recipe")
    boolean linkRecipe;

    protected transient List<Ingredient> ingredients = new ArrayList<>();

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);

        List<IVariable> items = Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10);

        for (IVariable itemVar : items) {
            if (itemVar == null) continue;

            Ingredient ingredient = itemVar.as(Ingredient.class);
            if (ingredient == null) continue;

            ingredients.add(ingredient);

            if (linkRecipe) {
                for (ItemStack stack : ingredient.getItems()) {
                    entry.addRelevantStack(builder, stack, pageNum);
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        RenderSystem.enableBlend();
        for (int i = 0; i < ingredients.size() / 2; i++) {
            int y = getY() + i * getRecipeHeight();
            ERecipeLayout.SIMPLE_RECIPE.draw(graphics, getCraftingTexturePlus(), getX() - 10, y, 271, 83, 24);
            parent.renderIngredient(graphics, 50 - 10, getY() + 4 + i * getRecipeHeight(), mouseX, mouseY, ingredients.get(i));
            parent.renderIngredient(graphics, 50 + 9, getY() + 4 + i * getRecipeHeight(), mouseX, mouseY, ingredients.get(i + 1));
        }

        super.render(graphics, mouseX, mouseY, pticks);
    }

    @Override
    protected int getRecipeNumber() {
        return ingredients.size();
    }
}