package com.tortugolen.patchouliplus.Book.Pages.spotlight;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.PageMultiSpotlight;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;

import java.util.ArrayList;
import java.util.List;

public class PageSpotlightPlus extends PageMultiSpotlight {
    @SerializedName("item")
    protected IVariable item;
    @SerializedName("link_recipe")
    boolean linkRecipe;

    protected transient List<Ingredient> ingredients = new ArrayList<>();

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);

        ingredients = new ArrayList<>();
        if (item == null) return;

        for (IVariable itemVar : item.asListOrSingleton()) {
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
            int y = getY() + i * SLOT_HEIGHT;
            GUIBookPlus.drawCraftingTexturePlus(graphics, book, SPOTLIGHT_START_X, y, 64, 24);
            parent.renderIngredient(graphics, 50, y + 4, mouseX, mouseY, ingredients.get(i));
        }
    }

    @Override
    protected int getSlotNumber() {
        return ingredients.size();
    }
}