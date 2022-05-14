package com.StarJ.LA.Listeners;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.StarJ.LA.Systems.AdvancementStore;
import com.StarJ.LA.Systems.Basics;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Fishes;
import com.StarJ.LA.Systems.Fishes.Biomes;
import com.StarJ.LA.Systems.Fishes.Rarity;

public class FishListener implements Listener {

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
				int level = Basics.Fishing.getLevel(player);
				int count = 1;
				if (Basics.Fishing.isActive(level))
					count += (level / 10 + 1);
				double money_multi = 1;
				int money_chance = ConfigStore.getBasicsNumber(player, Basics.Fishing, 1);
				if (money_chance > 0) {
					money_multi = level * 0.04;
					ConfigStore.setBasicsNumber(player, Basics.Fishing, 1, money_chance - 1);
					player.sendMessage(
							ChatColor.GREEN + "손맛 남은 횟수 : " + ConfigStore.getBasicsNumber(player, Basics.Fishing, 1));
				}			
				HashMap<Rarity, BigDecimal> abil = new HashMap<Rarity, BigDecimal>();
				int multi_chance = ConfigStore.getBasicsNumber(player, Basics.Fishing, 2);
				if (multi_chance > 0) {
					ConfigStore.setBasicsNumber(player, Basics.Fishing, 2, multi_chance - 1);
					abil.put(Rarity.God, BigDecimal.valueOf(level).multiply(BigDecimal.valueOf(0.026)));
					abil.put(Rarity.Treasure, BigDecimal.valueOf(level).multiply(BigDecimal.valueOf(0.024)));
					abil.put(Rarity.Epic, BigDecimal.valueOf(level).multiply(BigDecimal.valueOf(0.022)));
					abil.put(Rarity.Rare, BigDecimal.valueOf(level).multiply(BigDecimal.valueOf(0.02)));
					player.sendMessage(ChatColor.GREEN + "대어의 감 남은 횟수 : "
							+ ConfigStore.getBasicsNumber(player, Basics.Fishing, 2));
				}
				HashMap<Rarity, BigDecimal> ench = new HashMap<Rarity, BigDecimal>();
				if (rod.containsEnchantment(Enchantment.LUCK)) {
					int ench_level = rod.getEnchantmentLevel(Enchantment.LUCK);
					ench.put(Rarity.God, BigDecimal.valueOf(ench_level).multiply(BigDecimal.valueOf(0.13)));
					ench.put(Rarity.Treasure, BigDecimal.valueOf(ench_level).multiply(BigDecimal.valueOf(0.12)));
					ench.put(Rarity.Epic, BigDecimal.valueOf(ench_level).multiply(BigDecimal.valueOf(0.11)));
					ench.put(Rarity.Rare, BigDecimal.valueOf(ench_level).multiply(BigDecimal.valueOf(0.1)));
				}
				List<Rarity> remove = new ArrayList<Rarity>();
				HashMap<Rarity, BigDecimal> remove_multi = new HashMap<Rarity, BigDecimal>();
				int remove_chance = ConfigStore.getBasicsNumber(player, Basics.Fishing, 3);
				if (remove_chance > 0) {
					remove.add(Rarity.Trash);
					remove.add(Rarity.Common);
					remove.add(Rarity.Uncommon);
					remove_multi.put(Rarity.God, BigDecimal.valueOf(3.0d));
					remove_multi.put(Rarity.Treasure, BigDecimal.valueOf(2.5d));
					remove_multi.put(Rarity.Epic, BigDecimal.valueOf(2.0d));
					remove_multi.put(Rarity.Rare, BigDecimal.valueOf(1.5d));
					ConfigStore.setBasicsNumber(player, Basics.Fishing, 3, remove_chance - 1);
					player.sendMessage(ChatColor.GREEN + "낚시의 신 남은 횟수 : "
							+ ConfigStore.getBasicsNumber(player, Basics.Fishing, 3));
				}
				for (int c = 0; c < count; c++) {
					Fishes fish = Fishes.getCaugthFish(item.getLocation(), rod);
					if (fish != null) {
						ItemStack i = fish.getItemStack(money_multi, abil, ench, remove, remove_multi);
						if (c > 0) {
							Location player_loc = player.getLocation();
							Location item_loc = item.getLocation();
							Item spawn = item.getWorld().dropItem(item_loc, i);
							Vector vec = new Vector(player_loc.getX() - item_loc.getX(),
									player_loc.getY() - item_loc.getY(), player_loc.getZ() - item_loc.getZ());
							vec.normalize();
							spawn.setVelocity(vec);
						} else
							item.setItemStack(i);
						AdvancementStore.setCaught(player, AdvancementStore.getCaught(player) + 1);
						AdvancementStore.setCaughtFishes(player, fish,
								AdvancementStore.getCaughtFishes(player, fish) + 1);
						Biomes biome = Biomes.getBiome(fish.getBiomes(), item.getLocation().getBlock().getBiome());
						AdvancementStore.setCaughtBiomes(player, biome,
								AdvancementStore.getCaughtBiomes(player, biome) + 1);
						AdvancementStore.confirmBiomes(player);
						AdvancementStore.confirmBiomesType(player, biome);
						int exp = Basics.getFishingExp(Fishes.getRarity(i));
						if (exp > 0) {
							Basics.Fishing.addEXP(player, exp);
						}
						player.sendMessage(i.getItemMeta().getDisplayName() + ChatColor.WHITE + "을 낚았습니다.");
					}
				}
			}
	}
}
