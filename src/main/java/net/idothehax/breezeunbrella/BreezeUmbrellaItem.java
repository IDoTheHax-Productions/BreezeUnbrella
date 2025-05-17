package net.idothehax.breezeunbrella;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;

public class BreezeUmbrellaItem extends Item {
    private static final float FALL_REDUCTION_FACTOR = 0.4f;
    private static final int MAX_USE_TIME = 72000;

    public BreezeUmbrellaItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (!world.isClient) {
            // Create the umbrella entity
            BreezeUmbrellaEntity umbrellaEntity = new BreezeUmbrellaEntity(
                    BreezeUmbrella.BREEZE_UMBRELLA_ENTITY,
                    world
            );

            // Position the umbrella slightly above and behind the player
            Vec3d pos = player.getPos();
            double offsetY = 2.0; // Adjust this value to change how high above the player it appears
            double offsetZ = -0.5; // Adjust this to change how far behind the player it appears

            // Calculate position with player's rotation
            double angle = Math.toRadians(player.getYaw());
            double offsetX = -Math.sin(angle) * offsetZ;
            offsetZ = Math.cos(angle) * offsetZ;

            umbrellaEntity.setPos(
                    pos.x + offsetX,
                    pos.y + offsetY,
                    pos.z + offsetZ
            );

            // Link the umbrella to the player
            umbrellaEntity.setOwner(player);

            // Spawn the entity in the world
            world.spawnEntity(umbrellaEntity);
        }

        player.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPYGLASS;
    }

    public static int getMaxUseTime() {
        return MAX_USE_TIME;
    }

    public static boolean isUsingUmbrella(PlayerEntity player) {
        ItemStack activeItem = player.getActiveItem();
        return activeItem.getItem() instanceof BreezeUmbrellaItem && player.isUsingItem();
    }
}