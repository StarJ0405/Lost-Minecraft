package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantItem extends Items implements Buyable {

	public EnchantItem(String key, Material type, ChatColor color) {
		super(key, type, color);
	}

	@Override
	public ItemStack getItemStack(int count) {
		ItemStack i = super.getItemStack(1);
		Enchantment[] enches = Enchantment.values();
		Enchantment ench = enches[new Random().nextInt(enches.length)];
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) i.getItemMeta();
//		meta.addStoredEnchant(ench, 1 + new Random().nextInt(ench.getMaxLevel()), true);
		meta.addStoredEnchant(ench, 1, true);
		i.setItemMeta(meta);
		return i;
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public int getMaxCount() {
		return 1;
	}

	@Override
	public BigInteger getMoney(Player player) {
		return BigInteger.valueOf(1000);
	}

	@Override
	public ItemStack getInfoItemStack(Player player, ItemStack i) {
		ItemStack item = Buyable.super.getInfoItemStack(player, super.getItemStack(i.getAmount()));
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		lore.add(0, ChatColor.GRAY + "랜덤 인챈트 랜덤 레벨");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
