package net.idothehax.breezeunbrella.client.mixin;

import net.idothehax.breezeunbrella.BreezeUmbrellaItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin<T extends LivingEntity> {

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    public void setAngles(T entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (entity instanceof AbstractClientPlayerEntity player && BreezeUmbrellaItem.isUsingUmbrella(player)) {
            PlayerEntityModel<T> model = (PlayerEntityModel<T>) (Object) this;

            // Ensure arms stay in position
            model.rightArm.pitch = (float) Math.toRadians(-180.0f);
            model.leftArm.pitch = (float) Math.toRadians(-180.0f);

            model.rightArm.yaw = (float) Math.toRadians(15.0f);
            model.leftArm.yaw = (float) Math.toRadians(-15.0f);

            // Also set the sleeve positions
            if (model instanceof PlayerEntityModel) {
                PlayerEntityModel<?> playerModel = (PlayerEntityModel<?>) model;
                playerModel.rightSleeve.copyTransform(model.rightArm);
                playerModel.leftSleeve.copyTransform(model.leftArm);
            }
        }
    }
}