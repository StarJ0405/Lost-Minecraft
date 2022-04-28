package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Systems.ConfigStore;

public class MoneyItem extends Items implements RightClickable {

	public MoneyItem(String key, Material type, ChatColor color) {
		super(key, type, color);
	}

	public MoneyItem(String key, Material type, ChatColor color, BigInteger money) {
		super(key, type, color);
		setMoney(money);
	}

	@Override
	public void RightClick(Player player, ItemStack item, Block b, Entity et) {
		BigInteger money = getMoney(item);
		if (money.compareTo(BigInteger.ZERO) > 0) {
			ConfigStore.setPlayerMoney(player, ConfigStore.getPlayerMoney(player).add(money));
			item.setAmount(item.getAmount() - 1);
			player.sendMessage(ChatColor.GREEN + "" + money.toString() + ChatColor.WHITE + "원 획득하셨습니다.");
		}
	}

	public ItemStack setMoney(BigInteger money) {
		return setMoney(money, 1);
	}

	public ItemStack setMoney(BigInteger money, int count) {
		if (lore.size() == 0) {
			lore.add(ChatColor.WHITE + "액수 : " + money);
		} else
			lore.set(0, ChatColor.WHITE + "액수 : " + money);
		return getItemStack(count);
	}

	public static BigInteger getMoney(ItemStack i) {
		if (i != null && i.hasItemMeta() && i.getItemMeta().hasLore()) {
			List<String> lore = i.getItemMeta().getLore();
			if (lore.size() > 0) {
				String[] sp = lore.get(0).split(" : ");
				if (sp.length > 1)
					try {
						return new BigInteger(sp[1]);
					} catch (IllegalArgumentException ex) {

					}
			}
		}
		return BigInteger.ZERO;
	}

	@Override
	public boolean isRightCancel() {
		return true;
	}
}
