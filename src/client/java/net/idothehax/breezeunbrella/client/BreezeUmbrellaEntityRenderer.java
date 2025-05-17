package net.idothehax.breezeunbrella.client;

import net.idothehax.breezeunbrella.BreezeUmbrella;
import net.idothehax.breezeunbrella.BreezeUmbrellaEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.VertexConsumerProvider;

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
        // Optionally: matrices.translate(0, -1.5, 0); // adjust position
        this.model.render(matrices, vertexConsumers.getBuffer(this.model.getLayer(TEXTURE)), light,
                net.minecraft.client.render.OverlayTexture.DEFAULT_UV, 1);
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