package com.StarJ.LA.Skills.Reaper.LunarSound;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
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
import com.StarJ.LA.Skills.ProjectileRunnable;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Runnable.ComboCoolRunnable;

public class Nightmare extends Skills {
	private static HashMap<UUID, UUID> target = new HashMap<UUID, UUID>();

	public Nightmare() {
		// 쿨타임 : (12 - 5) * 0.8 = 5.6d
		// 무력 : 28 / 2 = 14d
		super("nightmare", "나이트메어", 7.0d, 14d, ChatColor.GREEN, new AttackType[] { AttackType.BACK },
				ChatColor.BLUE + "체인                 " + ChatColor.GREEN + "[단검 스킬]", "정면으로 단검을 던집니다.",
				"재입력시 맞은 대상 뒤로 이동", " - 이동시 적에게 피해", " - 이동전까지 이동속도가 +10%");
	}

	private double getHitDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 1452
		return 1452 * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getTelportDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 1452 * 0.6 = 871
		return 871d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getHitIdentity() {
		return 175;
	}

	private double getTeleportIdentity() {
		return 75;
	}

	public static float getSpeed(Player player) {
		return target.containsKey(player.getUniqueId()) ? 0.1f : 0f;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		if (ComboCoolRunnable.hasCombo(player, this) && target.containsKey(player.getUniqueId())) {
			Entity et = Bukkit.getEntity(target.get(player.getUniqueId()));
			if (Skills.canAttack(player, et)) {
				LivingEntity le = (LivingEntity) et;
				Location loc = le.getLocation().clone();
				loc.setPitch(0f);
				Vector dir = loc.getDirection().multiply(0.5).setY(0);
				player.teleport(loc.subtract(dir));
				new BukkitRunnable() {
					@Override
					public void run() {
						damage(player, le, getTelportDamage(player), getTeleportIdentity());
					}
				}.runTaskLater(Core.getCore(), 1);
				player.playSound(le.getLocation(), Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 2f, 1f);
			}
			comboEnd(player, slot);
		} else {
			Location loc = player.getLocation();
			Vector dir = loc.getDirection().multiply(2);
			Effects.Infos[] infos = new Effects.Infos[1];
			infos[0] = new Effects.RedStoneInfos(Effects.getRel(dir, 0, 0, 0), 0, 255, 0, 1, 2, 0.1, 0.1, 0.1);
			new ProjectileRunnable(player, this, slot, player.getEyeLocation(), dir, 10, Integer.MAX_VALUE, infos)
					.runTaskTimer(Core.getCore(), 0, 1);
			player.playSound(player, Sound.ENTITY_ARROW_SHOOT, 1f, 1f);
		}
		Skills.Persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public void End(Player player, int slot) {
		super.End(player, slot);
		target.remove(player.getUniqueId());
	}

	@Override
	public boolean Hit(Player att, LivingEntity le, int slot) {
		damage(att, le, getHitDamage(att), getHitIdentity());
		target.put(att.getUniqueId(), le.getUniqueId());
		ComboCoolRunnable.run(att, this, getComboDuration(), slot);
		att.playSound(att, Sound.ENTITY_ARROW_HIT, 2f, 1f);
		Effects.spawnRedStone(att, le.getEyeLocation(), 50, 255, 50, 1, 25, 0.5, 0.5, 0.5);
		Jobs job = ConfigStore.getJob(att);
		if (job != null)
			att.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(att));
		return true;
	}

	@Override
	public void comboEnd(Player player, int slot) {
		super.comboEnd(player, slot);
		target.remove(player.getUniqueId());
		Jobs job = ConfigStore.getJob(player);
		if (job != null)
			player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
