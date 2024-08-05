package me.adamix.mekanism.tasks;

import me.adamix.mekanism.Mekanism;
import me.adamix.mekanism.blocks.BlockManager;
import org.bukkit.Bukkit;

public class BlockUpdateTaskManager {

	public static void startUpdateTask(Mekanism mekanism) {
		Bukkit.getScheduler().runTaskTimerAsynchronously(mekanism, task -> {
			BlockManager.getBlocks().forEach( (location, mekanismBlock) -> {
				mekanismBlock.onUpdate();
			});

		}, 0L, 1L);
	}

}
