package me.adamix.mekanism.blocks;

import me.adamix.mekanism.blocks.components.ElectricityComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public abstract class ElectricityBlock {
	private final String id;
	private final Set<ElectricityComponent> componentSet = new HashSet<>();
	private ArmorStand armorStand;

	public ElectricityBlock(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public abstract ItemStack getItem();
	public abstract int getBlockCMD();
	public abstract Material getBlockMaterial();
	public void onPlace(Block block, Player player) {}
	public void onBreak(Block block, Player player) {}
	public void onRightClick(Block block, Player player) {}
	public void onLeftClick(Block block, Player player) {}
	public void onBlockUpdate(Block block) {}

	public ArmorStand getArmorStand() {
		return this.armorStand;
	}

	public void setArmorStand(ArmorStand armorStand) {
		this.armorStand = armorStand;
	}

	public <T extends ElectricityComponent> T getComponent(Class<T> clazz) {
		if (clazz == null) {
			throw new RuntimeException("Component class must not be null.");
		}

		for (ElectricityComponent component : componentSet) {
			if (clazz.isAssignableFrom(component.getClass())) {
				return clazz.cast(component);
			}
		}

		return null;
	}

	public boolean hasComponent(Class<? extends ElectricityComponent> clazz) {
		if (clazz == null) {
			throw new RuntimeException("Component class must not be null.");
		}

		return getComponent(clazz) != null;
	}

	public void addComponent(ElectricityComponent... components) {
		componentSet.addAll(List.of(components));
	}

	public ArmorStand spawnArmorStand(Block block) {
		Location armorStandLocation = block.getLocation().toCenterLocation();
		ArmorStand armorStand = block.getWorld().spawn( armorStandLocation.add(0, 320, 0), ArmorStand.class);
		armorStand.setGravity(false);
		armorStand.setInvisible(true);
		armorStand.setInvulnerable(true);
		armorStand.setMarker(true);
		return spawnArmorStand(block, armorStand);
	}

	@SuppressWarnings("UnstableApiUsage")
	public ArmorStand spawnArmorStand(Block block, ArmorStand templateArmorStand) {
		Location armorStandLocation = block.getLocation().toCenterLocation();
		Entity entity = templateArmorStand.copy(armorStandLocation.add(0, -0.5f, 0));

		ArmorStand armorStand = (ArmorStand) entity;
		armorStand.getEquipment().setHelmet(getItem());
		setArmorStand(armorStand);
		return armorStand;
	}

	public void updateSurroundingBlocks(Block block) {
		for (Block surroundingBlock : BlockManager.getSurroundingBlocks(block)) {
			ElectricityBlock electricityBlock = BlockManager.getBlock(surroundingBlock);
			if (electricityBlock == null) {
				continue;
			}
			electricityBlock.onBlockUpdate(surroundingBlock);
		}
	}

}
