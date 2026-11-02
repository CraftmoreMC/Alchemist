package io.github.unix_supremacist.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

import static io.github.unix_supremacist.content.AlchemistItems.*;

public class ItemTag extends FabricTagsProvider.ItemTagsProvider {
    public static String common = "c";
    public static String trinkets = "trinkets";
    public static TagKey<Item> handring = TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(trinkets, "hand/ring"));
    public static TagKey<Item> offhandring = TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(trinkets, "offhand/ring"));
    public static TagKey<Item> water_bucket = TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(common, "buckets/water"));
    public static TagKey<Item> lava_bucket = TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(common, "buckets/lava"));
    public ItemTag(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    private static ResourceKey<Item> key(Item item) {
        return item.builtInRegistryHolder().key();
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        builder(handring).add(key(swiftwolfs_rending_gale.getItem()));
        builder(offhandring).add(key(swiftwolfs_rending_gale.getItem()));
        builder(water_bucket).add(key(evertide_gem.getItem()));
        builder(lava_bucket).add(key(volcanite_gem.getItem()));
    }
}
