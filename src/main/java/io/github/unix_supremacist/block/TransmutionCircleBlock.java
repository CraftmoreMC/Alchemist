package io.github.unix_supremacist.block;

import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import io.github.unix_supremacist.Alchemist;
import io.github.unix_supremacist.interfaces.TransmuteEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class TransmutionCircleBlock extends Block implements TransmuteEntity, PolymerTexturedBlock {
    BlockState blockState;
    public TransmutionCircleBlock(Properties properties) {
        super(properties);
        blockState = PolymerBlockResourceUtils.requestBlock(BlockModelType.PLANT_BLOCK, PolymerBlockModel.of(ResourceLocation.fromNamespaceAndPath(Alchemist.MODID, "block/transmutation_circle")));
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

    /*@Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.box(0f, 0f, 0f, 1f, 0.02f, 1f);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }*/

    @Override
    public BlockState getPolymerBlockState(BlockState state){
        return blockState;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, ServerPlayer player){
        return blockState;
    }
}
