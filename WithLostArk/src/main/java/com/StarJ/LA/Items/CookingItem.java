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

import com.StarJ.LA.Systems.ConfigStore;
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
		ItemStack item = super.getItemStack(1);
		ItemMeta meta = item.getItemMeta();
		Rank rank = Rank.getRandomRank();
		meta.setDisplayName(rank.getPrefix() + " 요리");
		lore.add(ChatColor.GREEN + "회복량 : " + count * rank.getHealth());
		lore.add("RANK : " + rank.getPrefix() + rank.name());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public boolean use(Player player, ItemStack item) {
		Jobs job = ConfigStore.getJob(player);
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
				player.playSound(player, Sound.ITEM_TOTEM_USE, 2f, 0.5f);
				HashMapStore.setHealth(player, health);
			} else {
				player.sendMessage(ChatColor.RED + "체력이 이미 최대치입니다.");
				player.closeInventory();
			}
		}
		return true;
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
		private final int health;

		private Rank(ChatColor color, String prefix, int chance, int health) {
			this.color = color;
			this.prefix = prefix;
			this.chance = chance;
			this.health = health;
		}

		public String getPrefix() {
			return color + prefix;
		}

		public BigDecimal getChance() {
			return BigDecimal.valueOf(chance);
		}

		public int getHealth() {
			return health;
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
