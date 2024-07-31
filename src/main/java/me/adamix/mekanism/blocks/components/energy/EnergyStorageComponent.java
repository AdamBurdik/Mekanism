package me.adamix.mekanism.blocks.components.energy;

import me.adamix.mekanism.blocks.components.MekanismComponent;

public class EnergyStorageComponent implements MekanismComponent {
	private float currentEnergyCapacity = 0;
	private final float maxEnergyCapacity;

	public EnergyStorageComponent(int maxEnergyCapacity) {
		this.maxEnergyCapacity = maxEnergyCapacity;
	}

	public void addEnergy(float amount) {
		currentEnergyCapacity = Math.min(currentEnergyCapacity + amount, maxEnergyCapacity);
	}

	public void removeEnergy(float amount) {
		currentEnergyCapacity = Math.max(currentEnergyCapacity - amount, 0);
	}

	public void setEnergy(float amount) {
		currentEnergyCapacity = Math.min(amount, maxEnergyCapacity);
	}

	public void setEnergyToMax() {
		currentEnergyCapacity = maxEnergyCapacity;
	}

	public float getCurrentEnergyCapacity() {
		return currentEnergyCapacity;
	}

	public float getMaxEnergyCapacity() {
		return maxEnergyCapacity;
	}

	@Override
	public String toString() {
		return STR."EnergyStorageComponent{currentEnergyCapacity=\{currentEnergyCapacity},maxEnergyCapacity=\{maxEnergyCapacity}}";
	}

}
