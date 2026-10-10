package com.tortugolen.patchouliplus.book.page;

import com.google.gson.annotations.SerializedName;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

public class PageLinkPlus extends PageTextPlus {
    String url;
    @SerializedName("link_text")
    IVariable linkText;
    transient Component realText;

    public void build(Level level, BookEntry entry, BookContentsBuilder builder, int pageNum) {
        super.build(level, entry, builder, pageNum);
        realText = (Component)linkText.as(Component.class);
    }

    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        addButton(Button.builder(i18nText(realText.getString()), (b) -> GuiBook.openWebLink(parent, url)).pos(8, 121).size(100, 20).build());
    }
}
