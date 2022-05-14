package com.StarJ.LA.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class FlySpeedCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 1) {
				try {
					float speed = Float.parseFloat(args[0]);
					speed /= 10f;
					if (speed > 1)
						speed = 1f;
					player.setFlySpeed(speed);
					player.sendMessage(ChatColor.GREEN + "비행 속도를 " + speed + "로 변경했습니다.");
				} catch (NumberFormatException nfe) {

				}
				return true;
			} else {
				player.sendMessage(ChatColor.GREEN + "비행 속도 : " + player.getFlySpeed());
			}
		} else
			sender.sendMessage(ChatColor.RED + "플레이어만 사용 가능합니다.");
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		return list;
	}
}