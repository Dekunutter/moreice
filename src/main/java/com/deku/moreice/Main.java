package com.deku.moreice;

import com.deku.eastwardjourneys.utils.LogTweaker;
import com.deku.eastwardjourneys.utils.ModConfiguration;
import com.deku.moreice.common.blocks.ModBlockInitializer;
import com.deku.moreice.common.items.ModItems;
import com.deku.moreice.common.ui.ModCreativeTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MOD_ID)
public class Main
{
    final boolean HIDE_CONSOLE_NOISE = true;

    // declare Mod ID
    public static final String MOD_ID = "moreice";

    // Initialize logger
    public static final Logger LOGGER = LogManager.getLogger(Main.class);


    /**
     * Constructor for initializing the mod.
     * Handles the setup of:
     *      - Log filtering.
     *      - Event Bus listeners
     *      - Registries
     *      - Ensuring client-only registrars only execute on a client
     *      - Ensures that mod structure piece types are registered early
     *      - Ensures that biomes are registered early
     *      - Adds additional forge event listeners for biome and world loading events
     */
    public Main() {
        System.out.println("STARTING EXECUTION");

        if (HIDE_CONSOLE_NOISE) {
            LogTweaker.applyLogFilterLevel(Level.WARN);
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfiguration.COMMON_SPEC, "moreice-common.toml");

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Block logic
        ModBlockInitializer.BLOCKS.register(eventBus);

        // Creative Mode Tabs
        ModCreativeTabs.CREATIVE_MOD_TABS.register(eventBus);

        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.register(this);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("moreice", "helloworld", () -> { LOGGER.debug("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.debug("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
    }

    /**
     * Inner class for different event registers used by the mod
     */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        /**
         * Used to register items into the game using the mod event bus
         *
         * @param registerEvent The register event with which items will be registered
         */
        @SubscribeEvent
        public static void onItemsRegistry(final RegisterEvent registerEvent) {
            registerEvent.register(ForgeRegistries.Keys.ITEMS, registrar -> {
                // Ice
                registrar.register(new ResourceLocation(MOD_ID, "ice_stairs"), new BlockItem(ModBlockInitializer.ICE_STAIRS.get(), new Item.Properties()));
            });
        }

        /**
         * Used to register items into vanilla creative mode tabs in the creative mode UI using the mod event bus
         *
         * @param creativeTabBuilderRegistryEvent The registry event with which new items are added to vanilla creative mode tabs
         */
        @SubscribeEvent
        public static void onCreativeModeTabBuilderRegister(BuildCreativeModeTabContentsEvent creativeTabBuilderRegistryEvent) {
            MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = creativeTabBuilderRegistryEvent.getEntries();
            CreativeModeTab.TabVisibility visibility = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

            if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
                // Ice
                entries.putAfter(new ItemStack(Items.ICE), new ItemStack(ModItems.ICE_STAIRS), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.COMBAT) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.INGREDIENTS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {

            }
        }
    }
}
