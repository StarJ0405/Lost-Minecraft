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

public class SkillCoolRunnable extends BukkitRunnable {
	private final static HashMap<UUID, HashMap<Skills, BukkitTask>> tasks = new HashMap<UUID, HashMap<Skills, BukkitTask>>();
	private final OfflinePlayer off;
	private final String key;
	private final int slot;
	private final Skills skill;

	public SkillCoolRunnable(Player player, Skills skill, int slot) {
		this.off = player;
		this.skill = skill;
		Jobs job = ConfigStore.getJob(player);
		this.key = job != null ? job.getKey() : "None";
		this.slot = slot;
		if (slot >= 0)
			player.getInventory().setItem(slot, skill.getCoolItemStack());
	}

	public static void end(Player player, Skills skill) {
		UUID uuid = player.getUniqueId();
		if (!tasks.containsKey(uuid))
			tasks.put(uuid, new HashMap<Skills, BukkitTask>());
		HashMap<Skills, BukkitTask> hs = tasks.get(uuid);
		if (hs.containsKey(skill)) {
			BukkitTask task = hs.get(skill);
			if (!task.isCancelled())
				task.cancel();
			hs.remove(skill);
			tasks.put(uuid, hs);
		}
		ConfigStore.setSkillCooldown(player, ConfigStore.getJob(player), skill, 0);
	}

	public static void run(Player player, Skills skill, int slot) {
		if (skill != null)
			run(player, skill, slot, skill.getCooldown(player));
	}

	public static void run(Player player, Skills skill, int slot, double pre_cool) {
		if (skill != null) {
			if (pre_cool > 0) {
				int cool = (int) (pre_cool * 20);
				ConfigStore.setSkillCooldown(player, ConfigStore.getJob(player), skill, cool * 1000 / 20);
				UUID uuid = player.getUniqueId();
				if (!tasks.containsKey(uuid))
					tasks.put(uuid, new HashMap<Skills, BukkitTask>());
				HashMap<Skills, BukkitTask> hs = tasks.get(uuid);
				if (hs.containsKey(skill)) {
					BukkitTask task = hs.get(skill);
					if (!task.isCancelled())
						task.cancel();
				}
				hs.put(skill, new SkillCoolRunnable(player, skill, slot).runTaskLater(Core.getCore(), cool));
				tasks.put(uuid, hs);
			} else if (slot > 0)
				player.getInventory().setItem(slot, skill.getItemStack());
		}
	}

	@Override
	public void run() {
		if (off.isOnline()) {
			Player player = off.getPlayer();
			Jobs job = ConfigStore.getJob(player);
			if (job != null && job.getKey().equalsIgnoreCase(key)) {
				skill.End(player, slot);
			}
			end(player, skill);
			this.cancel();
		} else
			this.cancel();

	}

}
