package com.StarJ.LA.Systems;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public enum EnchantsType {
	Pickaxe(Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE,
			Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE),
	Shovel(Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL,
			Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL),
	Axe(Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE,
			Material.NETHERITE_AXE),
	Hoe(Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLDEN_HOE, Material.DIAMOND_HOE,
			Material.NETHERITE_HOE),
	Shears(Material.SHEARS), Fishing_Rod(Material.FISHING_ROD),
	Sword(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD,
			Material.DIAMOND_SWORD, Material.NETHERITE_SWORD),
	Bow(Material.BOW), Crossbow(Material.CROSSBOW), Trident(Material.TRIDENT),
	Helmet(Material.TURTLE_HELMET, Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET,
			Material.GOLDEN_HELMET, Material.DIAMOND_HELMET, Material.NETHERITE_HELMET),
	Chestplate(Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE,
			Material.GOLDEN_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE),
	Leggings(Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLDEN_LEGGINGS,
			Material.DIAMOND_LEGGINGS, Material.NETHERITE_LEGGINGS),
	Boots(Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.GOLDEN_BOOTS,
			Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS),
	Elytra(Material.ELYTRA)

	//
	;

	Material[] types;
	private static HashMap<Enchantment, Integer> max_level = new HashMap<Enchantment, Integer>();
	private static HashMap<Enchantment, String> display = new HashMap<Enchantment, String>();

	private EnchantsType(Material... types) {
		this.types = types;
	}

	public static EnchantsType getEnchantType(Material type) {
		for (EnchantsType enchant : values())
			for (Material m : enchant.types)
				if (m.equals(type))
					return enchant;
		return null;
	}

	public static int getEnchantMaxLevel(Enchantment enchant) {
		return max_level.containsKey(enchant) ? max_level.get(enchant) : 0;
	}

	public static String getEnchantName(Enchantment enchant) {
		return display.containsKey(enchant) ? display.get(enchant) : "";
	}

	public static ItemStack getEnchantItem(Enchantment enchant, int level, ItemStack book, String... lores) {
		double chance = 1.0d;
		if (book != null && book.hasItemMeta()) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
			chance += meta.getStoredEnchantLevel(enchant) * 0.2;
		}
		int max = getEnchantMaxLevel(enchant);
		ItemStack i = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + getEnchantName(enchant) + (max > 1 ? level : ""));
		List<String> lore = new ArrayList<String>();
		if (level <= max) {
			meta.setLocalizedName(enchant.getKey().toString());
			lore.add(ChatColor.GOLD + "?????? ?????? : " + max);
			double upgrade_chance = getUpgradeChance(level, chance, max);
			lore.add(ChatColor.GREEN + "?????? ?????? : " + Math.ceil(upgrade_chance * 100) + "%");
			double break_chance = getBreakChance(level, max);
			if (break_chance > 0)
				lore.add(ChatColor.GREEN + "?????? ?????? : " + Math.ceil(break_chance * 100) + "%");
			BigDecimal money = new BigDecimal(getNeedMoney(level, max));
			lore.add(ChatColor.WHITE + "?????? : " + money);
			if (upgrade_chance > 0)
				lore.add(ChatColor.LIGHT_PURPLE + "?????? ?????? : " + money
						.divide(BigDecimal.valueOf(upgrade_chance), 2, RoundingMode.CEILING).toBigInteger().toString());
		} else
			lore.add(ChatColor.RED + "??? ?????? ?????? ??????");
		lore.addAll(Arrays.asList(lores));
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}

	public static void initial() {
		// ??????
		Enchantment ench = Enchantment.DURABILITY;
		max_level.put(ench, 10);
		display.put(ench, "?????????");

		ench = Enchantment.MENDING;
		max_level.put(ench, 1);
		display.put(ench, "??????");

		// ??????
		ench = Enchantment.DIG_SPEED;
		max_level.put(ench, 10);
		display.put(ench, "??????");

		ench = Enchantment.SILK_TOUCH;
		max_level.put(ench, 1);
		display.put(ench, "????????? ??????");

		ench = Enchantment.LOOT_BONUS_BLOCKS;
		max_level.put(ench, 10);
		display.put(ench, "??????");

		ench = Enchantment.LUCK;
		max_level.put(ench, 10);
		display.put(ench, "????????? ??????");

		ench = Enchantment.LURE;
		max_level.put(ench, 5);
		display.put(ench, "??????");

		// ???
		ench = Enchantment.DAMAGE_ALL;
		max_level.put(ench, 10);
		display.put(ench, "????????????");

		ench = Enchantment.DAMAGE_UNDEAD;
		max_level.put(ench, 10);
		display.put(ench, "??????");

		ench = Enchantment.DAMAGE_ARTHROPODS;
		max_level.put(ench, 10);
		display.put(ench, "??????");

		ench = Enchantment.KNOCKBACK;
		max_level.put(ench, 3);
		display.put(ench, "?????????");

		ench = Enchantment.FIRE_ASPECT;
		max_level.put(ench, 4);
		display.put(ench, "??????");

		ench = Enchantment.SWEEPING_EDGE;
		max_level.put(ench, 5);
		display.put(ench, "?????????");

		ench = Enchantment.LOOT_BONUS_MOBS;
		max_level.put(ench, 10);
		display.put(ench, "??????");

		// ???
		ench = Enchantment.ARROW_DAMAGE;
		max_level.put(ench, 10);
		display.put(ench, "???");

		ench = Enchantment.ARROW_KNOCKBACK;
		max_level.put(ench, 3);
		display.put(ench, "????????????");

		ench = Enchantment.ARROW_FIRE;
		max_level.put(ench, 4);
		display.put(ench, "??????");

		ench = Enchantment.ARROW_INFINITE;
		max_level.put(ench, 1);
		display.put(ench, "??????");

		// ??????
		ench = Enchantment.QUICK_CHARGE;
		max_level.put(ench, 5);
		display.put(ench, "?????? ??????");

		ench = Enchantment.MULTISHOT;
		max_level.put(ench, 1);
		display.put(ench, "?????? ??????");

		ench = Enchantment.PIERCING;
		max_level.put(ench, 5);
		display.put(ench, "??????");

		// ?????????
		ench = Enchantment.LOYALTY;
		max_level.put(ench, 10);
		display.put(ench, "??????");

		ench = Enchantment.IMPALING;
		max_level.put(ench, 10);
		display.put(ench, "?????????");

		ench = Enchantment.RIPTIDE;
		max_level.put(ench, 10);
		display.put(ench, "??????");

		ench = Enchantment.CHANNELING;
		max_level.put(ench, 1);
		display.put(ench, "??????");

		// ?????????
		ench = Enchantment.PROTECTION_ENVIRONMENTAL;
		max_level.put(ench, 4);
		display.put(ench, "??????");

		ench = Enchantment.PROTECTION_FIRE;
		max_level.put(ench, 4);
		display.put(ench, "?????????????????? ??????");

		ench = Enchantment.PROTECTION_EXPLOSIONS;
		max_level.put(ench, 4);
		display.put(ench, "??????????????? ??????");

		ench = Enchantment.PROTECTION_PROJECTILE;
		max_level.put(ench, 4);
		display.put(ench, "?????????????????? ??????");

		ench = Enchantment.THORNS;
		max_level.put(ench, 4);
		display.put(ench, "??????");

		// ??????
		ench = Enchantment.OXYGEN;
		max_level.put(ench, 5);
		display.put(ench, "??????");

		ench = Enchantment.WATER_WORKER;
		max_level.put(ench, 1);
		display.put(ench, "?????????");

		// ??????
		ench = Enchantment.PROTECTION_FALL;
		max_level.put(ench, 4);
		display.put(ench, "????????? ??????");

		ench = Enchantment.DEPTH_STRIDER;
		max_level.put(ench, 3);
		display.put(ench, "?????????");

		ench = Enchantment.FROST_WALKER;
		max_level.put(ench, 3);
		display.put(ench, "????????? ??????");

		ench = Enchantment.SOUL_SPEED;
		max_level.put(ench, 5);
		display.put(ench, "?????? ??????");
	}

	public static BigInteger getNeedMoney(int level, int max) {
		return BigInteger.valueOf(level * 2000 * 10 / max);
	}

	public static double getUpgradeChance(int level, double chance, int max) {
		return (1.0 / (1.0 + level * level) * chance * max / 10.0);
	}

	public static boolean isUpgrade(int level, double chance, int max) {
		return new Random().nextDouble() < getUpgradeChance(level, chance, max);
	}

	public static double getBreakChance(int level, int max) {
		return level >= (max * 0.5) ? ((level - (max * 0.5)) * (level - (max * 0.5))) / max / max : 0;
	}

	public static boolean isBreak(int level, int max) {
		return new Random().nextDouble() < getBreakChance(level, max);
	}
}
