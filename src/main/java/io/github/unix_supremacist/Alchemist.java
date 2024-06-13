package io.github.unix_supremacist;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import io.github.unix_supremacist.content.AlchemistBlocks;
import io.github.unix_supremacist.content.AlchemistItems;
import io.github.unix_supremacist.data.BlockTag;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Alchemist implements ModInitializer {
	public static final String MODID = "alchemist";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final CreativeModeTab tab = PolymerItemGroupUtils.builder()
			.icon(() -> new ItemStack(AlchemistItems.philosophers_stone.getItem()))
			.title(Component.translatable("itemGroup."+MODID))
			.build();
	public static final DataComponentType<Integer> POWER = DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build();


	@Override
	public void onInitialize() {
		LOGGER.info("Welcome to the World of Alchemy!");
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "empowered"), Alchemist.POWER);
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(MODID, "tab"), Alchemist.tab);
		AlchemistBlocks.values(); //force the enum to load
		AlchemistItems.values(); //force the enum to load
		CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> BlockTag.init());
		PolymerResourcePackUtils.addModAssets(MODID);
		PolymerItemUtils.markAsPolymer(POWER);
	}
}