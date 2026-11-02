package io.github.unix_supremacist.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * A reusable block placer. With a fluid block (evertide/volcanite gems) it places that fluid,
 * and the volcanite gem can also soak lava sources back up. With any other block (chalk) it places
 * the block without being used up, losing durability instead.
 */
public class AlchemistBlockItem extends BlockItem implements PolymerItem {
    private final Item polymerItem;

    public AlchemistBlockItem(Block block, Properties properties, Item polymerItem) {
        super(block, properties);
        this.polymerItem = polymerItem;
    }

    @Override
    public Item getPolymerItem(ItemStack stack, PacketContext context) {
        return polymerItem;
    }

    private boolean isFluidPlacer() {
        return getBlock() == Blocks.WATER || getBlock() == Blocks.LAVA;
    }

    /** True for a gem that keeps a fluid level (the volcanite gem); the evertide gem never runs out. */
    public boolean holdsFluid(ItemStack stack) {
        return isFluidPlacer() && stack.getMaxDamage() > 0;
    }

    /** A gem with no fluid left: it stays around (it doesn't break) until it soaks up more. */
    public boolean isDrained(ItemStack stack) {
        return holdsFluid(stack) && stack.getDamageValue() >= stack.getMaxDamage();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null)
            return InteractionResult.PASS;
        if (!isFluidPlacer())
            return placeBlock(context, player);

        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        if (!player.mayUseItemAt(pos, context.getClickedFace(), stack))
            return InteractionResult.FAIL;

        if (getBlock() == Blocks.LAVA && level.getBlockState(pos) == Blocks.LAVA.defaultBlockState()) {
            // soak up a lava source, refilling the gem
            if (stack.getDamageValue() == 0 && !player.isCreative())
                return InteractionResult.PASS;
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            if (!player.isCreative())
                stack.setDamageValue(stack.getDamageValue() - 1);
            level.playSound(null, pos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS_SERVER;
        }

        if (!level.getBlockState(pos).canBeReplaced() || isDrained(stack))
            return InteractionResult.FAIL;
        level.setBlock(pos, getBlock().defaultBlockState(), Block.UPDATE_ALL);
        // the durability bar is the fluid level; drain it by hand so an empty gem doesn't break
        if (holdsFluid(stack) && !player.hasInfiniteMaterials())
            stack.setDamageValue(stack.getDamageValue() + 1);
        level.playSound(null, pos, getBlock() == Blocks.LAVA ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        // vanilla clients see a lapis/blaze powder that does nothing, so they never swing on their own;
        // SUCCESS_SERVER makes the server send the swing (and the sound above plays for them too)
        return InteractionResult.SUCCESS_SERVER;
    }

    private InteractionResult placeBlock(UseOnContext context, Player player) {
        ItemStack stack = context.getItemInHand();
        // place() consumes one item outside creative, so add it back first; durability is the real cost
        boolean refund = !player.hasInfiniteMaterials();
        if (refund)
            stack.grow(1);
        InteractionResult result = this.place(new BlockPlaceContext(context));
        if (result instanceof InteractionResult.Success) {
            if (stack.getMaxDamage() > 0)
                stack.hurtAndBreak(1, player, context.getHand());
        } else if (refund) {
            stack.shrink(1);
        }
        return result;
    }

    // used as a bucket in recipes, so hand back the same gem (damage and all) instead of a fresh one
    @Override
    public ItemStackTemplate getCraftingRemainder(ItemStack stack) {
        return ItemStackTemplate.fromNonEmptyStack(stack, 1);
    }
}
