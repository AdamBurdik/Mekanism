package me.adamix.mekanism.blocks.transport.cables;

import me.adamix.mekanism.Mekanism;
import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.ElectricityBlock;
import me.adamix.mekanism.blocks.components.ElectricityComponent;
import me.adamix.mekanism.blocks.components.EnergyTransportComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

// UP, DOWN, SOUTH, NORTH, EAST, WEST


public class CableManager {

	private static Mekanism plugin;
	private static ArmorStand templateArmorStand;

	public static void init(Mekanism pluginClass) {
		plugin = pluginClass;

		World world = Bukkit.getWorlds().getFirst();
		templateArmorStand = world.spawn(new Location(world, 0, -100, 0), ArmorStand.class);
		templateArmorStand.setGravity(false);
		templateArmorStand.setInvisible(true);
		templateArmorStand.setInvulnerable(true);
		templateArmorStand.setMarker(true);

		ItemStack item = new ItemStack(Material.CONDUIT);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(5000);
		item.setItemMeta(meta);
		templateArmorStand.getEquipment().setHelmet(item);
	}


	public static void updateBlock(Block block, ElectricityBlock electricityCable) {
		Block[] surroundingBlocks = BlockManager.getSurroundingBlocks(block);

		StringBuilder stringModel = new StringBuilder();

		for (Block surroundingBlock : surroundingBlocks) {
			ElectricityBlock electricityBlock = BlockManager.getBlock(surroundingBlock);
			if (electricityBlock == null) {
				stringModel.append("0");
				continue;
			}
			if (electricityBlock.hasComponent(EnergyTransportComponent.class)) {
				stringModel.append("1");
			} else {
				stringModel.append("0");
			}
		}

		ItemStack item = new ItemStack(Material.CONDUIT);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(5000 + Integer.parseInt(stringModel.toString(), 2));
		item.setItemMeta(meta);

		ArmorStand oldArmorStand = electricityCable.getArmorStand();

		ArmorStand newArmorStand = electricityCable.spawnArmorStand(block, templateArmorStand);
		newArmorStand.getEquipment().setHelmet(item);

		if (oldArmorStand != null) {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {
					oldArmorStand.remove();
				}
			}, 1L);
		}

	}

	public static void placeCable(Block block, ElectricityBlock electricityCable) {
		updateBlock(block, electricityCable);

		block.setType(Material.CONDUIT);
		Waterlogged waterlogged = (Waterlogged) block.getBlockData();
		waterlogged.setWaterlogged(false);
		block.setBlockData(waterlogged);

//		ElectricityBlock[] surroundingBlocks = BlockManager.getSurroundingBlocks(block);
//		for (ElectricityBlock surroundingBlock : surroundingBlocks) {
//			if (surroundingBlock == null) {
//				continue;
//			}
//			surroundingBlock.onBlockUpdate(block);
//		}
	}

	public static void breakCable(Block block, ElectricityBlock electricityCable) {
		ArmorStand armorStand = electricityCable.getArmorStand();
		armorStand.remove();

		electricityCable.updateSurroundingBlocks(block);
	}

}
