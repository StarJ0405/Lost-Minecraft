package com.StarJ.LA.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class sudoCommand implements CommandExecutor, TabCompleter {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 2) {
			OfflinePlayer off = Bukkit.getOfflinePlayer(args[0]);
			if (off.isOnline()) {
				String command = "";
				for (int c = 1; c < args.length; c++)
					command += args[c] + " ";
				off.getPlayer().performCommand(command);
				sender.sendMessage(
						ChatColor.GOLD + off.getName() + ChatColor.WHITE + "님에게" + ChatColor.WHITE + "을 사용시켰습니다.");
			} else
				sender.sendMessage(ChatColor.GOLD + off.getName() + ChatColor.RED + "님이 접속중이 아닙니다.");
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 1)
			for (Player player : Bukkit.getOnlinePlayers())
				if (args[0].equals("") || player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
					list.add(player.getName());

		return list;
	}

}
