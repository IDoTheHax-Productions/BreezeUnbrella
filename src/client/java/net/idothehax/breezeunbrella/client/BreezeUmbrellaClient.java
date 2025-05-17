package net.idothehax.breezeunbrella.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.idothehax.breezeunbrella.BreezeUmbrellaItem;
import net.minecraft.client.MinecraftClient;

public class BreezeUmbrellaClient implements ClientModInitializer {

    private static BreezeUmbrellaRenderer umbrellaRenderer;

    @Override
    public void onInitializeClient() {
        umbrellaRenderer = new BreezeUmbrellaRenderer(); // Remove ItemRenderer parameter

        // Register rendering event
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                client.player.getInventory().main.stream()
                        .filter(stack -> stack.getItem() instanceof BreezeUmbrellaItem)
                        .findFirst()
                        .ifPresent(stack -> umbrellaRenderer.render(
                                client.player,
                                stack,
                                context.matrixStack(),
                                context.consumers(),
                                15728880,
                                1.0f     // Fixed tick delta
                        ));
            }
        });
    }
}
