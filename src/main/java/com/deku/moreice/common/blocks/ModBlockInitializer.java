package com.deku.moreice.common.blocks;

import com.deku.moreice.common.blocks.ice.IceStairs;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.moreice.Main.MOD_ID;


public class ModBlockInitializer {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    // ICE
    public static final RegistryObject<Block> ICE_STAIRS = BLOCKS.register("ice_stairs", IceStairs::new);
}
