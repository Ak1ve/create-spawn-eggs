package net.ak1ve.create_spawn_eggs.items;

import net.ak1ve.create_spawn_eggs.Create_spawn_eggs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Create_spawn_eggs.MODID);

    public static final RegistryObject<CreativeModeTab> CREATE_SPAWN_EGGS_TAB =
            CREATIVE_MODE_TABS.register("create_spawn_eggs_tab", () ->
                    CreativeModeTab
                            .builder()
                            .icon(() -> new ItemStack(SpawnEggs.OBJECTS.get(0).get()))
                            .title(Component.translatable("creativetab.create_spawn_eggs"))
                            .displayItems(((itemDisplayParameters, output) -> {
                                for (var obj : SpawnEggs.OBJECTS) {
                                    output.accept(obj.get());
                                }
                            }))
                            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
