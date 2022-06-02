package com.StarJ.LA.Skills.Battlemaster.Beginner;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
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
import com.StarJ.LA.Systems.Jobs;

public class MoonFlashKick extends Skills {

	public MoonFlashKick() {
		// 쿨타임 : 22d
		// 무력 : 69 / 3 = 23d
		super("moon_flash_kick", "월섬각", 22d, 23d, ChatColor.GRAY, new AttackType[] { AttackType.BACK },
				ChatColor.YELLOW + "일반                            " + ChatColor.GRAY + "[일반 스킬]",
				"돌진 이후 즉시 돌면서 마무리 타격을 가한다.", "- 돌진 적중시 4초간 감전", "- 단일 타격시 추가피해");
	}

	private double getRushFirstDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 344 * 1.1 * 1.05 = 397
		return 397d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getRushSecondDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 266 * 1.1 * 1.05 = 307
		return 307d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getLightningDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 276 * 1.1 * 1.05 = 
		return 318d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getFinalDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 1068 * 4.41 * 1.1 * 1.05 = 
		return 5439d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getMulti() {
		return 1.8;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Location loc = player.getLocation();
		Vector dir = loc.getDirection().clone().setY(0).normalize().setY(0.2);
		player.setVelocity(dir);
		player.playSound(player, Sound.ENTITY_SLIME_JUMP, 3f, 1f);
		List<LivingEntity> list = new ArrayList<LivingEntity>();
		for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(dir), 2, 2, 2))
			if (Skills.canAttack(player, et))
				list.add((LivingEntity) et);
		if (list.size() == 1) {
			LivingEntity le = list.get(0);
			damage(player, le, getRushFirstDamage(player) * getMulti());
			new BukkitRunnable() {
				@Override
				public void run() {
					damage(player, le, getRushSecondDamage(player) * getMulti());
				}
			}.runTaskLater(Core.getCore(), 1);
			new BukkitRunnable() {
				private int time = 0;

				@Override
				public void run() {
					if (Skills.canAttack(player, le))
						if (time < 4) {
							player.playSound(le.getLocation(), Sound.ENTITY_GUARDIAN_ATTACK, 3f, 2f);
							damage(player, le, null, getLightningDamage(player) * getMulti(), 0, 0);
							for (int y = 0; y <= 6; y++)
								Effects.spawnRedStone(le.getEyeLocation().clone().add(0, y * 0.5, 0), 0, 255, 255, 1,
										10, 0.1, 0.1, 0.1);
							time++;
						} else
							this.cancel();
				}
			}.runTaskTimer(Core.getCore(), 10, 20);
		} else
			for (LivingEntity le : list) {
				damage(player, le, getRushFirstDamage(player));
				new BukkitRunnable() {
					@Override
					public void run() {
						damage(player, le, getRushSecondDamage(player));
					}
				}.runTaskLater(Core.getCore(), 1);
				new BukkitRunnable() {
					private int time = 0;

					@Override
					public void run() {
						if (Skills.canAttack(player, le))
							if (time < 4) {
								player.playSound(le.getLocation(), Sound.ENTITY_GUARDIAN_ATTACK, 3f, 2f);
								damage(player, le, null, getLightningDamage(player), 0, 0);
								for (int y = 0; y <= 6; y++)
									Effects.spawnRedStone(le.getEyeLocation().clone().add(0, y * 0.5, 0), 0, 255, 255,
											1, 10, 0.1, 0.1, 0.1);
								time++;
							} else
								this.cancel();
					}
				}.runTaskTimer(Core.getCore(), 10, 20);
			}
		new BukkitRunnable() {
			@Override
			public void run() {
				player.setVelocity(new Vector());
				Location loc = player.getLocation();
				double r = 1.5;
				for (int i = 0; i <= r * 10; i++) {
					double x = i / 10.0d;
					double z = Math.sqrt(r * r * 100 - i * i) / 10.0d;
					Effects.spawnRedStone(loc.clone().add(x, 0, z), 0, 127, 255, 1f, 1, 0, 0, 0);
					Effects.spawnRedStone(loc.clone().add(x, 0, -z), 0, 127, 255, 1f, 1, 0, 0, 0);
					Effects.spawnRedStone(loc.clone().add(-x, 0, z), 0, 127, 255, 1f, 1, 0, 0, 0);
					Effects.spawnRedStone(loc.clone().add(-x, 0, -z), 0, 127, 255, 1f, 1, 0, 0, 0);
				}
				player.playSound(player, Sound.ENTITY_MAGMA_CUBE_JUMP, 2f, 1f);
			}
		}.runTaskLater(Core.getCore(), 5);
		new BukkitRunnable() {
			@Override
			public void run() {
				player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 2f, 1f);
				Location loc = player.getLocation();
				for (int c = 0; c <= 16; c++)
					Effects.spawnRedStone(
							loc.clone().add(Effects.getRel(dir, -c * 0.15, Math.min(2.5 + c * 0.05, c * 0.3), 0)), 0,
							127, 255, 1f, 10, 0.1, 0.1, 0.1);
				player.setVelocity(dir.multiply(-0.5).setY(0.2));
				List<LivingEntity> list = new ArrayList<LivingEntity>();
				for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(dir), 2, 2, 2))
					if (Skills.canAttack(player, et))
						list.add((LivingEntity) et);
				if (list.size() == 1) {
					LivingEntity le = list.get(0);
					damage(player, le, getFinalDamage(player) * getMulti());
				} else
					for (LivingEntity le : list)
						damage(player, le, getFinalDamage(player));
			}
		}.runTaskLater(Core.getCore(), 9);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
