package com.StarJ.LA.Items;

import java.math.BigInteger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class FishBoxItem extends Items implements Buyable, RightClickable {
	public FishBoxItem(String key, ChatColor color) {
		super(key, Material.SHULKER_BOX, color);
		lore.add(ChatColor.GREEN + "물고기 전용 상자");
	}

	@Override
	public BigInteger getMoney(Player player) {
		return BigInteger.valueOf(100000);
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public void RightClick(Player player, ItemStack item, Block b, Entity et) {
		BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
		ShulkerBox box = (ShulkerBox) meta.getBlockState();
		Inventory open = Bukkit.createInventory(box, InventoryType.SHULKER_BOX, ChatColor.AQUA + "어항");
		open.setContents(box.getInventory().getContents());
		player.openInventory(open);
	}

	@Override
	public boolean isRightCancel() {
		return true;
	}

}
