package com.StarJ.LA.Skills.Battlemaster_Beginner;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Stats;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;

public class RoarofCourage extends Skills {

	public RoarofCourage() {
		// 쿨타임 : (25 - 5) = 20d
		// 무력 : 21d
		super("roar_of_courage", "용맹의 포효", 20d, 21d, ChatColor.GRAY, new AttackType[] { AttackType.BACK },
				ChatColor.YELLOW + "일반                " + ChatColor.GRAY + "[일반 스킬]", "기합을 내뿜어 피해를 줍니다.",
				"- 6초동안 치명타 적중률 +18%");
	}

	private double getDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 1136
		return 1136d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getDuration() {
		return 6d;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Location loc = player.getEyeLocation();
		Effects.spawnRedStone(loc, 255, 127, 0, 1, 250, 1f, 1f, 1f);
		for (Entity et : loc.getWorld().getNearbyEntities(loc, 2f, 2f, 2f))
			if (Skills.canAttack(player, et)) 
				damage(player, (LivingEntity) et, getDamage(player));
		player.playSound(player, Sound.ENTITY_WOLF_GROWL, 0.75f, 1f);
		Stats.Critical.setImportantStat(player, 0.18);
		BuffRunnable.run(player, this, getDuration(), slot);
		return false;
	}

	@Override
	public void BuffEnd(Player player, int slot) {
		super.BuffEnd(player, slot);
		Stats.Critical.setImportantStat(player, 0);
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
