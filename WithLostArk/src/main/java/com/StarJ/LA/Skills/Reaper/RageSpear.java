package com.StarJ.LA.Skills.Reaper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
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

public class RageSpear extends Skills {

	public RageSpear() {
		super("rage_spear", "레이지 스피어", 5.0d, ChatColor.RED);
	}

	public double getDrainDamage(Player player, boolean persona) {
		Jobs job = ConfigStore.getJob(player);
		return 2.0d * (job != null ? job.getAttackDamagePercent(player) : 1)
				* (persona ? 2 * Stats.Specialization.getStatPercent(player) : 1);
	}

	public double getAttackDamage(Player player, boolean persona) {
		Jobs job = ConfigStore.getJob(player);
		return 32.0d * (job != null ? job.getAttackDamagePercent(player) : 1)
				* (persona ? 2 * Stats.Specialization.getStatPercent(player) : 1);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Skills persona = Skills.Persona;
		Location loc = player.getLocation();
		Location eyeloc = player.getEyeLocation();
		Vector dir = loc.getDirection();

		new RageDrain(eyeloc, dir, 8, player, BuffRunnable.has(player, persona)).runTaskTimer(Core.getCore(), 0, 3);

		persona.BuffEnd(player, -1);
		return false;
	}

	private class RageDrain extends BukkitRunnable {
		private final Location start;
		private final Vector dir;
		private final Location end;
		private final int range;
		private final OfflinePlayer off;
		private final boolean persona;
		private Location move;
		private int time;

		public RageDrain(Location start, Vector dir, int range, OfflinePlayer off, boolean persona) {
			this.start = start;
			this.dir = dir;
			this.end = start.clone().add(dir.clone().multiply(range));
			this.range = range;
			this.off = off;
			this.persona = persona;
			this.move = end.clone();
			this.time = range;
			HashMapStore.setSkillStop(off.getUniqueId().toString(), true);
		}

		@Override
		public void run() {
			if (off.isOnline() && ConfigStore.getPlayerStatus(off.getPlayer())) {
				if (this.time > 0) {
					Player player = off.getPlayer();
					Location now = start.clone();
					List<UUID> list = new ArrayList<UUID>();
					list.add(off.getUniqueId());
					for (int c = 0; c <= range; c++) {
						for (Entity et : now.getWorld().getNearbyEntities(now, 1, 1, 1)) {
							if (!list.contains(et.getUniqueId()) && et instanceof LivingEntity
									&& (!(et instanceof Player) || (ConfigStore.getPlayerStatus((Player) et)
											&& ConfigStore.getPVP(player) && ConfigStore.getPVP((Player) et)))) {
								LivingEntity le = (LivingEntity) et;
								le.setNoDamageTicks(0);
								le.damage(getDrainDamage(player, persona), player);
								le.setVelocity(dir.clone().multiply(-0.1));
								addIdentity(player, 0);
								list.add(et.getUniqueId());
							}
						}
						Effects.spawnRedStone(now, 255, 0, 0, 1, 20, 1.2, 1.2, 1.2);
						now = now.add(dir);

					}
					Effects.spawnRedStone(move, 0, 0, 0, 3, 10, 0.3, 0.3, 0.3);
					move = move.subtract(dir);
					this.time--;
				} else {
					new RageAttack(start, dir, range, off, persona).runTaskLater(Core.getCore(), 3);
					this.cancel();
				}
			} else {
				HashMapStore.setSkillStop(off.getUniqueId().toString(), false);
				this.cancel();
			}

		}
	}

	public class RageAttack extends BukkitRunnable {
		private final Location start;
		private final Vector dir;
		private final int range;
		private final OfflinePlayer off;
		private final boolean persona;

		public RageAttack(Location start, Vector dir, int range, OfflinePlayer off, boolean persona) {
			this.start = start;
			this.dir = dir;
			this.range = range;
			this.off = off;
			this.persona = persona;
			HashMapStore.setSkillStop(off.getUniqueId().toString(), true);
		}

		@Override
		public void run() {
			if (off.isOnline() && ConfigStore.getPlayerStatus(off.getPlayer())) {
				Player player = off.getPlayer();
				Location now = start.clone();
				List<UUID> list = new ArrayList<UUID>();
				list.add(off.getUniqueId());
				for (int c = 0; c <= range; c++) {
					for (Entity et : now.getWorld().getNearbyEntities(now, 1, 1, 1)) {
						if (!list.contains(et.getUniqueId()) && et instanceof LivingEntity
								&& (!(et instanceof Player) || (ConfigStore.getPlayerStatus((Player) et)
										&& ConfigStore.getPVP(player) && ConfigStore.getPVP((Player) et)))) {
							LivingEntity le = (LivingEntity) et;
							le.setNoDamageTicks(0);
							le.damage(getAttackDamage(player, persona), player);
							addIdentity(player, 0);
							list.add(et.getUniqueId());
						}
					}
					Effects.spawnRedStone(now, 163, 0, 0, 4, 50, 0.7, 0.7, 0.7);
					now = now.add(dir);
				}
			}
			HashMapStore.setSkillStop(off.getUniqueId().toString(), false);
			this.cancel();
		}
	}

	@Override
	public boolean Hit(Player player, LivingEntity entity, int slot) {
//		entity.setNoDamageTicks(0);
//		entity.damage(getHitDamage(player), player);
//		target.put(player.getUniqueId(), entity.getUniqueId());
//		ComboCoolRunnable.run(player, this, getDuration(), slot);
//		HashMapStore.setIdentity(player, HashMapStore.getIdentity(player) + getHitIdentity());
		return true;
	}

	@Override
	public void comboEnd(Player player, int slot) {
//		super.comboEnd(player, slot);
//		target.remove(player.getUniqueId());
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
