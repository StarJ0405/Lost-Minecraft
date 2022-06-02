package com.StarJ.LA.Skills.Battlemaster.Beginner;

import java.util.HashMap;
import java.util.UUID;

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

public class EnergyCombustion extends Skills {

	public EnergyCombustion() {
		// 쿨타임 : 36d
		// 무력 : 45d
		super("energy_combustion", "내공연소", 36d, 45d, ChatColor.GRAY,
				ChatColor.YELLOW + "일반                " + ChatColor.GRAY + "[일반 스킬]", "자신을 휘감는 돌풍을 발생시킨다.",
				"- 피해 감소 19.2%", "- 적중시 연소 피해 +9%씩", "- 최대 증가량 90%", "- 종료시 폭발");
	}

	private double getFirstDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 203 * 1.1 * 1.05 = 234d
		return 234d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getWindDamage(Player player, int stack) {
		Jobs job = ConfigStore.getJob(player);
		// 166 * 1.1 * 1.05 = 191d
		return 191d * (job != null ? job.getAttackDamagePercent(player) : 1) * Math.min(1.9d, (1 + stack * 0.09d));
	}

	private double getExplodeDamage(Player player, int stack) {
		Jobs job = ConfigStore.getJob(player);
		// (234 + 191 * 40) * 1.4 = 11023
		return 11023d * (job != null ? job.getAttackDamagePercent(player) : 1) * Math.min(1.9d, (1 + stack * 0.09d));
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Location loc = player.getEyeLocation();
		for (Entity et : loc.getWorld().getNearbyEntities(loc, 1.5d, 1.5d, 1.5d))
			if (Skills.canAttack(player, et))
				damage(player, (LivingEntity) et, getFirstDamage(player), 0, 0);
		new BukkitRunnable() {
			private int time = 0;
			private int max = 80;
			private HashMap<UUID, Integer> stacks = new HashMap<UUID, Integer>();

			@Override
			public void run() {
				Location loc = player.getEyeLocation();
				if (player.isOnline() && ConfigStore.getPlayerStatus(player)) {
					if (time < max) {
						Vector dir = loc.getDirection().clone().setY(0).normalize();
						if (time % 2 == 0) {
							for (Entity et : loc.getWorld().getNearbyEntities(loc, 1.5d, 1.5d, 1.5d))
								if (Skills.canAttack(player, et)) {
									UUID uuid = et.getUniqueId();
									int stack = stacks.containsKey(uuid) ? stacks.get(uuid) : 0;
									damage(player, (LivingEntity) et, getWindDamage(player, stack), 0, 0);
									stacks.put(uuid, stack + 1);
								}
							player.playSound(player, Sound.ENTITY_PHANTOM_BITE, 0.1f, 1f);
						}
						if (time % 10 < 5) {
							double x = time % 5 / 5d;
							double z = Math.sqrt(1 - x * x);
							Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, x, 0, z)), time * (255 / max),
									127, 255 - time * (255 / max), 1f, 10, 0.2, 0.1, 0.2);
							Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, -x, 0, -z)), time * (255 / max),
									127, 255 - time * (255 / max), 1f, 10, 0.2, 0.1, 0.2);
						} else {
							double x = time % 5 / 5d;
							double z = Math.sqrt(1 - x * x);
							Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, z, 0, -x)), time * (255 / max),
									127, 255 - time * (255 / max), 1f, 10, 0.2, 0.1, 0.2);
							Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, -z, 0, x)), time * (255 / max),
									127, 255 - time * (255 / max), 1f, 10, 0.2, 0.1, 0.2);
						}
						this.time++;
					} else {
						for (Entity et : loc.getWorld().getNearbyEntities(loc, 1.5d, 1.5d, 1.5d))
							if (Skills.canAttack(player, et))
								damage(player, (LivingEntity) et, getExplodeDamage(player,
										stacks.containsKey(et.getUniqueId()) ? stacks.get(et.getUniqueId()) : 0));
						Effects.spawnRedStone(loc.clone(), 255, 127, 0, 1f, 50, 0.5, 0.5, 0.5);
						player.playSound(player, Sound.ENTITY_GENERIC_EXPLODE, 0.5f, 1f);
						this.cancel();
					}
				} else
					this.cancel();
			}
		}.runTaskTimer(Core.getCore(), 5, 5);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
