package com.StarJ.LA.Skills.Reaper_Lunarsound;

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
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Runnable.ComboCoolRunnable;
import com.StarJ.LA.Systems.Runnable.SkillCoolRunnable;

public class ShadowDot extends Skills {

	public ShadowDot() {
		// 쿨타임 : (7 - 3) * 0.8 = 3.2d
		// 무력 : 22 / 2 = 11d
		super("shadow_dot", "쉐도우 닷", 4d, 11d, ChatColor.GREEN, new AttackType[] { AttackType.BACK },
				ChatColor.AQUA + "콤보                     " + ChatColor.GREEN + "[단검 스킬]", "적에게 피해를 줍니다.",
				"- 사용시 급습스킬 쿨타임 감소 1.5초");

	}

	private double getFirstDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 196
		return 196d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getIdentity() {
		return 125;
	}

	private double getMaxIdentity() {
		// 125 * 1.4
		return 175;
	}

	private double getSecondDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 394
		return 394d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	public double getReduceCool() {
		return 1.5d;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Location loc = player.getEyeLocation().clone();
		Vector dir = loc.getDirection();
		Vector vel = dir.clone().setY(0).normalize().multiply(0.15).setY(0.1);
		double identity = HashMapStore.getIdentity(player);
		double add = 0.0d;
		if (ComboCoolRunnable.hasCombo(player, this)) {
			for (Entity et : loc.getWorld().getNearbyEntities(loc.add(dir), 1.5, 1.5, 1.5)) {
				if (Skills.canAttack(player, et)) {
					LivingEntity le = (LivingEntity) et;
					damage(player, le, getSecondDamage(player));
					player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 2f, 1f);
					new BukkitRunnable() {
						@Override
						public void run() {
							if (Skills.canAttack(player, le)) {
								damage(player, le, getSecondDamage(player));
								player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 2f, 1f);
							}
						}
					}.runTaskLater(Core.getCore(), 2);
					if (add == 0.0d) {
						add += getIdentity();
					} else
						add += getIdentity() * 0.5;
				}
			}
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0.8, 0, 0)), 0, 128, 0, 1f, 10, 0.1, 0.1, 0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0.4, -0.1, -0.2)), 0, 128, 0, 1f, 10, 0.1, 0.1,
					0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0.4, 0.1, 0.2)), 0, 128, 0, 1f, 10, 0.1, 0.1,
					0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0.2, -0.2, -0.4)), 0, 128, 0, 1f, 10, 0.1, 0.1,
					0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0.2, 0.2, 0.4)), 0, 128, 0, 1f, 10, 0.1, 0.1,
					0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0, -0.3, -0.8)), 0, 128, 0, 1f, 10, 0.1, 0.1,
					0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0, 0.3, 0.8)), 0, 128, 0, 1f, 10, 0.1, 0.1, 0.1);
			comboEnd(player, slot);
		} else {
			for (Entity et : loc.getWorld().getNearbyEntities(loc.add(dir), 1, 1, 1)) {
				if (Skills.canAttack(player, et)) {
					LivingEntity le = (LivingEntity) et;
					damage(player, le, getFirstDamage(player));
					if (add == 0.0d) {
						add += getIdentity();
					} else
						add += getIdentity() * 0.5;
				}
			}
			if (add > 0) {
				Jobs job = ConfigStore.getJob(player);
				if (job != null) {
					Skills[] skills = new Skills[] { Skills.RageSpear, Skills.LastGrapity, Skills.DancingofFury };
					for (Skills skill : skills) {
						double cool = ConfigStore.getSkillCooldown(player, ConfigStore.getJob(player), skill);
						if (cool > 0)
							SkillCoolRunnable.run(player, skill, job.getSkillSlot(player, skill),
									cool - getReduceCool());
					}
				}
			}
			player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
			ComboCoolRunnable.run(player, this, getComboDuration(), slot);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0.8, 0, 0)), 0, 128, 0, 1f, 10, 0.1, 0.1, 0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0.4, 0.1, -0.2)), 0, 128, 0, 1f, 10, 0.1, 0.1,
					0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0.4, -0.1, 0.2)), 0, 128, 0, 1f, 10, 0.1, 0.1,
					0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0.2, 0.2, -0.4)), 0, 128, 0, 1f, 10, 0.1, 0.1,
					0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0.2, -0.2, 0.4)), 0, 128, 0, 1f, 10, 0.1, 0.1,
					0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0, 0.3, -0.8)), 0, 128, 0, 1f, 10, 0.1, 0.1, 0.1);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, 0, -0.3, 0.8)), 0, 128, 0, 1f, 10, 0.1, 0.1, 0.1);
		}
		player.setVelocity(vel);
		dir.setY(0.0d);

		if (add > getMaxIdentity())
			add = getMaxIdentity();
		HashMapStore.setIdentity(player, identity + add);
		Skills.Persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
