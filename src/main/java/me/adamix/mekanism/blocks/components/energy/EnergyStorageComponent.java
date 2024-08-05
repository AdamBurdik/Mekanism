package me.adamix.mekanism.blocks.components.energy;

import me.adamix.mekanism.blocks.components.MekanismComponent;
import org.bukkit.Bukkit;

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

	public boolean isFull() {
		return currentEnergyCapacity >= maxEnergyCapacity;
	}

	public boolean isEmpty() {
		return currentEnergyCapacity <= 0;
	}

	@Override
	public String toString() {
		return "EnergyStorageComponent{currentEnergyCapacity=" + currentEnergyCapacity + ",maxEnergyCapacity=}" + maxEnergyCapacity;
	}

}
