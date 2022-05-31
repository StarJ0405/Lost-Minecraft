package com.StarJ.LA.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.block.data.type.Cocoa;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.StarJ.LA.Core;
import com.StarJ.LA.Items.Consumeable;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.LeftClickable;
import com.StarJ.LA.Items.RightClickable;
import com.StarJ.LA.Systems.Basics;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.EnchantsType;
import com.StarJ.LA.Systems.GUIStores;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.ShopStores;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;
import com.StarJ.LA.Systems.Runnable.DebuffRunnable;
import com.StarJ.LA.Systems.Runnable.DebuffRunnable.DebuffType;
import com.StarJ.LA.Systems.Runnable.SneakRunnable;

public class PlayerInteractListener implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void Events(PlayerToggleSneakEvent e) {
		Player player = e.getPlayer();
		if (ConfigStore.getPlayerStatus(player)) {
			ActionBarRunnable.run(player);
		}
		if (player.isSneaking()) {
			if (!ConfigStore.getPlayerStatus(player))
				SneakRunnable.Start(player);
		} else if (SneakRunnable.isSneaking(player)) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item == null || EnchantsType.getEnchantType(item.getType()) == null)
				item = player.getInventory().getItemInOffHand();
			if (item != null) {
				EnchantsType tool = EnchantsType.getEnchantType(item.getType());
				if (tool != null && !ConfigStore.getPlayerStatus(player)) {
					if (tool.equals(EnchantsType.Hoe)) {
						int level = Basics.Farming.getLevel(player);
						int range = level / 10 * 5 + 5;
						if (level >= 40)
							if (player.getGameMode().equals(GameMode.CREATIVE)
									|| !ConfigStore.isBasicsCool(player, Basics.Farming, 3)) {
								Location block_loc = player.getLocation().getBlock().getLocation();
								player.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 3f);
								for (int y = -range; y <= range; y++)
									for (int x = -range; x <= range; x++)
										for (int z = -range; z <= range; z++) {
											Block b = block_loc.clone().add(x, y, z).getBlock();
											Material b_type = b.getType();
											World world = b.getWorld();
											if (Basics.isFarming(b_type))
												if (b_type.equals(Material.SUGAR_CANE)) {
													Location loc = b.getLocation().clone();
													while (loc.getBlock().getType().equals(Material.SUGAR_CANE)) {
														loc.setY(loc.getBlockY() + 1);
													}
													loc.setY(loc.getBlockY() - 1);
													while (loc.getBlock().getType().equals(Material.SUGAR_CANE)) {
														Location confirm = loc.clone();
														confirm.setY(confirm.getBlockY() - 1);
														if (confirm.getBlock().getType().equals(Material.SUGAR_CANE)) {
															DropItem(item, loc.getBlock(), world,
																	player.getEyeLocation());
															loc.getBlock().setType(Material.AIR);
															player.playSound(loc, Sound.BLOCK_CROP_BREAK, 0.5f, 0.5f);
														} else
															break;
														loc.setY(loc.getBlockY() - 1);
													}
												} else if (b_type.equals(Material.KELP_PLANT)
														|| b_type.equals(Material.KELP)) {
													Location loc = b.getLocation().clone();
													while (loc.getBlock().getType().equals(Material.KELP_PLANT)
															|| loc.getBlock().getType().equals(Material.KELP)) {
														loc.setY(loc.getBlockY() + 1);
													}
													loc.setY(loc.getBlockY() - 1);
													while (loc.getBlock().getType().equals(Material.KELP_PLANT)
															|| loc.getBlock().getType().equals(Material.KELP)) {
														Location confirm = loc.clone();
														confirm.setY(confirm.getBlockY() - 1);
														if (confirm.getBlock().getType().equals(Material.KELP_PLANT)) {
															DropItem(item, loc.getBlock(), world,
																	player.getEyeLocation());
															loc.getBlock().setType(Material.WATER);
															player.playSound(loc, Sound.BLOCK_CROP_BREAK, 0.5f, 0.5f);
														} else
															break;
														loc.setY(loc.getBlockY() - 1);
													}
												} else if (b_type.equals(Material.COCOA)) {
													if (((Cocoa) b.getBlockData()).getAge() >= Basics
															.getFarmingData(b_type)) {
														DropItem(item, b, world, player.getEyeLocation());
														Cocoa cocoa = ((Cocoa) b.getBlockData());
														cocoa.setAge(0);
														b.setBlockData(cocoa);
														player.playSound(b.getLocation(), Sound.BLOCK_CROP_BREAK, 0.5f,
																0.5f);
													}
												} else if (Basics.hasFarmingData(b_type)) {
													if (b.getState().getRawData() >= Basics.getFarmingData(b_type)) {
														DropItem(item, b, world, player.getEyeLocation());
														b.setType(b_type);
														player.playSound(b.getLocation(), Sound.BLOCK_CROP_BREAK, 0.5f,
																0.5f);
													}
												} else {
													DropItem(item, b, world, player.getEyeLocation());
													b.setType(Material.AIR);
												}
										}

								if (!player.getGameMode().equals(GameMode.CREATIVE))
									ConfigStore.setBasicsCool(player, Basics.Farming, 3, 450 - level * 3);
							} else
								player.sendMessage(ChatColor.RED + "채집의 왕 쿨타임 : "
										+ ConfigStore.getBasicsCool(player, Basics.Farming, 2) / 1000 + "초");
					} else if (tool.equals(EnchantsType.Fishing_Rod)) {
						int level = Basics.Fishing.getLevel(player);
						if (level >= 40)
							if (player.getGameMode().equals(GameMode.CREATIVE)
									|| !ConfigStore.isBasicsCool(player, Basics.Fishing, 3)) {
								int number = ConfigStore.getBasicsNumber(player, Basics.Fishing, 3);
								if (number <= 0) {
									ConfigStore.setBasicsNumber(player, Basics.Fishing, 3, (level / 10 + 1));
									player.playSound(player, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1f, 0.5f);
									player.sendMessage(ChatColor.GREEN + "낚시의 신 남은 횟수 : "
											+ ConfigStore.getBasicsNumber(player, Basics.Fishing, 3));
									if (!player.getGameMode().equals(GameMode.CREATIVE))
										ConfigStore.setBasicsCool(player, Basics.Fishing, 3, 450 - level * 3);
								} else
									player.sendMessage(ChatColor.GREEN + "낚시의 신 남은 횟수 : " + number);
							} else
								player.sendMessage(ChatColor.RED + "낚시의 신 쿨타임 : "
										+ ConfigStore.getBasicsCool(player, Basics.Fishing, 3) / 1000 + "초");
					} else if (tool.equals(EnchantsType.Sword)) {
						int level = Basics.Hunting.getLevel(player);
						if (level >= 40)
							if (!ConfigStore.isBasicsDuration(player, Basics.Hunting, 3)) {
								if (player.getGameMode().equals(GameMode.CREATIVE)
										|| !ConfigStore.isBasicsCool(player, Basics.Hunting, 3)) {
									ConfigStore.setBasicsDuration(player, Basics.Hunting, 3, (level / 2));
									player.playSound(player, Sound.BLOCK_BEACON_POWER_SELECT, 1f, 0.5f);
									if (!player.getGameMode().equals(GameMode.CREATIVE))
										ConfigStore.setBasicsCool(player, Basics.Hunting, 3, 450 - level * 3);
								} else
									player.sendMessage(ChatColor.RED + "깔끔한 도축가 쿨타임 : "
											+ ConfigStore.getBasicsCool(player, Basics.Hunting, 3) / 1000 + "초");
							} else
								player.sendMessage(ChatColor.GREEN + "깔끔한 도축가 지속시간 : "
										+ ConfigStore.getBasicsDuration(player, Basics.Hunting, 3) / 1000 + "초");
					} else if (tool.equals(EnchantsType.Pickaxe)) {
						int level = Basics.Mining.getLevel(player);
						if (level >= 40)
							if (player.getGameMode().equals(GameMode.CREATIVE)
									|| !ConfigStore.isBasicsCool(player, Basics.Mining, 3)) {
								player.playSound(player, Sound.BLOCK_BEACON_POWER_SELECT, 1f, 2f);
								Location loc = player.getLocation();
								int range = 5 + (level / 5) * 5;
								HashMap<Material, Location> mine_loc = new HashMap<Material, Location>();
								for (int y = -range; y <= range; y++)
									for (int x = -range; x <= range; x++)
										for (int z = -range; z <= range; z++) {
											Block block = loc.clone().add(x, y, z).getBlock();
											Material type = block.getType();
											if (Basics.isMining(type)) {
												if (mine_loc.containsKey(type)) {
													if (loc.distance(mine_loc.get(type)) < loc
															.distance(block.getLocation()))
														mine_loc.put(type, block.getLocation());
												} else
													mine_loc.put(type, block.getLocation());
											}
										}
								for (Material type : mine_loc.keySet()) {
									Location block_loc = mine_loc.get(type);
									player.sendMessage(ChatColor.GOLD + "" + Basics.getMiningName(type) + " : "
											+ block_loc.getX() + ", " + block_loc.getY() + ", " + block_loc.getZ());
								}
								if (!player.getGameMode().equals(GameMode.CREATIVE))
									ConfigStore.setBasicsCool(player, Basics.Mining, 3, 450 - level * 3);
							} else
								player.sendMessage(ChatColor.RED + "X-Ray 쿨타임 : "
										+ ConfigStore.getBasicsCool(player, Basics.Mining, 3) / 1000 + "초");
					} else if (tool.equals(EnchantsType.Axe)) {
						int level = Basics.Chopping.getLevel(player);
						if (level >= 40)
							if (!ConfigStore.isBasicsDuration(player, Basics.Chopping, 3)) {
								if (player.getGameMode().equals(GameMode.CREATIVE)
										|| !ConfigStore.isBasicsCool(player, Basics.Chopping, 3)) {
									ConfigStore.setBasicsDuration(player, Basics.Chopping, 3, (level / 2));
									player.playSound(player, Sound.BLOCK_BELL_USE, 1f, 0.5f);
									if (!player.getGameMode().equals(GameMode.CREATIVE))
										ConfigStore.setBasicsCool(player, Basics.Chopping, 3, 450 - level * 3);
								} else
									player.sendMessage(ChatColor.RED + "포츈 타임 쿨타임 : "
											+ ConfigStore.getBasicsCool(player, Basics.Chopping, 3) / 1000 + "초");
							} else
								player.sendMessage(ChatColor.GREEN + "포츈 타임 지속시간 : "
										+ ConfigStore.getBasicsDuration(player, Basics.Chopping, 3) / 1000 + "초");
					} else if (tool.equals(EnchantsType.Shovel)) {
						int level = Basics.Digging.getLevel(player);
						if (level >= 40)
							if (player.getGameMode().equals(GameMode.CREATIVE)
									|| !ConfigStore.isBasicsCool(player, Basics.Digging, 3)) {
								int number = ConfigStore.getBasicsNumber(player, Basics.Digging, 3);
								if (number <= 0) {
									ConfigStore.setBasicsNumber(player, Basics.Digging, 3, (level / 10 + 1));
									player.playSound(player, Sound.BLOCK_ROOTED_DIRT_BREAK, 2f, 0.5f);
									player.sendMessage(ChatColor.GREEN + "완벽한 발굴 남은 횟수 : "
											+ ConfigStore.getBasicsNumber(player, Basics.Digging, 3));
									if (!player.getGameMode().equals(GameMode.CREATIVE))
										ConfigStore.setBasicsCool(player, Basics.Digging, 3, 450 - level * 3);
								} else
									player.sendMessage(ChatColor.GREEN + "완벽한 발굴 남은 횟수 : " + number);
							} else
								player.sendMessage(ChatColor.RED + "완벽한 발굴 쿨타임 : "
										+ ConfigStore.getBasicsCool(player, Basics.Digging, 3) / 1000 + "초");
					}
				}
			}
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Events(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Random r = new Random();
		if (e.getAction().equals(Action.PHYSICAL)) {
			Block b = e.getClickedBlock();
			if (b != null)
				if (b.getType().equals(Material.FARMLAND))
					e.setCancelled(true);
		} else {
			Block block = e.getClickedBlock();
			if (block != null) {
				Material type = block.getType();
				if (type.equals(Material.ENCHANTING_TABLE) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					GUIStores.enchant.openGUI(player, 0);
					e.setCancelled(true);
					return;
				} else if ((type.equals(Material.CAMPFIRE) || type.equals(Material.SOUL_CAMPFIRE))
						&& e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					GUIStores.cooking.openGUI(player, 0);
					e.setCancelled(true);
					return;
				} else if (type.equals(Material.BREWING_STAND) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					GUIStores.potioning.openGUI(player, 0);
					e.setCancelled(true);
					return;
				} else if (type.equals(Material.BEEHIVE) || type.equals(Material.BEE_NEST)) {
					Beehive hive = (Beehive) block.getState().getBlockData();
					if (hive.getHoneyLevel() == 5) {
						ItemStack main = player.getInventory().getItemInMainHand();
						Items main_i = Items.valueOf(main);
						ItemStack off = player.getInventory().getItemInOffHand();
						Items off_i = Items.valueOf(off);
						if (main != null && main_i == null && main.getType().equals(Material.GLASS_BOTTLE)) {
							if (main.getAmount() == 1) {
								player.getInventory().setItemInMainHand(Items.honey_bottle.getItemStack());
							} else {
								main.setAmount(main.getAmount() - 1);
								player.updateInventory();
								if (player.getInventory().firstEmpty() != -1) {
									player.getInventory().addItem(Items.honey_bottle.getItemStack());
								} else
									player.getWorld().dropItemNaturally(player.getEyeLocation(),
											Items.honey_bottle.getItemStack());
							}
							hive.setHoneyLevel(0);
							block.setBlockData(hive);
							e.setCancelled(true);
							return;
						} else if (off != null && off_i == null && off.getType().equals(Material.GLASS_BOTTLE)) {
							if (off.getAmount() == 1) {
								player.getInventory().setItemInOffHand(Items.honey_bottle.getItemStack());
							} else {
								off.setAmount(off.getAmount() - 1);
								player.updateInventory();
								if (player.getInventory().firstEmpty() != -1) {
									player.getInventory().addItem(Items.honey_bottle.getItemStack());
								} else
									player.getWorld().dropItemNaturally(player.getEyeLocation(),
											Items.honey_bottle.getItemStack());
							}
							hive.setHoneyLevel(0);
							block.setBlockData(hive);
							e.setCancelled(true);
							return;
						}
					}
				}
			}
			Items now = Items.valueOf(player.getInventory().getItemInMainHand());
			ItemStack item = player.getInventory().getItemInMainHand();
			if (e.getHand().equals(EquipmentSlot.OFF_HAND))
				if (now != null) {
					e.setCancelled(true);
					return;
				} else {
					now = Items.valueOf(player.getInventory().getItemInOffHand());
					item = player.getInventory().getItemInOffHand();
				}

			if (now != null) {
				if (now instanceof RightClickable && (e.getAction().equals(Action.RIGHT_CLICK_AIR)
						|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
					RightClickable rc = (RightClickable) now;
					rc.RightClick(player, item, e.getClickedBlock(), null);
					e.setCancelled(rc.isRightCancel());
					return;
				} else if (now instanceof LeftClickable && (e.getAction().equals(Action.LEFT_CLICK_AIR)
						|| e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
					LeftClickable lc = (LeftClickable) now;
					lc.LeftClick(player, item, e.getClickedBlock(), null);
					e.setCancelled(lc.isLeftCancel());
					return;
				} else
					e.setCancelled(true);
			} else {
				item = player.getInventory().getItemInMainHand();
				if (e.getHand().equals(EquipmentSlot.OFF_HAND))
					if (item != null && (item.getType().name().contains("SHULKER_BOX")
							|| EnchantsType.getEnchantType(item.getType()) != null)) {
						e.setCancelled(true);
						return;
					} else
						item = player.getInventory().getItemInOffHand();
				if (item != null) {
					Material type = item.getType();
					EnchantsType tool = EnchantsType.getEnchantType(type);
					if (type.name().contains("SHULKER_BOX") && !player.isSneaking()) {
						BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
						ShulkerBox box = (ShulkerBox) meta.getBlockState();
						Inventory open = Bukkit.createInventory(box, InventoryType.SHULKER_BOX);
						open.setContents(box.getInventory().getContents());
						player.openInventory(open);
						e.setCancelled(true);
					} else if (tool != null && !ConfigStore.getPlayerStatus(player)) {
						if (tool.equals(EnchantsType.Hoe)) {
							int level = Basics.Farming.getLevel(player);
							int range = level / 10 + 1;
							if (player.isSneaking()) {
								if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
									if (level >= 20)
										if (player.getGameMode().equals(GameMode.CREATIVE)
												|| !ConfigStore.isBasicsCool(player, Basics.Farming, 2)) {
											Location player_loc = player.getEyeLocation();
											range = level / 5 + 2;
											for (Entity et : block.getWorld().getNearbyEntities(block.getLocation(),
													range, range, range))
												if (et != null && et instanceof Item) {
													Item i = (Item) et;
													if (Basics.isFarmingCrops(i.getItemStack()))
														et.teleport(player_loc);

												}
											Effects.Directional.ASH.spawnDirectional(player_loc, 1, 0, 0, 0, 0);
											player.playSound(block.getLocation(), Sound.ENTITY_WITHER_SPAWN, 0.5f, 3f);
											if (!player.getGameMode().equals(GameMode.CREATIVE))
												ConfigStore.setBasicsCool(player, Basics.Farming, 2, 300 - level * 2);
										} else
											player.sendMessage(ChatColor.RED + "빠른 수거 쿨타임 : "
													+ ConfigStore.getBasicsCool(player, Basics.Farming, 2) / 1000
													+ "초");
									e.setCancelled(true);
								} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
									if (level >= 10) {
										Location block_loc = block.getLocation();
										List<Location> canes = new ArrayList<Location>();
										List<Location> kelps = new ArrayList<Location>();
										if (player.getGameMode().equals(GameMode.CREATIVE)
												|| !ConfigStore.isBasicsCool(player, Basics.Farming, 1)) {
											for (int y = range; y >= -range; y--)
												for (int x = -range; x <= range; x++)
													z: for (int z = -range; z <= range; z++) {
														Block b = block_loc.clone().add(x, y, z).getBlock();
														Material b_type = b.getType();
														if (Basics.isFarming(b_type)) {
															if (b_type.equals(Material.SUGAR_CANE)) {
																Location loc = b.getLocation().clone();
																for (Location cane : canes)
																	if (cane.getBlockX() == loc.getBlockX()
																			&& cane.getBlockZ() == loc.getBlockZ())
																		continue z;
																while (loc.getBlock().getType()
																		.equals(Material.SUGAR_CANE)) {
																	loc.setY(loc.getBlockY() + 1);
																}
																loc.getBlock().setType(Material.SUGAR_CANE);
																canes.add(loc);
																player.playSound(loc.clone(),
																		Sound.BLOCK_CHORUS_FLOWER_GROW, 0.5f, 0.5f);
																Effects.Directional.VILLAGER_HAPPY.spawnDirectional(
																		loc.clone().add(0.5, 0, 0.5), 1, 0, 0, 0, 0);
															} else if (b_type.equals(Material.KELP)) {
																Location loc = b.getLocation().clone();
																for (Location kelp : kelps)
																	if (kelp.getBlockX() == loc.getBlockX()
																			&& kelp.getBlockZ() == loc.getBlockZ())
																		continue z;
																while (loc.getBlock().getType().equals(Material.KELP)) {
																	loc.setY(loc.getBlockY() + 1);
																}
																if (loc.getBlock().getType().equals(Material.WATER))
																	loc.getBlock().setType(Material.KELP);
																kelps.add(loc);
																player.playSound(loc.clone(),
																		Sound.BLOCK_CHORUS_FLOWER_GROW, 0.5f, 0.5f);
																Effects.Directional.VILLAGER_HAPPY.spawnDirectional(
																		loc.clone().add(0.5, 0, 0.5), 1, 0, 0, 0, 0);
															} else if (b_type.equals(Material.COCOA)) {
																if (((Cocoa) b.getBlockData()).getAge() < Basics
																		.getFarmingData(b_type)) {
																	Cocoa cocoa = ((Cocoa) b.getBlockData());
																	cocoa.setAge(cocoa.getAge() + 1);
																	b.setBlockData(cocoa);
																	player.playSound(b.getLocation(),
																			Sound.BLOCK_CHORUS_FLOWER_GROW, 0.5f, 0.5f);
																	Effects.Directional.VILLAGER_HAPPY.spawnDirectional(
																			b.getLocation().clone().add(0.5, 0.75, 0.5),
																			1, 0, 0, 0, 0);
																}
															} else if (Basics.hasFarmingData(b_type)) {
																if (b.getState().getRawData() < Basics
																		.getFarmingData(b_type)) {
																	BlockState state = b.getState();
																	state.setRawData((byte) (state.getRawData() + 1));
																	state.update();
																	state.update(true);
																	player.playSound(b.getLocation(),
																			Sound.BLOCK_CHORUS_FLOWER_GROW, 0.5f, 0.5f);
																	Effects.Directional.VILLAGER_HAPPY.spawnDirectional(
																			b.getLocation().clone().add(0.5, 0.75, 0.5),
																			1, 0, 0, 0, 0);
																}
															}
														} else if (b_type.equals(Material.MELON_STEM)
																|| b_type.equals(Material.PUMPKIN_STEM)) {
															BlockState state = b.getState();
															if (state.getRawData() < 4) {
																state.setRawData((byte) (state.getRawData() + 1));
																state.update();
																state.update(true);
																player.playSound(b.getLocation(),
																		Sound.BLOCK_CHORUS_FLOWER_GROW, 0.5f, 0.5f);
																Effects.Directional.VILLAGER_HAPPY.spawnDirectional(
																		b.getLocation().clone().add(0.5, 0.75, 0.5), 1,
																		0, 0, 0, 0);
															}
														}
													}
											if (!player.getGameMode().equals(GameMode.CREATIVE))
												ConfigStore.setBasicsCool(player, Basics.Farming, 1, 60 - level);
										} else
											player.sendMessage(ChatColor.RED + "비료 뿌리기 쿨타임 : "
													+ ConfigStore.getBasicsCool(player, Basics.Farming, 1) / 1000
													+ "초");
									}
								}
							} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block != null
									&& Basics.isFarming(block.getType())) {
								// 0
								Location block_loc = block.getLocation();
								int exp = 0;
								for (int y = -1; y <= 1; y++)
									for (int x = -range; x <= range; x++)
										for (int z = -range; z <= range; z++) {
											Block b = block_loc.clone().add(x, y, z).getBlock();
											Material b_type = b.getType();
											World world = b.getWorld();
											if (Basics.isFarming(b_type))
												if (b_type.equals(Material.SUGAR_CANE)) {
													Location loc = b.getLocation().clone();
													while (loc.getBlock().getType().equals(Material.SUGAR_CANE)) {
														loc.setY(loc.getBlockY() + 1);
													}
													loc.setY(loc.getBlockY() - 1);
													while (loc.getBlock().getType().equals(Material.SUGAR_CANE)) {
														Location confirm = loc.clone();
														confirm.setY(confirm.getBlockY() - 1);
														if (confirm.getBlock().getType().equals(Material.SUGAR_CANE)) {
															DropItem(item, loc.getBlock(), world);
															loc.getBlock().setType(Material.AIR);
															player.playSound(loc, Sound.BLOCK_CROP_BREAK, 0.5f, 0.5f);
															if (!player.getGameMode().equals(GameMode.CREATIVE)) {
																exp += Basics.getFarmingExp(b_type);
																if (Basics.Farming.isActive(level))
																	if (!item
																			.containsEnchantment(Enchantment.DURABILITY)
																			|| r.nextDouble() < (1
																					/ (1.0 + item.getEnchantmentLevel(
																							Enchantment.DURABILITY))))
																		item.setDurability(
																				(short) (item.getDurability() + 1));
															}
														} else
															break;
														loc.setY(loc.getBlockY() - 1);
													}
												} else if (b_type.equals(Material.KELP_PLANT)
														|| b_type.equals(Material.KELP)) {
													Location loc = b.getLocation().clone();
													while (loc.getBlock().getType().equals(Material.KELP_PLANT)
															|| loc.getBlock().getType().equals(Material.KELP)) {
														loc.setY(loc.getBlockY() + 1);
													}
													loc.setY(loc.getBlockY() - 1);
													while (loc.getBlock().getType().equals(Material.KELP_PLANT)
															|| loc.getBlock().getType().equals(Material.KELP)) {
														Location confirm = loc.clone();
														confirm.setY(confirm.getBlockY() - 1);
														if (confirm.getBlock().getType().equals(Material.KELP_PLANT)
																|| confirm.getBlock().getType().equals(Material.KELP)) {
															DropItem(item, loc.getBlock(), world);
															loc.getBlock().setType(Material.WATER);
															player.playSound(loc, Sound.BLOCK_CROP_BREAK, 0.5f, 0.5f);
															if (!player.getGameMode().equals(GameMode.CREATIVE)) {
																exp += Basics.getFarmingExp(b_type);
																if (Basics.Farming.isActive(level))
																	if (!item
																			.containsEnchantment(Enchantment.DURABILITY)
																			|| r.nextDouble() < (1
																					/ (1.0 + item.getEnchantmentLevel(
																							Enchantment.DURABILITY))))
																		item.setDurability(
																				(short) (item.getDurability() + 1));
															}
														} else
															break;
														loc.setY(loc.getBlockY() - 1);
													}
												} else if (b_type.equals(Material.COCOA)) {
													if (((Cocoa) b.getBlockData()).getAge() >= Basics
															.getFarmingData(b_type)) {
														DropItem(item, b, world);
														Cocoa cocoa = ((Cocoa) b.getBlockData());
														cocoa.setAge(0);
														b.setBlockData(cocoa);
														player.playSound(b.getLocation(), Sound.BLOCK_CROP_BREAK, 0.5f,
																0.5f);
														if (!player.getGameMode().equals(GameMode.CREATIVE)) {
															exp += Basics.getFarmingExp(b_type);
															if (Basics.Farming.isActive(level))
																if (!item.containsEnchantment(Enchantment.DURABILITY)
																		|| r.nextDouble() < (1
																				/ (1.0 + item.getEnchantmentLevel(
																						Enchantment.DURABILITY))))
																	item.setDurability(
																			(short) (item.getDurability() + 1));
														}
													}
												} else if (Basics.hasFarmingData(b_type)) {
													if (b.getState().getRawData() >= Basics.getFarmingData(b_type)) {
														DropItem(item, b, world);
														b.setType(b_type);
														player.playSound(b.getLocation(), Sound.BLOCK_CROP_BREAK, 0.5f,
																0.5f);
														if (!player.getGameMode().equals(GameMode.CREATIVE)) {
															exp += Basics.getFarmingExp(b_type);
															if (Basics.Farming.isActive(level))
																if (!item.containsEnchantment(Enchantment.DURABILITY)
																		|| r.nextDouble() < (1
																				/ (1.0 + item.getEnchantmentLevel(
																						Enchantment.DURABILITY))))
																	item.setDurability(
																			(short) (item.getDurability() + 1));
														}
													}
												} else {
													DropItem(item, b, world);
													b.setType(Material.AIR);
													if (!player.getGameMode().equals(GameMode.CREATIVE)) {
														exp += Basics.getFarmingExp(b_type);
														if (Basics.Farming.isActive(level))
															if (!item.containsEnchantment(Enchantment.DURABILITY)
																	|| r.nextDouble() < (1
																			/ (1.0 + item.getEnchantmentLevel(
																					Enchantment.DURABILITY))))
																item.setDurability((short) (item.getDurability() + 1));
													}
												}
										}
								if (exp > 0)
									Basics.Farming.addEXP(player, exp);
								if (item.getDurability() > item.getType().getMaxDurability()) {
									item.setAmount(0);
									player.playSound(player, Sound.ENTITY_ITEM_BREAK, 0.5f, 0.5f);
								}

							}
						} else if (tool.equals(EnchantsType.Fishing_Rod)) {
							int level = Basics.Fishing.getLevel(player);
							if (player.isSneaking()) {
								if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
									if (level >= 20)
										if (player.getGameMode().equals(GameMode.CREATIVE)
												|| !ConfigStore.isBasicsCool(player, Basics.Fishing, 2)) {
											int number = ConfigStore.getBasicsNumber(player, Basics.Fishing, 2);
											if (number <= 0) {
												ConfigStore.setBasicsNumber(player, Basics.Fishing, 2,
														(level / 10 + 1));
												player.playSound(player, Sound.ENTITY_FISH_SWIM, 1f, 0.5f);
												player.sendMessage(ChatColor.GREEN + "대어의 감 남은 횟수 : "
														+ ConfigStore.getBasicsNumber(player, Basics.Fishing, 2));
												if (!player.getGameMode().equals(GameMode.CREATIVE))
													ConfigStore.setBasicsCool(player, Basics.Fishing, 2,
															300 - level * 2);
											} else
												player.sendMessage(ChatColor.GREEN + "대어의 감 남은 횟수 : " + number);
										} else
											player.sendMessage(ChatColor.RED + "대어의 감 쿨타임 : "
													+ ConfigStore.getBasicsCool(player, Basics.Fishing, 2) / 1000
													+ "초");
									e.setCancelled(true);
								}
							} else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
								if (level >= 10)
									if (player.getGameMode().equals(GameMode.CREATIVE)
											|| !ConfigStore.isBasicsCool(player, Basics.Fishing, 1)) {
										int number = ConfigStore.getBasicsNumber(player, Basics.Fishing, 1);
										if (number <= 0) {
											ConfigStore.setBasicsNumber(player, Basics.Fishing, 1, (level / 10 + 1));
											player.playSound(player, Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1f, 0.5f);
											player.sendMessage(ChatColor.GREEN + "손맛 남은 횟수 : "
													+ ConfigStore.getBasicsNumber(player, Basics.Fishing, 1));
											if (!player.getGameMode().equals(GameMode.CREATIVE))
												ConfigStore.setBasicsCool(player, Basics.Fishing, 1, 100 - level);
										} else
											player.sendMessage(ChatColor.GREEN + "손맛 남은 횟수 : " + number);
									} else
										player.sendMessage(ChatColor.RED + "손맛 쿨타임 : "
												+ ConfigStore.getBasicsCool(player, Basics.Fishing, 1) / 1000 + "초");
								e.setCancelled(true);
							}
						} else if (tool.equals(EnchantsType.Sword)) {
							int level = Basics.Hunting.getLevel(player);
							if (!player.isSneaking())
								if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
									if (level >= 10)
										if (player.getGameMode().equals(GameMode.CREATIVE)
												|| !ConfigStore.isBasicsCool(player, Basics.Hunting, 1)) {
											int range = level / 2 + 5;
											for (Entity et : block.getWorld().getNearbyEntities(block.getLocation(),
													range, range, range))
												if (et != null && et instanceof LivingEntity
														&& Basics.isHunting(et.getType()))
													((LivingEntity) et).addPotionEffect(new PotionEffect(
															PotionEffectType.GLOWING, 20 * 15, 0, true, true, false));
											player.playSound(player, Sound.ENTITY_GLOW_SQUID_SQUIRT, 2f, 1f);
											if (!player.getGameMode().equals(GameMode.CREATIVE))
												ConfigStore.setBasicsCool(player, Basics.Hunting, 1, 100 - level);
										} else
											player.sendMessage(ChatColor.RED + "사냥의 눈 쿨타임 : "
													+ ConfigStore.getBasicsCool(player, Basics.Hunting, 1) / 1000
													+ "초");

						} else if (tool.equals(EnchantsType.Pickaxe)) {
							int level = Basics.Mining.getLevel(player);
							if (player.isSneaking()) {
								if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
									if (level >= 20)
										if (!ConfigStore.isBasicsDuration(player, Basics.Mining, 2)) {
											if (player.getGameMode().equals(GameMode.CREATIVE)
													|| !ConfigStore.isBasicsCool(player, Basics.Mining, 2)) {
												ConfigStore.setBasicsDuration(player, Basics.Mining, 2, (level * 2));
												player.playSound(player, Sound.ENTITY_SQUID_SQUIRT, 2f, 1f);
												if (!player.getGameMode().equals(GameMode.CREATIVE))
													ConfigStore.setBasicsCool(player, Basics.Mining, 2, 300 - level);

											} else
												player.sendMessage(ChatColor.RED + "피버 타임 쿨타임 : "
														+ ConfigStore.getBasicsCool(player, Basics.Mining, 2) / 1000
														+ "초");
										} else
											player.sendMessage(ChatColor.GREEN + "피버 타입 지속시간 : "
													+ ConfigStore.getBasicsDuration(player, Basics.Mining, 2) / 1000
													+ "초");
							} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
								if (level >= 10)
									if (player.getGameMode().equals(GameMode.CREATIVE)
											|| !ConfigStore.isBasicsCool(player, Basics.Mining, 1)) {
										player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,
												20 * level * 5, level / 10, true, true, false));
										player.playSound(player, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 2f, 1f);
										if (!player.getGameMode().equals(GameMode.CREATIVE))
											ConfigStore.setBasicsCool(player, Basics.Mining, 1, 300 - level * 5);
									} else
										player.sendMessage(ChatColor.RED + "숙련된 곡괭이질 쿨타임 : "
												+ ConfigStore.getBasicsCool(player, Basics.Mining, 1) / 1000 + "초");
							}
						} else if (tool.equals(EnchantsType.Axe)) {
							int level = Basics.Chopping.getLevel(player);
							int range = level / 2 + 2;
							Location loc = player.getLocation();
							if (player.isSneaking()) {
								if (level >= 20)
									if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
										if (player.getGameMode().equals(GameMode.CREATIVE)
												|| !ConfigStore.isBasicsCool(player, Basics.Chopping, 2)) {
											for (int y = -range; y <= range; y++)
												for (int x = -range; x <= range; x++)
													for (int z = -range; z <= range; z++) {
														Block b = loc.clone().add(x, y, z).getBlock();
														if (Basics.isChoppingTreetype(b.getType())) {
															Material b_type = b.getType();
															b.setType(Material.AIR);
															b.getWorld().generateTree(b.getLocation(),
																	Basics.getChoppingTreetype(b_type));
															player.playSound(b.getLocation(),
																	Sound.BLOCK_CHORUS_FLOWER_GROW, 2f, 1f);
														}
													}
											if (!player.getGameMode().equals(GameMode.CREATIVE))
												ConfigStore.setBasicsCool(player, Basics.Chopping, 2, 300 - level * 2);
										} else
											player.sendMessage(ChatColor.RED + "나무 성장 쿨타임 : "
													+ ConfigStore.getBasicsCool(player, Basics.Chopping, 2) / 1000
													+ "초");
							} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
								if (level >= 10)
									if (player.getWorld().getName().equals("world")) {
										player.sendMessage(ChatColor.RED + "현재 월드에서는 사용 불가능 스킬입니다.");
									} else if (player.getGameMode().equals(GameMode.CREATIVE)
											|| !ConfigStore.isBasicsCool(player, Basics.Chopping, 1)) {
										for (int y = -range; y <= range; y++)
											for (int x = -range; x <= range; x++)
												for (int z = -range; z <= range; z++) {
													Block b = loc.clone().add(x, y, z).getBlock();
													if (Basics.isChopping(b.getType())) {
														if (b.getType().name().contains("LEAVES")) {
															ItemStack[] is = Basics.getChopping(b.getType());
															ItemStack i = is[r.nextInt(is.length)];
															int amount = i.getDurability() + r.nextInt(i.getAmount());
															if (amount > 0) {
																if (ConfigStore.isBasicsDuration(player,
																		Basics.Chopping, 3))
																	amount += 1;
																if (item.containsEnchantment(
																		Enchantment.LOOT_BONUS_BLOCKS)) {
																	int ench_level = item.getEnchantmentLevel(
																			Enchantment.LOOT_BONUS_BLOCKS);
																	if (r.nextDouble() < (1.0 / 5 * ench_level))
																		amount += (int) (ench_level / 3 + 1);
																}
																ItemStack clone = i.clone();
																clone.setAmount(amount);
																clone.setDurability((short) 0);
																b.getWorld().dropItem(loc, clone);
															}
														} else
															for (ItemStack i : Basics.getChopping(b.getType())) {
																int amount = i.getDurability()
																		+ r.nextInt(i.getAmount());
																if (amount > 0) {
																	if (ConfigStore.isBasicsDuration(player,
																			Basics.Chopping, 3))
																		amount += 1;
																	if (item.containsEnchantment(
																			Enchantment.LOOT_BONUS_BLOCKS)) {
																		int ench_level = item.getEnchantmentLevel(
																				Enchantment.LOOT_BONUS_BLOCKS);
																		if (r.nextDouble() < (1.0 / 5 * ench_level))
																			amount += (int) (ench_level / 3 + 1);
																	}
																	ItemStack clone = i.clone();
																	clone.setAmount(amount);
																	clone.setDurability((short) 0);
																	b.getWorld().dropItem(b.getLocation(), clone);
																}
															}
														Material b_type = b.getType();
														b.setType(Material.AIR);
														Material confirm = b.getLocation().add(0, -1, 0).getBlock()
																.getType();
														if (confirm.equals(Material.GRASS_BLOCK)
																|| confirm.equals(Material.DIRT)
																|| confirm.equals(Material.COARSE_DIRT)
																|| confirm.equals(Material.PODZOL)
																|| confirm.equals(Material.ROOTED_DIRT))
															if (Basics.Chopping.isActive(level))
																if (Basics.isChoppingSappling(b_type)) {
																	b.setType(Basics.getChoppingSappling(b_type));
																} else if (b_type.equals(Material.MUSHROOM_STEM))
																	if (r.nextBoolean()) {
																		b.setType(Material.RED_MUSHROOM);
																	} else
																		b.setType(Material.BROWN_MUSHROOM);
														player.playSound(b.getLocation(), Sound.BLOCK_WOOD_BREAK, 2f,
																1f);
													}
												}

										if (!player.getGameMode().equals(GameMode.CREATIVE))
											ConfigStore.setBasicsCool(player, Basics.Chopping, 1, 300 - level * 5);
									} else
										player.sendMessage(ChatColor.RED + "진심 베기 쿨타임 : "
												+ ConfigStore.getBasicsCool(player, Basics.Chopping, 1) / 1000 + "초");
							}
						} else if (tool.equals(EnchantsType.Shovel)) {
							int level = Basics.Digging.getLevel(player);
							if (player.isSneaking()) {
								if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
									if (level >= 20)
										if (!ConfigStore.isBasicsDuration(player, Basics.Digging, 2)) {
											if (player.getGameMode().equals(GameMode.CREATIVE)
													|| !ConfigStore.isBasicsCool(player, Basics.Digging, 2)) {
												ConfigStore.setBasicsDuration(player, Basics.Digging, 2, (level * 2));
												player.playSound(player, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 2f, 1f);
												if (!player.getGameMode().equals(GameMode.CREATIVE))
													ConfigStore.setBasicsCool(player, Basics.Digging, 2,
															300 - level * 2);
											} else
												player.sendMessage(ChatColor.RED + "노련한 탐색 쿨타임 : "
														+ ConfigStore.getBasicsCool(player, Basics.Digging, 2) / 1000
														+ "초");
										} else
											player.sendMessage(ChatColor.GREEN + "노련한 탐색 지속시간 : "
													+ ConfigStore.getBasicsDuration(player, Basics.Digging, 2) / 1000
													+ "초");
							} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
								if (level >= 10)
									if (!ConfigStore.isBasicsDuration(player, Basics.Digging, 1)) {
										if (player.getGameMode().equals(GameMode.CREATIVE)
												|| !ConfigStore.isBasicsCool(player, Basics.Digging, 1)) {
											ConfigStore.setBasicsDuration(player, Basics.Digging, 1, (level * 2));
											player.playSound(player, Sound.ENTITY_SQUID_SQUIRT, 2f, 1f);
											if (!player.getGameMode().equals(GameMode.CREATIVE))
												ConfigStore.setBasicsCool(player, Basics.Digging, 2, 300 - level);
										} else
											player.sendMessage(ChatColor.RED + "과감한 발굴 쿨타임 : "
													+ ConfigStore.getBasicsCool(player, Basics.Digging, 1) / 1000
													+ "초");
									} else
										player.sendMessage(ChatColor.GREEN + "과감한 발굴 지속시간 : "
												+ ConfigStore.getBasicsDuration(player, Basics.Digging, 1) / 1000
												+ "초");
							}
						}
					}
				}
			}
		}

	}

	private void DropItem(ItemStack item, Block block, World world) {
		DropItem(item, block, world, block.getLocation());
	}

	@SuppressWarnings("deprecation")
	private void DropItem(ItemStack item, Block block, World world, Location loc) {
		if (block != null) {
			Material b_type = block.getType();
			for (ItemStack i : Basics.getFarming(b_type)) {
				int amount = i.getDurability() + new Random().nextInt(i.getAmount());
				if (amount > 0) {
					i = i.clone();
					if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
						int ench_level = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
						if (new Random().nextDouble() < (1.0 / 5 * ench_level))
							amount += (int) (ench_level / 3 + 1);
					}
					i.setAmount(amount);
					i.setDurability((short) 0);
					world.dropItemNaturally(loc, i);
				}
			}
		}
	}

	@EventHandler
	public void Events(PlayerInteractEntityEvent e) {
		Player player = e.getPlayer();
		if (e.getRightClicked() != null) {
			Entity et = e.getRightClicked();
			if (et.getType().equals(EntityType.VILLAGER) && et.hasMetadata("key")) {
				for (MetadataValue v : et.getMetadata("key"))
					if (v.getOwningPlugin().equals(Core.getCore())) {
						String key = v.asString();
						ShopStores shop = ShopStores.valueOf(key);
						if (shop != null)
							shop.getGui().openGUI(player, 0);
						break;
					}
			} else {
				ItemStack item = player.getInventory().getItemInMainHand();
				if (e.getHand().equals(EquipmentSlot.OFF_HAND))
					if (item != null && EnchantsType.getEnchantType(item.getType()) != null
							&& EnchantsType.getEnchantType(item.getType()).equals(EnchantsType.Sword)) {
						e.setCancelled(true);
						return;
					} else
						item = player.getInventory().getItemInOffHand();
				if (item != null) {
					EnchantsType tool = EnchantsType.getEnchantType(item.getType());
					if (tool != null && !ConfigStore.getPlayerStatus(player) && tool.equals(EnchantsType.Sword)
							&& player.isSneaking() && Basics.isHunting(et.getType())) {
						int level = Basics.Hunting.getLevel(player);
						if (level >= 20)
							if (player.getGameMode().equals(GameMode.CREATIVE)
									|| !ConfigStore.isBasicsCool(player, Basics.Hunting, 2)) {
								for (int c = 0; c < (level / 10 + 1); c++) {
									Entity baby = et.getWorld().spawnEntity(et.getLocation(), et.getType());
									if (baby instanceof Ageable)
										((Ageable) baby).setBaby();
									player.playSound(baby.getLocation(), Sound.ENTITY_CHICKEN_EGG, 2f, 1f);
								}
								if (!player.getGameMode().equals(GameMode.CREATIVE))
									ConfigStore.setBasicsCool(player, Basics.Hunting, 2, 100 - level);
							} else
								player.sendMessage(ChatColor.RED + "번식 쿨타임 : "
										+ ConfigStore.getBasicsCool(player, Basics.Hunting, 2) / 1000 + "초");
					} else if (et.getType().equals(EntityType.MUSHROOM_COW) && Items.valueOf(item) == null
							&& item.getType().equals(Material.BOWL)) {
						item.setAmount(item.getAmount() - 1);
						if (player.getInventory().firstEmpty() != -1) {
							player.getInventory().addItem(Items.mushroom_stew.getItemStack());
						} else
							player.getWorld().dropItemNaturally(player.getEyeLocation(),
									Items.mushroom_stew.getItemStack());
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void Events(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		Items i = Items.valueOf(item);
		if (i != null && i instanceof Consumeable) {
			((Consumeable) i).Comsume(player, item);
		}
	}

	@EventHandler
	public void Events(PlayerInteractAtEntityEvent e) {

	}

	@EventHandler
	public void Events(EntityPickupItemEvent e) {
		if (e.getEntityType().equals(EntityType.PLAYER)) {
			Player player = (Player) e.getEntity();
			ItemStack i = e.getItem().getItemStack();
			List<ItemStack> items = ConfigStore.getPlayerInventory(player);
			int slot = -1;
			for (int c = 0; c < items.size(); c++) {
				ItemStack item = items.get(c);
				if (item != null && !item.getType().equals(Material.AIR)) {
					if (i.getAmount() > 0) {
						if (i.isSimilar(item)) {
							int now = item.getAmount() + i.getAmount();
							if (now > item.getType().getMaxStackSize()) {
								int max = item.getType().getMaxStackSize();
								i.setAmount(now - max);
								now = max;
								e.getItem().setItemStack(i);
							} else
								i.setAmount(0);
							item.setAmount(now);
							items.set(c, item);
						}
					} else
						break;
				} else if (slot == -1)
					slot = c;
			}
			if (slot != -1 && i.getAmount() > 0) {
				items.set(slot, i.clone());
				i.setAmount(0);
			}
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (i.getAmount() == 0) {
				e.setCancelled(true);
				e.getItem().remove();
			}
			String title = player.getOpenInventory().getTitle();
			if (title.equals(GUIStores.extra.getTitle()))
				player.closeInventory();
			ConfigStore.setPlayerInventory(player, items);
		}
	}

	@EventHandler
	public void Events(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if (ConfigStore.getPlayerStatus(player)) {
			Jobs job = ConfigStore.getJob(player);
			if (job != null)
				job.getSpecialArts().Use(player, 25);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void Events(PlayerSwapHandItemsEvent e) {
		Player player = e.getPlayer();
		if (ConfigStore.getPlayerStatus(player)) {
			Jobs job = ConfigStore.getJob(player);
			if (job != null)
				job.getIdentity().Use(player, 34);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void Events(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();
		if (ConfigStore.getPlayerStatus(player)) {
			Jobs job = ConfigStore.getJob(player);
			int slot = e.getNewSlot();
			if (slot < 7) {
				job.getSkills(player)[slot].Use(player, slot);
			} else if (slot == 8)
				job.getSkills(player)[7].Use(player, slot);
			player.getInventory().setHeldItemSlot(7);
		}
	}

	@EventHandler
	public void Events(FoodLevelChangeEvent e) {
		Entity et = e.getEntity();
		if (et instanceof Player) {
			Player player = (Player) et;
			if (ConfigStore.getPlayerStatus(player))
				e.setFoodLevel(19);
		}
	}

	@EventHandler
	public void Events(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (ConfigStore.getPlayerStatus(player) && DebuffRunnable.hasDebuff(player, DebuffType.Restraint)
				&& e.getTo().distance(e.getFrom()) > 0)
			e.setCancelled(true);
	}

	@EventHandler
	public void Events(PlayerTeleportEvent e) {
		Player player = e.getPlayer();
		if (ConfigStore.getPlayerStatus(player) && DebuffRunnable.hasDebuff(player, DebuffType.Restraint)
				&& e.getTo().distance(e.getFrom()) > 0)
			e.setCancelled(true);
	}
}
