package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.StarJ.LA.Systems.Stats;

public class JewerlyItems extends Items {
	private static List<JewerlyItems> list = new ArrayList<JewerlyItems>();

	//
	private final Stats stat;
	private final Rank rank;

	public JewerlyItems(String key, Material type, ChatColor color, Stats stat, Rank rank) {
		super("JEWERLY", key, type, color);
		list.add(this);
		this.stat = stat;
		this.rank = rank;
		if (stat != null)
			lore.add(ChatColor.WHITE + stat.getDisplayname() + " : +" + rank.getStat());
	}

	@Override
	public ItemStack getItemStack(int count) {
		ItemStack i = super.getItemStack(1);
		ItemMeta meta = i.getItemMeta();
		meta.addAttributeModifier(Attribute.GENERIC_FOLLOW_RANGE, new AttributeModifier(UUID.randomUUID(),
				System.currentTimeMillis() + "", 0, Operation.ADD_NUMBER, EquipmentSlot.CHEST));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}

	@Override
	public int getMaxCount() {
		return 1;
	}

	public enum Rank {
		One(25, 0.6), Two(50, 0.3), Three(100, 0.1)
		//
		;

		private final int stat;
		private final double chance;

		private Rank(int stat, double chance) {
			this.stat = stat;
			this.chance = chance;
		}

		public int getStat() {
			return stat;
		}

		public double getChance() {
			return chance;
		}
	}

	public Stats getStat() {
		return stat;
	}

	public Rank getRank() {
		return rank;
	}

	public static List<JewerlyItems> getJewerlyItems() {
		return list;
	}

	public static JewerlyItems valueOf(String key) {
		if (key != null)
			for (JewerlyItems item : getJewerlyItems())
				if (item.getKey().equalsIgnoreCase(key) || item.getKeyName().equalsIgnoreCase(key))
					return item;
		return null;
	}

	public static JewerlyItems valueOf(ItemStack i) {
		if (i != null && i.hasItemMeta() && i.getItemMeta().hasLocalizedName())
			return valueOf(i.getItemMeta().getLocalizedName());
		return null;
	}
}
