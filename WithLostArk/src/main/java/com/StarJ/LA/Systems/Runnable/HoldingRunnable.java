package com.StarJ.LA.Systems.Runnable;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.StarJ.LA.Core;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;

public class HoldingRunnable extends BukkitRunnable {
	private final static HashMap<UUID, HashMap<String, HoldingInfo>> holdings = new HashMap<UUID, HashMap<String, HoldingInfo>>();
	private final OfflinePlayer off;
	private final Skills skill;
	private final int slot;
	private final int max;
	private int times;

	public HoldingRunnable(Player player, Skills skill, int slot, int max) {
		this.off = player;
		this.skill = skill;
		this.slot = slot;
		this.max = max;
		this.times = 0;
		if (slot >= 0)
			player.getInventory().setItem(slot, skill.getSneakingItemStack());
	}

	public static void run(Player player, Skills skill, int times, int tick, int slot) {
		if (skill != null)
			if (times > 0) {
				UUID key = player.getUniqueId();
				HashMap<String, HoldingInfo> holds = holdings.containsKey(key) ? holdings.get(key)
						: new HashMap<String, HoldingInfo>();
				if (holds.containsKey(skill.getKey())) {
					HoldingInfo info = holds.get(skill.getKey());
					BukkitTask task = info.getTask();
					if (task != null)
						task.cancel();
				}
				holds.put(skill.getKey(), new HoldingInfo(
						new HoldingRunnable(player, skill, slot, times).runTaskTimer(Core.getCore(), tick, tick),
						slot));
				holdings.put(key, holds);
			} else
				skill.HoldingSucceed(player, slot);

	}

	public static void cancel(Player player, Skills skill) {
		UUID key = player.getUniqueId();
		if (holdings.containsKey(key)) {
			HashMap<String, HoldingInfo> holds = holdings.get(key);
			if (holds.containsKey(skill.getKey())) {
				HoldingInfo info = holds.get(skill.getKey());
				BukkitTask task = info.getTask();
				if (!task.isCancelled())
					task.cancel();
				holds.remove(skill.getKey());
				holdings.put(key, holds);
				int slot = info.getSlot();
				if (slot >= 0 && ConfigStore.getPlayerStatus(player)) {
					double cool = ConfigStore.getSkillCooldown(player, ConfigStore.getJob(player), skill);
					if (cool > 0) {
						player.getInventory().setItem(slot, skill.getCoolItemStack());
					} else
						player.getInventory().setItem(slot, skill.getItemStack());
				}
			}
		}
	}

	@Override
	public void run() {
		if (off.isOnline() && skill != null) {
			Player player = off.getPlayer();
			if (this.times < this.max) {
				if (player.isSneaking()) {
					String sub = "";
					for (int c = 0; c < max; c++)
						if (c <= times) {
							sub += "■";
						} else
							sub += "□";
					player.sendTitle(ChatColor.GOLD + skill.getDisplayname(), sub, 1, 20, 1);
					skill.Holding(player, times);
					this.times++;
				} else {
					skill.HoldingFail(player, slot);
					player.sendTitle(ChatColor.GOLD + skill.getDisplayname(), ChatColor.RED + "FAIL", 1, 20, 1);
					this.cancel();
				}
			} else {
				skill.HoldingSucceed(player, slot);
				player.sendTitle(ChatColor.GOLD + skill.getDisplayname(), ChatColor.GREEN + "SUCCEED", 1, 20, 1);
				this.cancel();
			}
		} else
			this.cancel();
	}

	private static class HoldingInfo {
		private final BukkitTask task;
		private final int slot;

		public HoldingInfo(BukkitTask task, int slot) {
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
