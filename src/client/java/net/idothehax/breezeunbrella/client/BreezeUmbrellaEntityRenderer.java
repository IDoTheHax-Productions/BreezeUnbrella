package net.idothehax.breezeunbrella.client;

import net.idothehax.breezeunbrella.BreezeUmbrellaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.util.Identifier;
import net.idothehax.breezeunbrella.BreezeUmbrella;

public class BreezeUmbrellaEntityRenderer extends LivingEntityRenderer<BreezeUmbrellaEntity, BreezeUmbrellaModel> {
    private static final Identifier TEXTURE = Identifier.of(BreezeUmbrella.MOD_ID, "textures/entity/umbrella.png");

    public BreezeUmbrellaEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BreezeUmbrellaModel(context.getPart(BreezeUmbrellaClient.UMBRELLA_MODEL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(BreezeUmbrellaEntity entity) {
        return TEXTURE;
    }
}