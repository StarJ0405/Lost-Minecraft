package com.StarJ.LA.Skills.Reaper.LunarSound;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
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

public class ShadowStorm extends Skills {

	public ShadowStorm() {
		// 쿨타임 : 22d
		// 무력 : 41d
		super("shadow_storm", "쉐도우 스톰", 22d, 41d, ChatColor.DARK_PURPLE, new AttackType[] { AttackType.BACK },
				ChatColor.YELLOW + "일반                     " + ChatColor.DARK_PURPLE + "[그림자 스킬]",
				"전방으로 찌르면서 그림자를 소환합니다.", "- 그림자가 마지막에 폭발");
	}

	private double getFirstDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 613 * 1.7 * 1.1 = 1146d
		return 1146d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	public double getFirstIdentity() {
		return 100d;
	}

	public double getFirstMaxIdentity() {
		return 150d;
	}

	private double getShadowDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 1228 * 1.7 / 5 * 1.1 = 459d 
		return 459d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	public double getShadowIdentity() {
		return 30d;
	}

	public double getShadowMaxIdentity() {
		return 40d;
	}

	private double getFinalDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 1230 * 1.7 * 2.5 * 1.1 = 5750d 
		return 5750d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	public double getFinalIdentity() {
		return 500d;
	}

	public double getFinalMaxIdentity() {
		return 550d;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Location main = player.getEyeLocation().clone().subtract(0, 0.5, 0);
		double range = 2.5;
		double interval = 4;
		int tick = 2;
		Vector dir = main.getDirection().clone().setY(0).normalize();
		Location loc_front = main.clone().add(Effects.getRel(dir, 1 * range, 0, 0));
		Location loc_front_left = main.clone().add(Effects.getRel(dir, 0.31 * range, 0, -0.95 * range));
		Location loc_front_right = main.clone().add(Effects.getRel(dir, 0.31 * range, 0, 0.95 * range));
		Location loc_back_left = main.clone().add(Effects.getRel(dir, -0.81 * range, 0, -0.58 * range));
		Location loc_back_right = main.clone().add(Effects.getRel(dir, -0.81 * range, 0, 0.58 * range));

		Vector one = loc_back_left.clone().subtract(loc_front).toVector().multiply(1 / interval);
		Vector two = loc_front_right.clone().subtract(loc_back_left).toVector().multiply(1 / interval);
		Vector three = loc_front_left.clone().subtract(loc_front_right).toVector().multiply(1 / interval);
		Vector four = loc_back_right.clone().subtract(loc_front_left).toVector().multiply(1 / interval);
		Vector five = loc_front.clone().subtract(loc_back_right).toVector().multiply(1 / interval);

		Vector vel = main.getDirection().clone().setY(0).normalize().multiply(1.5d).setY(0.1d);
		player.setVelocity(vel);
		player.playSound(main, Sound.ENTITY_PLAYER_ATTACK_STRONG, 1f, 1f);
		double identity = HashMapStore.getIdentity(player);
		double add = 0;
		for (Entity et : player.getWorld().getNearbyEntities(main, 2.5, 3, 2.5))
			if (Skills.canAttack(player, et)) {
				damage(player, (LivingEntity) et, getFirstDamage(player));
				if (add == 0) {
					add = getFirstIdentity();
				} else
					add += 10;
			}

		if (add > getFirstMaxIdentity())
			add = getFirstMaxIdentity();
		HashMapStore.setIdentity(player, identity + add);
		for (int c = 0; c < 5; c++)
			new BukkitRunnable() {
				private int time = 0;
				private OfflinePlayer off = player;

				@Override
				public void run() {
					if (off.isOnline() && ConfigStore.getPlayerStatus(off.getPlayer())) {
						Player player = off.getPlayer();
						if (time <= interval) {
							Effects.spawnRedStone(loc_front.clone().add(one.clone().multiply(time)), 111, 0, 204, 1f,
									25, 0.1, 0.1, 0.1);
							Effects.spawnRedStone(loc_back_left.clone().add(two.clone().multiply(time)), 111, 0, 204,
									1f, 25, 0.1, 0.1, 0.1);
							Effects.spawnRedStone(loc_front_right.clone().add(three.clone().multiply(time)), 111, 0,
									204, 1f, 25, 0.1, 0.1, 0.1);
							Effects.spawnRedStone(loc_front_left.clone().add(four.clone().multiply(time)), 111, 0, 204,
									1f, 25, 0.1, 0.1, 0.1);
							Effects.spawnRedStone(loc_back_right.clone().add(five.clone().multiply(time)), 111, 0, 204,
									1f, 25, 0.1, 0.1, 0.1);
							if (time % 2 == 0)
								player.playSound(main, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
							this.time++;
						} else {
							double identity = HashMapStore.getIdentity(player);
							double add = 0;
							for (Entity et : player.getWorld().getNearbyEntities(main, 2.5, 3, 2.5))
								if (main.distance(et.getLocation()) < (range + 0.5d) && Skills.canAttack(player, et)) {
									damage(player, (LivingEntity) et, AttackType.getAttackType(et, main),
											getShadowDamage(player));
									if (add == 0) {
										add = getShadowIdentity();
									} else
										add += 10;
								}
							if (add > getShadowMaxIdentity())
								add = getShadowMaxIdentity();
							if (add > 0)
								player.playSound(main, Sound.ENTITY_PLAYER_HURT, 1f, 1f);
							HashMapStore.setIdentity(player, identity + add);
							this.cancel();
						}
					} else
						this.cancel();
				}
			}.runTaskTimer(Core.getCore(), (long) (tick * interval * c) + 2, tick);
		new BukkitRunnable() {
			private OfflinePlayer off = player;

			@Override
			public void run() {
				if (off.isOnline() && ConfigStore.getPlayerStatus(off.getPlayer())) {
					Player player = off.getPlayer();
					for (int c = 0; c < interval; c++) {
						Effects.spawnRedStone(loc_front.clone().add(one.clone().multiply(c)), 111, 0, 204, 1f, 25, 0.1,
								0.1, 0.1);
						Effects.spawnRedStone(loc_back_left.clone().add(two.clone().multiply(c)), 111, 0, 204, 1f, 25,
								0.1, 0.1, 0.1);
						Effects.spawnRedStone(loc_front_right.clone().add(three.clone().multiply(c)), 111, 0, 204, 1f,
								25, 0.1, 0.1, 0.1);
						Effects.spawnRedStone(loc_front_left.clone().add(four.clone().multiply(c)), 111, 0, 204, 1f, 25,
								0.1, 0.1, 0.1);
						Effects.spawnRedStone(loc_back_right.clone().add(five.clone().multiply(c)), 111, 0, 204, 1f, 25,
								0.1, 0.1, 0.1);
					}
					player.playSound(main, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
					double identity = HashMapStore.getIdentity(player);
					double add = 0;
					for (Entity et : player.getWorld().getNearbyEntities(main, 2.5, 3, 2.5))
						if (main.distance(et.getLocation()) < (range + 0.5d) && Skills.canAttack(player, et)) {
							damage(player, (LivingEntity) et, AttackType.getAttackType(et, main),
									getFinalDamage(player));
							if (add == 0) {
								add = getFinalIdentity();
							} else
								add += 10;
						}
					if (add > getFinalMaxIdentity())
						add = getFinalMaxIdentity();
					HashMapStore.setIdentity(player, identity + add);
					this.cancel();
				} else
					this.cancel();
			}
		}.runTaskLater(Core.getCore(), (long) (tick * interval * 5) + 10);
		Skills.Persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
