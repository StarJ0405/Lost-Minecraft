package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface Buyable {

	public abstract BigInteger getMoney(Player player);

	public abstract int getCount();

	public default ItemStack getInfoItemStack(Player player, ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
		if (getMoney(player).doubleValue() > 0) {
			lore.add(ChatColor.YELLOW + "구매 가격 : " + getMoney(player).toString());
		} else
			lore.add(ChatColor.YELLOW + "구매 불가");
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}

	public static BigInteger getMoney(ItemStack i) {
		if (i.hasItemMeta() && i.getItemMeta().hasLore()) {
			for (String lore : i.getItemMeta().getLore())
				if (lore.contains("구매 가격 : ")) {
					String[] sp = lore.split(" : ");
					if (sp.length > 1)
						try {
							return new BigInteger(sp[1]);
						} catch (NumberFormatException ex) {

						}
				}
		}
		return BigInteger.ZERO;
	}
}
