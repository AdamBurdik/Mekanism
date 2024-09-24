package me.adamix.mekanism.managers;

import me.adamix.mekanism.MekanismPlugin;
import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.utils.BlockUtils;
import me.adamix.mekanism.utils.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockManager {
	private final Map<String, MekanismBlock> registeredBlocks = new HashMap<>();
	private final Map<Location, String> blocks = new HashMap<>();
	private final ExecutorService executorService = Executors.newFixedThreadPool(10);

	public void register(String id, MekanismBlock block) {
		registeredBlocks.put(id, block);
	}

	public void unregister(String id) {
		registeredBlocks.remove(id);
	}

	public boolean registered(String id) {
		return registeredBlocks.containsKey(id);
	}

	public boolean registered(Location location) {
		return registered(getId(location));
	}

	public void onPlace(String id, Location location) {
		blocks.put(location, id);
		var block = getRegistered(id);
		block.onPlace(location);
		block.onBlockUpdate(location);

		updateSurrounding(location);
	}

	public void onBreak(Location location) {
		if (!registered(location)) {
			return;
		}

		var block = getRegistered(location);
		block.onBreak(location);
		blocks.remove(location);

		var memory = block.getBlockMemory().getMemory(location);
		UUID uuid = memory.getUUID("entity");
		EntityUtils.removeEntity(uuid);
		uuid = memory.getUUID("hologram");
		EntityUtils.removeEntity(uuid);

		updateSurrounding(location);
	}

	public void updateSurrounding(Location location) {
		Location[] surroundings = BlockUtils.getSurroundingBlocks(location);
		for (Location surrounding : surroundings) {
			if (!exists(surrounding)) {
				continue;
			}

			if (registered(surrounding)) {
				getRegistered(surrounding).onBlockUpdate(surrounding);
			}
		}
	}

	public boolean exists(Location location) {
		return blocks.containsKey(location);
	}

	public String getId(Location location) {
		return blocks.get(location);
	}

	public MekanismBlock getRegistered(String id) {
		return registeredBlocks.get(id);
	}

	public MekanismBlock getRegistered(Location location) {
		return getRegistered(getId(location));
	}

	public Set<String> getRegisteredBlocksIds() {
		return registeredBlocks.keySet();
	}

	public void removeAllBlocks() {
		blocks.forEach((location, id) -> {
			location.getBlock().setType(Material.AIR);
		});

		blocks.clear();

		// Remove entities and holograms
		registeredBlocks.forEach((id, block) -> {
			block.getBlockMemory().getMemorySet().forEach(memory -> {
				Entity entity = Bukkit.getEntity(memory.getUUID("entity"));
				if (entity != null) {
					entity.remove();
				}
				UUID hologram = memory.getUUID("hologram");
				if (hologram != null) {
					entity = Bukkit.getEntity(hologram);
					if (entity != null) {
						entity.remove();
					}
				}
			});
		});

		registeredBlocks.clear();
	}

	public void tick() {
		blocks.forEach((location, id) -> {
			var block = getRegistered(id);
			executorService.submit(() -> block.tick(location));
		});
	}

	public void shutdown() {
		executorService.shutdown();
	}

}
