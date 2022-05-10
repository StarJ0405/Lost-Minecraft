package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.StarJ.LA.Items.Cooking.CookingIngredient;
import com.StarJ.LA.Systems.Fishes.Length;
import com.StarJ.LA.Systems.Fishes.Rarity;
import com.StarJ.LA.Systems.Fishes.Size;

public class FishItem extends CookingIngredient {
	private final String name;
	private final Size size;
	private final double man_chance;
	private final Length length;
	private final long price;
	private final Rarity rarity;

	public FishItem(String key, ChatColor color, String name, Size size, double man_chance, Length length, long price,
			Rarity rarity) {
		this(key, color, name, size, man_chance, length, price, rarity, 32);
	}

	public FishItem(String key, ChatColor color, String name, Size size, double man_chance, Length length, long price,
			Rarity rarity, double multi) {
		super(key, Material.SALMON, color, rarity.getMulti() * multi, IngredientType.Fishing);
		this.name = name;
		this.size = size;
		this.man_chance = man_chance;
		this.length = length;
		this.price = price;
		this.rarity = rarity;
	}

	public Rarity getRarity() {
		return rarity;
	}

	protected Material getType() {
		Material[] types = new Material[] { Material.COD, Material.SALMON, Material.TROPICAL_FISH,
				Material.PUFFERFISH };
		return types[new Random().nextInt(types.length)];
	}

	@Override
	public ItemStack getItemStack(int count) {
		return getItemStack(Double.valueOf(count));
	}

	public ItemStack getItemStack(Double money_multi) {
		ItemStack i = new ItemStack(getType());
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(rarity.getPrefix() + " " + name);
		meta.setLocalizedName(getKey());
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "크기 : " + size.getName());
		lore.add(ChatColor.WHITE + "희귀도 : " + rarity.getColor() + rarity.name());
		double len = length.getRandom();
		if (len > 0)
			lore.add(ChatColor.WHITE + "길이 : " + Math.ceil(len));
		double now_chance = new Random().nextDouble();
		if (man_chance > 0)
			lore.add(ChatColor.WHITE + "성별 : " + (now_chance < man_chance ? "수컷" : "암컷"));
		lore.add(ChatColor.WHITE + "가격 : "
				+ (long) (money_multi * rarity.getMulti() * price
						* (1 + length.getPercent(len) * 3
								* (man_chance == 0.5 ? 1
										: (man_chance > 0.5 ? (now_chance < man_chance ? 2 - man_chance : 1)
												: (now_chance > man_chance ? 1 + man_chance : 1))))));
		lore.add(ChatColor.GREEN + "영양 : " + getHelath());
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}
}
