package com.deku.moreice.forge;

import com.deku.moreice.MoreIce;
import com.deku.moreice.common.blocks.ModBlockSetType;
import com.deku.moreice.common.items.ModItems;
import com.deku.moreice.forge.client.ClientRegistrar;
import com.deku.moreice.forge.utils.ModConfiguration;
import com.deku.moreice.utils.LogTweaker;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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
import org.apache.logging.log4j.Level;

import java.util.stream.Collectors;

import static com.deku.moreice.MoreIce.*;
import static com.deku.moreice.MoreIce.LOGGER;

@Mod(MOD_ID)
public class Main
{
    public Main() {
        System.out.println("STARTING EXECUTION");

        if (HIDE_CONSOLE_NOISE) {
            LogTweaker.applyLogFilterLevel(Level.WARN);
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfiguration.COMMON_SPEC, "moreice-common.toml");

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(MOD_ID, eventBus);

        MoreIce.init();

        eventBus.addListener(this::setup);

        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);

        ClientRegistrar clientRegistrar = new ClientRegistrar(eventBus);

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
