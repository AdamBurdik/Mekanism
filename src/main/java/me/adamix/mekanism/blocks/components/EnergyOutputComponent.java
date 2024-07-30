package me.adamix.mekanism.blocks.components;

public class EnergyOutputComponent implements ElectricityComponent {

	private final float energyOutputRate;

	public EnergyOutputComponent(float energyOutputRate) {
		this.energyOutputRate = energyOutputRate;
	}

	public float getEnergyOutputRate() {
		return energyOutputRate;
	}

	@Override
	public String toString() {
		return STR."EnergyOutputComponent{energyOutputRate=\{energyOutputRate}}";
	}
}
