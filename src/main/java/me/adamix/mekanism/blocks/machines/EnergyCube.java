package me.adamix.mekanism.blocks.machines;

import me.adamix.mekanism.Mekanism;
import me.adamix.mekanism.blocks.ElectricityBlock;
import me.adamix.mekanism.blocks.components.EnergyInputComponent;
import me.adamix.mekanism.blocks.components.EnergyOutputComponent;
import me.adamix.mekanism.blocks.components.EnergyStorageComponent;
import me.adamix.mekanism.blocks.components.EnergyTransportComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class EnergyCube extends ElectricityBlock {
	private static final int BLOCK_CMD = 100;
	private static final Material BLOCK_MATERIAL = Material.BARRIER;
	public EnergyCube(String id, Block block) {
		super(id, block);
	}

	@Override
	public void onPlace(Player player) {
		var energyStorageComponent = new EnergyStorageComponent(2000000);
		var energyOutputComponent = new EnergyOutputComponent(16000, new boolean[]{true, false, true, false, true, false});
		var energyInputComponent = new EnergyInputComponent(new boolean[]{false, false, false, false, false, false});
		addComponent(energyStorageComponent, energyOutputComponent, energyInputComponent);

		spawnArmorStand();
	}

	@Override
	public ItemStack getItem() {
		NamespacedKey key = new NamespacedKey(Mekanism.getInstance(), "block-type");
		ItemStack item = new ItemStack(Material.BEACON);

		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, getId());
		meta.displayName(MiniMessage.miniMessage().deserialize("<yellow>Energy Cube"));
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
