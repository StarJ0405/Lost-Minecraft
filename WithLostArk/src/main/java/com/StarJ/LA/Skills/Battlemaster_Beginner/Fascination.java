package com.StarJ.LA.Skills.Battlemaster_Beginner;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
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

public class Fascination extends Skills {

	public Fascination() {
		super("fascination", "매혹의 본능", 0, ChatColor.YELLOW, "??");
	}

	private double getDuration() {
		return 7.0d;
	}

	public float getSpeed() {
		return 0.3f;
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (super.Use(player, slot))
			return true;
		Jobs job = ConfigStore.getJob(player);
		if (job != null) {
			if (job.getMaxIdentity() - HashMapStore.getIdentity(player) <= 0) {
				Effects.Directional.SMOKE_NORMAL.spawnDirectional(player.getEyeLocation(), 200, 0.75, 0.75, 0.75, 0.1);
				Vector vec = player.getLocation().getDirection().multiply(-1).setY(0).normalize().setY(0.5);
				player.setVelocity(vec);
				HashMapStore.addAttackList(player, this);
				BuffRunnable.run(player, this, getDuration(), slot);
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (int) (getDuration() * 20), 0,
						true, true, true));
				player.playSound(player, Sound.ENTITY_PHANTOM_AMBIENT, 1f, 0.5f);
				HashMapStore.setIdentity(player, 0);
				player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
			} else {
				player.sendMessage(job.getIdentityName() + "이 부족합니다.");
				if (player.getGameMode().equals(GameMode.CREATIVE))
					HashMapStore.setIdentity(player, job.getMaxIdentity());
			}
		}

		return false;
	}

	@Override
	public boolean Attack(Player att) {
		att.removePotionEffect(PotionEffectType.INVISIBILITY);
		BuffRunnable.cancel(att, this);
		HashMapStore.removeAttackList(att, this);
		Jobs job = ConfigStore.getJob(att);
		if (job != null)
			att.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(att));
		return super.Attack(att);
	}

	@Override
	public void BuffEnd(Player player, int slot) {
		super.BuffEnd(player, slot);
		HashMapStore.removeAttackList(player, this);
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		Jobs job = ConfigStore.getJob(player);
		if (job != null)
			player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
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
