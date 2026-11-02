package io.github.unix_supremacist.item;

import io.github.unix_supremacist.interfaces.AreaBox;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

/**
 * Extends a surface: using it on a block places a copy of that block in front of every matching block
 * in an area around it, taking the blocks from the player's inventory. Sneak-use cycles the area size.
 */
public class BuildersStoneItem extends AbstractEmpowerableItem implements AreaBox {
    protected final int[] MODES;
    private final int maxWidth;
    private final int maxHeight;

    public BuildersStoneItem(Properties properties, int maxWidth, int maxHeight) {
        super(properties, maxWidth * maxHeight, Items.GOLDEN_CARROT);
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;

        MODES = new int[maxWidth * maxHeight * 2];
        for (int i = 0; i < maxPower; i++) {
            MODES[i * 2] = i % maxWidth;
            MODES[i * 2 + 1] = i / maxWidth;
        }
    }

    private int width(ItemStack stack) {
        return maxWidth - 1 - MODES[getPower(stack) * 2];
    }

    private int height(ItemStack stack) {
        return maxHeight - 1 - MODES[getPower(stack) * 2 + 1];
    }

    @Override
    public void empower(ItemStack item, Player p) {
        super.empower(item, p);
        p.sendOverlayMessage(Component.literal("Width: " + (width(item) * 2 + 1) + " and Height: " + (height(item) * 2 + 1)));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || player.isShiftKeyDown())
            return super.useOn(context);

        Level level = context.getLevel();
        BlockPos clicked = context.getClickedPos();
        Direction face = context.getClickedFace();
        BlockState target = level.getBlockState(clicked);
        if (!(target.getBlock().asItem() instanceof BlockItem blockItem))
            return InteractionResult.PASS;
        if (level.isClientSide())
            return InteractionResult.SUCCESS;

        ItemStack held = context.getItemInHand();
        List<BlockPos> area = getAreaFromFacing(face, player.getDirection(), clicked, width(held), height(held), 0);
        // closest to the clicked block first, so running out of blocks leaves the centre filled
        area.sort(Comparator.comparingDouble(pos -> pos.distSqr(clicked)));

        boolean placed = false;
        for (BlockPos against : area) {
            if (!level.getBlockState(against).equals(target))
                continue;
            BlockPos pos = against.relative(face);
            if (!level.getBlockState(pos).isAir() || !player.mayUseItemAt(pos, face, held))
                continue;
            ItemStack blocks = player.hasInfiniteMaterials() ? new ItemStack(blockItem) : findStack(player.getInventory(), blockItem);
            if (blocks == null)
                break;
            // place() takes the block out of that inventory stack itself
            BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(pos), face, against, false);
            if (blockItem.place(new BlockPlaceContext(player, context.getHand(), blocks, hit)) instanceof InteractionResult.Success)
                placed = true;
        }
        if (!placed)
            return InteractionResult.PASS;

        // place() leaves the sound out for the player, expecting their client to have played it
        SoundType sound = target.getSoundType();
        level.playSound(null, clicked.relative(face), sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
        resyncHeldItem(player, context);
        // vanilla clients see a golden carrot, so the server has to send the swing
        return InteractionResult.SUCCESS_SERVER;
    }

    private static ItemStack findStack(Inventory inventory, BlockItem item) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.is(item))
                return stack;
        }
        return null;
    }
}
