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

public class LastGrapity extends Skills {

	public LastGrapity() {
		// 22
		super("last_grapity", "라스트 그래피티", 22.0d, ChatColor.RED, AttackType.BACK,
				ChatColor.YELLOW + "일반              " + ChatColor.RED + "[급습 스킬]", "뛰어올라 강하게 내려찍습니다.",
				" - 치명타 적중률 +40%");
	}

	private double getDamage(Player player, boolean persona) {
		Jobs job = ConfigStore.getJob(player);
		/// 666 * 1.6 * 1.8
		return 1918d * (job != null ? job.getAttackDamagePercent(player, true, persona) : 1);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Skills persona = Skills.Persona;
		Location loc = player.getLocation().clone();
		loc.add(0, 0.1, 0);
		int range = 30;
		int half = range / 2;
		for (int i = 0; i <= half; i++) {
			double x = i / 10.0d;
			double z = Math.sqrt(half * half - i * i) / 10.0d;
			Effects.spawnRedStone(loc.clone().add(x, 0, z), 255, 0, 0, 1, 20, 0.1, 0.1, 0.1);
			Effects.spawnRedStone(loc.clone().add(x, 0, -z), 255, 0, 0, 1, 20, 0.1, 0.1, 0.1);
			Effects.spawnRedStone(loc.clone().add(-x, 0, z), 255, 0, 0, 1, 20, 0.1, 0.1, 0.1);
			Effects.spawnRedStone(loc.clone().add(-x, 0, -z), 255, 0, 0, 1, 20, 0.1, 0.1, 0.1);
		}
		Vector dir = loc.getDirection().clone().setY(0).normalize().setY(0.75d);
		player.playSound(player, Sound.ENTITY_WITHER_SHOOT, 0.5f, 1f);
		player.setVelocity(dir);
		new BukkitRunnable() {
			private final int max = 20 * 10;
			private int time = 0;
			private OfflinePlayer off = player;
			private boolean per = BuffRunnable.has(player, persona);

			@Override
			public void run() {
				if (off.isOnline()) {
					Player player = off.getPlayer();
					if (ConfigStore.getPlayerStatus(player)) {
						if ((this.time > 5 && ((Entity) player).isOnGround()) || this.time > this.max) {
							int range = 60;
							int half = range / 2;
							Location loc = player.getLocation().clone();
							loc.add(0, 0.1, 0);
							for (int i = 0; i <= half; i++) {
								double x = i / 10.0d;
								double z = Math.sqrt(half * half - i * i) / 10.0d;
								Effects.spawnRedStone(loc.clone().add(x, 0, z), 111, 0, 0, 1, 20, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(loc.clone().add(x, 0, -z), 111, 0, 0, 1, 20, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(loc.clone().add(-x, 0, z), 111, 0, 0, 1, 20, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(loc.clone().add(-x, 0, -z), 111, 0, 0, 1, 20, 0.1, 0.1, 0.1);
							}
							double l = half * 0.5 / 10.0d;
							for (int y = 1; y <= 7; y++) {
								Effects.spawnRedStone(loc.clone().add(l, y * 0.5, l), 111, 0, 0, 1, 5, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(loc.clone().add(l, y * 0.5, -l), 111, 0, 0, 1, 5, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(loc.clone().add(-l, y * 0.5, l), 111, 0, 0, 1, 5, 0.1, 0.1, 0.1);
								Effects.spawnRedStone(loc.clone().add(-l, y * 0.5, -l), 111, 0, 0, 1, 5, 0.1, 0.1, 0.1);
							}
							double identity = HashMapStore.getIdentity(player);
							Stats.Critical.setImportantStat(player, 0.4);
							for (Entity et : loc.getWorld().getNearbyEntities(loc, 3, 3, 3))
								if (loc.distance(et.getLocation()) < (half / 10.0d + 0.5d)
										&& Skills.canAttack(player, et))
									if (AttackType.getAttackType(et, player).equals(getAttackType())) {
										Stats.Critical.setImportantStat(player, 0.4 + 0.1);
										damage(player, (LivingEntity) et, getDamage(player, per) * 1.05);
										Stats.Critical.setImportantStat(player, 0.4);
									} else
										damage(player, (LivingEntity) et, getDamage(player, per));

							player.playSound(player, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1f, 1f);
							Stats.Critical.removeImportantStat(player);
							HashMapStore.setIdentity(player, identity);
							this.cancel();
						} else
							Effects.spawnRedStone(player.getLocation(), 111, 0, 0, 1, 20, 0.1, 0.1, 0.1);
						this.time++;
					} else
						this.cancel();
				} else
					this.cancel();
			}

		}.runTaskTimer(Core.getCore(), 2, 2);
		persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
