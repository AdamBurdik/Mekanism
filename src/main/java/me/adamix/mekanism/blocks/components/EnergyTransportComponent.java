package me.adamix.mekanism.blocks.components;

public class EnergyTransportComponent implements ElectricityComponent{

	private final int energyTransportRate;

	public EnergyTransportComponent(int energyTransportRate) {
		this.energyTransportRate = energyTransportRate;
	}

	public int getEnergyTransportRate() {
		return energyTransportRate;
	}

	@Override
	public String toString() {
		return STR."EnergyTransportComponent{energyTransportRate=\{energyTransportRate}}";
	}

}
