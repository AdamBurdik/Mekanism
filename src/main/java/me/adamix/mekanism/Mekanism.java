package me.adamix.mekanism;

import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.machines.EnergyCube;
import me.adamix.mekanism.blocks.transport.cables.UniversalCableAdvanced;
import me.adamix.mekanism.blocks.transport.cables.UniversalCableBasic;
import me.adamix.mekanism.blocks.machines.SolarPanel;
import me.adamix.mekanism.blocks.transport.cables.UniversalCableElite;
import me.adamix.mekanism.blocks.transport.cables.UniversalCableUltimate;
import me.adamix.mekanism.commands.EnergyCommand;
import me.adamix.mekanism.commands.GiveMachinesCommand;
import me.adamix.mekanism.listener.PlayerListener;
import me.adamix.mekanism.tasks.BlockUpdateTaskManager;
import me.adamix.mekanism.views.ViewManager;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Mekanism extends JavaPlugin {
	private static Mekanism instance;

	@Override
	public void onEnable() {
		instance = this;
		ViewManager.init(this);
//		ChunkTaskManager.startChunkUpdateTask();
		BlockUpdateTaskManager.startUpdateTask(this);

		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Objects.requireNonNull(getCommand("mekanism")).setExecutor(new GiveMachinesCommand());
		Objects.requireNonNull(getCommand("energy")).setExecutor(new EnergyCommand());

		BlockManager.registerBlock("solar_panel", SolarPanel.class);
		BlockManager.registerBlock("universal_cable:basic", UniversalCableBasic.class);
		BlockManager.registerBlock("universal_cable:advanced", UniversalCableAdvanced.class);
		BlockManager.registerBlock("universal_cable:elite", UniversalCableElite.class);
		BlockManager.registerBlock("universal_cable:ultimate", UniversalCableUltimate.class);
		BlockManager.registerBlock("energy_cube", EnergyCube.class);

	}

	@Override
	public void onDisable() {
	}

	public static Mekanism getInstance() {
		return instance;
	}
}
