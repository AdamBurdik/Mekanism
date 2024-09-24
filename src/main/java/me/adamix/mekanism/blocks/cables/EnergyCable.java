package me.adamix.mekanism.blocks.cables;

import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.MekanismPlugin;
import me.adamix.mekanism.blocks.memory.BlockMemory;
import me.adamix.mekanism.components.CableComponent;
import me.adamix.mekanism.managers.BlockManager;
import me.adamix.mekanism.utils.BlockUtils;
import me.adamix.mekanism.utils.ConnectionUtils;
import me.adamix.mekanism.utils.EntityUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;

import java.util.UUID;

public class EnergyCable extends MekanismBlock implements CableComponent {
	private final int transportRate;
	private final Material blockType = Material.CONDUIT;
	private final Material itemType = Material.CONDUIT;

	public EnergyCable(int customModelData, int transportRate) {
		super(customModelData);
		this.transportRate = transportRate;
	}

	@Override
	public void onBlockUpdate(Location location) {
		BlockManager blockManager = MekanismPlugin.getBlockManager();
		Location[] surroundingBlocks = BlockUtils.getSurroundingBlocks(location);

		StringBuilder stringModel = new StringBuilder();
		for (Location surroundingBlock : surroundingBlocks) {
			if (!blockManager.exists(surroundingBlock)) {
				stringModel.append("0");
				continue;
			}

			MekanismBlock mekanismBlock = blockManager.getRegistered(surroundingBlock);

			boolean canConnect = ConnectionUtils.canCableConnect(this, mekanismBlock, location, surroundingBlock);
			if (!canConnect) {
				stringModel.append("0");
				continue;
			}

			stringModel.append("1");
		}

		int newCustomModelData = Integer.parseInt(stringModel.toString(), 2);

		UUID entityUUID = blockMemory.getMemory(location).getUUID("entity");
		EntityUtils.updateEntity(entityUUID, customModelData + newCustomModelData);
	}

	@Override
	public void onPlace(Location location) {
		Block block = location.getBlock();
		block.setType(Material.CONDUIT);
		var waterlogged = (Waterlogged) block.getBlockData();
		waterlogged.setWaterlogged(false);
		block.setBlockData(waterlogged);

		var memory = blockMemory.createMemory(location);
		memory.set("transport_rate", transportRate);
		var entity = EntityUtils.spawnEntity(location, customModelData, itemType);
		memory.set("entity", entity.getUniqueId());
	}

	@Override
	public Material getBlockType() {
		return blockType;
	}

	@Override
	public Material getItemType() {
		return itemType;
	}

	@Override
	public long getTransportRate(Location location) {
		return transportRate;
	}
}