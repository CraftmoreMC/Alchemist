package io.github.unix_supremacist.item;

import io.github.unix_supremacist.data.BlockTag;
import io.github.unix_supremacist.interfaces.AreaBox;
import io.github.unix_supremacist.interfaces.TransmuteEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class PhilosophersStoneItem extends AbstractEmpowerableItem implements AreaBox, TransmuteEntity {
    public PhilosophersStoneItem(Properties properties) {
        super(properties, 3, Items.SLIME_BALL);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || player.isShiftKeyDown())
            return super.useOn(context);

        Level level = context.getLevel();
        List<Block> exchange = BlockTag.findExchange(level.getBlockState(context.getClickedPos()).getBlock());
        if (exchange == null)
            return InteractionResult.PASS;

        if (!level.isClientSide()) {
            List<BlockPos> blocks = getAreaFromFacing(context.getClickedFace(), context.getClickedPos(), 2 - getPower(context.getItemInHand()), 0);
            for (BlockPos pos : blocks) {
                Block block = level.getBlockState(pos).getBlock();
                if (exchange.contains(block) && player.mayUseItemAt(pos, context.getClickedFace(), context.getItemInHand()))
                    level.setBlock(pos, BlockTag.getBlockWithOffset(block, exchange, 1).defaultBlockState(), Block.UPDATE_ALL);
            }
            resyncHeldItem(player, context);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        return transmuteEntity(entity, player.level());
    }

    // the stone is a catalyst: crafting hands back the same stone, keeping its power setting
    @Override
    public ItemStackTemplate getCraftingRemainder(ItemStack stack) {
        return ItemStackTemplate.fromNonEmptyStack(stack, 1);
    }
}
