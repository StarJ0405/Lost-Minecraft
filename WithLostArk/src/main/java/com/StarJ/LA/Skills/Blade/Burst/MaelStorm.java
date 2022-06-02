package com.StarJ.LA.Skills.Blade.Burst;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;

public class MaelStorm extends Skills {

	public MaelStorm() {
		// 쿨타임 : (30- 9) = 21d
		// 무력 : 0d
		super("winds_whisper", "바람의 속삭임", 21d, 0, ChatColor.GRAY,
				ChatColor.YELLOW + "일반          " + ChatColor.GRAY + "[일반 스킬]", "바람의 힘을 깃들입니다.", "- 이동속도 16% 증가",
				"- 피해 35.5% 감소", "- 스킬 피해량 49.7% 증가");

	}

	public double getPower() {
		return 0.497d;
	}

	public double getReduceDamage() {
		return 0.355d;
	}

	public float getWalkSpeed() {
		return 0.16f;
	}

	private double getDuration() {
		return 6.0d;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Location loc = player.getLocation().clone().add(0, 0.25, 0);
		Vector dir = loc.getDirection().clone().setY(0).normalize();
		for (int c = 1; c <= 36; c++) {
			double x = Math.sin(Math.PI / 36 * c) * 0.25 * c;
			double z = Math.cos(Math.PI / 36 * c) * 0.25 * c;
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, x, 0, z)), 255, 255, 255, 1, 5, 0.1f, 0.1f, 0.1f);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, z, 0, -x)), 255, 255, 255, 1, 5, 0.1f, 0.1f,
					0.1f);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, -z, 0, x)), 255, 255, 255, 1, 5, 0.1f, 0.1f,
					0.1f);
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, -x, 0, -z)), 255, 255, 255, 1, 5, 0.1f, 0.1f,
					0.1f);
		}
		player.playSound(player, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1f, 1f);
		BuffRunnable.run(player, this, getDuration(), slot);
		Jobs job = ConfigStore.getJob(player);
		if (job != null)
			player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
		return false;
	}

	@Override
	public void BuffEnd(Player player, int slot) {
		super.BuffEnd(player, slot);
		Jobs job = ConfigStore.getJob(player);
		if (job != null)
			player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
