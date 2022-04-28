package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnimalItem extends Items implements Buyable {

	public AnimalItem(String key, Material type, ChatColor color) {
		super(key, type, color);
	}

	@Override
	public ItemStack getItemStack(int count) {
		Material[] types = new Material[] { Material.AXOLOTL_SPAWN_EGG, Material.BAT_SPAWN_EGG, Material.BEE_SPAWN_EGG,
				Material.CAT_SPAWN_EGG, Material.CHICKEN_SPAWN_EGG, Material.COD_SPAWN_EGG, Material.COW_SPAWN_EGG,
				Material.DOLPHIN_SPAWN_EGG, Material.DONKEY_SPAWN_EGG, Material.FOX_SPAWN_EGG,
				Material.GLOW_SQUID_SPAWN_EGG, Material.GOAT_SPAWN_EGG, Material.HORSE_SPAWN_EGG,
				Material.LLAMA_SPAWN_EGG, Material.MOOSHROOM_SPAWN_EGG, Material.MULE_SPAWN_EGG,
				Material.OCELOT_SPAWN_EGG, Material.PANDA_SPAWN_EGG, Material.PARROT_SPAWN_EGG, Material.PIG_SPAWN_EGG,
				Material.POLAR_BEAR_SPAWN_EGG, Material.PUFFERFISH_SPAWN_EGG, Material.RABBIT_SPAWN_EGG,
				Material.SALMON_SPAWN_EGG, Material.SHEEP_SPAWN_EGG, Material.SQUID_SPAWN_EGG,
				Material.TRADER_LLAMA_SPAWN_EGG, Material.TROPICAL_FISH_SPAWN_EGG, Material.TURTLE_SPAWN_EGG,
				Material.VILLAGER_SPAWN_EGG, Material.WANDERING_TRADER_SPAWN_EGG, Material.WOLF_SPAWN_EGG };
		Random r = new Random();
		Material now = types[r.nextInt(types.length)];
		return new ItemStack(now);
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public BigInteger getMoney(Player player) {
		return BigInteger.valueOf(10000);
	}

	@Override
	public ItemStack getInfoItemStack(Player player, ItemStack i) {
		ItemStack item = Buyable.super.getInfoItemStack(player, super.getItemStack(i.getAmount()));
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		lore.add(0, ChatColor.WHITE + "랜덤 몬스터 소환");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
