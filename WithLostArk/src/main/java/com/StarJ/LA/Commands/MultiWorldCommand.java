package com.StarJ.LA.Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class MultiWorldCommand implements CommandExecutor, TabCompleter {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("load")) {
				File file = new File(args[1]);
				if (file.exists()) {
					new WorldCreator(args[1]).createWorld();
					sender.sendMessage(ChatColor.RED + args[1] + "를 Load했습니다.");
				} else
					sender.sendMessage(ChatColor.RED + args[1] + "가 존재하지 않습니다.");
				return true;
			} else if (args[0].equalsIgnoreCase("unload")) {
				World world = Bukkit.getWorld(args[1]);
				if (world != null) {
					Bukkit.unloadWorld(args[1], true);
					sender.sendMessage(ChatColor.RED + args[1] + "를 Unload했습니다.");
				} else
					sender.sendMessage(ChatColor.RED + args[1] + "가 존재하지 않습니다.");
				return true;
			} else if (args[0].equalsIgnoreCase("create")) {
				World world = Bukkit.getWorld(args[1]);
				if (world == null) {
					Bukkit.unloadWorld(args[1], true);
					sender.sendMessage(ChatColor.RED + args[1] + "를 생성했습니다.");
				} else
					sender.sendMessage(ChatColor.RED + args[1] + "가 존재합니다.");
				return true;
			} else if (args[0].equalsIgnoreCase("delete")) {
				File file = new File(args[1]);
				if (file.exists()) {
					World world = Bukkit.getWorld(args[1]);
					if (world != null)
						Bukkit.unloadWorld(args[1], true);
					sender.sendMessage(ChatColor.RED + args[1] + "를 삭제했습니다.");
				} else
					sender.sendMessage(ChatColor.RED + args[1] + "가 존재하지 않습니다.");
				return true;
			} else if (args[0].equalsIgnoreCase("goto")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					World world = Bukkit.getWorld(args[1]);
					if (world != null) {
						player.teleport(world.getSpawnLocation());
						sender.sendMessage(ChatColor.RED + args[1] + "로 이동했습니다.");
					} else
						sender.sendMessage(ChatColor.RED + args[1] + "가 존재하지 않습니다.");
				} else
					sender.sendMessage(ChatColor.RED + "플레이어만 사용 가능합니다.");
				return true;
			}

		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("goto")) {
				World world = Bukkit.getWorld(args[1]);
				if (world != null) {
					OfflinePlayer off = Bukkit.getOfflinePlayer(args[2]);
					if (off.isOnline()) {
						off.getPlayer().teleport(world.getSpawnLocation());
						off.getPlayer().sendMessage(ChatColor.RED + args[1] + "로 이동했습니다.");
						sender.sendMessage(ChatColor.RED + args[1] + "로 이동했습니다.");
					} else
						sender.sendMessage(ChatColor.RED + args[2] + "가 존재하지않는 플레이어입니다.");
				} else
					sender.sendMessage(ChatColor.RED + args[1] + "가 존재하지 않습니다.");

				sender.sendMessage(ChatColor.RED + "플레이어만 사용 가능합니다.");
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 1) {
			if (args[0].equals("") || "load".startsWith(args[0].toLowerCase()))
				list.add("load");
			if (args[0].equals("") || "unload".startsWith(args[0].toLowerCase()))
				list.add("unload");
			if (args[0].equals("") || "create".startsWith(args[0].toLowerCase()))
				list.add("create");
			if (args[0].equals("") || "delete".startsWith(args[0].toLowerCase()))
				list.add("delete");
			if (args[0].equals("") || "goto".startsWith(args[0].toLowerCase()))
				list.add("goto");
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("unload") || args[0].equalsIgnoreCase("delete")
					|| args[0].equalsIgnoreCase("goto"))
				for (World world : Bukkit.getWorlds())
					if (args[1].equals("") || world.getName().toLowerCase().startsWith(args[1].toLowerCase()))
						list.add(world.getName());
		}
		return list;
	}

}
