package com.StarJ.LA.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;

public class FurnaceListener implements Listener {
	@EventHandler
	public void Events(FurnaceStartSmeltEvent e) {
		e.setTotalCookTime(1);
	}
}
