package com.StarJ.LA.Skills.Battlemaster.Beginner;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
import com.StarJ.LA.Systems.Runnable.BuffRunnable;
import com.StarJ.LA.Systems.Runnable.ComboCoolRunnable;
import com.StarJ.LA.Systems.Runnable.SkillCoolRunnable;

public class SkyShatteringBlow extends Skills {

	public SkyShatteringBlow() {
		// 쿨타임 : 8d
		// 무력 : 20 / 2 = 10d
		super("sky_shattering_blow", "붕천퇴", 8d, 10d, ChatColor.GRAY, new AttackType[] { AttackType.BACK },
				ChatColor.YELLOW + "일반                     " + ChatColor.GRAY + "[일반 스킬]", "돌려차기로 적에게 피해를줍니다.",
				"- 2타 적중시 3초간 공격력 +27.6%");
	}

	public double getPower() {
		return 0.276d;
	}

	private double getFirstDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 277
		return 277 * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getSecondDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 383
		return 383 * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getDuration() {
		return 3;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		if (ComboCoolRunnable.hasCombo(player, this)) {
			Vector dir = player.getLocation().getDirection().clone().setY(0).normalize().multiply(0.75).setY(0.2);
			player.setVelocity(dir);
			new BukkitRunnable() {
				@Override
				public void run() {
					Location loc = player.getEyeLocation();
					for (int c = -4; c <= 4; c++)
						Effects.spawnRedStone(
								loc.clone().add(Effects.getRel(dir, 1 + -Math.abs(c) * 0.25, c * 0.25, c * 0.5)), 255,
								127, 0, 1f, 10, 0.1, 0.1, 0.1);
					boolean hit = false;
					for (Entity et : loc.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5))
						if (Skills.canAttack(player, et)) {
							damage(player, (LivingEntity) et, getSecondDamage(player));
							hit = true;
						}
					player.setVelocity(new Vector());
					if (hit)
						BuffRunnable.run(player, SkyShatteringBlow.this, getDuration(), slot);
					player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 3f, 1f);
				}
			}.runTaskLater(Core.getCore(), 5);
			comboEnd(player, slot);
		} else {
			Vector dir = player.getLocation().getDirection().clone().setY(0).normalize().multiply(0.75).setY(0.2);
			player.setVelocity(dir);
			if (player.getGameMode().equals(GameMode.CREATIVE))
				SkillCoolRunnable.run(player, this, slot, 7 / 20.0d);
			new BukkitRunnable() {
				@Override
				public void run() {
					Location loc = player.getEyeLocation();
					for (int c = -4; c <= 4; c++)
						Effects.spawnRedStone(
								loc.clone().add(
										Effects.getRel(dir, 1.25 + -Math.abs(c) * 0.25, -0.25 + c * 0.05, c * 0.5)),
								255, 127, 0, 1f, 10, 0.1, 0.1, 0.1);
					for (Entity et : loc.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5))
						if (Skills.canAttack(player, et))
							damage(player, (LivingEntity) et, getFirstDamage(player));
					player.setVelocity(new Vector());
					ComboCoolRunnable.run(player, SkyShatteringBlow.this, getComboDuration(), slot);
					player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 3f, 1f);
				}
			}.runTaskLater(Core.getCore(), 6);

		}
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
