package com.StarJ.LA.Systems;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Items.WeaponItems;
import com.StarJ.LA.Skills.Skills;

public class Jobs {
	private final static List<Jobs> jobs = new ArrayList<Jobs>();
	//
	public final static Jobs Reaper = new Jobs("luan_soul_reaper", ChatColor.DARK_RED + "달소리퍼", Skills.Nightmare,
			Skills.ShadowDot, Skills.SpiritCatch, Skills.RageSpear, Skills.Distortion, Skills.ShadowStorm,
			Skills.LastGrapity, Skills.DancingofFury, Skills.Eclipse_Kadencha, Skills.Persona, WeaponItems.reaper, 500,
			0.14f, ChatColor.DARK_PURPLE + "페르소나", 1000, true);
	//
	private final String key;
	private final String displayname;
	private final Skills[] skills = new Skills[8];
	private final Skills specialarts;
	private final Skills identity;
	private final boolean identity_percent;
	private final WeaponItems weapon;
	private final double max_health;
	private final float walkspeed;
	private final String identity_name;
	private final double max_identity;

	public Jobs(String key, String displayname, Skills skill1, Skills skill2, Skills skill3, Skills skill4,
			Skills skill5, Skills skill6, Skills skill7, Skills skill8, Skills awakening, Skills identity,
			WeaponItems weapon, double max_health, float walkspeed, String identity_name, double max_identity,
			boolean identity_percent) {
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
		this.identity_percent = identity_percent;
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

	public boolean isIdentityPercent() {
		return identity_percent;
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

	public double getMaxHealth(Player player) {
		return max_health * ConfigStore.getArmorHealth(player, this);
	}

	public float getWalkspeed() {
		return walkspeed;
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
