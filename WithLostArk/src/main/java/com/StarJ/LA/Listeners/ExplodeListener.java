package com.StarJ.LA.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplodeListener implements Listener {
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
}
