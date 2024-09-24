package me.adamix.mekanism;

import me.adamix.mekanism.blocks.cables.EnergyCable;
import me.adamix.mekanism.blocks.generators.SolarPanel;
import me.adamix.mekanism.commands.InfoSubCommand;
import me.adamix.mekanism.commands.ItemSubCommand;
import me.adamix.mekanism.commands.MekanismCommand;
import me.adamix.mekanism.listeners.PlayerListener;
import me.adamix.mekanism.blocks.machines.EnergyCube;
import me.adamix.mekanism.managers.BlockManager;
import me.adamix.mekanism.managers.UpdateManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class MekanismPlugin extends JavaPlugin {
	private static MekanismPlugin instance;
	private static BlockManager blockManager;
	private static UpdateManager updateManager;

	@Override
	public void onEnable() {
		instance = this;
		blockManager = new BlockManager();
		updateManager = new UpdateManager();

		var command = new MekanismCommand()
				.addSubCommand("item", new ItemSubCommand())
				.addSubCommand("info", new InfoSubCommand());
		Objects.requireNonNull(getCommand("mekanism")).setExecutor(command);
		Objects.requireNonNull(getCommand("mekanism")).setTabCompleter(command);

		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

		blockManager.register("cable:basic", new EnergyCable(5000, 50));
		blockManager.register("cable:advanced", new EnergyCable(5100, 50));
		blockManager.register("cable:elite", new EnergyCable(5200, 50));
		blockManager.register("cable:ultimate", new EnergyCable(5300, 50));
		blockManager.register("energy_cube", new EnergyCube(100, 2500));
		blockManager.register("solar_panel", new SolarPanel(200, 2500, 2));

		updateManager.startTask();

	}

	@Override
	public void onDisable() {
		blockManager.removeAllBlocks();
		blockManager.shutdown();
		updateManager.stopTask();
	}

	public static MekanismPlugin getInstance() {
		return instance;
	}
	public static BlockManager getBlockManager() {
		return blockManager;
	}
	public static UpdateManager getUpdateManager() {
		return updateManager;
	}
}
