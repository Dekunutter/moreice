package com.deku.moreice.client.models.geom;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static com.deku.moreice.Main.MOD_ID;

public class ModModelLayerDefinition {
    public static ModelLayerLocation FREEZER = ModModelLayerDefinition.getLayerLocation("freezer", "main");

    public static ModelLayerLocation getLayerLocation(String layerName, String part) {
        return new ModelLayerLocation(new ResourceLocation(MOD_ID, layerName), part);
    }
}
