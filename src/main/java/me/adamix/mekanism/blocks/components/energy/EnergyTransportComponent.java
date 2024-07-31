package me.adamix.mekanism.blocks.components.energy;

import me.adamix.mekanism.blocks.components.MekanismComponent;

public class EnergyTransportComponent implements MekanismComponent {

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
