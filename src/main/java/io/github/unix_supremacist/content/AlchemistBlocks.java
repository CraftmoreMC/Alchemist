package io.github.unix_supremacist.content;

import io.github.unix_supremacist.Alchemist;
import io.github.unix_supremacist.block.TransmutationCircleBlock;
import lombok.Getter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public enum AlchemistBlocks {
    transmutation_circle(TransmutationCircleBlock::new);
    @Getter
    private final TransmutationCircleBlock block;

    AlchemistBlocks(Function<BlockBehaviour.Properties, TransmutationCircleBlock> factory){
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Alchemist.id(this.name()));
        this.block = Registry.register(BuiltInRegistries.BLOCK, key, factory.apply(BlockBehaviour.Properties.of().setId(key)));
    }
}
