package me.adamix.mekanism.blocks;

import me.adamix.mekanism.blocks.capabilities.InputCapability;
import me.adamix.mekanism.blocks.capabilities.OutputCapability;
import me.adamix.mekanism.blocks.capabilities.TransportCapability;
import me.adamix.mekanism.blocks.components.MekanismComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyInputComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyOutputComponent;
import me.adamix.mekanism.blocks.components.energy.EnergyTransportComponent;
import me.adamix.mekanism.transports.TransportType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
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

	public void onUpdate() {

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

	public boolean canConnect(MekanismBlock otherBlock, TransportType transportType) {
		if (otherBlock == null) {
			return false;
		}

		int side = BlockManager.getSide(this.block, otherBlock.getBlock());
		if (side < 0) {
			return false;
		}

		for (MekanismComponent component : componentSet) {
			if (component instanceof InputCapability inputCap) {
				if (inputCap.getInputSides()[side] && canConnectWithOutput(otherBlock, inputCap)) {
					return true;
				}
			}
			if (component instanceof OutputCapability outputCap) {
				if (outputCap.getOutputSides()[side] && canConnectWithInput(otherBlock, outputCap)) {
					return true;
				}
			}
			if (component instanceof TransportCapability transportCap) {
				if (canConnectWithTransport(otherBlock, transportCap, transportType)) {
					return true;
				}
			}
		}

		return false;

//		if (mekanismBlock.hasComponent(EnergyTransportComponent.class) && this.hasComponent(EnergyTransportComponent.class)) {
//			return mekanismBlock.getId().equals(this.id);
//		}
//
//		int side = BlockManager.getSide(this.block, mekanismBlock.getBlock());
//		if (side < 0) {
//			return false;
//		}
//
//		var energyInputComponent = this.getComponent(EnergyInputComponent.class);
//
//		if (energyInputComponent != null) {
//			boolean[] energyInputSides = energyInputComponent.getEnergyInputSides();
//			if (energyInputSides[side]) {
//				return true;
//			}
//		}
//
//		var energyOutputComponent = this.getComponent(EnergyOutputComponent.class);
//
//		if (energyOutputComponent != null) {
//			boolean[] energyOutputSides = energyOutputComponent.getEnergyOutputSides();
//			return energyOutputSides[side];
//		}
	}

	private boolean canConnectWithOutput(MekanismBlock otherBlock, InputCapability inputCap) {
		for (MekanismComponent component : otherBlock.getComponentSet()) {
			if (component instanceof TransportCapability transportCap) {
				return true;
			}

			if (component instanceof OutputCapability outputCap) {
				int side = BlockManager.getSide(this.block, otherBlock.getBlock());
				if (side < 0) {
					return false;
				}

				if (inputCap.getType() == outputCap.getType() && outputCap.getOutputSides()[side]) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean canConnectWithInput(MekanismBlock otherBlock, OutputCapability outputCap) {
		for (MekanismComponent component : otherBlock.getComponentSet()) {
			if (component instanceof TransportCapability transportCap) {
				return true;
			}

			if (component instanceof InputCapability inputCap) {
				int side = BlockManager.getSide(this.block, otherBlock.getBlock());
				if (side < 0) {
					return false;
				}

				if (outputCap.getType() == inputCap.getType() && inputCap.getInputSides()[side]) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean canConnectWithTransport(MekanismBlock otherBlock, TransportCapability transportCap, TransportType transportType) {
		for (MekanismComponent component : otherBlock.getComponentSet()) {
			if (component instanceof TransportCapability otherTransportCap) {
				return transportCap.getType() == otherTransportCap.getType() && otherBlock.getId().equals(this.id);
			}
			if (component instanceof InputCapability inputCap && (transportType == TransportType.INPUT || transportType == TransportType.ANY)) {
				int side = BlockManager.getSide(otherBlock.getBlock(), this.block);
				if (side < 0) {
					return false;
				}

				if (inputCap.getInputSides()[side]) {
					return inputCap.getInputSides()[side];
				}
			}
			if (component instanceof OutputCapability outputCap && (transportType == TransportType.OUTPUT || transportType == TransportType.ANY)) {
				int side = BlockManager.getSide(otherBlock.getBlock(), this.block);
				if (side < 0) {
					return false;
				}

				if (outputCap.getOutputSides()[side]) {
					return outputCap.getOutputSides()[side];
				}
			}
		}
		return false;
	}
}
