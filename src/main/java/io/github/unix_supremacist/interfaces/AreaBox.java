package io.github.unix_supremacist.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;

import java.util.ArrayList;
import java.util.List;

public interface AreaBox {
    /** Every position in the box from anchor+negative to anchor+positive (inclusive). */
    default List<BlockPos> getArea(BlockPos anchor, BlockPos negative, BlockPos positive){
        List<BlockPos> blocks = new ArrayList<>();
        for (int x = negative.getX(); x <= positive.getX(); x++)
            for (int y = negative.getY(); y <= positive.getY(); y++)
                for (int z = negative.getZ(); z <= positive.getZ(); z++)
                    blocks.add(anchor.offset(x, y, z));
        return blocks;
    }

    /** A square of radius {@code size} on the clicked face, extending {@code depth} blocks into the surface. */
    default List<BlockPos> getAreaFromFacing(Direction dir, BlockPos anchor, int size, int depth){
        return getAreaFromFacing(dir, Direction.NORTH, anchor, size, size, depth);
    }

    /**
     * A rectangle on the clicked face reaching {@code width} blocks left/right and {@code height} blocks
     * up/down (or forward/back on the top and bottom faces, turned to match {@code playerDir}),
     * extending {@code depth} blocks into the surface.
     */
    default List<BlockPos> getAreaFromFacing(Direction dir, Direction playerDir, BlockPos anchor, int width, int height, int depth){
        // on the top/bottom faces, "width" runs across the player's view and "height" along it
        boolean alongX = playerDir.getAxis() == Direction.Axis.Z;
        int x = alongX ? width : height;
        int z = alongX ? height : width;
        return switch (dir) {
            case SOUTH -> getArea(anchor, new BlockPos(-width, -height, -depth), new BlockPos(width, height, 0));
            case NORTH -> getArea(anchor, new BlockPos(-width, -height, 0), new BlockPos(width, height, depth));
            case EAST -> getArea(anchor, new BlockPos(-depth, -height, -width), new BlockPos(0, height, width));
            case WEST -> getArea(anchor, new BlockPos(0, -height, -width), new BlockPos(depth, height, width));
            case UP -> getArea(anchor, new BlockPos(-x, -depth, -z), new BlockPos(x, 0, z));
            case DOWN -> getArea(anchor, new BlockPos(-x, 0, -z), new BlockPos(x, depth, z));
        };
    }

    /** Re-sends the held stack to the client after changing many blocks, so vanilla clients don't show a stale item. */
    default void resyncHeldItem(Player player, UseOnContext context){
        player.setItemInHand(context.getHand(), player.getItemInHand(context.getHand()).copy());
    }
}
