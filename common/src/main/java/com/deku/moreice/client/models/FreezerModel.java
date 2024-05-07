package com.deku.moreice.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class FreezerModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "freezer_converted"), "main");
    private final ModelPart container;
    private final ModelPart lid;

    public FreezerModel(ModelPart root) {
        this.container = root.getChild("container");
        this.lid = root.getChild("lid");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition container = partdefinition.addOrReplaceChild("container", CubeListBuilder.create().texOffs(0, 30).addBox(-13.0F, -2.0F, 3.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(32, 18).addBox(-3.0F, -10.0F, 3.0F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-15.0F, -10.0F, 3.0F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(24, 42).addBox(-15.0F, -10.0F, 13.0F, 14.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-15.0F, -10.0F, 1.0F, 14.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

        PartDefinition lid = partdefinition.addOrReplaceChild("lid", CubeListBuilder.create(), PartPose.offset(8.0F, 24.0F, -8.0F));

        PartDefinition cube_r1 = lid.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -4.0F, -14.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -10.0F, 15.0F, -1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        container.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        lid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
