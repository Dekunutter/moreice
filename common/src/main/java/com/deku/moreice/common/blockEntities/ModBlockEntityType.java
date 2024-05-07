package com.deku.moreice.common.blockEntities;

import com.deku.moreice.common.blocks.ModBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

import static com.deku.moreice.MoreIce.MOD_ID;

public class ModBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final Supplier<BlockEntityType<FreezerBlockEntity>> FREEZER_BLOCK_ENTITY = BLOCK_ENTITIES.register("freezer_block_entity", () -> BlockEntityType.Builder.of(FreezerBlockEntity::new, ModBlocks.FREEZER.get()).build(null));
}
