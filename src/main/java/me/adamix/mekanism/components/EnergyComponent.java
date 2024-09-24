package me.adamix.mekanism.components;

import org.bukkit.Location;

public interface EnergyComponent {

	default void storeEnergy(Location location, long amount) {
		setStoredEnergy( location, Math.min(Math.max(0, getStoredEnergy(location) + amount), Long.MAX_VALUE));
	}

	default long extractEnergy(Location location, long amount) {
		long storedEnergy = getStoredEnergy(location);
		if (storedEnergy < amount) {
			setStoredEnergy(location, 0);
			return storedEnergy;
		} else {
			setStoredEnergy(location, storedEnergy - amount);
			return amount;
		}
	}

	long getStoredEnergy(Location location);
	void setStoredEnergy(Location location, long amount);
	long getEnergyCapacity(Location location);
	boolean[] getInputSides(Location location);
	boolean[] getOutputSides(Location location);
	void setInputSides(Location location, boolean[] inputSides);
	void setOutputSides(Location location, boolean[] outputSides);

}