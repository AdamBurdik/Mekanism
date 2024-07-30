package me.adamix.mekanism.blocks;

import me.adamix.mekanism.blocks.components.ElectricityComponent;
import me.adamix.mekanism.blocks.components.EnergyInputComponent;
import me.adamix.mekanism.blocks.components.EnergyOutputComponent;
import me.adamix.mekanism.blocks.components.EnergyTransportComponent;
import org.bukkit.Bukkit;
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
	private final Block block;
	private final Set<ElectricityComponent> componentSet = new HashSet<>();
	private ArmorStand armorStand;

	public ElectricityBlock(String id, Block block) {
		this.id = id;
		this.block = block;
	}

	public String getId() {
		return this.id;
	}

	public Block getBlock() {
		return this.block;
	}

	public abstract ItemStack getItem();
	public abstract int getBlockCMD();
	public abstract Material getBlockMaterial();
	public void onPlace(Player player) {}
	public void onBreak(Player player) {}
	public void onRightClick(Player player) {}
	public void onLeftClick(Player player) {}
	public void onBlockUpdate() {}

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

	public Set<ElectricityComponent> getComponentSet() {
		return this.componentSet;
	}

	public ArmorStand spawnArmorStand() {
		Location armorStandLocation = block.getLocation().toCenterLocation();
		ArmorStand armorStand = block.getWorld().spawn( armorStandLocation.add(0, 320, 0), ArmorStand.class);
		armorStand.setGravity(false);
		armorStand.setInvisible(true);
		armorStand.setInvulnerable(true);
		armorStand.setMarker(true);
		return spawnArmorStand(armorStand);
	}

	@SuppressWarnings("UnstableApiUsage")
	public ArmorStand spawnArmorStand(ArmorStand templateArmorStand) {
		Location armorStandLocation = block.getLocation().toCenterLocation();
		Entity entity = templateArmorStand.copy(armorStandLocation.add(0, -0.5f, 0));

		ArmorStand armorStand = (ArmorStand) entity;
		armorStand.getEquipment().setHelmet(getItem());
		setArmorStand(armorStand);
		return armorStand;
	}

	public void updateSurroundingBlocks() {
		for (Block surroundingBlock : BlockManager.getSurroundingBlocks(block)) {
			ElectricityBlock electricityBlock = BlockManager.getBlock(surroundingBlock);
			if (electricityBlock == null) {
				continue;
			}
			electricityBlock.onBlockUpdate();
		}
	}

	public boolean canConnect(Block block) {
		if (hasComponent(EnergyTransportComponent.class)) {
			return true;
		}

		int side = BlockManager.getSide(getBlock(), block);
		if (side < 0) {
			return false;
		}

		if (hasComponent(EnergyInputComponent.class)) {
			var energyInputComponent = getComponent(EnergyInputComponent.class);
			boolean[] energyInputSides = energyInputComponent.getEnergyInputSides();

			if (energyInputSides[side]) {
				return true;
			}
		}

		if (hasComponent(EnergyOutputComponent.class)) {
			var energyOutputComponent = getComponent(EnergyOutputComponent.class);
			boolean[] energyOutputSides = energyOutputComponent.getEnergyOutputSides();

			return energyOutputSides[side];
		}

		return false;
	}

}
