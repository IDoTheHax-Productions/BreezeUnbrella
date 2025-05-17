package net.idothehax.breezeunbrella.mixin;

import net.idothehax.breezeunbrella.BreezeUmbrella;
import net.idothehax.breezeunbrella.BreezeUmbrellaItem;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    // Track how long the player has been using the umbrella while falling
    private int umbrellaFallTicks = 0;

    // Maximum horizontal speed allowed with umbrella
    private static final double MAX_HORIZONTAL_SPEED = 0.8;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Check if player is actively using (holding right click with) the umbrella
        if (BreezeUmbrellaItem.isUsingUmbrella(player)) {
            // Handle falling mechanics
            if (!player.isOnGround() && player.getVelocity().y < 0) {
                // Apply slow fall effect
                handleUmbrellaFalling(player);
            } else {
                // Reset counter when not falling
                umbrellaFallTicks = 0;
            }

            // Handle ground movement - prevent excessive speed
            if (player.isOnGround()) {
                limitHorizontalSpeed(player);
            }

            // Protect from rain
            if (player.getWorld().isRaining() && player.getWorld().isSkyVisible(player.getBlockPos())) {
                player.setOnFire(false);
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
        } else {
            // Reset counter when not using umbrella
            umbrellaFallTicks = 0;
        }
    }

    private void handleUmbrellaFalling(PlayerEntity player) {
        // Increment fall counter
        umbrellaFallTicks++;

        // Calculate vertical slowdown factor - starts at 0.1 and gradually increases to 0.5
        // This prevents the player from falling too slowly for extended periods
        double verticalFactor = Math.min(0.1 + (umbrellaFallTicks / 200.0) * 0.4, 0.5);

        // Calculate horizontal boost - decreases over time to prevent excessive travel distance
        double horizontalFactor = Math.max(1.15 - (umbrellaFallTicks / 100.0) * 0.1, 1.0);

        // Apply modified velocity with more reasonable factors
        Vec3d currentVelocity = player.getVelocity();
        player.setVelocity(
                currentVelocity.x * horizontalFactor,
                currentVelocity.y * verticalFactor,
                currentVelocity.z * horizontalFactor
        );

        // Apply speed caps to prevent excessive movement
        limitHorizontalSpeed(player);
    }

    private void limitHorizontalSpeed(PlayerEntity player) {
        Vec3d velocity = player.getVelocity();
        double horizontalSpeed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);

        if (horizontalSpeed > MAX_HORIZONTAL_SPEED) {
            double scaleFactor = MAX_HORIZONTAL_SPEED / horizontalSpeed;
            player.setVelocity(
                    velocity.x * scaleFactor,
                    velocity.y,
                    velocity.z * scaleFactor
            );
        }
    }
}