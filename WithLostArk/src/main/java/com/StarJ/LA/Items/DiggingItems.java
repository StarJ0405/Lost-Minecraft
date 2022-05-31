package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DiggingItems extends Items {
	private final static List<DiggingItems> list = new ArrayList<DiggingItems>();
	private final static HashMap<Material, String> translate = getTranslate();
	//
	private final PotionItems potion;
	private final ItemStack[] ings;
	private final int result_count;

	public DiggingItems(String key, ChatColor color, PotionItems potion, ItemStack[] ings) {
		this(key, color, potion, ings, 1);
	}

	public DiggingItems(String key, ChatColor color, PotionItems potion, ItemStack[] ings, int result_count) {
		super("BASICS_DIGGING", key, Material.MAP, color);
		this.potion = potion;
		this.ings = ings;
		this.result_count = result_count;
		lore.add(ChatColor.GREEN + "순서에 맞춰 아이템 넣어주세요.");
		for (ItemStack ing : ings)
			if (ing != null) {
				Material type = ing.getType();
				lore.add(ChatColor.WHITE + (translate.containsKey(type) ? translate.get(type) : type.name()) + " X "
						+ ing.getAmount());
			}
		list.add(this);
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack item = super.getItemStack();
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
		lore.add(ChatColor.DARK_GRAY + "" + ChatColor.MAGIC + new Random().nextDouble());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static HashMap<Material, String> getTranslate() {
		HashMap<Material, String> translate = new HashMap<Material, String>();
		// Mining
		translate.put(Material.COPPER_INGOT, "구리 주괴");
		translate.put(Material.IRON_INGOT, "철 주괴");
		translate.put(Material.GOLD_INGOT, "금 주괴");
		translate.put(Material.DIAMOND, "다이아몬드");
		translate.put(Material.EMERALD, "에메랄드");
		translate.put(Material.NETHERITE_INGOT, "네더라이트 주괴");
		translate.put(Material.REDSTONE, "레드스톤 가루");
		translate.put(Material.LAPIS_LAZULI, "청금석");
		// Chopping
		translate.put(Material.BAMBOO, "대나무");
		translate.put(Material.ACACIA_LOG, "아카시아나무 원목");
		translate.put(Material.BIRCH_LOG, "자작나무 원목");
		translate.put(Material.DARK_OAK_LOG, "짙은 참나무 원목");
		translate.put(Material.JUNGLE_LOG, "정글 나무 원목");
		translate.put(Material.OAK_LOG, "참나무");
		translate.put(Material.SPRUCE_LOG, "가문비나무 원목");
		return translate;
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
		return result_count;
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
