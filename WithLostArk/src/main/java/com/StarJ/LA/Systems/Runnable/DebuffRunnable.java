package com.StarJ.LA.Systems.Runnable;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.StarJ.LA.Core;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Jobs;

public class DebuffRunnable extends BukkitRunnable {
	private static HashMap<UUID, HashMap<DebuffType, BukkitTask>> debuffs = new HashMap<UUID, HashMap<DebuffType, BukkitTask>>();
	private static HashMap<UUID, Float> slowness = new HashMap<UUID, Float>();
	private final OfflinePlayer off;
	private final DebuffType type;

	public DebuffRunnable(Player player, DebuffType type) {
		this.off = player;
		this.type = type;
	}

	public static float getSlowness(Player player) {
		UUID uuid = player.getUniqueId();
		if (debuffs.containsKey(uuid)) {
			HashMap<DebuffType, BukkitTask> debuff = debuffs.get(uuid);
			if (debuff.containsKey(DebuffType.Slowness)) {
				BukkitTask task = debuff.get(DebuffType.Slowness);
				if (!task.isCancelled())
					return slowness.containsKey(uuid) ? slowness.get(uuid) : 0;
			}
		}
		return 0;
	}

	public static void run(Player player, DebuffType type, double duration) {
		run(player, type, 0f, duration);
	}

	public static void run(Player player, DebuffType type, float power, double duration) {
		UUID uuid = player.getUniqueId();
		if (!debuffs.containsKey(uuid))
			debuffs.put(player.getUniqueId(), new HashMap<DebuffType, BukkitTask>());
		HashMap<DebuffType, BukkitTask> debuff = debuffs.get(uuid);
		if (debuff.containsKey(type)) {
			BukkitTask task = debuff.get(type);
			if (!task.isCancelled())
				task.cancel();
		}
		debuff.put(type, new DebuffRunnable(player, type).runTaskLater(Core.getCore(), (int) duration * 20));
		debuffs.put(uuid, debuff);
		type.Start(player, power, duration);
	}

	public static boolean hasDebuff(Player player, DebuffType type) {
		UUID uuid = player.getUniqueId();
		if (debuffs.containsKey(uuid)) {
			HashMap<DebuffType, BukkitTask> debuff = debuffs.get(uuid);
			if (debuff.containsKey(type)) {
				BukkitTask task = debuff.get(type);
				if (!task.isCancelled())
					return true;
			}
		}
		return false;
	}

	public static void cancel(Player player, DebuffType type) {
		UUID uuid = player.getUniqueId();
		if (!debuffs.containsKey(uuid))
			debuffs.put(player.getUniqueId(), new HashMap<DebuffType, BukkitTask>());
		HashMap<DebuffType, BukkitTask> debuff = debuffs.get(uuid);
		if (debuff.containsKey(type)) {
			BukkitTask task = debuff.get(type);
			if (!task.isCancelled())
				task.cancel();
			debuffs.put(uuid, debuff);
			type.End(player);
		}
	}

	@Override
	public void run() {
		UUID uuid = off.getUniqueId();
		if (debuffs.containsKey(uuid)) {
			HashMap<DebuffType, BukkitTask> debuff = debuffs.get(uuid);
			debuff.remove(type);
			debuffs.put(uuid, debuff);
			if (off.isOnline())
				type.End(off.getPlayer());
		}
		this.cancel();
	}

	public enum DebuffType {
		// 암흑, 에어본
		Restraint("속박") {
			@Override
			public void Start(Player player, float power, double duration) {
			}

			@Override
			public void End(Player player) {
			}
		},
		Silence("침묵") {
			@Override
			public void Start(Player player, float power, double duration) {
				player.setCooldown(Material.ENCHANTED_BOOK, (int) duration * 20);
			}

			@Override
			public void End(Player player) {
				player.setCooldown(Material.ENCHANTED_BOOK, 0);
			}
		},
		Slowness("슬로우") {
			@Override
			public void Start(Player player, float power, double duration) {
				slowness.put(player.getUniqueId(), power);
				Jobs job = ConfigStore.getJob(player);
				if (job != null)
					player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
			}

			@Override
			public void End(Player player) {
				Jobs job = ConfigStore.getJob(player);
				slowness.remove(player.getUniqueId());
				if (job != null)
					player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
			}
		}
		//
		;

		private final String name;

		private DebuffType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public abstract void Start(Player player, float power, double duration);

		public abstract void End(Player player);
	}

}
