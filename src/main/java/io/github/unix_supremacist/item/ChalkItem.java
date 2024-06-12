package io.github.unix_supremacist.item;

import eu.pb4.factorytools.api.item.FactoryBlockItem;
import io.github.unix_supremacist.block.TransmutionCircleBlock;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;

public class ChalkItem extends FactoryBlockItem {
    public ChalkItem(TransmutionCircleBlock block, Properties properties) {
        super(block, properties, Items.STICK);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        context.getItemInHand().grow(1);
        InteractionResult interactionResult = this.place(new BlockPlaceContext(context));
        if(interactionResult == InteractionResult.FAIL) context.getItemInHand().shrink(1);
        context.getItemInHand().hurtAndBreak(1, context.getPlayer(), EquipmentSlot.MAINHAND);
        return interactionResult;
    }

    @Override
    public int getBarColor(ItemStack item) {
        float f = Math.max(0.0F, ((float)item.getMaxDamage() - (float)item.getDamageValue()) / (float)item.getMaxDamage());
        return Mth.hsvToRgb(f / 1.1F, 1.0F, 1.0F);
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
