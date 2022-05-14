package com.StarJ.LA.Systems;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import com.StarJ.LA.Skills.Skills;

public class HashMapStore {
	private static HashMap<String, BigInteger> home_cool = new HashMap<String, BigInteger>();
	private static HashMap<String, BigInteger> exit_cool = new HashMap<String, BigInteger>();
	private static List<Villages> villages = new ArrayList<Villages>();
	private static HashMap<Location, String> msgs = new HashMap<Location, String>();
	private static HashMap<Location, ShopStores> stores = new HashMap<Location, ShopStores>();
	private static HashMap<String, BigInteger> bifrosts_cool = new HashMap<String, BigInteger>();
	private static HashMap<String, BigInteger> bifrosts_save_cool = new HashMap<String, BigInteger>();
	private static HashMap<String, Villages> bifrosts = new HashMap<String, Villages>();
	private static HashMap<String, BigInteger> bifrost2s_cool = new HashMap<String, BigInteger>();
	private static HashMap<String, BigInteger> bifrost2s_save_cool = new HashMap<String, BigInteger>();
	private static HashMap<String, Villages> bifrost2s = new HashMap<String, Villages>();
	private static HashMap<String, HashMap<String, BigInteger>> coins = new HashMap<String, HashMap<String, BigInteger>>();
	private static HashMap<String, Double> health = new HashMap<String, Double>();
	private static HashMap<String, BukkitTask> actions = new HashMap<String, BukkitTask>();
	private static HashMap<String, Double> identity = new HashMap<String, Double>();
	private static HashMap<String, List<String>> attacks = new HashMap<String, List<String>>(); // 공격
	private static HashMap<String, List<String>> attackeds = new HashMap<String, List<String>>(); // 피격
	private static HashMap<String, Boolean> skill_stop = new HashMap<String, Boolean>(); // 스킬 정지
	private static HashMap<String, EnchantsType> enchant_type = new HashMap<String, EnchantsType>();// 인챈트창
	private static HashMap<String, ItemStack> enchant_item = new HashMap<String, ItemStack>();// 인챈트창
	private static HashMap<String, ItemStack> enchant_book = new HashMap<String, ItemStack>();// 인챈트창
	private static HashMap<UUID, Integer> skill_change_slot = new HashMap<UUID, Integer>();
	private static HashMap<UUID, Long> measure_starttime = new HashMap<UUID, Long>();
	private static HashMap<UUID, Long> measure_end = new HashMap<UUID, Long>();
	private static HashMap<UUID, Double> measure_damage = new HashMap<UUID, Double>();
	private static HashMap<UUID, Integer> measure_damage_count = new HashMap<UUID, Integer>();
	private static HashMap<UUID, Integer> measure_critical_count = new HashMap<UUID, Integer>();

	public static BigInteger getHomeCool(String key) {
		return home_cool.containsKey(key) ? home_cool.get(key) : BigInteger.ZERO;
	}

	public static void setHomeCool(String key, BigInteger value) {
		home_cool.put(key, value);
	}

	public static BigInteger getExitCool(String key) {
		return exit_cool.containsKey(key) ? exit_cool.get(key) : BigInteger.ZERO;
	}

	public static void setExitCool(String key, BigInteger value) {
		exit_cool.put(key, value);
	}

	public static List<Villages> getVillages() {
		return villages;
	}

	public static void addVillages(Villages value) {
		villages.add(value);
	}

	public static boolean removeVillages(String name) {
		for (Villages vil : villages)
			if (vil.getName().equals(name)) {
				villages.remove(vil);
				return true;
			}
		return false;
	}

	public static List<String> getVillageNames() {
		final List<String> list = new ArrayList<String>();
		for (Villages vil : villages)
			list.add(vil.getName());
		return list;
	}

	public static HashMap<Location, String> getMsgs() {
		return msgs;
	}

	public static void setMsgs(Location loc, String msg) {
		msgs.put(loc, msg);
	}

	public static void removeMsgs(Location loc) {
		msgs.remove(loc);
	}

	public static HashMap<Location, ShopStores> getStores() {
		return stores;
	}

	public static void setStores(Location loc, ShopStores store) {
		stores.put(loc, store);
	}

	public static void removeStores(Location loc) {
		if (stores.containsKey(loc))
			stores.remove(loc);
	}

