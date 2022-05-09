package com.StarJ.LA.Items.Potioning;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.StarJ.LA.Items.Items;

public class PotioningIngredient extends Items {
	private static List<PotioningIngredient> list = new ArrayList<PotioningIngredient>();

	public PotioningIngredient(String key, Material type, ChatColor color) {
		super("POTIONING_INGREDIENT", key, type, color);
		list.add(this);
	}

	public static List<PotioningIngredient> getList() {
		return list;
	}
}
