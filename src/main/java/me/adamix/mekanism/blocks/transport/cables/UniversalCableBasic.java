package me.adamix.mekanism.blocks.transport.cables;

import me.adamix.mekanism.Mekanism;
import me.adamix.mekanism.blocks.ElectricityBlock;
import me.adamix.mekanism.blocks.components.EnergyTransportComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class UniversalCableBasic extends ElectricityBlock {
	private static final int BLOCK_CMD = 5000;
	private static final Material BLOCK_MATERIAL = Material.CONDUIT;

	public UniversalCableBasic(String id) {
		super(id);
	}

	@Override
	public void onPlace(Block block, Player player) {
		EnergyTransportComponent energyTransportComponent = new EnergyTransportComponent(3200);
		addComponent(energyTransportComponent);

		CableManager.placeCable(block, this);
	}

	@Override
	public void onBreak(Block block, Player player) {
		CableManager.breakCable(block, this);
	}

	@Override
	public void onBlockUpdate(Block block) {
		CableManager.updateBlock(block, this);
	}

	@Override
	public ItemStack getItem() {
		NamespacedKey key = new NamespacedKey(Mekanism.getInstance(), "block-type");
		ItemStack item = new ItemStack(Material.CONDUIT);

		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, getId());
		meta.displayName(MiniMessage.miniMessage().deserialize("<yellow>Basic Universal Cable"));
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
