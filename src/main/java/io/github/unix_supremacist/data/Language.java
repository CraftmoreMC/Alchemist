package io.github.unix_supremacist.data;

import io.github.unix_supremacist.Alchemist;
import io.github.unix_supremacist.content.AlchemistBlocks;
import io.github.unix_supremacist.content.AlchemistItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.text.WordUtils;

import java.util.concurrent.CompletableFuture;

public class Language extends FabricLanguageProvider {
    protected Language(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        for (AlchemistItems item : AlchemistItems.values())
            translationBuilder.add(item.getItem(), WordUtils.capitalize(item.name().replace("_", " ")));

        for (AlchemistBlocks block : AlchemistBlocks.values())
            translationBuilder.add(block.getBlock(), WordUtils.capitalize(block.name().replace("_", " ")));

        translationBuilder.add(ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), new ResourceLocation(Alchemist.MODID, "tab")), WordUtils.capitalize(Alchemist.MODID.replace("_", " ")));
    }
}
