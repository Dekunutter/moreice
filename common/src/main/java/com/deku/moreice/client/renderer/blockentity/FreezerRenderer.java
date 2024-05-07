package com.deku.moreice.client.renderer.blockentity;

import com.deku.moreice.client.models.geom.ModModelLayerDefinition;
import com.deku.moreice.common.blocks.ModBlockStateProperties;
import com.deku.moreice.common.blocks.ModBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.deku.moreice.MoreIce.MOD_ID;

public class FreezerRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
    private static final String BOTTOM = "container";
    private static final String LID = "lid";

    private final ModelPart lid;
    private final ModelPart bottom;

    public FreezerRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelpart = context.bakeLayer(ModModelLayerDefinition.FREEZER);
        this.bottom = modelpart.getChild("container");
        this.lid = modelpart.getChild("lid");
    }

    public static LayerDefinition createSingleBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition container = partdefinition.addOrReplaceChild("container", CubeListBuilder.create().texOffs(0, 30).addBox(-13.0F, -2.0F, 3.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(32, 18).addBox(-3.0F, -10.0F, 3.0F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-15.0F, -10.0F, 3.0F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(24, 42).addBox(-15.0F, -10.0F, 13.0F, 14.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-15.0F, -10.0F, 1.0F, 14.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

        PartDefinition ice = container.addOrReplaceChild("ice", CubeListBuilder.create().texOffs(20, 58).addBox(-13.0F, -5.0F, 3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 18).addBox(-13.0F, -6.0F, 7.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(36, 56).addBox(-11.0F, -4.0F, 7.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(44, 0).addBox(-6.0F, -5.0F, 11.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 5).addBox(-6.0F, -7.0F, 3.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -3.0F, 3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(-6.0F, -6.0F, 7.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        final PartDefinition lid = partdefinition.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -14.0F, 1.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    // TODO: This renderer is not whats applying the offset?? Maybe its the partPose values
    public void render(T entity, float yaw, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int combinedOverlay) {
        Level level = entity.getLevel();
        boolean flag = level != null;
        BlockState blockstate = flag ? entity.getBlockState() : ModBlocks.FREEZER.get().defaultBlockState().setValue(ModBlockStateProperties.FACING, Direction.SOUTH);

        poseStack.pushPose();

        float f = blockstate.getValue(ModBlockStateProperties.FACING).toYRot();

        // NOTE: Seems blockbench models can come out a bit offset for thin blocks like this, so I had to modify the position a bit to get it to display correctly, as well as rotate it
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        float openness = entity.getOpenNess(1);
        openness = 1.0F - openness;
        openness = 1.0F - openness * openness * openness;
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
        this.render(poseStack, vertexconsumer, this.lid, this.bottom, openness, packedLight, combinedOverlay);

        poseStack.popPose();
    }

    private void render(PoseStack poseStack, VertexConsumer vertexConsumer, ModelPart lid, ModelPart bottom, float openness, int packedLight, int combinedOverlay) {
        // NOTE: Just swinging the freezer open on the Y axis since the X axis rotation to open like a chest wouldn't work. Couldn't figure out how to have a blockbench model have a specific pivot point for rotation without moving the entire model position
        lid.yRot = -((openness / 2) * ((float)Math.PI / 2F));
        lid.render(poseStack, vertexConsumer, packedLight, combinedOverlay);
        bottom.render(poseStack, vertexConsumer, packedLight, combinedOverlay);
    }

    public ResourceLocation getTextureLocation(T entity) {
        return new ResourceLocation(MOD_ID, "textures/entity/freezer.png");
    }
}

