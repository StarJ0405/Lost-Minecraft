package com.StarJ.LA.Listeners;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Cocoa;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.metadata.MetadataValue;

import com.StarJ.LA.Core;
import com.StarJ.LA.Items.Consumeable;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.LeftClickable;
import com.StarJ.LA.Items.RightClickable;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.GUIStores;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.ShopStores;

public class PlayerInteractListener implements Listener {
	@EventHandler
	public void Events(PlayerToggleSneakEvent e) {
		Player player = e.getPlayer();
		if (player.getGameMode().equals(GameMode.CREATIVE) && player.isSneaking()) {
			if (player.isInWater() && player.getInventory().getItemInMainHand() != null
					&& player.getInventory().getItemInMainHand().getType().equals(Material.SPONGE)) {
				Location loc = player.getLocation();
				for (int x = -2; x <= 2; x++)
					for (int y = -2; y <= 2; y++)
						for (int z = -2; z <= 2; z++) {
							Block b = loc.clone().add(x, y, z).getBlock();
							if (b.getType().equals(Material.WATER))
								b.setType(Material.AIR);
						}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Events(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (e.getAction().equals(Action.PHYSICAL)) {
			Block b = e.getClickedBlock();
			if (b != null)
				if (b.getType().equals(Material.FARMLAND))
					e.setCancelled(true);
		} else {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				Block block = e.getClickedBlock();
				World world = block.getWorld();
				Location loc = block.getLocation();
				Random r = new Random();
				if (block != null) {
					Material type = block.getType();
					byte data = block.getState().getRawData();
					if (!(type.equals(Material.WOODEN_HOE) || type.equals(Material.STONE_HOE)
							|| type.equals(Material.IRON_HOE) || type.equals(Material.GOLDEN_HOE)
							|| type.equals(Material.DIAMOND_HOE) || type.equals(Material.NETHERITE_HOE)))
						if (type.equals(Material.WHEAT) && data >= 7) {
							block.setType(Material.WHEAT);
							world.dropItem(loc, new ItemStack(Material.WHEAT, 1 + (r.nextDouble() < 0.15 ? 1 : 0)));
							int amount = r.nextInt(4) * (r.nextDouble() < 0.15 ? 2 : 1);
							if (amount > 0)
								world.dropItem(loc, new ItemStack(Material.WHEAT_SEEDS, amount));
						} else if (type.equals(Material.POTATOES) && data >= 7) {
							block.setType(Material.POTATOES);
							world.dropItem(loc, new ItemStack(Material.POTATO,
									(1 + r.nextInt(4)) * (r.nextDouble() < 0.15 ? 2 : 1)));
							if (r.nextDouble() < 0.02)
								world.dropItem(loc,
										new ItemStack(Material.POISONOUS_POTATO, 1 + (r.nextDouble() < 0.15 ? 1 : 0)));
						} else if (type.equals(Material.CARROTS) && data >= 7) {
							block.setType(Material.CARROTS);
							world.dropItem(loc, new ItemStack(Material.CARROT,
									(1 + r.nextInt(4) * (r.nextDouble() < 0.15 ? 2 : 1))));
						} else if (type.equals(Material.BEETROOTS) && data >= 3) {
							block.setType(Material.BEETROOTS);
							world.dropItem(loc, new ItemStack(Material.BEETROOT, 1 * (r.nextDouble() < 0.15 ? 2 : 1)));
							world.dropItem(loc, new ItemStack(Material.BEETROOT_SEEDS,
									(1 + r.nextInt(3)) * (r.nextDouble() < 0.15 ? 2 : 1)));
						} else if (type.equals(Material.PUMPKIN)) {
							ItemStack main = player.getInventory().getItemInMainHand();
							ItemStack off = player.getInventory().getItemInOffHand();
							if (main != null) {
								Material tool_type = main.getType();
								if (tool_type.equals(Material.SHEARS)) {
									return;
								}
							}
							if (off != null) {
								Material tool_type = off.getType();
								if (tool_type.equals(Material.SHEARS)) {
									return;
								}
							}
							block.setType(Material.AIR);
							world.dropItem(loc, new ItemStack(Material.PUMPKIN));
						} else if (type.equals(Material.MELON)) {
							block.setType(Material.AIR);
							world.dropItem(loc, new ItemStack(Material.MELON_SLICE,
									(3 + r.nextInt(5)) * (r.nextDouble() < 0.15 ? 2 : 1)));
						} else if (type.equals(Material.COCOA) && ((Cocoa) block.getBlockData()).getAge() >= 2) {
							Cocoa cocoa = ((Cocoa) block.getBlockData());
							cocoa.setAge(0);
							block.setBlockData(cocoa);
							world.dropItem(loc, new ItemStack(Material.COCOA_BEANS,
									(2 + r.nextInt(2)) * (r.nextDouble() < 0.15 ? 2 : 1)));
						}
					if (block.getType().equals(Material.ENCHANTING_TABLE)) {
						GUIStores.enchant.openGUI(player, 0);
						e.setCancelled(true);
					}
				}
			}
			Items main = Items.valueOf(player.getInventory().getItemInMainHand());
			if (e.getHand().equals(EquipmentSlot.HAND)) {
				ItemStack item = player.getInventory().getItemInMainHand();
				if (item != null) {
					Material type = item.getType();
					if (main != null) {
						if (main instanceof RightClickable && (e.getAction().equals(Action.RIGHT_CLICK_AIR)
								|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
							RightClickable rc = (RightClickable) main;
							rc.RightClick(player, item, e.getClickedBlock(), null);
							e.setCancelled(rc.isRightCancel());
						} else if (main instanceof LeftClickable && (e.getAction().equals(Action.LEFT_CLICK_AIR)
								|| e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
							LeftClickable lc = (LeftClickable) main;
							lc.LeftClick(player, item, e.getClickedBlock(), null);
							e.setCancelled(lc.isLeftCancel());
						}
					} else if (type.name().contains("SHULKER_BOX") && !player.isSneaking()) {
						BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
						ShulkerBox box = (ShulkerBox) meta.getBlockState();
						Inventory open = Bukkit.createInventory(box, InventoryType.SHULKER_BOX);
						open.setContents(box.getInventory().getContents());
						player.openInventory(open);
						e.setCancelled(true);
					} else if ((type.equals(Material.WOODEN_HOE) || type.equals(Material.STONE_HOE)
							|| type.equals(Material.IRON_HOE) || type.equals(Material.GOLDEN_HOE)
							|| type.equals(Material.DIAMOND_HOE) || type.equals(Material.NETHERITE_HOE))) {
						e.setCancelled(true);
						if (player.isSneaking()) {
							Block base = player.getLocation().getBlock();
							Random r = new Random();
							for (int x = -2; x <= 2; x++)
								for (int y = -2; y <= 2; y++)
									for (int z = -2; z <= 2; z++) {
										Location now_loc = base.getLocation().add(x, y, z);
										Block now = now_loc.getBlock();
										Material confirm = now.getType();
										if (confirm.equals(Material.WHEAT) || confirm.equals(Material.POTATOES)
												|| confirm.equals(Material.CARROTS)
												|| confirm.equals(Material.BEETROOTS) || confirm.equals(Material.COCOA))
											if (now.getBlockData() instanceof Ageable) {
												Ageable age = (Ageable) now.getBlockData();
												if (age.getAge() < age.getMaximumAge()) {
													if (r.nextDouble() < 0.15d) {
														age.setAge(age.getAge() + 1);
														now.setBlockData(age);
													}
													now_loc.getWorld().playEffect(now_loc, Effect.VILLAGER_PLANT_GROW,
															1);
													if (r.nextDouble() < 0.15d
															&& !player.getGameMode().equals(GameMode.CREATIVE)) {
														item.setDurability((short) (item.getDurability() + 1));
													}
													if (item.getDurability() > item.getType().getMaxDurability())
														player.getInventory()
																.setItemInMainHand(new ItemStack(Material.AIR));
												}
											}

									}
						} else {
							Location loc = player.getLocation();
							World world = loc.getWorld();
							Random r = new Random();
							for (int x = -2; x <= 2; x++)
								for (int y = -2; y <= 2; y++)
									for (int z = -2; z <= 2; z++) {
										Block block = loc.clone().add(x, y, z).getBlock();
										Material block_type = block.getType();
										byte data = block.getState().getRawData();
										if (block_type.equals(Material.WHEAT) && data >= 7) {
											block.setType(Material.WHEAT);
											world.dropItem(loc,
													new ItemStack(Material.WHEAT, 1 + (r.nextDouble() < 0.15 ? 1 : 0)));
											int amount = r.nextInt(4) * (r.nextDouble() < 0.15 ? 2 : 1);
											if (amount > 0)
												world.dropItem(loc, new ItemStack(Material.WHEAT_SEEDS, amount));
											if (r.nextDouble() < 0.15d
													&& !player.getGameMode().equals(GameMode.CREATIVE)) {
												item.setDurability((short) (item.getDurability() + 1));
											}
											if (item.getDurability() > item.getType().getMaxDurability())
												player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
										} else if (block_type.equals(Material.POTATOES) && data >= 7) {
											block.setType(Material.POTATOES);
											world.dropItem(loc, new ItemStack(Material.POTATO,
													(1 + r.nextInt(4)) * (r.nextDouble() < 0.15 ? 2 : 1)));
											if (r.nextDouble() < 0.02)
												world.dropItem(loc, new ItemStack(Material.POISONOUS_POTATO,
														1 + (r.nextDouble() < 0.15 ? 1 : 0)));
											if (r.nextDouble() < 0.15d
													&& !player.getGameMode().equals(GameMode.CREATIVE)) {
												item.setDurability((short) (item.getDurability() + 1));
											}
											if (item.getDurability() > item.getType().getMaxDurability())
												player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
										} else if (block_type.equals(Material.CARROTS) && data >= 7) {
											block.setType(Material.CARROTS);
											world.dropItem(loc, new ItemStack(Material.CARROT,
													(1 + r.nextInt(4) * (r.nextDouble() < 0.15 ? 2 : 1))));
											if (r.nextDouble() < 0.15d
													&& !player.getGameMode().equals(GameMode.CREATIVE)) {
												item.setDurability((short) (item.getDurability() + 1));
											}
											if (item.getDurability() > item.getType().getMaxDurability())
												player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
										} else if (block_type.equals(Material.BEETROOTS) && data >= 3) {
											block.setType(Material.BEETROOTS);
											world.dropItem(loc, new ItemStack(Material.BEETROOT,
													1 * (r.nextDouble() < 0.15 ? 2 : 1)));
											world.dropItem(loc, new ItemStack(Material.BEETROOT_SEEDS,
													(1 + r.nextInt(3)) * (r.nextDouble() < 0.15 ? 2 : 1)));
											if (r.nextDouble() < 0.15d
													&& !player.getGameMode().equals(GameMode.CREATIVE)) {
												item.setDurability((short) (item.getDurability() + 1));
											}
											if (item.getDurability() > item.getType().getMaxDurability())
												player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
										} else if (block_type.equals(Material.PUMPKIN)) {
											block.setType(Material.AIR);
											world.dropItem(loc, new ItemStack(Material.PUMPKIN));
										} else if (block_type.equals(Material.MELON)) {
											block.setType(Material.AIR);
											world.dropItem(loc, new ItemStack(Material.MELON_SLICE,
													(3 + r.nextInt(5)) * (r.nextDouble() < 0.15 ? 2 : 1)));
											if (r.nextDouble() < 0.15d
													&& !player.getGameMode().equals(GameMode.CREATIVE)) {
												item.setDurability((short) (item.getDurability() + 1));
											}
											if (item.getDurability() > item.getType().getMaxDurability())
												player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
										} else if (block_type.equals(Material.COCOA)
												&& ((Cocoa) block.getBlockData()).getAge() >= 2) {
											Cocoa cocoa = ((Cocoa) block.getBlockData());
											cocoa.setAge(0);
											block.setBlockData(cocoa);
											world.dropItem(loc, new ItemStack(Material.COCOA_BEANS,
													(2 + r.nextInt(2)) * (r.nextDouble() < 0.15 ? 2 : 1)));
											if (r.nextDouble() < 0.15d
													&& !player.getGameMode().equals(GameMode.CREATIVE)) {
												item.setDurability((short) (item.getDurability() + 1));
											}
											if (item.getDurability() > item.getType().getMaxDurability())
												player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
										}
									}
						}
					}
				}
			} else if (e.getHand().equals(EquipmentSlot.OFF_HAND)) {
				Items off = Items.valueOf(player.getInventory().getItemInOffHand());
				ItemStack item = player.getInventory().getItemInOffHand();
				if (item != null) {
					Material type = item.getType();
					if (main == null && off != null) {
						if (off instanceof RightClickable && (e.getAction().equals(Action.RIGHT_CLICK_AIR)
								|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
							RightClickable rc = (RightClickable) off;
							rc.RightClick(player, item, e.getClickedBlock(), null);
							e.setCancelled(rc.isRightCancel());
						} else if (off instanceof LeftClickable && (e.getAction().equals(Action.LEFT_CLICK_AIR)
								|| e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
							LeftClickable lc = (LeftClickable) off;
							lc.LeftClick(player, item, e.getClickedBlock(), null);
							e.setCancelled(lc.isLeftCancel());
						}
						e.setCancelled(true);
					} else if (main == null) {
						if ((type.equals(Material.WOODEN_HOE) || type.equals(Material.STONE_HOE)
								|| type.equals(Material.IRON_HOE) || type.equals(Material.GOLDEN_HOE)
								|| type.equals(Material.DIAMOND_HOE) || type.equals(Material.NETHERITE_HOE))) {
							if (player.isSneaking()) {
								Block base = player.getLocation().getBlock();
								Random r = new Random();
								for (int x = -2; x <= 2; x++)
									for (int y = -2; y <= 2; y++)
										for (int z = -2; z <= 2; z++) {
											Location now_loc = base.getLocation().add(x, y, z);
											Block now = now_loc.getBlock();
											Material confirm = now.getType();
											if (confirm.equals(Material.WHEAT) || confirm.equals(Material.POTATOES)
													|| confirm.equals(Material.CARROTS)
													|| confirm.equals(Material.BEETROOTS)
													|| confirm.equals(Material.COCOA))
												if (now.getBlockData() instanceof Ageable) {
													Ageable age = (Ageable) now.getBlockData();
													if (age.getAge() < age.getMaximumAge()) {
														if (r.nextDouble() < 0.15d) {
															age.setAge(age.getAge() + 1);
															now.setBlockData(age);
														}
														now_loc.getWorld().playEffect(now_loc,
																Effect.VILLAGER_PLANT_GROW, 1);
														if (r.nextDouble() < 0.15d
																&& !player.getGameMode().equals(GameMode.CREATIVE)) {
															item.setDurability((short) (item.getDurability() + 1));
														}
														if (item.getDurability() > item.getType().getMaxDurability())
															player.getInventory()
																	.setItemInOffHand(new ItemStack(Material.AIR));

													}
												}
										}
							} else {
								Location loc = player.getLocation();
								World world = loc.getWorld();
								Random r = new Random();
								for (int x = -2; x <= 2; x++)
									for (int y = -2; y <= 2; y++)
										for (int z = -2; z <= 2; z++) {
											Block block = loc.clone().add(x, y, z).getBlock();
											Material block_type = block.getType();
											byte data = block.getState().getRawData();
											if (block_type.equals(Material.WHEAT) && data >= 7) {
												block.setType(Material.WHEAT);
												world.dropItem(loc, new ItemStack(Material.WHEAT,
														1 + (r.nextDouble() < 0.15 ? 1 : 0)));
												int amount = r.nextInt(4) * (r.nextDouble() < 0.15 ? 2 : 1);
												if (amount > 0)
													world.dropItem(loc, new ItemStack(Material.WHEAT_SEEDS, amount));
												if (r.nextDouble() < 0.15d
														&& !player.getGameMode().equals(GameMode.CREATIVE)) {
													item.setDurability((short) (item.getDurability() + 1));
												}
												if (item.getDurability() > item.getType().getMaxDurability())
													player.getInventory()
															.setItemInMainHand(new ItemStack(Material.AIR));
											} else if (block_type.equals(Material.POTATOES) && data >= 7) {
												block.setType(Material.POTATOES);
												world.dropItem(loc, new ItemStack(Material.POTATO,
														(1 + r.nextInt(4)) * (r.nextDouble() < 0.15 ? 2 : 1)));
												if (r.nextDouble() < 0.02)
													world.dropItem(loc, new ItemStack(Material.POISONOUS_POTATO,
															1 + (r.nextDouble() < 0.15 ? 1 : 0)));
												if (r.nextDouble() < 0.15d
														&& !player.getGameMode().equals(GameMode.CREATIVE)) {
													item.setDurability((short) (item.getDurability() + 1));
												}
												if (item.getDurability() > item.getType().getMaxDurability())
													player.getInventory()
															.setItemInMainHand(new ItemStack(Material.AIR));
											} else if (block_type.equals(Material.CARROTS) && data >= 7) {
												block.setType(Material.CARROTS);
												world.dropItem(loc, new ItemStack(Material.CARROT,
														(1 + r.nextInt(4) * (r.nextDouble() < 0.15 ? 2 : 1))));
												if (r.nextDouble() < 0.15d
														&& !player.getGameMode().equals(GameMode.CREATIVE)) {
													item.setDurability((short) (item.getDurability() + 1));
												}
												if (item.getDurability() > item.getType().getMaxDurability())
													player.getInventory()
															.setItemInMainHand(new ItemStack(Material.AIR));
											} else if (block_type.equals(Material.BEETROOTS) && data >= 3) {
												block.setType(Material.BEETROOTS);
												world.dropItem(loc, new ItemStack(Material.BEETROOT,
														1 * (r.nextDouble() < 0.15 ? 2 : 1)));
												world.dropItem(loc, new ItemStack(Material.BEETROOT_SEEDS,
														(1 + r.nextInt(3)) * (r.nextDouble() < 0.15 ? 2 : 1)));
												if (r.nextDouble() < 0.15d
														&& !player.getGameMode().equals(GameMode.CREATIVE)) {
													item.setDurability((short) (item.getDurability() + 1));
												}
												if (item.getDurability() > item.getType().getMaxDurability())
													player.getInventory()
															.setItemInMainHand(new ItemStack(Material.AIR));
											} else if (block_type.equals(Material.PUMPKIN)) {
												block.setType(Material.AIR);
												world.dropItem(loc, new ItemStack(Material.PUMPKIN));
											} else if (block_type.equals(Material.MELON)) {
												block.setType(Material.AIR);
												world.dropItem(loc, new ItemStack(Material.MELON_SLICE,
														(3 + r.nextInt(5)) * (r.nextDouble() < 0.15 ? 2 : 1)));
												if (r.nextDouble() < 0.15d
														&& !player.getGameMode().equals(GameMode.CREATIVE)) {
													item.setDurability((short) (item.getDurability() + 1));
												}
												if (item.getDurability() > item.getType().getMaxDurability())
													player.getInventory()
															.setItemInMainHand(new ItemStack(Material.AIR));
											} else if (block_type.equals(Material.COCOA)
													&& ((Cocoa) block.getBlockData()).getAge() >= 2) {
												Cocoa cocoa = ((Cocoa) block.getBlockData());
												cocoa.setAge(0);
												block.setBlockData(cocoa);
												world.dropItem(loc, new ItemStack(Material.COCOA_BEANS,
														(2 + r.nextInt(2)) * (r.nextDouble() < 0.15 ? 2 : 1)));
												if (r.nextDouble() < 0.15d
														&& !player.getGameMode().equals(GameMode.CREATIVE)) {
													item.setDurability((short) (item.getDurability() + 1));
												}
												if (item.getDurability() > item.getType().getMaxDurability())
													player.getInventory()
															.setItemInMainHand(new ItemStack(Material.AIR));
											}
										}
							}
						}
					}
				}
			}
		}

	}

	@EventHandler
	public void Events(PlayerInteractEntityEvent e) {
		Player player = e.getPlayer();
		if (e.getRightClicked() != null) {
			Entity et = e.getRightClicked();
			if (et.getType().equals(EntityType.VILLAGER)) {
				if (et.hasMetadata("key"))
					for (MetadataValue v : et.getMetadata("key"))
						if (v.getOwningPlugin().equals(Core.getCore())) {
							String key = v.asString();
							ShopStores shop = ShopStores.valueOf(key);
							if (shop != null)
								shop.getGui().openGUI(player, 0);
							break;
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
	public void Events(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if (ConfigStore.getPlayerStatus(player)) {
			Jobs job = ConfigStore.getJob(player);
			if (job != null)
				job.getSpecialArts().Use(player, -1);
			e.setCancelled(true);
		}
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
	public void Events(PlayerSwapHandItemsEvent e) {
		Player player = e.getPlayer();
		if (ConfigStore.getPlayerStatus(player)) {
			Jobs job = ConfigStore.getJob(player);
			if (job != null)
				job.getIdentity().Use(player, -1);
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
				job.getSkills().get(slot).Use(player, slot);
			} else if (slot == 8)
				job.getSkills().get(7).Use(player, slot);
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
		e.setCancelled(HashMapStore.isSkillStop(player));
	}
}
