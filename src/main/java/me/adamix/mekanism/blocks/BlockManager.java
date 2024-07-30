package me.adamix.mekanism.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BlockManager {

	private static final ConcurrentMap<String, Class<? extends ElectricityBlock>> registeredBlocks = new ConcurrentHashMap<>();
	private static final ConcurrentMap<Location, ElectricityBlock> blocks = new ConcurrentHashMap<>();

	public static void registerBlock(String id, Class<? extends ElectricityBlock> clazz) {
		if (id == null || clazz == null) {
			throw new IllegalArgumentException("Block ID and class must not be null.");
		}

		if (!ElectricityBlock.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException("Class must extend ElectricityBlock.");
		}

		registeredBlocks.put(id, clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T extends ElectricityBlock> T getBlock(String id) {
		if (id == null) {;
			throw new IllegalArgumentException("Block ID must not be null.");
		}

		Class<? extends ElectricityBlock> clazz = registeredBlocks.get(id);

		if (clazz == null) {
			throw new RuntimeException("No block registered with ID: " + id);
		}

		try {
			Constructor<? extends ElectricityBlock> constructor = clazz.getConstructor(String.class);
			return (T) constructor.newInstance(id);

		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static void onBlockPlace(Block block, String blockId, Player player) {
		ElectricityBlock electricityBlock = getBlock(blockId);
		block.setType(electricityBlock.getBlockMaterial());
		electricityBlock.onPlace(block, player);
		blocks.put(block.getLocation(), electricityBlock);
		electricityBlock.updateSurroundingBlocks(block);
	}

	public static void onBlockBreak(Block block, Player player) {
		ElectricityBlock electricityBlock = getBlock(block);
		if (electricityBlock == null) {
			return;
		}
		blocks.remove(block.getLocation());
		electricityBlock.onBreak(block, player);
		ArmorStand armorStand = electricityBlock.getArmorStand();
		if (armorStand != null) {
			armorStand.remove();
		}
		electricityBlock.updateSurroundingBlocks(block);
	}

	public static ElectricityBlock getBlock(Block block) {
		return blocks.get(block.getLocation());
	}

	public static Block[] getSurroundingBlocks(Block block) {
		Block[] surroundingCables = new Block[6];

		surroundingCables[0] = block.getLocation().add(0, 1, 0).getBlock(); // UP
		surroundingCables[1] = block.getLocation().add(0, -1, 0).getBlock(); // DOWN
		surroundingCables[2] = block.getLocation().add(0, 0, 1).getBlock(); // SOUTH
		surroundingCables[3] = block.getLocation().add(0, 0, -1).getBlock(); // NORTH
		surroundingCables[4] = block.getLocation().add(1, 0, 0).getBlock(); // EAST
		surroundingCables[5] = block.getLocation().add(-1, 0, 0).getBlock(); // WEST

		return surroundingCables;
	}

}
