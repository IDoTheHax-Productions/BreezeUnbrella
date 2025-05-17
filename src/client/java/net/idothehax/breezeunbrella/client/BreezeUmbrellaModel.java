package net.idothehax.breezeunbrella.client;

import net.idothehax.breezeunbrella.BreezeUmbrellaEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class BreezeUmbrellaModel extends EntityModel<BreezeUmbrellaEntity> {
    private final ModelPart umbrella;

    public BreezeUmbrellaModel(ModelPart root) {
        this.umbrella = root.getChild("umbrella");
    }

    public static TexturedModelData createModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        // Simple cube for testing, replace with your real model later
        root.addChild("umbrella", ModelPartBuilder.create()
                        .uv(0,0).cuboid(-8.0F, -2.0F, -8.0F, 16.0F, 2.0F, 16.0F),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(BreezeUmbrellaEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // No animation
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        umbrella.render(matrices, vertices, light, overlay);
    }
}