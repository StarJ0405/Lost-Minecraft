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

public class InventoryItem extends Items implements Buyable {
	public InventoryItem(String key, Material type, ChatColor color) {
		super(key, type, color);
		lore.add(ChatColor.GREEN + "사용시 확장 인벤토리가 1줄 늘어납니다.");
		lore.add(ChatColor.GREEN + "확장 인벤토리 명령어 : /inv");
	}

	@Override
	public BigInteger getMoney(Player player) {
		return BigInteger.valueOf(100000 * (ConfigStore.getPlayerInventorySize(player) / 9 + 1));
	}

	@Override
	public int getCount() {
		return 1;
	}

	public void Use(Player player, ItemStack item, Block b, Entity et) {
		int size = ConfigStore.getPlayerInventorySize(player) + 9;
		ConfigStore.setPlayerInventory(player, size);
		if (item != null && !item.getType().equals(Material.AIR))
			item.setAmount(item.getAmount() - 1);
		player.sendMessage(ChatColor.GREEN + "인벤토토리를 확장했습니다.");
		player.sendMessage(ChatColor.GREEN + "총사이즈 : " + size);
		List<ItemStack> items = ConfigStore.getPlayerInventory(player);
		for (int c = items.size(); c < size; c++)
			items.add(new ItemStack(Material.AIR));
		ConfigStore.setPlayerInventory(player, items);
	}

}
