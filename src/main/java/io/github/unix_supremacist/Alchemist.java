package io.github.unix_supremacist;

import com.mojang.serialization.Codec;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import io.github.unix_supremacist.content.AlchemistBlocks;
import io.github.unix_supremacist.content.AlchemistItems;
import io.github.unix_supremacist.content.AlchemistSplashes;
import io.github.unix_supremacist.data.BlockTag;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Alchemist {
    public static final String MODID = "alchemist";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final ResourceLocation empower_packet = new ResourceLocation(Alchemist.MODID, "empower_packet");
    public static final CreativeModeTab tab = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(AlchemistItems.philosophers_stone.getItem()))
            .title(Component.translatable("itemGroup."+MODID))
            .build();
    //public static final DataComponentType<Integer> POWER = DataComponents.register("alchemist.empowered", builder -> builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    //public static final DataComponentType<Integer> POWER = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, "alchemist.empowered", ((DataComponentType.Builder)builder -> builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).apply(DataComponentType.builder())).build());
    public static final DataComponentType<Integer> POWER = DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build();




    public static void Init(){
        LOGGER.info("Welcome to the World of Alchemy!");
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, new ResourceLocation(MODID, "empowered"), POWER);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(MODID, "tab"), tab);
        AlchemistSplashes.init();
        AlchemistBlocks.values(); //force the enum to load
        AlchemistItems.values(); //force the enum to load
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            BlockTag.init();
        });
        PolymerResourcePackUtils.addModAssets(MODID);
    }
}
