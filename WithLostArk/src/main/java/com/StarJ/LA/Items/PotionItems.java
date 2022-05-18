package com.StarJ.LA.Items;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.StarJ.LA.Systems.Basics;

public abstract class PotionItems extends Items {
	private static List<PotionItems> list = new ArrayList<PotionItems>();
	//
	private final String pre;
	private final String sub;
	protected final double power;

	public PotionItems(String key, Material type, ChatColor color, String pre, String sub, double power) {
		super("POTION", key, type, color);
		list.add(this);
		this.pre = pre;
		this.sub = sub;
		this.power = power;
	}

	public static List<PotionItems> getPotionItems() {
		return list;
	}

	public abstract boolean Use(Player player, ItemStack item);

	public int getCooldown() {
		return 20 * 30;
	}

	public static double getValue(ItemStack item) {
		Items i = Items.valueOf(item);
		if (i != null && i instanceof PotionItems) {
			PotionItems pi = (PotionItems) i;
			if (item.hasItemMeta() && item.getItemMeta().hasLore())
				for (String l : item.getItemMeta().getLore())
					if (l.contains(pi.pre) && l.contains(pi.sub)) {
						try {
							String sp = l.split(pi.pre)[1].split(pi.sub)[0];
							if (pi.sub.equals(""))
								sp = l.split(pi.pre)[1];
							return Double.parseDouble(sp);
						} catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
							break;
						}
					}

		}
		return 0;
	}

	@Deprecated
	public static Rank getRank(ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
			for (String l : item.getItemMeta().getLore())
				if (l.contains("RANK : "))
					try {
						return Rank.valueOf(l.split("RANK : ")[1]);
					} catch (Exception ex) {
					}
		}
		return Rank.Trash;
	}

	@Override
	public ItemStack getItemStack(int level) {
		Random r = new Random();
		ItemStack item = super.getItemStack(1);
		ItemMeta meta = item.getItemMeta();
		Rank rank = Rank.getRandomRank();
		double chance = Basics.Potioning.getChance(level).doubleValue();
		double power = this.power;
		if (level > 40 && r.nextDouble() < chance / 5) {
			int ord = rank.ordinal();
			if (ord < Rank.values().length - 1)
				rank = Rank.values()[ord + 1];
		}
		if (level > 10 && r.nextDouble() < chance / 2)
			power *= 1 + level * 0.02;
		meta.setDisplayName(rank.getPrefix() + " " + this.key);
		List<String> lore = new ArrayList<String>();
		lore.addAll(this.lore);
		lore.add(ChatColor.GREEN + pre + Math.round(power * rank.getMulti() * 10.0) / 10.0 + sub);
		lore.add(rank.getColor() + "RANK : " + rank.name());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public enum Rank {
		Trash(ChatColor.GRAY, "형편없는", 9600, 1), Common(ChatColor.WHITE, "평범한", 4800, 2),
		Special(ChatColor.YELLOW, "특별한", 2400, 3), Rare(ChatColor.AQUA, "희귀한", 1200, 5),
		Epic(ChatColor.LIGHT_PURPLE, "환상적인", 600, 10), Legendary(ChatColor.RED, "전설적인", 300, 15),
		God(ChatColor.GOLD, "신이 만든", 100, 20),
		//
		;

		private final int chance;
		private final ChatColor color;
		private final String prefix;
		private final int multi;

		private Rank(ChatColor color, String prefix, int chance, int multi) {
			this.color = color;
			this.prefix = prefix;
			this.chance = chance;
			this.multi = multi;
		}

		public String getPrefix() {
			return color + prefix;
		}

		public BigDecimal getChance() {
			return BigDecimal.valueOf(chance);
		}

		public int getMulti() {
			return multi;
		}

		public ChatColor getColor() {
			return color;
		}

		public static Rank getRandomRank() {
			int num = 0;
			Rank[] ranks = Rank.values().clone();
			for (Rank r : ranks)
				if (r != null)
					num += r.getChance().intValue();

			int rand = new Random().nextInt(num);
			int chance = 0;
			for (Rank r : ranks)
				if (r != null) {
					chance += r.getChance().intValue();
					if (rand < chance)
						return r;
				}

			return Rank.Trash;
		}
	}
}
