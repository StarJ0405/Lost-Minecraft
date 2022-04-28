package com.StarJ.LA.Listeners;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Systems.Basics;
import com.StarJ.LA.Systems.ConfigStore;

public class BlockListener implements Listener {
	@EventHandler
	public void Events(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Random r = new Random();
		Block b = e.getBlock();
		Location loc = b.getLocation();
		Material type = b.getType();
		if (ConfigStore.getPlayerStatus(player)) {
			e.setCancelled(true);
			return;
		}
		if (!player.getGameMode().equals(GameMode.CREATIVE)) {
			if (Basics.isMining(type)) {
				int level = Basics.Mining.getLevel(player);
				if (Basics.Mining.isActive(level)) {
					Material get = Basics.getMining(type);
					if (!get.equals(Material.AIR)) {
						b.getWorld().dropItem(b.getLocation(), new ItemStack(get, 1 + (level / 10)));
					}
				}
			}
			int exp = Basics.getMiningExp(type);
			if (exp > 0) {
				Basics.Mining.addEXP(player, exp);
			}
			if (type.equals(Material.SUGAR_CANE)) {
				for (int x = -10; x <= 10; x++)
					for (int z = -10; z <= 10; z++) {
						Location now_loc = loc.clone().add(x, 0, z);
						Block now = now_loc.getBlock();
						Material now_type = now.getType();
						if (now_type.equals(Material.SUGAR_CANE)) {
							now.setType(Material.AIR);
							now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.SUGAR_CANE));
						}
					}

			} else if (type.equals(Material.BAMBOO)) {
				for (int x = -10; x <= 10; x++)
					for (int z = -10; z <= 10; z++) {
						Location now_loc = loc.clone().add(x, 0, z);
						Block now = now_loc.getBlock();
						Material now_type = now.getType();
						if (now_type.equals(Material.BAMBOO)) {
							now.setType(Material.AIR);
							now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.BAMBOO));
						}
					}
			} else if (type.equals(Material.ACACIA_LOG) || type.equals(Material.BIRCH_LOG)
					|| type.equals(Material.DARK_OAK_LOG) || type.equals(Material.JUNGLE_LOG)
					|| type.equals(Material.OAK_LOG) || type.equals(Material.SPRUCE_LOG)) {
				ItemStack main = player.getInventory().getItemInMainHand();
				if (main != null) {
					Material main_type = main.getType();
					if (main_type.equals(Material.WOODEN_AXE) || main_type.equals(Material.STONE_AXE)
							|| main_type.equals(Material.GOLDEN_AXE) || main_type.equals(Material.DIAMOND_AXE)
							|| main_type.equals(Material.IRON_AXE) || main_type.equals(Material.NETHERITE_AXE))
						for (int x = -3; x <= 3; x++)
							for (int y = -7; y <= 7; y++)
								for (int z = -3; z <= 3; z++) {
									Location now_loc = loc.clone().add(x, y, z);
									Block now = now_loc.getBlock();
									Material now_type = now.getType();
									if (now_type.equals(Material.ACACIA_LOG) || now_type.equals(Material.BIRCH_LOG)
											|| now_type.equals(Material.DARK_OAK_LOG)
											|| now_type.equals(Material.JUNGLE_LOG) || now_type.equals(Material.OAK_LOG)
											|| now_type.equals(Material.SPRUCE_LOG)) {
										now.setType(Material.AIR);
										now_loc.getWorld().dropItem(now_loc, new ItemStack(now_type));
									}
								}
				}
			} else if (type.equals(Material.ACACIA_LEAVES) || type.equals(Material.AZALEA_LEAVES)
					|| type.equals(Material.BIRCH_LEAVES) || type.equals(Material.DARK_OAK_LEAVES)
					|| type.equals(Material.FLOWERING_AZALEA_LEAVES) || type.equals(Material.JUNGLE_LEAVES)
					|| type.equals(Material.OAK_LEAVES) || type.equals(Material.SPRUCE_LEAVES)) {
				ItemStack main = player.getInventory().getItemInMainHand();
				if (main != null) {
					Material main_type = main.getType();
					if (main_type.equals(Material.WOODEN_AXE) || main_type.equals(Material.STONE_AXE)
							|| main_type.equals(Material.IRON_AXE) || main_type.equals(Material.GOLDEN_AXE)
							|| main_type.equals(Material.DIAMOND_AXE) || main_type.equals(Material.NETHERITE_AXE))
						for (int x = -3; x <= 3; x++)
							for (int y = -7; y <= 7; y++)
								for (int z = -3; z <= 3; z++) {
									Location now_loc = loc.clone().add(x, y, z);
									Block now = now_loc.getBlock();
									Material now_type = now.getType();
									if (now_type.equals(Material.ACACIA_LEAVES)) {
										now.setType(Material.AIR);
										if (r.nextDouble() < 0.1D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.ACACIA_SAPLING));
										if (r.nextDouble() < 0.05D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.STICK, 1 + new Random().nextInt(2)));
										if (r.nextDouble() < 0.01D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.APPLE));
									} else if (now_type.equals(Material.AZALEA_LEAVES)) {
										now.setType(Material.AIR);
										if (r.nextDouble() < 0.1D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.AZALEA));
										if (r.nextDouble() < 0.05D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.STICK, 1 + new Random().nextInt(2)));
										if (r.nextDouble() < 0.01D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.APPLE));
									} else if (now_type.equals(Material.BIRCH_LEAVES)) {
										now.setType(Material.AIR);
										if (r.nextDouble() < 0.1D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.BIRCH_SAPLING));
										if (r.nextDouble() < 0.05D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.STICK, 1 + new Random().nextInt(2)));
										if (r.nextDouble() < 0.01D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.APPLE));
									} else if (now_type.equals(Material.DARK_OAK_LEAVES)) {
										now.setType(Material.AIR);
										if (r.nextDouble() < 0.1D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.DARK_OAK_SAPLING));
										if (r.nextDouble() < 0.05D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.STICK, 1 + new Random().nextInt(2)));
										if (r.nextDouble() < 0.01D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.APPLE));
									} else if (now_type.equals(Material.FLOWERING_AZALEA_LEAVES)) {
										now.setType(Material.AIR);
										if (r.nextDouble() < 0.1D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.FLOWERING_AZALEA));
										if (r.nextDouble() < 0.05D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.STICK, 1 + new Random().nextInt(2)));
										if (r.nextDouble() < 0.01D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.APPLE));
									} else if (now_type.equals(Material.JUNGLE_LEAVES)) {
										now.setType(Material.AIR);
										if (r.nextDouble() < 0.1D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.JUNGLE_SAPLING));
										if (r.nextDouble() < 0.05D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.STICK, 1 + new Random().nextInt(2)));
										if (r.nextDouble() < 0.01D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.APPLE));
									} else if (now_type.equals(Material.OAK_LEAVES)) {
										now.setType(Material.AIR);
										if (r.nextDouble() < 0.1D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.OAK_SAPLING));
										if (r.nextDouble() < 0.05D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.STICK, 1 + new Random().nextInt(2)));
										if (r.nextDouble() < 0.01D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.APPLE));
									} else if (now_type.equals(Material.SPRUCE_LEAVES)) {
										now.setType(Material.AIR);
										if (r.nextDouble() < 0.1D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.SPRUCE_SAPLING));
										if (r.nextDouble() < 0.05D)
											now_loc.getWorld().dropItem(now_loc,
													new ItemStack(Material.STICK, 1 + new Random().nextInt(2)));
										if (r.nextDouble() < 0.01D)
											now_loc.getWorld().dropItem(now_loc, new ItemStack(Material.APPLE));
									}
								}
				}
			}
		}

	}
}
