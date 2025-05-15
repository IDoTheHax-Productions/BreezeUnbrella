package net.idothehax.breezeunbrella.mixin;

import net.idothehax.breezeunbrella.BreezeUmbrella;
import net.idothehax.breezeunbrella.BreezeUmbrellaItem;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Check if player is actively using (holding right click with) the umbrella
        if (BreezeUmbrellaItem.isUsingUmbrella(player)) {
            // Slow fall if player is falling
            if (!player.isOnGround() && player.getVelocity().y < 0) {
                player.setVelocity(player.getVelocity().multiply(3, 0.3, 3));
            }

            // Protect from rain
            if (player.getWorld().isRaining() && player.getWorld().isSkyVisible(player.getBlockPos())) {
                player.setOnFire(false);
                // Add Dolphin's Grace effect for 3 seconds (60 ticks) with amplifier 0
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.DOLPHINS_GRACE,
                        20,
                        0,
                        false,
                        false,
                        true
                ));

                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.RESISTANCE,
                        20,
                        1,
                        false,
                        false,
                        true
                ));

                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.WIND_CHARGED,
                        20,
                        1,
                        false,
                        false,
                        true
                ));
            }
        }
    }
}