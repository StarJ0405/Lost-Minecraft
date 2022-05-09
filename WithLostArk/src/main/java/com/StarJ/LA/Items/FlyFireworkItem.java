package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class FlyFireworkItem extends Items implements Buyable {

	public FlyFireworkItem(String key, Material type, ChatColor color) {
		super(key, type, color);
		if (lore.size() == 0)
			lore.add(ChatColor.GREEN + "비행용 폭죽");
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	@Override
	public ItemStack getItemStack(int count) {
		Random r = new Random();
		ItemStack item = new ItemStack(type, count);
		FireworkMeta meta = (FireworkMeta) item.getItemMeta();
		meta.setPower(1 + r.nextInt(3));
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public BigInteger getMoney(Player player) {
		return BigInteger.valueOf(1600);

	}

	@Override
	public int getCount() {
		return 16;
	}

	@Override
	public ItemStack getInfoItemStack(Player player, ItemStack i) {
		return Buyable.super.getInfoItemStack(player, item.clone());

	}
}
