package io.github.unix_supremacist.item;

import eu.pb4.factorytools.api.item.AutoModeledPolymerItem;
import eu.pb4.factorytools.api.item.FactoryBlockItem;
import io.github.unix_supremacist.block.TransmutionCircleBlock;
import io.github.unix_supremacist.content.AlchemistItems;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.Vec3;

public class AlchemistBlockItem extends BlockItem implements AutoModeledPolymerItem {
    @Getter Item polymerItem;
    Block block;

    public AlchemistBlockItem(Block block, Properties properties, Item polymerItem) {
        super(block, properties);
        this.polymerItem = polymerItem;
        this.block = block;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult interactionResult;
        ItemStack item = context.getItemInHand();
        BlockPos blockPos = context.getClickedPos().relative(context.getClickedFace());
        if(context.getLevel().getBlockState(blockPos) == Blocks.LAVA.defaultBlockState() && block.equals(Blocks.LAVA)){
            if (item.getDamageValue() > 0 || context.getPlayer().isCreative()){
                context.getLevel().setBlock(blockPos, Blocks.AIR.defaultBlockState(), 0);
                if(!context.getPlayer().isCreative()) context.getItemInHand().setDamageValue(item.getDamageValue()-1);
                interactionResult = InteractionResult.SUCCESS;
            } else interactionResult = InteractionResult.PASS;
        } else if (block.equals(Blocks.WATER) || block.equals(Blocks.LAVA)) {
            context.getLevel().setBlock(blockPos, block.defaultBlockState(), 0);
            if (item.getMaxDamage() > 0) context.getItemInHand().hurtAndBreak(1, context.getPlayer(), EquipmentSlot.MAINHAND);
            interactionResult = InteractionResult.SUCCESS;
        } else {
            context.getItemInHand().grow(1);
            interactionResult = this.place(new BlockPlaceContext(context));
            if(interactionResult == InteractionResult.FAIL) context.getItemInHand().shrink(1);
            if (item.getMaxDamage() > 0) context.getItemInHand().hurtAndBreak(1, context.getPlayer(), EquipmentSlot.MAINHAND);
        }

        return interactionResult;
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        return new ItemStack(this);
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
