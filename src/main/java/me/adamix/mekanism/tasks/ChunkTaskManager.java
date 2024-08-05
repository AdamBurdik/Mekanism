package me.adamix.mekanism.tasks;

import me.adamix.mekanism.Mekanism;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChunkTaskManager {

	private static final Set<Chunk> loadedChunks = new CopyOnWriteArraySet<>();

	public static Set<Chunk> getLoadedChunks() {
		return loadedChunks;
	}

	public static void startChunkUpdateTask() {
		Bukkit.getScheduler().runTaskTimer(Mekanism.getInstance(), () -> {
			Set<Chunk> newLoadedChunks = getChunksLoadedByPlayers();
			loadedChunks.clear();
			loadedChunks.addAll(newLoadedChunks);
		}, 0L, 20L * 10); // Run every 10 seconds
	}

	private static Set<Chunk> getChunksLoadedByPlayers() {

		Set<Chunk> chunks = new CopyOnWriteArraySet<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			World world = player.getWorld();
			chunks.addAll(List.of(world.getLoadedChunks()));

//			Location loc = player.getLocation();
//			int radius = player.getSimulationDistance();
//
//			int centerX = loc.getBlockX() >> 4;
//			int centerZ = loc.getBlockZ() >> 4;
//
//			for (int x = -radius; x <= radius; x++) {
//				for (int z = -radius; z <= radius; z++) {
//					chunks.add(world.getChunkAt(centerX + x, centerZ + z));
//				}
//			}
		}
		return chunks;
	}

}
