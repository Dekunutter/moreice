package com.deku.moreice.client.models.geom;

import com.deku.moreice.client.renderer.blockentity.FreezerRenderer;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class ModLayerDefinition {
    public static LayerDefinition FREEZER_LAYER = FreezerRenderer.createSingleBodyLayer();
}
