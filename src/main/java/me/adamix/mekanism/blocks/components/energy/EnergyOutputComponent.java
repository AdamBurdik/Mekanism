package me.adamix.mekanism.blocks.components.energy;

import me.adamix.mekanism.blocks.components.MekanismComponent;

import java.util.Arrays;

public class EnergyOutputComponent implements MekanismComponent {

	private final float energyOutputRate;
	private boolean[] energyOutputSides = new boolean[6];

	public EnergyOutputComponent(float energyOutputRate) {
		this.energyOutputRate = energyOutputRate;
	}

	public EnergyOutputComponent(float energyOutputRate, boolean[] energyOutputSides) {
		this.energyOutputRate = energyOutputRate;
		this.energyOutputSides = energyOutputSides;
	}

	public float getEnergyOutputRate() {
		return energyOutputRate;
	}

	public boolean[] getEnergyOutputSides() {
		return energyOutputSides;
	}

	@Override
	public String toString() {
		return STR."EnergyOutputComponent{energyOutputRate=\{energyOutputRate},energyOutputSides=\{Arrays.toString(energyOutputSides)}}";
	}
}
