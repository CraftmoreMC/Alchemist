package io.github.unix_supremacist.item;

import io.github.unix_supremacist.Alchemist;
import io.github.unix_supremacist.interfaces.AreaBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class DestructionItem extends AbstractEmpowerableItem implements AreaBox {
    protected final int[] MODES;
    private final int maxWidth;
    private final int maxDepth;
    private final int mult;

    public DestructionItem(Properties properties, int maxWidth, int maxDepth, int mult, Item polymerItem) {
        super(properties, maxWidth * maxDepth, polymerItem);
        this.maxWidth = maxWidth;
        this.maxDepth = maxDepth;
        this.mult = mult;

        MODES = new int[maxWidth * maxDepth * 2];
        for (int i = 0; i < maxPower; i++) {
            MODES[i * 2] = i % maxWidth;
            MODES[i * 2 + 1] = i / maxWidth;
        }
    }

    @Override
    public void empower(ItemStack item, Player p){
        super.empower(item, p);
        p.sendOverlayMessage(Component.literal("Width: "+((maxWidth-1)*2-MODES[getPower(item)*2]*2+1)+" and Depth: "+((maxDepth-1-MODES[getPower(item)*2+1])*mult+1)));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || player.isShiftKeyDown())
            return super.useOn(context);

        int power = getPower(context.getItemInHand());
        List<BlockPos> blocks = getAreaFromFacing(context.getClickedFace(), context.getClickedPos(), maxWidth-1-MODES[power*2], (maxDepth-1-MODES[power*2+1])*mult);
        Level level = context.getLevel();
        if (!level.isClientSide()) {
            ItemStack stack = context.getItemInHand();
            boolean free = player.hasInfiniteMaterials();
            // blocks still paid for by fuel burned earlier, so a dust's leftover isn't wasted
            int fuel = stack.getOrDefault(Alchemist.FUEL, 0);
            // nearest blocks first, so running out of fuel leaves the far end standing
            Vec3 eyes = player.getEyePosition();
            blocks.sort(Comparator.comparingDouble(pos -> pos.distToCenterSqr(eyes)));
            for (BlockPos pos : blocks) {
                BlockState state = level.getBlockState(pos);
                if (state.isAir() || state.liquid() || state.getBlock().defaultDestroyTime() < 0
                        || !player.mayUseItemAt(pos, context.getClickedFace(), context.getItemInHand()))
                    continue;
                if (!free && fuel == 0) {
                    fuel = takeFuel(player);
                    if (fuel == 0) {
                        player.sendOverlayMessage(Component.literal("Out of fuel"));
                        break;
                    }
                }
                if (level.destroyBlock(pos, !player.isCreative(), player) && !free)
                    fuel--;
            }
            if (fuel > 0)
                stack.set(Alchemist.FUEL, fuel);
            else
                stack.remove(Alchemist.FUEL);
            resyncHeldItem(player, context);
        }
        return InteractionResult.SUCCESS;
    }

    /**
     * Burns one unit of fuel and returns how many blocks it pays for, or 0 if there is none.
     * Tried in order: redstone dust (8 blocks), redstone block (72, i.e. 9 dust), glowstone dust (48),
     * glowstone block (192, i.e. 4 dust), then the player's health at 16 blocks per hitpoint,
     * taken half a hitpoint at a time and never going below half a heart.
     */
    private static int takeFuel(Player player) {
        Inventory inventory = player.getInventory();
        if (takeItem(inventory, Items.REDSTONE))
            return 8;
        if (takeItem(inventory, Items.REDSTONE_BLOCK))
            return 8 * 9;
        if (takeItem(inventory, Items.GLOWSTONE_DUST))
            return 48;
        if (takeItem(inventory, Items.GLOWSTONE))
            return 48 * 4;
        if (player.getHealth() - 0.5F >= 1.0F) {
            player.setHealth(player.getHealth() - 0.5F);
            return 8;
        }
        return 0;
    }

    private static boolean takeItem(Inventory inventory, Item item) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.is(item)) {
                stack.shrink(1);
                inventory.setChanged();
                return true;
            }
        }
        return false;
    }
}
