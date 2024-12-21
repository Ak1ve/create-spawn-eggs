package net.ak1ve.create_spawn_eggs.items.custom;

import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SoulCanister extends ForgeSpawnEggItem {
    public SoulCanister(EntityType<? extends Mob> typ, Properties pProperties) {
        super(() -> typ,
                ForgeSpawnEggItem.fromEntityType(typ).getColor(0),
                ForgeSpawnEggItem.fromEntityType(typ).getColor(1), pProperties);
    }

    @Nullable
    protected DispenseItemBehavior createDispenseBehavior()
    {
        return new DefaultDispenseItemBehavior();
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return InteractionResult.FAIL;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public @NotNull Optional<Mob> spawnOffspringFromSpawnEgg(Player pPlayer, Mob pMob, EntityType<? extends Mob> pEntityType, ServerLevel pServerLevel, Vec3 pPos, ItemStack pStack) {
        return Optional.empty();
    }

    @Override
    public int getColor(int pTintIndex) {
        return pTintIndex == 0 || pTintIndex == 1 ? super.getColor(pTintIndex) : -1;
    }
}
