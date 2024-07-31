package me.adamix.mekanism.blocks.components.energy;

import me.adamix.mekanism.blocks.components.MekanismComponent;
import me.adamix.mekanism.blocks.components.MekanismInputComponent;
import me.adamix.mekanism.blocks.components.MekanismOutputComponent;

import java.util.Arrays;

public class EnergyInputComponent implements MekanismComponent, MekanismInputComponent {

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
