package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.StarJ.LA.Systems.Fishes.FIshCookedType;
import com.StarJ.LA.Systems.Fishes.Length;
import com.StarJ.LA.Systems.Fishes.NormalType;
import com.StarJ.LA.Systems.Fishes.Rarity;
import com.StarJ.LA.Systems.Fishes.Size;

public class FishItem extends CookingIngredient {
	private final String fishes;
	private final String name;
	private final Size size;
	private final double man_chance;
	private final Length length;
	private final long price;
	private final Rarity rarity;
	private final NormalType normal;
	private final FIshCookedType cook;
	private final static List<FishItem> normals = new ArrayList<FishItem>();
	private final static List<FishItem> cooks = new ArrayList<FishItem>();

	public FishItem(String fishes, String key, ChatColor color, String name, Size size, double man_chance,
			Length length, long price, Rarity rarity, NormalType normal) {
		this(fishes, key, color, name, size, man_chance, length, price, rarity, normal, null);
	}

	public FishItem(String fishes, String key, ChatColor color, String name, Size size, double man_chance,
			Length length, long price, Rarity rarity, FIshCookedType cook) {
		this(fishes, key, color, name, size, man_chance, length, price, rarity, null, cook);
	}

	public FishItem(String fishes, String key, ChatColor color, String name, Size size, double man_chance,
			Length length, long price, Rarity rarity, NormalType normal, FIshCookedType cook) {
		super(cook != null ? "익힌_" + cook.getPrefix() + "_" + key
				: (normal != null ? normal.getPrefix() + "_" + key : key),
				cook != null ? cook.getType() : (normal != null ? normal.getType() : Material.BEDROCK), color,
				(cook != null ? cook.getBase() : (normal != null ? normal.getBase() : 1)) * rarity.getMulti() * 8,
				IngredientType.Fishing);
		this.fishes = fishes;
		this.name = name;
		this.size = size;
		this.man_chance = man_chance;
		this.length = length;
		this.price = price;
		this.rarity = rarity;
		this.normal = normal;
		this.cook = cook;
		if (cook != null) {
			cooks.add(this);
		} else if (normal != null)
			normals.add(this);
	}

	public Rarity getRarity() {
		return rarity;
	}

	public NormalType getNormalType() {
		return normal;
	}

	public FIshCookedType getCookType() {
		return cook;
	}

	public boolean isCooked() {
		return cook != null;
	}

	@Override
	public ItemStack getItemStack(int count) {
		return getItemStack(Double.valueOf(count));
	}

	public ItemStack getItemStack(Double money_multi) {
		ItemStack i = new ItemStack(this.type);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(rarity.getPrefix() + " " + this.name);
		meta.setLocalizedName(getKey());
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "크기 : " + this.size.getName());
		lore.add(ChatColor.WHITE + "희귀도 : " + this.rarity.getColor() + this.rarity.name());
		double len = this.length.getRandom();
		if (len > 0)
			lore.add(ChatColor.WHITE + "길이 : " + Math.ceil(len));
		double now_chance = new Random().nextDouble();
		if (this.man_chance > 0)
			lore.add(ChatColor.WHITE + "성별 : " + (now_chance < this.man_chance ? "수컷" : "암컷"));
		lore.add(ChatColor.WHITE + "가격 : "
				+ (long) (money_multi * rarity.getMulti() * this.price
						* (this.cook != null ? cook.getBase() : (this.normal != null ? normal.getBase() : 1))
						* (1 + this.length.getPercent(len) * 3
								* ((this.man_chance == 0.5 || this.man_chance == 1 || this.man_chance == 0) ? 1
										: (this.man_chance > 0.5
												? (now_chance < this.man_chance ? 0.5 + this.man_chance : 1)
												: (now_chance > this.man_chance ? 1.5 - this.man_chance : 1))))));
		lore.add(ChatColor.GREEN + "영양 : " + this.getHelath());
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}

	public static List<FishItem> getNormals() {
		return normals;
	}

	public static List<FishItem> getCooks() {
		return cooks;
	}

	public static ItemStack getCook(ItemStack item) {
		Items i = Items.valueOf(item);
		if (i != null && i instanceof FishItem) {
			FishItem pre = (FishItem) i;
			if (pre.getNormalType() != null) {
				FIshCookedType cooktype = FIshCookedType.getCookedType(pre.getNormalType());
				if (cooktype != null)
					for (FishItem cook : cooks)
						if (cook.getCookType() != null)
							if (pre.fishes.equals(cook.fishes) && cook.getCookType().equals(cooktype)
									&& cook.getRarity().equals(pre.getRarity())) {

								if (item.hasItemMeta() && item.getItemMeta().hasLore()
										&& item.getItemMeta().getLore().size() > 0) {
									ItemMeta pre_meta = item.getItemMeta();
									List<String> lore = pre_meta.getLore();
									for (int c = 0; c < lore.size(); c++)
										if (lore.get(c).contains("영양 : ")) {
											lore.set(c, ChatColor.GREEN + "영양 : " + cook.getHelath());
											break;
										}
									ItemStack now = cook.getItemStack();
									ItemMeta now_meta = now.getItemMeta();
									now_meta.setLore(lore);
									now.setItemMeta(now_meta);
									return now;
								} else
									return cook.getItemStack(0);
							}
			}

		}
		List<FishItem> copy = new ArrayList<FishItem>();
		copy.addAll(cooks);
		Collections.shuffle(copy);
		return copy.size() > 0 ? copy.get(0).getItemStack(0)
				: new ItemStack(FIshCookedType.getCookedType(item.getType()).getType());
	}

	public static Material getMaterial(EntityType type) {
		if (type.equals(EntityType.COD)) {
			return Material.COD;
		} else if (type.equals(EntityType.SALMON)) {
			return Material.SALMON;
		} else if (type.equals(EntityType.PUFFERFISH)) {
			return Material.PUFFERFISH;
		} else if (type.equals(EntityType.TROPICAL_FISH))
			return Material.TROPICAL_FISH;
		return Material.SALMON;
	}
}
