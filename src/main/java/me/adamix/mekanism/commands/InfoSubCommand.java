package me.adamix.mekanism.commands;

import me.adamix.mekanism.MekanismPlugin;
import me.adamix.mekanism.managers.BlockManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class InfoSubCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] strings) {
		if (!(sender instanceof Player player)) {
			return true;
		}

		Block target = player.getTargetBlockExact(100);
		if (target == null) {
			return false;
		}

		Location location = target.getLocation();
		BlockManager blockManager = MekanismPlugin.getBlockManager();
		String id = blockManager.getId(location);
		var block = blockManager.getRegistered(id);
		if (block == null) {
			return false;
		}

		var memory = block.getBlockMemory().getMemory(location);
		String json = memory.toString();

		player.sendMessage(Component.text(json).color(TextColor.color(0, 255, 0)));

		return false;
	}
}
