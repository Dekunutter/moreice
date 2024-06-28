package com.deku.moreice.neoforge;

import com.deku.moreice.MoreIce;
import com.deku.moreice.neoforge.client.ClientRegistrar;
import com.deku.moreice.client.MoreIceClient;
import com.deku.moreice.common.blocks.ModBlockSetType;
import com.deku.moreice.common.items.ModItems;
import com.deku.moreice.neoforge.util.ModConfiguration;
import com.deku.moreice.utils.LogTweaker;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.event.lifecycle.InterModProcessEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.MutableHashedLinkedMap;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.Level;

import java.util.stream.Collectors;

import static com.deku.moreice.MoreIce.*;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MOD_ID)
public class Main
{
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
    public Main(IEventBus eventBus, ModContainer modContainer) {
        System.out.println("STARTING EXECUTION");

        if (HIDE_CONSOLE_NOISE) {
            LogTweaker.applyLogFilterLevel(Level.WARN);
        }

        modContainer.registerConfig(ModConfig.Type.COMMON, ModConfiguration.COMMON_SPEC, "moreice-common.toml");

        MoreIce.init();

        eventBus.addListener(this::setup);

        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);

        eventBus.addListener(this::clientSetup);

        ClientRegistrar clientRegistrar = new ClientRegistrar(eventBus);

        // Register ourselves for server and other game events we are interested in
        IEventBus neoForgeEventBus = NeoForge.EVENT_BUS;
        neoForgeEventBus.register(this);

        if (FMLLoader.getDist().isClient()) {
            clientRegistrar.registerClientOnlyEvents();
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BlockSetType.register(ModBlockSetType.ICE);
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MoreIceClient.initClient();
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
    @EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
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
                entries.putAfter(new ItemStack(Items.ICE), new ItemStack(ModItems.ICE_STAIRS.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_STAIRS.get()), new ItemStack(ModItems.ICE_SLAB.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_SLAB.get()), new ItemStack(ModItems.ICE_WALL.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_WALL.get()), new ItemStack(ModItems.ICE_BRICKS.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BRICKS.get()), new ItemStack(ModItems.ICE_BRICK_SLAB.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BRICK_SLAB.get()), new ItemStack(ModItems.ICE_BRICK_WALL.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BRICK_WALL.get()), new ItemStack(ModItems.ICE_BRICK_STAIRS.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BRICK_STAIRS.get()), new ItemStack(ModItems.ICE_PRESSURE_PLATE.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_PRESSURE_PLATE.get()), new ItemStack(ModItems.ICE_BUTTON.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BUTTON.get()), new ItemStack(ModItems.CHISELED_ICE.get()), visibility);

                // Packed Ice
                entries.putAfter(new ItemStack(Items.PACKED_ICE), new ItemStack(ModItems.PACKED_ICE_STAIRS.get()), visibility);
                entries.putAfter(new ItemStack(Items.PACKED_ICE), new ItemStack(ModItems.PACKED_ICE_SLAB.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_SLAB.get()), new ItemStack(ModItems.PACKED_ICE_WALL.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_WALL.get()), new ItemStack(ModItems.PACKED_ICE_BRICKS.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_BRICKS.get()), new ItemStack(ModItems.PACKED_ICE_BRICK_SLAB.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_BRICK_SLAB.get()), new ItemStack(ModItems.PACKED_ICE_BRICK_WALL.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_BRICK_WALL.get()), new ItemStack(ModItems.PACKED_ICE_BRICK_STAIRS.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_BRICK_STAIRS.get()), new ItemStack(ModItems.PACKED_ICE_PRESSURE_PLATE.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_PRESSURE_PLATE.get()), new ItemStack(ModItems.PACKED_ICE_BUTTON.get()), visibility);

                // Blue Ice
                entries.putAfter(new ItemStack(Items.BLUE_ICE), new ItemStack(ModItems.BLUE_ICE_STAIRS.get()), visibility);
                entries.putAfter(new ItemStack(Items.BLUE_ICE), new ItemStack(ModItems.BLUE_ICE_SLAB.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.BLUE_ICE_SLAB.get()), new ItemStack(ModItems.BLUE_ICE_WALL.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.BLUE_ICE_WALL.get()), new ItemStack(ModItems.BLUE_ICE_BRICKS.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.BLUE_ICE_BRICKS.get()), new ItemStack(ModItems.BLUE_ICE_BRICK_SLAB.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.BLUE_ICE_BRICK_SLAB.get()), new ItemStack(ModItems.BLUE_ICE_BRICK_WALL.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.BLUE_ICE_BRICK_WALL.get()), new ItemStack(ModItems.BLUE_ICE_BRICK_STAIRS.get()), visibility);

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                entries.putAfter(new ItemStack(Items.FURNACE), new ItemStack(ModItems.FREEZER.get()), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
                // Ice
                entries.putAfter(new ItemStack(Items.POLISHED_BLACKSTONE_PRESSURE_PLATE), new ItemStack(ModItems.ICE_PRESSURE_PLATE.get()), visibility);
                entries.putAfter(new ItemStack(Items.POLISHED_BLACKSTONE_BUTTON), new ItemStack(ModItems.ICE_BUTTON.get()), visibility);

                // Packed Ice
                entries.putAfter(new ItemStack(ModItems.ICE_PRESSURE_PLATE.get()), new ItemStack(ModItems.PACKED_ICE_PRESSURE_PLATE.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BUTTON.get()), new ItemStack(ModItems.PACKED_ICE_BUTTON.get()), visibility);

                // Blue Ice
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_PRESSURE_PLATE.get()), new ItemStack(ModItems.BLUE_ICE_PRESSURE_PLATE.get()), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_BUTTON.get()), new ItemStack(ModItems.BLUE_ICE_BUTTON.get()), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.COMBAT) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.INGREDIENTS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {

            }
        }
    }
}
