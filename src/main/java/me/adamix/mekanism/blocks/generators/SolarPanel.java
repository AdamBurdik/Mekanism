package me.adamix.mekanism.blocks.generators;

import me.adamix.mekanism.MekanismPlugin;
import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.blocks.memory.BlockMemory;
import me.adamix.mekanism.components.EnergyComponent;
import me.adamix.mekanism.utils.ConnectionUtils;
import me.adamix.mekanism.utils.EnergyBlockOutputRecord;
import me.adamix.mekanism.utils.EntityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class SolarPanel extends MekanismBlock implements EnergyComponent {

	private final long energyCapacity;
	private final long generationRate;
	private final boolean[] energyOutputSides = {false, true, false, false, false, false};
	private final Material blockType = Material.BARRIER;
	private final Material itemType = Material.DAYLIGHT_DETECTOR;

	public SolarPanel(int customModelData, long energyCapacity, long generationRate) {
		super(customModelData);
		this.energyCapacity = energyCapacity;
		this.generationRate = generationRate;
	}

	@Override
	public void onPlace(Location location) {
		location.getBlock().setType(blockType);
		var entity = EntityUtils.spawnEntity(location, customModelData, itemType);

		var memory = blockMemory.createMemory(location);
		memory.set("entity", entity.getUniqueId());
		memory.set("energy_capacity", energyCapacity);
		memory.set("stored_energy", 0L);
		memory.set("generation_rate", generationRate);
	}

	private void sendEnergy(Location location) {
		var output = ConnectionUtils.getEnergyBlockOnNetwork(this, location, 500, 100, new ArrayList<>());
		if (output == null) {
			return;
		}

		MekanismBlock mekanismBlock = output.mekanismBlock();
		Location otherLocation = output.location();
		long maxTransportRate = output.maxTransportRate();

		if (mekanismBlock instanceof EnergyComponent energyComponent) {
			long storedEnergy = getStoredEnergy(location);
			long otherStoredEnergy = energyComponent.getStoredEnergy(otherLocation);
			long otherEnergyCapacity = energyComponent.getEnergyCapacity(otherLocation);

			long canFit = Math.max(0, otherEnergyCapacity - otherStoredEnergy);
			long canSend = Math.min(canFit, maxTransportRate);
			long result = Math.min(storedEnergy, canSend);

			setStoredEnergy(location, storedEnergy - result);
			energyComponent.setStoredEnergy(otherLocation, otherStoredEnergy + result);
		}

	}

	@Override
	public void tick(Location location) {
		var memory = blockMemory.getMemory(location);

		long rate = memory.getLong("generation_rate");
		long storedEnergy = memory.getLong("stored_energy");
		long energyCapacity = memory.getLong("energy_capacity");

		memory.set("stored_energy", Math.min(storedEnergy + rate, energyCapacity));
		sendEnergy(location);
	}

	@Override
	public void setInputSides(Location location, boolean[] inputSides) {
		return;
	}

	@Override
	public void setOutputSides(Location location, boolean[] outputSides) {
		return;
	}

	@Override
	public boolean[] getInputSides(Location location) {
		return new boolean[6];
	}

	@Override
	public boolean[] getOutputSides(Location location) {
		return this.energyOutputSides;
	}

	@Override
	public long getStoredEnergy(Location location) {
		var memory = blockMemory.getMemory(location);
		return memory.getLong("stored_energy");
	}

	@Override
	public void setStoredEnergy(Location location, long amount) {
		var memory = blockMemory.getMemory(location);
		memory.set("stored_energy", amount);
	}

	@Override
	public long getEnergyCapacity(Location location) {
		return this.energyCapacity;
	}

	@Override
	public Material getBlockType() {
		return blockType;
	}

	@Override
	public Material getItemType() {
		return itemType;
	}

}
