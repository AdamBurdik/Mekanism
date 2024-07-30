package me.adamix.mekanism.blocks.machines;

import me.adamix.mekanism.Mekanism;
import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.ElectricityBlock;
import me.adamix.mekanism.blocks.components.EnergyStorageComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


public class SolarPanel extends ElectricityBlock {
	private static final int BLOCK_CMD = 200;
	private static final Material BLOCK_MATERIAL = Material.BARRIER;
	public SolarPanel(String id) {
		super(id);
	}

	@Override
	public void onPlace(Block block, Player player) {
		EnergyStorageComponent energyStorageComponent = new EnergyStorageComponent(96000, 45);
		addComponent(energyStorageComponent);

		block.setType(BLOCK_MATERIAL);
		spawnArmorStand(block);
	}

	@Override
	public ItemStack getItem() {
		NamespacedKey key = new NamespacedKey(Mekanism.getInstance(), "block-type");
		ItemStack item = new ItemStack(Material.DAYLIGHT_DETECTOR);

		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, getId());
		meta.displayName(MiniMessage.miniMessage().deserialize("<yellow>Solar Panel Generator"));
		meta.setCustomModelData(BLOCK_CMD);
		item.setItemMeta(meta);

		return item;
	}

	@Override
	public int getBlockCMD() {
		return BLOCK_CMD;
	}

	@Override
	public Material getBlockMaterial() {
		return BLOCK_MATERIAL;
	}

}
