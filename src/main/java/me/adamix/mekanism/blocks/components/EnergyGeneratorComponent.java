package me.adamix.mekanism.blocks.components;

public class EnergyGeneratorComponent implements ElectricityComponent{
	private final float generateRate;

	public EnergyGeneratorComponent(float generateRate) {
		this.generateRate = generateRate;
	}

	public float getGenerateRate() {
		return generateRate;
	}
}
