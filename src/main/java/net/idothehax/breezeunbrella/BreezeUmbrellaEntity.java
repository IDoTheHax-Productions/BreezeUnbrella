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
                // Remove the umbrella if the owner is gone or stops using the item
                discard();
                return;
            }

            // Update position to follow owner
            Vec3d ownerPos = owner.getPos();
            double angle = Math.toRadians(owner.getYaw());
            double offsetZ = -0.5;
            double offsetX = -Math.sin(angle) * offsetZ;
            offsetZ = Math.cos(angle) * offsetZ;

            setPosition(
                    ownerPos.x + offsetX,
                    ownerPos.y + 2.0,
                    ownerPos.z + offsetZ
            );
        }
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