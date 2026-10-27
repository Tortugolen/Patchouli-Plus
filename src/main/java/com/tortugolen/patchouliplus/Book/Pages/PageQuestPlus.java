package com.tortugolen.patchouliplus.Book.Pages;

import com.tortugolen.patchouliplus.Book.Gui.GUIBookPlus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import vazkii.patchouli.client.base.ClientAdvancements;
import vazkii.patchouli.client.base.PersistentData;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.common.book.Book;

public class PageQuestPlus extends PageTextPlus {
    protected ResourceLocation trigger;
    protected transient boolean isManual;

    @Override
    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);
        isManual = trigger == null;
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);

        if (isManual) {
            Button button = Button.builder(Component.empty(), this::questButtonClicked).pos(8, 121).size(100, 20).build();
            addButton(button);
            updateButtonText(button);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
        super.render(graphics, mouseX, mouseY, pticks);

        if (!hasValidTitle()) parent.drawCenteredStringNoShadow(graphics, I18n.get("patchouli.gui.lexicon.objective", new Object[0]), 58, 0, book.headerColor);

        if (!isManual) {
            if (hasSeparator(1)) GUIBookPlus.drawSeparator(graphics, getSeparatorTexture(1), 3, 131, 110, 3);
            boolean completed = isCompleted(parent.book);
            String s = I18n.get(completed ? "patchouli.gui.lexicon.complete" : "patchouli.gui.lexicon.incomplete");
            int color = completed ? '謚' : book.headerColor;
            parent.drawCenteredStringNoShadow(graphics, s, 58, 139, color);
        }
    }

    protected void questButtonClicked(Button button) {
        ResourceLocation res = entry.getId();
        PersistentData.BookData data = PersistentData.data.getBookData(parent.book);

        if (data.completedManualQuests.contains(res)) data.completedManualQuests.remove(res);
        else data.completedManualQuests.add(res);

        PersistentData.save();
        updateButtonText(button);
        entry.markReadStateDirty();
    }

    private void updateButtonText(Button button) {
        boolean completed = isCompleted(parent.book);
        Component s = Component.translatable(completed ? "patchouli.gui.lexicon.mark_incomplete" : "patchouli.gui.lexicon.mark_complete");
        button.setMessage(s);
    }

    public boolean isCompleted(Book book) {
        return isManual ? PersistentData.data.getBookData(book).completedManualQuests.contains(entry.getId()) : trigger != null && ClientAdvancements.hasDone(trigger.toString());
    }
}
