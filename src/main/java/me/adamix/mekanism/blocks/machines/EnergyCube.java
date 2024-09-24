package me.adamix.mekanism.blocks.machines;

import me.adamix.mekanism.MekanismPlugin;
import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.components.EnergyComponent;
import me.adamix.mekanism.utils.EntityUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.UUID;

public class EnergyCube extends MekanismBlock implements EnergyComponent {

	private final long energyCapacity;
	private final Material blockType = Material.BARRIER;
	private final Material itemType = Material.BEACON;

	public EnergyCube(int customModelData, long energyCapacity) {
		super(customModelData);
		this.energyCapacity = energyCapacity;
	}

	@Override
	public void onPlace(Location location) {
		location.getBlock().setType(blockType);
		var entity = EntityUtils.spawnEntity(location, customModelData, itemType);

		var memory = blockMemory.createMemory(location);
		memory.set("entity", entity.getUniqueId());
		memory.set("input_sides", new boolean[] {true, true, true, false, false, false});
		memory.set("output_sides", new boolean[] {false, false, false, true, false, false});
		memory.set("energy_capacity", energyCapacity);
		memory.set("stored_energy", 0L);
	}

	@Override
	public void setInputSides(Location location, boolean[] inputSides) {
		var memory = blockMemory.getMemory(location);
		memory.set("input_sides", inputSides);
	}

	@Override
	public void setOutputSides(Location location, boolean[] outputSides) {
		var memory = blockMemory.getMemory(location);
		memory.set("output_sides", outputSides);
	}

	@Override
	public boolean[] getInputSides(Location location) {
		var memory = blockMemory.getMemory(location);
		return memory.getBooleanArray("input_sides");
	}

	@Override
	public boolean[] getOutputSides(Location location) {
		var memory = blockMemory.getMemory(location);
		return memory.getBooleanArray("output_sides");
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