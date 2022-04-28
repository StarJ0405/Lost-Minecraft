package com.StarJ.LA.Skills.Reaper;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;

public class Persona extends Skills {

	public Persona() {
		super("persona", "페르소나", 0, ChatColor.YELLOW);
	}

	private int getDuration() {
		return 5 * 20;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Jobs job = ConfigStore.getJob(player);
		if (job != null) {
			if (job.getMaxIdentity() - HashMapStore.getIdentity(player) <= 0) {
				Effects.Directional.SMOKE_NORMAL.spawnDirectional(player.getEyeLocation(), 200, 0.75, 0.75, 0.75, 0.1);
				Vector vec = player.getLocation().getDirection().multiply(-1);
				vec.setY(0.5);
				player.setVelocity(vec);
				HashMapStore.addAttackList(player, this);
				BuffRunnable.run(player, this, getDuration(), slot);
				player.addPotionEffect(
						new PotionEffect(PotionEffectType.INVISIBILITY, getDuration(), 0, true, true, true));
				HashMapStore.setIdentity(player, 0);
			} else
				player.sendMessage(job.getIdentityName() + "가 부족합니다.");
		}
		return false;
	}

	@Override
	public boolean Attack(Player att) {
		att.removePotionEffect(PotionEffectType.INVISIBILITY);
		BuffRunnable.cancel(att, this);
		HashMapStore.removeAttackList(att, this);
		return super.Attack(att);
	}

	@Override
	public void BuffEnd(Player player, int slot) {
		super.BuffEnd(player, slot);
		HashMapStore.removeAttackList(player, this);
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
	}

	@Override
	public boolean Hit(Player player, LivingEntity entity, int slot) {
		return true;
	}

	@Override
	public void comboEnd(Player player, int slot) {
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
