package com.StarJ.LA.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Jobs;

public class StatusCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Jobs job = ConfigStore.getJob(player);
			if (job != null) {
				ConfigStore.changePlayerStatus(player);
			} else
				player.sendMessage(ChatColor.RED + "직업이 없습니다.");
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		return list;
	}

}