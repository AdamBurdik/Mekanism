package me.adamix.mekanism;

import me.adamix.mekanism.utils.BlockUtils;
import org.bukkit.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockUtilsTest {

	@Test
	public void getSideTest() {
		var aLoc = new Location(null, 0, 0, 0);
		var bLoc = new Location(null, 0, 1, 0);
		int side = BlockUtils.getSide(aLoc, bLoc);
		assertEquals(side, 0);

		aLoc = new Location(null, 0, 0, 0);
		bLoc = new Location(null, 0, -1, 0);
		side = BlockUtils.getSide(aLoc, bLoc);
		assertEquals(side, 1);

		aLoc = new Location(null, 0, 0, 0);
		bLoc = new Location(null, 0, 0, 1);
		side = BlockUtils.getSide(aLoc, bLoc);
		assertEquals(side, 2);

		aLoc = new Location(null, 0, 0, 0);
		bLoc = new Location(null, 0, 0, -1);
		side = BlockUtils.getSide(aLoc, bLoc);
		assertEquals(side, 3);

		aLoc = new Location(null, 0, 0, 0);
		bLoc = new Location(null, 1, 0, 0);
		side = BlockUtils.getSide(aLoc, bLoc);
		assertEquals(side, 4);

		aLoc = new Location(null, 0, 0, 0);
		bLoc = new Location(null, -1, 0, 0);
		side = BlockUtils.getSide(aLoc, bLoc);
		assertEquals(side, 5);

		aLoc = new Location(null, 0, 0, 0);
		bLoc = new Location(null, 0, 0, 0);
		side = BlockUtils.getSide(aLoc, bLoc);
		assertEquals(side, -1);
	}

}
