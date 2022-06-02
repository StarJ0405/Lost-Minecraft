package com.StarJ.LA.Systems.Runnable;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.StarJ.LA.Core;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Jobs;

public class BuffRunnable extends BukkitRunnable {
	private final static HashMap<UUID, HashMap<String, BuffInfo>> infos = new HashMap<UUID, HashMap<String, BuffInfo>>();
	private final OfflinePlayer off;
	private final Skills skill;
	private final String key;
	private final int slot;

	public BuffRunnable(Player player, Skills skill, int slot) {
		this.off = player;
		this.skill = skill;
		Jobs job = ConfigStore.getJob(player);
		this.key = job != null ? job.getKey() : "None";
		this.slot = slot;
		if (slot >= 0)
			player.getInventory().setItem(slot, skill.getBuffItemStack());
	}

	public static void run(Player player, Skills skill, double duration, int slot) {
		if (skill != null)
			if (duration > 0) {
				UUID key = player.getUniqueId();
				HashMap<String, BuffInfo> buffs = infos.containsKey(key) ? infos.get(key)
						: new HashMap<String, BuffInfo>();
				if (buffs.containsKey(skill.getKey())) {
					BuffInfo info = buffs.get(skill.getKey());
					BukkitTask task = info.getTask();
					if (task != null)
						task.cancel();
				}
				buffs.put(skill.getKey(), new BuffInfo(
						new BuffRunnable(player, skill, slot).runTaskLater(Core.getCore(), (int) duration * 20), slot));
				infos.put(key, buffs);
			} else
				skill.BuffEnd(player, slot);
	}

	public static boolean has(Player player, Skills skill) {
		UUID key = player.getUniqueId();
		if (infos.containsKey(key)) {
			HashMap<String, BuffInfo> buffs = infos.get(key);
			if (buffs.containsKey(skill.getKey())) {
				BuffInfo info = buffs.get(skill.getKey());
				return !info.getTask().isCancelled();
			}
		}
		return false;
	}

	public static void cancel(Player player, Skills skill) {
		UUID key = player.getUniqueId();
		if (infos.containsKey(key)) {
			HashMap<String, BuffInfo> buffs = infos.get(key);
			if (buffs.containsKey(skill.getKey())) {
				BuffInfo info = buffs.get(skill.getKey());
				BukkitTask task = info.getTask();
				if (!task.isCancelled())
					task.cancel();
				buffs.remove(skill.getKey());
				infos.put(key, buffs);
				skill.BuffEnd(player, info.getSlot());
			}
		}
	}

	@Override
	public void run() {
		if (off.isOnline()) {
			Player player = off.getPlayer();
			Jobs job = ConfigStore.getJob(player);
			if (job != null && job.getKey().equalsIgnoreCase(key)) {
				skill.BuffEnd(player, slot);
				this.cancel();
			} else
				this.cancel();
		} else
			this.cancel();
	}

	private static class BuffInfo {
		private final BukkitTask task;
		private final int slot;

		public BuffInfo(BukkitTask task, int slot) {
			this.task = task;
			this.slot = slot;
		}

		public BukkitTask getTask() {
			return task;
		}

		public int getSlot() {
			return slot;
		}
	}
}
