package net.idothehax.breezeunbrella;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;


public class BreezeUmbrellaItem extends Item {
    private static final float FALL_REDUCTION_FACTOR = 0.4f;
    private static final int MAX_USE_TIME = 72000; // 1 hour in ticks, standard for held items

    public BreezeUmbrellaItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        player.setCurrentHand(hand); // This tells Minecraft the player is using this item
        return TypedActionResult.consume(itemStack); // 'consume' indicates the use action should continue
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPYGLASS; // This gives a nice animation like using a spyglass
    }

    public static int getMaxUseTime() {
        return MAX_USE_TIME;
    }

    // Method to check if the umbrella is being actively used
    public static boolean isUsingUmbrella(PlayerEntity player) {
        ItemStack activeItem = player.getActiveItem();
        return activeItem.getItem() instanceof BreezeUmbrellaItem && player.isUsingItem();
    }
}