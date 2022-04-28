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

import com.StarJ.LA.Core;

public class gopCommand implements CommandExecutor, TabCompleter {
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			OfflinePlayer off = Bukkit.getOfflinePlayer(args[0]);
			if (off.isOnline()) {
				Player player = off.getPlayer();
				if (!player.hasPermission("gambler")) {
					player.addAttachment(Core.getCore()).setPermission("gambler", true);
					sender.sendMessage(ChatColor.RED + "해당 플레이어가에게 권한을 주었습니다.");
				} else
					sender.sendMessage(ChatColor.RED + "해당 플레이어가 이미 권한을 가지고 있습니다.");
			} else
				sender.sendMessage(ChatColor.RED + "플레이어가 접속중이 아닙니다.");
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