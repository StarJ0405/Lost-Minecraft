package com.StarJ.LA.Listeners;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.StarJ.LA.Items.CookingIngredient;
import com.StarJ.LA.Items.FishItem;
import com.StarJ.LA.Items.Items;

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
			ItemStack source = e.getSource();
			Items i = Items.valueOf(source);
			if (i != null && i instanceof CookingIngredient && ((CookingIngredient) i).getCookedFood() != null) {
				e.setResult(((CookingIngredient) i).getCookedFood().getItemStack());
			} else if (type.equals(Material.BAKED_POTATO)) {
				e.setResult(Items.baked_potato.getItemStack());
			} else if (type.equals(Material.DRIED_KELP)) {
				e.setResult(Items.dried_kelp.getItemStack());
			} else if (type.equals(Material.COOKED_COD) || type.equals(Material.COOKED_SALMON)) {
				e.setResult(FishItem.getCook(source));
			} else if (type.equals(Material.COOKED_PORKCHOP) || type.equals(Material.COOKED_BEEF)
					|| type.equals(Material.COOKED_CHICKEN) || type.equals(Material.COOKED_RABBIT)
					|| type.equals(Material.COOKED_MUTTON)) {
				List<CookingIngredient> list = CookingIngredient.getList();
				Collections.shuffle(list);
				for (CookingIngredient cook : list) {
					ItemStack item = cook.getItemStack();
					if (item.getType().equals(type)) {
						e.setResult(item);
						break;
					}
				}
			}
		}
	}

	@EventHandler
	public void Events(FurnaceStartSmeltEvent e) {
		e.setTotalCookTime(1);
	}
}
