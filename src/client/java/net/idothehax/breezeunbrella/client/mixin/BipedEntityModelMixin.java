package net.idothehax.breezeunbrella.client.mixin;

import net.idothehax.breezeunbrella.BreezeUmbrellaItem;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin<T extends LivingEntity> {
    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    public void setAngles(T entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (entity instanceof AbstractClientPlayerEntity player && BreezeUmbrellaItem.isUsingUmbrella(player)) {
            // Reset arm animations
            this.rightArm.roll = 0.0F;
            this.rightArm.yaw = 0.0F;
            this.leftArm.roll = 0.0F;
            this.leftArm.yaw = 0.0F;

            // Set arms straight up
            this.rightArm.pitch = (float) Math.toRadians(-180.0f); // Straight up
            this.leftArm.pitch = (float) Math.toRadians(-180.0f);

            // Add slight outward angle
            this.rightArm.yaw = (float) Math.toRadians(15.0f);
            this.leftArm.yaw = (float) Math.toRadians(-15.0f);

            // Add gentle sway
            float sway = MathHelper.sin(h * 0.1f) * 0.05f;
            this.rightArm.roll = sway;
            this.leftArm.roll = -sway;
        }
    }
}
