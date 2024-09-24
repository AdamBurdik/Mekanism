package me.adamix.mekanism.utils;

import me.adamix.mekanism.MekanismPlugin;
import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.components.CableComponent;
import me.adamix.mekanism.components.EnergyComponent;
import me.adamix.mekanism.managers.BlockManager;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectionUtils {

	public static boolean canCableConnect(MekanismBlock cable, MekanismBlock block, Location cableLocation, Location blockLocation) {

		if (cable.getCustomModelData() == block.getCustomModelData()) {
			return true;
		}

		int otherSide = BlockUtils.getSide(blockLocation, cableLocation);

		if (block instanceof EnergyComponent energyComponent) {
			boolean[] inputSides = energyComponent.getInputSides(blockLocation);
			boolean[] outputSides = energyComponent.getOutputSides(blockLocation);

			if (inputSides[otherSide] || outputSides[otherSide]) {
				return true;
			}
		}
		// Other Checks

		return false;
	}

	public static boolean canConnect(MekanismBlock source, MekanismBlock other, Location sourceLocation, Location otherLocation) {
		int otherSide = BlockUtils.getSide(otherLocation, sourceLocation);

		if (other instanceof EnergyComponent energyComponent) {
			boolean[] inputSides = energyComponent.getInputSides(otherLocation);

			if (inputSides[otherSide]) {
				return true;
			}
		}
		// Other Checks

		return false;
	}

	public static EnergyBlockOutputRecord getEnergyBlockOnNetwork(MekanismBlock block, Location location, long maxTransportRate, int distance, List<Location> alreadyProcessed) {
		BlockManager blockManager = MekanismPlugin.getBlockManager();

		alreadyProcessed.add(location);
		if (distance < 1) {
			return null;
		}

		Location[] surroundings = BlockUtils.getSurroundingBlocks(location);

		for (Location surrounding : surroundings) {
			if (alreadyProcessed.contains(surrounding) | !blockManager.exists(surrounding)) {
				continue;
			}

			alreadyProcessed.add(surrounding);

			String id = blockManager.getId(surrounding);
			MekanismBlock other = blockManager.getRegistered(id);
			if (other instanceof EnergyComponent energyComponent) {
				if (block instanceof CableComponent) {
					if (!canCableConnect(block, other, location, surrounding)) {
						continue;
					}
				}

				if (!canConnect(block, other, location, surrounding)) {
					continue;
				}

				if (energyComponent.getStoredEnergy(surrounding) >= energyComponent.getEnergyCapacity(surrounding)) {
					continue;
				}

				return new EnergyBlockOutputRecord(other, surrounding, maxTransportRate);
			}
			if (other instanceof CableComponent cableComponent) {
				if (!canCableConnect(other, block, surrounding, location)) {
					continue;
				}

				long transportRate = cableComponent.getTransportRate(surrounding);
				maxTransportRate = Math.max(transportRate, maxTransportRate);

				var output = getEnergyBlockOnNetwork(other, surrounding, maxTransportRate, distance - 1, alreadyProcessed);
				if (output == null) {
					continue;
				}

				return output;
			}
		}
		return null;
	}

}
