package com.deku.moreice.common.blockEntities;

import com.deku.moreice.common.blocks.ModBlockInitializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.deku.moreice.Main.MOD_ID;

public class ModBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    public static final Supplier<BlockEntityType<FreezerBlockEntity>> FREEZER_BLOCK_ENTITY = BLOCK_ENTITIES.register("freezer_block_entity", () -> BlockEntityType.Builder.of(FreezerBlockEntity::new, ModBlockInitializer.FREEZER.get()).build(null));
}
