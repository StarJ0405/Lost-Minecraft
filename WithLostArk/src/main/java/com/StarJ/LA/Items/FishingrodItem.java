package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.StarJ.LA.Systems.Fishes.Size;

public class FishingrodItem extends Items implements Buyable {
	public FishingrodItem(String key, Material type, ChatColor color) {
		super(key, type, color);
		if (lore.size() == 0) {
			meta.setDisplayName(ChatColor.GREEN + "랜덤 낚시대");
			lore.add(ChatColor.WHITE + "어떤 낚시대가 뜰지 모른다.");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	@Override
	public ItemStack getItemStack(int count) {
		ItemStack i = new ItemStack(type);
		ItemMeta meta = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		Size[] sizes = Size.values();
		int num = new Random().nextInt(sizes.length+1);
		if (num >= sizes.length) {
			meta.setDisplayName(ChatColor.GREEN + "일반 낚시대");
			lore.add(ChatColor.WHITE + "어떤 물고기든 잘 잡힌다.");
		} else {
			Size size = sizes[num];
			meta.setDisplayName(ChatColor.GREEN + size.getName() + " 낚시대");
			lore.add(ChatColor.WHITE + "잡히는 크기 : " + size.name());
		}
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
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

	public static Size getSize(ItemStack item) {
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore())
			for (String lore : item.getItemMeta().getLore())
				if (lore.contains("잘 잡히는 크기 : ")) {
					String[] sp = lore.split(" : ");
					if (sp.length == 2)
						return Size.valueOf(sp[1]);
				}
		return null;
	}
}