package io.github.unix_supremacist.item;

import dev.emi.trinkets.TrinketSlot;
import dev.emi.trinkets.api.*;
import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import io.github.unix_supremacist.Alchemist;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class GaleItem extends ModeledItem implements Trinket {
    public GaleItem(Properties properties) {
        super(Items.FEATHER, properties);
    }
    public static final AbilitySource gale_ability = Pal.getAbilitySource(Alchemist.MODID, "swiftwolfs_rending_gale");

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!entity.level().isClientSide())
            if (entity instanceof Player)
                gale_ability.grantTo((Player) entity, VanillaAbilities.ALLOW_FLYING);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!entity.level().isClientSide())
            if (entity instanceof Player)
                gale_ability.revokeFrom((Player) entity, VanillaAbilities.ALLOW_FLYING);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (equipItem(user, stack)) {
            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
        }
        return super.use(world, user, hand);
    }

    public static boolean equipItem(Player user, ItemStack stack) {
        return equipItem((LivingEntity) user, stack);
    }

    public static boolean equipItem(LivingEntity user, ItemStack stack) {
        var optional = TrinketsApi.getTrinketComponent(user);
        if (optional.isPresent()) {
            TrinketComponent comp = optional.get();
            for (var group : comp.getInventory().values()) {
                for (TrinketInventory inv : group.values()) {
                    for (int i = 0; i < inv.getContainerSize(); i++) {
                        if (inv.getItem(i).isEmpty()) {
                            SlotReference ref = new SlotReference(inv, i);
                            if (TrinketSlot.canInsert(stack, ref, user)) {
                                ItemStack newStack = stack.copy();
                                inv.setItem(i, newStack);
                                Trinket trinket = TrinketsApi.getTrinket(stack.getItem());
                                Holder<SoundEvent> soundEvent = trinket.getEquipSound(stack, ref, user);
                                if (!stack.isEmpty() && soundEvent != null) {
                                    user.gameEvent(GameEvent.EQUIP);
                                    user.playSound(soundEvent.value(), 1.0F, 1.0F);
                                }
                                stack.setCount(0);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
