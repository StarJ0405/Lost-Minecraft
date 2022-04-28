package com.StarJ.LA.Commands;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.StarJ.LA.Items.MoneyItem;
import com.StarJ.LA.Systems.ConfigStore;

public class MoneyCommand implements CommandExecutor, TabCompleter {
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0 && sender instanceof Player) {
			Player player = (Player) sender;
			BigInteger money = ConfigStore.getPlayerMoney(player);
			player.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.WHITE + "님께서 " + ChatColor.GREEN + money
					+ ChatColor.WHITE + "원을 가지고 있습니다.");
			return true;
		} else if (args.length == 1 && (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l"))) {
			List<PlayerMoney> pms = new ArrayList<PlayerMoney>();
			for (Player player : Bukkit.getOnlinePlayers())
				pms.add(new PlayerMoney(player));
			Collections.sort(pms, new Comparators());
			for (PlayerMoney pm : pms)
				sender.sendMessage(ChatColor.GOLD + pm.getName() + ChatColor.WHITE + "님께서 " + ChatColor.GREEN
						+ pm.getMoney().toString() + ChatColor.WHITE + "원을 가지고 있습니다.");
			return true;
		} else if (args.length == 2 && (args[0].equalsIgnoreCase("confirm") || args[0].equalsIgnoreCase("c"))) {
			OfflinePlayer off = Bukkit.getOfflinePlayer(args[1]);
			if (off.isOnline()) {
				Player player = off.getPlayer();
				BigInteger money = ConfigStore.getPlayerMoney(player);
				sender.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.WHITE + "님께서 " + ChatColor.GREEN
						+ money + ChatColor.WHITE + "원을 가지고 있습니다.");
			} else
				sender.sendMessage(ChatColor.RED + "플레이어가 접속중이 아닙니다.");
			return true;
		} else if (args.length == 2 && sender instanceof Player
				&& (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i"))) {
			Player player = (Player) sender;
			try {
				BigInteger amount = new BigInteger(args[1]);
				if (amount.compareTo(BigInteger.ZERO) > 0) {
					BigInteger has = ConfigStore.getPlayerMoney(player);
					if (has.compareTo(amount) >= 0) {
						if (player.getInventory().firstEmpty() != -1) {
							MoneyItem money = Items.money;
							money.setMoney(amount);
							player.getInventory().addItem(money.getItemStack());
							ConfigStore.setPlayerMoney(player, has.subtract(amount));
							player.sendMessage(
									ChatColor.WHITE + "돈을 " + ChatColor.GREEN + amount + ChatColor.WHITE + "원 꺼냈습니다.");
						} else
							player.sendMessage(ChatColor.RED + "인벤토리에 빈 공간이 없습니다.");
					} else
						player.sendMessage(ChatColor.RED + "가지고 있는 금액이 부족합니다.");
				} else
					player.sendMessage(ChatColor.RED + "액수는 1 이상이어야 합니다.");
				return true;
			} catch (IllegalArgumentException ex) {
				sender.sendMessage(ChatColor.RED + "액수는 숫자를 입력하세요.");
			}
		} else if (args.length == 3 && sender instanceof Player
				&& (args[0].equalsIgnoreCase("send") || args[0].equalsIgnoreCase("s"))) {
			Player att = (Player) sender;
			try {
				OfflinePlayer off = Bukkit.getOfflinePlayer(args[1]);
				BigInteger amount = new BigInteger(args[2]);
				if (off.isOnline()) {
					Player vic = off.getPlayer();
					if (amount.compareTo(BigInteger.ZERO) > 0) {
						BigInteger has = ConfigStore.getPlayerMoney(att);
						if (has.compareTo(amount) >= 0) {
							ConfigStore.setPlayerMoney(att, has.subtract(amount));
							ConfigStore.setPlayerMoney(vic, ConfigStore.getPlayerMoney(vic).add(amount));
							att.sendMessage(ChatColor.GOLD + vic.getName() + ChatColor.WHITE + "님에게 돈을 "
									+ ChatColor.GREEN + amount + ChatColor.WHITE + "원 보냈습니다.");
							vic.sendMessage(ChatColor.GOLD + att.getName() + ChatColor.WHITE + "님으로 부터 돈을 "
									+ ChatColor.GREEN + amount + ChatColor.WHITE + "원 받았습니다.");
						} else
							att.sendMessage(ChatColor.RED + "가지고 있는 금액이 부족합니다.");
					} else
						att.sendMessage(ChatColor.RED + "액수는 1 이상이어야 합니다.");
				} else
					att.sendMessage(ChatColor.RED + "플레이어가 접속중이 아닙니다.");
				return true;
			} catch (IllegalArgumentException ex) {

			}
		} else if (args.length == 3 && sender instanceof Player
				&& (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i"))) {
			Player player = (Player) sender;
			try {
				BigInteger amount = new BigInteger(args[1]);
				int count = Integer.parseInt(args[2]);
				BigInteger sum = amount.multiply(BigInteger.valueOf(count));
				if (amount.compareTo(BigInteger.ZERO) > 0) {
					BigInteger has = ConfigStore.getPlayerMoney(player);
					if (has.compareTo(sum) >= 0) {
						MoneyItem money = Items.money;
						money.setMoney(amount);
						player.getInventory().addItem(money.getItemStack(count));
						ConfigStore.setPlayerMoney(player, has.subtract(sum));
						player.sendMessage(
								ChatColor.WHITE + "돈을 " + ChatColor.GREEN + amount + ChatColor.WHITE + "원 꺼냈습니다.");
					} else
						player.sendMessage(ChatColor.RED + "가지고 있는 금액보다 큰 경우 꺼낼 수 없습니다.");
				} else
					player.sendMessage(ChatColor.RED + "액수는 1 이상이어야 합니다.");
				return true;
			} catch (IllegalArgumentException ex) {
				sender.sendMessage(ChatColor.RED + "액수는 숫자를 입력하세요.");
			}
		} else if (args.length == 3
				&& (!(sender instanceof Player) || ((sender instanceof Player) && ((Player) sender).isOp())))
			try {
				OfflinePlayer off = Bukkit.getOfflinePlayer(args[1]);
				BigInteger amount = new BigInteger(args[2]);
				if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("g")) {
					if (off.isOnline()) {
						Player player = off.getPlayer();
						ConfigStore.setPlayerMoney(player, ConfigStore.getPlayerMoney(player).add(amount));
						player.sendMessage(
								ChatColor.RED + "강제적으로 " + ChatColor.GREEN + amount + ChatColor.RED + "원을 지급받았습니다.");
					} else
						sender.sendMessage(ChatColor.RED + "플레이어가 접속중이 아닙니다.");
				} else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) {
					if (off.isOnline()) {
						Player player = off.getPlayer();
						BigInteger money = ConfigStore.getPlayerMoney(player).subtract(amount);
						if (money.compareTo(BigInteger.ZERO) < 0)
							money = BigInteger.ZERO;
						ConfigStore.setPlayerMoney(player, money);
						player.sendMessage(
								ChatColor.RED + "강제적으로 " + ChatColor.GREEN + amount + ChatColor.RED + "원이 제거되었습니다.");
					} else
						sender.sendMessage(ChatColor.RED + "플레이어가 접속중이 아닙니다.");
				}
				return true;
			} catch (IllegalArgumentException ex) {
				sender.sendMessage(ChatColor.RED + "액수는 숫자를 입력하세요.");
			}

		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 1) {
			if (sender instanceof Player && args[0].equals("") || "item".startsWith(args[0].toLowerCase()))
				list.add("item");
			if (sender instanceof Player && args[0].equals("") || "list".startsWith(args[0].toLowerCase()))
				list.add("list");
			if (sender instanceof Player && args[0].equals("") || "send".startsWith(args[0].toLowerCase()))
				list.add("send");
			if (sender instanceof Player && args[0].equals("") || "confirm".startsWith(args[0].toLowerCase()))
				list.add("confirm");
			if (!(sender instanceof Player) || (sender instanceof Player && ((Player) sender).isOp()))
				if (args[0].equals("") || "give".startsWith(args[0].toLowerCase())) {
					list.add("give");
				} else if (args[0].equals("") || "remove".startsWith(args[0].toLowerCase()))
					list.add("remove");
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("confirm") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("send")
					|| args[0].equalsIgnoreCase("s"))
				for (Player player : Bukkit.getOnlinePlayers())
					if (args[1].equalsIgnoreCase("")
							|| player.getName().toLowerCase().startsWith(args[1].toLowerCase()))
						list.add(player.getName());
			if (!(sender instanceof Player) || (sender instanceof Player && ((Player) sender).isOp()))
				if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("g")
						|| args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r"))
					for (Player player : Bukkit.getOnlinePlayers())
						if (args[1].equalsIgnoreCase("")
								|| player.getName().toLowerCase().startsWith(args[1].toLowerCase()))
							list.add(player.getName());
		}
		return list;
	}

	private class PlayerMoney {
		private final String name;
		private final BigInteger money;

		public PlayerMoney(Player player) {
			this.name = player.getName();
			this.money = ConfigStore.getPlayerMoney(player);
		}

		public String getName() {
			return name;
		}

		public BigInteger getMoney() {
			return money;
		}
	}

	private class Comparators implements Comparator<PlayerMoney> {
		@Override
		public int compare(PlayerMoney o1, PlayerMoney o2) {
			return o2.getMoney().compareTo(o1.getMoney());
		}
	}
}
