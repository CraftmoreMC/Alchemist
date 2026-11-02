package io.github.unix_supremacist.data;

import io.github.unix_supremacist.Alchemist;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import io.github.unix_supremacist.recipe.LavaRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipe;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static io.github.unix_supremacist.content.AlchemistItems.*;

public class Recipes extends FabricRecipeProvider {
    public Recipes(FabricPackOutput generator, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(generator, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<net.minecraft.world.item.crafting.Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new Gen(recipes, advancements);
    }

    private static class Gen extends RecipeProvider {
        Gen(BootstrapContext<net.minecraft.world.item.crafting.Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
            super(recipes, advancements);
        }

        /** An alchemist: recipe id named after the result (Fabric only renames ids for its own output). */
        private static ResourceKey<net.minecraft.world.item.crafting.Recipe<?>> key(Item result) {
            return ResourceKey.create(Registries.RECIPE, Alchemist.id(path(result)));
        }

        /** Saves a recipe as a {@link LavaRecipe}, so a drained volcanite gem doesn't count as lava. */
        private RecipeOutput lava(boolean consumesLava) {
            RecipeOutput parent = output;
            return new RecipeOutput() {
                @Override
                public void accept(ResourceKey<net.minecraft.world.item.crafting.Recipe<?>> key, net.minecraft.world.item.crafting.Recipe<?> recipe, AdvancementHolder advancement) {
                    parent.accept(key, new LavaRecipe((ShapedRecipe) recipe, consumesLava), advancement);
                }

                @Override
                public Advancement.Builder advancement() {
                    return parent.advancement();
                }

                @Override
                public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> registry) {
                    return parent.lookup(registry);
                }

                @Override
                public <S> Stream<Holder.Reference<S>> listContextElements(ResourceKey<? extends Registry<? extends S>> registry) {
                    return parent.listContextElements(registry);
                }
            };
        }

        @Override
        public void buildRecipes() {
            shaped(RecipeCategory.TOOLS, swiftwolfs_rending_gale.getItem())
                    .pattern("L")
                    .pattern("B")
                    .define('L', light_matter.getItem())
                    .define('B', iron_band.getItem())
                    .unlockedBy(getHasName(light_matter.getItem()), has(light_matter.getItem()))
                    .unlockedBy(getHasName(iron_band.getItem()), has(iron_band.getItem()))
                    .save(output);
            shaped(RecipeCategory.TOOLS, iron_band.getItem())
                    .pattern("III")
                    .pattern("ILI")
                    .pattern("III")
                    .define('I', Items.IRON_INGOT)
                    .define('L', ItemTag.lava_bucket)
                    .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                    .unlockedBy(getHasName(Items.LAVA_BUCKET), has(Items.LAVA_BUCKET))
                    .save(lava(false), key(iron_band.getItem()));
            shaped(RecipeCategory.TOOLS, catalytic_lens.getItem())
                    .pattern("DDD")
                    .pattern("CDC")
                    .pattern("DDD")
                    .define('D', red_matter.getItem())
                    .define('C', destruction_catalyst.getItem())
                    .unlockedBy(getHasName(red_matter.getItem()), has(red_matter.getItem()))
                    .unlockedBy(getHasName(destruction_catalyst.getItem()), has(destruction_catalyst.getItem()))
                    .save(output);
            shaped(RecipeCategory.TOOLS, destruction_catalyst.getItem())
                    .pattern("FRF")
                    .pattern("ASA")
                    .pattern("FRF")
                    .define('F', Items.FLINT_AND_STEEL)
                    .define('A', aeternalis_fuel.getItem())
                    .define('S', philosophers_stone.getItem())
                    .define('R', red_matter.getItem())
                    .unlockedBy(getHasName(Items.FLINT_AND_STEEL), has(Items.FLINT_AND_STEEL))
                    .unlockedBy(getHasName(aeternalis_fuel.getItem()), has(aeternalis_fuel.getItem()))
                    .unlockedBy(getHasName(philosophers_stone.getItem()), has(philosophers_stone.getItem()))
                    .unlockedBy(getHasName(red_matter.getItem()), has(red_matter.getItem()))
                    .save(output);
            shaped(RecipeCategory.TOOLS, volcanite_gem.getItem())
                    .pattern("LLL")
                    .pattern("DDD")
                    .pattern("LLL")
                    .define('D', dark_matter.getItem())
                    .define('L', ItemTag.lava_bucket)
                    .unlockedBy(getHasName(dark_matter.getItem()), has(dark_matter.getItem()))
                    .unlockedBy(getHasName(Items.LAVA_BUCKET), has(Items.LAVA_BUCKET))
                    .save(lava(false), key(volcanite_gem.getItem()));
            shaped(RecipeCategory.TOOLS, builders_stone.getItem())
                    .pattern("BBB")
                    .pattern("BDB")
                    .pattern("BBB")
                    .define('B', Items.BRICK)
                    .define('D', dark_matter.getItem())
                    .unlockedBy(getHasName(dark_matter.getItem()), has(dark_matter.getItem()))
                    .unlockedBy(getHasName(Items.BRICK), has(Items.BRICK))
                    .save(output);
            shaped(RecipeCategory.TOOLS, evertide_gem.getItem())
                    .pattern("WWW")
                    .pattern("DDD")
                    .pattern("WWW")
                    .define('D', dark_matter.getItem())
                    .define('W', ItemTag.water_bucket)
                    .unlockedBy(getHasName(dark_matter.getItem()), has(dark_matter.getItem()))
                    .unlockedBy(getHasName(Items.WATER_BUCKET), has(Items.WATER_BUCKET))
                    .save(output);
            shaped(RecipeCategory.TOOLS, chalk.getItem())
                    .pattern("CD")
                    .pattern("D ")
                    .define('D', Items.DIORITE)
                    .define('C', Items.CLAY_BALL)
                    .unlockedBy(getHasName(Items.DIORITE), has(Items.DIORITE))
                    .unlockedBy(getHasName(Items.CLAY_BALL), has(Items.CLAY_BALL))
                    .save(output);
            shaped(RecipeCategory.MISC, Items.COBBLESTONE)
                    .pattern("WPL")
                    .define('W', ItemTag.water_bucket)
                    .define('P', philosophers_stone.getItem())
                    .define('L', ItemTag.lava_bucket)
                    .unlockedBy(getHasName(Items.LAVA_BUCKET), has(Items.LAVA_BUCKET))
                    .unlockedBy(getHasName(Items.WATER_BUCKET), has(Items.WATER_BUCKET))
                    .unlockedBy(getHasName(philosophers_stone.getItem()), has(philosophers_stone.getItem()))
                    .save(lava(false), key(Items.COBBLESTONE));
            shaped(RecipeCategory.MISC, Items.OBSIDIAN)
                    .pattern("PWL")
                    .define('W', ItemTag.water_bucket)
                    .define('P', philosophers_stone.getItem())
                    .define('L', ItemTag.lava_bucket)
                    .unlockedBy(getHasName(Items.LAVA_BUCKET), has(Items.LAVA_BUCKET))
                    .unlockedBy(getHasName(Items.WATER_BUCKET), has(Items.WATER_BUCKET))
                    .unlockedBy(getHasName(philosophers_stone.getItem()), has(philosophers_stone.getItem()))
                    .save(lava(true), key(Items.OBSIDIAN));
            shaped(RecipeCategory.MISC, Items.STONE)
                    .pattern("PLW")
                    .define('W', ItemTag.water_bucket)
                    .define('P', philosophers_stone.getItem())
                    .define('L', ItemTag.lava_bucket)
                    .unlockedBy(getHasName(Items.LAVA_BUCKET), has(Items.LAVA_BUCKET))
                    .unlockedBy(getHasName(Items.WATER_BUCKET), has(Items.WATER_BUCKET))
                    .unlockedBy(getHasName(philosophers_stone.getItem()), has(philosophers_stone.getItem()))
                    .save(lava(false), key(Items.STONE));
            shaped(RecipeCategory.MISC, dark_matter.getItem())
                    .pattern("AAA")
                    .pattern("ANA")
                    .pattern("AAA")
                    .define('A', alchemical_coal.getItem())
                    .define('N', Items.NETHERITE_BLOCK)
                    .unlockedBy(getHasName(alchemical_coal.getItem()), has(alchemical_coal.getItem()))
                    .unlockedBy(getHasName(Items.NETHERITE_BLOCK), has(Items.NETHERITE_BLOCK))
                    .save(output);
            shaped(RecipeCategory.MISC, red_matter.getItem())
                    .pattern("MMM")
                    .pattern("DDD")
                    .pattern("MMM")
                    .define('D', dark_matter.getItem())
                    .define('M', mobius_fuel.getItem())
                    .unlockedBy(getHasName(dark_matter.getItem()), has(dark_matter.getItem()))
                    .unlockedBy(getHasName(mobius_fuel.getItem()), has(mobius_fuel.getItem()))
                    .save(output);
            shaped(RecipeCategory.MISC, light_matter.getItem())
                    .pattern("AAA")
                    .pattern("RRR")
                    .pattern("AAA")
                    .define('R', red_matter.getItem())
                    .define('A', aeternalis_fuel.getItem())
                    .unlockedBy(getHasName(red_matter.getItem()), has(red_matter.getItem()))
                    .unlockedBy(getHasName(aeternalis_fuel.getItem()), has(aeternalis_fuel.getItem()))
                    .save(output);
            twoWayPhilStoneRecipe(Items.COAL, Items.CHARCOAL, 1);
            philStoneRecipe(coal_coke.getItem(), 2, Items.COAL, 2);
            twoWayPhilStoneRecipe(alchemical_coal.getItem(), coal_coke.getItem(), 8);
            twoWayPhilStoneRecipe(mobius_fuel.getItem(), alchemical_coal.getItem(), 8);
            twoWayPhilStoneRecipe(aeternalis_fuel.getItem(), mobius_fuel.getItem(), 8);

            twoWayPhilStoneRecipe(Items.IRON_INGOT, Items.COPPER_INGOT, 8);
            twoWayPhilStoneRecipe(Items.DIAMOND, Items.GOLD_INGOT, 4);
            twoWayPhilStoneRecipe(Items.NETHERITE_SCRAP, Items.DIAMOND, 4);
            philStoneRecipe(Items.FLINT, 3, Items.GRAVEL, 3);
            twoWayPhilStoneRecipe(Items.GRAVEL, 4, Items.COBBLESTONE, 4);
            twoWayPhilStoneRecipe(Items.DIRT, 4, Items.SAND, 4);
            twoWayPhilStoneRecipe(Items.GRAVEL, 8, Items.DIRT, 8);
            twoWayPhilStoneRecipe(Items.COBBLESTONE, 8, Items.SAND, 8);
            twoWayPhilStoneRecipe(Items.CLAY_BALL, Items.STONE, 4);
            twoWayPhilStoneRecipe(Items.RAW_COPPER, Items.CLAY, 4);
            twoWayPhilStoneRecipe(Items.RAW_IRON, Items.RAW_COPPER, 8);
            twoWayPhilStoneRecipe(Items.RAW_GOLD, Items.RAW_IRON, 8);
            twoWayPhilStoneRecipe(Items.GUNPOWDER, Items.EMERALD, 1);
            twoWayPhilStoneRecipe(Items.BLAZE_ROD, Items.GUNPOWDER, 2);
            twoWayPhilStoneRecipe(Items.ENDER_PEARL, Items.BLAZE_ROD, 2);

        }

        public void twoWayPhilStoneRecipe(Item first, Item second, int count){
            twoWayPhilStoneRecipe(first, 1, second, count);
        }

        public void twoWayPhilStoneRecipe(Item first, int firstcount, Item second, int secondcount){
            philStoneRecipe(first, firstcount, second, secondcount);
            philStoneRecipe(second, secondcount, first, firstcount);
        }

        public void philStoneRecipe(Item result, int outputcount, Item input, int inputcount){
            shapeless(RecipeCategory.MISC, result, outputcount)
                    .requires(philosophers_stone.getItem())
                    .requires(input, inputcount)
                    .unlockedBy(getHasName(philosophers_stone.getItem()), has(philosophers_stone.getItem()))
                    .save(output, ResourceKey.create(Registries.RECIPE, Alchemist.id(path(result) + "_to_" + path(input))));
        }

        private static String path(Item item){
            return BuiltInRegistries.ITEM.getKey(item).getPath();
        }
    }
}
