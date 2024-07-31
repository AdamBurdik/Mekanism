package me.adamix.mekanism.blocks;

import me.adamix.mekanism.blocks.components.MekanismComponent;
import me.adamix.mekanism.blocks.components.MekanismTransportComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyInputComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyOutputComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyTransportComponent;
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


public abstract class MekanismBlock {
	private final String id;
	private final Block block;
	private final Set<MekanismComponent> componentSet = new HashSet<>();
	private ArmorStand armorStand;

	public MekanismBlock(String id, Block block) {
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

	public void onPlace(Player player) {
	}

	public void onBreak(Player player) {
	}

	public void onRightClick(Player player) {
	}

	public void onLeftClick(Player player) {
	}

	public void onBlockUpdate() {
	}

	public ArmorStand getArmorStand() {
		return this.armorStand;
	}

	public void setArmorStand(ArmorStand armorStand) {
		this.armorStand = armorStand;
	}

	public <T extends MekanismComponent> T getComponent(Class<T> clazz) {
		if (clazz == null) {
			throw new RuntimeException("Component class must not be null.");
		}

		for (MekanismComponent component : componentSet) {
			if (clazz.isAssignableFrom(component.getClass())) {
				return clazz.cast(component);
			}
		}

		return null;
	}

	public boolean hasComponent(Class<? extends MekanismComponent> clazz) {
		if (clazz == null) {
			throw new RuntimeException("Component class must not be null.");
		}

		return getComponent(clazz) != null;
	}

	public void addComponent(MekanismComponent... components) {
		componentSet.addAll(List.of(components));
	}

	public Set<MekanismComponent> getComponentSet() {
		return this.componentSet;
	}

	public ArmorStand spawnArmorStand() {
		Location armorStandLocation = block.getLocation().toCenterLocation();
		ArmorStand armorStand = block.getWorld().spawn(armorStandLocation.add(0, 320, 0), ArmorStand.class);
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
			MekanismBlock mekanismBlock = BlockManager.getBlock(surroundingBlock);
			if (mekanismBlock == null) {
				continue;
			}
			mekanismBlock.onBlockUpdate();
		}
	}

	// Can connect =
	//  1. Společná strana má input / output
	//  2. Stejný typ kabelu ( transporteru )


	// Input
	// Output
	// Transport

	// Energy, Item, Gas, Fluid


	public boolean canConnect(MekanismBlock mekanismBlock) {
		// ToDo
		return false;
	}
}
