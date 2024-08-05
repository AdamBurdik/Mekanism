package me.adamix.mekanism.views;

import me.adamix.mekanism.Mekanism;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.entity.Player;

public class ViewManager {
	private static ViewFrame viewFrame;

	public static void init(Mekanism mekanism) {
		viewFrame = ViewFrame.create(mekanism)
				.with(new BasicEnergyCubeView())
				.register();

	}

	public static void openMenu(Player player, Class<? extends BasicEnergyCubeView> clazz) {
		viewFrame.open(clazz, player);
	}

}
