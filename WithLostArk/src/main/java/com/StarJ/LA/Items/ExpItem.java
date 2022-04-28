package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import com.StarJ.LA.Core;

public class ExpItem extends Items implements RightClickable, Projectileable, Buyable {

	public ExpItem(String key, Material type, ChatColor color) {
		super(key, type, color);
		lore.add(ChatColor.WHITE + "경험치 : 0 - 100");
	}

	@Override
	public void RightClick(Player player, ItemStack item, Block b, Entity et) {
		item.setAmount(item.getAmount() - 1);
		getProjectile(player, item, player.getLocation().getDirection());
	}

	@Override
	public Projectile getProjectile(Player player, ItemStack item, Vector vec) {
		ThrownExpBottle exp = player.launchProjectile(ThrownExpBottle.class, vec);
		exp.setMetadata("key", new FixedMetadataValue(Core.getCore(), key));
		return exp;
	}

	@Override
	public void Hit(Projectile pro, Entity et) {
		pro.remove();
		Location loc = et.getLocation();
		ExperienceOrb orb = (ExperienceOrb) loc.getWorld().spawnEntity(loc, EntityType.EXPERIENCE_ORB);
		orb.setExperience(new Random().nextInt(100));
	}

	@Override
	public void Hit(Projectile pro, Block block) {
		pro.remove();
		Location loc = block.getLocation();
		ExperienceOrb orb = (ExperienceOrb) loc.getWorld().spawnEntity(loc, EntityType.EXPERIENCE_ORB);
		orb.setExperience(new Random().nextInt(100));
	}

	@Override
	public boolean isRightCancel() {
		return true;
	}

	@Override
	public BigInteger getMoney(Player player) {
		return BigInteger.valueOf(1000);
	}

	@Override
	public int getCount() {
		return 1;
	}
}
