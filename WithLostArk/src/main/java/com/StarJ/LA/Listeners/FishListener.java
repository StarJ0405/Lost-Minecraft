package com.StarJ.LA.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Systems.AdvancementStore;
import com.StarJ.LA.Systems.Fishes;
import com.StarJ.LA.Systems.Fishes.Biomes;

public class FishListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Events(PlayerFishEvent e) {
		if (e.getState().equals(State.CAUGHT_FISH))
			if (e.getCaught() instanceof Item) {
				Player player = e.getPlayer();
				Item item = (Item) e.getCaught();
				ItemStack main = player.getInventory().getItemInMainHand();
				ItemStack off = player.getInventory().getItemInOffHand();
				ItemStack rod = null;
				if (main != null && main.getType().equals(Material.FISHING_ROD)) {
					rod = main;
				} else if (off != null && off.getType().equals(Material.FISHING_ROD))
					rod = off;
				Fishes fish = Fishes.getCaugthFish(item.getLocation(), rod);
				if (fish != null) {
					item.setItemStack(fish.getItemStack());
					AdvancementStore.setCaught(player, AdvancementStore.getCaught(player) + 1);
					AdvancementStore.setCaughtFishes(player, fish, AdvancementStore.getCaughtFishes(player, fish) + 1);
					Biomes biome = Biomes.getBiome(fish.getBiomes(), item.getLocation().getBlock().getBiome());
					AdvancementStore.setCaughtBiomes(player, biome,
							AdvancementStore.getCaughtBiomes(player, biome) + 1);
					AdvancementStore.confirmBiomes(player);
					AdvancementStore.confirmBiomesType(player, biome);
				}
				rod.setDurability((short) (rod.getDurability() + 1));
				if (rod.getDurability() >= rod.getType().getMaxDurability())
					rod.setType(Material.AIR);
			}
	}
}
