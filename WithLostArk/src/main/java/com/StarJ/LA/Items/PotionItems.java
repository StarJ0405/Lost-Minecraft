package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Items.Potioning.PotioningIngredient;
import com.StarJ.LA.Systems.Comparables.ItemComparator;

public class PotionItems extends Items {
	private static List<PotionItems> list = new ArrayList<PotionItems>();
	//
	private final PotioningIngredient[] ings;

	public PotionItems(String loc, String key, Material type, ChatColor color, PotioningIngredient[] ings) {
		super("POTION", key, type, color);
		this.ings = ings;
		list.add(this);
	}

	public PotioningIngredient[] getIngs() {
		return ings;
	}

	public boolean isReady(PotioningIngredient[] readies) {
		loop: for (PotioningIngredient ing : this.ings) {
			for (PotioningIngredient ready : readies) {
				int comp = new ItemComparator().compare(ing.getItemStack(), ready.getItemStack());
				if (comp == 0 || comp == 8)
					continue loop;
			}
			return false;
		}
		return false;
	}

	public static List<PotionItems> getPotionItems() {
		return list;
	}

	public boolean click(Player player, ItemStack item) {
		return true;
	}
}
