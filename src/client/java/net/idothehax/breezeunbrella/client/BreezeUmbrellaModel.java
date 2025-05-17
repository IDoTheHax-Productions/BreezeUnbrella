package net.idothehax.breezeunbrella.client;// Made with Blockbench 4.12.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class BreezeUmbrellaModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart umbrella;
    private final ModelPart handle;
    private final ModelPart canopy;

    public BreezeUmbrellaModel(ModelPart root) {
        this.umbrella = root.getChild("Umbrella");
        this.handle = this.umbrella.getChild("Handle");
        this.canopy = this.handle.getChild("Canopy");
    }

    public static TexturedModelData createModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        // Umbrella root part
        ModelPartData umbrella = partdefinition.addChild(
                "Umbrella",
                ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 26.0F, 0.0F)
        );

        // Handle
        ModelPartData handle = umbrella.addChild(
                "Handle",
                ModelPartBuilder.create()
                        .uv(0, 105).cuboid(-2.0F, -43.0F, 0.0F, 4.0F, 43.0F, 0.0F),
                ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F)
        );

        // handle/cube_r1
        handle.addChild(
                "cube_r1",
                ModelPartBuilder.create()
                        .uv(8, 105).cuboid(-2.0F, -43.0F, 0.0F, 4.0F, 43.0F, 0.0F),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
        );

        // Canopy
        ModelPartData canopy = handle.addChild(
                "Canopy",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-32.0F, -5.0F, -32.0F, 64.0F, 5.0F, 64.0F)
                        .uv(0, 93).cuboid(-16.0F, 0.0F, -16.0F, 32.0F, 6.0F, 0.0F)
                        .uv(0, 87).cuboid(-32.0F, 0.0F, -32.0F, 64.0F, 6.0F, 0.0F)
                        .uv(64, 93).cuboid(-16.0F, 0.0F, 16.0F, 32.0F, 6.0F, 0.0F)
                        .uv(0, 81).cuboid(-32.0F, 0.0F, 32.0F, 64.0F, 6.0F, 0.0F),
                ModelTransform.of(0.0F, -43.0F, 0.0F, -0.2182F, 0.0F, 0.0F)
        );

        // canopy/cube_r2
        canopy.addChild(
                "cube_r2",
                ModelPartBuilder.create()
                        .uv(0, 75).cuboid(-32.0F, -2.0F, -1.0F, 64.0F, 6.0F, 0.0F),
                ModelTransform.of(31.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
        );

        // canopy/cube_r3
        canopy.addChild(
                "cube_r3",
                ModelPartBuilder.create()
                        .uv(0, 69).cuboid(-32.0F, -2.0F, -1.0F, 64.0F, 6.0F, 0.0F),
                ModelTransform.of(-33.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
        );

        // canopy/cube_r4
        canopy.addChild(
                "cube_r4",
                ModelPartBuilder.create()
                        .uv(64, 99).cuboid(-16.0F, -2.0F, -1.0F, 32.0F, 6.0F, 0.0F),
                ModelTransform.of(-17.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
        );

        // canopy/cube_r5
        canopy.addChild(
                "cube_r5",
                ModelPartBuilder.create()
                        .uv(0, 99).cuboid(-16.0F, -2.0F, -1.0F, 32.0F, 6.0F, 0.0F),
                ModelTransform.of(15.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
        );

        return TexturedModelData.of(meshdefinition, 256, 256);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // No animation
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        umbrella.render(matrices, vertices, light, overlay, color);
    }
}