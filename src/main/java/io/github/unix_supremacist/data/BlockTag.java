package io.github.unix_supremacist.data;

import io.github.unix_supremacist.Alchemist;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockTag extends FabricTagsProvider.BlockTagsProvider {
    /** Groups of blocks the philosopher's stone cycles between, rebuilt from the tags on every reload. */
    public static final List<List<Block>> exchanges = new ArrayList<>();
    public static TagKey<Block> terratag = TagKey.create(BuiltInRegistries.BLOCK.key(), Alchemist.id("terra"));
    public static TagKey<Block> woodtag = TagKey.create(BuiltInRegistries.BLOCK.key(), Alchemist.id("wood"));
    public static TagKey<Block> netherwoodtag = TagKey.create(BuiltInRegistries.BLOCK.key(), Alchemist.id("netherwood"));
    public static TagKey<Block> leavetag = TagKey.create(BuiltInRegistries.BLOCK.key(), Alchemist.id("leave"));
    public static TagKey<Block> stonetag = TagKey.create(BuiltInRegistries.BLOCK.key(), Alchemist.id("stone"));
    public static TagKey<Block> obsidiantag = TagKey.create(BuiltInRegistries.BLOCK.key(), Alchemist.id("obsidian"));
    public static TagKey<Block> stonebrickstag = TagKey.create(BuiltInRegistries.BLOCK.key(), Alchemist.id("stonebricks"));

    public BlockTag(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    protected static void addExchange(TagKey<Block> tag){
        List<Block> blocks = new ArrayList<>();
        for (Holder<Block> holder : BuiltInRegistries.BLOCK.getTagOrEmpty(tag))
            if (!blocks.contains(holder.value()))
                blocks.add(holder.value());
        if (!blocks.isEmpty())
            exchanges.add(blocks);
    }

    public static void init(){
        exchanges.clear();
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
        TagAppender<Block> terra = builder(terratag);
        terra.add(key(Blocks.GRASS_BLOCK));
        terra.add(key(Blocks.DIRT));
        terra.add(key(Blocks.COARSE_DIRT));
        terra.add(key(Blocks.COBBLESTONE));
        terra.add(key(Blocks.MOSSY_COBBLESTONE));
        terra.add(key(Blocks.COBBLED_DEEPSLATE));
        terra.add(key(Blocks.STONE));
        terra.add(key(Blocks.DEEPSLATE));
        terra.add(key(Blocks.SAND));
        terra.add(key(Blocks.RED_SAND));
        terra.add(key(Blocks.GRAVEL));

        TagAppender<Block> wood = builder(woodtag);
        wood.add(key(Blocks.OAK_LOG));
        wood.add(key(Blocks.ACACIA_LOG));
        wood.add(key(Blocks.BIRCH_LOG));
        wood.add(key(Blocks.CHERRY_LOG));
        wood.add(key(Blocks.JUNGLE_LOG));
        wood.add(key(Blocks.DARK_OAK_LOG));
        wood.add(key(Blocks.MANGROVE_LOG));
        wood.add(key(Blocks.SPRUCE_LOG));
        wood.add(key(Blocks.PALE_OAK_LOG));
        wood.add(key(Blocks.POPLAR_LOG));
        wood.add(key(Blocks.STRIPPED_OAK_LOG));
        wood.add(key(Blocks.STRIPPED_ACACIA_LOG));
        wood.add(key(Blocks.STRIPPED_BIRCH_LOG));
        wood.add(key(Blocks.STRIPPED_CHERRY_LOG));
        wood.add(key(Blocks.STRIPPED_JUNGLE_LOG));
        wood.add(key(Blocks.STRIPPED_DARK_OAK_LOG));
        wood.add(key(Blocks.STRIPPED_MANGROVE_LOG));
        wood.add(key(Blocks.STRIPPED_SPRUCE_LOG));
        wood.add(key(Blocks.STRIPPED_PALE_OAK_LOG));
        wood.add(key(Blocks.STRIPPED_POPLAR_LOG));
        wood.add(key(Blocks.OAK_WOOD));
        wood.add(key(Blocks.ACACIA_WOOD));
        wood.add(key(Blocks.BIRCH_WOOD));
        wood.add(key(Blocks.CHERRY_WOOD));
        wood.add(key(Blocks.JUNGLE_WOOD));
        wood.add(key(Blocks.DARK_OAK_WOOD));
        wood.add(key(Blocks.MANGROVE_WOOD));
        wood.add(key(Blocks.SPRUCE_WOOD));
        wood.add(key(Blocks.PALE_OAK_WOOD));
        wood.add(key(Blocks.POPLAR_WOOD));

        TagAppender<Block> netherwood = builder(netherwoodtag);
        netherwood.add(key(Blocks.CRIMSON_STEM));
        netherwood.add(key(Blocks.WARPED_STEM));
        netherwood.add(key(Blocks.STRIPPED_CRIMSON_STEM));
        netherwood.add(key(Blocks.STRIPPED_WARPED_STEM));

        TagAppender<Block> leave = builder(leavetag);
        leave.add(key(Blocks.OAK_LEAVES));
        leave.add(key(Blocks.ACACIA_LEAVES));
        leave.add(key(Blocks.BIRCH_LEAVES));
        leave.add(key(Blocks.CHERRY_LEAVES));
        leave.add(key(Blocks.JUNGLE_LEAVES));
        leave.add(key(Blocks.DARK_OAK_LEAVES));
        leave.add(key(Blocks.MANGROVE_LEAVES));
        leave.add(key(Blocks.SPRUCE_LEAVES));
        leave.add(key(Blocks.PALE_OAK_LEAVES));
        leave.add(key(Blocks.ORANGE_POPLAR_LEAVES));
        leave.add(key(Blocks.RED_POPLAR_LEAVES));
        leave.add(key(Blocks.YELLOW_POPLAR_LEAVES));
        leave.add(key(Blocks.NETHER_WART_BLOCK));
        leave.add(key(Blocks.WARPED_WART_BLOCK));

        TagAppender<Block> stone = builder(stonetag);
        stone.add(key(Blocks.ANDESITE));
        stone.add(key(Blocks.DIORITE));
        stone.add(key(Blocks.GRANITE));
        stone.add(key(Blocks.SANDSTONE));
        stone.add(key(Blocks.RED_SANDSTONE));
        stone.add(key(Blocks.CLAY));

        TagAppender<Block> obsidian = builder(obsidiantag);
        obsidian.add(key(Blocks.OBSIDIAN));
        obsidian.add(key(Blocks.CRYING_OBSIDIAN));

        TagAppender<Block> stonebricks = builder(stonebrickstag);
        stonebricks.add(key(Blocks.STONE_BRICKS));
        stonebricks.add(key(Blocks.MOSSY_STONE_BRICKS));
    }

    private static ResourceKey<Block> key(Block block){
        return block.builtInRegistryHolder().key();
    }

    /** The exchange group containing this block, or null if it has none. */
    public static List<Block> findExchange(Block block){
        for (List<Block> exchange : exchanges)
            if (exchange.contains(block))
                return exchange;
        return null;
    }

    public static Block getBlockWithOffset(Block b, List<Block> exchange, int offset){
        int id = Math.floorMod(exchange.indexOf(b) + offset, exchange.size());
        return exchange.get(id);
    }
}
