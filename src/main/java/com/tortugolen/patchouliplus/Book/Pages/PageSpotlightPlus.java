package com.tortugolen.patchouliplus.Book.Pages;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import com.tortugolen.patchouliplus.Book.Pages.Abstract.PageWithItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;

import java.util.ArrayList;
import java.util.List;

public class PageSpotlightPlus extends PageWithItem {
    protected static final int SPOTLIGHT_START_X = START_X + 21;
    protected static final int SLOT_HEIGHT = 24 + SLOT_SEPARATION_Y;
    protected static final int MAX_SLOTS_PER_ROW = 4;

    protected IVariable item;
    @SerializedName("row_slots")
    protected int slotsPerRow = 1;
    protected boolean separated = true;
    @SerializedName("link_recipe")
    protected boolean linkRecipe = false;

    protected transient List<Ingredient> items = new ArrayList<>();

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);

        items = new ArrayList<>();
        if (item == null) return;

        for (IVariable itemVar : item.asListOrSingleton()) {
            Ingredient ingredient = resolveSlot(itemVar);
            if (ingredient == null) {
                PatchouliAPI.LOGGER.warn("Could not resolve item '{}' in entry {}", itemVar.unwrap(), entry.getId());
                continue;
            }

            items.add(ingredient);

            if (linkRecipe) for (ItemStack stack : ingredient.getItems()) entry.addRelevantStack(builder, stack, pageNum);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        super.render(graphics, mouseX, mouseY, pticks);

        RenderSystem.enableBlend();

        drawContent(graphics, mouseX, mouseY);
    }

    @Override
    public int getTextY() {
        return super.getTextY() + SLOT_HEIGHT * getSlotNumber() + SLOT_SEPARATION_Y;
    }

    protected void drawContent(GuiGraphics graphics, int mouseX, int mouseY) {
        int x = 50;

        if (slotsPerRow == 1) {
            for (int i = 0; i < items.size(); i++) {
                int y = getY() + i * SLOT_HEIGHT;
                GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X, y, 1, 64, 24);
                parent.renderIngredient(graphics, x, y + 4, mouseX, mouseY, items.get(i));
            }
        }
        if (slotsPerRow == 2) {
            for (int i = 0; i < (items.size() + 1) / 2; i++) {
                int y = getY() + i * SLOT_HEIGHT;
                if (items.size() >= i * 2 + 1) {
                    if (separated) {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 20, y, 51, 104, 24);
                        parent.renderIngredient(graphics, x - 20, y + 4, mouseX, mouseY, items.get(i * 2));
                        parent.renderIngredient(graphics, x + 20, y + 4, mouseX, mouseY, items.get(i * 2 + 1));
                    } else {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 9, y, 26, 83, 24);
                        parent.renderIngredient(graphics, x - 9, y + 4, mouseX, mouseY, items.get(i * 2));
                        parent.renderIngredient(graphics, x + 10, y + 4, mouseX, mouseY, items.get(i * 2 + 1));
                    }
                } else {
                    GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X, y, 1, 64, 24);
                    parent.renderIngredient(graphics, x, y + 4, mouseX, mouseY, items.get(i * 2));
                }
            }
        }
        if (slotsPerRow == 3) {
            for (int i = 0; i < (items.size() + 2) / 3; i++) {
                int y = getY() + i * SLOT_HEIGHT;
                if (items.size() - i * 3 >= 3) {
                    if (separated) {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 27, y, 101, 122, 24);
                        parent.renderIngredient(graphics, x - 27, y + 4, mouseX, mouseY, items.get(i * 3));
                        parent.renderIngredient(graphics, x, y + 4, mouseX, mouseY, items.get(i * 3 + 1));
                        parent.renderIngredient(graphics, x + 27, y + 4, mouseX, mouseY, items.get(i * 3 + 2));
                    } else {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 19, y, 76, 102, 24);
                        parent.renderIngredient(graphics, x - 19, y + 4, mouseX, mouseY, items.get(i * 3));
                        parent.renderIngredient(graphics, x, y + 4, mouseX, mouseY, items.get(i * 3 + 1));
                        parent.renderIngredient(graphics, x + 19, y + 4, mouseX, mouseY, items.get(i * 3 + 2));
                    }
                } else if (items.size() - i * 3 == 2) {
                    if (separated) {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 20, y, 51, 104, 24);
                        parent.renderIngredient(graphics, x - 20, y + 4, mouseX, mouseY, items.get(i * 3));
                        parent.renderIngredient(graphics, x + 20, y + 4, mouseX, mouseY, items.get(i * 3 + 1));
                    } else {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 9, y, 26, 83, 24);
                        parent.renderIngredient(graphics, x - 9, y + 4, mouseX, mouseY, items.get(i * 3));
                        parent.renderIngredient(graphics, x + 10, y + 4, mouseX, mouseY, items.get(i * 3 + 1));
                    }
                } else {
                    GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X, y, 1, 64, 24);
                    parent.renderIngredient(graphics, x, y + 4, mouseX, mouseY, items.get(i * 3));
                }
            }
        }
        if (slotsPerRow == 4) {
            for (int i = 0; i < (items.size() + 3) / 4; i++) {
                int y = getY() + i * SLOT_HEIGHT;
                if (items.size() - i * 4 >= 4) {
                    if (separated) {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 28, y, 126, 121, 24);
                        parent.renderIngredient(graphics, x - 28, y + 4, mouseX, mouseY, items.get(i * 4));
                        parent.renderIngredient(graphics, x - 9, y + 4, mouseX, mouseY, items.get(i * 4 + 1));
                        parent.renderIngredient(graphics, x + 10, y + 4, mouseX, mouseY, items.get(i * 4 + 2));
                        parent.renderIngredient(graphics, x + 29, y + 4, mouseX, mouseY, items.get(i * 4 + 3));
                    } else {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 28, y, 126, 121, 24);
                        parent.renderIngredient(graphics, x - 28, y + 4, mouseX, mouseY, items.get(i * 4));
                        parent.renderIngredient(graphics, x - 9, y + 4, mouseX, mouseY, items.get(i * 4 + 1));
                        parent.renderIngredient(graphics, x + 10, y + 4, mouseX, mouseY, items.get(i * 4 + 2));
                        parent.renderIngredient(graphics, x + 29, y + 4, mouseX, mouseY, items.get(i * 4 + 3));
                    }
                } else if (items.size() - i * 4 == 3) {
                    if (separated) {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 27, y, 101, 122, 24);
                        parent.renderIngredient(graphics, x - 27, y + 4, mouseX, mouseY, items.get(i * 4));
                        parent.renderIngredient(graphics, x, y + 4, mouseX, mouseY, items.get(i * 4 + 1));
                        parent.renderIngredient(graphics, x + 27, y + 4, mouseX, mouseY, items.get(i * 4 + 2));
                    } else {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 19, y, 76, 102, 24);
                        parent.renderIngredient(graphics, x - 19, y + 4, mouseX, mouseY, items.get(i * 4));
                        parent.renderIngredient(graphics, x, y + 4, mouseX, mouseY, items.get(i * 4 + 1));
                        parent.renderIngredient(graphics, x + 19, y + 4, mouseX, mouseY, items.get(i * 4 + 2));
                    }
                } else if (items.size() - i * 4 == 2) {
                    if (separated) {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 20, y, 51, 104, 24);
                        parent.renderIngredient(graphics, x - 20, y + 4, mouseX, mouseY, items.get(i * 4));
                        parent.renderIngredient(graphics, x + 20, y + 4, mouseX, mouseY, items.get(i * 4 + 1));
                    } else {
                        GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X - 9, y, 26, 83, 24);
                        parent.renderIngredient(graphics, x - 9, y + 4, mouseX, mouseY, items.get(i * 4));
                        parent.renderIngredient(graphics, x + 10, y + 4, mouseX, mouseY, items.get(i * 4 + 1));
                    }
                } else {
                    GUIBookPlus.drawSpotlightTexturePlus(graphics, book, SPOTLIGHT_START_X, y, 1, 64, 24);
                    parent.renderIngredient(graphics, x, y + 4, mouseX, mouseY, items.get(i * 4));
                }
            }
        }
        if (slotsPerRow < 1 || slotsPerRow > MAX_SLOTS_PER_ROW) {
            PatchouliAPI.LOGGER.warn("Invalid 'row_slots' value {} in entry {}, using 4", slotsPerRow, entry.getId());
        }
    }

    protected int getSlotNumber() {
        return (items.size() + slotsPerRow - 1) / slotsPerRow;
    }
}