package com.StarJ.LA.Systems;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
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
import com.StarJ.LA.Listeners.EntityDamageListener;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.Fishes.Biomes;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;

public class ConfigStore {
	//
	public static boolean isBasicsDuration(Player player, Basics basic, int num) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null && fc.isConfigurationSection("basics")) {
				ConfigurationSection cs = fc.getConfigurationSection("basics");
				if (cs.isLong(basic.name() + "_duration_" + num))
					return cs.getLong(basic.name() + "_duration_" + num) - System.currentTimeMillis() > 0;
			}

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static long getBasicsDuration(Player player, Basics basic, int num) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null && fc.isConfigurationSection("basics")) {
				ConfigurationSection cs = fc.getConfigurationSection("basics");
				if (cs.isLong(basic.name() + "_duration_" + num))
					return cs.getLong(basic.name() + "_duration_" + num) - System.currentTimeMillis();
			}

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setBasicsDuration(Player player, Basics basic, int num, long duration) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null) {
				ConfigurationSection cs = fc.isConfigurationSection("basics") ? fc.getConfigurationSection("basics")
						: fc.createSection("basics");
				cs.set(basic.name() + "_duration_" + num, System.currentTimeMillis() + duration * 1000);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static int getBasicsNumber(Player player, Basics basic, int num) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null && fc.isConfigurationSection("basics")) {
				ConfigurationSection cs = fc.getConfigurationSection("basics");
				if (cs.isInt(basic.name() + "_number_" + num))
					return cs.getInt(basic.name() + "_number_" + num);
			}

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setBasicsNumber(Player player, Basics basic, int num, int number) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null) {
				ConfigurationSection cs = fc.isConfigurationSection("basics") ? fc.getConfigurationSection("basics")
						: fc.createSection("basics");
				cs.set(basic.name() + "_number_" + num, number);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static boolean isBasicsCool(Player player, Basics basic, int num) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null && fc.isConfigurationSection("basics")) {
				ConfigurationSection cs = fc.getConfigurationSection("basics");
				if (cs.isLong(basic.name() + "_cool_" + num))
					return cs.getLong(basic.name() + "_cool_" + num) - System.currentTimeMillis() > 0;
			}

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static long getBasicsCool(Player player, Basics basic, int num) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null && fc.isConfigurationSection("basics")) {
				ConfigurationSection cs = fc.getConfigurationSection("basics");
				if (cs.isLong(basic.name() + "_cool_" + num))
					return cs.getLong(basic.name() + "_cool_" + num) - System.currentTimeMillis();
			}

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setBasicsCool(Player player, Basics basic, int num, long cool) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null) {
				ConfigurationSection cs = fc.isConfigurationSection("basics") ? fc.getConfigurationSection("basics")
						: fc.createSection("basics");
				cs.set(basic.name() + "_cool_" + num, System.currentTimeMillis() + cool * 1000);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static int getBasicsLevel(Player player, Basics basic) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null && fc.isConfigurationSection("basics")) {
				ConfigurationSection cs = fc.getConfigurationSection("basics");
				if (cs.isInt(basic.name() + "_LEVEL"))
					return cs.getInt(basic.name() + "_LEVEL");
			}

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public static void setBasicsLevel(Player player, Basics basic, int level) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null) {
				ConfigurationSection cs = fc.isConfigurationSection("basics") ? fc.getConfigurationSection("basics")
						: fc.createSection("basics");
				cs.set(basic.name() + "_LEVEL", level);
				fc.set("basics", cs);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static int getBasicsEXP(Player player, Basics basic) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null && fc.isConfigurationSection("basics")) {
				ConfigurationSection cs = fc.getConfigurationSection("basics");
				if (cs.isInt(basic.name() + "_EXP"))
					return cs.getInt(basic.name() + "_EXP");
			}

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void setBasicsEXP(Player player, Basics basic, int exp) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (basic != null) {
				ConfigurationSection cs = fc.isConfigurationSection("basics") ? fc.getConfigurationSection("basics")
						: fc.createSection("basics");
				cs.set(basic.name() + "_EXP", exp);
				fc.set("basics", cs);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	//

	public static double getSkillCooldown(Player player, Jobs job, Skills skill) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					if (skill != null && fc.isConfigurationSection("skill_cools")) {
						ConfigurationSection cs = fc.getConfigurationSection("skill_cools");
						if (cs.isLong(skill.getKey()))
							return (cs.getLong(skill.getKey()) - System.currentTimeMillis()) / 1000.0d;
					}
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		return 0;
	}

	public static void setSkillCooldown(Player player, Jobs job, Skills skill, int cool) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null) {
					if (skill != null) {
						ConfigurationSection cs = fc.isConfigurationSection("skill_cools")
								? fc.getConfigurationSection("skill_cools")
								: fc.createSection("skill_cools");
						cs.set(skill.getKey(), System.currentTimeMillis() + cool);
						fc.set("skill_cools", cs);
					}
				}
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static ItemStack[] getConsumeItems(Player player) {
		final ItemStack[] list = new ItemStack[4];
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isConfigurationSection("consumes")) {
				ConfigurationSection cs = fc.getConfigurationSection("consumes");
				for (int slot = 0; slot <= 3; slot++)
					if (cs.isItemStack(slot + ""))
						list[slot] = cs.getItemStack(slot + "");
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void setConsumeItems(Player player, ItemStack[] list) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			ConfigurationSection cs = fc.isConfigurationSection("consumes") ? fc.getConfigurationSection("consumes")
					: fc.createSection("consumes");
			for (int slot = 0; slot <= 3; slot++)
				if (slot < list.length)
					cs.set(slot + "", list[slot]);
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
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null) {
					if (fc.isList("보석"))
						for (String key : fc.getStringList("보석")) {
							JewerlyItems jewerly = JewerlyItems.valueOf(key);
							if (jewerly != null)
								list.add(jewerly);
						}
				}
				for (int c = list.size(); c < 24; c++)
					list.add(Items.jewerly_zero);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		return list;
	}

	public static void setJewerlyItems(Player player, List<JewerlyItems> jewerlys) {
		setJewerlyItems(player, getJob(player), jewerlys);
	}

	public static void setJewerlyItems(Player player, Jobs job, List<JewerlyItems> jewerlys) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null) {
					final List<String> list = new ArrayList<String>();
					for (JewerlyItems jewerly : jewerlys) {
						list.add(jewerly != null ? jewerly.getKey() : Items.jewerly_zero.getKey());
					}
					fc.set("보석", list);
				}
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static int getWeaponLevel(Player player, Jobs job) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null) {
					if (fc.isInt("WeaponLevel"))
						return fc.getInt("WeaponLevel");
				}
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		setWeaponLevel(player, job, 0);
		return 0;
	}

	public static void setWeaponLevel(Player player, Jobs job, int level) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null) {
					fc.set("WeaponLevel", level);
				}
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static double getArmorHealth(Player player, Jobs job) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				double amount = 1;
				if (job != null) {
					if (fc.isInt("HelmetLevel"))
						amount += fc.getInt("HelmetLevel") * Items.helmet.getPercent();
					if (fc.isInt("ChestplateLevel"))
						amount += fc.getInt("ChestplateLevel") * Items.chestplate.getPercent();
					if (fc.isInt("LeggingsLevel"))
						amount += fc.getInt("LeggingsLevel") * Items.leggings.getPercent();
					if (fc.isInt("BootsLevel"))
						amount += fc.getInt("BootsLevel") * Items.boots.getPercent();
					return amount;
				}
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		return 1;
	}

	public static int getHelmetLevel(Player player, Jobs job) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					if (fc.isInt("HelmetLevel"))
						return fc.getInt("HelmetLevel");
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		setHelmetLevel(player, job, 0);
		return 0;
	}

	public static void setHelmetLevel(Player player, Jobs job, int level) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					fc.set("HelmetLevel", level);
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static int getChestplateLevel(Player player, Jobs job) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					if (fc.isInt("ChestplateLevel"))
						return fc.getInt("ChestplateLevel");
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		setChestplateLevel(player, job, 0);
		return 0;
	}

	public static void setChestplateLevel(Player player, Jobs job, int level) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					fc.set("ChestplateLevel", level);
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static int getLeggingsLevel(Player player, Jobs job) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					if (fc.isInt("LeggingsLevel"))
						return fc.getInt("LeggingsLevel");
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		setLeggingsLevel(player, job, 0);
		return 0;
	}

	public static void setLeggingsLevel(Player player, Jobs job, int level) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					fc.set("LeggingsLevel", level);
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static int getBootsLevel(Player player, Jobs job) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					if (fc.isInt("BootsLevel"))
						return fc.getInt("BootsLevel");
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		setBootsLevel(player, job, 0);
		return 0;
	}

	public static void setBootsLevel(Player player, Jobs job, int level) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					fc.set("BootsLevel", level);
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	//
	public static boolean getPVP(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			boolean pvp = fc.isBoolean("pvp") ? !fc.getBoolean("pvp") : true;
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

	public static LinkedHashSet<Integer> getSkillSlots(Player player, Jobs job) {
		final LinkedHashSet<Integer> list = new LinkedHashSet<Integer>();
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null) {
					for (int num = 1; num <= 8; num++)
						if (fc.isInt("skill_slot" + num)) {
							list.add(fc.getInt("skill_slot" + num));
						} else
							list.add(-1);
				}
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		return list;
	}

	public static void setSkillSlot(Player player, Jobs job, int num, int slot) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					fc.set("skill_slot" + num, slot);
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static Jobs getJob(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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

	public static void loadIdentity(Player player, Jobs job) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					if (fc.isDouble("identity"))
						HashMapStore.setIdentity(player, fc.getDouble("identity"));
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static void saveIdentity(Player player, Jobs job) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					fc.set("identity", HashMapStore.getIdentity(player));
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static void loadJobHealth(Player player, Jobs job) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					if (fc.isDouble("health")) {
						HashMapStore.setHealth(player, fc.getDouble("health"));
					} else
						HashMapStore.setHealth(player, job.getMaxHealth(player));
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static void saveJobHealth(Player player, Jobs job) {
		if (job != null)
			try {
				File file = new File(
						"plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs/" + job.getKey() + ".yml");
				File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/Jobs");
				FileConfiguration fc = new YamlConfiguration();
				if (!file.exists()) {
					loc.mkdirs();
					file.createNewFile();
				}
				fc.load(file);
				if (job != null)
					fc.set("health", HashMapStore.getHealth(player));
				fc.save(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	}

	public static void confirmFoodLevel(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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

	public static double getHealth(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			if (fc.isDouble("health"))
				return fc.getDouble("health");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return 20.0d;
	}

	public static void setHealth(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			fc.set("health", player.getHealth());
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void changePlayerStatus(Player player) {
		try {
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
			FileConfiguration fc = new YamlConfiguration();
			if (!file.exists()) {
				loc.mkdirs();
				file.createNewFile();
			}
			fc.load(file);
			boolean status = fc.isBoolean("status") ? !fc.getBoolean("status") : true;
			Jobs job = getJob(player);
			PlayerInventory inv = player.getInventory();
			ItemStack air = new ItemStack(Material.AIR);
			if (job != null)
				if (status) {
					List<ItemStack> saves = new ArrayList<ItemStack>();
					saves.addAll(Arrays.asList(player.getInventory().getContents()));

					List<ItemStack> items = job.getJobitems(player);
					for (int c = 9; c < 36; c++)
						inv.setItem(c, getEmpty());
					for (int c = 0; c < 9; c++) {
						if (c < items.size())
							inv.setItem(c, items.get(c));
					}
					inv.setItem(25, job.getSpecialArts().getItemStack());
					inv.setItem(34, job.getIdentity().getItemStack());
					inv.setHelmet(air);
					inv.setChestplate(air);
					inv.setLeggings(air);
					inv.setBoots(air);
					if (fc.isConfigurationSection("consumes")) {
						ConfigurationSection cs = fc.getConfigurationSection("consumes");
						for (int slot = 0; slot <= 3; slot++)
							if (cs.isItemStack(slot + ""))
								inv.setItem(13 + slot, cs.getItemStack(slot + ""));
					}
					fc.set("change_save", saves);
					//
					player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100D);
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(0D);
					player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
					inv.setItemInOffHand(air);
					//
					
					inv.setHeldItemSlot(8);
					fc.set("health", player.getHealth());
					player.setHealthScale(100);
					loadJobHealth(player, job);
					loadIdentity(player, job);
					EntityDamageListener.confirmHealthPercent(job, player, HashMapStore.getHealth(player),
							HashMapStore.getAllAbsorption(player));
					fc.set("food", player.getFoodLevel());
					player.setFoodLevel(19);
					player.sendMessage(ChatColor.RED + "전투 모드로 변경하였습니다.");
					player.getInventory().setHeldItemSlot(7);
					ActionBarRunnable.run(player);
				} else {
					if (fc.isList("change_save")) {
						@SuppressWarnings("unchecked")
						List<ItemStack> saves = (List<ItemStack>) fc.getList("change_save");
						inv.setContents(saves.toArray(ItemStack[]::new));
						fc.set("change_save", null);
					}
					saveJobHealth(player, job);
					saveIdentity(player, job);
					player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
					player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0D);
					player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue((double) 0.1f);
					player.setHealthScale(20D);
					if (fc.isDouble("health")) {
						player.setHealth(fc.getDouble("health"));
					} else
						player.setHealth(20.0D);
					player.setAbsorptionAmount(0);
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

	public static ItemStack getEmpty() {
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/advancement.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/advancement.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/main.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/inventory.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/inventory.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/inventory.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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
			File file = new File("plugins/COIN/Players/" + player.getUniqueId().toString() + "/inventory.yml");
			File loc = new File("plugins/COIN/Players/" + player.getUniqueId().toString());
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

	//
	public static void addVillages(Villages village) {
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
				cs.set(village.getName(), village.getLocation());
				fc.set("homes", cs);
			}
			fc.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static boolean removeVillages(String name) {
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
				cs.set(name, null);
				fc.set("homes", cs);
				fc.save(file);
				return true;
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<Villages> getVillages() {
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
				List<Villages> list = new ArrayList<Villages>();
				ConfigurationSection cs = fc.getConfigurationSection("homes");
				for (String key : cs.getKeys(false))
					if (cs.isLocation(key))
						list.add(new Villages(key, cs.getLocation(key)));
				return list;
			}
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return null;
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
				if (fc.isConfigurationSection("shops"))
					fc.set("shops", null);
				ConfigurationSection cs = fc.createSection("shops");
				for (World world : Bukkit.getWorlds())
					for (LivingEntity le : world.getLivingEntities())
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
						for (World world : Bukkit.getWorlds())
							for (LivingEntity le : world.getLivingEntities())
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
		}.runTaskLater(Core.getCore(), 1);
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
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}
