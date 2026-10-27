package com.tortugolen.patchouliplus;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.function.Supplier;

public class InitTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PatchouliPlus.MOD_ID);

    private static final Supplier<ItemStack> INFO_BOOK = () -> PatchouliAPI.get().getBookStack(new ResourceLocation(PatchouliPlus.MOD_ID, "info_book"));

    public static final RegistryObject<CreativeModeTab> PATCHOULI_PLUS = TABS.register("patchouli_plus", () -> CreativeModeTab.builder().title(Component.translatable("tab.patchouli_plus.patchouli_plus")).icon(INFO_BOOK).displayItems((pParameters, pOutput) -> {}).build());
}