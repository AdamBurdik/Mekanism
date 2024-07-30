package me.adamix.mekanism.commands;

import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.ElectricityBlock;
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

		String action = args[0];
		switch (action.toLowerCase()) {
			case "send_energy":
				// TODO Add send energy debug command

			case "fill":
				var energyStorage = electricityBlock.getComponent(EnergyStorageComponent.class);
				if (energyStorage == null) {
					return false;
				}
				energyStorage.setEnergyToMax();
				player.sendMessage(MiniMessage.miniMessage().deserialize(
						STR."<green>Electricity block has been filled with energy! \{energyStorage.getCurrentEnergyCapacity()}/\{energyStorage.getMaxEnergyCapacity()}"
				));
		}

		return false;
	}
}
