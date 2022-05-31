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
import com.StarJ.LA.Systems.Jobs.IdentityType;

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
				double abp = HashMapStore.getAllAbsorption(player);
				double max_identity = job.getMaxIdentity();
				double identity = HashMapStore.getIdentity(player);
				IdentityType iden_type = job.getIdentityType();
				if (iden_type.equals(IdentityType.Buff)) {
					Effects.sendActionBar(player,
							ChatColor.GREEN + "체력 : " + Math.round(health)
									+ (abp > 0 ? "(+" + Math.round(abp) + ")" : "") + " / " + Math.round(max_health)
									+ "      " + job.getIdentityName() + " : " + identity + "초");
				} else if (iden_type.equals(IdentityType.Percent)) {
					Effects.sendActionBar(player, ChatColor.GREEN + "체력 : " + Math.round(health)
							+ (abp > 0 ? "(+" + Math.round(abp) + ")" : "") + " / " + Math.round(max_health) + "      "
							+ job.getIdentityName() + " : "
							+ Math.ceil(new BigDecimal(identity).divide(new BigDecimal(max_identity)).doubleValue()
									* 10000.0d) / 100.0d
							+ "%");
				} else if (iden_type.equals(IdentityType.Int)) {
					Effects.sendActionBar(player,
							ChatColor.GREEN + "체력 : " + Math.round(health)
									+ (abp > 0 ? "(+" + Math.round(abp) + ")" : "") + " / " + Math.round(max_health)
									+ "      " + job.getIdentityName() + " : " + identity + " / " + max_identity);
				} else
					Effects.sendActionBar(player,
							ChatColor.GREEN + "체력 : " + Math.round(health)
									+ (abp > 0 ? "(+" + Math.round(abp) + ")" : "") + " / " + Math.round(max_health)
									+ "                            ");
				this.time++;
			} else {
				Effects.sendActionBar(player, "");
				this.cancel();
			}
		} else
			this.cancel();
	}
}
