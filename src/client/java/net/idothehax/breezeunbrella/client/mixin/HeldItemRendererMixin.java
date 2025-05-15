package net.idothehax.breezeunbrella.client.mixin;

import net.idothehax.breezeunbrella.BreezeUmbrellaItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
    private void onRenderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta,
                                         float pitch, Hand hand, float swingProgress,
                                         ItemStack item, float equipProgress,
                                         MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                         int light, CallbackInfo ci) {
        // Check if the player is actively using the BreezeUmbrella item
        if (item.getItem() instanceof BreezeUmbrellaItem && player.isUsingItem()
                && player.getActiveItem() == item) {
            // Cancel the rendering of the item
            ci.cancel();
        }
    }
}
