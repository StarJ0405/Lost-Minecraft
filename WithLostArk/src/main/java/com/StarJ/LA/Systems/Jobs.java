package com.StarJ.LA.Systems;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Items.WeaponItems;
import com.StarJ.LA.Items.Potioning.SpeedRobeItem;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Skills.Reaper_Lunarsound.Nightmare;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;
import com.StarJ.LA.Systems.Runnable.DebuffRunnable;

public abstract class Jobs {
	private final static List<Jobs> jobs = new ArrayList<Jobs>();
	//
	public final static Jobs Reaper_Lunar = new Jobs("reaper_lunarsound", ChatColor.DARK_RED + "리퍼: 달의소리",
			Skills.Nightmare, Skills.ShadowDot, Skills.SpiritCatch, Skills.RageSpear, Skills.Distortion,
			Skills.ShadowStorm, Skills.LastGrapity, Skills.DancingofFury, Skills.Eclipse_Kadencha, Skills.Persona,
			WeaponItems.reaper_lunarsound, 5000, 0.14f, ChatColor.DARK_PURPLE + "페르소나", 1000, IdentityType.Percent,
			new String[] { "급습 스킬 피해량" }) {

		@Override
		public float getWalkspeed(Player player) {
			return walkspeed * Math.max(0f,
					Math.min(1.4f, Skills.Eclipse_Kadencha.isActive(player) ? 1.4f
							: (1f + SpeedRobeItem.getPower(player) + Nightmare.getSpeed(player)
									+ (BuffRunnable.has(player, Skills.Persona) ? Skills.Persona.getSpeed() : 0f)
									+ (BuffRunnable.has(player, Skills.Distortion) ? Skills.Distortion.getSpeed() : 0f)
									+ SpeedRobeItem.getPower(player) - DebuffRunnable.getSlowness(player))));
		}

		@Override
		public <T1, T2> double getAttackDamagePercent(Player player, T1 sudden_attack, T2 persona) {
			if (sudden_attack instanceof Boolean) {
				if (persona instanceof Boolean) {
					return (1 + ConfigStore.getWeaponLevel(player, this) * 0.05) * ((boolean) persona ? 2.6 : 1)
							* ((boolean) sudden_attack ? Stats.Specialization.getStatPercent(player) : 1);
				} else
					return (1 + ConfigStore.getWeaponLevel(player, this) * 0.05)
							* ((boolean) sudden_attack ? Stats.Specialization.getStatPercent(player) : 1);
			} else
				return (1 + ConfigStore.getWeaponLevel(player, this) * 0.05);
		}

	};
	public final static Jobs Battlemaster_Beginner = new Jobs("battlemaster_beginner", ChatColor.DARK_RED + "배틀마스터: 초심",
			Skills.RoarofCourage, Skills.WindsWhisper, Skills.SweepingKick, Skills.MoonFlashKick, Skills.FlashHeatFang,
			Skills.SkyShatteringBlow, Skills.LightningKick, Skills.EnergyCombustion, Skills.OnesHeart_MomentaryBlow,
			Skills.Fascination, WeaponItems.battlemaster_beginner, 5000, 0.14f, ChatColor.DARK_PURPLE + "매혹의 본능",
			Skills.Fascination.getDuration(), IdentityType.Buff, new String[] { "매혹 피해량" }) {
		@Override
		public float getWalkspeed(Player player) {
			return walkspeed * Math.max(0f, Math.min(1.4f, 1f + SpeedRobeItem.getPower(player)
					- DebuffRunnable.getSlowness(player)
					+ (BuffRunnable.has(player, Skills.WindsWhisper) ? Skills.WindsWhisper.getWalkSpeed() : 0f)));
		}

		@Override
		public double getAttackDamagePercent(Player player) {
			return super.getAttackDamagePercent(player) * (1
					+ (BuffRunnable.has(player, Skills.WindsWhisper) ? Skills.WindsWhisper.getPower() : 0d)
					+ (BuffRunnable.has(player, Skills.SkyShatteringBlow) ? Skills.SkyShatteringBlow.getPower() : 0d))
					* 1.32;
		}

		@Override
		public double getReduceDamage(Player player) {
			return BuffRunnable.has(player, Skills.WindsWhisper) ? Skills.WindsWhisper.getReduceDamage() : 0;
		}
	};
	public final static Jobs Blade_Burst = new Jobs("blade_burst", ChatColor.DARK_RED + "블레이드: 버스트",
			Skills.RoarofCourage, Skills.WindsWhisper, Skills.SweepingKick, Skills.MoonFlashKick, Skills.FlashHeatFang,
			Skills.SkyShatteringBlow, Skills.LightningKick, Skills.EnergyCombustion, Skills.OnesHeart_MomentaryBlow,
			Skills.Fascination, WeaponItems.battlemaster_beginner, 5000, 0.14f, ChatColor.DARK_PURPLE + "매혹의 본능",
			Skills.Fascination.getDuration(), IdentityType.Buff, new String[] { "매혹 피해량" }) {
		@Override
		public float getWalkspeed(Player player) {
			return walkspeed * Math.max(0f, Math.min(1.4f, 1f + SpeedRobeItem.getPower(player)
					- DebuffRunnable.getSlowness(player)
					+ (BuffRunnable.has(player, Skills.WindsWhisper) ? Skills.WindsWhisper.getWalkSpeed() : 0f)));
		}

		@Override
		public double getAttackDamagePercent(Player player) {
			return super.getAttackDamagePercent(player) * (1
					+ (BuffRunnable.has(player, Skills.WindsWhisper) ? Skills.WindsWhisper.getPower() : 0d)
					+ (BuffRunnable.has(player, Skills.SkyShatteringBlow) ? Skills.SkyShatteringBlow.getPower() : 0d))
					* 1.32;
		}

		@Override
		public double getReduceDamage(Player player) {
			return BuffRunnable.has(player, Skills.WindsWhisper) ? Skills.WindsWhisper.getReduceDamage() : 0;
		}
	};
	//
	private final String key;
	private final String displayname;
	private final Skills[] skills = new Skills[8];
	private final Skills specialarts;
	private final Skills identity;
	private final IdentityType identity_type;
	private final WeaponItems weapon;
	private final double max_health;
	protected final float walkspeed;
	private final String identity_name;
	private final double max_identity;
	private final String[] special_info;

