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

public class ComboCoolRunnable extends BukkitRunnable {
	private final static HashMap<UUID, HashMap<Skills, BukkitTask>> tasks = new HashMap<UUID, HashMap<Skills, BukkitTask>>();
	private final OfflinePlayer off;
	private final String key;
	private final int slot;
	private final Skills skill;

	public ComboCoolRunnable(Player player, Skills skill, int slot) {
		this.off = player;
		this.skill = skill;
		Jobs job = ConfigStore.getJob(player);
		this.key = job != null ? job.getKey() : "None";
		this.slot = slot;
		if (slot >= 0)
			player.getInventory().setItem(slot, skill.getComboItemStack());
	}

	public static boolean hasCombo(OfflinePlayer off, Skills skill) {
		UUID key = off.getUniqueId();
		return tasks.containsKey(key) && tasks.get(key).containsKey(skill) && !tasks.get(key).get(skill).isCancelled();
	}

	public static void EndCombo(OfflinePlayer off, Skills skill) {
		UUID key = off.getUniqueId();
		if (tasks.containsKey(key)) {
			HashMap<Skills, BukkitTask> hs = tasks.get(key);
			if (hs.containsKey(skill)) {
				BukkitTask task = hs.get(skill);
				if (!task.isCancelled())
					task.cancel();
				hs.remove(skill);
			}
			tasks.put(key, hs);
		}
	}

	public static void run(Player player, Skills skill, int duration, int slot) {
		UUID key = player.getUniqueId();
		if (!tasks.containsKey(key)) {
			tasks.put(key, new HashMap<Skills, BukkitTask>());
		}
		HashMap<Skills, BukkitTask> hs = tasks.get(key);
		if (hs.containsKey(skill)) {
			BukkitTask task = hs.get(skill);
			if (!task.isCancelled())
				task.cancel();
		}
		if (skill != null && duration > 0)
			hs.put(skill, new ComboCoolRunnable(player, skill, slot).runTaskLater(Core.getCore(), duration));
		tasks.put(key, hs);
	}

	@Override
	public void run() {
		if (hasCombo(off, skill)) {
			if (off.isOnline()) {
				Player player = off.getPlayer();
				Jobs job = ConfigStore.getJob(player);
				if (job != null && job.getKey().equalsIgnoreCase(key)) {
					skill.comboEnd(player, slot);
					this.cancel();
				} else
					this.cancel();
			} else
				this.cancel();
		} else
			this.cancel();
		
	}

}
