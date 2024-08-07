package me.adamix.mekanism.commands;

import me.adamix.mekanism.blocks.BlockManager;
import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.blocks.components.energy.EnergyInputComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyOutputComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyStorageComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyTransportComponent;
import me.adamix.mekanism.transports.EnergyTransport;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnergyCommand implements CommandExecutor {

	private List<MekanismBlock> alreadyProcessedBlocks;

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

		MekanismBlock mekanismBlock = BlockManager.getBlock(block);
		if (mekanismBlock == null) {
			return false;
		}

		EnergyStorageComponent energyStorage = null;
		if (mekanismBlock.hasComponent(EnergyStorageComponent.class)) {
			energyStorage = mekanismBlock.getComponent(EnergyStorageComponent.class);
		}

		String subcommand = args[0];
		switch (subcommand.toLowerCase()) {
			case "send_energy":
				if (energyStorage != null) {
					EnergyTransport.sendEnergy(mekanismBlock, energyStorage.getCurrentEnergyCapacity());
				}
				return false;
			case "fill":
				if (energyStorage == null) {
					return false;
				}
				energyStorage.setEnergyToMax();
				player.sendMessage(MiniMessage.miniMessage().deserialize(
						"<green>Electricity block has been filled with energy! " + energyStorage.getCurrentEnergyCapacity() + "/" + energyStorage.getMaxEnergyCapacity()
				));
				Bukkit.getLogger().info(String.valueOf(energyStorage.getCurrentEnergyCapacity()));
				return true;
			case "info":
				player.sendMessage(MiniMessage.miniMessage().deserialize(
						"<aqua><bold>Component Set: <reset><aqua>" + mekanismBlock.getComponentSet()
				));
				if (energyStorage != null) {
					player.sendMessage(MiniMessage.miniMessage().deserialize(
							"<yellow>Energy: " + energyStorage.getCurrentEnergyCapacity() + "/" + energyStorage.getMaxEnergyCapacity()
					));
				}
				if (mekanismBlock.hasComponent(EnergyInputComponent.class)) {
					player.sendMessage(MiniMessage.miniMessage().deserialize(
							"<yellow>Energy Input Rate: Inf"
					));
				}
				if (mekanismBlock.hasComponent(EnergyOutputComponent.class)) {
					EnergyOutputComponent energyOutput = mekanismBlock.getComponent(EnergyOutputComponent.class);
					player.sendMessage(MiniMessage.miniMessage().deserialize(
							"<yellow>Energy Output Rate: " + energyOutput.getEnergyOutputRate() + "J/t"
					));
				}
		}

		return false;
	}
}
