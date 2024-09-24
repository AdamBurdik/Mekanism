package me.adamix.mekanism;

import me.adamix.mekanism.blocks.cables.EnergyCable;
import me.adamix.mekanism.blocks.machines.EnergyCube;
import me.adamix.mekanism.utils.ConnectionUtils;
import org.bukkit.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionUtilsTest {

	@Test
	public void canCableConnectWithCube() {

		var a = new EnergyCable(10, 10);
		var b = new EnergyCube(10, 10);
		var aLoc = new Location(null, 0, 0, 0);
		var bLoc = new Location(null, 0, 1, 0);

		boolean output = ConnectionUtils.canCableConnect(a, b, aLoc, bLoc);

		assertTrue(output);
	}

	@Test
	public void canCableConnectWithSameCable() {
		var a = new EnergyCable(10, 10);
		var b = new EnergyCable(10, 10);
		var aLoc = new Location(null, 0, 0, 0);
		var bLoc = new Location(null, 0, 1, 0);

		boolean output = ConnectionUtils.canCableConnect(a, b, aLoc, bLoc);

		assertTrue(output);
	}


	@Test
	public void canCableConnectWithDifferentCable() {
		var a = new EnergyCable(10, 10);
		var b = new EnergyCable(11, 10);
		var aLoc = new Location(null, 0, 0, 0);
		var bLoc = new Location(null, 0, 1, 0);

		boolean output = ConnectionUtils.canCableConnect(a, b, aLoc, bLoc);

		assertFalse(output);
	}

}
