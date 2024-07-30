package me.adamix.mekanism.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
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
	public static <T extends ElectricityBlock> T getBlock(String id, Block block) {
		if (id == null) {
			;
			throw new IllegalArgumentException("Block ID must not be null.");
		}

		Class<? extends ElectricityBlock> clazz = registeredBlocks.get(id);

		if (clazz == null) {
			throw new RuntimeException(STR."No block registered with ID: \{id}");
		}

		try {
			Constructor<? extends ElectricityBlock> constructor = clazz.getConstructor(String.class, Block.class);
			return (T) constructor.newInstance(id, block);

		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
		         InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T extends ElectricityBlock> T getBlock(String id) {
		return getBlock(id, null);
	}

	public static void onBlockPlace(Block block, String blockId, Player player) {
		ElectricityBlock electricityBlock = getBlock(blockId, block);

		block.setType(electricityBlock.getBlockMaterial());
		Waterlogged waterlogged = (Waterlogged) block.getBlockData();
		waterlogged.setWaterlogged(false);
		block.setBlockData(waterlogged);

		electricityBlock.onPlace(player);
		blocks.put(block.getLocation(), electricityBlock);
		electricityBlock.updateSurroundingBlocks();
	}

	public static void onBlockBreak(Block block, Player player) {
		ElectricityBlock electricityBlock = getBlock(block);
		if (electricityBlock == null) {
			return;
		}
		blocks.remove(block.getLocation());
		electricityBlock.onBreak(player);
		ArmorStand armorStand = electricityBlock.getArmorStand();
		if (armorStand != null) {
			armorStand.remove();
		}
		electricityBlock.updateSurroundingBlocks();
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

	public static ConcurrentMap<Location, ElectricityBlock> getBlocks() {
		return blocks;
	}

	// TODO Make this function better
	public static int getSide(Block sourceBlock, Block block) {
		if (sourceBlock.getY() < block.getY()) {
			return 0;
		}
		if (sourceBlock.getY() > block.getY()){
			return 1;
		}

		if (sourceBlock.getZ() < block.getZ()) {
			return 2;
		}
		if (sourceBlock.getZ() > block.getZ()) {
			return 3;
		}

		if (sourceBlock.getX() < block.getX()) {
			return 4;
		}
		if (sourceBlock.getX() > block.getX()) {
			return 5;
		}

		return -1;
	}

}
