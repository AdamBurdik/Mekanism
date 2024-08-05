package me.adamix.mekanism.blocks;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BlockManager {

	private static final ConcurrentMap<String, Class<? extends MekanismBlock>> registeredBlocks = new ConcurrentHashMap<>();
	private static final ConcurrentMap<Location, MekanismBlock> blocks = new ConcurrentHashMap<>();

	public static void registerBlock(String id, Class<? extends MekanismBlock> clazz) {
		if (id == null || clazz == null) {
			throw new IllegalArgumentException("Block ID and class must not be null.");
		}

		if (!MekanismBlock.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException("Class must extend MekanismBlock.");
		}

		registeredBlocks.put(id, clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T extends MekanismBlock> T getBlock(String id, Block block) {
		if (id == null) {
			;
			throw new IllegalArgumentException("Block ID must not be null.");
		}

		Class<? extends MekanismBlock> clazz = registeredBlocks.get(id);

		if (clazz == null) {
			throw new RuntimeException("No block registered with ID: " + id);
		}

		try {
			Constructor<? extends MekanismBlock> constructor = clazz.getConstructor(String.class, Block.class);
			return (T) constructor.newInstance(id, block);

		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
		         InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T extends MekanismBlock> T getBlock(String id) {
		return getBlock(id, null);
	}

	public static void onBlockPlace(Block block, String blockId, Player player) {
		MekanismBlock mekanismBlock = getBlock(blockId, block);

		block.setType(mekanismBlock.getBlockMaterial());
		Waterlogged waterlogged = (Waterlogged) block.getBlockData();
		waterlogged.setWaterlogged(false);
		block.setBlockData(waterlogged);

		mekanismBlock.onPlace(player);
		blocks.put(block.getLocation(), mekanismBlock);
		mekanismBlock.updateSurroundingBlocks();
	}

	public static void onBlockBreak(Block block, Player player) {
		MekanismBlock mekanismBlock = getBlock(block);
		if (mekanismBlock == null) {
			return;
		}
		blocks.remove(block.getLocation());
		mekanismBlock.onBreak(player);
		ArmorStand armorStand = mekanismBlock.getArmorStand();
		if (armorStand != null) {
			armorStand.remove();
		}
		mekanismBlock.updateSurroundingBlocks();
	}

	public static MekanismBlock getBlock(Block block) {
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

	public static ConcurrentMap<Location, MekanismBlock> getBlocks() {
		return blocks;
	}

	public static int getSide(Block sourceBlock, Block block) {
		if (sourceBlock.getY() != block.getY()) {
			return sourceBlock.getY() < block.getY() ? 0 : 1;
		}
		if (sourceBlock.getZ() != block.getZ()) {
			return sourceBlock.getZ() < block.getZ() ? 2 : 3;
		}
		return sourceBlock.getX() < block.getX() ? 4 : (sourceBlock.getX() > block.getX() ? 5 : -1);
	}

}
