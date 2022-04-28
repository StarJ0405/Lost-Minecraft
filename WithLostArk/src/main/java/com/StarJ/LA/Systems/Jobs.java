package com.StarJ.LA.Systems;

import java.util.ArrayList;
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
	private final Skills skill1;
	private final Skills skill2;
	private final Skills skill3;
	private final Skills skill4;
	private final Skills skill5;
	private final Skills skill6;
	private final Skills skill7;
	private final Skills skill8;
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
		this.skill1 = skill1;
		this.skill2 = skill2;
		this.skill3 = skill3;
		this.skill4 = skill4;
		this.skill5 = skill5;
		this.skill6 = skill6;
		this.skill7 = skill7;
		this.skill8 = skill8;
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

	public List<Skills> getSkills() {
		List<Skills> skills = new ArrayList<Skills>();
		skills.add(skill1);
		skills.add(skill2);
		skills.add(skill3);
		skills.add(skill4);
		skills.add(skill5);
		skills.add(skill6);
		skills.add(skill7);
		skills.add(skill8);
		return skills;
	}

	public double getMaxHealth(Player player) {
		return max_health * ConfigStore.getArmorHealth(player);
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
		for (Skills skill : getSkills())
			items.add(skill.getItemStack());
		items.add(7, weapon.getItemStack(ConfigStore.getWeaponLevel(player)));
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
