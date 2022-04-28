package com.StarJ.LA.Systems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import com.StarJ.LA.Core;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Systems.Recipes.Recipeinfo.Ingredient;

public class Recipes {
	private static List<Recipes> recipes = new ArrayList<Recipes>();
	//
	public static Recipes saddle = new Recipes(new NormalRecipeInfo(new String[] { "AAA", "ABA", "B B" },
			new Ingredient[] { new Ingredient(Material.LEATHER, 'A'), new Ingredient(Material.IRON_INGOT, 'B') }),
			"saddle", Material.SADDLE);
	//

	private final Recipeinfo info;
	private final String key;
	private final ItemStack item;

	public Recipes(Recipeinfo info, String key, ItemStack item) {
		this.info = info;
		this.key = key;
		this.item = item;
		recipes.add(this);
	}

	public Recipes(Recipeinfo info, String key, Material type) {
		this.info = info;
		this.key = key;
		this.item = new ItemStack(type);
		recipes.add(this);
	}

	public String getKey() {
		return key;
	}

	public void RegistRecipe() {
		if (info != null) {
			NamespacedKey key = new NamespacedKey(Core.getCore(), getKey());
			if (info instanceof NormalRecipeInfo) {
				NormalRecipeInfo reinfo = (NormalRecipeInfo) info;
				String[] coms = reinfo.getCombination();
				Ingredient[] ings = reinfo.getIngredients();
				if (coms != null && ings != null && coms.length > 0 && ings.length > 0) {
					ShapedRecipe recipe = new ShapedRecipe(key, item);
					recipe.shape(coms);
					for (Ingredient ing : ings) {
						recipe.setIngredient(ing.getChar(), new Choice(getKey(), ing.getitemStack()));
					}
					if (Bukkit.getRecipe(key) == null)
						Bukkit.addRecipe(recipe);
				}

			} else if (info instanceof FurnaceRecipeInfo) {
				FurnaceRecipeInfo reinfo = (FurnaceRecipeInfo) info;
				Items i = Items.valueOf(reinfo.getIngredient().getitemStack());
				if (i != null)
					if (Bukkit.getRecipe(key) == null) {
						Bukkit.addRecipe(new FurnaceRecipe(key, item, new Choice(i.getKey(), i.getItemStack()),
								reinfo.getExp(), reinfo.getCookingtime()));
					}
			}
		}
	}

	public static class Recipeinfo {
		public static class Ingredient {
			private ItemStack item;
			private char c;
			private int amount;

			public Ingredient(Material type) {
				this(type, ' ', 1);
			}

			public Ingredient(Material type, int amount) {
				this(type, ' ', amount);
			}

			public Ingredient(Material type, char c) {
				this(type, c, 1);
			}

			public Ingredient(Material type, char c, int amount) {
				this.item = new ItemStack(type);
				this.c = c;
				this.amount = amount;
			}

			public Ingredient(ItemStack item) {
				this(item, ' ', 1);
			}

			public Ingredient(ItemStack item, int amount) {
				this(item, ' ', amount);
			}

			public Ingredient(ItemStack item, char c) {
				this(item, c, 1);
			}

			public Ingredient(ItemStack item, char c, int amount) {
				this.item = item;
				this.c = c;
				this.amount = amount;
			}

			public ItemStack getitemStack() {
				return item;
			}

			public char getChar() {
				return c;
			}

			public int getAmount() {
				return amount;
			}
		}
	}

	public static class NormalRecipeInfo extends Recipeinfo {
		private String[] combination;
		private Ingredient[] ingredients;

		public NormalRecipeInfo(String[] com, Ingredient[] ings) {
			this.combination = com;
			this.ingredients = ings;
		}

		public String[] getCombination() {
			return combination;
		}

		public Ingredient[] getIngredients() {
			return ingredients;
		}

		public List<ItemStack> getKeys() {
			List<ItemStack> keys = new ArrayList<ItemStack>();
			for (Ingredient ing : ingredients)
				keys.add(ing.getitemStack());
			return keys;
		}
	}

	public static class FurnaceRecipeInfo extends Recipeinfo {
		private Ingredient ingredient;
		private float exp;
		private int cookingtime;

		public FurnaceRecipeInfo(Ingredient ing, float exp, int time) {
			this.ingredient = ing;
			this.exp = exp;
			this.cookingtime = time;
		}

		public Ingredient getIngredient() {
			return ingredient;
		}

		public float getExp() {
			return exp;
		}

		public int getCookingtime() {
			return cookingtime;
		}
	}

	protected class Choice extends RecipeChoice.ExactChoice {
		private String key;

		public Choice(String key, ItemStack item) {
			super(item);
			this.key = key;
		}

		public Choice(String key, ItemStack... items) {
			super(items);
			this.key = key;
		}

		public Choice(String key, List<ItemStack> list) {
			super(list);
			this.key = key;
		}

		@Override
		public boolean test(ItemStack t) {
			Items i = Items.valueOf(t);
			Bukkit.broadcastMessage(t.getType().name());
			return i != null && i.getKey().equals(this.key);
		}
	}

	public static List<Recipes> values() {
		return recipes;
	}

}
