package me.adamix.mekanism.tasks;

import me.adamix.mekanism.Mekanism;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class BlockTaskManager {

	public void startBlockUpdateTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Mekanism.getInstance(), () -> {
				Set<Chunk> chunksToProcess = new CopyOnWriteArraySet<>(ChunkTaskManager.getLoadedChunks());

	        for (Chunk toProcess : chunksToProcess) {
		        toProcess.
	        }

			}, 0L, 1L); // Runs every tick
	}

}
