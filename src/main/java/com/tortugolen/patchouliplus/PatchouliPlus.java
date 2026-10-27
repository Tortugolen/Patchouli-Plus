package com.tortugolen.patchouliplus;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PatchouliPlus.MOD_ID)
public class PatchouliPlus {
    public static final String MOD_ID = "patchouli_plus";

    public PatchouliPlus() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitTabs.TABS.register(modEventBus);
    }
}