package com.StarJ.LA.Items.Cooking;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Items.Items;

public class CookingIngredient extends Items {
	private static List<CookingIngredient> list = new ArrayList<CookingIngredient>();

	private final double helath;
	private final int amount;
	private final short random;
	private final IngredientType ingredient_type;

	public CookingIngredient(String key, Material type, ChatColor color, double health,
			IngredientType ingredient_type) {
		this(key, type, color, health, 1 + 1, (short) 0, ingredient_type);
	}

	public CookingIngredient(String key, Material type, ChatColor color, double health, int amount,
			IngredientType ingredient_type) {
		this(key, type, color, health, amount, (short) 0, ingredient_type);
	}

	public CookingIngredient(String key, Material type, ChatColor color, double health, int amount, short random,
			IngredientType ingredient_type) {
		super("COOKING_INGREDIENT", key, type, color);
		this.helath = health;
		this.amount = amount;
		this.random = random;
		lore.add(ChatColor.GREEN + "영양 : " + health);
		this.ingredient_type = ingredient_type;
		list.add(this);
	}

	@SuppressWarnings("deprecation")
	public ItemStack getRandomItemStack() {
		ItemStack item = getItemStack();
		item.setAmount(amount);
		item.setDurability(random);
		return item;
	}

	public IngredientType getIngredienttype() {
		return ingredient_type;
	}

	public enum IngredientType {
		Farming, Fishing, Hunting
	}

	public double getHelath() {
		return helath;
	}

	public static List<CookingIngredient> getList() {
		return list;
	}
}
