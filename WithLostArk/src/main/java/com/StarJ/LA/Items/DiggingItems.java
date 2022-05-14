package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DiggingItems extends Items {
	private final static List<DiggingItems> list = new ArrayList<DiggingItems>();
	//
	private final PotionItems potion;
	private final ItemStack[] ings;

	public DiggingItems(String key, Material type, ChatColor color, PotionItems potion, ItemStack[] ings) {
		super("BASICS_DIGGING", key, type, color);
		this.potion = potion;
		this.ings = ings;
		list.add(this);
	}

	public PotionItems getPotion() {
		return potion;
	}

	public ItemStack[] getIngs() {
		return ings;
	}

	public static List<DiggingItems> getDiggingItems() {
		return list;
	}
}
