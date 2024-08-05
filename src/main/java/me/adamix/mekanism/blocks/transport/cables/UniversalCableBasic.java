package me.adamix.mekanism.blocks.transport.cables;

import org.bukkit.block.Block;

public class UniversalCableBasic extends UniversalCable {
	public UniversalCableBasic(String id, Block block) {
		super(id, block, "<yellow>Basic Universal Cable", 5000, 8_000 / 20);
	}
}
