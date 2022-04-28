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
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.StarJ.LA.Core;
import com.StarJ.LA.Systems.HashMapStore;

import net.minecraft.world.entity.decoration.EntityArmorStand;

public class MsgCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				spawnArmorStand(player.getLocation(), args[0]);
			} else
				sender.sendMessage(ChatColor.RED + "플레이어만 사용 가능합니다.");
			return true;
		} else if (args.length == 4) {
			try {
				double x = Double.parseDouble(args[1]);
				double y = Double.parseDouble(args[2]);
				double z = Double.parseDouble(args[3]);
				Location loc = new Location(Bukkit.getWorlds().get(0), x, y, z);
				spawnArmorStand(loc, args[0]);
			} catch (IllegalArgumentException ex) {
				sender.sendMessage(ChatColor.RED + "잘못된 좌표를 입력했습니다.");
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		return list;
	}

	public static ArmorStand spawnArmorStand(Location loc, String msg) {
		HashMapStore.setMsgs(loc, msg);
		msg = msg.replace('_', ' ');
		ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		stand.setMetadata("key", new FixedMetadataValue(Core.getCore(), "msg"));
		stand.setSmall(true);
		if (msg.contains("§"))
			msg = ChatColor.translateAlternateColorCodes('§', msg);

		stand.setCustomName(msg);
		stand.setCustomNameVisible(true);
		EntityArmorStand entity = ((CraftArmorStand) stand).getHandle();
//		entity.a(false);
		entity.j(true);
		return stand;
	}
}