package com.StarJ.LA.Skills.Battlemaster_Beginner;

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
import com.StarJ.LA.Systems.Stats;

public class FlashHeatFang extends Skills {

	public FlashHeatFang() {
		// 쿨타임 : 14d
		// 무력 : 54 / 9 = 6d
		super("flash_heat_fang", "섬열란아", 14.0d, 6d, ChatColor.GRAY, new AttackType[] { AttackType.BACK },
				ChatColor.YELLOW + "일반                  " + ChatColor.GRAY + "[일반 스킬]", "무수한 주먹을 날려 피해를 줍니다.",
				"- 치명타 적중률 +70%");
	}

	private double getFirstDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 1199 * 1.96 * 1.8 / 9 * 1.1 * 1.05 = 542d
		return 542d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getLastDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 798 * 1.96 * 1.8 * 1.5 * 1.1 * 1.05 = 4877
		return 4877d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		for (int c = 0; c < 9; c++) {
			int a = c;
			new BukkitRunnable() {
				@Override
				public void run() {
					Location loc = player.getEyeLocation();
					Vector dir = loc.getDirection().clone();
					double critical = Stats.Critical.getImportantStat(player);
					Stats.Critical.setImportantStat(player, 0.7 + critical);
					for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(dir), 2, 2, 2))
						if (Skills.canAttack(player, et))
							damage(player, (LivingEntity) et, getFirstDamage(player));
					Stats.Critical.setImportantStat(player, critical);
					player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_WEAK, 1f, 1f);
					for (int c = 0; c < 8; c++)
						Effects.spawnRedStone(
								loc.clone()
										.add(Effects.getRel(dir, c * 0.25,
												(a % 4 == 2 ? -1 + 0.25 * c : (a % 4 == 3 ? 1 - 0.25 * c : 0)),
												a % 2 == 0 ? -1 + c * 0.25 : 1 - c * 0.25)),
								127, 0, 127, 0.8f, 20, 0.1, 0.1, 0.1);

				}
			}.runTaskLater(Core.getCore(), c * 3);
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				Location loc = player.getEyeLocation();
				Vector dir = loc.getDirection().clone();
				double critical = Stats.Critical.getImportantStat(player);
				Stats.Critical.setImportantStat(player, 0.7 + critical);
				for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(dir), 2, 2, 2))
					if (Skills.canAttack(player, et))
						damage(player, (LivingEntity) et, getLastDamage(player));
				Stats.Critical.setImportantStat(player, critical);
				player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_STRONG, 2f, 1f);
				for (int c = 1; c < 8; c++)
					Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0, 0, 0)), 255, 0, 255, 1f, 50, 0.2, 0.2,
							0.2);

			}
		}.runTaskLater(Core.getCore(), 27);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
