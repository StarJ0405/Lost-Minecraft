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
import com.StarJ.LA.Systems.GUIStores;

public class InfoCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (ConfigStore.getPlayerStatus(player)) {
				player.sendMessage(ChatColor.RED + "전투 모드에서는 열 수 없습니다.");
			} else
				GUIStores.info.openGUI(player, 0);

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