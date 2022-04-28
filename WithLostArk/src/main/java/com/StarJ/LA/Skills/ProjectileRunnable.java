package com.StarJ.LA.Skills;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;

public class ProjectileRunnable extends BukkitRunnable {
	private final OfflinePlayer caster;
	private final Skills skill;
	private final int slot;
	private final Location start;
	private Location now;
	private final Vector dir;
	private final int max_time;
	private int time;
	private final double max_distance;
	private final Effects.Infos[] infos;

	public ProjectileRunnable(OfflinePlayer caster, Skills skill, int slot, Location start, Vector dir, int max_time,
			double max_distance, Effects.Infos[] infos) {
		this.caster = caster;
		this.skill = skill;
		this.slot = slot;
		this.start = start;
		this.now = start;
		this.dir = dir;
		this.max_time = max_time;
		this.time = 0;
		this.max_distance = max_distance;
		this.infos = infos;
	}

	@Override
	public void run() {
		if (caster.isOnline() && this.infos.length > 0 && this.time < this.max_time
				&& this.start.distance(this.now) < this.max_distance) {
			for (Effects.Infos info : infos)
				if (info != null)
					info.spawn(this.now);
			Vector box = skill.getHitBox();
			for (double x = -box.getX(); x < box.getX(); x++)
				for (double y = -box.getY(); y < box.getY(); y++)
					for (double z = -box.getZ(); z < box.getZ(); z++) {
						Block block = this.now.clone().add(x, y, z).getBlock();
						if (!block.isPassable()) {
							if (this.skill.Hit(caster.getPlayer(), block, slot)) {
								this.cancel();
								return;
							}
						}
					}
			for (Entity et : this.now.getWorld().getNearbyEntities(this.now, box.getX(), box.getY(), box.getZ())) {
				if (!et.getUniqueId().equals(caster.getUniqueId()))
					if (et instanceof LivingEntity
							&& (!(et instanceof Player) || (ConfigStore.getPlayerStatus((Player) et)
									&& ConfigStore.getPVP((Player) et) && ConfigStore.getPVP(caster.getPlayer())))
							&& !et.hasMetadata("key")) {
						if (this.skill.Hit(caster.getPlayer(), (LivingEntity) et, slot)) {
							this.cancel();
							return;
						}
					}
			}
			this.time++;
			this.now = now.add(dir);

		} else
			this.cancel();
	}
}
