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
            // Adjust arms to look natural when holding umbrella
            float holdingAngle = -160.0f;
            this.rightArm.pitch = (float) Math.toRadians(holdingAngle);
            this.leftArm.pitch = (float) Math.toRadians(holdingAngle);

            // Add slight outward angle
            this.rightArm.yaw = (float) Math.toRadians(15.0f);
            this.leftArm.yaw = (float) Math.toRadians(-15.0f);

            // Add natural arm sway
            float swaySpeed = 0.05f;
            float swayAmount = 5.0f;
            float sway = MathHelper.sin(player.age * swaySpeed) * swayAmount;

            this.rightArm.roll = (float) Math.toRadians(sway);
            this.leftArm.roll = (float) Math.toRadians(-sway);
        }
    }
}