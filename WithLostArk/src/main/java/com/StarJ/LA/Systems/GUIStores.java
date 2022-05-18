package com.StarJ.LA.Systems;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.StarJ.LA.Core;
import com.StarJ.LA.Items.Buyable;
import com.StarJ.LA.Items.CookingIngredient;
import com.StarJ.LA.Items.CookingIngredient.IngredientType;
import com.StarJ.LA.Items.CookingItem;
import com.StarJ.LA.Items.DiggingItems;
import com.StarJ.LA.Items.FishItem;
import com.StarJ.LA.Items.InventoryItem;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.JewerlyItems;
import com.StarJ.LA.Items.PotionItems;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.Comparables.ItemComparator;

public enum GUIStores {
	exit(ChatColor.GREEN + "탈출의노래", 6 * 9) {
		@Override
		public void openGUI(Player player, int page) {
			List<Villages> list = HashMapStore.getVillages();
			int max = (list.size() + 1) / (5 * 9);
			if (page > max)
				page = max;
			if (page < 0)
				page = 0;
			for (int c = 5 * 9; c < 6 * 9; c++)
				inv.setItem(c, getBLACKEmpty());
			if (list.size() > 5 * 9)
				inv.setItem(inv.getSize() - 1, getInfoItemStack(page, max));
			for (int c = page * 5 * 9; c < (page + 1) * 5 * 9; c++) {
				if (list.size() <= c)
					break;
				inv.setItem(c % (5 * 9), getExitItemStack(player, list.get(c)));
			}
			player.openInventory(inv);
		}

		private ItemStack getExitItemStack(Player player, Villages village) {
			ItemStack item = new ItemStack(Material.CLOCK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + village.getName());
			Location loc = village.getLocation();
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "좌표 : " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
			lore.add(ChatColor.RED + "재사용대기시간 : 5분");
			BigInteger time = BigInteger.valueOf(System.currentTimeMillis())
					.subtract(HashMapStore.getExitCool(player.getUniqueId().toString()));
			BigInteger last = BigInteger.valueOf(1000 * 60 * 5);
			lore.add(ChatColor.AQUA + (time.compareTo(last) > 0 ? "사용 가능"
					: "남은 시간 : " + (last.subtract(time)).divide(BigInteger.valueOf(1000)).toString() + "s"));
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			if (clicked != null && !clicked.getType().equals(Material.AIR) && slot != inv.getSize() - 1) {
				int page = getInfoPage(inv.getItem(inv.getSize() - 1));
				List<Villages> list = HashMapStore.getVillages();
				if (list.size() > page * 5 * 9 + slot) {
					BigInteger time = BigInteger.valueOf(System.currentTimeMillis())
							.subtract(HashMapStore.getExitCool(player.getUniqueId().toString()));
					BigInteger last = BigInteger.valueOf(1000 * 60 * 5);
					if (time.compareTo(last) > 0 || player.getGameMode().equals(GameMode.CREATIVE)) {
						player.teleport(list.get(page * 5 * 9 + slot).getLocation());
						if (!player.getGameMode().equals(GameMode.CREATIVE))
							HashMapStore.setExitCool(player.getUniqueId().toString(),
									new Random().nextDouble() < 0.15 ? BigInteger.ZERO
											: BigInteger.valueOf(System.currentTimeMillis()));
					} else
						player.sendMessage(ChatColor.RED + "아직 사용할 수 없습니다. - "
								+ (last.subtract(time)).divide(BigInteger.valueOf(1000)).toString() + "s");

					player.closeInventory();
				}
			}
			return true;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			return false;
		}
	},
	bifrost(ChatColor.GREEN + "비프로스트", 1 * 9) {
		@Override
		public void openGUI(Player player, int page) {
			inv.setItem(3, getBifrostItemStack(player));
			inv.setItem(5, getBifrost2ItemStack(player));
			player.openInventory(inv);
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			if (clicked != null) {
				if (type.equals(ClickType.LEFT) || type.equals(ClickType.SHIFT_LEFT)) {
					if (slot == 3) {
						BigInteger time = BigInteger.valueOf(System.currentTimeMillis())
								.subtract(HashMapStore.getBifrostCool(player.getUniqueId().toString()));
						BigInteger last = BigInteger.valueOf(1000 * 60 * 15);
						if (time.compareTo(last) > 0 || player.getGameMode().equals(GameMode.CREATIVE)) {
							Villages bifrost = HashMapStore.getBifrost(player.getUniqueId().toString());
							if (bifrost != null) {
								player.teleport(bifrost.getLocation());
								if (!player.getGameMode().equals(GameMode.CREATIVE))
									HashMapStore.setBifrostCool(player.getUniqueId().toString(),
											new Random().nextDouble() < 0.15 ? BigInteger.ZERO
													: BigInteger.valueOf(System.currentTimeMillis()));
							} else
								player.sendMessage(ChatColor.RED + "비프로스트가 등록되어 있지않습니다.");
						} else
							player.sendMessage(ChatColor.RED + "아직 사용할 수 없습니다. - "
									+ (last.subtract(time)).divide(BigInteger.valueOf(1000)).toString() + "s");
						player.closeInventory();
					} else if (slot == 5) {
						BigInteger time = BigInteger.valueOf(System.currentTimeMillis())
								.subtract(HashMapStore.getBifrost2Cool(player.getUniqueId().toString()));
						BigInteger last = BigInteger.valueOf(1000 * 60 * 15);
						if (time.compareTo(last) > 0 || player.getGameMode().equals(GameMode.CREATIVE)) {
							Villages bifrost2 = HashMapStore.getBifrost2(player.getUniqueId().toString());
							if (bifrost2 != null) {
								player.teleport(bifrost2.getLocation());
								if (!player.getGameMode().equals(GameMode.CREATIVE))
									HashMapStore.setBifrost2Cool(player.getUniqueId().toString(),
											new Random().nextDouble() < 0.15 ? BigInteger.ZERO
													: BigInteger.valueOf(System.currentTimeMillis()));
							} else
								player.sendMessage(ChatColor.RED + "비프로스트가 등록되어 있지않습니다.");
						} else
							player.sendMessage(ChatColor.RED + "아직 사용할 수 없습니다. - "
									+ (last.subtract(time)).divide(BigInteger.valueOf(1000)).toString() + "s");
						player.closeInventory();
					}
				} else if (type.equals(ClickType.RIGHT) || type.equals(ClickType.SHIFT_RIGHT)) {
					if (slot == 3) {
						BigInteger time = BigInteger.valueOf(System.currentTimeMillis())
								.subtract(HashMapStore.getBifrostSaveCool(player.getUniqueId().toString()));
						BigInteger last = BigInteger.valueOf(1000 * 60 * 60 * 2);
						if (time.compareTo(last) > 0 || player.getGameMode().equals(GameMode.CREATIVE)) {
							HashMapStore.setBifrost(player.getUniqueId().toString(),
									new Villages("비프로스트", player.getLocation()));
							if (!player.getGameMode().equals(GameMode.CREATIVE))
								HashMapStore.setBifrostSaveCool(player.getUniqueId().toString(),
										new Random().nextDouble() < 0.15 ? BigInteger.ZERO
												: BigInteger.valueOf(System.currentTimeMillis()));
						} else
							player.sendMessage(ChatColor.RED + "아직 사용할 수 없습니다. - "
									+ (last.subtract(time)).divide(BigInteger.valueOf(1000)).toString() + "s");
						player.closeInventory();
					} else if (slot == 5) {
						BigInteger time = BigInteger.valueOf(System.currentTimeMillis())
								.subtract(HashMapStore.getBifrost2SaveCool(player.getUniqueId().toString()));
						BigInteger last = BigInteger.valueOf(1000 * 60 * 60 * 2);
						if (time.compareTo(last) > 0 || player.getGameMode().equals(GameMode.CREATIVE)) {
							HashMapStore.setBifrost2(player.getUniqueId().toString(),
									new Villages("비프로스트", player.getLocation()));
							if (!player.getGameMode().equals(GameMode.CREATIVE))
								HashMapStore.setBifrost2SaveCool(player.getUniqueId().toString(),
										new Random().nextDouble() < 0.15 ? BigInteger.ZERO
												: BigInteger.valueOf(System.currentTimeMillis()));
						} else
							player.sendMessage(ChatColor.RED + "아직 사용할 수 없습니다. - "
									+ (last.subtract(time)).divide(BigInteger.valueOf(1000)).toString() + "s");
						player.closeInventory();
					}
				}
			}
			return true;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}

		private ItemStack getBifrostItemStack(Player player) {
			ItemStack item = new ItemStack(Material.COMPASS);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "비프로스트");
			List<String> lore = new ArrayList<String>();

			BigInteger time1 = BigInteger.valueOf(System.currentTimeMillis())
					.subtract(HashMapStore.getBifrostCool(player.getUniqueId().toString()));
			BigInteger time2 = BigInteger.valueOf(System.currentTimeMillis())
					.subtract(HashMapStore.getBifrostSaveCool(player.getUniqueId().toString()));
			BigInteger last1 = BigInteger.valueOf(1000 * 60 * 15);
			BigInteger last2 = BigInteger.valueOf(1000 * 60 * 60 * 2);
			Villages bifrost = HashMapStore.getBifrost(player.getUniqueId().toString());
			if (bifrost != null) {
				Location loc = bifrost.getLocation();
				lore.add(ChatColor.WHITE + "좌표 : " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
			}
			lore.add(ChatColor.WHITE + "좌 클릭 - 이동 / 우클릭 - 저장");
			lore.add(ChatColor.RED + "재사용대기시간 : 15분 / 2시간");
			lore.add(
					ChatColor.AQUA + (bifrost == null ? "비프로스트 미지정"
							: time1.compareTo(last1) > 0 ? "이동 가능"
									: "남은 이동 시간 : "
											+ (last1.subtract(time1)).subtract(BigInteger.valueOf(1000)).toString()
											+ "s"));
			lore.add(ChatColor.AQUA + (time2.compareTo(last2) > 0 ? "저장 가능"
					: "남은 이동 시간 : " + (last2.subtract(time2)).divide(BigInteger.valueOf(1000)) + "s"));
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

		private ItemStack getBifrost2ItemStack(Player player) {
			ItemStack item = new ItemStack(Material.COMPASS);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "비프로스트2");
			List<String> lore = new ArrayList<String>();

			BigInteger time1 = BigInteger.valueOf(System.currentTimeMillis())
					.subtract(HashMapStore.getBifrost2Cool(player.getUniqueId().toString()));
			BigInteger time2 = BigInteger.valueOf(System.currentTimeMillis())
					.subtract(HashMapStore.getBifrost2SaveCool(player.getUniqueId().toString()));
			BigInteger last1 = BigInteger.valueOf(1000 * 60 * 15);
			BigInteger last2 = BigInteger.valueOf(1000 * 60 * 60 * 2);
			Villages bifrost2 = HashMapStore.getBifrost2(player.getUniqueId().toString());
			if (bifrost2 != null) {
				Location loc = bifrost2.getLocation();
				lore.add(ChatColor.WHITE + "좌표 : " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
			}
			lore.add(ChatColor.WHITE + "좌 클릭 - 이동 / 우클릭 - 저장");
			lore.add(ChatColor.RED + "재사용대기시간 : 15분 / 2시간");
			lore.add(
					ChatColor.AQUA + (bifrost2 == null ? "비프로스트2 미지정"
							: time1.compareTo(last1) > 0 ? "이동 가능"
									: "남은 이동 시간 : "
											+ (last1.subtract(time1).divide(BigInteger.valueOf(1000))).toString()
											+ "s"));
			lore.add(ChatColor.AQUA + (time2.compareTo(last2) > 0 ? "저장 가능"
					: "남은 이동 시간 : " + (last2.subtract(time2)).divide(BigInteger.valueOf(1000)) + "s"));
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

	},
	home(ChatColor.GREEN + "귀환의 노래", 1 * 9) {
		@Override
		public void openGUI(Player player, int page) {
			inv.setItem(4, getHomeItemStack(player, player.getBedSpawnLocation()));
			player.openInventory(inv);
		}

		private ItemStack getHomeItemStack(Player player, Location loc) {
			ItemStack item = new ItemStack(Material.CLOCK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "귀환의 노레");
			List<String> lore = new ArrayList<String>();
			if (loc != null) {
				lore.add(ChatColor.WHITE + "좌표 : " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
			} else
				lore.add(ChatColor.WHITE + "저장된 귀환 장소 없음");
			lore.add(ChatColor.RED + "재사용대기시간 : 5분");
			BigInteger time = BigInteger.valueOf(System.currentTimeMillis())
					.subtract(HashMapStore.getHomeCool(player.getUniqueId().toString()));
			BigInteger last = BigInteger.valueOf(1000 * 60 * 5);
			lore.add(ChatColor.AQUA + (loc != null
					? (time.compareTo(last) > 0 ? "사용 가능"
							: "남은 시간 : " + (last.subtract(time).divide(BigInteger.valueOf(1000))).toString() + "s")
					: "사용 불가"));
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			if (clicked != null && !clicked.getType().equals(Material.AIR) && slot == 4) {
				BigInteger time = BigInteger.valueOf(System.currentTimeMillis())
						.subtract(HashMapStore.getHomeCool(player.getUniqueId().toString()));
				BigInteger last = BigInteger.valueOf(1000 * 60 * 5);
				Location loc = player.getBedSpawnLocation();
				if (loc != null) {
					if (time.compareTo(last) > 0 || player.getGameMode().equals(GameMode.CREATIVE)) {
						player.teleport(loc);
						if (!player.getGameMode().equals(GameMode.CREATIVE))
							HashMapStore.setHomeCool(player.getUniqueId().toString(),
									new Random().nextDouble() < 0.15 ? BigInteger.ZERO
											: BigInteger.valueOf(System.currentTimeMillis()));
					} else
						player.sendMessage(ChatColor.RED + "아직 사용할 수 없습니다. - "
								+ (last.subtract(time)).divide(BigInteger.valueOf(1000)).toString() + "s");
				} else
					player.sendMessage(ChatColor.RED + "저장된 귀환장소가 없어서 귀환할 수 없습니다.");
				player.closeInventory();
			}
			return true;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}

	},
	trash(ChatColor.GRAY + "잡템상인", 4 * 9) {
		@Override
		public void openGUI(Player player, int page) {
			if (player.isSneaking() && player.getInventory().getItemInMainHand() != null
					&& !player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				ItemStack main = player.getInventory().getItemInMainHand();
				player.sendMessage(ChatColor.GOLD + main.getType().name() + ChatColor.WHITE + "의 가격 : "
						+ ChatColor.GREEN + ShopStores.getPrice(main).toString());
			} else
				player.openInventory(Bukkit.createInventory(null, inv.getSize(), this.getTitle()));
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			return false;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			BigInteger amount = BigInteger.ZERO;
			for (ItemStack i : inv.getContents())
				if (i != null) {
					amount = amount.add(ShopStores.getPrice(i).multiply(BigInteger.valueOf(i.getAmount())));
				}
			if (amount.compareTo(BigInteger.ZERO) > 0) {
				ConfigStore.setPlayerMoney(player, ConfigStore.getPlayerMoney(player).add(amount));
				player.sendMessage(ChatColor.WHITE + "잡템을 팔아 " + ChatColor.GREEN + amount.toString() + ChatColor.WHITE
						+ "원을 얻었습니다.");
			}
			inv.clear();
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return false;
		}
	},
	seller(ChatColor.AQUA + "잡화상인", 6 * 9) {
		@Override
		public void openGUI(Player player, int page) {
			{
				Items i = Items.exp;
				Buyable buy = (Buyable) i;
				inv.setItem(10, buy.getInfoItemStack(player, i.getItemStack()));
			}
			{
				Items i = Items.ench;
				Buyable buy = (Buyable) i;
				inv.setItem(12, buy.getInfoItemStack(player, i.getItemStack()));
			}
			{
				Items i = Items.potion;
				Buyable buy = (Buyable) i;
				inv.setItem(14, buy.getInfoItemStack(player, i.getItemStack()));
			}
			{
				Items i = Items.tool;
				Buyable buy = (Buyable) i;
				inv.setItem(16, buy.getInfoItemStack(player, i.getItemStack()));
			}
			{
				Items i = Items.equip;
				Buyable buy = (Buyable) i;
				inv.setItem(28, buy.getInfoItemStack(player, i.getItemStack()));
			}
			{
				Items i = Items.expendables;
				Buyable buy = (Buyable) i;
				inv.setItem(30, buy.getInfoItemStack(player, i.getItemStack()));
			}
			{
				Items i = Items.animal;
				Buyable buy = (Buyable) i;
				inv.setItem(32, buy.getInfoItemStack(player, i.getItemStack()));
			}
			{
				Items i = Items.rod;
				Buyable buy = (Buyable) i;
				inv.setItem(34, buy.getInfoItemStack(player, i.getItemStack()));
			}
			{
				Items i = Items.fly;
				Buyable buy = (Buyable) i;
				inv.setItem(46, buy.getInfoItemStack(player, i.getItemStack()));
			}
			{
				Items i = Items.inv;
				Buyable buy = (Buyable) i;
				inv.setItem(48, buy.getInfoItemStack(player, i.getItemStack()));
			}
			{
				Items i = Items.fishbox;
				Buyable buy = (Buyable) i;
				inv.setItem(50, buy.getInfoItemStack(player, i.getItemStack()));
			}
			//
			player.openInventory(inv);
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			Items i = Items.valueOf(clicked);
			if (raw_slot < inv.getSize() && i != null) {
				if (type.equals(ClickType.LEFT) && i instanceof Buyable) {
					Buyable buy = (Buyable) i;
					BigInteger has = ConfigStore.getPlayerMoney(player);
					if (has.compareTo(buy.getMoney(player)) >= 0 || player.getGameMode().equals(GameMode.CREATIVE)) {
						if (!player.getGameMode().equals(GameMode.CREATIVE))
							ConfigStore.setPlayerMoney(player, has.subtract(buy.getMoney(player)));
						if (buy instanceof InventoryItem) {
							((InventoryItem) buy).Use(player, new ItemStack(Material.AIR), null, null);
							openGUI(player, 0);
						} else
							player.getInventory().addItem(i.getItemStack(buy.getCount()));
						player.sendMessage(ChatColor.GREEN + i.getKey() + ChatColor.WHITE + "을 구매하였습니다.");
					} else
						player.sendMessage(ChatColor.RED + "돈이 부족합니다.");

				}
			}
			return true;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}
	},
	warp(ChatColor.GOLD + "워프", 6 * 9) {
		@Override
		public void openGUI(Player player, int page) {
			List<Villages> list = HashMapStore.getVillages();
			int max = (list.size() + 1) / (5 * 9);
			if (page > max)
				page = max;
			if (page < 0)
				page = 0;
			for (int c = 5 * 9; c < 6 * 9; c++)
				inv.setItem(c, getBLACKEmpty());
			if (list.size() > 5 * 9)
				inv.setItem(inv.getSize() - 1, getInfoItemStack(page, max));

			for (int c = page * 5 * 9; c < (page + 1) * 5 * 9; c++) {
				if (list.size() <= c)
					break;
				inv.setItem(c, getWarpItemStack(player, list.get(c)));
			}
			player.openInventory(inv);
		}

		private ItemStack getWarpItemStack(Player player, Villages village) {
			ItemStack item = new ItemStack(Material.CLOCK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + village.getName());
			Location loc = village.getLocation();
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "좌표 : " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			if (clicked != null && !clicked.getType().equals(Material.AIR) && slot != inv.getSize() - 1) {
				int page = getInfoPage(inv.getItem(inv.getSize() - 1));
				List<Villages> list = HashMapStore.getVillages();
				if (list.size() > page * 5 * 9 + slot) {
					player.teleport(list.get(page * 5 * 9 + slot).getLocation());
					player.closeInventory();
				}
			}
			return true;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}
	},
	coin(ChatColor.GOLD + "코인 상인", 1 * 9) {
		@Override
		public void openGUI(Player player, int page) {
			player.sendMessage(ChatColor.RED + "아직 준비중입니다.");

//			player.openInventory(inv);
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			return true;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}
	},
	extra(ChatColor.GREEN + "확장 인벤토리", 9) {
		@Override
		public void openGUI(Player player, int page) {
			int size = ConfigStore.getPlayerInventorySize(player);
			List<ItemStack> list = ConfigStore.getPlayerInventory(player);
			if (size > 0) {
				if (size <= 5 * 9) {
					Inventory inv = Bukkit.createInventory(null, size, getTitle());
					for (int c = 0; c < list.size(); c++)
						if (c < size) {
							inv.setItem(c, list.get(c));
						} else
							break;
					player.openInventory(inv);
				} else {
					int max = (size + 1) / (5 * 9);
					Inventory inv = Bukkit.createInventory(null, page == max ? size % (5 * 9) + 9 : 6 * 9, getTitle());
					if (max > 1 && size % (5 * 9) == 0)
						max = max - 1;
					for (int c = page * 5 * 9; c < (page + 1) * 5 * 9; c++)
						if (c < size && list.size() > c) {
							inv.setItem(c % (5 * 9), list.get(c));
						} else
							break;
					for (int c = inv.getSize() - 9; c < inv.getSize(); c++)
						inv.setItem(c, getBLACKEmpty());
					inv.setItem(inv.getSize() - 1, getInfoItemStack(page, max));
					// 45 46 47 48 49 50 51 52 53
					player.openInventory(inv);
				}
			} else
				player.sendMessage(ChatColor.RED + "확장된 인벤토리가 없습니다.");
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			int size = ConfigStore.getPlayerInventorySize(player);
			if (size > 45 && slot >= inv.getSize() - 9) {
				int page = getInfoPage(inv.getItem(inv.getSize() - 1));
				int max = (size + 1) / (5 * 9);
				if (max > 1 && size % (5 * 9) == 0)
					max = max - 1;
				if (slot == inv.getSize() - 1)
					if ((type.equals(ClickType.LEFT) || type.equals(ClickType.SHIFT_LEFT)) && page > 0) {
						openGUI(player, page - 1);
					} else if ((type.equals(ClickType.RIGHT) || type.equals(ClickType.SHIFT_RIGHT)) && page < max)
						openGUI(player, page + 1);
				return true;
			}
			return false;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			int size = ConfigStore.getPlayerInventorySize(player);
			int page = getInfoPage(inv.getItem(inv.getSize() - 1));
			List<ItemStack> items = ConfigStore.getPlayerInventory(player);
			for (int c = page * 5 * 9; c < (page + 1) * 5 * 9; c++) {
				if (inv.getSize() > c % (5 * 9) && c < size) {
					items.set(c, inv.getItem(c % (5 * 9)));
				} else
					break;
			}
			ConfigStore.setPlayerInventory(player, items);
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return false;
		}
	},
	info(ChatColor.GREEN + "정보창", 54) {
		@Override
		public void openGUI(Player player, int page) {
			Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "정보창");
			ItemStack none = getBLACKEmpty();
			for (int c = 0; c < inv.getSize(); c++)
				inv.setItem(c, none);
			Jobs job = ConfigStore.getJob(player);
			ItemStack[] items = ConfigStore.getConsumeItems(player);
			if (items.length >= 1 && items[0] != null) {
				inv.setItem(21, items[0]);
			} else
				inv.setItem(21, getGREENEmpty("소모템 칸", "소모템을 올려주세요"));

			if (items.length >= 2 && items[1] != null) {
				inv.setItem(22, items[1]);
			} else
				inv.setItem(22, getGREENEmpty("소모템 칸", "소모템을 올려주세요"));

			if (items.length >= 3 && items[2] != null) {
				inv.setItem(23, items[2]);
			} else
				inv.setItem(23, getGREENEmpty("소모템 칸", "소모템을 올려주세요"));

			if (items.length >= 4 && items[3] != null) {
				inv.setItem(24, items[3]);
			} else
				inv.setItem(24, getGREENEmpty("소모템 칸", "소모템을 올려주세요"));
			//
			inv.setItem(10, Items.helmet.getItemStack(ConfigStore.getHelmetLevel(player, job)));
			inv.setItem(19, Items.chestplate.getItemStack(ConfigStore.getChestplateLevel(player, job)));
			inv.setItem(28, Items.leggings.getItemStack(ConfigStore.getLeggingsLevel(player, job)));
			inv.setItem(37, Items.boots.getItemStack(ConfigStore.getBootsLevel(player, job)));
			//
			inv.setItem(12, getJobInfo(player, job));
			if (job != null) {
				Skills[] skills = job.getSkills(player);
				int change_slot = HashMapStore.getSkillChangeSlot(player);
				inv.setItem(30, change_slot == 1 ? getChangeItemStack() : skills[0].getInfoItemStack());
				inv.setItem(31, change_slot == 2 ? getChangeItemStack() : skills[1].getInfoItemStack());
				inv.setItem(32, change_slot == 3 ? getChangeItemStack() : skills[2].getInfoItemStack());
				inv.setItem(33, change_slot == 4 ? getChangeItemStack() : skills[3].getInfoItemStack());
				inv.setItem(39, change_slot == 5 ? getChangeItemStack() : skills[4].getInfoItemStack());
				inv.setItem(40, change_slot == 6 ? getChangeItemStack() : skills[5].getInfoItemStack());
				inv.setItem(41, change_slot == 7 ? getChangeItemStack() : skills[6].getInfoItemStack());
				inv.setItem(42, change_slot == 8 ? getChangeItemStack() : skills[7].getInfoItemStack());

				inv.setItem(35, job.getSpecialArts().getItemStack());
				inv.setItem(44, job.getIdentity().getItemStack());
				//
				inv.setItem(13, job.getJobitems(player).get(7));
				//
			}
			inv.setItem(15, getStatInfo(player));
			inv.setItem(17, getBasicsInfo(player));
			player.openInventory(inv);
		}

		private ItemStack getChangeItemStack() {
			ItemStack item = new ItemStack(Material.BEDROCK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.RED + "슬롯 변경중");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "클릭시 변경 취소");
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			if (raw_slot == 10 || raw_slot == 19 || raw_slot == 28 || raw_slot == 37) {
				jewerly.openGUI(player, 0);
			} else if (raw_slot == 12) {
				job.openGUI(player, 0);
			} else if (raw_slot == 17) {
				basics.openGUI(player, 0);
			} else if (raw_slot >= 21 && raw_slot <= 24) {
				ItemStack cursor = player.getItemOnCursor();
				int now = raw_slot - 21;
				ItemStack[] items = ConfigStore.getConsumeItems(player);
				if (cursor != null && !cursor.getType().equals(Material.AIR)) {
					Items i = Items.valueOf(cursor);
					if (i != null && (i instanceof CookingItem || i instanceof PotionItems)) {
						ItemStack pre = items[now];
						items[now] = cursor;
						ConfigStore.setConsumeItems(player, items);
						Items pre_i = Items.valueOf(pre);
						player.setItemOnCursor(pre_i != null ? pre : new ItemStack(Material.AIR));
						openGUI(player, 0);
					}
				} else if (clicked != null) {
					Items i = Items.valueOf(clicked);
					if (i != null) {
						ItemStack pre = items[now];
						items[now] = null;
						ConfigStore.setConsumeItems(player, items);
						Items pre_i = Items.valueOf(pre);
						player.setItemOnCursor(pre_i != null ? pre : new ItemStack(Material.AIR));
						openGUI(player, 0);
					}
				}
			} else if (raw_slot == 30 || raw_slot == 31 || raw_slot == 32 || raw_slot == 33 || raw_slot == 39
					|| raw_slot == 40 || raw_slot == 41 || raw_slot == 42) {
				Jobs job = ConfigStore.getJob(player);
				if (type.equals(ClickType.RIGHT)) {
					for (int num = 1; num <= 8; num++)
						ConfigStore.setSkillSlot(player, job, num, num);
					HashMapStore.setSkillChangeSlot(player, -1);
				} else {
					if (job != null) {
						int skill_slot = (raw_slot % 9 - 2 + (raw_slot / 9 - 3) * 4);
						int pre_slot = HashMapStore.getSkillChangeSlot(player);
						if (pre_slot == -1) {
							player.closeInventory();
							HashMapStore.setSkillChangeSlot(player, skill_slot);
						} else if (pre_slot == skill_slot) {
							HashMapStore.setSkillChangeSlot(player, -1);
						} else {
							HashSet<Integer> slots = ConfigStore.getSkillSlots(player, job);
							if (slots.size() < 8 || slots.contains(-1)) {
								for (int num = 1; num <= 8; num++)
									ConfigStore.setSkillSlot(player, job, num, num);
								slots = ConfigStore.getSkillSlots(player, job);
							}
							Integer[] slot_array = slots.toArray(Integer[]::new);
							ConfigStore.setSkillSlot(player, job, pre_slot, slot_array[skill_slot - 1]);
							ConfigStore.setSkillSlot(player, job, skill_slot, slot_array[pre_slot - 1]);

							HashMapStore.setSkillChangeSlot(player, -1);
						}
					}
					openGUI(player, 0);
				}
			} else {
				ItemStack cursor = player.getItemOnCursor();
				if (cursor == null || cursor.getType().equals(Material.AIR)) {
					if (clicked != null && !clicked.getType().equals(Material.AIR)) {
						Items i = Items.valueOf(clicked);
						if (i != null && (i instanceof CookingItem || i instanceof PotionItems))
							return false;
					}
				} else {
					if (clicked != null && !clicked.getType().equals(Material.AIR)) {
						Items i = Items.valueOf(clicked);
						if (i != null && (i instanceof CookingItem || i instanceof PotionItems))
							return false;
					} else
						return false;

				}
			}

			return true;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			HashMapStore.setSkillChangeSlot(player, -1);
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}

		private ItemStack getBasicsInfo(Player player) {
			ItemStack item = new ItemStack(Material.IRON_PICKAXE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "내실");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "클릭시 내실창으로 이동");
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

		private ItemStack getStatInfo(Player player) {
			ItemStack item = new ItemStack(Material.FLOWER_BANNER_PATTERN);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "스탯");
			List<String> lore = new ArrayList<String>();
			for (Stats stat : Stats.values()) {
				lore.add(ChatColor.GREEN + stat.getDisplayname() + " : " + (int) stat.getStat(player));
				if (stat.equals(Stats.Speed)) {
					lore.add(ChatColor.WHITE + " - 쿨타임 "
							+ Math.round((1 - Stats.Speed.getStatPercent(player)) * 10000.d) / 100.0d + "% 감소");
				} else if (stat.equals(Stats.Specialization)) {
					Jobs job = ConfigStore.getJob(player);
					if (job != null)
						for (String l : job.getSpecialinfo())
							lore.add(ChatColor.WHITE + l + " : "
									+ Math.round((Stats.Specialization.getStatPercent(player) - 1) * 10000.0d) / 100.0d
									+ "%");
				} else if (stat.equals(Stats.Critical)) {
					lore.add(ChatColor.WHITE + " - 치명타 확률 "
							+ Math.round((0.01 + Stats.Critical.getStatPercent(player)) * 10000.0d) / 100.0 + "%");
				} else if (stat.equals(Stats.Enduration)) {
					lore.add(ChatColor.WHITE + " - 피해량 "
							+ Math.round((1 - Stats.Enduration.getStatPercent(player)) * 10000.d) / 100.0d + "% 감소");
				}
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

		private ItemStack getJobInfo(Player player, Jobs job) {
			ItemStack item = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			meta.setDisplayName(job != null ? job.getDisplayname() : ChatColor.WHITE + "무직");
			meta.setOwningPlayer(player);
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "클릭시 직업 선택");
			for (Jobs j : Jobs.values()) {
				double stat = Stats.getAllStats(player, job);
				if (stat > 0 || (job != null && job.getKey().equals(j.getKey())))
					lore.add(j.getDisplayname() + " 스탯 : " + (int) stat);
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

	},
	job(ChatColor.GREEN + "직업", 3 * 9) {
		@Override
		public void openGUI(Player player, int page) {
			Inventory inv = Bukkit.createInventory(null, 3 * 9, ChatColor.GREEN + "직업");
			List<Jobs> list = Jobs.values();
			for (int c = 0; c < list.size(); c++) {
				if (c < inv.getSize())
					inv.setItem(c, getJobItem(list.get(c)));
			}
			inv.setItem(inv.getSize() - 1, getBack());
			player.openInventory(inv);
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			Jobs job = Jobs.valueOf(clicked);
			if (job != null) {
				if (ConfigStore.getPlayerStatus(player))
					ConfigStore.changePlayerStatus(player);
				new BukkitRunnable() {
					@Override
					public void run() {
						ConfigStore.changePlayerStatus(player);
					}
				}.runTaskLater(Core.getCore(), 1);
				ConfigStore.setJob(player, job);
				player.sendMessage(ChatColor.RED + job.getDisplayname() + "을 직업으로 고르셨습니다.");
				player.closeInventory();
			} else if (raw_slot == inv.getSize() - 1)
				info.openGUI(player, 0);
			return true;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			return false;

		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}

		private ItemStack getJobItem(Jobs job) {
			ItemStack i = new ItemStack(Material.PAPER);
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName(job.getDisplayname());
			meta.setLocalizedName(job.getKey());
			i.setItemMeta(meta);
			return i;
		}
	},
	jewerly(ChatColor.AQUA + "보석창", 54) {
		@Override
		public void openGUI(Player player, int page) {
			Inventory inv = Bukkit.createInventory(null, 54, ChatColor.AQUA + "보석창");
			ItemStack none = getBLACKEmpty();
			for (int c = 0; c < inv.getSize(); c++)
				inv.setItem(c, none);
			List<JewerlyItems> list = ConfigStore.getJewerlyItems(player);
			// HELMET
			inv.setItem(10, list.get(0).getItemStack());
			inv.setItem(1, list.get(1).getItemStack());
			inv.setItem(2, list.get(2).getItemStack());
			inv.setItem(3, list.get(3).getItemStack());
			inv.setItem(12, list.get(4).getItemStack());
			// CHESTPLATE
			inv.setItem(28, list.get(5).getItemStack());
			inv.setItem(30, list.get(6).getItemStack());
			inv.setItem(37, list.get(7).getItemStack());
			inv.setItem(38, list.get(8).getItemStack());
			inv.setItem(39, list.get(9).getItemStack());
			inv.setItem(46, list.get(10).getItemStack());
			inv.setItem(47, list.get(11).getItemStack());
			inv.setItem(48, list.get(12).getItemStack());
			// LEGGINGS
			inv.setItem(23, list.get(13).getItemStack());
			inv.setItem(14, list.get(14).getItemStack());
			inv.setItem(5, list.get(15).getItemStack());
			inv.setItem(6, list.get(16).getItemStack());
			inv.setItem(7, list.get(17).getItemStack());
			inv.setItem(16, list.get(18).getItemStack());
			inv.setItem(25, list.get(19).getItemStack());
			// BOOTS
			inv.setItem(41, list.get(20).getItemStack());
			inv.setItem(50, list.get(21).getItemStack());
			inv.setItem(43, list.get(22).getItemStack());
			inv.setItem(52, list.get(23).getItemStack());

			//
			inv.setItem(inv.getSize() - 1, getBack());
			player.openInventory(inv);
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			JewerlyItems cursor = JewerlyItems.valueOf(player.getItemOnCursor());
			JewerlyItems click = JewerlyItems.valueOf(clicked);
			List<JewerlyItems> list = ConfigStore.getJewerlyItems(player);
			if (clicked != null && !type.equals(ClickType.DOUBLE_CLICK))
				// HELMET
				if (raw_slot == 10) {
					list.set(0, cursor != null ? cursor : Items.jewerly_zero);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 1) {
					list.set(1, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 2) {
					list.set(2, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 3) {
					list.set(3, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 12) {
					list.set(4, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
					// CHESTPLATE
				} else if (raw_slot == 28) {
					list.set(5, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 30) {
					list.set(6, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 37) {
					list.set(7, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 38) {
					list.set(8, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 39) {
					list.set(9, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 46) {
					list.set(10, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 47) {
					list.set(11, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 48) {
					list.set(12, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
					// LEGGINGS
				} else if (raw_slot == 23) {
					list.set(13, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 14) {
					list.set(14, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 5) {
					list.set(15, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 6) {
					list.set(16, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 7) {
					list.set(17, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 16) {
					list.set(18, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 25) {
					list.set(19, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
					// BOOTS
				} else if (raw_slot == 41) {
					list.set(20, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 50) {
					list.set(21, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 43) {
					list.set(22, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == 52) {
					list.set(23, cursor);
					if (!click.getKey().equals(Items.jewerly_zero.getKey())) {
						player.setItemOnCursor(clicked);
					} else
						player.setItemOnCursor(new ItemStack(Material.AIR));
					ConfigStore.setJewerlyItems(player, list);
					openGUI(player, 0);
				} else if (raw_slot == inv.getSize() - 1)
					info.openGUI(player, 0);
			return raw_slot < 54;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			for (int raw_slot : raw_slots)
				if (raw_slot < 54)
					return true;
			return false;
		}
	},
	basics(ChatColor.GREEN + "내실", 9) {

		@Override
		public void openGUI(Player player, int page) {
			Inventory inv = Bukkit.createInventory(null, 6 * 9, ChatColor.GREEN + "내실");
			inv.setItem(11, getBasicItem(player, Basics.Farming));
			inv.setItem(13, getBasicItem(player, Basics.Fishing));
			inv.setItem(15, getBasicItem(player, Basics.Hunting));
			//
			inv.setItem(29, getBasicItem(player, Basics.Mining));
			inv.setItem(31, getBasicItem(player, Basics.Chopping));
			inv.setItem(33, getBasicItem(player, Basics.Digging));
			//
			inv.setItem(48, getBasicItem(player, Basics.Cooking));
			inv.setItem(50, getBasicItem(player, Basics.Potioning));

			inv.setItem(inv.getSize() - 1, getBack());
			player.openInventory(inv);
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			if (raw_slot == inv.getSize() - 1)
				info.openGUI(player, 0);
			return true;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}

		private ItemStack getBasicItem(Player player, Basics basic) {
			ItemStack i = new ItemStack(basic.getIcon());
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + basic.getDisplyname());
			List<String> lore = new ArrayList<String>();
			int level = basic.getLevel(player);
			lore.add(ChatColor.YELLOW + "레벨 : " + level + " / " + basic.getMaxLevel());
			if (level < basic.getMaxLevel())
				lore.add(ChatColor.WHITE + "경험치 : " + basic.getEXP(player) + " / " + basic.getNeedEXP(level));
			lore.addAll(basic.getLore(level));
			meta.setLore(lore);
			i.setItemMeta(meta);
			return i;
		}
	},
	cooking(ChatColor.GOLD + "요리", 1 * 9) {

		@Override
		public void openGUI(Player player, int page) {
			Inventory inv = Bukkit.createInventory(null, 1 * 9, ChatColor.GOLD + "요리");
			int level = Basics.Cooking.getLevel(player);
			for (int c = (level / 10) + 4; c < 9; c++)
				inv.setItem(c, getBROWNEmpty());
			player.openInventory(inv);
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			return new ItemComparator().compare(clicked, getBROWNEmpty()) >= 3;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			int amount = 0;
			HashSet<String> list = new HashSet<String>();
			HashSet<IngredientType> type = new HashSet<IngredientType>();
			for (ItemStack item : inv.getContents())
				if (new ItemComparator().compare(item, getBROWNEmpty()) < 3)
					if (item != null) {
						Items i = Items.valueOf(item);
						if (i != null && i instanceof CookingIngredient) {
							CookingIngredient co_i = (CookingIngredient) i;
							amount += co_i.getHelath() * item.getAmount();
							list.add(co_i.getKey());
							type.add(co_i.getIngredienttype());
						} else {
							if (player.getInventory().firstEmpty() != -1) {
								player.getInventory().addItem(item);
							} else
								player.getWorld().dropItem(player.getEyeLocation(), item);
						}
					}
			amount *= (1 + list.size() * 0.1) * (1 + type.size() * 0.5);
			if (amount > 0) {
				ItemStack item = Items.cooking.getItemStack(amount, Basics.Cooking.getLevel(player));
				if (player.getInventory().firstEmpty() != -1) {
					player.getInventory().addItem(item);
				} else
					player.getWorld().dropItem(player.getEyeLocation(), item);
				if (!player.getGameMode().equals(GameMode.CREATIVE))
					Basics.Cooking.addEXP(player, CookingItem.getHealth(item) / 100);
			}
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return false;

		}

	},
	potioning(ChatColor.GOLD + "양조", 2 * 9) {
		@Override
		public void openGUI(Player player, int page) {
			Inventory inv = Bukkit.createInventory(null, 3 * 9, ChatColor.GOLD + "양조");
			for (int c = 0; c < 27; c++)
				if (c == 4) {
					inv.setItem(c, getBLACKEmpty());
				} else
					inv.setItem(c, getBROWNEmpty());
			player.openInventory(inv);
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			if (raw_slot == 4) {
				if (clicked != null) {
					ItemStack air = new ItemStack(Material.AIR);
					if (clicked.getType().equals(getBLACKEmpty().getType())) {
						ItemStack cursor = player.getItemOnCursor();
						Items i = Items.valueOf(cursor);
						if (i != null && i instanceof DiggingItems) {
							DiggingItems dig = (DiggingItems) i;
							if (clicked.getType().equals(getBLACKEmpty().getType())) {
								player.setItemOnCursor(air);
							} else
								player.setItemOnCursor(clicked);
							inv.setItem(4, cursor);
							for (int c = 18; c < 18 + dig.getSlotCount(); c++)
								inv.setItem(c, air);
						}
					} else {
						ItemStack cursor = player.getItemOnCursor();
						Items i = Items.valueOf(cursor);
						if (i != null && i instanceof DiggingItems) {
							DiggingItems dig = (DiggingItems) i;
							player.setItemOnCursor(clicked);
							inv.setItem(4, cursor);
							for (int c = 18; c < 18 + dig.getSlotCount(); c++)
								inv.setItem(c, air);
						} else {
							if (cursor == null || cursor.getType().equals(Material.AIR)) {
								player.setItemOnCursor(clicked);
								inv.setItem(4, getBLACKEmpty());
								for (int c = 18; c < inv.getSize(); c++)
									inv.setItem(c, getBROWNEmpty());
							}
							return true;
						}
					}
				}
			}
			return clicked != null && (clicked.getType().equals(getBLACKEmpty().getType())
					|| clicked.getType().equals(getBROWNEmpty().getType()));
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			ItemStack blueprint = inv.getItem(4);
			Items i = Items.valueOf(blueprint);
			if (i != null && i instanceof DiggingItems) {
				DiggingItems dig = (DiggingItems) i;
				ItemStack[] items = new ItemStack[dig.getSlotCount()];
				for (int c = 0; c < items.length; c++)
					items[c] = inv.getItem(c + 18);
				if (dig.canMake(items)) {
					int level = Basics.Potioning.getLevel(player);
					int amount = dig.getResultCount();
					Random r = new Random();
					double chance = Basics.Potioning.getChance(level).doubleValue();
					if (level > 20 && r.nextDouble() < chance / 3)
						amount += level / 16;
					for (int c = 0; c < amount; c++) {
						PotionItems potion = dig.getPotion();
						ItemStack item = potion.getItemStack(level);
						if (player.getInventory().firstEmpty() != -1) {
							player.getInventory().addItem(item);
						} else
							player.getWorld().dropItemNaturally(player.getEyeLocation(), item);
					}
					if (r.nextDouble() < chance / 2)
						if (player.getInventory().firstEmpty() != -1) {
							player.getInventory().addItem(blueprint);
						} else
							player.getWorld().dropItemNaturally(player.getEyeLocation(), blueprint);
					return false;
				}
			}
			for (ItemStack item : inv.getContents())
				if (item != null && !item.getType().equals(getBLACKEmpty().getType())
						&& !item.getType().equals(getBROWNEmpty().getType()))
					if (item != null) {
						if (player.getInventory().firstEmpty() != -1) {
							player.getInventory().addItem(item);
						} else
							player.getWorld().dropItem(player.getEyeLocation(), item);
					}
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return false;
		}

	},
	enchant(ChatColor.DARK_PURPLE + "인챈트", 6 * 9) {
		@Override
		public void openGUI(Player player, int page) {
			String key = player.getUniqueId().toString();
			ItemStack tool = HashMapStore.getEnchantItemStack(key);
			EnchantsType enchant = HashMapStore.getEnchantType(key);
			ItemStack empty = getBLACKEmpty();
			ItemStack brown = getBROWNEmpty();
			ItemStack red = getREDEmpty();
			ItemStack book = HashMapStore.getEnchantBook(key);
			if (tool == null) {
				// 기본
				Inventory inv = Bukkit.createInventory(null, 1 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c != 4) {
						inv.setItem(c, brown);
					} else
						inv.setItem(c, empty);
				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Hoe)) {
				Inventory inv = Bukkit.createInventory(null, 3 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 행운
				ench = Enchantment.LOOT_BONUS_BLOCKS;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Pickaxe) || enchant.equals(EnchantsType.Shovel)) {
				Inventory inv = Bukkit.createInventory(null, 5 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 효율
				ench = Enchantment.DIG_SPEED;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 섬세한 손길
				ench = Enchantment.SILK_TOUCH;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 행운
				ench = Enchantment.LOOT_BONUS_BLOCKS;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Shears)) {
				Inventory inv = Bukkit.createInventory(null, 3 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 효율
				ench = Enchantment.DIG_SPEED;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Fishing_Rod)) {
				Inventory inv = Bukkit.createInventory(null, 4 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 바다의 행운
				ench = Enchantment.LUCK;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 미끼
				ench = Enchantment.LURE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Axe)) {
				Inventory inv = Bukkit.createInventory(null, 5 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 효율
				ench = Enchantment.DIG_SPEED;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 섬세한 손길
				ench = Enchantment.SILK_TOUCH;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 행운
				ench = Enchantment.LOOT_BONUS_BLOCKS;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 날카로움
				ench = Enchantment.DAMAGE_ALL;
				slot = 6;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 강타
				ench = Enchantment.DAMAGE_UNDEAD;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 살충
				ench = Enchantment.DAMAGE_ARTHROPODS;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Sword)) {
				Inventory inv = Bukkit.createInventory(null, 5 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 날카로움
				ench = Enchantment.DAMAGE_ALL;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 강타
				ench = Enchantment.DAMAGE_UNDEAD;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 살충
				ench = Enchantment.DAMAGE_ARTHROPODS;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				// 밀치기
				ench = Enchantment.KNOCKBACK;
				slot = 6;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 발화
				ench = Enchantment.FIRE_ASPECT;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 휩쓸기
				ench = Enchantment.SWEEPING_EDGE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 약탈
				ench = Enchantment.LOOT_BONUS_MOBS;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Bow)) {
				Inventory inv = Bukkit.createInventory(null, 6 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 힘
				ench = Enchantment.ARROW_DAMAGE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 밀어내기
				ench = Enchantment.ARROW_KNOCKBACK;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 화염
				ench = Enchantment.ARROW_FIRE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 무한
				ench = Enchantment.ARROW_INFINITE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Crossbow)) {
				Inventory inv = Bukkit.createInventory(null, 5 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 빠른장전
				ench = Enchantment.QUICK_CHARGE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 다중 발사
				ench = Enchantment.MULTISHOT;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 관통
				ench = Enchantment.PIERCING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Trident)) {
				Inventory inv = Bukkit.createInventory(null, 6 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 충성
				ench = Enchantment.LOYALTY;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 찌르기
				ench = Enchantment.IMPALING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 급류
				ench = Enchantment.RIPTIDE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book,
						ChatColor.DARK_RED + "집전과 동시 적용 불가", ChatColor.GRAY + "동시 적용시 급류만 적용"));
				// 집전
				ench = Enchantment.CHANNELING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book,
						ChatColor.DARK_RED + "급류와 동시 적용 불가", ChatColor.GRAY + "동시 적용시 급류만 적용"));

				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Helmet)) {
				Inventory inv = Bukkit.createInventory(null, 6 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 보호
				ench = Enchantment.PROTECTION_ENVIRONMENTAL;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 화염으로부터 보호
				ench = Enchantment.PROTECTION_FIRE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 폭발로부터 보호
				ench = Enchantment.PROTECTION_EXPLOSIONS;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 발사체로부터 보호
				ench = Enchantment.PROTECTION_PROJECTILE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				// 가시
				ench = Enchantment.THORNS;
				slot = 6;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 호흡
				ench = Enchantment.OXYGEN;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 친수성
				ench = Enchantment.WATER_WORKER;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Chestplate) || enchant.equals(EnchantsType.Leggings)) {
				Inventory inv = Bukkit.createInventory(null, 4 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 보호
				ench = Enchantment.PROTECTION_ENVIRONMENTAL;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 화염으로부터 보호
				ench = Enchantment.PROTECTION_FIRE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				// 폭발로부터 보호
				ench = Enchantment.PROTECTION_EXPLOSIONS;
				slot = 6;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 발사체로부터 보호
				ench = Enchantment.PROTECTION_PROJECTILE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 가시
				ench = Enchantment.THORNS;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Boots)) {
				Inventory inv = Bukkit.createInventory(null, 6 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 보호
				ench = Enchantment.PROTECTION_ENVIRONMENTAL;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 화염으로부터 보호
				ench = Enchantment.PROTECTION_FIRE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 폭발로부터 보호
				ench = Enchantment.PROTECTION_EXPLOSIONS;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 발사체로부터 보호
				ench = Enchantment.PROTECTION_PROJECTILE;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				// 가시
				ench = Enchantment.THORNS;
				slot = 6;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				// 가벼운 착지
				ench = Enchantment.PROTECTION_FALL;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				// 물갈퀴
				ench = Enchantment.DEPTH_STRIDER;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				// 차가운 걸음
				ench = Enchantment.FROST_WALKER;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));

				// 영혼 가속
				ench = Enchantment.SOUL_SPEED;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				player.openInventory(inv);
			} else if (enchant.equals(EnchantsType.Elytra)) {
				Inventory inv = Bukkit.createInventory(null, 2 * 9, ChatColor.DARK_PURPLE + "인챈트");
				for (int c = 0; c < inv.getSize(); c++)
					if (c % 9 == 4) {
						inv.setItem(c, red);
					} else
						inv.setItem(c, brown);
				inv.setItem(1, tool);
				inv.setItem(2, empty);
				if (book != null)
					inv.setItem(2, book);
				// 내구성
				Enchantment ench = Enchantment.DURABILITY;
				int slot = 8;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				// 수선
				ench = Enchantment.MENDING;
				slot += 9;
				inv.setItem(slot, EnchantsType.getEnchantItem(ench, 1 + tool.getEnchantmentLevel(ench), book));
				player.openInventory(inv);
			}
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			String key = player.getUniqueId().toString();
			ItemStack tool = HashMapStore.getEnchantItemStack(key);
			ItemStack book = HashMapStore.getEnchantBook(key);
			EnchantsType enchant = HashMapStore.getEnchantType(key);
			ItemStack cursor = player.getItemOnCursor();
			if (tool == null && enchant == null) {
				if (raw_slot == 4 && cursor != null) {
					EnchantsType enchant_type = EnchantsType.getEnchantType(cursor.getType());
					if (enchant_type != null) {
						player.setItemOnCursor(new ItemStack(Material.AIR));
						player.closeInventory();
						HashMapStore.setEnchantType(key, enchant_type);
						HashMapStore.setEnchantItemStack(key, cursor);
						openGUI(player, 0);
					}
				}
			} else if (tool != null && enchant != null) {
				if (raw_slot == 2 && cursor != null && cursor.getType().equals(Material.ENCHANTED_BOOK)) {
					HashMapStore.setEnchantType(key, null);
					HashMapStore.setEnchantItemStack(key, null);
					HashMapStore.setEnchantBook(key, null);
					if (book != null)
						if (player.getInventory().firstEmpty() != -1) {
							player.getInventory().addItem(book);
						} else
							player.getWorld().dropItemNaturally(player.getEyeLocation(), book);
					player.setItemOnCursor(new ItemStack(Material.AIR));
					player.closeInventory();
					HashMapStore.setEnchantType(key, EnchantsType.getEnchantType(tool.getType()));
					HashMapStore.setEnchantItemStack(key, tool);
					HashMapStore.setEnchantBook(key, cursor);
					openGUI(player, 0);
				} else if (clicked != null && clicked.hasItemMeta() && clicked.getItemMeta().hasLocalizedName()) {
					Enchantment ench = Enchantment
							.getByKey(NamespacedKey.fromString(clicked.getItemMeta().getLocalizedName()));
					if (ench != null) {
						int now = 1 + tool.getEnchantmentLevel(ench);
						int max = EnchantsType.getEnchantMaxLevel(ench);
						double chance = 1.0d;
						if (book != null && book.hasItemMeta()) {
							EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
							chance += meta.getStoredEnchantLevel(ench) * 0.2;
						}
						BigInteger need = EnchantsType.getNeedMoney(now, max);
						BigInteger has = ConfigStore.getPlayerMoney(player);
						if (player.getGameMode().equals(GameMode.CREATIVE) || has.compareTo(need) >= 0) {
							ConfigStore.setPlayerMoney(player, has.subtract(need));
							if (now <= max) {
								if (EnchantsType.isUpgrade(now, chance, max)) {
									tool.removeEnchantment(ench);
									tool.addUnsafeEnchantment(ench, now);
									HashMapStore.setEnchantItemStack(key, null);
									HashMapStore.setEnchantType(key, null);
									HashMapStore.setEnchantBook(key, null);
									player.closeInventory();
//									player.sendTitle(
//											ChatColor.GREEN + "강화 성공", ChatColor.WHITE
//													+ EnchantsType.getEnchantName(ench) + (max > 1 ? "+" + now : ""),
//											1, 40, 1);
									player.playSound(player, Sound.BLOCK_ANVIL_USE, 0.5f, 1f);
									HashMapStore.setEnchantType(key, EnchantsType.getEnchantType(tool.getType()));
									HashMapStore.setEnchantItemStack(key, tool);
									if (chance == 1.0d)
										HashMapStore.setEnchantBook(key, book);
									openGUI(player, 0);
								} else if (EnchantsType.isBreak(now, max)) {
									HashMapStore.setEnchantItemStack(key, null);
									HashMapStore.setEnchantType(key, null);
									if (chance == 1.0d) {
										HashMapStore.setEnchantBook(key, book);
									} else
										HashMapStore.setEnchantBook(key, null);
									player.closeInventory();
									player.playSound(player, Sound.BLOCK_END_PORTAL_SPAWN, 0.5f, 1f);
//									player.sendTitle(ChatColor.RED + "장비 파괴", ChatColor.RED + "", 1, 40, 1);
								} else {
									HashMapStore.setEnchantItemStack(key, null);
									HashMapStore.setEnchantType(key, null);
									HashMapStore.setEnchantBook(key, null);
									player.closeInventory();
//									player.sendTitle(ChatColor.RED + "강화 실패", ChatColor.RED + "", 1, 40, 1);
									player.playSound(player, Sound.BLOCK_GRINDSTONE_USE, 3f, 1f);
									HashMapStore.setEnchantType(key, EnchantsType.getEnchantType(tool.getType()));
									HashMapStore.setEnchantItemStack(key, tool);
									if (chance == 1.0d)
										HashMapStore.setEnchantBook(key, book);
									openGUI(player, 0);
								}
							}
						} else {
							player.closeInventory();
							player.sendTitle(ChatColor.RED + "강화 불가", ChatColor.WHITE + "소지 자금이 부족합니다.", 1, 40, 1);
						}
					}

				}
			}
			return ConfigStore.getPlayerStatus(player)
					|| raw_slot < player.getOpenInventory().getTopInventory().getSize();
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			String key = player.getUniqueId().toString();
			ItemStack item = HashMapStore.getEnchantItemStack(key);
			if (item != null)
				if (player.getInventory().firstEmpty() != -1) {
					player.getInventory().addItem(item);
				} else
					player.getWorld().dropItemNaturally(player.getEyeLocation(), item);

			ItemStack book = HashMapStore.getEnchantBook(key);
			if (book != null)
				if (player.getInventory().firstEmpty() != -1) {
					player.getInventory().addItem(book);
				} else
					player.getWorld().dropItemNaturally(player.getEyeLocation(), book);

			HashMapStore.setEnchantItemStack(key, null);
			HashMapStore.setEnchantBook(key, null);
			HashMapStore.setEnchantType(key, null);
			return false;

		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}

	},
	fix_error(ChatColor.GREEN + "오류 교환", 1 * 9) {

		@Override
		public void openGUI(Player player, int page) {
			Inventory inv = Bukkit.createInventory(null, 1 * 9, ChatColor.GREEN + "오류 교환");
			player.openInventory(inv);
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			return false;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			for (ItemStack item : inv.getContents())
				if (item != null) {
					Items i = Items.valueOf(item);
					List<CookingIngredient> list = CookingIngredient.getList();
					Collections.shuffle(list);
					if (i == null)
						for (CookingIngredient ing : list)
							if (ing.getItemStack().getType().equals(item.getType())) {
								int amount = item.getAmount();
								if (ing instanceof FishItem) {
									item = ((FishItem) ing).getItemStack();
									item.setAmount(amount);
									break;
								} else {
									item = ing.getItemStack(amount);
									break;
								}
							}
					if (player.getInventory().firstEmpty() != -1) {
						player.getInventory().addItem(item);
					} else
						player.getWorld().dropItem(player.getEyeLocation(), item);
				}
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return false;
		}

	},
	training(ChatColor.DARK_PURPLE + "허수아비", 1 * 9) {

		@Override
		public void openGUI(Player player, int page) {
			Inventory inv = Bukkit.createInventory(null, 1 * 9, ChatColor.DARK_PURPLE + "허수아비");
			inv.setItem(3, getInfoItemStack(player));
			inv.setItem(5, getResetItemStack(player));
			player.openInventory(inv);
		}

		private ItemStack getInfoItemStack(Player player) {
			ItemStack item = new ItemStack(Material.COMPASS);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "데미지 측정");
			List<String> lore = new ArrayList<String>();
			long duration = ((HashMapStore.getMeasureEndTime(player) - HashMapStore.getMeasureStartTime(player))
					/ 1000);
			double damage = HashMapStore.getMeasureDamage(player);
			lore.add(ChatColor.WHITE + "지속시간 : " + duration);
			lore.add(ChatColor.WHITE + "총 피해량 : " + Math.round(damage * 10) / 10.0);
			lore.add(ChatColor.WHITE + "총타수 : " + HashMapStore.getMeasureDamageCount(player));
			lore.add(ChatColor.WHITE + "치명타율 : " + Math.round(10000.0d * HashMapStore.getMeasureCriticalCount(player)
					/ HashMapStore.getMeasureDamageCount(player)) / 100.0d + "%");
			lore.add(ChatColor.GREEN + "DPS : " + (duration > 0 ? Math.round(damage * 10.0 / duration) / 10.0 : 0));
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

		private ItemStack getResetItemStack(Player player) {
			ItemStack item = new ItemStack(Material.BARRIER);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "초기화");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.WHITE + "클릭시 초기화");
			lore.add(ChatColor.WHITE + "측정은 공격 시작 및 종료 시간으로 자동 측정");
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}

		@Override
		public boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked, Inventory inv) {
			if (raw_slot == 5) {
				HashMapStore.setMeasureDamage(player, 0);
				HashMapStore.setMeasureDamageCount(player, 0);
				HashMapStore.setMeasureCriticalCount(player, 0);
				HashMapStore.setMeasureStartTime(player);
				HashMapStore.setMeasureEndTime(player);
				player.closeInventory();
			} else
				openGUI(player, 0);
			return true;
		}

		@Override
		public boolean Close(Player player, Inventory inv) {
			return false;
		}

		@Override
		public boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots) {
			return true;
		}
	}

	//
	;

	private final String title;
	public final Inventory inv;

	private GUIStores(String title, int size) {
		this.title = title;
		this.inv = Bukkit.createInventory(null, size, title);
	}

	private GUIStores(String title, InventoryType type) {
		this.title = title;
		this.inv = Bukkit.createInventory(null, type, title);
	}

	public String getTitle() {
		return this.title;
	}

	public abstract void openGUI(Player player, int page);

	public abstract boolean Click(Player player, ClickType type, int slot, int raw_slot, ItemStack clicked,
			Inventory inv);

	public abstract boolean Close(Player player, Inventory inv);

	public abstract boolean Drag(Player player, Set<Integer> slots, Set<Integer> raw_slots);

	public static int getInfoPage(ItemStack i) {
		if (i != null && i.getType().equals(Material.BARRIER) && i.hasItemMeta() && i.getItemMeta().hasDisplayName()
				&& i.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "페이지") && i.getItemMeta().hasLore()) {
			List<String> lore = i.getItemMeta().getLore();
			if (lore.size() > 0)
				try {
					return Integer.parseInt(ChatColor.stripColor(lore.get(lore.size() - 1)));
				} catch (IllegalArgumentException ex) {

				}
		}
		return 0;
	}

	private static ItemStack getBROWNEmpty() {
		return getBROWNEmpty("");
	}

	private static ItemStack getBROWNEmpty(String display, String... lores) {
		ItemStack i = new ItemStack(Material.BROWN_STAINED_GLASS_PANE);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + display);
		meta.addItemFlags(ItemFlag.values());
		List<String> lore = new ArrayList<String>();
		for (String l : lores)
			lore.add(ChatColor.WHITE + l);
		meta.setLore(lore);
		meta.setLocalizedName(new Random().nextDouble() + "");
		i.setItemMeta(meta);
		return i;
	}

	@SuppressWarnings("unused")
	private static ItemStack getGREENEmpty() {
		return getGREENEmpty("");
	}

	private static ItemStack getGREENEmpty(String display, String... lores) {
		ItemStack i = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + display);
		meta.addItemFlags(ItemFlag.values());
		List<String> lore = new ArrayList<String>();
		for (String l : lores)
			lore.add(ChatColor.WHITE + l);
		meta.setLore(lore);
		meta.setLocalizedName(new Random().nextDouble() + "");
		i.setItemMeta(meta);
		return i;
	}

	private static ItemStack getREDEmpty() {
		return getREDEmpty("");
	}

	private static ItemStack getREDEmpty(String display, String... lores) {
		ItemStack i = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + display);
		meta.addItemFlags(ItemFlag.values());
		List<String> lore = new ArrayList<String>();
		for (String l : lores)
			lore.add(ChatColor.WHITE + l);
		meta.setLore(lore);
		meta.setLocalizedName(new Random().nextDouble() + "");
		i.setItemMeta(meta);
		return i;
	}

	private static ItemStack getBLACKEmpty() {
		return getBLACKEmpty("");
	}

	private static ItemStack getBLACKEmpty(String display, String... lores) {
		ItemStack i = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + display);
		meta.addItemFlags(ItemFlag.values());
		List<String> lore = new ArrayList<String>();
		for (String l : lores)
			lore.add(ChatColor.WHITE + l);
		meta.setLore(lore);
		meta.setLocalizedName(new Random().nextDouble() + "");
		i.setItemMeta(meta);
		return i;
	}

	private static ItemStack getBack() {
		ItemStack i = new ItemStack(Material.BEDROCK);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "뒤로가기");
		meta.addItemFlags(ItemFlag.values());
		meta.setLocalizedName(new Random().nextDouble() + "");
		i.setItemMeta(meta);
		return i;
	}

	public static ItemStack getInfoItemStack(int page, int max) {
		ItemStack i = new ItemStack(Material.BARRIER);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "페이지");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "현재 페이지 : " + (page + 1) + " / " + (max + 1));
		lore.add(ChatColor.WHITE + "좌클릭 - 이전 / 우클릭 - 다음");
		lore.add(ChatColor.GRAY + "" + page);
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}

	public static GUIStores getGUI(String title) {
		for (GUIStores stores : values())
			if (stores.title.equalsIgnoreCase(title))
				return stores;
		return null;
	}
}
