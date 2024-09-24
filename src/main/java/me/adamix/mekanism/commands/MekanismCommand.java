package me.adamix.mekanism.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MekanismCommand implements CommandExecutor, TabExecutor {
	private final Map<String, CommandExecutor> subCommandExecutors = new HashMap<>();
	private final Map<String, TabExecutor> subTabExecutors = new HashMap<>();
	private final Logger logger = LoggerFactory.getLogger(MekanismCommand.class);

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		MiniMessage miniMessage = MiniMessage.miniMessage();
		if (args.length < 1) {
			sender.sendMessage(miniMessage.deserialize("<red>Please specify subcommand!"));
			return true;
		}

		String subCommand = args[0];
		if (!subCommandExecutors.containsKey(subCommand)) {
			sender.sendMessage(miniMessage.deserialize("<red>Unknown sub command!"));
			return true;
		}

		return subCommandExecutors.get(subCommand).onCommand(sender, command, alias, args);
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		logger.info("{}", args.length);
		logger.info("{}", Arrays.toString(args));
		if (args.length == 1 && args[0].isEmpty()) {
			return new ArrayList<>(subCommandExecutors.keySet());
		}

		String subCommand = args[0];
		if (!subTabExecutors.containsKey(subCommand)) {
			return new ArrayList<>(subCommandExecutors.keySet()).stream().filter(s -> s.startsWith(subCommand)).toList();
		}

		return subTabExecutors.get(subCommand).onTabComplete(sender, command, alias, args);
	}

	public MekanismCommand addSubCommand(String name, CommandExecutor subCommand) {
		subCommandExecutors.put(name, subCommand);
		if (subCommand instanceof TabExecutor tab) {
			subTabExecutors.put(name, tab);
		}
		return this;
	}
}
