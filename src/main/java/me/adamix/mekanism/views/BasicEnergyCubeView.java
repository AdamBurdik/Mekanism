package me.adamix.mekanism.views;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.ViewType;
import me.devnatan.inventoryframework.context.RenderContext;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BasicEnergyCubeView extends View {

	@Override
	public void onInit(ViewConfigBuilder config) {
		config.title("§fӫ七七七㈁");
		config.type(ViewType.CHEST);
		config.size(3);
	}
}
