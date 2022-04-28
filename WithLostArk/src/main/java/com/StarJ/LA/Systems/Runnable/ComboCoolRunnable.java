package com.StarJ.LA.Systems.Runnable;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.StarJ.LA.Core;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Jobs;

public class ComboCoolRunnable extends BukkitRunnable {

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

	public static void run(Player player, Skills skill, int duration, int slot) {
		if (skill != null && duration > 0)
			new ComboCoolRunnable(player, skill, slot).runTaskLater(Core.getCore(), duration);
	}

	@Override
	public void run() {
		if (off.isOnline()) {
			Player player = off.getPlayer();
			Jobs job = ConfigStore.getJob(player);
			if (job != null && job.getKey().equalsIgnoreCase(key)) {
				skill.comboEnd(player, slot);
			} else
				this.cancel();
		} else
			this.cancel();
	}

}
