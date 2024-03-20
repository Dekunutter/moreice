package com.deku.moreice.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import static com.deku.moreice.Main.MOD_ID;

public class ModBlockSetType {
    public static BlockSetType ICE = new BlockSetType(new ResourceLocation(MOD_ID, "ice").toString(), false, false, false, BlockSetType.PressurePlateSensitivity.EVERYTHING, SoundType.AMETHYST, SoundEvents.COPPER_DOOR_CLOSE, SoundEvents.COPPER_DOOR_OPEN, SoundEvents.COPPER_TRAPDOOR_CLOSE, SoundEvents.COPPER_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON);
    public static BlockSetType BLUE_ICE = new BlockSetType(new ResourceLocation(MOD_ID, "blue_ice").toString(), false, false, false, BlockSetType.PressurePlateSensitivity.EVERYTHING, SoundType.AMETHYST, SoundEvents.COPPER_DOOR_CLOSE, SoundEvents.COPPER_DOOR_OPEN, SoundEvents.COPPER_TRAPDOOR_CLOSE, SoundEvents.COPPER_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON);
    public static BlockSetType PACKED_ICE = new BlockSetType(new ResourceLocation(MOD_ID, "packed_ice").toString(), false, false, false, BlockSetType.PressurePlateSensitivity.EVERYTHING, SoundType.AMETHYST, SoundEvents.COPPER_DOOR_CLOSE, SoundEvents.COPPER_DOOR_OPEN, SoundEvents.COPPER_TRAPDOOR_CLOSE, SoundEvents.COPPER_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON);
}