	public static BigInteger getBifrostCool(String key) {
		return bifrosts_cool.containsKey(key) ? bifrosts_cool.get(key) : BigInteger.ZERO;
	}

	public static void setBifrostCool(String key, BigInteger value) {
		bifrosts_cool.put(key, value);
	}

	public static BigInteger getBifrostSaveCool(String key) {
		return bifrosts_save_cool.containsKey(key) ? bifrosts_save_cool.get(key) : BigInteger.ZERO;
	}

	public static void setBifrostSaveCool(String key, BigInteger value) {
		bifrosts_save_cool.put(key, value);
	}

	public static Villages getBifrost(String key) {
		return bifrosts.containsKey(key) ? bifrosts.get(key) : null;
	}

	public static void setBifrost(String key, Villages value) {
		bifrosts.put(key, value);
	}

	public static BigInteger getBifrost2Cool(String key) {
		return bifrost2s_cool.containsKey(key) ? bifrost2s_cool.get(key) : BigInteger.ZERO;
	}

	public static void setBifrost2Cool(String key, BigInteger value) {
		bifrost2s_cool.put(key, value);
	}

	public static BigInteger getBifrost2SaveCool(String key) {
		return bifrost2s_save_cool.containsKey(key) ? bifrost2s_save_cool.get(key) : BigInteger.ZERO;
	}

	public static void setBifrost2SaveCool(String key, BigInteger value) {
		bifrost2s_save_cool.put(key, value);
	}

	public static Villages getBifrost2(String key) {
		return bifrost2s.containsKey(key) ? bifrost2s.get(key) : null;
	}

	public static void setBifrost2(String key, Villages value) {
		bifrost2s.put(key, value);
	}

	public static void setCoin(String key, String name, BigInteger coin) {
		if (!coins.containsKey(key))
			coins.put(key, new HashMap<String, BigInteger>());
		HashMap<String, BigInteger> hs = coins.get(key);
		hs.put(name, coin);
		coins.put(key, hs);
	}

	public static List<String> getCoinList(String key) {
		List<String> list = new ArrayList<String>();
		if (!coins.containsKey(key))
			return list;
		list.addAll(coins.get(key).keySet());
		return list;
	}

	public static BigInteger getCoin(String key, String name) {
		if (!coins.containsKey(key))
			return BigInteger.ZERO;
		HashMap<String, BigInteger> hs = coins.get(key);
		return hs.containsKey(name) ? hs.get(name) : BigInteger.ZERO;
	}

	public static double getHealth(Player player) {
		String key = player.getUniqueId().toString();
		Jobs job = ConfigStore.getJob(player);
		return health.containsKey(key) ? health.get(key) : (job != null ? job.getMaxHealth(player) : 20);
	}

	public static void setHealth(Player player, double health) {
		String key = player.getUniqueId().toString();
		Jobs job = ConfigStore.getJob(player);
		health = Math.ceil(health);
		if (job != null && health > job.getMaxHealth(player)) {
			health = job.getMaxHealth(player);
		}
		HashMapStore.health.put(key, health);
	}

	public static void setActionbar(Player player, BukkitTask task) {
		String key = player.getUniqueId().toString();
		cancelActionbar(player);
		actions.put(key, task);
	}

	public static void cancelActionbar(Player player) {
		String key = player.getUniqueId().toString();
		if (actions.containsKey(key)) {
			BukkitTask pre = actions.get(key);
			if (!pre.isCancelled())
				pre.cancel();
		}
	}

	public static double getIdentity(Player player) {
		String key = player.getUniqueId().toString();
		return identity.containsKey(key) ? identity.get(key) : 0;
	}

	public static void setIdentity(Player player, double identity) {
		String key = player.getUniqueId().toString();
		Jobs job = ConfigStore.getJob(player);
		identity = Math.ceil(identity);
		if (job != null && identity > job.getMaxIdentity())
			identity = job.getMaxIdentity();
		HashMapStore.identity.put(key, identity);
	}

	//
	public static List<String> getAttackList(Player player) {
		String key = player.getUniqueId().toString();
		List<String> list = new ArrayList<String>();
		if (attacks.containsKey(key))
			list.addAll(attacks.get(key));
		return list;
	}

	public static void addAttackList(Player player, Skills skill) {
		String key = player.getUniqueId().toString();
		List<String> list = attacks.containsKey(key) ? attacks.get(key) : new ArrayList<String>();
		list.add(skill.getKey());
		attacks.put(key, list);
	}

