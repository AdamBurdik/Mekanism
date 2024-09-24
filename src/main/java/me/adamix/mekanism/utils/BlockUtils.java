package me.adamix.mekanism.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class BlockUtils {

	public static int getSide(Location sourceBlock, Location block) {
		if (sourceBlock.getY() != block.getY()) {
			return sourceBlock.getY() < block.getY() ? 0 : 1;
		}
		if (sourceBlock.getZ() != block.getZ()) {
			return sourceBlock.getZ() < block.getZ() ? 2 : 3;
		}
		return sourceBlock.getX() < block.getX() ? 4 : (sourceBlock.getX() > block.getX() ? 5 : -1);
	}

	public static Location[] getSurroundingBlocks(Location location) {
		Location[] surroundingCables = new Location[6];

		surroundingCables[0] = location.clone().add(0, 1, 0); // UP
		surroundingCables[1] = location.clone().add(0, -1, 0); // DOWN
		surroundingCables[2] = location.clone().add(0, 0, 1); // SOUTH
		surroundingCables[3] = location.clone().add(0, 0, -1); // NORTH
		surroundingCables[4] = location.clone().add(1, 0, 0); // EAST
		surroundingCables[5] = location.clone().add(-1, 0, 0); // WEST

		return surroundingCables;
	}

}
