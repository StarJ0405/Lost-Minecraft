package com.StarJ.LA.Skills.Reaper_Lunarsound;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.StarJ.LA.Core;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;

public class Distortion extends Skills {

	public Distortion() {
		// 12
		// 12 - 4 = 8
		super("distortion", "디스토션", 8.0d, ChatColor.DARK_PURPLE,
				ChatColor.YELLOW + "일반                    " + ChatColor.DARK_PURPLE + "[그림자 스킬]", "전방으로 이동하며 피해를 줍니다.",
				" - 시전 후 4초 동안 이동속도 +30%");
	}

	private double getFirstIdentity() {
		return 150;
	}

	private double getFirstMaxIdentity() {
		return 350;
	}

	private double getSecondIdentity() {
		return 100;
	}

	private double getSecondMaxIdentity() {
		return 350;
	}

	public float getSpeed() {
		return 0.3f;
	}

	private double getDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		return 185d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	public double getDuration() {
		return 4.0d;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Location loc = player.getLocation();
		Vector dir = loc.getDirection().multiply(0.5);
		Location eyeloc = player.getEyeLocation().clone();
		Location bodyloc = eyeloc.clone().subtract(0, 1, 0);
		double identity = HashMapStore.getIdentity(player);
		double add = 0;
		List<LivingEntity> les = new ArrayList<LivingEntity>();
		for (int c = 0; c < 18; c++) {
			eyeloc = eyeloc.add(dir);
			bodyloc = bodyloc.add(dir);
			if (!eyeloc.getBlock().isPassable() || !bodyloc.getBlock().isPassable()) {
				eyeloc = eyeloc.subtract(dir);
				bodyloc = bodyloc.subtract(dir);
				break;
			}
			Effects.spawnRedStone(bodyloc, 111, 0, 204, 2.5f, 9, 0.2, 0.2, 0.2);
			for (Entity et : eyeloc.getWorld().getNearbyEntities(eyeloc, 1, 1.5, 1))
				if (et instanceof LivingEntity) {
					LivingEntity le = ((LivingEntity) et);
					if (!et.getUniqueId().equals(player.getUniqueId()))
						if (!les.contains(le))
							if (Skills.canAttack(player, et)) {
								damage(player, le, getDamage(player));
								les.add(le);
								if (add == 0) {
									add += getFirstIdentity();
								} else {
									add += getFirstIdentity() / 10;
								}
								player.playSound(le.getLocation(), Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 2f, 1f);
							}
				}
		}
		player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
		if (add > getFirstMaxIdentity())
			add = getFirstMaxIdentity();
		HashMapStore.setIdentity(player, identity + add);
		player.teleport(bodyloc);
		new BukkitRunnable() {
			@Override
			public void run() {
				double identity = HashMapStore.getIdentity(player);
				double add = 0.0d;
				for (LivingEntity le : les)
					if (Skills.canAttack(player, le)) {
						damage(player, le, getDamage(player));
						player.playSound(le.getLocation(), Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 2f, 1f);
						if (add == 0) {
							add += getSecondIdentity();
						} else {
							add += getSecondIdentity() * 0.5;
						}
					}
				if (add > getSecondMaxIdentity())
					add = getSecondMaxIdentity();
				HashMapStore.setIdentity(player, identity + add);
			}
		}.runTaskLater(Core.getCore(), 2);
		BuffRunnable.run(player, this, getDuration(), slot);
		Jobs job = ConfigStore.getJob(player);
		if (job != null)
			player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
		Skills.Persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public void BuffEnd(Player player, int slot) {
		super.BuffEnd(player, slot);
		Jobs job = ConfigStore.getJob(player);
		if (job != null)
			player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
