package me.adamix.mekanism.commands;

import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.ElectricityBlock;
import me.adamix.mekanism.blocks.components.EnergyInputComponent;
import me.adamix.mekanism.blocks.components.EnergyOutputComponent;
import me.adamix.mekanism.blocks.components.EnergyStorageComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnergyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		Player player = (Player) sender;

		if (args.length < 1) {
			return false;
		}

		Block block = player.getTargetBlockExact(5);
		if (block == null) {
			return false;
		}

		ElectricityBlock electricityBlock = BlockManager.getBlock(block);
		if (electricityBlock == null) {
			return false;
		}

		EnergyStorageComponent energyStorage = null;
		if (electricityBlock.hasComponent(EnergyStorageComponent.class)) {
			energyStorage = electricityBlock.getComponent(EnergyStorageComponent.class);
		}

		String subcommand = args[0];
		switch (subcommand.toLowerCase()) {
			case "send_energy":
				// TODO Add send energy debug command

			case "fill":
				if (energyStorage == null) {
					return false;
				}
				energyStorage.setEnergyToMax();
				player.sendMessage(MiniMessage.miniMessage().deserialize(
						STR."<green>Electricity block has been filled with energy! \{energyStorage.getCurrentEnergyCapacity()}/\{energyStorage.getMaxEnergyCapacity()}"
				));
				return true;
			case "info":
				player.sendMessage(MiniMessage.miniMessage().deserialize(
						STR."<aqua><bold>Component Set: <reset><aqua>\{electricityBlock.getComponentSet()}"
				));
				if (energyStorage != null) {
					player.sendMessage(MiniMessage.miniMessage().deserialize(
							STR."<yellow>Energy: \{energyStorage.getCurrentEnergyCapacity()}/\{energyStorage.getMaxEnergyCapacity()}"
					));
				}
				if (electricityBlock.hasComponent(EnergyInputComponent.class)) {
					player.sendMessage(MiniMessage.miniMessage().deserialize(
							"<yellow>Energy Input Rate: Inf"
					));
				}
				if (electricityBlock.hasComponent(EnergyOutputComponent.class)) {
					var energyOutput = electricityBlock.getComponent(EnergyOutputComponent.class);
					player.sendMessage(MiniMessage.miniMessage().deserialize(
							STR."<yellow>Energy Output Rate: \{energyOutput.getEnergyOutputRate()}J/t"
					));
				}
		}

		return false;
	}
}
