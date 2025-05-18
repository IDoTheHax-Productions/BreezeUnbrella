package net.idothehax.breezeunbrella.client;

import net.idothehax.breezeunbrella.BreezeUmbrella;
import net.idothehax.breezeunbrella.BreezeUmbrellaEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;

public class BreezeUmbrellaEntityRenderer extends EntityRenderer<BreezeUmbrellaEntity> {
    private static final Identifier TEXTURE = Identifier.of(BreezeUmbrella.MOD_ID, "textures/entity/umbrella.png");
    private final BreezeUmbrellaModel<BreezeUmbrellaEntity> model;

    public BreezeUmbrellaEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new BreezeUmbrellaModel<>(ctx.getPart(BreezeUmbrellaClient.UMBRELLA_MODEL_LAYER));
    }

    @Override
    public void render(BreezeUmbrellaEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(-1.0F, -1.0F, 1.0F);
        // Get the correct vertex consumer for the textured entity
        var vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(getTexture(entity)));
        this.model.render(matrices, vertexConsumers.getBuffer(model.getLayer(this.getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(BreezeUmbrellaEntity entity) {
        return TEXTURE;
    }

    // Hide the nameplate
    @Override
    protected boolean hasLabel(BreezeUmbrellaEntity entity) {
        return false;
    }
}