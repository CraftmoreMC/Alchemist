package io.github.unix_supremacist;

import eu.pb4.polymer.core.api.item.PolymerCreativeModeTabUtils;
import eu.pb4.polymer.core.api.other.PolymerComponent;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.rsm.api.RegistrySyncUtils;
import io.github.unix_supremacist.content.AlchemistBlocks;
import io.github.unix_supremacist.content.AlchemistItems;
import io.github.unix_supremacist.data.BlockTag;
import io.github.unix_supremacist.recipe.LavaRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Alchemist implements ModInitializer {
	public static final String MODID = "alchemist";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final DataComponentType<Integer> POWER = DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build();
	/** Blocks a destruction item can still break from fuel it already burned. */
	public static final DataComponentType<Integer> FUEL = DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build();

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Welcome to the World of Alchemy!");
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id("empowered"), Alchemist.POWER);
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id("fuel"), Alchemist.FUEL);
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id("lava_shaped"), LavaRecipe.SERIALIZER);
		RegistrySyncUtils.setServerEntry(BuiltInRegistries.RECIPE_SERIALIZER, LavaRecipe.SERIALIZER);
		AlchemistBlocks.values(); //force the enum to load
		AlchemistItems.values(); //force the enum to load
		PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(
				id("tab"),
				CreativeModeTab.builder(CreativeModeTab.Row.BOTTOM, -1)
						.icon(() -> new ItemStack(AlchemistItems.philosophers_stone.getItem()))
						.title(Component.translatable("itemGroup."+MODID))
						.displayItems(((c, e) -> {
							for (AlchemistItems item : AlchemistItems.values())
								e.accept(item.getItem());
						}))
						.build());
		CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> BlockTag.init());
		PolymerResourcePackUtils.addModAssets(MODID);
		PolymerComponent.registerDataComponent(POWER, FUEL);
	}
}
