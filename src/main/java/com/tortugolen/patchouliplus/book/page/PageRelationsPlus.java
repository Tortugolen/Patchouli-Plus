package com.tortugolen.patchouliplus.book.page;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.gui.button.GuiButtonEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageRelationsPlus extends PageTextPlus {
    List<String> entries;
    transient List<BookEntry> entryObjs;

    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);
        entryObjs = new ArrayList();

        for(String s : entries) {
            ResourceLocation targetId = new ResourceLocation(s);
            BookEntry targetEntry = builder.getEntry(targetId);
            if (targetEntry == null) {
                throw new IllegalArgumentException("Could not find entry " + String.valueOf(targetId));
            }
            entryObjs.add(targetEntry);
        }
    }

    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        List<BookEntry> displayedEntries = new ArrayList(entryObjs);
        displayedEntries.removeIf(BookEntry::shouldHide);
        Collections.sort(displayedEntries);

        for(int i = 0; i < displayedEntries.size(); ++i) {
            Button button = new GuiButtonEntry(parent, 0, getY() + i * 11, (BookEntry)displayedEntries.get(i), this::handleButtonEntry);
            addButton(button);
        }
    }

    public void handleButtonEntry(Button button) {
        GuiBookEntry.displayOrBookmark(parent, ((GuiButtonEntry)button).getEntry());
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        super.render(graphics, mouseX, mouseY, pticks);

        if (!hasValidTitle()) {
            parent.drawCenteredStringNoShadow(graphics, I18n.get("patchouli.gui.lexicon.relations", new Object[0]), 58, 0, book.headerColor);
        }
    }

    @Override
    public int getTextHeight() {
        return super.getTextHeight() + entryObjs.size() * 12;
    }
}
