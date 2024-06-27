package io.github.unix_supremacist.content;

import eu.pb4.factorytools.api.item.ModeledItem;
import io.github.unix_supremacist.Alchemist;
import io.github.unix_supremacist.item.AlchemistBlockItem;
import io.github.unix_supremacist.item.DestructionItem;
import io.github.unix_supremacist.item.GaleItem;
import io.github.unix_supremacist.item.PhilosophersStoneItem;
import lombok.Getter;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public enum AlchemistItems {
    alchemical_coal(25600, Items.DIAMOND),
    aeternalis_fuel(1638400, Items.NETHERITE_SCRAP),
    //archangels_smite,
    //black_hole_band,
    catalytic_lens(new DestructionItem(new Properties().stacksTo(1).durability(30), 3, 10, 5, Items.GOLD_NUGGET)),
    chalk(new AlchemistBlockItem(AlchemistBlocks.transmutation_circle.getBlock(), new Properties().durability(64), Items.STICK)),
    coal_coke(3200, Items.COOKED_BEEF),
    dark_matter(0, Items.FIRE_CHARGE),
    //dark_matter_pickaxe,
    //dark_matter_shovel,
    //dark_matter_hoe,
    //dark_matter_sword,
    //dark_matter_axe,
    //dark_matter_shears,
    //dark_matter_hammer,
    destruction_catalyst(new DestructionItem(new Properties().stacksTo(1).durability(15), 3, 5, 1, Items.GOLD_INGOT)),
    evertide_gem(new AlchemistBlockItem(Blocks.WATER, new Properties().stacksTo(1), Items.LAPIS_LAZULI)),
    //gem_of_eternal_density,
    //harvest_goddess_band,
    //hyperkinetic_lens,
    iron_band(0, Items.IRON_BARS),
    mobius_fuel(204800, Items.EMERALD),
    philosophers_stone(new PhilosophersStoneItem(new Properties().stacksTo(1).durability(3))),
    red_matter(0, Items.MAGMA_CREAM),
    swiftwolfs_rending_gale(new GaleItem(new Properties().stacksTo(1))),
    volcanite_gem(new AlchemistBlockItem(Blocks.LAVA, new Properties().durability(64), Items.BLAZE_POWDER)),
    ;
    @Getter Item item;

    AlchemistItems(int burntime, Item polymerItem){
        this(new Properties(), burntime, polymerItem);
    }

    AlchemistItems(Properties properties, int burntime, Item polymerItem){
        this(new ModeledItem(polymerItem, properties), burntime);
    }

    AlchemistItems(Item item){
        this(item, 0);
    }

    AlchemistItems(Item item, int burntime){
        this.item = item;
        FuelRegistry.INSTANCE.add(item, burntime);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Alchemist.MODID, this.name()), item);
    }
}
