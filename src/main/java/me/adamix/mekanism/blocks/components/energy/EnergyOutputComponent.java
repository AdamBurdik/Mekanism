package me.adamix.mekanism.blocks.components.energy;

import me.adamix.mekanism.blocks.capabilities.CapabilityType;
import me.adamix.mekanism.blocks.capabilities.OutputCapability;
import me.adamix.mekanism.blocks.components.MekanismComponent;

import java.util.Arrays;

public class EnergyOutputComponent implements MekanismComponent, OutputCapability {

	private final float energyOutputRate;
	private boolean[] energyOutputSides;

	public EnergyOutputComponent(float energyOutputRate, boolean[] energyOutputSides) {
		this.energyOutputRate = energyOutputRate;
		this.energyOutputSides = energyOutputSides;
	}

	public float getEnergyOutputRate() {
		return energyOutputRate;
	}

	@Override
	public CapabilityType getType() {
		return CapabilityType.ENERGY;
	}

	@Override
	public boolean[] getOutputSides() {
		return this.energyOutputSides;
	}

	@Override
	public void setOutputSides(boolean[] outputSides) {

	}
}
