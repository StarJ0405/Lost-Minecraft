package com.StarJ.LA.Skills.Reaper;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;

public class DancingofFury extends Skills {

	public DancingofFury() {
		super("dancing_of_fury", "댄싱 오브 퓨리", 10.0d, ChatColor.RED);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
//		Location loc = player.getLocation();
//		Vector dir = loc.getDirection().multiply(2);
//		Effects.Infos[] infos = new Effects.Infos[1];
//		infos[0] = new Effects.RedStoneInfos(Effects.getRel(dir, 0, 0, 0), 0, 255, 0, 1, 2, 0.1, 0.1, 0.1);
//		new ProjectileRunnable(player, Skills.Nightmare, slot, player.getEyeLocation(), dir, 50, infos)
//				.runTaskTimer(Core.getCore(), 0, 1);
		Skills persona = Skills.Persona;
		BuffRunnable.has(player, persona);
		persona.BuffEnd(player, -1);
		return false;
	}

	@Override
	public boolean Hit(Player player, LivingEntity entity, int slot) {
//		entity.setNoDamageTicks(0);
//		entity.damage(getHitDamage(player), player);
//		target.put(player.getUniqueId(), entity.getUniqueId());
//		ComboCoolRunnable.run(player, this, getDuration(), slot);
//		HashMapStore.setIdentity(player, HashMapStore.getIdentity(player) + getHitIdentity());
		return true;
	}

	@Override
	public void comboEnd(Player player, int slot) {
//		super.comboEnd(player, slot);
//		target.remove(player.getUniqueId());
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
