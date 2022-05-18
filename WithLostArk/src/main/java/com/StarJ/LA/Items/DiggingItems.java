package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
		lore.add(ChatColor.GREEN + "아이템을 이름과 개수에 맞춰 순서대로 넣어주세요.");
		for (ItemStack ing : ings)
			lore.add(ChatColor.WHITE + ing.getType().name() + " X " + ing.getAmount());
		lore.add(ChatColor.DARK_GRAY + "" + ChatColor.MAGIC + new Random().nextInt());
		list.add(this);
	}

	public PotionItems getPotion() {
		return potion;
	}

	@Deprecated
	public ItemStack[] getIngs() {
		return ings;
	}

	public int getSlotCount() {
		return ings.length;
	}

	@Override
	public ItemStack getItemStack(int count) {
		return super.getItemStack(1);
	}

	public int getResultCount() {
		return 1;
	}

	public boolean canMake(ItemStack[] items) {
		if (items.length == 0 || ings.length == 0 || ings.length != items.length)
			return false;
		boolean[] pass = new boolean[ings.length];
		Arrays.fill(pass, false);
		for (int c = 0; c < ings.length; c++) {
			ItemStack item = items[c];
			ItemStack ing = ings[c];
			if (item == null || ing == null)
				return false;
			Items i = Items.valueOf(item);
			if (i != null)
				return false;
			if (item.getType().equals(ing.getType()) && item.getAmount() == ing.getAmount()) {
				pass[c] = true;
			} else
				return false;
		}
		boolean test = pass.length > 0;
		for (boolean p : pass)
			test = test && p;
		return test;
	}

	public static List<DiggingItems> getDiggingItems() {
		return list;
	}
}
