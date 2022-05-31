package com.StarJ.LA.Skills.Battlemaster_Beginner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

public class EnergyCombustion extends Skills {

	public EnergyCombustion() {
		// 25 - 9 = 16
		super("rage_spear", "레이지 스피어", 16.0d, ChatColor.RED, AttackType.BACK,
				ChatColor.YELLOW + "일반         " + ChatColor.RED + "[급습 스킬]", "전방의 적을 찌릅니다.", " - 치명타 적중률 +40%",
				" - 기 모으는 도중 피해");
	}

	public double getDrainDamage(Player player, boolean persona) {
		Jobs job = ConfigStore.getJob(player);
		// 698 * 1.7 * 0.85 / 2
		return 504d * (job != null ? job.getAttackDamagePercent(player, true, persona) : 1);
	}

	public double getAttackDamage(Player player, boolean persona) {
		Jobs job = ConfigStore.getJob(player);
		// 698 * 1.7
		return 1186d * (job != null ? job.getAttackDamagePercent(player, true, persona) : 1);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Skills persona = Skills.Persona;
		Location loc = player.getLocation();
		Location eyeloc = player.getEyeLocation();
		Vector dir = loc.getDirection();

		new RageDrain(eyeloc, dir, 8, player, BuffRunnable.has(player, persona)).runTaskTimer(Core.getCore(), 0, 2);

		persona.BuffEnd(player, -1);
		return false;
	}

	private class RageDrain extends BukkitRunnable {
		private Location start;
		private Vector dir;
		private Location end;
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
		}

		@Override
		public void run() {
			if (off.isOnline() && ConfigStore.getPlayerStatus(off.getPlayer())) {
				Player player = off.getPlayer();
				this.start = player.getEyeLocation();
				this.dir = start.getDirection();
				this.end = start.clone().add(dir.clone().multiply(range));
				this.move = end.clone().subtract(dir.clone().multiply(this.range - this.time));
				if (this.time > 0) {
					Location now = start.clone();
					List<UUID> list = new ArrayList<UUID>();
					list.add(off.getUniqueId());
					double identity = HashMapStore.getIdentity(player);
					for (int c = 0; c <= range; c++) {
						if (time % 4 == 2)
							for (Entity et : now.getWorld().getNearbyEntities(now, 1, 1, 1)) {
								if (!list.contains(et.getUniqueId()) && Skills.canAttack(player, et)) {
									LivingEntity le = (LivingEntity) et;
									Stats.Critical.setImportantStat(player, 0.4d
											+ (AttackType.getAttackType(et, player).equals(getAttackType()) ? 0.1 : 0));
									damage(player, le, getDrainDamage(player, persona));
									Stats.Critical.removeImportantStat(player);
									list.add(et.getUniqueId());
								}
							}
						Effects.spawnRedStone(now, 255, 0, 0, 1, 20, 1.2, 1.2, 1.2);
						now = now.add(dir);
					}
					if (time % 4 == 2) {
						player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
					} else
						player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 0.2f, 0.2f);
					HashMapStore.setIdentity(player, identity);
					Effects.spawnRedStone(move, 0, 0, 0, 1.5f, 10, 0.3, 0.3, 0.3);
					this.time--;
				} else {
					new RageAttack(start, dir, range, off, persona).runTaskLater(Core.getCore(), 3);
					this.cancel();
				}
			} else {
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
		}

		@Override
		public void run() {
			if (off.isOnline() && ConfigStore.getPlayerStatus(off.getPlayer())) {
				Player player = off.getPlayer();
				Location now = start.clone();
				List<UUID> list = new ArrayList<UUID>();
				list.add(off.getUniqueId());
				double identity = HashMapStore.getIdentity(player);
				for (int c = 0; c <= range; c++) {
					for (Entity et : now.getWorld().getNearbyEntities(now, 1, 1, 1)) {
						if (!list.contains(et.getUniqueId()) && Skills.canAttack(player, et)) {
							LivingEntity le = (LivingEntity) et;
							Stats.Critical.setImportantStat(player,
									0.4d + (AttackType.getAttackType(et, player).equals(getAttackType()) ? 0.1 : 0));
							damage(player, le, getAttackDamage(player, persona));
							Stats.Critical.removeImportantStat(player);
							list.add(et.getUniqueId());
						}
					}
					Effects.spawnRedStone(now, 163, 0, 0, 1.5f, 50, 0.7, 0.7, 0.7);
					now = now.add(dir);
				}
				player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 2f, 1f);
				HashMapStore.setIdentity(player, identity);
			}
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
