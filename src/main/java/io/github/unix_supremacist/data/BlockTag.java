package io.github.unix_supremacist.data;

import io.github.unix_supremacist.Alchemist;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

public class BlockTag extends FabricTagProvider.BlockTagProvider {
    public static HashSet<ArrayList<Block>> exchanges = new HashSet<>();
    public static TagKey<Block> terratag = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(Alchemist.MODID, "terra"));
    public static TagKey<Block> woodtag = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(Alchemist.MODID, "wood"));
    public static TagKey<Block> netherwoodtag = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(Alchemist.MODID, "netherwood"));
    public static TagKey<Block> leavetag = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(Alchemist.MODID, "leave"));
    public static TagKey<Block> stonetag = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(Alchemist.MODID, "stone"));
    public static TagKey<Block> obsidiantag = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(Alchemist.MODID, "obsidian"));
    public static TagKey<Block> stonebrickstag = TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(Alchemist.MODID, "stonebricks"));

    public BlockTag(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    protected static void addExchange(TagKey<Block> tag){
        ArrayList<Block> arrayList = new ArrayList<>();
        BuiltInRegistries.BLOCK.getTagOrEmpty(tag).iterator().forEachRemaining(t ->arrayList.add(t.value()));
        exchanges.add(arrayList);
    }

    public static void init(){
        addExchange(terratag);
        addExchange(woodtag);
        addExchange(netherwoodtag);
        addExchange(leavetag);
        addExchange(stonetag);
        addExchange(obsidiantag);
        addExchange(stonebrickstag);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        FabricTagBuilder terra = getOrCreateTagBuilder(terratag);
        terra.add(Blocks.GRASS_BLOCK);
        terra.add(Blocks.DIRT);
        terra.add(Blocks.COARSE_DIRT);
        terra.add(Blocks.COBBLESTONE);
        terra.add(Blocks.MOSSY_COBBLESTONE);
        terra.add(Blocks.COBBLED_DEEPSLATE);
        terra.add(Blocks.STONE);
        terra.add(Blocks.DEEPSLATE);
        terra.add(Blocks.SAND);
        terra.add(Blocks.RED_SAND);
        terra.add(Blocks.GRAVEL);

        FabricTagBuilder wood = getOrCreateTagBuilder(woodtag);
        wood.add(Blocks.OAK_LOG);
        wood.add(Blocks.ACACIA_LOG);
        wood.add(Blocks.BIRCH_LOG);
        wood.add(Blocks.CHERRY_LOG);
        wood.add(Blocks.JUNGLE_LOG);
        wood.add(Blocks.DARK_OAK_LOG);
        wood.add(Blocks.MANGROVE_LOG);
        wood.add(Blocks.SPRUCE_LOG);
        wood.add(Blocks.STRIPPED_OAK_LOG);
        wood.add(Blocks.STRIPPED_ACACIA_LOG);
        wood.add(Blocks.STRIPPED_BIRCH_LOG);
        wood.add(Blocks.STRIPPED_CHERRY_LOG);
        wood.add(Blocks.STRIPPED_JUNGLE_LOG);
        wood.add(Blocks.STRIPPED_DARK_OAK_LOG);
        wood.add(Blocks.STRIPPED_MANGROVE_LOG);
        wood.add(Blocks.STRIPPED_SPRUCE_LOG);
        wood.add(Blocks.OAK_WOOD);
        wood.add(Blocks.ACACIA_LOG);
        wood.add(Blocks.BIRCH_LOG);
        wood.add(Blocks.CHERRY_LOG);
        wood.add(Blocks.JUNGLE_LOG);
        wood.add(Blocks.DARK_OAK_LOG);
        wood.add(Blocks.MANGROVE_LOG);
        wood.add(Blocks.SPRUCE_LOG);

        FabricTagBuilder netherwood = getOrCreateTagBuilder(netherwoodtag);
        netherwood.add(Blocks.CRIMSON_STEM);
        netherwood.add(Blocks.WARPED_STEM);
        netherwood.add(Blocks.STRIPPED_CRIMSON_STEM);
        netherwood.add(Blocks.STRIPPED_WARPED_STEM);

        FabricTagBuilder leave = getOrCreateTagBuilder(leavetag);
        leave.add(Blocks.OAK_LEAVES);
        leave.add(Blocks.ACACIA_LEAVES);
        leave.add(Blocks.BIRCH_LEAVES);
        leave.add(Blocks.CHERRY_LEAVES);
        leave.add(Blocks.JUNGLE_LEAVES);
        leave.add(Blocks.DARK_OAK_LEAVES);
        leave.add(Blocks.MANGROVE_LEAVES);
        leave.add(Blocks.SPRUCE_LEAVES);
        leave.add(Blocks.NETHER_WART_BLOCK);
        leave.add(Blocks.WARPED_WART_BLOCK);

        FabricTagBuilder stone = getOrCreateTagBuilder(stonetag);
        stone.add(Blocks.ANDESITE);
        stone.add(Blocks.DIORITE);
        stone.add(Blocks.GRANITE);
        stone.add(Blocks.SANDSTONE);
        stone.add(Blocks.RED_SANDSTONE);
        stone.add(Blocks.CLAY);

        FabricTagBuilder obsidian = getOrCreateTagBuilder(obsidiantag);
        obsidian.add(Blocks.OBSIDIAN);
        obsidian.add(Blocks.CRYING_OBSIDIAN);

        FabricTagBuilder stonebricks = getOrCreateTagBuilder(stonebrickstag);
        stonebricks.add(Blocks.STONE_BRICKS);
        stonebricks.add(Blocks.MOSSY_STONE_BRICKS);
    }

    public static Block getBlockWithOffset(Block b, ArrayList<Block> exchange, int o){
        int id = exchange.indexOf(b);
        int size = exchange.size();
        id += o;
        if (id < 0)
            id = size + id % size;
        else
            id %= size;
        return exchange.get(id);
    }
}
