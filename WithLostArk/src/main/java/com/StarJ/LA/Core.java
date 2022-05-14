package com.StarJ.LA;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.StarJ.LA.Commands.BifrostCommand;
import com.StarJ.LA.Commands.ExitCommand;
import com.StarJ.LA.Commands.HomeCommand;
import com.StarJ.LA.Commands.InfoCommand;
import com.StarJ.LA.Commands.InventoryCommand;
import com.StarJ.LA.Commands.ItemCommand;
import com.StarJ.LA.Commands.MoneyCommand;
import com.StarJ.LA.Commands.MsgCommand;
import com.StarJ.LA.Commands.MultiWorldCommand;
import com.StarJ.LA.Commands.PVPCommand;
import com.StarJ.LA.Commands.ShopCommand;
import com.StarJ.LA.Commands.StatusCommand;
import com.StarJ.LA.Commands.degopCommand;
import com.StarJ.LA.Commands.fixexitCommand;
import com.StarJ.LA.Commands.FlySpeedCommand;
import com.StarJ.LA.Commands.gopCommand;
import com.StarJ.LA.Commands.sudoCommand;
import com.StarJ.LA.Listeners.BlockListener;
import com.StarJ.LA.Listeners.CraftListener;
import com.StarJ.LA.Listeners.EntityDamageListener;
import com.StarJ.LA.Listeners.EntityShootListener;
import com.StarJ.LA.Listeners.EntitySpawnListener;
import com.StarJ.LA.Listeners.WorldListener;
import com.StarJ.LA.Listeners.FishListener;
import com.StarJ.LA.Listeners.InventoryListener;
import com.StarJ.LA.Listeners.PlayerDeathListener;
import com.StarJ.LA.Listeners.PlayerInteractListener;
import com.StarJ.LA.Listeners.PlayerLogListener;
import com.StarJ.LA.Listeners.ProjectListener;
import com.StarJ.LA.Systems.Basics;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.CustomAdvancements;
import com.StarJ.LA.Systems.EnchantsType;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Recipes;
import com.StarJ.LA.Systems.ScoreboarStore;
import com.StarJ.LA.Systems.ShopStores;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;

public class Core extends JavaPlugin {
	private static Core core;
	private PluginManager pm;

	public static Core getCore() {
		return core;
	}

	public void onEnable() {
		core = this;
		//
		this.pm = Bukkit.getPluginManager();
		this.pm.registerEvents(new BlockListener(), this);
		this.pm.registerEvents(new EntityDamageListener(), this);
		this.pm.registerEvents(new EntityShootListener(), this);
		this.pm.registerEvents(new EntitySpawnListener(), this);
		this.pm.registerEvents(new WorldListener(), this);
		this.pm.registerEvents(new FishListener(), this);
		this.pm.registerEvents(new InventoryListener(), this);
		this.pm.registerEvents(new PlayerDeathListener(), this);
		this.pm.registerEvents(new PlayerInteractListener(), this);
		this.pm.registerEvents(new PlayerLogListener(), this);
		this.pm.registerEvents(new ProjectListener(), this);
		this.pm.registerEvents(new CraftListener(), this);
		//
		getCommand("home").setExecutor(new HomeCommand());
		getCommand("exit").setExecutor(new ExitCommand());
		getCommand("fixexit").setExecutor(new fixexitCommand());
		getCommand("bifrost").setExecutor(new BifrostCommand());
		getCommand("money").setExecutor(new MoneyCommand());
		getCommand("shop").setExecutor(new ShopCommand());
		getCommand("item").setExecutor(new ItemCommand());
		getCommand("gop").setExecutor(new gopCommand());
		getCommand("degop").setExecutor(new degopCommand());
		getCommand("msg").setExecutor(new MsgCommand());
		getCommand("inventory").setExecutor(new InventoryCommand());
		getCommand("info").setExecutor(new InfoCommand());
		getCommand("status").setExecutor(new StatusCommand());
		getCommand("pvp").setExecutor(new PVPCommand());
		getCommand("multiworld").setExecutor(new MultiWorldCommand());
		getCommand("sudo").setExecutor(new sudoCommand());
		getCommand("flyspeed").setExecutor(new FlySpeedCommand());
		for (World world : Bukkit.getWorlds()) {
			world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
			world.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 15);
			world.setGameRule(GameRule.KEEP_INVENTORY, true);
		}
		//
		for (Player player : Bukkit.getOnlinePlayers()) {
			ConfigStore.LoadPlayerAllData(player);
			ConfigStore.LoadPlayerAdvancement(player);
			ScoreboarStore.join(player);
			ScoreboarStore.confirmTeam(player);
			Jobs job = ConfigStore.getJob(player);
			ConfigStore.loadJobHealth(player, job);
			ConfigStore.loadIdentity(player, job);
			if (ConfigStore.getPlayerStatus(player)) {
				double max = job != null ? job.getMaxHealth(player) : 20;
				double health = HashMapStore.getHealth(player);

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
				player.setHealth(per);
				HashMapStore.setActionbar(player, new ActionBarRunnable(player).runTaskTimer(Core.getCore(), 0, 10));
			}
			HashMapStore.setSkillStop(player.getUniqueId().toString(), false);
		}
		ConfigStore.Load();
		ShopStores.initial();
		CustomAdvancements.Registry();
//		CustomAdvancements.ResetAdvancements();
		Basics.initial();
		EnchantsType.initial();
		//
		for (Recipes recipe : Recipes.values())
			recipe.RegistRecipe();
		//
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "플러그인 정상 작동되었습니다.");
	}

	public void onDisable() {
		//
		for (Player player : Bukkit.getOnlinePlayers()) {
			ConfigStore.SavePlayerAllData(player);
			ConfigStore.SavePlayerAdvancement(player);
			Jobs job = ConfigStore.getJob(player);
			ConfigStore.saveJobHealth(player, job);
			ConfigStore.saveIdentity(player, job);
			String key = player.getUniqueId().toString();
			ItemStack tool = HashMapStore.getEnchantItemStack(key);
			if (tool != null)
				if (player.getInventory().firstEmpty() != -1) {
					player.getInventory().addItem(tool);
				} else
					player.getWorld().dropItemNaturally(player.getEyeLocation(), tool);
			player.closeInventory();
		}
		ConfigStore.Save();
		//
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "플러그인 종료되었습니다.");
	}
}
