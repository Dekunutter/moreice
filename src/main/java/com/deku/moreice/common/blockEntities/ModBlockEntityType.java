package com.deku.moreice.common.blockEntities;

import com.deku.moreice.common.blocks.ModBlockInitializer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.moreice.Main.MOD_ID;

public class ModBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<BlockEntityType<FreezerBlockEntity>> FREEZER_BLOCK_ENTITY = BLOCK_ENTITIES.register("freezer_block_entity", () -> BlockEntityType.Builder.of(FreezerBlockEntity::new, ModBlockInitializer.FREEZER.get()).build(null));
}
