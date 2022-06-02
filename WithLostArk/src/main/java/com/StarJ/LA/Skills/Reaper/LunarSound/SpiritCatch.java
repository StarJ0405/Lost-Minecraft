package com.StarJ.LA.Skills.Reaper.LunarSound;

import java.util.ArrayList;
import java.util.List;
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
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;

public class SpiritCatch extends Skills {

	public SpiritCatch() {
		// 쿨타임 : 5d
		// 무력 : 30 / 2 = 15d
		super("spirit_catch", "스피릿 캐치", 5d, 15d, ChatColor.GREEN, new AttackType[] { AttackType.BACK },
				ChatColor.YELLOW + "일반             " + ChatColor.GREEN + "[단검 스킬]", "전방을 향해 빠르게 찌릅니다.");
	}

	private double getFirstDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 813
		return 813d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getSecondDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 813 * 1
		return 813 * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getIdentity() {
		return 125;
	}

	private double getMaxIdentity() {
		// 125 * 1.4
		return 175;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Location loc = player.getEyeLocation();
		Vector dir = loc.getDirection();
		player.setVelocity(dir.clone().setY(0).normalize().multiply(0.4).setY(0.125));
		new BukkitRunnable() {
			@Override
			public void run() {
				List<UUID> uuids = new ArrayList<UUID>();
				uuids.add(player.getUniqueId());
				double add = 0.0d;
				double identity = HashMapStore.getIdentity(player);
				for (int c = 0; c < 8; c++) {
					Location now = player.getEyeLocation().clone().add(dir.clone().multiply(0.25 * (c + 1)));
					now.setY(now.getY() - 0.5);
					Effects.spawnRedStone(now, 0, 128, 0, 0.8f, 20, 0.1, 0.08, 0.1);
					for (Entity et : loc.getWorld().getNearbyEntities(now, 0.25, 0.25, 0.25))
						if (Skills.canAttack(player, et))
							if (!uuids.contains(et.getUniqueId())) {
								damage(player, (LivingEntity) et, getFirstDamage(player));
								uuids.add(et.getUniqueId());
								if (add == 0.0d) {
									add += getIdentity();
								} else
									add += getIdentity() / 10;
							}
				}
				if (add > getMaxIdentity())
					add = getMaxIdentity();
				player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_STRONG, 1f, 1f);
				HashMapStore.setIdentity(player, identity + add);
			}
		}.runTaskLater(Core.getCore(), 5);
		new BukkitRunnable() {
			@Override
			public void run() {
				List<UUID> uuids = new ArrayList<UUID>();
				uuids.add(player.getUniqueId());
				double add = 0.0d;
				double identity = HashMapStore.getIdentity(player);
				for (int c = 1; c < 10; c++) {
					Location now = player.getEyeLocation().clone().add(dir.clone().multiply(0.25 * (c + 1)));
					now.setY(now.getY() - 0.5);
					Effects.spawnRedStone(now, 0, 0, 0, 1.2f, 20, 0.1, 0.12, 0.1);
					for (Entity et : loc.getWorld().getNearbyEntities(now, 0.25, 0.25, 0.25))
						if (Skills.canAttack(player, et))
							if (!uuids.contains(et.getUniqueId())) {
								damage(player, (LivingEntity) et, getSecondDamage(player));
								uuids.add(et.getUniqueId());
								if (add == 0.0d) {
									add += getIdentity();
								} else
									add += getIdentity() / 10;
							}
				}
				if (add > getMaxIdentity())
					add = getMaxIdentity();
				player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_STRONG, 1f, 1f);
				HashMapStore.setIdentity(player, identity + add);
			}
		}.runTaskLater(Core.getCore(), 8);
		player.playSound(player, Sound.ENTITY_SLIME_JUMP, 5f, 1f);
		Skills.Persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
