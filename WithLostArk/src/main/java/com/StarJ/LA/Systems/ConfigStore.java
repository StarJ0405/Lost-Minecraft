package com.StarJ.LA.Systems;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.StarJ.LA.Core;
import com.StarJ.LA.Commands.MsgCommand;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.JewerlyItems;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.Fishes.Biomes;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;

public class ConfigStore {
	//
	public static int getBasicsLevel(Player player, Basics basic) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null && fc.isConfigurationSection("basics")) {
				ConfigurationSection cs = fc.getConfigurationSection("basics");
				if (cs.isInt(basic.name() + "LEVEL"))
					return cs.getInt(basic.name() + "LEVEL");
			}

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public static void setBasicsLevel(Player player, Basics basic, int level) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null) {
				ConfigurationSection cs = fc.isConfigurationSection("basics") ? fc.getConfigurationSection("basics")
						: fc.createSection("basics");
				cs.set(basic.name() + "LEVEL", level);
				fc.set("basics", cs);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static int getBasicsEXP(Player player, Basics basic) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null && fc.isConfigurationSection("basics")) {
				ConfigurationSection cs = fc.getConfigurationSection("basics");
				if (cs.isInt(basic.name() + "EXP"))
					return cs.getInt(basic.name() + "EXP");
			}

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setBasicsEXP(Player player, Basics basic, int exp) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null) {
				ConfigurationSection cs = fc.isConfigurationSection("basics") ? fc.getConfigurationSection("basics")
						: fc.createSection("basics");
				cs.set(basic.name() + "EXP", exp);
				fc.set("basics", cs);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	//

	public static double getSkillCooldown(Player player, Skills skill) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (skill != null && fc.isConfigurationSection("skills")) {
				ConfigurationSection cs = fc.getConfigurationSection("skills");
				if (cs.isLong(skill.getKey()))
					return (cs.getLong(skill.getKey()) - System.currentTimeMillis()) / 1000.0d;
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setSkillCooldown(Player player, Skills skill, int cool) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (skill != null) {
				ConfigurationSection cs = fc.isConfigurationSection("skills") ? fc.getConfigurationSection("skills")
						: fc.createSection("skills");
				cs.set(skill.getKey(), System.currentTimeMillis() + cool);
				fc.set("skills", cs);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static List<JewerlyItems> getJewerlyItems(Player player) {
		return getJewerlyItems(player, getJob(player));
	}

	public static List<JewerlyItems> getJewerlyItems(Player player, Jobs job) {
		final List<JewerlyItems> list = new ArrayList<JewerlyItems>();
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (job != null) {
				if (fc.isList(job.getKey()))
					for (String key : fc.getStringList(job.getKey())) {
						JewerlyItems jewerly = JewerlyItems.valueOf(key);
						if (jewerly != null)
							list.add(jewerly);
					}
			}
			for (int c = list.size(); c < 24; c++)
				list.add(Items.zero);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void setJewerlyItems(Player player, List<JewerlyItems> jewerlys) {
		setJewerlyItems(player, getJob(player), jewerlys);
	}

	public static void setJewerlyItems(Player player, Jobs job, List<JewerlyItems> jewerlys) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (job != null) {
				final List<String> list = new ArrayList<String>();
				for (JewerlyItems jewerly : jewerlys) {
					list.add(jewerly != null ? jewerly.getKey() : Items.zero.getKey());
				}
				fc.set(job.getKey(), list);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static int getWeaponLevel(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isInt("WeaponLevel"))
				return fc.getInt("WeaponLevel");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setWeaponLevel(Player player, int level) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("WeaponLevel", level);
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static double getArmorHealth(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			double amount = 1;
			if (fc.isInt("HelmetLevel"))
				amount += fc.getInt("HelmetLevel") * Items.helmet.getPercent();
			if (fc.isInt("ChestplateLevel"))
				amount += fc.getInt("ChestplateLevel") * Items.chestplate.getPercent();
			if (fc.isInt("LeggingsLevel"))
				amount += fc.getInt("LeggingsLevel") * Items.leggings.getPercent();
			if (fc.isInt("BootsLevel"))
				amount += fc.getInt("BootsLevel") * Items.boots.getPercent();
			return amount;
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public static int getHelmetLevel(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isInt("HelmetLevel"))
				return fc.getInt("HelmetLevel");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setHelmetLevel(Player player, int level) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("HelmetLevel", level);
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static int getChestplateLevel(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isInt("ChestplateLevel"))
				return fc.getInt("ChestplateLevel");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setChestplateLevel(Player player, int level) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("ChestplateLevel", level);
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static int getLeggingsLevel(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isInt("LeggingsLevel"))
				return fc.getInt("LeggingsLevel");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setLeggingsLevel(Player player, int level) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("LeggingsLevel", level);
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static int getBootsLevel(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isInt("BootsLevel"))
				return fc.getInt("BootsLevel");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setBootsLevel(Player player, int level) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("BootsLevel", level);
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	//
	public static boolean getPVP(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isBoolean("pvp")) {
				return fc.getBoolean("pvp");
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void changePVP(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			boolean pvp = fc.isBoolean("pvp") ? !fc.getBoolean("pvp") : false;
			fc.set("pvp", pvp);
			if (pvp) {
				player.sendMessage(ChatColor.RED + "PVP 모드를 활성화 합니다.");
			} else
				player.sendMessage(ChatColor.GREEN + "PVP 모드를 해제합니다.");
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static Jobs getJob(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isString("job")) {
				return Jobs.valuOf(fc.getString("job"));
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setJob(Player player, Jobs job) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("job", job.getKey());
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static boolean getPlayerStatus(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isBoolean("status")) {
				return fc.getBoolean("status");
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return false; // 생활
	}

	public static void confirmIdentity(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isDouble("identity"))
				HashMapStore.setIdentity(player, fc.getDouble("identity"));
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void setIdentity(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("identity", HashMapStore.getIdentity(player));
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void confirmHealth(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isDouble("health"))
				HashMapStore.setHealth(player, fc.getDouble("health"));
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void setHealth(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("health", HashMapStore.getHealth(player));
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void confirmFoodLevel(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isInt("food"))
				player.setFoodLevel(fc.getInt("food"));
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void setFoodLevel(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("food", player.getFoodLevel());
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void changePlayerStatus(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			boolean status = fc.isBoolean("status") ? !fc.getBoolean("status") : false;
			Jobs job = getJob(player);
			PlayerInventory inv = player.getInventory();
			ItemStack air = new ItemStack(Material.AIR);
			if (job != null)
				if (status) {
//					ConfigurationSection cs = fc.isConfigurationSection("hotbar") ? fc.getConfigurationSection("hotbar")
//							: fc.createSection("hotbar");
//					cs.set("offhand", inv.getItemInOffHand());
//					cs.set("helmet", inv.getHelmet());
//					cs.set("chestplate", inv.getChestplate());
//					cs.set("leggings", inv.getLeggings());
//					cs.set("boots", inv.getBoots());
					List<ItemStack> saves = new ArrayList<ItemStack>();
					saves.addAll(Arrays.asList(player.getInventory().getContents()));

					List<ItemStack> items = job.getJobitems(player);
					for (int c = 0; c < 9; c++) {
						if (c < items.size())
							inv.setItem(c, items.get(c));
					}
					ItemStack empty = getEmpty();
					for (int c = 9; c < 36; c++)
						inv.setItem(c, empty);
					inv.setHelmet(air);
					inv.setChestplate(air);
					inv.setLeggings(air);
					inv.setBoots(air);
					fc.set("change_save", saves);
					//
					player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100D);
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(0D);
					player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed());
					inv.setItemInOffHand(air);
					//

					inv.setHeldItemSlot(8);
					double max = job != null ? job.getMaxHealth(player) : 20;
					double health = fc.isDouble("health") ? fc.getDouble("health") : max;

					double per = health / max * 100;
					if (per <= 1 && per > 0) {
						per = 1;
					} else if (per >= 99 && per < 100) {
						per = 99;
					}
					if (per > 100) {
						per = 100;
					}
					if (per <= 0)
						if (health > 0) {
							per = 1;
						} else
							per = 0;
					HashMapStore.setHealth(player, health);
					player.setHealthScale(100);
					player.setHealth(per);
					fc.set("food", player.getFoodLevel());
					player.setFoodLevel(19);
					player.sendMessage(ChatColor.RED + "전투 모드로 변경하였습니다.");
					player.getInventory().setHeldItemSlot(7);
					ActionBarRunnable.run(player);
				} else {
					if (fc.isList("change_save")) {
//						ConfigurationSection cs = fc.getConfigurationSection("hotbar");
//						for (int c = 0; c < 9; c++)
//							inv.setItem(c, cs.isItemStack(c + "") ? cs.getItemStack(c + "") : air);
//						//
//						inv.setItemInOffHand(cs.isItemStack("offhand") ? cs.getItemStack("offhand") : air);
//						//
//						inv.setHelmet(cs.isItemStack("helmet") ? cs.getItemStack("helmet") : air);
//						inv.setChestplate(cs.isItemStack("chestplate") ? cs.getItemStack("chestplate") : air);
//						inv.setLeggings(cs.isItemStack("leggings") ? cs.getItemStack("leggings") : air);
//						inv.setBoots(cs.isItemStack("boots") ? cs.getItemStack("boots") : air);
						@SuppressWarnings("unchecked")
						List<ItemStack> saves = (List<ItemStack>) fc.getList("change_save");
						inv.setContents(saves.toArray(ItemStack[]::new));
						fc.set("change_save", null);
					}
					fc.set("health", HashMapStore.getHealth(player));
					player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0D);
					player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue((double) 0.1f);
					player.setHealthScale(20D);
					player.setHealth(20D);
					if (fc.isInt("food"))
						player.setFoodLevel(fc.getInt("food"));
					Effects.sendActionBar(player, "");
					player.sendMessage(ChatColor.GREEN + "생활 모드로 변경하였습니다.");
					HashMapStore.cancelActionbar(player);
				}

			fc.set("status", status);
			ScoreboarStore.confirmTeam(player);
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static ItemStack getEmpty() {
		ItemStack i = new ItemStack(Material.BROWN_STAINED_GLASS_PANE);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "");
		meta.addItemFlags(ItemFlag.values());
		meta.setLocalizedName(new Random().nextDouble() + "");
		i.setItemMeta(meta);
		return i;
	}

	public static List<ItemStack> getPlayerHotbarItems(Player player) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isConfigurationSection("hotbar")) {
				ConfigurationSection cs = fc.getConfigurationSection("hotbar");
				for (int c = 0; c < 9; c++)
					if (cs.isItemStack(c + "")) {
						items.add(cs.getItemStack(c + ""));
					}
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return items;
	}

	public static void SavePlayerAdvancement(Player player) {
		try {
			File file = new File("plugins/COIN/Players/Advancement/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players/Advancement");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			//
			{
				ConfigurationSection cs = fc.isConfigurationSection("advancement")
						? fc.getConfigurationSection("advancement")
						: fc.createSection("advancement");
				for (CustomAdvancements cad : CustomAdvancements.values())
					cs.set(cad.getKey(), cad.has(player));
				fc.set("advancement", cs);
			}
			{
				ConfigurationSection cs = fc.isConfigurationSection("condition")
						? fc.getConfigurationSection("condition")
						: fc.createSection("condition");
				cs.set("caught", AdvancementStore.getCaught(player));
				{
					ConfigurationSection ccs = cs.isConfigurationSection("caught_fishes")
							? cs.getConfigurationSection("caught_fishes")
							: cs.createSection("caught_fishes");
					for (Fishes fish : Fishes.values())
						ccs.set(fish.getKey(), AdvancementStore.getCaughtFishes(player, fish));
					cs.set("caught_fishes", ccs);
				}
				{
					ConfigurationSection ccs = cs.isConfigurationSection("caught_biomes")
							? cs.getConfigurationSection("caught_biomes")
							: cs.createSection("caught_biomes");
					for (Biomes biome : Biomes.values())
						ccs.set(biome.name(), AdvancementStore.getCaughtBiomes(player, biome));
					cs.set("caught_biomes", ccs);
				}
				fc.set("condition", cs);
			}
			//
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void LoadPlayerAdvancement(Player player) {
		try {
			File file = new File("plugins/COIN/Players/Advancement/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players/Advancement");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			//
			if (fc.isConfigurationSection("advancement")) {
				ConfigurationSection cs = fc.getConfigurationSection("advancement");
				for (CustomAdvancements cad : CustomAdvancements.values())
					if (cs.isBoolean(cad.getKey()) && cs.getBoolean(cad.getKey()))
						cad.grant(player);
			}
			if (fc.isConfigurationSection("condition")) {
				ConfigurationSection cs = fc.getConfigurationSection("condition");
				if (cs.isInt("caught"))
					AdvancementStore.setCaught(player, cs.getInt("caught"));
				if (cs.isConfigurationSection("caught_fishes")) {
					ConfigurationSection ccs = cs.getConfigurationSection("caught_fishes");
					for (Fishes fish : Fishes.values())
						if (ccs.isInt(fish.getKey()))
							AdvancementStore.setCaughtFishes(player, fish, ccs.getInt(fish.getKey()));
				}
				if (cs.isConfigurationSection("caught_biomes")) {
					ConfigurationSection ccs = cs.createSection("caught_biomes");
					for (Biomes biome : Biomes.values())
						if (ccs.isInt(biome.name()))
							AdvancementStore.setCaughtBiomes(player, biome, ccs.getInt(biome.name()));
				}
			}
			//
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void SavePlayerAllData(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			String key = player.getUniqueId().toString();
			//
			fc.set("Name", player.getName());
			fc.set("home", HashMapStore.getHomeCool(key));
			fc.set("exit", HashMapStore.getExitCool(key));
			//
			Villages bifrost = HashMapStore.getBifrost(key);
			if (bifrost != null)
				fc.set("bifrost", bifrost.getLocation());
			fc.set("bifrost_cool", HashMapStore.getBifrostCool(key));
			fc.set("bifrost_save", HashMapStore.getBifrostSaveCool(key));
			Villages bifrost2 = HashMapStore.getBifrost2(key);
			if (bifrost2 != null)
				fc.set("bifrost2", bifrost2.getLocation());
			fc.set("bifrost2_cool", HashMapStore.getBifrost2Cool(key));
			fc.set("bifrost2_save", HashMapStore.getBifrost2SaveCool(key));
			//
			{
				ConfigurationSection cs = fc.isConfigurationSection("coins") ? fc.getConfigurationSection("coins")
						: fc.createSection("coins");
				List<String> list = HashMapStore.getCoinList(key);
				for (String name : list)
					cs.set(name, HashMapStore.getCoin(key, name));
				fc.set("coins", cs);
			}
			//
			fc.set("gambler", player.hasPermission("gambler"));
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void LoadPlayerAllData(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			String key = player.getUniqueId().toString();
			//
			if (fc.isLong("home"))
				HashMapStore.setExitCool(key, BigInteger.valueOf(fc.getLong("home")));
			if (fc.isLong("exit"))
				HashMapStore.setExitCool(key, BigInteger.valueOf(fc.getLong("exit")));
			//
			if (fc.isLocation("bifrost"))
				HashMapStore.setBifrost(key, new Villages("비프로스트", fc.getLocation("bifrost")));
			if (fc.isLong("bifrost_cool"))
				HashMapStore.setBifrostCool(key, BigInteger.valueOf(fc.getLong("bifrost_cool")));
			if (fc.isLong("bifrost_save"))
				HashMapStore.setBifrostSaveCool(key, BigInteger.valueOf(fc.getLong("bifrost_save")));
			if (fc.isLocation("bifrost2"))
				HashMapStore.setBifrost2(key, new Villages("비프로스트", fc.getLocation("bifrost2")));
			if (fc.isLong("bifrost2_cool"))
				HashMapStore.setBifrost2Cool(key, BigInteger.valueOf(fc.getLong("bifrost2_cool")));
			if (fc.isLong("bifrost2_save"))
				HashMapStore.setBifrost2SaveCool(key, BigInteger.valueOf(fc.getLong("bifrost2_save")));
			//
			if (fc.isBoolean("gambler")) {
				player.addAttachment(Core.getCore()).setPermission("gambler", fc.getBoolean("gambler"));
			}

			//
			if (fc.isConfigurationSection("coins")) {
				ConfigurationSection cs = fc.getConfigurationSection("coins");
				for (String name : cs.getKeys(false))
					HashMapStore.setCoin(key, name, BigInteger.valueOf(cs.getLong(name)));
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void setPlayerMoney(Player player, BigInteger money) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("돈", money.toString());
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static BigInteger getPlayerMoney(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isString("돈"))
				return new BigInteger(fc.getString("돈"));
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return BigInteger.ZERO;
	}

	public static void setPlayerInventory(Player player, List<ItemStack> items) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("items", items);
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<ItemStack> getPlayerInventory(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isList("items"))
				return (List<ItemStack>) fc.getList("items");

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return new ArrayList<ItemStack>();
	}

	public static void setPlayerInventory(Player player, int size) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("size", size);
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static int getPlayerInventorySize(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + ".yml");
			File loc = new File("plugins/COIN/Players");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isInt("size")) {
				return fc.getInt("size");
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void Save() {
		try {
			File file = new File("plugins/COIN/Config.yml");
			File loc = new File("plugins/COIN");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			{
				ConfigurationSection cs = fc.isConfigurationSection("homes") ? fc.getConfigurationSection("homes")
						: fc.createSection("homes");
				for (Villages village : HashMapStore.getVillages())
					cs.set(village.getName(), village.getLocation());
				fc.set("homes", cs);
			}
			{
				if (fc.isConfigurationSection("shops"))
					fc.set("shops", null);
				ConfigurationSection cs = fc.createSection("shops");
				for (LivingEntity le : Bukkit.getWorlds().get(0).getLivingEntities())
					if (le instanceof Villager) {
						if (le.hasMetadata("key")) {
							for (MetadataValue v : le.getMetadata("key"))
								if (v.getOwningPlugin().equals(Core.getCore())) {
									le.remove();
									break;
								}
						}
						if (!le.hasAI()) {
							le.remove();
						}
						if (le.getCustomName() != null || le.isCustomNameVisible())
							le.remove();
					}
				HashMap<Location, ShopStores> hs = HashMapStore.getStores();
				for (Location l : hs.keySet()) {
					String key = l.getWorld().getName() + ", " + l.getX() + ", " + l.getY() + ", " + l.getZ() + ", "
							+ l.getYaw() + ", " + l.getPitch();
					key = key.replace(".", "^");
					cs.set(key, hs.get(l).name());
				}
				fc.set("shops", cs);

			}
			{
				if (fc.isConfigurationSection("msgs"))
					fc.set("msgs", null);
				ConfigurationSection cs = fc.createSection("msgs");
				for (Entity et : Bukkit.getWorlds().get(0).getEntities())
					if (et instanceof ArmorStand) {
						if (et.hasMetadata("key"))
							for (MetadataValue v : et.getMetadata("key"))
								if (v.getOwningPlugin().equals(Core.getCore())) {
									et.remove();
									break;
								}
						if (et.getCustomName() != null || et.isCustomNameVisible())
							et.remove();
					}
				HashMap<Location, String> hs = HashMapStore.getMsgs();
				for (Location l : hs.keySet()) {
					String key = l.getWorld().getName() + ", " + l.getX() + ", " + l.getY() + ", " + l.getZ() + ", "
							+ l.getYaw() + ", " + l.getPitch();
					key = key.replace(".", "^");
					cs.set(key, hs.get(l));
				}
				fc.set("msgs", cs);
			}
			{
				List<String> worlds = new ArrayList<String>();
				for (World world : Bukkit.getWorlds())
					worlds.add(world.getName());
				fc.set("worlds", worlds);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void Confirm() {
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					File file = new File("plugins/COIN/Config.yml");
					File loc = new File("plugins/COIN");
					FileConfiguration fc = new YamlConfiguration();
					if (!file.exists()) {
						loc.mkdirs();
						file.createNewFile();
					}
					fc.load(file);
					{
						for (LivingEntity le : Bukkit.getWorlds().get(0).getLivingEntities())
							if (le instanceof Villager) {
								if (le.hasMetadata("key")) {
									for (MetadataValue v : le.getMetadata("key"))
										if (v.getOwningPlugin().equals(Core.getCore())) {
											le.remove();
											break;
										}
								}
								if (!le.hasAI()) {
									le.remove();
								}
								if (le.getCustomName() != null || le.isCustomNameVisible())
									le.remove();
							} else if (le instanceof ArmorStand) {
								if (le.hasMetadata("key")) {
									for (MetadataValue v : le.getMetadata("key"))
										if (v.getOwningPlugin().equals(Core.getCore())) {
											le.remove();
											break;
										}
								}
								if (!le.hasAI()) {
									le.remove();
								}
								if (le.getCustomName() != null || le.isCustomNameVisible())
									le.remove();
							}
					}
					if (fc.isConfigurationSection("shops")) {
						ConfigurationSection cs = fc.getConfigurationSection("shops");
						for (String key : cs.getKeys(false)) {
							if (cs.isString(key)) {
								String[] sp = key.replace("^", ".").split(", ");
								if (sp.length == 6)
									try {
										Location l = new Location(Bukkit.getWorld(sp[0]), Double.parseDouble(sp[1]),
												Double.parseDouble(sp[2]), Double.parseDouble(sp[3]),
												Float.parseFloat(sp[4]), Float.parseFloat(sp[5]));
										ShopStores shop = ShopStores.valueOf(cs.getString(key));
										if (shop != null)
											shop.spawnEntity(l);
									} catch (IllegalArgumentException ex) {
										cs.set(key, null);
										fc.set("shops", cs);
									}
							}
						}
					}
					if (fc.isConfigurationSection("msgs")) {
						ConfigurationSection cs = fc.getConfigurationSection("msgs");
						for (String key : cs.getKeys(false)) {
							if (cs.isString(key)) {
								String[] sp = key.replace("^", ".").split(", ");
								if (sp.length == 6)
									try {
										Location l = new Location(Bukkit.getWorld(sp[0]), Double.parseDouble(sp[1]),
												Double.parseDouble(sp[2]), Double.parseDouble(sp[3]),
												Float.parseFloat(sp[4]), Float.parseFloat(sp[5]));
										MsgCommand.spawnArmorStand(l, cs.getString(key));
									} catch (IllegalArgumentException ex) {
										cs.set(key, null);
										fc.set("msgs", cs);
									}
							}
						}
					}
				} catch (IOException | InvalidConfigurationException e) {
					e.printStackTrace();
				}
			}
		}.runTaskLater(Core.getCore(), 20 * 5);
	}

	public static void Load() {
		try {
			File file = new File("plugins/COIN/Config.yml");
			File loc = new File("plugins/COIN");
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isConfigurationSection("homes")) {
				ConfigurationSection cs = fc.getConfigurationSection("homes");
				for (String key : cs.getKeys(false)) {
					if (cs.isLocation(key))
						HashMapStore.addVillages(new Villages(key, cs.getLocation(key)));
				}
			}
			if (fc.isConfigurationSection("shops")) {
				ConfigurationSection cs = fc.getConfigurationSection("shops");
				for (String key : cs.getKeys(false)) {
					if (cs.isString(key)) {
						String[] sp = key.replace("^", ".").split(", ");
						if (sp.length == 6)
							try {
								Location l = new Location(Bukkit.getWorld(sp[0]), Double.parseDouble(sp[1]),
										Double.parseDouble(sp[2]), Double.parseDouble(sp[3]), Float.parseFloat(sp[4]),
										Float.parseFloat(sp[5]));
								ShopStores shop = ShopStores.valueOf(cs.getString(key));
								if (shop != null)
									shop.spawnEntity(l);
							} catch (IllegalArgumentException ex) {
								cs.set(key, null);
								fc.set("shops", cs);
							}
					}
				}
			}
			if (fc.isConfigurationSection("msgs")) {
				ConfigurationSection cs = fc.getConfigurationSection("msgs");
				for (String key : cs.getKeys(false)) {
					if (cs.isString(key)) {
						String[] sp = key.replace("^", ".").split(", ");
						if (sp.length == 6)
							try {
								Location l = new Location(Bukkit.getWorld(sp[0]), Double.parseDouble(sp[1]),
										Double.parseDouble(sp[2]), Double.parseDouble(sp[3]), Float.parseFloat(sp[4]),
										Float.parseFloat(sp[5]));
								MsgCommand.spawnArmorStand(l, cs.getString(key));
							} catch (IllegalArgumentException ex) {
								cs.set(key, null);
								fc.set("shops", cs);
							}
					}
				}
			}
			if (fc.isList("worlds")) {
				List<String> list = fc.getStringList("worlds");
				for (String name : list) {
					World world = Bukkit.getWorld(name);
					if (world == null) {
						File f = new File(name);
						if (f.exists()) {
							new WorldCreator(name).createWorld();
							Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + name + "을 LOAD했습니다.");
						}
					}
				}

			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}
