package me.adamix.mekanism.blocks.transport.cables;

import me.adamix.mekanism.Mekanism;
import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.ElectricityBlock;
import me.adamix.mekanism.blocks.components.EnergyTransportComponent;
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

		spawnArmorStand(block);
		onBlockUpdate(block);
	}

	@Override
	public void onBreak(Block block, Player player) {
		getArmorStand().remove();
		updateSurroundingBlocks(block);
	}

	@Override
	public void onBlockUpdate(Block block) {
		Block[] surroundingBlocks = BlockManager.getSurroundingBlocks(block);

		StringBuilder stringModel = new StringBuilder();
		for (Block surroundingBlock : surroundingBlocks) {
			ElectricityBlock electricityBlock = BlockManager.getBlock(surroundingBlock);
			if (electricityBlock == null || !electricityBlock.hasComponent(EnergyTransportComponent.class)) {
				stringModel.append("0");
			} else {
				stringModel.append("1");
			}
		}

		ItemStack item = getItem();
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(BLOCK_CMD + Integer.parseInt(stringModel.toString(), 2));
		item.setItemMeta(meta);

		ArmorStand oldArmorStand = getArmorStand();

		ArmorStand newArmorStand = spawnArmorStand(block);
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
