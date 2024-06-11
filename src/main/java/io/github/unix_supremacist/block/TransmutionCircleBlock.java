package io.github.unix_supremacist.block;

import eu.pb4.polymer.core.api.block.SimplePolymerBlock;
import io.github.unix_supremacist.interfaces.TransmuteEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class TransmutionCircleBlock extends SimplePolymerBlock implements TransmuteEntity {
    public TransmutionCircleBlock(Properties properties) {
        super(properties, Blocks.TRIPWIRE);
    }

     @Override
     public InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos pos, Player player, BlockHitResult blockHitResult) {
         if (!level.isClientSide()) {
             AABB aABB = new AABB(pos);
             List<Villager> villagerList = level.getEntitiesOfClass(Villager.class, aABB);
             if(!villagerList.isEmpty()){
                 level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                 return transmuteEntity(villagerList.get(0), level);
             }
         }

         return InteractionResult.PASS;
     }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.box(0f, 0f, 0f, 1f, 0.02f, 1f);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }
}
