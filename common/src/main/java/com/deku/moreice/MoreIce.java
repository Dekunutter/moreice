package com.deku.moreice;

import com.deku.moreice.common.blockEntities.ModBlockEntityType;
import com.deku.moreice.common.blocks.ModBlocks;
import com.deku.moreice.common.items.ModItems;
import com.deku.moreice.common.ui.ModCreativeTabs;
import com.deku.moreice.world.inventory.ModMenuType;
import com.deku.moreice.world.item.crafting.ModRecipeSerializer;
import com.deku.moreice.world.item.crafting.ModRecipeType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoreIce {
    public static final String MOD_ID = "moreice";

    public static final Logger LOGGER = LogManager.getLogger(MoreIce.class);
    public static final boolean HIDE_CONSOLE_NOISE = true;

    public static void init() {
        // Recipes
        ModRecipeType.RECIPE_TYPES.register();
        ModRecipeSerializer.RECIPE_SERIALIZERS.register();

        // Blocks
        ModBlocks.BLOCKS.register();
        ModBlockEntityType.BLOCK_ENTITIES.register();

        // Items
        ModItems.ITEMS.register();

        // Menus
        ModMenuType.MENU_TYPES.register();

        // Creative Mode Tabs
        ModCreativeTabs.CREATIVE_MOD_TABS.register();
    }
}
