package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EquipItem extends Items implements Buyable {
	private static List<Material> list = getList();

	public EquipItem(String key, Material type, ChatColor color) {
		super(key, type, color);
		if (lore.size() == 0)
			lore.add(ChatColor.GREEN + "랜덤 전투");
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	private static List<Material> getList() {
		List<Material> list = new ArrayList<Material>();
		for (Material m : Material.values())
			if (m.name().contains("_SWORD") || m.equals(Material.BOW) || m.equals(Material.CROSSBOW)
					|| m.name().contains("_HELMET") || m.name().contains("_CHESTPLATE")
					|| m.name().contains("_HORSE_ARMOR") || m.name().contains("_LEGGINGS")
					|| m.name().contains("_BOOTS") || m.equals(Material.SHIELD)
					|| m.equals(Material.TRIDENT) && !m.name().contains("LEGACY"))
				list.add(m);
		return list;
	}

	@Override
	public ItemStack getItemStack(int count) {
		Collections.shuffle(list);
		return new ItemStack(list.get(0), count);
	}

	@Override
	public BigInteger getMoney(Player player) {
		return BigInteger.valueOf(100);

	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public ItemStack getInfoItemStack(Player player, ItemStack i) {
		return Buyable.super.getInfoItemStack(player, item.clone());

	}
}
