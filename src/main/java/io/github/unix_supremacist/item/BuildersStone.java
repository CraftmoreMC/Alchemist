package io.github.unix_supremacist.item;

import io.github.unix_supremacist.interfaces.AreaBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class BuildersStone extends AbstractEmpowerableItem implements AreaBox {
    protected final int[] MODES;
    private final int maxWidth;
    private final int maxHeight;

    public BuildersStone(Properties properties, int maxWidth, int maxHeight) {
        super(properties, maxWidth * maxHeight, Items.GOLDEN_CARROT);
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;

        MODES = new int[maxWidth * maxHeight * 2];
        for (int i = 0; i < maxPower; i++) {
            MODES[i * 2] = i % maxWidth;
            MODES[i * 2 + 1] = i / maxWidth;
        }
    }

    @Override
    public void empower(ItemStack item, Player p){
        super.empower(item, p);
        p.displayClientMessage(Component.literal("Width: "+((maxWidth-1)*2-MODES[getPower(item)*2]*2+1)+" and Height: "+((maxHeight-1)*2-MODES[getPower(item)*2+1]*2+1)), true);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = super.useOn(context);
        if (context.getPlayer().isShiftKeyDown() == false){
            ArrayList<BlockPos> againstBlocks = getAreaFromFacing(context.getClickedFace(), context.getPlayer().getDirection(), context.getClickedPos(), maxWidth-1-MODES[getPower(context.getItemInHand())*2], (maxHeight-1-MODES[getPower(context.getItemInHand())*2+1]), 0);
            ArrayList<BlockPos> blocks = getAreaFromFacing(context.getClickedFace(), context.getPlayer().getDirection(), context.getClickedPos().relative(context.getClickedFace()), maxWidth-1-MODES[getPower(context.getItemInHand())*2], (maxHeight-1-MODES[getPower(context.getItemInHand())*2+1]), 0);

            if(!blocks.isEmpty()){
                if (!context.getLevel().isClientSide()) for (int i = 0; i < againstBlocks.size(); i++){
                    BlockState state = context.getLevel().getBlockState(againstBlocks.get(i));

                    Block block = context.getLevel().getBlockState(blocks.get(i)).getBlock();
                    if (block.equals(Blocks.AIR) && state.equals(context.getLevel().getBlockState(context.getClickedPos()))) {
                        context.getLevel().setBlock(blocks.get(i), state, 3);
                        ItemStack stack = context.getPlayer().getItemInHand(context.getHand()).copy();
                        context.getPlayer().setItemInHand(context.getHand(), stack);
                        result = InteractionResult.SUCCESS;
                    }
                }
            }
        }

        return result;
    }

}
