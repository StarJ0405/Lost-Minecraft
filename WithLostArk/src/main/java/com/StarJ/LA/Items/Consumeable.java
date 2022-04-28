package com.StarJ.LA.Items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Consumeable {
	public abstract void Comsume(Player player, ItemStack item);

	public abstract boolean isConsumeCancel();
}
