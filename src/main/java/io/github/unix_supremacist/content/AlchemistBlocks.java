package io.github.unix_supremacist.content;

import eu.pb4.polymer.core.api.block.SimplePolymerBlock;
import io.github.unix_supremacist.Alchemist;
import io.github.unix_supremacist.block.TransmutionCircleBlock;
import lombok.Getter;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public enum AlchemistBlocks {
    transmutation_circle(new TransmutionCircleBlock(FabricBlockSettings.create()));
    @Getter
    SimplePolymerBlock block;

    AlchemistBlocks(SimplePolymerBlock block){
        this.block = block;
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Alchemist.MODID, this.name()), block);
    }
}
