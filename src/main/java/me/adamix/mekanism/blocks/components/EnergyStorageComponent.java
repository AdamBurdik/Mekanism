package me.adamix.mekanism.blocks.components;

public class EnergyStorageComponent implements ElectricityComponent {
	private int currentEnergyCapacity = 0;
	private final int maxEnergyCapacity;
	private final int outputRate;

	public EnergyStorageComponent(int maxEnergyCapacity, int outputRate) {
		this.maxEnergyCapacity = maxEnergyCapacity;
		this.outputRate = outputRate;
	}

	public void addEnergy(int amount) {
		currentEnergyCapacity = Math.min(currentEnergyCapacity + amount, maxEnergyCapacity);
	}

	public void removeEnergy( int amount) {
		currentEnergyCapacity = Math.max(currentEnergyCapacity - amount, 0);
	}

	public int getCurrentEnergyCapacity() {
		return currentEnergyCapacity;
	}

	public int getMaxEnergyCapacity() {
		return maxEnergyCapacity;
	}

	public int getOutputRate() {
		return outputRate;
	}

}
