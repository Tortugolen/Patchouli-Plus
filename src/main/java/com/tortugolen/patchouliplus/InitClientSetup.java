package com.tortugolen.patchouliplus;

import com.tortugolen.patchouliplus.Book.Pages.*;
import com.tortugolen.patchouliplus.Book.Pages.PageSpotlightPlus;
import com.tortugolen.patchouliplus.Book.Pages.Recipes.*;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.ClientBookRegistry;

@Mod.EventBusSubscriber(modid = PatchouliPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(InitClientSetup::addPageTypes);
        event.enqueueWork(InitClientSetup::addCommands);
        event.enqueueWork(InitClientSetup::addFlags);
    }

    private static void addPageTypes() {
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "chapter_opening"), PageChapterOpeningPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "empty"), PageEmptyPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "text"), PageTextPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "image"), PageImagePlus.class);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "link"), PageLinkPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "quest"), PageQuestPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "relations"), PageRelationsPlus.class);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "multiblock"), PageMultiblockPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "entity"), PageEntityPlus.class);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "spotlight"), PageSpotlightPlus.class);

        //Recipes
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "recipe"), PageMultiRecipePlus.class);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "crafting"), PageCraftingPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "smithing"), PageSmithingPlus.class);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "smelting"), PageSmeltingPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "blasting"), PageBlastingPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "smoking"), PageSmokingPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "campfire"), PageCampfireCookingPlus.class);
        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "stonecutting"), PageStonecuttingPlus.class);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(PatchouliPlus.MOD_ID, "brewing"), PageBrewingPlus.class);
    }

    private static void addCommands() {
        PatchouliAPI.IPatchouliAPI API = PatchouliAPI.get();

        API.registerCommand("Li", iStyleStack -> "Lorem ipsum dolor sit amet consectetur adipiscing elit egestas vulputate volutpat elementum, quisque pellentesque potenti interdum aliquet ut per dictum integer maecenas conubia, fusce cum senectus gravida lectus dictumst malesuada velit sagittis suscipit. Mi feugiat suscipit malesuada aliquam lacinia blandit egestas cubilia, auctor habitant rhoncus ultrices augue facilisis lobortis nisl, risus suspendisse gravida placerat litora interdum molestie. Odio blandit purus interdum nec vehicula cursus erat sed justo ornare, magna venenatis quam rutrum commodo facilisi faucibus suspendisse mattis risus suscipit, ut lobortis sem tortor pharetra bibendum tellus nulla nibh");
        API.registerCommand("true", iStyleStack -> {iStyleStack.modifyStyle(style -> style.withBold(true).withColor(ChatFormatting.DARK_GREEN)); return "true";});
        API.registerCommand("false", iStyleStack -> {iStyleStack.modifyStyle(style -> style.withBold(true).withColor(ChatFormatting.DARK_RED)); return "false";});
    }

    private static void addFlags() {
        PatchouliAPI.get().setConfigFlag("hidden", !FMLEnvironment.production);
    }
}