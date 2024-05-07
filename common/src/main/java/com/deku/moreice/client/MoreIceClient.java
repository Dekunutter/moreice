package com.deku.moreice.client;

import com.deku.moreice.client.models.geom.ModLayerDefinition;
import com.deku.moreice.client.models.geom.ModModelLayerDefinition;
import com.deku.moreice.client.gui.screens.inventory.FreezerScreen;
import com.deku.moreice.client.renderer.blockentity.FreezerRenderer;
import com.deku.moreice.common.blockEntities.ModBlockEntityType;
import com.deku.moreice.world.inventory.ModMenuType;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.menu.MenuRegistry;

public class MoreIceClient {

    public static void preInitClient() {
        registerEntityLayers();
    }
    public static void initClient() {
        registerMenuScreens();
        registerBlockEntityRenderers();
    }

    private static void registerMenuScreens() {
        MenuRegistry.registerScreenFactory(ModMenuType.FREEZER.get(), FreezerScreen::new);
    }

    private static void registerBlockEntityRenderers() {
        BlockEntityRendererRegistry.register(ModBlockEntityType.FREEZER_BLOCK_ENTITY.get(), FreezerRenderer::new);
    }

    private static void registerEntityLayers() {
        EntityModelLayerRegistry.register(ModModelLayerDefinition.FREEZER, () -> ModLayerDefinition.FREEZER_LAYER);
    }
}
