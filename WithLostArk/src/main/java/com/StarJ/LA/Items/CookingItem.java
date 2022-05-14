package com.StarJ.LA.Items;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.StarJ.LA.Systems.Basics;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;

public class CookingItem extends Items {
	private static List<CookingItem> list = new ArrayList<CookingItem>();
	//

	public CookingItem(String key, ChatColor color) {
		super("COOKING", key, Material.HONEY_BOTTLE, color);
		list.add(this);
	}

	public static List<CookingItem> getCookingItems() {
		return list;
	}

	@Override
	public ItemStack getItemStack(int count) {
		return getItemStack(count, 1);
	}

	public ItemStack getItemStack(int health, int level) {
		Random r = new Random();
		ItemStack item = super.getItemStack(1);
		ItemMeta meta = item.getItemMeta();
		Rank rank = Rank.getRandomRank();
		double chance = Basics.Cooking.getChance(level).doubleValue();
		if (level > 40 && r.nextDouble() < chance / 5) {
			int ord = rank.ordinal();
			if (ord < Rank.values().length - 1)
				rank = Rank.values()[ord + 1];
		}
		if (level > 10 && r.nextDouble() < chance / 2)
			health *= 1 + level * 0.02;
		meta.setDisplayName(rank.getPrefix() + " " + this.key);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "회복량 : " + health * rank.getMulti());
		lore.add(rank.getColor() + "RANK : " + rank.name());
		if (level > 20 && r.nextDouble() < chance / 3)
			item.setAmount(1 + (level / 16));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static boolean use(Player player, ItemStack item) {
		Jobs job = ConfigStore.getJob(player);
		if (!player.hasCooldown(Material.HONEY_BOTTLE)) {
			if (job != null) {
				double max = job.getMaxHealth(player);
				double health = HashMapStore.getHealth(player);
				if (health < max) {
					if (item.hasItemMeta() && item.getItemMeta().hasLore())
						for (String lore : item.getItemMeta().getLore())
							if (lore.contains("회복량 : ")) {
								health += Integer.valueOf(lore.split("회복량 : ")[1]);
								break;
							}
					HashMapStore.setHealth(player, health);
					player.setCooldown(Material.HONEY_BOTTLE, 20 * 10);
					player.closeInventory();
					player.playSound(player, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 2f, 1f);
					Effects.Directional.HEART.spawnDirectional(player, player.getEyeLocation(), 5, 0.1, 0.1, 0.1, 1);
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "체력이 이미 최대치입니다.");
					player.closeInventory();
				}
			}
		} else {
			player.sendMessage(ChatColor.RED + "회복 쿨타임 : " + player.getCooldown(Material.HONEY_BOTTLE) / 20.0d + "초");
		}
		return false;
	}

	public static int getHealth(ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasLore())
			for (String lore : item.getItemMeta().getLore())
				if (lore.contains("회복량 : ")) {
					return Integer.valueOf(lore.split("회복량 : ")[1]);
				}
		return 0;
	}

	public enum Rank {
		Trash(ChatColor.GRAY, "형편없는", 9600, 1), Common(ChatColor.WHITE, "평범한", 4800, 2),
		Special(ChatColor.YELLOW, "특별한", 2400, 5), Rare(ChatColor.AQUA, "희귀한", 1200, 10),
		Epic(ChatColor.LIGHT_PURPLE, "환상적인", 600, 25), Legendary(ChatColor.RED, "전설적인", 300, 50),
		God(ChatColor.GOLD, "신이 만든", 100, 100),
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
