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

public class PageSpotlightPlus extends PageMultiSpotlight {
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

    @SerializedName("link_recipe")
    boolean linkRecipe;

    protected transient List<Ingredient> ingredients = new ArrayList<>();

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);

        List<IVariable> items = Arrays.asList(item1, item2, item3, item4, item5);

        for (IVariable itemVar : items) {
            Ingredient ingredient = resolveIngredient(itemVar);
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
        super.render(graphics, mouseX, mouseY, pticks);

        RenderSystem.enableBlend();
        for (int i = 0; i < ingredients.size(); i++) {
            int y = getY() + i * getRecipeHeight();
            ERecipeLayout.SIMPLE_RECIPE.draw(graphics, getCraftingTexturePlus(), getX(), y, 246, 64, 24);
            parent.renderIngredient(graphics, 50, y + 4, mouseX, mouseY, ingredients.get(i));
        }
    }

    @Override
    protected int getRecipeNumber() {
        return ingredients.size();
    }
}