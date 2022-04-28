package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToolItem extends Items implements Buyable {
	private List<Material> list = new ArrayList<Material>();

	public ToolItem(String key, Material type, ChatColor color) {
		super(key, type, color);
		if (lore.size() == 0)
			lore.add(ChatColor.GREEN + "랜덤 도구");
		meta.setLore(lore);
		item.setItemMeta(meta);
		if (list.size() == 0)
			for (Material m : Material.values())
				if (m.name().contains("_SHOVEL") || m.name().contains("_PICKAXE") || m.name().contains("_AXE")
						|| m.name().contains("_HOE") || m.equals(Material.FLINT_AND_STEEL) || m.equals(Material.SHEARS)
						|| m.equals(Material.SPYGLASS) && !m.name().contains("LEGACY"))
					list.add(m);
	}

	@Override
	public ItemStack getItemStack(int count) {
		Collections.shuffle(list);
		return new ItemStack(list.get(0), count);
	}

	@Override
	public BigInteger getMoney(Player player) {
		return BigInteger.valueOf(1000);

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
