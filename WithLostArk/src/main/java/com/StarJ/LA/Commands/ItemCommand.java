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

import com.StarJ.LA.Items.Items;

public class ItemCommand implements CommandExecutor, TabCompleter {
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 2) {
			OfflinePlayer off = Bukkit.getOfflinePlayer(args[0]);
			if (off.isOnline()) {
				Player player = off.getPlayer();
				Items i = Items.valueOf(args[1]);
				if (i != null) {
					player.getInventory().addItem(i.getItemStack());
				} else
					sender.sendMessage(ChatColor.RED + "없는 아이템입니다.");
			} else
				sender.sendMessage(ChatColor.RED + "플레이거가 접속중이지 않습니다.");
			return true;
		} else if (args.length == 3) {
			try {
				OfflinePlayer off = Bukkit.getOfflinePlayer(args[0]);
				int amount = Integer.parseInt(args[2]);
				if (off.isOnline()) {
					Player player = off.getPlayer();
					Items i = Items.valueOf(args[1]);
					if (i != null) {
						if (i.getMaxCount() == 1) {
							for (int c = 0; c < amount; c++)
								player.getInventory().addItem(i.getItemStack());
						} else
							player.getInventory().addItem(i.getItemStack(amount));
					} else
						sender.sendMessage(ChatColor.RED + "없는 아이템입니다.");
				} else
					sender.sendMessage(ChatColor.RED + "플레이거가 접속중이지 않습니다.");
				return true;
			} catch (IllegalArgumentException ex) {
				sender.sendMessage(ChatColor.RED + "숫자 입력 부분을 틀리셨습니다.");
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 1) {
			for (Player player : Bukkit.getOnlinePlayers())
				if (args[0].equals("") || player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
					list.add(player.getName());
		} else if (args.length == 2) {
			for (Items i : Items.values())
				if (args[1].equals("") || i.getKeyName().toLowerCase().startsWith(args[1].toLowerCase()))
					list.add(i.getKeyName());
		}
		return list;
	}
}
