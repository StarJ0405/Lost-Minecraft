package com.StarJ.LA.Items.Cooking;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.StarJ.LA.Items.Items;

public class CookingIngredient extends Items {
	private static List<CookingIngredient> list = new ArrayList<CookingIngredient>();

	private final double helath;

	public CookingIngredient(String key, Material type, ChatColor color, double health) {
		this(key, type, color, health, 1, (short) 0);
	}

	@SuppressWarnings("deprecation")
	public CookingIngredient(String key, Material type, ChatColor color, double health, int amount, short data) {
		super("COOKING_INGREDIENT", key, type, color);
		this.helath = health;
		item.setAmount(amount);
		item.setDurability(data);
		lore.add(ChatColor.GREEN + "영양 : " + health);
		list.add(this);
	}

	public double getHelath() {
		return helath;
	}

	public static List<CookingIngredient> getList() {
		return list;
	}
}
