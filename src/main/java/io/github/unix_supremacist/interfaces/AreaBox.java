package io.github.unix_supremacist.interfaces;

import io.github.unix_supremacist.Alchemist;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.ArrayList;

public interface AreaBox {
    default ArrayList<BlockPos> getArea(Direction dir, BlockPos anchor, BlockPos negative, BlockPos positive){
        ArrayList<BlockPos> blocks = new ArrayList<>();

        for(int x = negative.getX(); x <= positive.getX(); x++)
            for (int y = negative.getY(); y <= positive.getY(); y++)
                for (int z = negative.getZ(); z <= positive.getZ(); z++)
                    blocks.add(anchor.offset(x, y, z));
        return blocks;
    }

    default ArrayList<BlockPos> getAreaFromFacing(Direction dir, BlockPos anchor, int size, int depth){
        switch(dir) {
            case SOUTH:
                return getArea(dir, anchor, new BlockPos(-size, -size, -depth), new BlockPos(size, size, 0));
            case NORTH:
                return getArea(dir, anchor, new BlockPos(-size, -size, 0), new BlockPos(size, size, depth));
            case EAST:
                return getArea(dir, anchor, new BlockPos(-depth, -size, -size), new BlockPos(0, size, size));
            case WEST:
                return getArea(dir, anchor, new BlockPos(0, -size, -size), new BlockPos(depth, size, size));
            case UP:
                return getArea(dir, anchor, new BlockPos(-size, -depth, -size), new BlockPos(size, 0, size));
            case DOWN:
                return getArea(dir, anchor, new BlockPos(-size, 0, -size), new BlockPos(size, depth, size));
            default:
                Alchemist.LOGGER.error("HOW DID YOU CLICK A DIRECTION THAT ISN'T NORTH SOUTH EAST WEST TOP OR BOTTOM");
                return null;
        }
    }

    default ArrayList<BlockPos> getAreaFromFacing(Direction dir, Direction playerDir, BlockPos anchor, int width, int height, int depth){
        switch(dir) {
            case SOUTH:
                return getArea(dir, anchor, new BlockPos(-width, -height, -depth), new BlockPos(width, height, 0));
            case NORTH:
                return getArea(dir, anchor, new BlockPos(-width, -height, 0), new BlockPos(width, height, depth));
            case EAST:
                return getArea(dir, anchor, new BlockPos(-depth, -height, -width), new BlockPos(0, height, width));
            case WEST:
                return getArea(dir, anchor, new BlockPos(0, -height, -width), new BlockPos(depth, height, width));
            case UP:
                switch (playerDir){
                    case SOUTH:
                    case NORTH:
                        return getArea(dir, anchor, new BlockPos(-width, -depth, -height), new BlockPos(width, 0, height));
                    case EAST:
                    case WEST:
                        return getArea(dir, anchor, new BlockPos(-height, -depth, -width), new BlockPos(height, 0, width));
                }
            case DOWN:
                switch (playerDir){
                    case SOUTH:
                    case NORTH:
                        return getArea(dir, anchor, new BlockPos(-width, 0, -height), new BlockPos(width, depth, height));
                    case EAST:
                    case WEST:
                        return getArea(dir, anchor, new BlockPos(-height, 0, -width), new BlockPos(height, depth, width));
                }
            default:
                Alchemist.LOGGER.error("HOW DID YOU CLICK A DIRECTION THAT ISN'T NORTH SOUTH EAST WEST TOP OR BOTTOM");
                return null;
        }
    }
}
