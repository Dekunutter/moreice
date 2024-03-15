package com.deku.eastwardjourneys.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ModConfiguration {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_SPEC;
    
    public static final ForgeConfigSpec.ConfigValue<Boolean> spawnCherryBlossomBiomes;

    static {
        BUILDER.comment("------ Eastward Journeys General Settings ------").push("eastward_journeys");
            BUILDER.push("biomes");
                spawnCherryBlossomBiomes = BUILDER.comment("Whether cherry blossom biomes should spawn").define("cherryBlossomBiomes", true);
            BUILDER.pop();
        BUILDER.pop();

        COMMON_SPEC = BUILDER.build();
    }
}
