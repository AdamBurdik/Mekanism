package me.adamix.mekanism.commands;

import me.adamix.mekanism.blocks.BlockManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveMachinesCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
		if (sender instanceof Player player) {
			player.getInventory().addItem(BlockManager.getBlock("solar_panel").getItem());
			player.getInventory().addItem(BlockManager.getBlock("universal_cable:basic").getItem());
			player.getInventory().addItem(BlockManager.getBlock("energy_cube").getItem());
		}
		return false;
	}
}
