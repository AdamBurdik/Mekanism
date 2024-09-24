package me.adamix.mekanism.managers;

import me.adamix.mekanism.MekanismPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class UpdateManager {
	private BukkitTask task;

	public void startTask() {
		MekanismPlugin plugin = MekanismPlugin.getInstance();
		task =  Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
			MekanismPlugin.getBlockManager().tick();
		}, 0L, 5L);
	}

	public void stopTask() {
		task.cancel();
	}

}
