package io.github.unix_supremacist.item;

import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import io.github.unix_supremacist.Alchemist;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

/**
 * An item with power levels, cycled by sneak-using it on a block. The level is shown
 * on the durability bar (the item's damage value mirrors its power).
 */
public abstract class AbstractEmpowerableItem extends SimplePolymerItem {
    protected final int maxPower;

    public AbstractEmpowerableItem(Properties properties, int maxPower, Item polymerItem) {
        super(properties, polymerItem, true);
        this.maxPower = maxPower;
    }

    public int getPower(ItemStack item) {
        return item.getOrDefault(Alchemist.POWER, 0);
    }

    public void setPower(ItemStack item, int i) {
        item.set(Alchemist.POWER, i);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null && player.isShiftKeyDown()) {
            empower(context.getItemInHand(), player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void empower(ItemStack item, Player p) {
        if (isBarVisible(item))
            setPower(item, getPower(item) - 1);
        else
            setPower(item, maxPower - 1);
        item.setDamageValue(getPower(item));
    }
}
