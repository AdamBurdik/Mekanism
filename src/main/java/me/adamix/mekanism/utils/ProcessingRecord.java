package me.adamix.mekanism.utils;

import me.adamix.mekanism.blocks.MekanismBlock;
import org.bukkit.Location;

public record ProcessingRecord(MekanismBlock mekanismBlock, Location location) {
}
