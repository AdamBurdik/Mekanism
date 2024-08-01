package me.adamix.mekanism.blocks.components.energy;

import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.blocks.capabilities.Capability;
import me.adamix.mekanism.blocks.capabilities.CapabilityType;
import me.adamix.mekanism.blocks.capabilities.TransportCapability;
import me.adamix.mekanism.blocks.components.MekanismComponent;

public class EnergyTransportComponent implements MekanismComponent, TransportCapability {

	private final int energyTransportRate;

	public EnergyTransportComponent(int energyTransportRate) {
		this.energyTransportRate = energyTransportRate;
	}

	public int getEnergyTransportRate() {
		return energyTransportRate;
	}

	@Override
	public CapabilityType getType() {
		return CapabilityType.ENERGY;
	}
}
