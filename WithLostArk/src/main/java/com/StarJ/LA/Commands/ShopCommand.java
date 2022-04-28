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

import com.StarJ.LA.Systems.ShopStores;

public class ShopCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (sender instanceof Player) {
				try {
					Player player = (Player) sender;
					ShopStores shop = ShopStores.valueOf(args[0]);
					if (shop != null) {
						Location loc = player.getLocation();
						shop.spawnEntity(player.getLocation());
						sender.sendMessage(ChatColor.GOLD + shop.name() + ChatColor.GREEN + "을 " + loc.getBlockX()
								+ ", " + loc.getBlockY() + ", " + loc.getBlockZ() + "에 소환했습니다.");
					}
				} catch (IllegalArgumentException ex) {
					sender.sendMessage(ChatColor.RED + "잘못된 종류의 상인입니다.");
				}
			} else
				sender.sendMessage(ChatColor.RED + "플레이어만 사용 가능합니다.");
			return true;
		} else if (args.length == 4) {
			try {
				ShopStores shop = ShopStores.valueOf(args[0]);
				if (shop != null) {
					double x = Double.parseDouble(args[1]);
					double y = Double.parseDouble(args[2]);
					double z = Double.parseDouble(args[3]);
					shop.spawnEntity(new Location(Bukkit.getWorlds().get(0), x, y, z));
					sender.sendMessage(ChatColor.GOLD + shop.name() + ChatColor.GREEN + "을 " + x + ", " + y + ", " + z
							+ "에 소환했습니다.");
				}
			} catch (IllegalArgumentException ex) {
				sender.sendMessage(ChatColor.RED + "잘못된 종류의 상인입니다.");
			}
		} else if (args.length == 6) {
			try {
				ShopStores shop = ShopStores.valueOf(args[0]);
				if (shop != null) {
					double x = Double.parseDouble(args[1]);
					double y = Double.parseDouble(args[2]);
					double z = Double.parseDouble(args[3]);
					float yaw = Float.parseFloat(args[4]);
					float pitch = Float.parseFloat(args[5]);
					shop.spawnEntity(new Location(Bukkit.getWorlds().get(0), x, y, z, yaw, pitch));
					sender.sendMessage(ChatColor.GOLD + shop.name() + ChatColor.GREEN + "을 " + x + ", " + y + ", " + z
							+ "에 소환했습니다.");
				}
			} catch (IllegalArgumentException ex) {
				sender.sendMessage(ChatColor.RED + "잘못된 종류의 상인입니다.");
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 1) {
			for (ShopStores shop : ShopStores.values())
				if (args[0].equals("") || shop.name().toLowerCase().startsWith(args[0].toLowerCase()))
					list.add(shop.name());
		}
		return list;
	}
}
