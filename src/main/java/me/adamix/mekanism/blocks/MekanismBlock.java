package me.adamix.mekanism.blocks;

import me.adamix.mekanism.blocks.memory.BlockMemory;
import org.bukkit.Location;
import org.bukkit.Material;

public abstract class MekanismBlock {
	protected int customModelData;
	protected BlockMemory blockMemory = new BlockMemory();

	public MekanismBlock(int customModelData) {
		this.customModelData = customModelData;
	}

	public void onBreak(Location location) {
		location.getBlock().setType(Material.AIR);
	}

	public abstract void onPlace(Location location);
	public abstract Material getBlockType();
	public abstract Material getItemType();

	public void onBlockUpdate(Location location) {}
	public void tick(Location location) {}
	public void onEntitySpawn(Location location) {}

	public int getCustomModelData() {
		return customModelData;
	}

	public BlockMemory getBlockMemory() {
		return this.blockMemory;
	}
}