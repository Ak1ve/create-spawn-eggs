package net.ak1ve.create_spawn_eggs.items.custom;

import net.ak1ve.create_spawn_eggs.items.SpawnEggs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class Scythe extends SwordItem {

    public Scythe(Properties pProperties) {
        super(
                new ForgeTier(
                    5, 256, 0.1F, 12.0F, 30, Tags.Blocks.OBSIDIAN,() -> Ingredient.of(Items.DRAGON_BREATH)
                ), 0, -2.8F, pProperties);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }


    @Override
    public @NotNull Rarity getRarity(ItemStack pStack) {
        return Rarity.EPIC;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        var offHand = player.getOffhandItem();
        var isMechanical = offHand.is(SpawnEggs.OBJECTS.get(0).get());
        if (!isMechanical && !offHand.is(SpawnEggs.OBJECTS.get(1).get())) {
            return super.onLeftClickEntity(stack, player, entity);
        }
        if (entity.serializeNBT().getBoolean("NoAI")) {
            return true;
        }

        if (entity.getType() == EntityType.ENDER_DRAGON && offHand.is(SpawnEggs.OBJECTS.get(0).get())) {
            return true;
        }

        var validEntity = false;
        for (var egg : SpawnEggItem.eggs()) {
            if (egg.getType(null).equals(entity.getType())) {
                validEntity = true;
            }
        }
        if (validEntity) {
            var itemKey = "create_spawn_eggs:" + entity.getType().toString().substring("entity.minecraft.".length());
            if (isMechanical) {
                itemKey += "_mechanical_spawn_egg";
            } else {
                itemKey += "_soul_canister";
            }
            var itemType = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemKey));
            if (itemType == null) {
                System.out.println("Could not form item of key " + itemKey);
                return true;
            }
            var item = new ItemStack(itemType);
            if (isMechanical) {
                var tag = new CompoundTag();
                tag.put("__create_spawn_eggs_nbt", entity.serializeNBT());
                item.setTag(tag);
            }
            offHand.shrink(1);
            entity.playSound(
                    SoundEvents.ALLAY_ITEM_TAKEN
            );
            entity.remove(Entity.RemovalReason.CHANGED_DIMENSION);
            player.getInventory().add(item);
            stack.hurtAndBreak(16, player, ( (p_43296_) -> {
                p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            }));
        }
        return true;
    }
}
