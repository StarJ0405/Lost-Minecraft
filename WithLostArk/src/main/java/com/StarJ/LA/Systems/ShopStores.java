package com.StarJ.LA.Systems;

import java.math.BigInteger;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.StarJ.LA.Core;

public enum ShopStores {
	Trash(ChatColor.GRAY + "잡템 상인", GUIStores.trash), Seller(ChatColor.GREEN + "잡화 상인", GUIStores.seller),
	Warp(ChatColor.AQUA + "워프", GUIStores.warp), Coin(ChatColor.GOLD + "코인", GUIStores.coin),
	//
	;

	private static HashMap<Material, Long> prices = new HashMap<Material, Long>();
	private final String title;
	private final GUIStores gui;

	private ShopStores(String title, GUIStores gui) {
		this.title = title;
		this.gui = gui;
	}

	public String getTitle() {
		return title;
	}

	public GUIStores getGui() {
		return gui;
	}

	public Entity spawnEntity(Location loc) {
		Villager et = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		et.setAI(false);
		et.setSilent(true);
		et.setCustomName(title);
		et.setCustomNameVisible(true);
		et.setAdult();
		et.setCanPickupItems(false);
		et.setPersistent(true);
		et.setMetadata("key", new FixedMetadataValue(Core.getCore(), this.name()));
		HashMapStore.setStores(loc, this);
		return et;
	}

	public static BigInteger getPrice(ItemStack item) {
		if (item == null)
			return BigInteger.ZERO;
		Material type = item.getType();
		if (item.hasItemMeta() && item.getItemMeta().hasLore())
			for (String str : item.getItemMeta().getLore())
				if (str.contains("가격 : ")) {
					String[] sp = str.split("가격 : ");
					if (sp.length > 1)
						try {
							return new BigInteger(sp[1]);
						} catch (NumberFormatException ex) {

						}
				}
		return prices.containsKey(type) ? BigInteger.valueOf(prices.get(type)) : BigInteger.TEN;
	}

	public static void initial() {
		// 농사
		prices.put(Material.POISONOUS_POTATO, 50l);
		prices.put(Material.BREAD, 60l);
		prices.put(Material.COOKED_PORKCHOP, 30l);
		prices.put(Material.PUFFERFISH, 50l);
		prices.put(Material.TROPICAL_FISH, 120l);
		prices.put(Material.CAKE, 3500l);
		prices.put(Material.COOKED_BEEF, 30l);
		prices.put(Material.COOKED_CHICKEN, 30l);
		prices.put(Material.BAKED_POTATO, 30l);
		prices.put(Material.PUMPKIN_PIE, 400l);
		prices.put(Material.COOKED_RABBIT, 30l);
		prices.put(Material.RABBIT_STEW, 400l);
		prices.put(Material.COOKED_MUTTON, 20l);
		prices.put(Material.BEETROOT_SOUP, 200l);
		prices.put(Material.MUSHROOM_STEM, 50l);
		prices.put(Material.COOKIE, 30l);
		prices.put(Material.SUGAR_CANE, 50l);
		prices.put(Material.EGG, 50l);
		prices.put(Material.GLISTERING_MELON_SLICE, 150l);
		prices.put(Material.GOLDEN_CARROT, 150l);

		// 사냥
		prices.put(Material.STRING, 100l);
		prices.put(Material.INK_SAC, 100l);
		prices.put(Material.GLOW_INK_SAC, 150l);
		prices.put(Material.BONE, 100l);
		prices.put(Material.GUNPOWDER, 100l);
		prices.put(Material.ENDER_PEARL, 30l);
		prices.put(Material.BLAZE_ROD, 200l);
		prices.put(Material.BLAZE_POWDER, 100l);
		prices.put(Material.FIRE_CHARGE, 100l);
		prices.put(Material.PRISMARINE_SHARD, 50l);

		// 도축
		prices.put(Material.FEATHER, 40l);
		prices.put(Material.LEATHER, 100l);
		prices.put(Material.INK_SAC, 100l);
		prices.put(Material.RABBIT_HIDE, 25l);
		prices.put(Material.SHULKER_SHELL, 100l);

		// 광산
		prices.put(Material.COPPER_BLOCK, 180l);
		prices.put(Material.IRON_INGOT, 200l);
		prices.put(Material.IRON_BLOCK, 2000l);
		prices.put(Material.GOLD_INGOT, 200l);
		prices.put(Material.GOLD_BLOCK, 2000l);
		prices.put(Material.DIAMOND, 800l);
		prices.put(Material.DIAMOND_BLOCK, 8000l);
		prices.put(Material.EMERALD_BLOCK, 440l);
		prices.put(Material.ANCIENT_DEBRIS, 7500l);
		prices.put(Material.NETHERITE_SCRAP, 7550l);
		prices.put(Material.NETHERITE_INGOT, 33330l);
		prices.put(Material.NETHERITE_BLOCK, 300000l);
		prices.put(Material.REDSTONE_BLOCK, 180l);
		prices.put(Material.LAPIS_BLOCK, 180l);

		// 나무
		prices.put(Material.BAMBOO, 10l);
		prices.put(Material.ACACIA_LOG, 250l);
		prices.put(Material.BIRCH_LOG, 250l);
		prices.put(Material.DARK_OAK_LOG, 250l);
		prices.put(Material.JUNGLE_LOG, 250l);
		prices.put(Material.OAK_LOG, 250l);
		prices.put(Material.SPRUCE_LOG, 250l);
		prices.put(Material.STRIPPED_ACACIA_LOG, 250l);
		prices.put(Material.STRIPPED_BIRCH_LOG, 250l);
		prices.put(Material.STRIPPED_DARK_OAK_LOG, 250l);
		prices.put(Material.STRIPPED_JUNGLE_LOG, 250l);
		prices.put(Material.STRIPPED_OAK_LOG, 250l);
		prices.put(Material.STRIPPED_SPRUCE_LOG, 250l);

		// 특수
		prices.put(Material.GOLDEN_APPLE, 3000l);
		prices.put(Material.ENCHANTED_GOLDEN_APPLE, 15000l);
		prices.put(Material.HEART_OF_THE_SEA, 100000l);

	}
}
