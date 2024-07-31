package me.adamix.mekanism.blocks.components.energy;

import me.adamix.mekanism.blocks.components.MekanismComponent;

import java.util.Arrays;

public class EnergyInputComponent implements MekanismComponent {

	private boolean[] energyInputSides = new boolean[6];

	public EnergyInputComponent() {
	}

	public EnergyInputComponent(boolean[] energyInputSides) {
		this.energyInputSides = energyInputSides;
	}

	public boolean[] getEnergyInputSides() {
		return energyInputSides;
	}

	@Override
	public String toString() {
		return STR."EnergyInputComponent{energyInputSides=\{Arrays.toString(energyInputSides)}}";
	}

}
