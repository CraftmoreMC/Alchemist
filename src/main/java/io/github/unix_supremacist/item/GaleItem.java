package io.github.unix_supremacist.item;

import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import eu.pb4.trinkets.api.DefaultTrinketSlots;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import eu.pb4.trinkets.api.component.TrinketDataComponents;
import eu.pb4.trinkets.api.component.TrinketEquippable;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import io.github.unix_supremacist.Alchemist;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GaleItem extends SimplePolymerItem implements TrinketCallback {
    public static final AbilitySource gale_ability = Pal.getAbilitySource(Alchemist.MODID, "swiftwolfs_rending_gale");

    public GaleItem(Properties properties) {
        // equipOnInteract lets Trinkets equip it on right click
        super(properties.component(TrinketDataComponents.EQUIPMENT, TrinketEquippable.DEFAULT
                .withSlots(DefaultTrinketSlots.HAND_RING, DefaultTrinketSlots.OFFHAND_RING)
                .withEquipOnInteract(true)), Items.FEATHER, true);
        TrinketCallback.setCallback(this, this);
    }

    @Override
    public void onEquip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        if (!entity.level().isClientSide() && entity instanceof Player player)
            gale_ability.grantTo(player, VanillaAbilities.ALLOW_FLYING);
    }

    @Override
    public void onUnequip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        if (!entity.level().isClientSide() && entity instanceof Player player)
            gale_ability.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
    }
}
