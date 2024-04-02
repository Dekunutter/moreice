package com.deku.moreice.common.blocks;

import com.deku.moreice.common.blockEntities.FreezerBlockEntity;
import com.deku.moreice.common.blockEntities.ModBlockEntityType;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static com.deku.moreice.common.blocks.ModBlockStateProperties.COOLING;
import static com.deku.moreice.common.blocks.ModBlockStateProperties.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class Freezer extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 14, 15);

    public Freezer() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F));
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(COOLING, Boolean.valueOf(false)).setValue(WATERLOGGED, false));
    }

    public Freezer(BlockBehaviour.Properties properties) {
        super(properties);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext collisionContext) {
        return SHAPE;
    }

    /**
     * Opens the menu when player interacts with the block
     *
     * @param state
     * @param level
     * @param position
     * @param player
     * @param hand
     * @param hitResult
     * @return
     */
    public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            this.openContainer(level, position, player);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    protected void openContainer(Level level, BlockPos position, Player player) {
        BlockEntity blockentity = level.getBlockEntity(position);
        if (blockentity instanceof FreezerBlockEntity) {
            player.openMenu((MenuProvider) blockentity);
            // TODO: In 1.20.1 I would use NetworkHooks here. That no longer exists. Do I need to do something here to communicate to the server that someone opened the freezer?
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        FluidState fluidState = placeContext.getLevel().getFluidState(placeContext.getClickedPos());
        return defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    public void setPlacedBy(Level level, BlockPos position, BlockState state, LivingEntity entity, ItemStack itemStack) {
        if (itemStack.hasCustomHoverName()) {
            BlockEntity blockentity = level.getBlockEntity(position);
            if (blockentity instanceof FreezerBlockEntity) {
                ((FreezerBlockEntity) blockentity).setCustomName(itemStack.getHoverName());
            }
        }
    }

    /**
     * Makes sure the freezer drops its contents when broken so items within are not lost
     * Any recipes that completed freezing items will rightfully award experience orbs too
     *
     * @param state
     * @param level
     * @param position
     * @param otherState
     * @param isMoving
     */
    public void onRemove(BlockState state, Level level, BlockPos position, BlockState otherState, boolean isMoving) {
        if (!state.is(otherState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(position);
            if (blockentity instanceof FreezerBlockEntity) {
                if (level instanceof ServerLevel) {
                    Containers.dropContents(level, position, (FreezerBlockEntity) blockentity);
                    ((FreezerBlockEntity) blockentity).getRecipesToAwardAndPopExperience((ServerLevel) level, Vec3.atCenterOf(position));
                }

                super.onRemove(state, level, position, otherState, isMoving);
                level.updateNeighbourForOutputSignal(position, this);
            } else {
                super.onRemove(state, level, position, otherState, isMoving);
            }
        }
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition) {
        stateDefinition.add(FACING, COOLING, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor levelAccessor, BlockPos position, BlockPos otherPosition) {
        if (state.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(position, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(state, direction, otherState, levelAccessor, position, otherPosition);
    }

    public boolean isPathfindable(BlockState state, BlockGetter blockGetter, BlockPos position, PathComputationType pathingType) {
        return false;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
        return new FreezerBlockEntity(position, state);
    }

    /**
     * Sets a ticker to run on the server that will process any active freezing items within
     *
     * @param level
     * @param state
     * @param entityType
     * @return
     * @param <T>
     */
    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return level.isClientSide ? createTickerHelper(entityType, ModBlockEntityType.FREEZER_BLOCK_ENTITY.get(), (clientLevel, position, blockState, entity) -> FreezerBlockEntity.clientTick(clientLevel, position, blockState, entity)) : createTickerHelper(entityType, ModBlockEntityType.FREEZER_BLOCK_ENTITY.get(), (serverLevel, position, blockState, entity) -> FreezerBlockEntity.serverTick(serverLevel, position, blockState, entity));
    }

    public void tick(BlockState state, ServerLevel level, BlockPos position, RandomSource random) {
        BlockEntity blockentity = level.getBlockEntity(position);
        if (blockentity instanceof FreezerBlockEntity) {
            ((FreezerBlockEntity) blockentity).recheckOpen();
        }

    }

//
//    public void animateTick(BlockState p_221253_, Level p_221254_, BlockPos p_221255_, RandomSource p_221256_) {
//        if (p_221253_.getValue(LIT)) {
//            double d0 = (double)p_221255_.getX() + 0.5D;
//            double d1 = (double)p_221255_.getY();
//            double d2 = (double)p_221255_.getZ() + 0.5D;
//            if (p_221256_.nextDouble() < 0.1D) {
//                p_221254_.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
//            }
//
//            Direction direction = p_221253_.getValue(FACING);
//            Direction.Axis direction$axis = direction.getAxis();
//            double d3 = 0.52D;
//            double d4 = p_221256_.nextDouble() * 0.6D - 0.3D;
//            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d4;
//            double d6 = p_221256_.nextDouble() * 6.0D / 16.0D;
//            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d4;
//            p_221254_.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
//            p_221254_.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
//        }
//    }
}
