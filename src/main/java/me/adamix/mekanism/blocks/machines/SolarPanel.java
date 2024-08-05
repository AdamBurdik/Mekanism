package me.adamix.mekanism.blocks.machines;

import me.adamix.mekanism.Mekanism;
import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.blocks.components.energy.EnergyOutputComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyStorageComponent;
import me.adamix.mekanism.transports.EnergyTransport;
import me.adamix.mekanism.views.ViewManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


public class SolarPanel extends MekanismBlock {
	private static final int BLOCK_CMD = 200;
	private static final Material BLOCK_MATERIAL = Material.BARRIER;
	public SolarPanel(String id, Block block) {
		super(id, block);
	}

	@Override
	public void onPlace(Player player) {
		var energyStorageComponent = new EnergyStorageComponent(96000);
		var energyOutputComponent = new EnergyOutputComponent(45, new boolean[]{false, true, false, false, false, false});
		addComponent(energyStorageComponent, energyOutputComponent);

		getBlock().setType(BLOCK_MATERIAL);
		spawnArmorStand();
	}

	@Override
	public void onUpdate() {
		var energyStorageComponent = getComponent(EnergyStorageComponent.class);
		if (!energyStorageComponent.isEmpty()) {
			EnergyTransport.sendEnergy(this, energyStorageComponent.getCurrentEnergyCapacity());
		}
		// TEMPORARY GENERATION OF ENERGY
		energyStorageComponent.addEnergy(60);

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
