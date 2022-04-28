package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class ExpendablesItem extends Items implements Buyable {
	private List<Material> list = new ArrayList<Material>();

	public ExpendablesItem(String key, Material type, ChatColor color) {
		super(key, type, color);
		if (lore.size() == 0)
			lore.add(ChatColor.GREEN + "랜덤 소모품");
		meta.setLore(lore);
		item.setItemMeta(meta);
		if (list.size() == 0)
			for (Material m : Material.values())
				if (m.name().contains("ARROW") || m.equals(Material.TOTEM_OF_UNDYING)
						|| m.equals(Material.FIREWORK_ROCKET) || m.equals(Material.ENDER_PEARL)
						|| m.equals(Material.LEAD) || m.equals(Material.NAME_TAG))
					list.add(m);
	}

	@Override
	public ItemStack getItemStack(int count) {
		Collections.shuffle(list);
		Material type = list.get(0);
		Random r = new Random();
		if (type.name().contains("ARROW") || type.equals(Material.FIREWORK_ROCKET))
			count = 16;
		if (type.equals(Material.ENDER_PEARL) || type.equals(Material.LEAD))
			count = 4;
		if (type.equals(Material.FIREWORK_ROCKET)) {
			ItemStack item = new ItemStack(type, count);
			FireworkMeta meta = (FireworkMeta) item.getItemMeta();
			for (int c = 0; c < 1 + r.nextInt(3); c++) {
				Builder b = FireworkEffect.builder();
				b.withColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
				Type[] types = Type.values();
				b.with(types[r.nextInt(types.length)]);
				b.withFade(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
				b.trail(r.nextBoolean());
				b.flicker(r.nextBoolean());
				meta.addEffect(b.build());
			}
			meta.setPower(1 + r.nextInt(3));
			item.setItemMeta(meta);
			return item;
		} else if (type.equals(Material.TIPPED_ARROW)) {
			ItemStack item = new ItemStack(type, count);
			PotionMeta meta = (PotionMeta) item.getItemMeta();
			PotionType[] types = PotionType.values();
			PotionType now = types[r.nextInt(types.length)];
			boolean extend = now.isExtendable() ? r.nextBoolean() : false;
			boolean upgrade = now.isUpgradeable() ? r.nextBoolean() : false;
			if (extend && upgrade)
				if (r.nextBoolean()) {
					extend = false;
				} else
					upgrade = false;
			meta.setBasePotionData(new PotionData(now, extend, upgrade));
			item.setItemMeta(meta);
			return item;
		} else
			return new ItemStack(type, count);
	}

	@Override
	public BigInteger getMoney(Player player) {
		return BigInteger.valueOf(800);

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
