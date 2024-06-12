package io.github.unix_supremacist.content;

import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.core.api.block.SimplePolymerBlock;
import io.github.unix_supremacist.Alchemist;
import io.github.unix_supremacist.block.TransmutionCircleBlock;
import lombok.Getter;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public enum AlchemistBlocks {
    transmutation_circle(new TransmutionCircleBlock(FabricBlockSettings.create()));
    @Getter
    TransmutionCircleBlock block;

    AlchemistBlocks(TransmutionCircleBlock block){
        this.block = block;
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Alchemist.MODID, this.name()), block);
    }
}
