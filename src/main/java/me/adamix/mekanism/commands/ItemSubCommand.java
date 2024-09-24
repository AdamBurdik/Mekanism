package me.adamix.mekanism.commands;

import me.adamix.mekanism.MekanismPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemSubCommand implements CommandExecutor, TabExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if (args.length < 2) {
			return true;
		}

		String item = args[1];

		var block = MekanismPlugin.getBlockManager().getRegistered(item);
		if (block == null) {
			return true;
		}

		ItemStack itemStack = new ItemStack(block.getItemType());
		var meta = itemStack.getItemMeta();
		var data = meta.getPersistentDataContainer();
		data.set(new NamespacedKey(MekanismPlugin.getInstance(), "block_id"), PersistentDataType.STRING, item);
		meta.displayName(Component.text(item).color(TextColor.color(0, 255, 255)));
		meta.setCustomModelData(block.getCustomModelData());
		itemStack.setItemMeta(meta);

		if (sender instanceof Player player) {
			player.getInventory().addItem(itemStack);
		}


		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		return new ArrayList<>(MekanismPlugin.getBlockManager().getRegisteredBlocksIds());
	}
}
