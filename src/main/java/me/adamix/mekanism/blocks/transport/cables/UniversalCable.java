package me.adamix.mekanism.blocks.transport.cables;

import me.adamix.mekanism.Mekanism;
import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.blocks.components.energy.EnergyTransportComponent;
import me.adamix.mekanism.transports.TransportType;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class UniversalCable extends MekanismBlock {
	private final String name;
	private final int customModelData;

	public UniversalCable(String id, Block block, String name, int customModelData, int energyTransportRate) {
		super(id, block);
		this.name = name;
		this.customModelData = customModelData;

		var energyTransportComponent = new EnergyTransportComponent(energyTransportRate);
		addComponent(energyTransportComponent);
	}

	@Override
	public void onPlace(Player player) {
		spawnArmorStand();
		onBlockUpdate();
	}

	@Override
	public void onBreak(Player player) {
		getArmorStand().remove();
		updateSurroundingBlocks();
	}

	@Override
	public void onBlockUpdate() {
		Block[] surroundingBlocks = BlockManager.getSurroundingBlocks(getBlock());

		StringBuilder stringModel = new StringBuilder();
		for (Block surroundingBlock : surroundingBlocks) {
			MekanismBlock mekanismBlock = BlockManager.getBlock(surroundingBlock);
			if (mekanismBlock == null || !mekanismBlock.canConnect(this, TransportType.ANY)) {
				stringModel.append("0");
			} else {
				stringModel.append("1");
			}
		}

		ItemStack item = getItem();
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(this.customModelData + Integer.parseInt(stringModel.toString(), 2));
		item.setItemMeta(meta);

		ArmorStand oldArmorStand = getArmorStand();

		ArmorStand newArmorStand = spawnArmorStand();
		newArmorStand.getEquipment().setHelmet(item);

		if (oldArmorStand != null) {
			Bukkit.getScheduler().runTaskLater(Mekanism.getInstance(), new Runnable() {
				@Override
				public void run() {
					oldArmorStand.remove();
				}
			}, 1L);
		}

	}

	@Override
	public ItemStack getItem() {
		NamespacedKey key = new NamespacedKey(Mekanism.getInstance(), "block-type");
		ItemStack item = new ItemStack(Material.CONDUIT);

		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, getId());
		meta.displayName(MiniMessage.miniMessage().deserialize(this.name));
		meta.setCustomModelData(this.customModelData);
		item.setItemMeta(meta);

		return item;
	}

	@Override
	public int getBlockCMD() {
		return this.customModelData;
	}

	@Override
	public Material getBlockMaterial() {
		return Material.CONDUIT;
	}
}
