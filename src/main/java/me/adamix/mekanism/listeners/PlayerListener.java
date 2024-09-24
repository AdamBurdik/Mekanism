package me.adamix.mekanism.listeners;

import me.adamix.mekanism.MekanismPlugin;
import me.adamix.mekanism.managers.BlockManager;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerListener implements Listener {
	@EventHandler()
	public void onBlockPlace(BlockPlaceEvent event) {
		ItemStack item = event.getItemInHand();

		var data = item.getItemMeta().getPersistentDataContainer();
		var key = NamespacedKey.fromString("block_id", MekanismPlugin.getInstance());
		assert key != null;

		if (!data.has(key, PersistentDataType.STRING)) {
			return;
		}

		String id = data.get(key, PersistentDataType.STRING);

		BlockManager blockManager = MekanismPlugin.getBlockManager();
		blockManager.onPlace(id, event.getBlock().getLocation());
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		BlockManager blockManager = MekanismPlugin.getBlockManager();
		blockManager.onBreak(event.getBlock().getLocation());
	}

}
