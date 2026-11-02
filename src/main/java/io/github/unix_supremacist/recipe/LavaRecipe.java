package io.github.unix_supremacist.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.unix_supremacist.item.AlchemistBlockItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * A shaped recipe that takes lava, where the volcanite gem can stand in for a lava bucket.
 * A drained gem doesn't count, and with {@code consumes_lava} the gem comes back with one less lava
 * (other recipes give it back untouched).
 */
public class LavaRecipe implements CraftingRecipe {
    public static final MapCodec<LavaRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ShapedRecipe.MAP_CODEC.forGetter(r -> r.recipe),
            Codec.BOOL.optionalFieldOf("consumes_lava", false).forGetter(r -> r.consumesLava)
    ).apply(i, LavaRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, LavaRecipe> STREAM_CODEC = StreamCodec.composite(
            ShapedRecipe.STREAM_CODEC, r -> r.recipe,
            ByteBufCodecs.BOOL, r -> r.consumesLava,
            LavaRecipe::new);
    public static final RecipeSerializer<LavaRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final ShapedRecipe recipe;
    private final boolean consumesLava;

    public LavaRecipe(ShapedRecipe recipe, boolean consumesLava) {
        this.recipe = recipe;
        this.consumesLava = consumesLava;
    }

    private static boolean isGem(ItemStack stack) {
        return stack.getItem() instanceof AlchemistBlockItem gem && gem.holdsFluid(stack);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        for (ItemStack stack : input.items())
            if (stack.getItem() instanceof AlchemistBlockItem gem && gem.isDrained(stack))
                return false;
        return recipe.matches(input, level);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = CraftingRecipe.defaultCraftingReminder(input);
        if (consumesLava) {
            for (int i = 0; i < input.size(); i++) {
                ItemStack stack = input.getItem(i);
                if (isGem(stack)) {
                    ItemStack drained = stack.copyWithCount(1);
                    drained.setDamageValue(drained.getDamageValue() + 1);
                    remaining.set(i, drained);
                }
            }
        }
        return remaining;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return recipe.assemble(input);
    }

    @Override
    public boolean showNotification() {
        return recipe.showNotification();
    }

    @Override
    public String group() {
        return recipe.group();
    }

    @Override
    public CraftingBookCategory category() {
        return recipe.category();
    }

    @Override
    public PlacementInfo placementInfo() {
        return recipe.placementInfo();
    }

    @Override
    public List<RecipeDisplay> display() {
        return recipe.display();
    }

    @Override
    public RecipeSerializer<LavaRecipe> getSerializer() {
        return SERIALIZER;
    }
}
