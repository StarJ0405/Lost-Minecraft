package com.StarJ.LA.Skills.Reaper.LunarSound;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
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

public class Eclipse_Kadencha extends Skills {
	private static HashSet<UUID> speed = new HashSet<UUID>();

	public Eclipse_Kadencha() {
		// 쿨타임 : 300d
		// 무력 : 60 / 12 = 5d
		super("eclipse_kadencha", "월식: 카덴차", 300.0d, 5d, ChatColor.DARK_RED,
				ChatColor.YELLOW + "일반                                  " + ChatColor.DARK_RED + "[각성기]",
				"그림자와 달의 기운으로 그림자 지대를 소환한다.", "- 지대가 마지막에 폭발", "- 첫 공격 적중시 아덴 100%", "- 사용 중 페르소나 상태");
	}

	public boolean isActive(Player player) {
		return speed.contains(player.getUniqueId());
	}

	private double getFirstDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		return 3921d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getShadowDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 15695 / 10 = 1569
		return 1569d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getFinalDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		return 19603d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		player.sendTitle(ChatColor.GOLD + "각성", ChatColor.AQUA + "" + ChatColor.BOLD + "월식: 카덴차", 1, 20, 1);
		Location main = player.getLocation().clone().add(0, 0.5, 0);
		double range = 6;
		Vector dir = main.getDirection().clone().setY(0).normalize();
		new BukkitRunnable() {
			OfflinePlayer off = player;

			@Override
			public void run() {
				if (off.isOnline()) {
					Player player = off.getPlayer();
					if (ConfigStore.getPlayerStatus(player))
						player.setVelocity(
								player.getLocation().getDirection().multiply(-1).setY(0).normalize().setY(0.5));
				}
				this.cancel();
			}
		}.runTaskLater(Core.getCore(), 5);

//		Location loc_front_front_right = main.clone().add(Effects.getRel(dir, 0.31 * range, 1.0, 0.95 * range));
//		Location loc_front_right = main.clone().add(Effects.getRel(dir, 0.81 * range, 1.0, 0.59 * range));
//		Location loc_right = main.clone().add(Effects.getRel(dir, 1 * range, 1.0, 0));
//		Location loc_back_right = main.clone().add(Effects.getRel(dir, 0.81 * range, 1.0, -0.59 * range));
//		Location loc_back_back_right = main.clone().add(Effects.getRel(dir, 0.31 * range, 1.0, -0.95 * range));
//		Location loc_back_back_left = main.clone().add(Effects.getRel(dir, -0.31 * range, 1.0, -0.95 * range));
//		Location loc_back_left = main.clone().add(Effects.getRel(dir, -0.81 * range, 1.0, -0.59 * range));
//		Location loc_left = main.clone().add(Effects.getRel(dir, -1 * range, 1.0, 0));
//		Location loc_front_left = main.clone().add(Effects.getRel(dir, -0.81 * range, 1.0, 0.59 * range));
//		Location loc_front_front_left = main.clone().add(Effects.getRel(dir, -0.31 * range, 1.0, 0.95 * range));
		final int times = 12;
		Location[] locs = new Location[] { main.clone().add(Effects.getRel(dir, 0.31 * range, 1.0, 0.95 * range)),
				main.clone().add(Effects.getRel(dir, 0.81 * range, 1.0, 0.59 * range)),
				main.clone().add(Effects.getRel(dir, 1 * range, 1.0, 0)),
				main.clone().add(Effects.getRel(dir, 0.81 * range, 1.0, -0.59 * range)),
				main.clone().add(Effects.getRel(dir, 0.31 * range, 1.0, -0.95 * range)),
				main.clone().add(Effects.getRel(dir, -0.31 * range, 1.0, -0.95 * range)),
				main.clone().add(Effects.getRel(dir, -0.81 * range, 1.0, -0.59 * range)),
				main.clone().add(Effects.getRel(dir, -1 * range, 1.0, 0)),
				main.clone().add(Effects.getRel(dir, -0.81 * range, 1.0, 0.59 * range)),
				main.clone().add(Effects.getRel(dir, -0.31 * range, 1.0, 0.95 * range)) };
		List<Location> list = Arrays.asList(locs);
		Collections.shuffle(list);
		final int max = locs.length;
		Vector[] vecs = new Vector[max];
		for (int n = 0; n < max; n++)
			vecs[n] = locs[(max / 2 + n + 1 < max ? max / 2 + n + 1 : n + 1 - max / 2)].clone().subtract(locs[n])
					.toVector().multiply(1.0 / times);
		player.playSound(main, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
		for (int i = 0; i <= range * 10; i++) {
			double x = i / 10.0d;
			double z = Math.sqrt(range * range * 100 - i * i) / 10.0d;
			Effects.spawnRedStone(main.clone().add(x, 0, z), 255, 255, 255, 1.5f, 10, 0.1, 0.1, 0.1);
			Effects.spawnRedStone(main.clone().add(x, 0, -z), 255, 255, 255, 1.5f, 10, 0.1, 0.1, 0.1);
			Effects.spawnRedStone(main.clone().add(-x, 0, z), 255, 255, 255, 1.5f, 10, 0.1, 0.1, 0.1);
			Effects.spawnRedStone(main.clone().add(-x, 0, -z), 255, 255, 255, 1.5f, 10, 0.1, 0.1, 0.1);
		}
		boolean add = false;
		for (Entity et : main.getWorld().getNearbyEntities(main, range + 0.5d, 2, range + 0.5d))
			if (main.distance(et.getLocation()) < range + 0.5d && Skills.canAttack(player, et)) {
				damage(player, (LivingEntity) et, getFirstDamage(player));
				add = true;
			}
		speed.add(player.getUniqueId());
		if (add) {
			Jobs job = ConfigStore.getJob(player);
			if (job != null)
				HashMapStore.setIdentity(player, job.getMaxIdentity());
		}
		for (Location loc : locs)
			Effects.spawnRedStone(loc, 0, 0, 0, 1.5f, 40, 0.1, 0.2, 0.1);

		new BukkitRunnable() {
			private int time = 0;
			Location[] locs = list.toArray(Location[]::new);

			@Override
			public void run() {
				if (time < max) {
					for (int n = 0; n < times; n++) {
						Effects.spawnRedStone(locs[time].clone().add(vecs[time].clone().multiply(n)), 0, 0, 0, 1, 20,
								0.1, 0.1, 0.1);
						if (time > max / 3)
							Effects.spawnRedStone(
									locs[(time + 2) % max].clone().add(vecs[(time + 2) % max].clone().multiply(n)), 0,
									0, 0, 1, 20, 0.1, 0.1, 0.1);
						if (time > max / 3 * 2)
							Effects.spawnRedStone(
									locs[(time + 4) % max].clone().add(vecs[(time + 4) % max].clone().multiply(n)), 0,
									0, 0, 1, 20, 0.1, 0.1, 0.1);
					}
					for (int n = time; n < max; n++)
						Effects.spawnRedStone(locs[n], 0, 0, 0, 1.5f, 40, 0.1, 0.2, 0.1);
					for (int i = 0; i <= range * 10; i++) {
						double x = i / 10.0d;
						double z = Math.sqrt(range * range * 100 - i * i) / 10.0d;
						Effects.spawnRedStone(main.clone().add(x, 0, z), 255, 255, 255, 1.5f, 10, 0.1, 0.1, 0.1);
						Effects.spawnRedStone(main.clone().add(x, 0, -z), 255, 255, 255, 1.5f, 10, 0.1, 0.1, 0.1);
						Effects.spawnRedStone(main.clone().add(-x, 0, z), 255, 255, 255, 1.5f, 10, 0.1, 0.1, 0.1);
						Effects.spawnRedStone(main.clone().add(-x, 0, -z), 255, 255, 255, 1.5f, 10, 0.1, 0.1, 0.1);
					}
					double identity = HashMapStore.getIdentity(player);
					for (Entity et : main.getWorld().getNearbyEntities(main, range + 0.5d, 2, range + 0.5d))
						if (main.distance(et.getLocation()) < range + 0.5d && Skills.canAttack(player, et))
							damage(player, (LivingEntity) et, getShadowDamage(player));
					Skills.Persona.Force(player, 34);
					HashMapStore.setIdentity(player, identity);
					player.playSound(main, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
					this.time++;
				} else {
					for (int y = 1; y <= 6; y++)
						for (int i = 0; i <= range * 10; i++) {
							double x = i / 10.0d;
							double z = Math.sqrt(range * range * 100 - i * i) / 10.0d;
							Effects.spawnRedStone(main.clone().add(x, y * 0.5, z), 10, 0, 0, 1f, 10, 0.1, 0.1, 0.1);
							Effects.spawnRedStone(main.clone().add(x, y * 0.5, -z), 10, 0, 0, 1f, 10, 0.1, 0.1, 0.1);
							Effects.spawnRedStone(main.clone().add(-x, y * 0.5, z), 10, 0, 0, 1f, 10, 0.1, 0.1, 0.1);
							Effects.spawnRedStone(main.clone().add(-x, y * 0.5, -z), 10, 0, 0, 1f, 10, 0.1, 0.1, 0.1);
						}
					for (double x = -range; x <= range; x++)
						for (double z = -range; z <= range; z++)
							if (x * x + z * z < range * range) {
								Effects.spawnRedStone(main.clone().add(x, 0, z), 10, 0, 0, 1f, 10, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(main.clone().add(x, 0, -z), 10, 0, 0, 1f, 10, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(main.clone().add(-x, 0, z), 10, 0, 0, 1f, 10, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(main.clone().add(-x, 0, -z), 10, 0, 0, 1f, 10, 0.1, 0.1, 0.1);
							}
					speed.remove(player.getUniqueId());
					double identity = HashMapStore.getIdentity(player);
					for (Entity et : main.getWorld().getNearbyEntities(main, range + 0.5d, 2, range + 0.5d))
						if (main.distance(et.getLocation()) < range + 0.5d && Skills.canAttack(player, et))
							damage(player, (LivingEntity) et, getFinalDamage(player));
					HashMapStore.setIdentity(player, identity);
					player.playSound(main, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);

					Jobs job = ConfigStore.getJob(player);
					if (job != null)
						player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
					this.cancel();
				}
			}
		}.runTaskTimer(Core.getCore(), 10, 10);

		Skills.Persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
