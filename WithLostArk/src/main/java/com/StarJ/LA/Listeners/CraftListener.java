package com.StarJ.LA.Listeners;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.StarJ.LA.Items.FishItem;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Systems.Fishes.Rarity;

public class CraftListener implements Listener {
	@EventHandler
	public void Events(PrepareItemCraftEvent e) {
		if (e.getRecipe() != null) {
			Recipe r = e.getRecipe();
			if (r.getResult() != null) {
				Material type = r.getResult().getType();
				if (type.equals(Material.BREAD)) {
					e.getInventory().setItem(0, Items.bread.getItemStack());
				} else if (type.equals(Material.COOKIE)) {
					e.getInventory().setItem(0, Items.cookie.getItemStack(8));
				} else if (type.equals(Material.PUMPKIN_PIE)) {
					e.getInventory().setItem(0, Items.pumpkin_pie.getItemStack());
				} else if (type.equals(Material.BEETROOT_SOUP)) {
					e.getInventory().setItem(0, Items.beetroot_soup.getItemStack());
				} else if (type.equals(Material.MUSHROOM_STEW)) {
					e.getInventory().setItem(0, Items.mushroom_stew.getItemStack());
				} else if (type.equals(Material.CAKE)) {
					e.getInventory().setItem(0, Items.cake.getItemStack());
				} else if (type.equals(Material.GOLDEN_APPLE)) {
					e.getInventory().setItem(0, Items.golden_apple.getItemStack());
				} else if (type.equals(Material.ENCHANTED_GOLDEN_APPLE)) {
					e.getInventory().setItem(0, Items.enchanted_golden_apple.getItemStack());
				}
			}
		}
	}

	@EventHandler
	public void Events(FurnaceSmeltEvent e) {
		if (e.getResult() != null) {
			Material type = e.getResult().getType();
			if (type.equals(Material.BAKED_POTATO)) {
				e.setResult(Items.baked_potato.getItemStack());
			} else if (type.equals(Material.DRIED_KELP)) {
				e.setResult(Items.dried_kelp.getItemStack());
			} else if (type.equals(Material.COOKED_COD)) {
				ItemStack item = e.getSource();
				Items i = Items.valueOf(item);
				Rarity rare = Rarity.getRandomRarity(new HashMap<Rarity, BigDecimal>(),
						new HashMap<Rarity, BigDecimal>(), new ArrayList<Rarity>(), new HashMap<Rarity, BigDecimal>());
				if (i != null && i instanceof FishItem)
					rare = ((FishItem) i).getRarity();
				if (rare.equals(Rarity.Trash)) {
					e.setResult(Items.trash_cooked_cod.getItemStack());
				} else if (rare.equals(Rarity.Common)) {
					e.setResult(Items.common_cooked_cod.getItemStack());
				} else if (rare.equals(Rarity.Uncommon)) {
					e.setResult(Items.uncommon_cooked_cod.getItemStack());
				} else if (rare.equals(Rarity.Rare)) {
					e.setResult(Items.rare_cooked_cod.getItemStack());
				} else if (rare.equals(Rarity.Epic)) {
					e.setResult(Items.epic_cooked_cod.getItemStack());
				} else if (rare.equals(Rarity.Treasure)) {
					e.setResult(Items.treasure_cooked_cod.getItemStack());
				} else if (rare.equals(Rarity.God)) {
					e.setResult(Items.god_cooked_cod.getItemStack());
				}
			} else if (type.equals(Material.COOKED_SALMON)) {
				ItemStack item = e.getSource();
				Items i = Items.valueOf(item);
				Rarity rare = Rarity.getRandomRarity(new HashMap<Rarity, BigDecimal>(),
						new HashMap<Rarity, BigDecimal>(), new ArrayList<Rarity>(), new HashMap<Rarity, BigDecimal>());
				if (i != null && i instanceof FishItem)
					rare = ((FishItem) i).getRarity();
				if (rare.equals(Rarity.Trash)) {
					e.setResult(Items.trash_cooked_salmon.getItemStack());
				} else if (rare.equals(Rarity.Common)) {
					e.setResult(Items.common_cooked_salmon.getItemStack());
				} else if (rare.equals(Rarity.Uncommon)) {
					e.setResult(Items.uncommon_cooked_salmon.getItemStack());
				} else if (rare.equals(Rarity.Rare)) {
					e.setResult(Items.rare_cooked_salmon.getItemStack());
				} else if (rare.equals(Rarity.Epic)) {
					e.setResult(Items.epic_cooked_salmon.getItemStack());
				} else if (rare.equals(Rarity.Treasure)) {
					e.setResult(Items.treasure_cooked_salmon.getItemStack());
				} else if (rare.equals(Rarity.God)) {
					e.setResult(Items.god_cooked_salmon.getItemStack());
				}
			}
		}
	}

	@EventHandler
	public void Events(FurnaceStartSmeltEvent e) {
		e.setTotalCookTime(1);
	}
}
