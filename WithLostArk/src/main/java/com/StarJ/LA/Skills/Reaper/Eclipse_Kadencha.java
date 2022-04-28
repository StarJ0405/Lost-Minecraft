package com.StarJ.LA.Skills.Reaper;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.StarJ.LA.Skills.Skills;

public class Eclipse_Kadencha extends Skills {

	public Eclipse_Kadencha() {
		super("eclipse_kadencha", "월식: 카덴차", 300.0d, ChatColor.AQUA);
	}

	@Override
	public boolean Use(Player player, int slot) {
//		player.sendTitle(ChatColor.AQUA+ "월식: 카덴차", ChatColor.DARK_PURPLE + "페르소나", 1, 100, 1);
		player.sendTitle(ChatColor.GOLD + "각성", ChatColor.AQUA + "" + ChatColor.BOLD + "월식: 카덴차", 1, 100, 1);
		if (super.Use(player, slot))
			return true;
//		Location loc = player.getLocation();
//		Vector dir = loc.getDirection().multiply(2);
//		Effects.Infos[] infos = new Effects.Infos[1];
//		infos[0] = new Effects.RedStoneInfos(Effects.getRel(dir, 0, 0, 0), 0, 255, 0, 1, 2, 0.1, 0.1, 0.1);
//		new ProjectileRunnable(player, Skills.Nightmare, slot, player.getEyeLocation(), dir, 50, infos)
//				.runTaskTimer(Core.getCore(), 0, 1);
		Skills.Persona.BuffEnd(player, -1);
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
