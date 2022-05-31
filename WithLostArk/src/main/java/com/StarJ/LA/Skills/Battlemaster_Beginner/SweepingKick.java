package com.StarJ.LA.Skills.Battlemaster_Beginner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.StarJ.LA.Core;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.Jobs;

public class SweepingKick extends Skills {

	public SweepingKick() {
		super("sweepingkick", "방천격", 25d, ChatColor.GREEN, AttackType.BACK,
				ChatColor.YELLOW + "일반                  " + ChatColor.GRAY + "[일반 스킬]", "공중 회전 발차기로 피해를 줍니다.",
				"- 치명타 적중률 +60%");
	}

	private double getDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// (69 + 69 + 91 + 114 + 228)* 3.05 = 1741
		// 1741 * 1.6 = 2786
		return 371d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Location loc = player.getLocation().clone().add(0, 0.25, 0);
		Vector dir = loc.getDirection();
		double r = 3;
		for (int i = 0; i <= r * 10; i++) {
			double x = i / 10.0d;
			double z = Math.sqrt(r * r * 100 - i * i) / 10.0d;
			Effects.spawnRedStone(loc.clone().add(x, 0, z), 255, 255, 255, 1f, 1, 0, 0, 0);
			Effects.spawnRedStone(loc.clone().add(x, 0, -z), 255, 255, 255, 1f, 1, 0, 0, 0);
			Effects.spawnRedStone(loc.clone().add(-x, 0, z), 255, 255, 255, 1f, 1, 0, 0, 0);
			Effects.spawnRedStone(loc.clone().add(-x, 0, -z), 255, 255, 255, 1f, 1, 0, 0, 0);
		}
		for (int c = 0; c <= 3; c++) {
			int a = c;
			new BukkitRunnable() {
				@Override
				public void run() {
					double r = 2.5 - 0.5 * a;
					for (int i = 0; i <= r * 10; i++) {
						double x = i / 10.0d;
						double z = Math.sqrt(r * r * 100 - i * i) / 10.0d;
						Effects.spawnRedStone(loc.clone().add(x, 0, z), 255, 255, 255, 1f, 1, 0, 0, 0);
						Effects.spawnRedStone(loc.clone().add(x, 0, -z), 255, 255, 255, 1f, 1, 0, 0, 0);
						Effects.spawnRedStone(loc.clone().add(-x, 0, z), 255, 255, 255, 1f, 1, 0, 0, 0);
						Effects.spawnRedStone(loc.clone().add(-x, 0, -z), 255, 255, 255, 1f, 1, 0, 0, 0);
					}
					if (a == 2)
						player.setVelocity(new Vector(0, 0.5, 0));
				}
			}.runTaskLater(Core.getCore(), 3 * c);
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				List<UUID> list = new ArrayList<UUID>();
				for (int c = 0; c <= 6; c++) {
					Location loc = player.getLocation();
					for (Entity et : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5))
						if (!list.contains(et.getUniqueId()) && Skills.canAttack(player, et)) {
							damage(player, (LivingEntity) et, getDamage(player));
							list.add(et.getUniqueId());
						}
					Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, c * 0.5, 0, 0)), 255, 127, 0, 1.5f, 30,
							0.25, 0.25, 0.25);
				}
			}
		}.runTaskLater(Core.getCore(), 12);
		return false;
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
