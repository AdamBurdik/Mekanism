package me.adamix.mekanism;

import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.machines.EnergyCube;
import me.adamix.mekanism.blocks.transport.cables.UniversalCableBasic;
import me.adamix.mekanism.blocks.machines.SolarPanel;
import me.adamix.mekanism.commands.EnergyCommand;
import me.adamix.mekanism.commands.GiveMachinesCommand;
import me.adamix.mekanism.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Mekanism extends JavaPlugin {
	private static Mekanism instance;

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Objects.requireNonNull(getCommand("mekanism")).setExecutor(new GiveMachinesCommand());
		Objects.requireNonNull(getCommand("energy")).setExecutor(new EnergyCommand());

		BlockManager.registerBlock("solar_panel", SolarPanel.class);
		BlockManager.registerBlock("universal_cable:basic", UniversalCableBasic.class);
		BlockManager.registerBlock("energy_cube", EnergyCube.class);

	}

	@Override
	public void onDisable() {
	}

	public static Mekanism getInstance() {
		return instance;
	}
}
