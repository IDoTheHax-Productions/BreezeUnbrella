package net.idothehax.breezeunbrella.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.idothehax.breezeunbrella.BreezeUmbrella;
import net.idothehax.breezeunbrella.BreezeUmbrellaItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.Identifier;

public class BreezeUmbrellaClient implements ClientModInitializer {
    public static final EntityModelLayer UMBRELLA_MODEL_LAYER =
            new EntityModelLayer(Identifier.of(BreezeUmbrella.MOD_ID, "umbrella"), "main");

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(
                UMBRELLA_MODEL_LAYER,
                BreezeUmbrellaModel::createModelData
        );
        EntityRendererRegistry.register(
                BreezeUmbrella.BREEZE_UMBRELLA_ENTITY,
                ctx -> new BreezeUmbrellaEntityRenderer(ctx)
        );
    }
}