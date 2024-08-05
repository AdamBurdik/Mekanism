package me.adamix.mekanism.blocks.components.energy;

import me.adamix.mekanism.blocks.components.MekanismComponent;

public class EnergyGeneratorComponent implements MekanismComponent {
	private final float generateRate;

	public EnergyGeneratorComponent(float generateRate) {
		this.generateRate = generateRate;
	}

	public float getGenerateRate() {
		return generateRate;
	}

	@Override
	public String toString() {
		return "EnergyGeneratorComponent{generateRate=}" + generateRate;
	}
}
