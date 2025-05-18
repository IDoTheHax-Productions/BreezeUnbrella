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
            BreezeUmbrellaEntity umbrellaEntity = new BreezeUmbrellaEntity(
                    BreezeUmbrella.BREEZE_UMBRELLA_ENTITY,
                    world
            );

            // Set initial position exactly where it should be
            double angle = Math.toRadians(player.getYaw());
            double offsetZ = -0.1;
            double offsetX = -Math.sin(angle) * offsetZ;
            offsetZ = Math.cos(angle) * offsetZ;

            umbrellaEntity.setPosition(
                    player.getX() + offsetX,
                    player.getY() + player.getStandingEyeHeight() + 0.6,
                    player.getZ() + offsetZ
            );

            umbrellaEntity.setOwner(player);
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