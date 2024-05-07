package com.deku.moreice.forge.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfiguration {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        BUILDER.comment("------ More Ice General Settings ------").push("more_ice");
        BUILDER.pop();

        COMMON_SPEC = BUILDER.build();
    }
}
