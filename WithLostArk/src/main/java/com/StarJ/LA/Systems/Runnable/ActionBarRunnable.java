package com.StarJ.LA.Systems.Runnable;

import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.StarJ.LA.Core;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;

public class ActionBarRunnable extends BukkitRunnable {
	private final OfflinePlayer off;
	private int time = 0;
	private final int max = 5 * 2;

	public ActionBarRunnable(OfflinePlayer off) {
		this.off = off;
	}

	public static void run(Player player) {
		HashMapStore.setActionbar(player, new ActionBarRunnable(player).runTaskTimer(Core.getCore(), 0, 10));
	}

	@Override
	public void run() {
		if (off.isOnline()) {
			Player player = off.getPlayer();
			if (ConfigStore.getPlayerStatus(off.getPlayer()) && this.time < this.max) {
				Jobs job = ConfigStore.getJob(player);
				if (job == null) {
					this.cancel();
					return;
				}
				double max_health = job.getMaxHealth(player);
				double health = HashMapStore.getHealth(player);
				double max_identity = job.getMaxIdentity();
				double identity = HashMapStore.getIdentity(player);
				if (job.isIdentityPercent()) {
					Effects.sendActionBar(player, ChatColor.GREEN + "체력 : " + health + " / " + max_health + "      "
							+ job.getIdentityName() + " : "
							+ Math.ceil(new BigDecimal(identity).divide(new BigDecimal(max_identity)).doubleValue() * 100.0d) + "%");
				} else
					Effects.sendActionBar(player, ChatColor.GREEN + "체력 : " + health + " / " + max_health + "      "
							+ job.getIdentityName() + " : " + identity + " / " + max_identity);
				this.time++;
			} else {
				Effects.sendActionBar(player, "");
				this.cancel();
			}
		} else
			this.cancel();
	}
}
