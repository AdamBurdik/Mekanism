package me.adamix.mekanism.blocks.memory;

import org.bukkit.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BlockMemory {
	private final Map<Location, Memory> memories = new HashMap<>();

	public Memory getMemory(Location location) {
		return memories.get(location);
	}

	public void setMemory(Location location, Memory memory) {
		memories.put(location, memory);
	}

	public Memory createMemory(Location location) {
		Memory memory = new Memory();
		setMemory(location, memory);
		return memory;
	}

	public Collection<Memory> getMemorySet() {
		return memories.values();
	}
}
