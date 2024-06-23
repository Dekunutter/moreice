package com.deku.moreice.fabric;

import com.deku.moreice.MoreIce;
import net.fabricmc.api.ModInitializer;


public class Main implements ModInitializer
{
    @Override
    public void onInitialize() {
        MoreIce.init();
        //registerFuelBlocks();
    }
}