	public enum IdentityType {
		Int, Percent, Buff
	}

	public Jobs(String key, String displayname, Skills skill1, Skills skill2, Skills skill3, Skills skill4,
			Skills skill5, Skills skill6, Skills skill7, Skills skill8, Skills awakening, Skills identity,
			WeaponItems weapon, double max_health, float walkspeed, String identity_name, double max_identity,
			IdentityType identity_type, String[] special_info) {
		jobs.add(this);
		this.key = key;
		this.displayname = displayname;
		this.skills[0] = skill1;
		this.skills[1] = skill2;
		this.skills[2] = skill3;
		this.skills[3] = skill4;
		this.skills[4] = skill5;
		this.skills[5] = skill6;
		this.skills[6] = skill7;
		this.skills[7] = skill8;
		this.specialarts = awakening;
		this.identity = identity;
		this.weapon = weapon;
		this.max_health = max_health;
		this.walkspeed = walkspeed;
		this.identity_name = identity_name;
		this.max_identity = max_identity;
		this.identity_type = identity_type;
		this.special_info = special_info;
	}

	public String getKey() {
		return key;
	}

	public String getDisplayname() {
		return displayname;
	}

	public Skills getSpecialArts() {
		return specialarts;
	}

	public Skills getIdentity() {
		return identity;
	}

	public IdentityType getIdentityType() {
		return identity_type;
	}

	public String[] getSpecialinfo() {
		return special_info;
	}

	public <T> double getAttackDamagePercent(Player player) {
		return getAttackDamagePercent(player, null, null);
	}

	public <T1> double getAttackDamagePercent(Player player, T1 T1) {
		return getAttackDamagePercent(player, T1, null);
	}

	public <T1, T2> double getAttackDamagePercent(Player player, T1 T1, T2 T2) {
		return (1 + ConfigStore.getWeaponLevel(player, this) * 0.05);
	}

	public double getReduceDamage(Player player) {
		return 0d;
	}

	public Skills[] getSkills(Player player) {
		Skills[] skills = new Skills[8];
		LinkedHashSet<Integer> slots = ConfigStore.getSkillSlots(player, this);
		if (slots.size() < 8 || slots.contains(-1)) {
			for (int num = 1; num <= 8; num++)
				ConfigStore.setSkillSlot(player, this, num, num);
			slots = ConfigStore.getSkillSlots(player, this);
		}

		int num = 0;
		for (int slot : slots) {
			num++;
			skills[num - 1] = this.skills[slot - 1];
		}
		return skills;
	}

	public int getSkillSlot(Player player, Skills skill) {
		Skills[] skills = getSkills(player);
		for (int c = 0; c < skills.length; c++)
			if (skills[c] == skill)
				if (c == 7) {
					return c + 1;
				} else
					return c;
		return -1;
	}

	public double getMaxHealth(Player player) {
		return max_health * ConfigStore.getArmorHealth(player, this);
	}

	public float getWalkspeed(Player player) {
		return walkspeed * Math.max(0f,
				Math.min(1.4f, 1f + SpeedRobeItem.getPower(player) - DebuffRunnable.getSlowness(player)));
	}

	public WeaponItems getWeapon() {
		return weapon;
	}

	public String getIdentityName() {
		return identity_name;
	}

	public double getMaxIdentity() {
		return max_identity;
	}

	public List<ItemStack> getJobitems(Player player) {
		final List<ItemStack> items = new ArrayList<ItemStack>();
		for (Skills skill : getSkills(player))
			items.add(skill.getItemStack());
		items.add(7, weapon.getItemStack(ConfigStore.getWeaponLevel(player, this)));
		return items;
	}

	public static Jobs valuOf(String key) {
		for (Jobs job : jobs)
			if (job.getKey().equalsIgnoreCase(key))
				return job;
		return null;
	}

	public static Jobs valueOf(ItemStack i) {
		if (i != null && i.hasItemMeta() && i.getItemMeta().hasLocalizedName())
			for (Jobs job : jobs)
				if (job.getKey().equalsIgnoreCase(i.getItemMeta().getLocalizedName()))
					return job;
		return null;
	}

	public static List<Jobs> values() {
		return jobs;
	}
}
