package io.github.unix_supremacist.content;

import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import io.github.unix_supremacist.Alchemist;
import io.github.unix_supremacist.item.AlchemistBlockItem;
import io.github.unix_supremacist.item.BuildersStoneItem;
import io.github.unix_supremacist.item.DestructionItem;
import io.github.unix_supremacist.item.GaleItem;
import io.github.unix_supremacist.item.PhilosophersStoneItem;
import lombok.Getter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;

import java.util.function.Function;

public enum AlchemistItems {
    alchemical_coal(25600, Items.DIAMOND),
    aeternalis_fuel(1638400, Items.NETHERITE_SCRAP),
    //archangels_smite,
    //black_hole_band,
    builders_stone(p -> new BuildersStoneItem(p.stacksTo(1).durability(16), 4, 4)),
    catalytic_lens(p -> new DestructionItem(p.stacksTo(1).durability(30), 3, 10, 5, Items.GOLD_NUGGET)),
    chalk(p -> new AlchemistBlockItem(AlchemistBlocks.transmutation_circle.getBlock(), p.durability(64), Items.STICK)),
    coal_coke(3200, Items.COOKED_BEEF),
    dark_matter(0, Items.FIRE_CHARGE),
    //dark_matter_pickaxe,
    //dark_matter_shovel,
    //dark_matter_hoe,
    //dark_matter_sword,
    //dark_matter_axe,
    //dark_matter_shears,
    //dark_matter_hammer,
    destruction_catalyst(p -> new DestructionItem(p.stacksTo(1).durability(15), 3, 5, 1, Items.GOLD_INGOT)),
    evertide_gem(p -> new AlchemistBlockItem(Blocks.WATER, p.stacksTo(1), Items.LAPIS_LAZULI)),
    //gem_of_eternal_density,
    //harvest_goddess_band,
    //hyperkinetic_lens,
    iron_band(0, Items.IRON_BARS),
    light_matter(0, Items.ENDER_EYE),
    mobius_fuel(204800, Items.EMERALD),
    philosophers_stone(p -> new PhilosophersStoneItem(p.stacksTo(1).durability(3))),
    red_matter(0, Items.MAGMA_CREAM),
    swiftwolfs_rending_gale(p -> new GaleItem(p.stacksTo(1))),
    volcanite_gem(p -> new AlchemistBlockItem(Blocks.LAVA, p.durability(64), Items.BLAZE_POWDER)),
    ;
    @Getter
    private final Item item;

    AlchemistItems(int burntime, Item polymerItem){
        // true = use our own item model instead of the vanilla stand-in's
        this(burntime, p -> new SimplePolymerItem(p, polymerItem, true));
    }

    AlchemistItems(Function<Properties, Item> factory){
        this(0, factory);
    }

    AlchemistItems(int burntime, Function<Properties, Item> factory){
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Alchemist.id(this.name()));
        Properties properties = new Properties().setId(key);
        // burn times live in data/alchemist/context_int_provider/cooking/time_<item>.json
        if (burntime > 0)
            properties.cookingFuel(ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, Alchemist.id("cooking/time_" + this.name())));
        this.item = Registry.register(BuiltInRegistries.ITEM, key, factory.apply(properties));
        // lets pick-block find the chalk for a transmutation circle; skipped for the fluid gems
        // so vanilla water and lava keep their own items
        if (item instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof LiquidBlock))
            blockItem.registerBlocks(Item.BY_BLOCK, item);
    }
}
