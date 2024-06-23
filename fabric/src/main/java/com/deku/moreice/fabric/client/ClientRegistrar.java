package com.deku.moreice.fabric.client;

import com.deku.moreice.client.MoreIceClient;
import com.deku.moreice.common.blocks.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class ClientRegistrar implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MoreIceClient.preInitClient();
        MoreIceClient.initClient();

        setTranslucency();
    }

    /**
     * Sets translucent rendering onto required blocks. Fabric does not do this automatically
     */
    private void setTranslucency() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_STAIRS.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_SLAB.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_WALL.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_BRICKS.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_BRICK_SLAB.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_BRICK_WALL.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_BRICK_STAIRS.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_PRESSURE_PLATE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_BUTTON.get(), RenderType.translucent());
    }
}