	public static void removeAttackList(Player player, Skills skill) {
		String key = player.getUniqueId().toString();
		List<String> list = attacks.containsKey(key) ? attacks.get(key) : new ArrayList<String>();
		if (list.contains(skill.getKey()))
			list.remove(skill.getKey());
		attacks.put(key, list);
	}

	public static List<String> getAttackedList(Player player) {
		String key = player.getUniqueId().toString();
		List<String> list = new ArrayList<String>();
		if (attackeds.containsKey(key))
			list.addAll(attackeds.get(key));
		return list;
	}

	public static void addAttackedList(Player player, Skills skill) {
		String key = player.getUniqueId().toString();
		List<String> list = attackeds.containsKey(key) ? attackeds.get(key) : new ArrayList<String>();
		if (!list.contains(skill.getKey()))
			list.add(skill.getKey());
		attackeds.put(key, list);
	}

	public static void removeAttackedList(Player player, Skills skill) {
		String key = player.getUniqueId().toString();
		List<String> list = attackeds.containsKey(key) ? attackeds.get(key) : new ArrayList<String>();
		if (list.contains(skill.getKey()))
			list.remove(skill.getKey());
		attackeds.put(key, list);
	}

	//
	public static boolean isSkillStop(Player player) {
		String key = player.getUniqueId().toString();
		return skill_stop.containsKey(key) ? skill_stop.get(key) : false;
	}

	public static void setSkillStop(String key, boolean stop) {
		skill_stop.put(key, stop);
	}

	//
	public static EnchantsType getEnchantType(String key) {
		return enchant_type.containsKey(key) ? enchant_type.get(key) : null;
	}

	public static void setEnchantType(String key, EnchantsType type) {
		enchant_type.put(key, type);
	}

	public static ItemStack getEnchantItemStack(String key) {
		return enchant_item.containsKey(key) ? enchant_item.get(key) : null;
	}

	public static void setEnchantItemStack(String key, ItemStack item) {
		enchant_item.put(key, item);
	}

	public static ItemStack getEnchantBook(String key) {
		return enchant_book.containsKey(key) ? enchant_book.get(key) : null;
	}

	public static void setEnchantBook(String key, ItemStack book) {
		enchant_book.put(key, book);
	}

	//
	public static int getSkillChangeSlot(Player player) {
		UUID uuid = player.getUniqueId();
		return skill_change_slot.containsKey(uuid) ? skill_change_slot.get(uuid) : -1;
	}

	public static void setSkillChangeSlot(Player player, int slot) {
		skill_change_slot.put(player.getUniqueId(), slot);
	}

	//
	public static long getMeasureStartTime(Player player) {
		UUID uuid = player.getUniqueId();
		return measure_starttime.containsKey(uuid) ? measure_starttime.get(uuid) : System.currentTimeMillis();
	}

	public static void setMeasureStartTime(Player player) {
		measure_starttime.put(player.getUniqueId(), System.currentTimeMillis());
	}

	public static long getMeasureEndTime(Player player) {
		UUID uuid = player.getUniqueId();
		return measure_end.containsKey(uuid) ? measure_end.get(uuid) : System.currentTimeMillis();
	}

	public static void setMeasureEndTime(Player player) {
		measure_end.put(player.getUniqueId(), System.currentTimeMillis());
	}

	public static double getMeasureDamage(Player player) {
		UUID uuid = player.getUniqueId();
		return measure_damage.containsKey(uuid) ? measure_damage.get(uuid) : 0.0d;
	}

	public static void setMeasureDamage(Player player, double damage) {
		measure_damage.put(player.getUniqueId(), damage);
	}

	public static int getMeasureDamageCount(Player player) {
		UUID uuid = player.getUniqueId();
		return measure_damage_count.containsKey(uuid) ? measure_damage_count.get(uuid) : 0;
	}

	public static void setMeasureDamageCount(Player player, int critical) {
		measure_damage_count.put(player.getUniqueId(), critical);
	}

	public static int getMeasureCriticalCount(Player player) {
		UUID uuid = player.getUniqueId();
		return measure_critical_count.containsKey(uuid) ? measure_critical_count.get(uuid) : 0;
	}

	public static void setMeasureCriticalCount(Player player, int critical) {
		measure_critical_count.put(player.getUniqueId(), critical);
	}
}
