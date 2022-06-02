package com.StarJ.LA.Skills.Blade.Burst;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.StarJ.LA.Core;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Stats;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;

public class BladeArts extends Skills {
	private HashMap<UUID, BukkitTask> tasks = new HashMap<UUID, BukkitTask>();

	public BladeArts() {
		// 쿨타임 : 36d
		// 무력 : 0d
		super("fascination", "매혹의 본능", 36d, 0d, ChatColor.YELLOW, "10% 확률로 지속 피해를 줍니다.");
	}

	public double getDuration() {
		return 20.0d;
	}

	private double getDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 200
		return 200d * (job != null ? job.getAttackDamagePercent(player) : 1)
				* Stats.Specialization.getStatPercent(player);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		BuffRunnable.run(player, this, getDuration(), slot);
		HashMapStore.addAttackList(player, this);
		HashMapStore.setIdentity(player, getDuration());
		if (tasks.containsKey(player.getUniqueId()))
			tasks.get(player.getUniqueId()).cancel();
		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				double identity = HashMapStore.getIdentity(player);
				if (identity > 0) {
					HashMapStore.setIdentity(player, identity - 1);
				} else
					this.cancel();
			}
		}.runTaskTimer(Core.getCore(), 0, 20);
		tasks.put(player.getUniqueId(), task);
		return false;
	}

	@Override
	public boolean Attack(Player att, LivingEntity vic) {
		if (vic != null && new Random().nextDouble() < 0.2d)
			new BukkitRunnable() {
				private int time = 0;
				private OfflinePlayer off = att;
				private String job = ConfigStore.getJob(att).getKey();

				@Override
				public void run() {
					if (off.isOnline()) {
						Player player = off.getPlayer();
						if (this.time < 5 && ConfigStore.getJob(player).getKey().equals(job)) {
							boolean has = HashMapStore.hasAttackList(att, BladeArts.this);
							if (has)
								HashMapStore.removeAttackList(att, BladeArts.this);
							damage(att, vic, getDamage(att));
							if (has)
								HashMapStore.addAttackList(att, BladeArts.this);
							Effects.spawnRedStone(vic.getEyeLocation().clone(), 255, 0, 0, 1f, 10, 0.1, 0.1, 0.1);
							att.playSound(vic.getEyeLocation(), Sound.BLOCK_FIRE_AMBIENT, 0.5f, 2f);
							this.time++;
						} else
							this.cancel();
					} else
						this.cancel();
				}
			}.runTaskTimer(Core.getCore(), 0, 10);

		return super.Attack(att, vic);
	}

	@Override
	public void BuffEnd(Player player, int slot) {
		super.BuffEnd(player, slot);
		HashMapStore.removeAttackList(player, this);
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
