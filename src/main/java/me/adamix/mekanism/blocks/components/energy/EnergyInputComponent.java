package me.adamix.mekanism.blocks.components.energy;

import me.adamix.mekanism.blocks.capabilities.CapabilityType;
import me.adamix.mekanism.blocks.capabilities.InputCapability;
import me.adamix.mekanism.blocks.components.MekanismComponent;

import java.util.Arrays;

public class EnergyInputComponent implements MekanismComponent, InputCapability {

	private boolean[] energyInputSides;

	public EnergyInputComponent(boolean[] energyInputSides) {
		this.energyInputSides = energyInputSides;
	}

	@Override
	public CapabilityType getType() {
		return CapabilityType.ENERGY;
	}

	@Override
	public boolean[] getInputSides() {
		return this.energyInputSides;
	}

	@Override
	public void setInputSides(boolean[] inputSides) {

	}
}
