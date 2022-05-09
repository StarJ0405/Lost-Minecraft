package com.StarJ.LA.Systems.Runnable;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.StarJ.LA.Core;

public class SneakRunnable extends BukkitRunnable {
	private static HashMap<UUID, BukkitTask> sneak = new HashMap<UUID, BukkitTask>();

	public static void Start(OfflinePlayer off) {
		if (off != null)
			sneak.put(off.getUniqueId(), new SneakRunnable().runTaskLater(Core.getCore(), 10));
	}

	public static boolean isSneaking(OfflinePlayer off) {
		return off != null && sneak.containsKey(off.getUniqueId()) && !sneak.get(off.getUniqueId()).isCancelled();
	}

	@Override
	public void run() {
		this.cancel();
		return;
	}

}
