package net.ak1ve.create_spawn_eggs.items;

import net.ak1ve.create_spawn_eggs.items.custom.FilledMechanicalSpawnEggItem;
import net.ak1ve.create_spawn_eggs.items.custom.MechanicalSpawnEggItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class GeneratedRegisterSpawnEggs
{{
    public static void registerFilled(DeferredRegister<Item> items, ArrayList<RegistryObject<Item>> objects) {{
        {lines}
    }}
}}
