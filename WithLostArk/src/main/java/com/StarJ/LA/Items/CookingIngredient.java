package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CookingIngredient extends Items implements RightClickable {
	private static List<CookingIngredient> list = new ArrayList<CookingIngredient>();

	private final double health;
	private final int amount;
	private final short random;
	private final IngredientType ingredient_type;
	private final CookingIngredient cooked_food;

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
		this(key, type, color, health, amount, random, ingredient_type, null);
	}

	public CookingIngredient(String key, Material type, ChatColor color, double health, int amount, short random,
			IngredientType ingredient_type, CookingIngredient cooked_food) {
		super("COOKING_INGREDIENT", key, type, color);
		this.health = ((int) (health * 100.0d)) / 100.0d;
		this.amount = amount;
		this.random = random;
		lore.add(ChatColor.GREEN + "영양 : " + this.health);
		this.ingredient_type = ingredient_type;
		list.add(this);
		this.cooked_food = cooked_food;
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

	public CookingIngredient getCookedFood() {
		return cooked_food;
	}

	public enum IngredientType {
		Farming, Fishing, Hunting
	}

	public double getHelath() {
		return health;
	}

	public static List<CookingIngredient> getList() {
		List<CookingIngredient> list = new ArrayList<CookingIngredient>();
		list.addAll(CookingIngredient.list);
		return list;
	}

	@Override
	public void RightClick(Player player, ItemStack item, Block b, Entity et) {
	}

	@Override
	public boolean isRightCancel() {
		return false;
	}
}
