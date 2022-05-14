package com.StarJ.LA.Skills.Reaper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.Jobs;

public class Distortion extends Skills {

	public Distortion() {
		super("distortion", "디스토션", 10.0d, ChatColor.DARK_PURPLE);
	}

	private double getIdentity() {
		return 100;
	}

	private double getMaxIdentity() {
		return 400;
	}

	private double getDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		return 10.0d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Location loc = player.getLocation();
		Vector dir = loc.getDirection().multiply(0.5);
		Location eyeloc = player.getEyeLocation().clone();
		Location bodyloc = eyeloc.clone().subtract(0, 1, 0);
		Effects.spawnRedStone(bodyloc, 111, 0, 204, 5, 9, 0.2, 0.2, 0.2);

		double added = 0;
		List<UUID> uuids = new ArrayList<UUID>();
		for (int c = 0; c < 18; c++) {
			eyeloc = eyeloc.add(dir);
			bodyloc = bodyloc.add(dir);
			if (!eyeloc.getBlock().isPassable() || !bodyloc.getBlock().isPassable()) {
				eyeloc = eyeloc.subtract(dir);
				bodyloc = bodyloc.subtract(dir);
				break;
			}
			Effects.spawnRedStone(bodyloc, 111, 0, 204, 5, 9, 0.2, 0.2, 0.2);
			uuids.add(player.getUniqueId());
			for (Entity et : eyeloc.getWorld().getNearbyEntities(eyeloc, 1, 1.5, 1))
				if (!uuids.contains(et.getUniqueId()))
					if (et instanceof LivingEntity
							&& (!(et instanceof Player) || (ConfigStore.getPlayerStatus((Player) et)
									&& ConfigStore.getPVP(player) && ConfigStore.getPVP((Player) et)))) {
						LivingEntity le = ((LivingEntity) et);
						le.setNoDamageTicks(0);
						le.damage(getDamage(player), player);
						uuids.add(et.getUniqueId());
						if (added < getMaxIdentity())
							if (added == 0) {
								addIdentity(player, getIdentity());
								added += getIdentity();
							} else {
								added += getIdentity() * 0.5;
								addIdentity(player,
										added > getMaxIdentity() ? (getIdentity() * 0.5 - getMaxIdentity() + added)
												: getIdentity() * 0.5);
							}
					}
		}
		player.teleport(bodyloc);
		Skills.Persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
