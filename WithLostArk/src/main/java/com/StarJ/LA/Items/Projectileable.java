package com.StarJ.LA.Items;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public interface Projectileable {
	public abstract Projectile getProjectile(Player player, ItemStack item, Vector vec);

	public abstract void Hit(Projectile pro, Entity et);

	public abstract void Hit(Projectile pro, Block block);
}
