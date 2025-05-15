package net.idothehax.breezeunbrella.client;

import net.idothehax.breezeunbrella.BreezeUmbrellaItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class BreezeUmbrellaRenderer {
    private final MinecraftClient client;

    public BreezeUmbrellaRenderer() {
        this.client = MinecraftClient.getInstance();
    }

    public void render(PlayerEntity player, ItemStack stack, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, float tickDelta) {
        if (!BreezeUmbrellaItem.isUsingUmbrella(player) || client.player == null) {
            return;
        }

        ItemRenderer itemRenderer = client.getItemRenderer();
        if (itemRenderer == null) {
            return;
        }

        matrices.push();

        // Position the umbrella at hand height
        float handHeight = player.getStandingEyeHeight() - 0.2f; // Slightly below eye level
        matrices.translate(0, handHeight, 0);

        // Rotate based on player movement
        float yaw = player.getYaw(tickDelta);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-yaw));

        // Add slight bobbing motion synchronized with arm sway
        float swaySpeed = 0.1f;
        float bobbingAmount = 0.025f; // Reduced bobbing amount
        float bobbing = (float) Math.sin(player.age * swaySpeed) * bobbingAmount;
        matrices.translate(0, bobbing, 0);

        // Render the umbrella model
        itemRenderer.renderItem(
                player,
                stack,
                ModelTransformationMode.FIXED,
                false,
                matrices,
                vertexConsumers,
                player.getWorld(),
                light,
                0,
                0
        );

        matrices.pop();
    }
}