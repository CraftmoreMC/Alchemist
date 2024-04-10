package io.github.unix_supremacist;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.unix_supremacist.content.AlchemistBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.glfw.GLFW;

public class AlchemistClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		KeyMapping empower = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.alchemist.empower", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, "key.categories.alchemist"));
		BlockRenderLayerMap.INSTANCE.putBlock(AlchemistBlocks.transmutation_circle.getBlock(), RenderType.cutout());

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (empower.consumeClick()) ClientPlayNetworking.send(Alchemist.empower_packet, PacketByteBufs.empty());
		});
	}
}