package me.adamix.mekanism.listener;

import me.adamix.mekanism.Mekanism;
import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.ElectricityBlock;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		NamespacedKey key = new NamespacedKey(Mekanism.getInstance(), "block-type");
		ItemStack item = event.getItemInHand();
		ItemMeta meta = item.getItemMeta();

		PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
		if (!persistentDataContainer.has(key)) {
			return;
		}

		String blockId = persistentDataContainer.get(key, PersistentDataType.STRING);

		BlockManager.onBlockPlace(event.getBlock(), blockId, event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		BlockManager.onBlockBreak(event.getBlock(), event.getPlayer());
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getClickedBlock() == null) {
			return;
		}
		if (event.getAction().isRightClick()) {
			ElectricityBlock electricityBlock = BlockManager.getBlock(event.getClickedBlock());
			if (electricityBlock != null) {
				electricityBlock.onRightClick(event.getClickedBlock(), event.getPlayer());
			}
		} else if (event.getAction().isLeftClick()) {
			ElectricityBlock electricityBlock = BlockManager.getBlock(event.getClickedBlock());
			if (electricityBlock != null) {
				electricityBlock.onLeftClick(event.getClickedBlock(), event.getPlayer());
			}
		}
	}

}
