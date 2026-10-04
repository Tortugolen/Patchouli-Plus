package com.tortugolen.patchouliplus;

import com.tortugolen.patchouliplus.book.page.*;
import com.tortugolen.patchouliplus.book.page.entity.PageEntityPlus;
import com.tortugolen.patchouliplus.book.page.entity.PageEntityProjectionPlus;
import com.tortugolen.patchouliplus.book.page.recipes.*;
import com.tortugolen.patchouliplus.book.page.spotlight.PageDoubleSpotlightPlus;
import com.tortugolen.patchouliplus.book.page.spotlight.PageFarDoubleSpotlightPlus;
import com.tortugolen.patchouliplus.book.page.spotlight.PageSpotlightPlus;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vazkii.patchouli.client.book.ClientBookRegistry;

@Mod.EventBusSubscriber(modid = PatchouliPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientBookRegistryPlus {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ClientBookRegistryPlus::addPageTypes);
    }

    private static void addPageTypes() {
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "chapter_start"), PageChapterStartPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "empty"), PageEmptyPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "text"), PageTextPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "image"), PageImagePlus.class);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "link"), PageLinkPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "quest"), PageQuestPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "multiblock"), PageMultiblockPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "relations"), PageRelationsPlus.class);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "entity"), PageEntityPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "entity_projection"), PageEntityProjectionPlus.class);

        //Spotlight
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "spotlight"), PageSpotlightPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "double_spotlight"), PageDoubleSpotlightPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "far_double_spotlight"), PageFarDoubleSpotlightPlus.class);

        //Recipes
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "simple_recipe"), PageSimpleRecipePlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "smelting"), PageSmeltingPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "blasting"), PageBlastingPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "smoking"), PageSmokingPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "campfire"), PageCampfireCookingPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "stonecutting"), PageStonecuttingPlus.class);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "crafting"), PageCraftingPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "smithing"), PageSmithingPlus.class);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "brewing"), PageBrewingPlus.class);
    }
}