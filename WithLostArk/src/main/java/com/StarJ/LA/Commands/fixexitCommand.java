package com.StarJ.LA.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Villages;

public class fixexitCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
			if (HashMapStore.removeVillages(args[1])) {
				sender.sendMessage(ChatColor.RED + args[1] + "스퀘어홀을 제거했습니다.");
			} else
				sender.sendMessage(ChatColor.RED + "없는 스퀘어홀입니다.");
			return true;
		} else if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				Villages village = new Villages(args[1], player.getLocation());
				HashMapStore.addVillages(village);
				sender.sendMessage(
						ChatColor.GREEN + "현재 위치를 스퀘어홀로 지정했습니다. " + args[1] + " - " + player.getLocation().toString());
				ConfigStore.Save();
			} else
				sender.sendMessage(ChatColor.RED + "플레이어만 사용 가능합니다.");
			return true;
		} else if (args.length == 5 && args[0].equalsIgnoreCase("add")) {
			try {
				double x = Double.parseDouble(args[2]);
				double y = Double.parseDouble(args[3]);
				double z = Double.parseDouble(args[4]);
				Location loc = new Location(Bukkit.getWorlds().get(0), x, y, z);
				Villages village = new Villages(args[1], loc);
				HashMapStore.addVillages(village);
				sender.sendMessage(ChatColor.GREEN + "현재 위치를 스퀘어홀로 지정했습니다. " + args[1] + " - " + loc.toString());
				ConfigStore.Save();
			} catch (IllegalArgumentException ex) {

			}
			return true;
		} else if (args.length == 7 && args[0].equalsIgnoreCase("add")) {
			try {
				double x = Double.parseDouble(args[2]);
				double y = Double.parseDouble(args[3]);
				double z = Double.parseDouble(args[4]);
				float yaw = Float.parseFloat(args[5]);
				float pitch = Float.parseFloat(args[6]);
				Location loc = new Location(Bukkit.getWorlds().get(0), x, y, z, yaw, pitch);
				Villages village = new Villages(args[1], loc);
				HashMapStore.addVillages(village);
				sender.sendMessage(ChatColor.GREEN + "현재 위치를 스퀘어홀로 지정했습니다. " + args[1] + " - " + loc.toString());
				ConfigStore.Save();
			} catch (IllegalArgumentException ex) {

			}
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("") && "add".startsWith(args[0].toLowerCase()))
				list.add("add");
			if (args[0].equalsIgnoreCase("") && "remove".startsWith(args[0].toLowerCase()))
				list.add("remove");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
			list.addAll(HashMapStore.getVillageNames());
		}
		return list;
	}
}