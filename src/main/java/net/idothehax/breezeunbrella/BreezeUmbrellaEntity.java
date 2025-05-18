package net.idothehax.breezeunbrella;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.nbt.NbtCompound;
import java.util.UUID;

public class BreezeUmbrellaEntity extends LivingEntity {
    private UUID ownerUUID;
    private PlayerEntity owner;

    public BreezeUmbrellaEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true; // Allow it to pass through blocks
    }

    public static DefaultAttributeContainer.Builder createUmbrellaAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

    public void setOwner(PlayerEntity player) {
        this.owner = player;
        this.ownerUUID = player.getUuid();
    }

    public PlayerEntity getOwner() {
        if (owner == null && ownerUUID != null && !getWorld().isClient) {
            // Try to find the owner by UUID
            this.owner = (PlayerEntity) ((ServerWorld) getWorld()).getEntity(ownerUUID);
        }
        return owner;
    }

    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient) {
            PlayerEntity owner = getOwner();

            if (owner == null || !owner.isAlive() || !owner.isUsingItem()) {
                discard();
                return;
            }

            // Calculate the target position relative to the player
            double angle = Math.toRadians(owner.getYaw());
            double offsetZ = -0.5;
            double offsetX = -Math.sin(angle) * offsetZ;
            offsetZ = Math.cos(angle) * offsetZ;

            double targetX = owner.getX() + offsetX;
            double targetY = owner.getY() + owner.getStandingEyeHeight() + 0.6;
            double targetZ = owner.getZ() + offsetZ;

            // Set position directly for server-side
            setPosition(targetX, targetY, targetZ);

            // Send position updates to clients more frequently
            this.setVelocity(0, 0, 0); // Prevent physics from affecting the umbrella
            this.velocityModified = true;
        } else {
            // Client-side interpolation
            this.prevX = this.getX();
            this.prevY = this.getY();
            this.prevZ = this.getZ();
        }
    }

    // Add these methods to disable physics and collision
    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }
    @Override
    public Arm getMainArm() {
        return null;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (ownerUUID != null) {
            nbt.putUuid("Owner", ownerUUID);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Owner")) {
            ownerUUID = nbt.getUuid("Owner");
        }
    }

    // Required overrides from LivingEntity
    @Override
    public Iterable<ItemStack> getArmorItems() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

}