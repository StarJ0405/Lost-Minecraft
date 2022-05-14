package com.StarJ.LA.Skills.Reaper;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.StarJ.LA.Core;
import com.StarJ.LA.Skills.ProjectileRunnable;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;
import com.StarJ.LA.Systems.Runnable.ComboCoolRunnable;

public class Nightmare extends Skills {
	private static HashMap<UUID, UUID> target = new HashMap<UUID, UUID>();

	public Nightmare() {
		super("nightmare", "나이트메어", 10.0d, ChatColor.GREEN);
	}

	private int getDuration() {
		return 3 * 20;
	}

	private double getHitDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		return 5.0d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getTelportDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		return 3.0d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getHitIdentity() {
		return 100;
	}

	private double getTeleportIdentity() {
		return 20;
	}

	@Override
	public boolean Use(Player player, int slot) {
//		if (super.Use(player, slot))
//			return true;
		if (HashMapStore.isSkillStop(player))
			return true;
		if (target.containsKey(player.getUniqueId())) {
			Entity et = Bukkit.getEntity(target.get(player.getUniqueId()));
			if (et != null && !et.isDead() && et instanceof LivingEntity) {
				LivingEntity le = (LivingEntity) et;
				Location loc = le.getLocation().clone();
				Vector dir = loc.getDirection().multiply(0.5);
				dir.setY(0);
				player.teleport(loc.subtract(dir));
				new BukkitRunnable() {
					@Override
					public void run() {
						if (!le.isDead()) {
							le.setNoDamageTicks(0);
							le.damage(getTelportDamage(player), player);
						}
					}
				}.runTaskLater(Core.getCore(), 1);
				addIdentity(player, getTeleportIdentity());
			}
			player.getInventory().setItem(slot, getCoolItemStack());
			target.remove(player.getUniqueId());
		} else if (!super.Use(player, slot)) {
			Location loc = player.getLocation();
			Vector dir = loc.getDirection().multiply(2);
			Effects.Infos[] infos = new Effects.Infos[1];
			infos[0] = new Effects.RedStoneInfos(Effects.getRel(dir, 0, 0, 0), 0, 255, 0, 1, 2, 0.1, 0.1, 0.1);
			new ProjectileRunnable(player, this, slot, player.getEyeLocation(), dir, 10, Integer.MAX_VALUE, infos)
					.runTaskTimer(Core.getCore(), 0, 1);
		} else
			return true;
		Skills persona = Skills.Persona;
		BuffRunnable.has(player, persona);
		persona.BuffEnd(player, -1);
		Skills.Persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public boolean Hit(Player player, LivingEntity entity, int slot) {
		entity.setNoDamageTicks(0);
		entity.damage(getHitDamage(player), player);
		target.put(player.getUniqueId(), entity.getUniqueId());
		ComboCoolRunnable.run(player, this, getDuration(), slot);
		addIdentity(player, getHitIdentity());
		return true;
	}

	@Override
	public void comboEnd(Player player, int slot) {
		super.comboEnd(player, slot);
		target.remove(player.getUniqueId());
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
