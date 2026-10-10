package com.tortugolen.patchouliplus.Book.Pages.Abstract;

public abstract class PageMultiSpotlight extends PageWithItem {
    protected static final int SPOTLIGHT_START_X = START_X + 21;
    protected static final int SLOT_HEIGHT = 24 + SLOT_SEPARATION_Y;

    @Override
    public int getTextY() {
        return super.getTextY() + SLOT_HEIGHT * getSlotNumber() + SLOT_SEPARATION_Y;
    }

    protected abstract int getSlotNumber();
}
