package com.StarJ.LA.Skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import com.StarJ.LA.Core;
import com.StarJ.LA.Skills.Battlemaster_Beginner.EnergyCombustion;
import com.StarJ.LA.Skills.Battlemaster_Beginner.Fascination;
import com.StarJ.LA.Skills.Battlemaster_Beginner.FlashHeatFang;
import com.StarJ.LA.Skills.Battlemaster_Beginner.LightningKick;
import com.StarJ.LA.Skills.Battlemaster_Beginner.MoonFlashKick;
import com.StarJ.LA.Skills.Battlemaster_Beginner.OnesHeart_MomentaryBlow;
import com.StarJ.LA.Skills.Battlemaster_Beginner.RoarofCourage;
import com.StarJ.LA.Skills.Battlemaster_Beginner.SkyShatteringBlow;
import com.StarJ.LA.Skills.Battlemaster_Beginner.SweepingKick;
import com.StarJ.LA.Skills.Battlemaster_Beginner.WindsWhisper;
import com.StarJ.LA.Skills.Reaper_Lunarsound.DancingofFury;
import com.StarJ.LA.Skills.Reaper_Lunarsound.Distortion;
import com.StarJ.LA.Skills.Reaper_Lunarsound.Eclipse_Kadencha;
import com.StarJ.LA.Skills.Reaper_Lunarsound.LastGrapity;
import com.StarJ.LA.Skills.Reaper_Lunarsound.Nightmare;
import com.StarJ.LA.Skills.Reaper_Lunarsound.Persona;
import com.StarJ.LA.Skills.Reaper_Lunarsound.RageSpear;
import com.StarJ.LA.Skills.Reaper_Lunarsound.ShadowDot;
import com.StarJ.LA.Skills.Reaper_Lunarsound.ShadowStorm;
import com.StarJ.LA.Skills.Reaper_Lunarsound.SpiritCatch;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.ShopStores;
import com.StarJ.LA.Systems.Stats;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;
import com.StarJ.LA.Systems.Runnable.ComboCoolRunnable;
import com.StarJ.LA.Systems.Runnable.DebuffRunnable;
import com.StarJ.LA.Systems.Runnable.DebuffRunnable.DebuffType;
import com.StarJ.LA.Systems.Runnable.HoldingRunnable;
import com.StarJ.LA.Systems.Runnable.SkillCoolRunnable;

public abstract class Skills {
	private final static List<Skills> skills = new ArrayList<Skills>();

	// REAPER_LUNAR
	public static Nightmare Nightmare = new Nightmare();
	public static ShadowDot ShadowDot = new ShadowDot();
	public static SpiritCatch SpiritCatch = new SpiritCatch();
	public static RageSpear RageSpear = new RageSpear();
	public static Distortion Distortion = new Distortion();
	public static ShadowStorm ShadowStorm = new ShadowStorm();
	public static LastGrapity LastGrapity = new LastGrapity();
	public static DancingofFury DancingofFury = new DancingofFury();
	public static Eclipse_Kadencha Eclipse_Kadencha = new Eclipse_Kadencha();
	public static Persona Persona = new Persona();

	// BATTLEMASTER_BEGINNER
	public static RoarofCourage RoarofCourage = new RoarofCourage();
	public static WindsWhisper WindsWhisper = new WindsWhisper();
	public static SweepingKick SweepingKick = new SweepingKick();
	public static MoonFlashKick MoonFlashKick = new MoonFlashKick();
	public static FlashHeatFang FlashHeatFang = new FlashHeatFang();
	public static SkyShatteringBlow SkyShatteringBlow = new SkyShatteringBlow();
	public static LightningKick LightningKick = new LightningKick();
	public static EnergyCombustion EnergyCombustion = new EnergyCombustion();
	public static OnesHeart_MomentaryBlow OnesHeart_MomentaryBlow = new OnesHeart_MomentaryBlow();
	public static Fascination Fascination = new Fascination();

	// BLADE_BURST
	
	private final String key;
	private final String displayname;
	private final List<String> lore;
	protected final double cooldown;
	private final double knockdown;
	private final ChatColor color;
	private final AttackType[] types;

