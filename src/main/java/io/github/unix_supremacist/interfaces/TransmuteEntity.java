package io.github.unix_supremacist.interfaces;

import io.github.unix_supremacist.content.AlchemistItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface TransmuteEntity {
    default InteractionResult transmuteEntity(LivingEntity entity, Level level){
        if(entity instanceof Villager){
            if (level instanceof ServerLevel serverLevel) {
                Vec3 pos = entity.position();
                serverLevel.addFreshEntity(new ItemEntity(serverLevel, pos.x(), pos.y(), pos.z(), new ItemStack(AlchemistItems.philosophers_stone.getItem())));
                entity.kill(serverLevel);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
