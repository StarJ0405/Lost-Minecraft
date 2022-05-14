package com.StarJ.LA.Skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.StarJ.LA.Skills.Reaper.DancingofFury;
import com.StarJ.LA.Skills.Reaper.Distortion;
import com.StarJ.LA.Skills.Reaper.Eclipse_Kadencha;
import com.StarJ.LA.Skills.Reaper.LastGrapity;
import com.StarJ.LA.Skills.Reaper.Nightmare;
import com.StarJ.LA.Skills.Reaper.Persona;
import com.StarJ.LA.Skills.Reaper.RageSpear;
import com.StarJ.LA.Skills.Reaper.ShadowDot;
import com.StarJ.LA.Skills.Reaper.ShadowStorm;
import com.StarJ.LA.Skills.Reaper.SpiritCatch;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Stats;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;
import com.StarJ.LA.Systems.Runnable.SkillCoolRunnable;

public abstract class Skills {
	private final static List<Skills> skills = new ArrayList<Skills>();

	// REAPER
	public static Skills Nightmare = new Nightmare();
	public static Skills ShadowDot = new ShadowDot();
	public static Skills SpiritCatch = new SpiritCatch();
	public static Skills RageSpear = new RageSpear();
	public static Skills Distortion = new Distortion();
	public static Skills ShadowStorm = new ShadowStorm();
	public static Skills LastGrapity = new LastGrapity();
	public static Skills DancingofFury = new DancingofFury();
	public static Skills Eclipse_Kadencha = new Eclipse_Kadencha();
	public static Skills Persona = new Persona();

	//

	private final String key;
	private final String displayname;
	private final List<String> lore;
	private final double cooldown;
	private final ChatColor color;

	public Skills(String key, String displayname, double cooldown, ChatColor color) {
		skills.add(this);
		this.key = key;
		this.displayname = displayname;
		this.lore = new ArrayList<String>();
		this.cooldown = cooldown;
		this.color = color;
	}

	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color + getDisplayname());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack getInfoItemStack() {
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color + getDisplayname());
		List<String> lore = new ArrayList<String>();
		lore.addAll(this.lore);
		lore.add(ChatColor.WHITE + "클릭시 슬롯 변경");
		lore.add(ChatColor.WHITE + "우클릭시 전체 스킬 슬롯 초기화");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack getComboItemStack() {
		ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color + getDisplayname());
		meta.setLore(lore);
		meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
		return item;
	}

	public ItemStack getBuffItemStack() {
		ItemStack item = new ItemStack(Material.END_CRYSTAL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color + getDisplayname());
		meta.setLore(lore);
		meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack getCoolItemStack() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color + getDisplayname());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public abstract Vector getHitBox();

	public boolean Use(Player player, int slot) {
		if (HashMapStore.isSkillStop(player))
			return true;// 종료
		double cool = ConfigStore.getSkillCooldown(player, ConfigStore.getJob(player), this);
		if (cool > 0) {
			player.sendMessage(this.getDisplayname() + ChatColor.AQUA + " : " + cool);
			return true; // 종료
		} else
			SkillCoolRunnable.run(player, this, slot);
		ActionBarRunnable.run(player);
		return false; // 진행

	}

	public boolean Hit(Player player, LivingEntity entity, int slot) {
		return false;// true=end/false=pass
	}

	public boolean Hit(Player player, Block block, int slot) {
		return false;// true=end/false=pass
	}

	public boolean Attack(Player att) {
		return false;
	}

	public boolean Attacked(Player vic) {
		return false;
	}

	public boolean Attacked(Player vic, Player att) {
		return false;
	}

	public void comboEnd(Player player, int slot) {
		if (slot >= 0 && ConfigStore.getPlayerStatus(player))
			player.getInventory().setItem(slot, getCoolItemStack());
	}

	public void End(Player player, int slot) {
		if (slot >= 0 && ConfigStore.getPlayerStatus(player))
			player.getInventory().setItem(slot, getItemStack());
	}

	public void BuffEnd(Player player, int slot) {
		if (slot >= 0 && ConfigStore.getPlayerStatus(player))
			player.getInventory().setItem(slot, getCoolItemStack());
		BuffRunnable.cancel(player, this);
	}

	public String getKey() {
		return key;
	}

	public String getDisplayname() {
		return displayname;
	}

	public List<String> getLore() {
		return lore;
	}

	public double getCooldown(Player player) {
		return cooldown * Stats.Speed.getStatPercent(player);
	}

	public void addIdentity(Player player, double identity) {
		Jobs job = ConfigStore.getJob(player);
		double now = HashMapStore.getIdentity(player);
		if (now < job.getMaxIdentity()) {
			now = now + identity - (job != null ? job.getWeapon().getIdentity() : 0);
			if (now < 0)
				now = 0;
			HashMapStore.setIdentity(player, now);
		} else
			HashMapStore.setIdentity(player, now);
	}

	public static Skills valueof(String key) {
		for (Skills skill : values())
			if (skill.getKey().equals(key))
				return skill;
		return null;
	}

	public static List<Skills> values() {
		return skills;
	}

}