	public Skills(String key, String displayname, double cooldown, double knockdown, ChatColor color, String... lore) {
		this(key, displayname, cooldown, knockdown, color, new AttackType[0], lore);
	}

	public Skills(String key, String displayname, double cooldown, double knockdown, ChatColor color,
			AttackType[] types, String... lore) {
		skills.add(this);
		this.key = key;
		this.displayname = displayname;
		this.lore = new ArrayList<String>();
		for (String l : lore)
			this.lore.add(ChatColor.WHITE + l);
		if (types.length == 1) {
			this.lore.add(ChatColor.GREEN + "공격타입 : " + types[0].getName());
		} else if (types.length == 2)
			this.lore.add(ChatColor.GREEN + "공격타입 : 백/헤드 어택");
		this.lore.add(ChatColor.GRAY + "재사용 대기시간 : " + cooldown + "초");
		this.cooldown = cooldown;
		this.knockdown = knockdown;
		this.color = color;
		this.types = types;
	}

	public AttackType[] getAttackTypes() {
		return types;
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
		lore.add(ChatColor.GRAY + "클릭시 슬롯 변경");
		lore.add(ChatColor.GRAY + "우클릭시 전체 스킬 슬롯 초기화");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public void damage(Player att, LivingEntity le, double damage) {
		damage(att, le, AttackType.getAttackType(le, att), damage, 0, this.knockdown);
	}

	public void damage(Player att, LivingEntity le, AttackType now, double damage) {
		damage(att, le, now, damage, 0, this.knockdown);
	}

	public void damage(Player att, LivingEntity le, double damage, double identity) {
		damage(att, le, AttackType.getAttackType(le, att), damage, identity, this.knockdown);
	}

	public void damage(Player att, LivingEntity le, double damage, double identity, double kncokdown) {
		damage(att, le, AttackType.getAttackType(le, att), damage, identity, this.knockdown);
	}

	public void damage(Player att, LivingEntity le, AttackType now, double damage, double identity, double kncokdown) {
		double now_identity = HashMapStore.getIdentity(att);
		if (now != null && Arrays.asList(getAttackTypes()).contains(now)) {
			double critical = Stats.Critical.getImportantStat(att);
			Stats.Critical.setImportantStat(att, critical + now.getCritical());
			int ndt = le.getNoDamageTicks();
			le.setNoDamageTicks(0);
			le.damage(damage * now.getDamageMulti(), att);
			le.setNoDamageTicks(ndt);
			Stats.Critical.setImportantStat(att, critical);
		} else {
			int ndt = le.getNoDamageTicks();
			le.setNoDamageTicks(0);
			le.damage(damage, att);
			le.setNoDamageTicks(ndt);
		}
		HashMapStore.setIdentity(att, now_identity + identity);
		int a;// 무력관련 추가
	}

	public ItemStack getSneakingItemStack() {
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color + getDisplayname());
		meta.setLore(lore);
		meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS);
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
		if (DebuffRunnable.hasDebuff(player, DebuffType.Silence)) {
			player.sendMessage(ChatColor.RED + "침묵 중에는 스킬 사용이 불가능합니다.");
			return true;// 종료
		}
		if (ComboCoolRunnable.hasCombo(player, this))
			return false;// 진행
		double cool = ConfigStore.getSkillCooldown(player, ConfigStore.getJob(player), this);
		if (cool > 0 && !player.getGameMode().equals(GameMode.CREATIVE)) {
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

	public boolean Attack(Player att, LivingEntity vic) {
		return false;
	}

	public boolean Attacked(Player vic, LivingEntity att) {
		return false;
	}

	public boolean Attacked(Player vic, Player att) {
		return false;
	}

	public void Holding(Player player, int times) {

	}

	public void HoldingSucceed(Player player, int slot) {
		if (slot >= 0 && ConfigStore.getPlayerStatus(player)) {
			double cool = ConfigStore.getSkillCooldown(player, ConfigStore.getJob(player), this);
			if (cool > 0) {
				player.getInventory().setItem(slot, getCoolItemStack());
			} else
				player.getInventory().setItem(slot, getItemStack());
		}
		HoldingRunnable.cancel(player, this);
	}

	public void HoldingFail(Player player, int slot) {
		if (slot >= 0 && ConfigStore.getPlayerStatus(player)) {
			double cool = ConfigStore.getSkillCooldown(player, ConfigStore.getJob(player), this);
			if (cool > 0) {
				player.getInventory().setItem(slot, getCoolItemStack());
			} else
				player.getInventory().setItem(slot, getItemStack());
		}
		HoldingRunnable.cancel(player, this);
	}

	public void comboEnd(Player player, int slot) {
		ComboCoolRunnable.EndCombo(player, this);
		if (slot >= 0 && ConfigStore.getPlayerStatus(player)) {
			double cool = ConfigStore.getSkillCooldown(player, ConfigStore.getJob(player), this);
			if (cool > 0) {
				player.getInventory().setItem(slot, getCoolItemStack());
			} else
				player.getInventory().setItem(slot, getItemStack());
		}
	}

	public void End(Player player, int slot) {
		if (slot >= 0 && ConfigStore.getPlayerStatus(player))
			player.getInventory().setItem(slot, getItemStack());
	}

	public void BuffEnd(Player player, int slot) {
		if (slot >= 0 && ConfigStore.getPlayerStatus(player)) {
			double cool = ConfigStore.getSkillCooldown(player, ConfigStore.getJob(player), this);
			if (cool > 0) {
				player.getInventory().setItem(slot, getCoolItemStack());
			} else
				player.getInventory().setItem(slot, getItemStack());
		}
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
		return player.getGameMode().equals(GameMode.CREATIVE) ? 0 : cooldown * Stats.Speed.getStatPercent(player);
	}

	public int getComboDuration() {
		return (int) (this.cooldown * 20 / 2);
	}

	public static boolean canAttack(Player player, Entity et) {
		if (!player.getUniqueId().equals(et.getUniqueId()))
			if (et != null && !et.isDead() && et instanceof LivingEntity
					&& (!(et instanceof Player) || (ConfigStore.getPlayerStatus((Player) et)
							&& ConfigStore.getPVP(player) && ConfigStore.getPVP((Player) et)))) {
				if (et.hasMetadata("key"))
					for (MetadataValue v : et.getMetadata("key"))
						if (v.getOwningPlugin().equals(Core.getCore())
								&& !v.asString().equals(ShopStores.Training.name()))
							return false;
				return true;
			}
		return false;
	}

//	public static void addIdentity(Player player, double identity) {
//		Jobs job = ConfigStore.getJob(player);
//		double now = HashMapStore.getIdentity(player);
//		if (now < job.getMaxIdentity()) {
//			now = now + identity - (job != null ? job.getWeapon().getIdentity() : 0);
//			if (now < 0)
//				now = 0;
//			HashMapStore.setIdentity(player, now);
//		} else
//			HashMapStore.setIdentity(player, now);
//	}

	public static Skills valueof(String key) {
		for (Skills skill : values())
			if (skill.getKey().equals(key))
				return skill;
		return null;
	}

	public static List<Skills> values() {
		return skills;
	}

	public enum AttackType {
		BACK("백 어택", 1.05, 0.1, 0), HEAD("헤드 어택", 1.2, 0, 0.1)
		//
		;

		private final double damage_multi;
		private final double critical;
		private final double knockdown;
		private final String name;

		private AttackType(String name, double damage_multi, double critical, double knockdown) {
			this.name = name;
			this.damage_multi = damage_multi;
			this.critical = critical;
			this.knockdown = knockdown;
		}

		public String getName() {
			return name;
		}

		public double getDamageMulti() {
			return damage_multi;
		}

		public double getCritical() {
			return critical;
		}

		public double getKnockdown() {
			return knockdown;
		}

		public static AttackType getAttackType(Entity vic_e, Entity att_e) {
			return getAttackType(vic_e, att_e.getLocation());
		}

		public static AttackType getAttackType(Entity vic_e, Location att_loc) {
			Vector vic_dir = vic_e.getLocation().getDirection().clone().setY(0).normalize();
			Vector att_dir = vic_e.getLocation().clone().subtract(att_loc).toVector().setY(0).normalize();
			double angle = vic_dir.angle(att_dir);
			if (angle > 2.4) {
				// head
				return HEAD;
			} else if (angle < 0.65) {
				// back
				return BACK;
			} else
				return null;
		}
	}
}
