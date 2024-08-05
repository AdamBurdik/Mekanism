package me.adamix.mekanism.views;

import me.adamix.mekanism.blocks.MekanismBlock;
import me.adamix.mekanism.blocks.components.energy.EnergyStorageComponent;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.ViewType;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.context.SlotClickContext;
import me.devnatan.inventoryframework.state.State;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BasicEnergyCubeView extends View {

	private final State<MekanismBlock> mekanismBlockState = initialState();

	@Override
	public void onInit(ViewConfigBuilder config) {
		config.title("§fӫ七七七㈁");
		config.type(ViewType.CHEST);
		config.size(3);
	}
	@Override
	public void onFirstRender(@NotNull RenderContext render) {
		// TODO Refactor this code

		MekanismBlock mekanismBlock = mekanismBlockState.get(render);
		EnergyStorageComponent energyStorageComponent = mekanismBlock.getComponent(EnergyStorageComponent.class);

		MiniMessage miniMessage = MiniMessage.miniMessage();

		ItemStack energyGraphItem = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
		energyGraphItem.editMeta(meta -> {
			meta.displayName(miniMessage.deserialize("<dark_green>Energy"));
			meta.lore(List.of(
					miniMessage.deserialize("<green>" + energyStorageComponent.getCurrentEnergyCapacity() / 1000 + "kJ/" + energyStorageComponent.getMaxEnergyCapacity() + " kJ")
			));
		});

		ItemStack item1 = energyGraphItem.clone();
		item1.editMeta(meta -> meta.setCustomModelData(100));
		render.slot(2, item1);

		ItemStack item2 = energyGraphItem.clone();
		item2.editMeta(meta -> meta.setCustomModelData(101));
		render.slot(3, item2);

		ItemStack item3 = energyGraphItem.clone();
		item3.editMeta(meta -> meta.setCustomModelData(102));
		render.slot(4, item3);

		ItemStack item4 = energyGraphItem.clone();
		item4.editMeta(meta -> meta.setCustomModelData(102));
		render.slot(5, item4);

		ItemStack item5 = energyGraphItem.clone();
		item5.editMeta(meta -> meta.setCustomModelData(103));
		render.slot(6, item5);

		ItemStack item6 = energyGraphItem.clone();
		item6.editMeta(meta -> meta.setCustomModelData(104));
		render.slot(11, item6);

		ItemStack item7 = energyGraphItem.clone();
		item7.editMeta(meta -> meta.setCustomModelData(105));
		render.slot(12, item7);

		ItemStack item8 = energyGraphItem.clone();
		item8.editMeta(meta -> meta.setCustomModelData(106));
		render.slot(13, item8);

		ItemStack item9 = energyGraphItem.clone();
		item9.editMeta(meta -> meta.setCustomModelData(106));
		render.slot(14, item9);

		ItemStack item10 = energyGraphItem.clone();
		item10.editMeta(meta -> meta.setCustomModelData(107));
		render.slot(15, item10);

		ItemStack item11 = energyGraphItem.clone();
		item11.editMeta(meta -> meta.setCustomModelData(108));
		render.slot(20, item11);

		ItemStack item12 = energyGraphItem.clone();
		item12.editMeta(meta -> meta.setCustomModelData(109));
		render.slot(21, item12);

		ItemStack item13 = energyGraphItem.clone();
		item13.editMeta(meta -> meta.setCustomModelData(110));
		render.slot(22, item13);

		ItemStack item14 = energyGraphItem.clone();
		item14.editMeta(meta -> meta.setCustomModelData(110));
		render.slot(23, item14);

		ItemStack item15 = energyGraphItem.clone();
		item15.editMeta(meta -> meta.setCustomModelData(111));
		render.slot(24, item15);
	}

	@Override
	public void onClick(@NotNull SlotClickContext click) {
		click.setCancelled(true);
		click.closeForPlayer();
		ViewManager.getViewFrame().open(BasicEnergyCubeView.class, click.getPlayer(), mekanismBlockState.get(click));
	}
}
