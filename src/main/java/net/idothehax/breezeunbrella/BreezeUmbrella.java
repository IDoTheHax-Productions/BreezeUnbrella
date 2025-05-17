package net.idothehax.breezeunbrella;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
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

    public static final EntityType<BreezeUmbrellaEntity> BREEZE_UMBRELLA_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MOD_ID, "breeze_umbrella"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BreezeUmbrellaEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6F, 1.8F))
                    .build()
    );



    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "breeze_umbrella"), BREEZE_UMBRELLA);
        FabricDefaultAttributeRegistry.register(BREEZE_UMBRELLA_ENTITY, BreezeUmbrellaEntity.createUmbrellaAttributes());

        LOGGER.info("Breeze Umbrella mod initialized!");
    }
}