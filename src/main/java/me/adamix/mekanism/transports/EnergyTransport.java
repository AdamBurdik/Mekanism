package me.adamix.mekanism.transports;

import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.blocks.components.energy.EnergyStorageComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyTransportComponent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class EnergyTransport {
	private final List<MekanismBlock> alreadyProcessedBlocks = new ArrayList<>();

	private EnergyTransportOutput getClosestMekanismBlock(MekanismBlock source, float energyAmount, long distance) {
		alreadyProcessedBlocks.add(source);
		if (distance < 1) {
			return null;
		}

		Block[] surroundingBlocks = BlockManager.getSurroundingBlocks(source.getBlock());
		for (Block block : surroundingBlocks) {
			MekanismBlock surroundingBlock = BlockManager.getBlock(block);
			if (surroundingBlock == null || alreadyProcessedBlocks.contains(surroundingBlock)) {
				continue;
			}

			alreadyProcessedBlocks.add(surroundingBlock);

			if (!source.canConnect(surroundingBlock, TransportType.INPUT)) {
				continue;
			}

			if (surroundingBlock.hasComponent(EnergyStorageComponent.class)) {
				var energyStorageComponent = surroundingBlock.getComponent(EnergyStorageComponent.class);
				if (energyStorageComponent.isFull()) {
					continue;
				}

				return new EnergyTransportOutput(surroundingBlock, energyAmount);
			}

			if (surroundingBlock.hasComponent(EnergyTransportComponent.class)) {
				var energyTransportComponent = surroundingBlock.getComponent(EnergyTransportComponent.class);

				EnergyTransportOutput output = getClosestMekanismBlock(surroundingBlock, Math.min(energyAmount, energyTransportComponent.getEnergyTransportRate()), distance - 1);
				if (output == null) {
					continue;
				}

				return output;
			}
		}

		return null;
	}

	public static void sendEnergy(MekanismBlock source, float energyAmount) {
		EnergyTransport energyTransport = new EnergyTransport();

		EnergyTransportOutput energyTransportOutput = energyTransport.getClosestMekanismBlock(source, energyAmount,Long.MAX_VALUE);
		if (energyTransportOutput == null) {
			return;
		}

		MekanismBlock closestMekanismBlock = energyTransportOutput.mekanismBlock();
		float realEnergyAmount = energyTransportOutput.maxEnergy();

		if (!closestMekanismBlock.hasComponent(EnergyStorageComponent.class)) {
			return;
		}

		var sourceEnergyStorage = source.getComponent(EnergyStorageComponent.class);
		var otherEnergyStorage = closestMekanismBlock.getComponent(EnergyStorageComponent.class);

		float energyFit = otherEnergyStorage.getMaxEnergyCapacity() - otherEnergyStorage.getCurrentEnergyCapacity();
		float energyToTransport = Math.min(energyFit, realEnergyAmount);

		sourceEnergyStorage.removeEnergy(energyToTransport);
		otherEnergyStorage.addEnergy(energyToTransport);
	}

}
