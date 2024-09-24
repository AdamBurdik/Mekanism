package me.adamix.mekanism.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.UUID;

public class EntityUtils {
	private static final Location defaultEntityLocation = new Location(Bukkit.getWorlds().get(0), 0, -150, 0);

	public static ArmorStand spawnEntity(Location location, int customModelData, Material blockType) {
		ArmorStand entity = (ArmorStand) location.getWorld().spawnEntity(defaultEntityLocation, EntityType.ARMOR_STAND);
		entity.setInvisible(true);
		entity.setMarker(true);
		entity.setInvulnerable(true);
		entity.setGravity(false);
		entity.setBasePlate(false);
		entity.setCanMove(false);
		entity.setCanTick(false);
		entity.setCollidable(false);

		ItemStack item = new ItemStack(blockType);
		item.editMeta(meta -> {
			meta.setCustomModelData(customModelData);
		});
		entity.getEquipment().setHelmet(item);

		entity.teleport(location.toCenterLocation().subtract(0, 0.5, 0));
		return entity;
	}

	public static void removeEntity(UUID uuid) {
		if (uuid == null) {
			return;
		}
		Entity entity = Bukkit.getEntity(uuid);
		if (entity != null) {
			entity.remove();
		}
	}

	public static void updateEntity(UUID entityUUID, int customModelData) {
		ArmorStand armorStand = (ArmorStand) Bukkit.getEntity(entityUUID);
		if (armorStand == null) {
			throw new RuntimeException("Unable to update entity! Entity is null");
		}

		ItemStack item = armorStand.getEquipment().getHelmet();
		item.editMeta(meta -> {
			meta.setCustomModelData(customModelData);
		});
		armorStand.getEquipment().setHelmet(item);
	}

	public static UUID showHologram(UUID entityUUID, Component component, Location location, Vector offset) {
		TextDisplay entity = null;
		if (entityUUID != null) {
			entity = (TextDisplay) Bukkit.getEntity(entityUUID);
		}
		if (entity == null) {
			entity = (TextDisplay) location.getWorld().spawnEntity(location.toCenterLocation().add(0, 0.5, 0).add(offset), EntityType.TEXT_DISPLAY);
		}

		entity.setDefaultBackground(false);
		entity.text(component);
		entity.setBillboard(Display.Billboard.CENTER);
		entity.setBackgroundColor(Color.fromARGB(50, 0, 0, 0));

		return entity.getUniqueId();
	}

	public static void setName(UUID entityUUID, Component component) {
		Entity entity = Bukkit.getEntity(entityUUID);
		if (entity != null) {
			entity.customName(component);
			entity.setCustomNameVisible(true);
		}
	}
}
