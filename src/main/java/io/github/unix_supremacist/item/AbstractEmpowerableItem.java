package io.github.unix_supremacist.item;

import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import io.github.unix_supremacist.Alchemist;
import net.minecraft.util.Mth;
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

    @Override
    public int getBarColor(ItemStack item) {
        float f = Math.max(0.0F, ((float) this.maxPower - (float) getPower(item)) / (float) this.maxPower);
        return Mth.hsvToRgb(f / 1.1F, 1.0F, 1.0F);
    }

    @Override
    public boolean isBarVisible(ItemStack item) {
        return getPower(item) > 0;
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
    }

    @Override
    public int getBarWidth(ItemStack item) {
        return Math.round(13.0F - (float) getPower(item) * 13.0F / (float) this.maxPower);
    }

    //@Override
    //public Rarity getRarity(ItemStack s){
    //    return Rarity.EPIC;
    //}
}
