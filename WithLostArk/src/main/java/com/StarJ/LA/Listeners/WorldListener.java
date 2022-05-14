package com.StarJ.LA.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.StarJ.LA.Core;
import com.StarJ.LA.Systems.ConfigStore;

public class WorldListener implements Listener {
	@EventHandler
	public void Events(BlockExplodeEvent e) {
		e.blockList().clear();
	}

	@EventHandler
	public void Events(ExplosionPrimeEvent e) {
	}

	@EventHandler
	public void Events(EntityExplodeEvent e) {
		e.blockList().clear();
	}

	@EventHandler
	public void Events(ChunkLoadEvent e) {
		for (Entity et : e.getChunk().getEntities()) {
			if (et instanceof Villager) {
				if (et.hasMetadata("key")) {
					new BukkitRunnable() {
						@Override
						public void run() {
							ConfigStore.Confirm();
						}
					}.runTaskLater(Core.getCore(), 5);
					break;
				}
			}
		}
	}

	@EventHandler
	public void Events(PlayerChangedWorldEvent e) {
		for (Entity et : e.getPlayer().getWorld().getEntities()) {
			if (et instanceof Villager) {
				if (et.hasMetadata("key")) {
					ConfigStore.Confirm();
					break;
				}
			}
		}
	}
}
