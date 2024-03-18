package com.deku.moreice.common.blocks;

import com.deku.moreice.common.blocks.ice.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.moreice.Main.MOD_ID;


public class ModBlockInitializer {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    // ICE
    public static final RegistryObject<Block> ICE_STAIRS = BLOCKS.register("ice_stairs", IceStairs::new);
    public static final RegistryObject<Block> ICE_SLAB = BLOCKS.register("ice_slab", IceSlab::new);

    // PACKED ICE
    public static final RegistryObject<Block> PACKED_ICE_STAIRS = BLOCKS.register("packed_ice_stairs", PackedIceStairs::new);
    public static final RegistryObject<Block> PACKED_ICE_SLAB = BLOCKS.register("packed_ice_slab", PackedIceSlab::new);

    // BLUE ICE
    public static final RegistryObject<Block> BLUE_ICE_STAIRS = BLOCKS.register("blue_ice_stairs", BlueIceStairs::new);
    public static final RegistryObject<Block> BLUE_ICE_SLAB = BLOCKS.register("blue_ice_slab", BlueIceSlab::new);
}
