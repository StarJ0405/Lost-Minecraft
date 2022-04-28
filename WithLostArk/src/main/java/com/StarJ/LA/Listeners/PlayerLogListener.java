package com.StarJ.LA.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.ScoreboarStore;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;

public class PlayerLogListener implements Listener {

	@EventHandler
	public void Events(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		ConfigStore.LoadPlayerAllData(player);
		ConfigStore.LoadPlayerAdvancement(player);
		ScoreboarStore.join(player);
		ConfigStore.confirmHealth(player);
		ConfigStore.confirmIdentity(player);
		ScoreboarStore.confirmTeam(player);
		if (ConfigStore.getPlayerStatus(player)) {
			Jobs job = ConfigStore.getJob(player);
			double max = job != null ? job.getMaxHealth(player) : 20;
			double health = HashMapStore.getHealth(player);

			double per = health / max * 100;
			if (per <= 1 && per > 0) {
				per = 1;
			} else if (per >= 99 && per < 100) {
				per = 99;
			}
			if (per > 100) {
				per = 100;
			}
			if (per <= 0)
				if (health > 0) {
					per = 1;
				} else
					per = 0;
			HashMapStore.setHealth(player, health);
			player.setHealth(per);
			ActionBarRunnable.run(player);
			HashMapStore.setSkillStop(player.getUniqueId().toString(), false);
		}
	}

	@EventHandler
	public void Events(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		ConfigStore.SavePlayerAllData(player);
		ConfigStore.SavePlayerAdvancement(player);
		ConfigStore.setHealth(player);
		ConfigStore.setIdentity(player);
	}

}
