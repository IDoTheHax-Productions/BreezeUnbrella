package net.idothehax.breezeunbrella;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreezeUmbrella implements ModInitializer {
    public static final String MOD_ID = "breeze_umbrella";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final BreezeUmbrellaItem BREEZE_UMBRELLA = new BreezeUmbrellaItem(
            new Item.Settings().maxCount(1)
    );

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "breeze_umbrella"), BREEZE_UMBRELLA);
        LOGGER.info("Breeze Umbrella mod initialized!");
    }
}