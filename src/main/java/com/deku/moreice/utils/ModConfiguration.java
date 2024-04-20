package com.deku.moreice.utils;


import net.neoforged.neoforge.common.ModConfigSpec;

public final class ModConfiguration {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec COMMON_SPEC;
    static {
        BUILDER.comment("------ More Ice General Settings ------").push("more_ice");
        BUILDER.pop();

        COMMON_SPEC = BUILDER.build();
    }
}
