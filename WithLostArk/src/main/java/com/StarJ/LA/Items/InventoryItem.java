package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.StarJ.LA.Systems.ConfigStore;

public class InventoryItem extends Items implements Buyable {
	public InventoryItem(String key, Material type, ChatColor color) {
		super(key, type, color);
		lore.add(ChatColor.GREEN + "사용시 확장 인벤토리가 1줄 늘어납니다.");
		lore.add(ChatColor.GREEN + "확장 인벤토리 명령어 : /inv");
	}

	@Override
	public BigInteger getMoney(Player player) {
		int size = ConfigStore.getPlayerInventorySize(player);
		if (size < getMax()) {
			return BigInteger.valueOf(100000 * (size / 9 + 1));
		} else
			return BigInteger.valueOf(8000000);
	}

	public int getMax() {
		return 5 * 9 * 5;
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public ItemStack getInfoItemStack(Player player, ItemStack i) {
		int size = ConfigStore.getPlayerInventorySize(player);
		if (size < getMax()) {
			return Buyable.super.getInfoItemStack(player, i);
		} else {
			i = i.clone();
			ItemMeta meta = i.getItemMeta();
			List<String> lore = new ArrayList<String>();
			if (getMoney(player).doubleValue() > 0) {
				lore.add(ChatColor.GREEN + "셜커 박스를 구매합니다.");
				lore.add(ChatColor.YELLOW + "구매 가격 : " + getMoney(player).toString());
			} else
				lore.add(ChatColor.YELLOW + "구매 불가");
			meta.setLore(lore);
			i.setItemMeta(meta);
			return i;
		}

	}

	public void Use(Player player, ItemStack item, Block b, Entity et) {
		int size = ConfigStore.getPlayerInventorySize(player);
		if (size < getMax()) {
			ConfigStore.setPlayerInventory(player, size + 9);
			if (item != null && !item.getType().equals(Material.AIR))
				item.setAmount(item.getAmount() - 1);
			player.sendMessage(ChatColor.GREEN + "인벤토토리를 확장했습니다.");
			player.sendMessage(ChatColor.GREEN + "총사이즈 : " + size);
			List<ItemStack> items = ConfigStore.getPlayerInventory(player);
			for (int c = items.size(); c < size; c++)
				items.add(new ItemStack(Material.AIR));
			ConfigStore.setPlayerInventory(player, items);
		} else if (player.getInventory().firstEmpty() != -1) {
			player.getInventory().addItem(new ItemStack(Material.SHULKER_BOX));
		} else
			player.getWorld().dropItemNaturally(player.getEyeLocation(), new ItemStack(Material.SHULKER_BOX));
	}

}
