package com.deku.moreice;

import com.deku.eastwardjourneys.utils.LogTweaker;
import com.deku.eastwardjourneys.utils.ModConfiguration;
import com.deku.moreice.client.ClientRegistrar;
import com.deku.moreice.common.blockEntities.ModBlockEntityType;
import com.deku.moreice.common.blocks.ModBlockInitializer;
import com.deku.moreice.common.blocks.ModBlockSetType;
import com.deku.moreice.common.items.ModItems;
import com.deku.moreice.common.ui.ModCreativeTabs;
import com.deku.moreice.world.inventory.ModMenuType;
import com.deku.moreice.world.item.crafting.ModRecipeSerializer;
import com.deku.moreice.world.item.crafting.ModRecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
        ModBlockEntityType.BLOCK_ENTITIES.register(eventBus);

        // Menus
        ModMenuType.MENU_TYPES.register(eventBus);

        // Recipes
        ModRecipeType.RECIPE_TYPES.register(eventBus);
        ModRecipeSerializer.RECIPE_SERIALIZERS.register(eventBus);

        // Creative Mode Tabs
        ModCreativeTabs.CREATIVE_MOD_TABS.register(eventBus);

        eventBus.addListener(this::setup);

        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);

        ClientRegistrar clientRegistrar = new ClientRegistrar(eventBus);

        // Register ourselves for server and other game events we are interested in
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.register(this);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientRegistrar::registerClientOnlyEvents);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BlockSetType.register(ModBlockSetType.ICE);
        });
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
                // TODO: Texture for ice bricks
                // TODO: ice sign, ice button, ice door, ice trapdoor, maybe make ice brick variant of the wall (or replace the current wall with an ice brick once since it will likely just look nicer
                // TODO: Consider adding a new form of ice entirely, clear ice?
                // TODO: Add ice cubes as a decoration item? Placed like candles?
                // TODO: Add either a tool to harvest ice blocks, or a freezer for turning buckets of water into 8 ice blocks
                // TODO: Add recipes for crafting ice blocks. Maybe just do the stonecutter?
                // Ice
                registrar.register(new ResourceLocation(MOD_ID, "ice_stairs"), new BlockItem(ModBlockInitializer.ICE_STAIRS.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "ice_slab"), new BlockItem(ModBlockInitializer.ICE_SLAB.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "ice_wall"), new BlockItem(ModBlockInitializer.ICE_WALL.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "ice_bricks"), new BlockItem(ModBlockInitializer.ICE_BRICKS.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "ice_brick_slab"), new BlockItem(ModBlockInitializer.ICE_BRICK_SLAB.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "ice_brick_wall"), new BlockItem(ModBlockInitializer.ICE_BRICK_WALL.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "ice_brick_stairs"), new BlockItem(ModBlockInitializer.ICE_BRICK_STAIRS.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "ice_pressure_plate"), new BlockItem(ModBlockInitializer.ICE_PRESSURE_PLATE.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "ice_button"), new BlockItem(ModBlockInitializer.ICE_BUTTON.get(), new Item.Properties()));

                // Packed Ice
                registrar.register(new ResourceLocation(MOD_ID, "packed_ice_stairs"), new BlockItem(ModBlockInitializer.PACKED_ICE_STAIRS.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "packed_ice_slab"), new BlockItem(ModBlockInitializer.PACKED_ICE_SLAB.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "packed_ice_wall"), new BlockItem(ModBlockInitializer.PACKED_ICE_WALL.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "packed_ice_bricks"), new BlockItem(ModBlockInitializer.PACKED_ICE_BRICKS.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "packed_ice_brick_slab"), new BlockItem(ModBlockInitializer.PACKED_ICE_BRICK_SLAB.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "packed_ice_brick_wall"), new BlockItem(ModBlockInitializer.PACKED_ICE_BRICK_WALL.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "packed_ice_brick_stairs"), new BlockItem(ModBlockInitializer.PACKED_ICE_BRICK_STAIRS.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "packed_ice_pressure_plate"), new BlockItem(ModBlockInitializer.PACKED_ICE_PRESSURE_PLATE.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "packed_ice_button"), new BlockItem(ModBlockInitializer.PACKED_ICE_BUTTON.get(), new Item.Properties()));

                // Blue Ice
                registrar.register(new ResourceLocation(MOD_ID, "blue_ice_stairs"), new BlockItem(ModBlockInitializer.BLUE_ICE_STAIRS.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "blue_ice_slab"), new BlockItem(ModBlockInitializer.BLUE_ICE_SLAB.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "blue_ice_wall"), new BlockItem(ModBlockInitializer.BLUE_ICE_WALL.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "blue_ice_bricks"), new BlockItem(ModBlockInitializer.BLUE_ICE_BRICKS.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "blue_ice_brick_slab"), new BlockItem(ModBlockInitializer.BLUE_ICE_BRICK_SLAB.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "blue_ice_brick_wall"), new BlockItem(ModBlockInitializer.BLUE_ICE_BRICK_WALL.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "blue_ice_brick_stairs"), new BlockItem(ModBlockInitializer.BLUE_ICE_BRICK_STAIRS.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "blue_ice_pressure_plate"), new BlockItem(ModBlockInitializer.BLUE_ICE_PRESSURE_PLATE.get(), new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "blue_ice_button"), new BlockItem(ModBlockInitializer.BLUE_ICE_BUTTON.get(), new Item.Properties()));

                // Misc
                registrar.register(new ResourceLocation(MOD_ID, "freezer"), new BlockItem(ModBlockInitializer.FREEZER.get(), new Item.Properties()));
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
                entries.putAfter(new ItemStack(ModItems.ICE_STAIRS), new ItemStack(ModItems.ICE_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_SLAB), new ItemStack(ModItems.ICE_WALL), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_WALL), new ItemStack(ModItems.ICE_BRICKS), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BRICKS), new ItemStack(ModItems.ICE_BRICK_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BRICK_SLAB), new ItemStack(ModItems.ICE_BRICK_WALL), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BRICK_WALL), new ItemStack(ModItems.ICE_BRICK_STAIRS), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BRICK_STAIRS), new ItemStack(ModItems.ICE_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_PRESSURE_PLATE), new ItemStack(ModItems.ICE_BUTTON), visibility);

                // Packed Ice
                entries.putAfter(new ItemStack(Items.PACKED_ICE), new ItemStack(ModItems.PACKED_ICE_STAIRS), visibility);
                entries.putAfter(new ItemStack(Items.PACKED_ICE), new ItemStack(ModItems.PACKED_ICE_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_SLAB), new ItemStack(ModItems.PACKED_ICE_WALL), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_WALL), new ItemStack(ModItems.PACKED_ICE_BRICKS), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_BRICKS), new ItemStack(ModItems.PACKED_ICE_BRICK_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_BRICK_SLAB), new ItemStack(ModItems.PACKED_ICE_BRICK_WALL), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_BRICK_WALL), new ItemStack(ModItems.PACKED_ICE_BRICK_STAIRS), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_BRICK_STAIRS), new ItemStack(ModItems.PACKED_ICE_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_PRESSURE_PLATE), new ItemStack(ModItems.PACKED_ICE_BUTTON), visibility);

                // Blue Ice
                entries.putAfter(new ItemStack(Items.BLUE_ICE), new ItemStack(ModItems.BLUE_ICE_STAIRS), visibility);
                entries.putAfter(new ItemStack(Items.BLUE_ICE), new ItemStack(ModItems.BLUE_ICE_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.BLUE_ICE_SLAB), new ItemStack(ModItems.BLUE_ICE_WALL), visibility);
                entries.putAfter(new ItemStack(ModItems.BLUE_ICE_WALL), new ItemStack(ModItems.BLUE_ICE_BRICKS), visibility);
                entries.putAfter(new ItemStack(ModItems.BLUE_ICE_BRICKS), new ItemStack(ModItems.BLUE_ICE_BRICK_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.BLUE_ICE_BRICK_SLAB), new ItemStack(ModItems.BLUE_ICE_BRICK_WALL), visibility);
                entries.putAfter(new ItemStack(ModItems.BLUE_ICE_BRICK_WALL), new ItemStack(ModItems.BLUE_ICE_BRICK_STAIRS), visibility);

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                entries.putAfter(new ItemStack(Items.FURNACE), new ItemStack(ModItems.FREEZER), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
                // Ice
                entries.putAfter(new ItemStack(Items.POLISHED_BLACKSTONE_PRESSURE_PLATE), new ItemStack(ModItems.ICE_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(Items.POLISHED_BLACKSTONE_BUTTON), new ItemStack(ModItems.ICE_BUTTON), visibility);

                // Packed Ice
                entries.putAfter(new ItemStack(ModItems.ICE_PRESSURE_PLATE), new ItemStack(ModItems.PACKED_ICE_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.ICE_BUTTON), new ItemStack(ModItems.PACKED_ICE_BUTTON), visibility);

                // Blue Ice
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_PRESSURE_PLATE), new ItemStack(ModItems.BLUE_ICE_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.PACKED_ICE_BUTTON), new ItemStack(ModItems.BLUE_ICE_BUTTON), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.COMBAT) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.INGREDIENTS) {

            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {

            }
        }
    }
}
