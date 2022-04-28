package com.StarJ.LA.Items;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface RightClickable {

	public abstract void RightClick(Player player, ItemStack item, Block b, Entity et);

	public abstract boolean isRightCancel();
}
