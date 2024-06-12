package io.github.unix_supremacist.item;

import eu.pb4.factorytools.api.item.ModeledItem;
import io.github.unix_supremacist.Alchemist;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class AbstractEmpowerableItem extends ModeledItem {
    protected final int maxPower;

    public AbstractEmpowerableItem(Properties properties, int maxPower, Item polymerItem) {
        super(polymerItem, properties);
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
        InteractionResult result;
        if(context.getPlayer().isShiftKeyDown()){
            empower(context.getItemInHand(), context.getPlayer());
            result = InteractionResult.SUCCESS;
        } else {
            result = InteractionResult.PASS;
        }

        return result;
    }

    public void empower(ItemStack item, Player p) {
        if (isBarVisible(item))
            setPower(item, getPower(item) - 1);
        else
            setPower(item, maxPower - 1);
        item.setDamageValue(getPower(item));
    }

    //@Override
    //public Rarity getRarity(ItemStack s){
    //    return Rarity.EPIC;
    //}
}
