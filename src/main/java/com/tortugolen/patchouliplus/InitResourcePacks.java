package com.tortugolen.patchouliplus;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = PatchouliPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class InitResourcePacks {

    private static final String PACK_FOLDER = "resourcepacks/patchouli_improvements";
    private static final String PACK_ID = "builtin/" + PatchouliPlus.MOD_ID + "_utils";

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) {
            return;
        }

        Path resourcePath = ModList.get().getModFileById(PatchouliPlus.MOD_ID).getFile().findResource(PACK_FOLDER);
        Pack pack = Pack.readMetaAndCreate(PACK_ID, Component.translatable("resourcepacks.patchouli_plus.patchouli_improvements"), false, id -> new PathPackResources(id, resourcePath, false), PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);

        event.addRepositorySource(consumer -> consumer.accept(pack));
    }
}