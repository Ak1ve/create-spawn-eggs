package net.ak1ve.create_spawn_eggs.items;

import net.ak1ve.create_spawn_eggs.Create_spawn_eggs;
import net.ak1ve.create_spawn_eggs.items.custom.MechanicalSpawnEggItem;
import net.ak1ve.create_spawn_eggs.items.custom.Scythe;
import net.ak1ve.create_spawn_eggs.items.custom.SoulCanister;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class SpawnEggs {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Create_spawn_eggs.MODID);

    public static final ArrayList<RegistryObject<Item>> OBJECTS = new ArrayList<>();


    public static void register(IEventBus eventBus) {

        OBJECTS.add(ITEMS.register(
                "mechanical_spawn_egg",
                () -> new MechanicalSpawnEggItem(new Item.Properties())
                ));
        OBJECTS.add(ITEMS.register(
                "empty_soul_canister",
                () -> new Item(new Item.Properties())
        ));
        OBJECTS.add(ITEMS.register(
                "scythe",
                () -> new Scythe(new Item.Properties())
        ));
        GeneratedRegisterSpawnEggs.registerFilled(ITEMS, OBJECTS);
        GeneratedRegisterSoulCanisters.registerFilled(ITEMS, OBJECTS);
        ITEMS.register(eventBus);

//        .getRandomItems(
//                new LootContext(new LootParams(ServerLevel.OVERWORLD))
//        );
    }
}
