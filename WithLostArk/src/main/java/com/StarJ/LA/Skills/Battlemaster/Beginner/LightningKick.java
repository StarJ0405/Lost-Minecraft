package com.StarJ.LA.Skills.Battlemaster.Beginner;

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
import com.StarJ.LA.Systems.Jobs;

public class LightningKick extends Skills {

	public LightningKick() {
		// 쿨타임 : 9d
		// 무력 : 20d
		super("lightning_kick", "뇌명각", 9d, 20d, ChatColor.GRAY, new AttackType[] { AttackType.BACK },
				ChatColor.YELLOW + "일반                " + ChatColor.GRAY + "[일반 스킬]", "강력하게 내려찍어 피해를 줍니다.",
				" - 내려찍기 적중시 전기 피해");
	}

	private double getDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 182 * 3 + 825 = 1371 
		// 1371 * 1.4 * 1.1 * 1.05 = 2216d 
		return 2216d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getLightningDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 164 * 1.1 * 1.05 = 189 
		return 189d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Vector dir = player.getLocation().getDirection().clone().setY(0).normalize().setY(0.4);
		player.setVelocity(dir);
		new BukkitRunnable() {
			private OfflinePlayer off = player;
			private int time = 0;

			@Override
			public void run() {
				if (off.isOnline()) {
					Player player = off.getPlayer();
					Location loc = player.getLocation();
					if (ConfigStore.getPlayerStatus(player)) {
						if (this.time > 5 && ((Entity) player).isOnGround()) {
							double half = 20;
							for (int i = 0; i <= half; i++) {
								double x = i / 10.0d;
								double z = Math.sqrt(half * half - i * i) / 10.0d;
								Effects.spawnRedStone(loc.clone().add(x, 0.15, z), 0, 127, 127, 1, 20, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(loc.clone().add(x, 0.15, -z), 0, 127, 127, 1, 20, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(loc.clone().add(-x, 0.15, z), 0, 127, 127, 1, 20, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(loc.clone().add(-x, 0.15, -z), 0, 127, 127, 1, 20, 0.1, 0.1, 0.1);
							}
							for (Entity et : loc.getWorld().getNearbyEntities(loc, 2, 2, 2))
								if (Skills.canAttack(player, et)) {
									damage(player, (LivingEntity) et, getDamage(player), 0, 0);
									new BukkitRunnable() {
										private int time = 0;

										@Override
										public void run() {
											if (this.time < 4) {
												if (Skills.canAttack(player, et)) {
													LivingEntity le = (LivingEntity) et;
													damage(player, le, null, getLightningDamage(player));
													player.playSound(le.getLocation(), Sound.ENTITY_GUARDIAN_ATTACK, 3f,
															2f);
													for (int y = 0; y <= 6; y++)
														Effects.spawnRedStone(
																le.getEyeLocation().clone().add(0, y * 0.5, 0), 0, 255,
																255, 1, 10, 0.1, 0.1, 0.1);
												}
												this.time++;
											} else
												this.cancel();
										}
									}.runTaskTimer(Core.getCore(), 10, 20);
								}
							this.cancel();
						} else
							Effects.spawnRedStone(loc, 0, 127, 127, 1, 20, 0.1, 0.1, 0.1);
						this.time++;
					} else
						this.cancel();
				} else
					this.cancel();
			}
		}.runTaskTimer(Core.getCore(), 2, 2);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
