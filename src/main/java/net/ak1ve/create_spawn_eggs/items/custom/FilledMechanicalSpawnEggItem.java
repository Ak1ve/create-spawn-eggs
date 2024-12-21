package net.ak1ve.create_spawn_eggs.items.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.Objects;
import java.util.Optional;


public class FilledMechanicalSpawnEggItem extends ForgeSpawnEggItem {

    public FilledMechanicalSpawnEggItem(EntityType<? extends Mob> typ, Properties pProperties) {
        super(() -> typ,
                ForgeSpawnEggItem.fromEntityType(typ).getColor(0),
                ForgeSpawnEggItem.fromEntityType(typ).getColor(1), pProperties);
    }

    @Override
    public @NotNull Optional<Mob> spawnOffspringFromSpawnEgg(Player pPlayer, Mob pMob, EntityType<? extends Mob> pEntityType, ServerLevel pServerLevel, Vec3 pPos, ItemStack pStack) {
        return Optional.empty();
    }


    private static final DispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR = (source, stack) ->
    {
        Direction face = source.getBlockState().getValue(DispenserBlock.FACING);
        EntityType<?> type = ((SpawnEggItem)stack.getItem()).getType(stack.getTag());

        try
        {
            var spawned = type.spawn(source.getLevel(), stack, null, source.getPos().relative(face), MobSpawnType.DISPENSER, face != Direction.UP, false);
            if (spawned != null) {
                setNBT(spawned, nbtData(stack));
            }
        }
        catch (Exception exception)
        {
            DispenseItemBehavior.LOGGER.error("Error while dispensing spawn egg from dispenser at {}", source.getPos(), exception);
            return ItemStack.EMPTY;
        }

        stack.shrink(1);
        source.getLevel().gameEvent(GameEvent.ENTITY_PLACE, source.getPos(), GameEvent.Context.of(source.getBlockState()));
        return stack;
    };

    private static void setNBT(Entity spawned, CompoundTag nbt) {
        if (spawned.serializeNBT().get("Pos") != null) {
            var entityNBT = spawned.serializeNBT();
            nbt.put("Pos", entityNBT.get("Pos"));
            spawned.deserializeNBT(nbt);
        }
    }

    @Nullable
    protected DispenseItemBehavior createDispenseBehavior()
    {
        return DEFAULT_DISPENSE_BEHAVIOR;
    }

    private static CompoundTag nbtData(ItemStack itemStack) {
        var nbt = itemStack.getTag();
        if (nbt == null || nbt.get("__create_spawn_eggs_nbt") == null) {
            nbt = new CompoundTag();
            nbt.putBoolean("NoAI", true);
        } else {
            nbt = (CompoundTag)nbt.get("__create_spawn_eggs_nbt");
        }
        return nbt;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = pContext.getItemInHand();
            BlockPos blockpos = pContext.getClickedPos();
            Direction direction = pContext.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            EntityType<?> entitytype = this.getType(itemstack.getTag());
            // no bosses!
            if (entitytype == EntityType.ENDER_DRAGON || entitytype == EntityType.WITHER) {
                return InteractionResult.SUCCESS;
            }
            var spawned = entitytype.spawn((ServerLevel)level, itemstack, pContext.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
            if (spawned != null) {
                setNBT(spawned, nbtData(itemstack));
                itemstack.shrink(1);
                level.gameEvent(pContext.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
            }

            return InteractionResult.CONSUME;
        }
    }

    //
//    public static void register_handler(IEventBus eventBus) {
//        eventBus.
//    }
}
