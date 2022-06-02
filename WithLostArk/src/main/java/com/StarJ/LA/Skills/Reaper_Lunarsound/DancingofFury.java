package com.StarJ.LA.Skills.Reaper_Lunarsound;

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
import com.StarJ.LA.Systems.Stats;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;

public class DancingofFury extends Skills {

	public DancingofFury() {
		// 쿨타임 : 28d
		// 무력 : 42 / 7 = 6d
		super("dancing_of_fury", "댄싱 오브 퓨리", 28d, 6d, ChatColor.RED, new AttackType[] { AttackType.BACK },
				ChatColor.YELLOW + "일반                    " + ChatColor.RED + "[급습 스킬]", "붉은 그림자 기운으로 피해를 줍니다.",
				" - 치명타 적중률 +60%");
	}

	private double getFirstDamage(Player player, boolean persona) {
		Jobs job = ConfigStore.getJob(player);
		// 1163 * 1.7 * 1.31 / 6 * 1.1 * 1.05 = 498d
		return 498d * (job != null ? job.getAttackDamagePercent(player, true, persona) : 1);
	}

	private double getLastDamage(Player player, boolean persona) {
		Jobs job = ConfigStore.getJob(player);
		// 1759 * 1.7 * 1.31 *1.1 * 1.05 = 4524d
		return 4524d * (job != null ? job.getAttackDamagePercent(player, true, persona) : 1);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Skills persona = Skills.Persona;

		final int num = 5;
		final int interval = 4;
		boolean per = BuffRunnable.has(player, persona);
		Location loc = player.getEyeLocation().clone().subtract(0, 0.75, 0);
		Vector dir = loc.getDirection().clone().setY(0).normalize();
		for (int i = 0; i < num; i++) {
			Location now = loc.clone().add(Effects.getRel(dir, 1.5 + 0.1 * i, 0.25 * i, -num / 2 * 0.5 + 0.5 * i));
			Effects.spawnRedStone(now, 255, 0, 0, 1, 10, 0.1, 0.1, 0.1);
		}
		double identity = HashMapStore.getIdentity(player);
		Stats.Critical.setImportantStat(player, 0.6);
		for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(Effects.getRel(dir, 1, 0, 0)), 2.5, 2, 2.5))
			if (Skills.canAttack(player, et)) {
				damage(player, (LivingEntity) et, getFirstDamage(player, per));
			}
		HashMapStore.setIdentity(player, identity);
		Stats.Critical.removeImportantStat(player);
		player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1f, 1f);
		new BukkitRunnable() {
			OfflinePlayer off = player;

			@Override
			public void run() {
				if (off.isOnline()) {
					Player player = off.getPlayer();
					if (ConfigStore.getPlayerStatus(player)) {
						Location loc = player.getEyeLocation().clone().subtract(0, 0.75, 0);
						Vector dir = loc.getDirection().clone().setY(0).normalize();
						for (int i = 0; i < num; i++) {
							Location now = loc.clone()
									.add(Effects.getRel(dir, 2.0 - 0.4 * i, 1.75 - 0.25 * i, 1.0 - 0.1 * i));
							Effects.spawnRedStone(now, 255, 0, 0, 1, 10, 0.1, 0.1, 0.1);
						}
						double identity = HashMapStore.getIdentity(player);
						Stats.Critical.setImportantStat(player, 0.6);
						for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(Effects.getRel(dir, 1, 0, 0)),
								2.5, 2, 2.5))
							if (Skills.canAttack(player, et))
								damage(player, (LivingEntity) et, getFirstDamage(player, per));
						HashMapStore.setIdentity(player, identity);
						Stats.Critical.removeImportantStat(player);
						player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1f, 1f);
					} else
						this.cancel();
				} else
					this.cancel();
			}
		}.runTaskLater(Core.getCore(), interval);
		new BukkitRunnable() {
			OfflinePlayer off = player;

			@Override
			public void run() {
				if (off.isOnline()) {
					Player player = off.getPlayer();
					if (ConfigStore.getPlayerStatus(player)) {
						Location loc = player.getEyeLocation().clone().subtract(0, 0.75, 0);
						Vector dir = loc.getDirection().clone().setY(0).normalize();
						for (int i = 0; i < num; i++) {
							Location now = loc.clone()
									.add(Effects.getRel(dir, 0 + 0.5 * i, 0.5 + 0.125 * i, 0.5 - 0.5 * i));
							Effects.spawnRedStone(now, 255, 0, 0, 1, 10, 0.1, 0.1, 0.1);
						}
						double identity = HashMapStore.getIdentity(player);
						Stats.Critical.setImportantStat(player, 0.6);
						for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(Effects.getRel(dir, 1, 0, 0)),
								2.5, 2, 2.5))
							if (Skills.canAttack(player, et))
								damage(player, (LivingEntity) et, getFirstDamage(player, per));
						HashMapStore.setIdentity(player, identity);
						Stats.Critical.removeImportantStat(player);
						player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1f, 1f);
					} else
						this.cancel();
				} else
					this.cancel();
			}
		}.runTaskLater(Core.getCore(), interval * 2);
		new BukkitRunnable() {
			OfflinePlayer off = player;

			@Override
			public void run() {
				if (off.isOnline()) {
					Player player = off.getPlayer();
					if (ConfigStore.getPlayerStatus(player)) {
						Location loc = player.getEyeLocation().clone().subtract(0, 0.75, 0);
						Vector dir = loc.getDirection().clone().setY(0).normalize();
						for (int i = 0; i < num; i++) {
							Location now = loc.clone().add(Effects.getRel(dir, 2.0, 1.0 + 0.125 * i, -1.5 + 0.75 * i));
							Effects.spawnRedStone(now, 255, 0, 0, 1, 10, 0.1, 0.1, 0.1);
						}
						double identity = HashMapStore.getIdentity(player);
						Stats.Critical.setImportantStat(player, 0.6);
						for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(Effects.getRel(dir, 1, 0, 0)),
								2.5, 2, 2.5))
							if (Skills.canAttack(player, et))
								damage(player, (LivingEntity) et, getFirstDamage(player, per));
						HashMapStore.setIdentity(player, identity);
						Stats.Critical.removeImportantStat(player);
						player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1f, 1f);
					} else
						this.cancel();
				} else
					this.cancel();
			}
		}.runTaskLater(Core.getCore(), interval * 3);
		new BukkitRunnable() {
			OfflinePlayer off = player;

			@Override
			public void run() {
				if (off.isOnline()) {
					Player player = off.getPlayer();
					if (ConfigStore.getPlayerStatus(player)) {
						Location loc = player.getEyeLocation().clone().subtract(0, 0.75, 0);
						Vector dir = loc.getDirection().clone().setY(0).normalize();
						for (int i = 0; i < num; i++) {
							Location now = loc.clone()
									.add(Effects.getRel(dir, 2.0 - 0.4 * i, 1.5 - 0.3 * i, 1.5 - 0.5 * i));
							Effects.spawnRedStone(now, 255, 0, 0, 1, 10, 0.1, 0.1, 0.1);
						}
						double identity = HashMapStore.getIdentity(player);
						Stats.Critical.setImportantStat(player, 0.6);
						for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(Effects.getRel(dir, 1, 0, 0)),
								2.5, 2, 2.5))
							if (Skills.canAttack(player, et))
								damage(player, (LivingEntity) et, getFirstDamage(player, per));
						HashMapStore.setIdentity(player, identity);
						Stats.Critical.removeImportantStat(player);
						player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1f, 1f);
					} else
						this.cancel();
				} else
					this.cancel();
			}
		}.runTaskLater(Core.getCore(), interval * 4);
		new BukkitRunnable() {
			OfflinePlayer off = player;

			@Override
			public void run() {
				if (off.isOnline()) {
					Player player = off.getPlayer();
					if (ConfigStore.getPlayerStatus(player)) {
						Location loc = player.getEyeLocation().clone().subtract(0, 0.75, 0);
						Vector dir = loc.getDirection().clone().setY(0).normalize();
						for (int i = 0; i < num; i++) {
							Location now = loc.clone()
									.add(Effects.getRel(dir, 0.4 + 0.4 * i, 0.3 + 0.3 * i, -0.5 + 0.25 * i));
							Effects.spawnRedStone(now, 255, 0, 0, 1, 10, 0.1, 0.1, 0.1);
						}
						double identity = HashMapStore.getIdentity(player);
						Stats.Critical.setImportantStat(player, 0.6);
						for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(Effects.getRel(dir, 1, 0, 0)),
								2.5, 2, 2.5))
							if (Skills.canAttack(player, et))
								damage(player, (LivingEntity) et, getFirstDamage(player, per));
						HashMapStore.setIdentity(player, identity);
						Stats.Critical.removeImportantStat(player);
						player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1f, 1f);
					} else
						this.cancel();
				} else
					this.cancel();
			}
		}.runTaskLater(Core.getCore(), interval * 5);
		new BukkitRunnable() {
			OfflinePlayer off = player;

			@Override
			public void run() {
				if (off.isOnline()) {
					Player player = off.getPlayer();
					if (ConfigStore.getPlayerStatus(player)) {
						Location loc = player.getEyeLocation().clone().subtract(0, 0.75, 0);
						Vector dir = loc.getDirection().clone().setY(0).normalize();
						for (int i = 0; i < num; i++) {
							Effects.Directional.SWEEP_ATTACK.spawnDirectional(
									loc.clone().add(Effects.getRel(dir, 0.5 * i, 0.2 * i, 1.5d + 0.3 * i)), 1, 0, 0, 0,
									1);
							Effects.Directional.SWEEP_ATTACK.spawnDirectional(
									loc.clone().add(Effects.getRel(dir, 0.5 * i, 0.2 * i, -1.5d + 0.3 * i)), 1, 0, 0, 0,
									1);
						}
						double identity = HashMapStore.getIdentity(player);
						Stats.Critical.setImportantStat(player, 0.6);
						for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(Effects.getRel(dir, 1, 0, 0)),
								2.5, 2, 2.5))
							if (Skills.canAttack(player, et))
								damage(player, (LivingEntity) et, getLastDamage(player, per));
						HashMapStore.setIdentity(player, identity);
						Stats.Critical.removeImportantStat(player);
						player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
					} else
						this.cancel();
				} else
					this.cancel();
			}
		}.runTaskLater(Core.getCore(), interval * 7);
		persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
