package com.StarJ.LA.Systems.Comparables;

import java.util.Comparator;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.StarJ.LA.Items.Items;

public class ItemComparator implements Comparator<ItemStack> {

	@SuppressWarnings("deprecation")
	@Override
	public int compare(ItemStack o1, ItemStack o2) {
		if (o1 != null && o2 != null) {
			Items io1 = Items.valueOf(o1);
			Items io2 = Items.valueOf(o2);
			if (io1 != null && io2 != null && io1.getKeyName().equals(io2.getKeyName())) {
				if (o1.getAmount() == o2.getAmount()) {
					return 0; // Items 개수까지 같음
				}
				return 1;// Items 종류가 같음
			} else if (io1 == null && io2 == null)
				if (o1.getType().equals(o2.getType())) {
					if (o1.hasItemMeta() && o2.hasItemMeta()) {
						ItemMeta mo1 = o1.getItemMeta();
						ItemMeta mo2 = o2.getItemMeta();
						if (mo1.hasDisplayName() && mo2.hasDisplayName()
								&& mo1.getDisplayName().equals(mo2.getDisplayName())) {
							if (mo1.hasLore() && mo2.hasLore() && mo1.getLore().equals(mo2.getLore())) {
								if (o2.getAmount() == o2.getAmount()) {
									if (mo1.getEnchants().equals(mo2.getEnchants())) {
										if (o1.getDurability() == o2.getDurability())
											return 7;// 내구도까지 같음.
										return 6;// 인챈트까지 같음
									}
									return 5;// 개수까지 같음
								}
								return 4;// Lore까지 같음
							}
							if (o2.getAmount() == o2.getAmount())
								return 8;// 개수만 같음

							return 3;// 아이템 이름이 같음
						}
					}
					return 2; // 아이템 타입만 같음 혹은 세부 내용이 없음.
				}

		}
		return -1; // 완전 다름
	}
}
