package com.StarJ.LA.Skills.Blade.Burst;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.StarJ.LA.Systems.Runnable.HoldingRunnable;

public class BladeAssault extends Skills {

	public BladeAssault() {
		// 쿨타임 : 300d
		// 무력 : 81 / 26 = 3
		super("ones_heart_momentary_blow", "극의: 일순난격", 300.0d, 3d, ChatColor.DARK_RED,
				ChatColor.GREEN + "홀딩                                  " + ChatColor.DARK_RED + "[각성기]",
				"대시 후 무차별 난사를 가합니다.", "- 홀딩 성고시 강한 피니쉬 어택", "- 홀딩 실패시 피니쉬 피해량 감소");
	}

	private double getDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 32378 / 23 = 1407d
		return 1407d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getSuceedDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 17677
		return 17677d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	private double getFailDamage(Player player) {
		Jobs job = ConfigStore.getJob(player);
		// 12559
		return 12559d * (job != null ? job.getAttackDamagePercent(player) : 1);
	}

	@Override
	public boolean Use(Player player, int slot) {
		if (!player.isSneaking()) {
			player.sendMessage(ChatColor.RED + "홀딩 스킬은 쉬프트 중에만 사용 가능합니다.");
			return true;
		}
		if (super.Use(player, slot))
			return true;
		player.sendTitle(ChatColor.GOLD + "각성", ChatColor.AQUA + "" + ChatColor.BOLD + "극의: 일순난격", 1, 20, 1);
		player.setVelocity(player.getLocation().getDirection().clone().setY(0).normalize().multiply(0.75).setY(0.2));
		HoldingRunnable.run(player, this, 25, 2, slot);
		return false;
	}

	@Override
	public void Holding(Player player, int times) {
		super.Holding(player, times);
		Location loc = player.getEyeLocation().clone().subtract(0, 0.25, 0);
		Vector dir = loc.clone().getDirection().multiply(0.5);
		for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(dir), 1.5, 1.5, 1.5))
			if (Skills.canAttack(player, et))
				damage(player, (LivingEntity) et, getDamage(player));
		player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
		for (int c = 0; c <= 5; c++) {
			if (times % 2 == 0) {
				if (times % 4 == 0) {
					Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, c * 0.5, -0.5 + 0.125 * c, -1 + 0.4 * c)),
							255, 127, 0, 1, 10, 0.1, 0.1, 0.1);
				} else
					Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, c * 0.5, 0.5 - 0.125 * c, -1 + 0.4 * c)),
							255, 127, 0, 1, 10, 0.1, 0.1, 0.1);
			} else {
				if (times % 4 == 1) {
					Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, c * 0.5, -0.5 + 0.125 * c, 1 - 0.4 * c)),
							255, 127, 0, 1, 10, 0.1, 0.1, 0.1);
				} else
					Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, c * 0.5, 0.5 - 0.125 * c, 1 - 0.4 * c)),
							255, 127, 0, 1, 10, 0.1, 0.1, 0.1);
			}
		}
	}

	@Override
	public void HoldingFail(Player player, int slot) {
		super.HoldingFail(player, slot);
		Location loc = player.getEyeLocation().clone().subtract(0, 0.25, 0);
		Vector dir = loc.clone().getDirection();
		List<UUID> list = new ArrayList<UUID>();
		for (int c = 0; c < 20; c++) {
			for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(dir.clone().multiply((c + 1) * 0.5)), 0.5,
					0.5, 0.5))
				if (!list.contains(et.getUniqueId()) && Skills.canAttack(player, et)) {
					damage(player, (LivingEntity) et, getFailDamage(player));
					list.add(et.getUniqueId());
				}
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, c * 0.5, 0, 0)), 255, 90, 0, 1f, 81, 0.3, 0.3,
					0.3);
		}
		player.playSound(player, Sound.ENTITY_CREEPER_PRIMED, 2f, 1f);
	}

	@Override
	public void HoldingSucceed(Player player, int slot) {
		super.HoldingSucceed(player, slot);
		Location loc = player.getEyeLocation().clone().subtract(0, 0.25, 0);
		Vector dir = loc.clone().getDirection();
		List<UUID> list = new ArrayList<UUID>();
		for (int c = 0; c < 20; c++) {
			for (Entity et : loc.getWorld().getNearbyEntities(loc.clone().add(dir.clone().multiply((c + 1) * 0.5)), 0.5,
					0.5, 0.5))
				if (!list.contains(et.getUniqueId()) && Skills.canAttack(player, et)) {
					damage(player, (LivingEntity) et, getSuceedDamage(player));
					list.add(et.getUniqueId());
				}
			Effects.spawnRedStone(loc.clone().add(Effects.getRel(dir, c * 0.5, 0, 0)), 255, 65, 0, 1.5f, 125, 0.5, 0.5,
					0.5);
		}
		player.playSound(player, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 2f, 1f);
	}

	@Override
	public Vector getHitBox() {
		return new Vector(1, 1, 1);
	}
}
