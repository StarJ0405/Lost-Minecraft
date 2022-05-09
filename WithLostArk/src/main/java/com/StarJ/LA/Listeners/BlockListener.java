package com.StarJ.LA.Listeners;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Items.DiggingItems;
import com.StarJ.LA.Systems.Basics;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.EnchantsType;

public class BlockListener implements Listener {
	@EventHandler
	public void Events(BlockDamageEvent e) {
		Player player = e.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		Block block = e.getBlock();
		if (item == null || EnchantsType.getEnchantType(item.getType()) == null)
			item = player.getInventory().getItemInOffHand();
		EnchantsType tool = EnchantsType.getEnchantType(item.getType());
		if (tool != null)
			if (tool.equals(EnchantsType.Pickaxe))
				if (ConfigStore.isBasicsDuration(player, Basics.Mining, 2))
					if (Basics.isMining(block.getType()) || block.getType().equals(Material.STONE)
							|| block.getType().equals(Material.NETHERRACK) || block.getType().equals(Material.DEEPSLATE)
							|| block.getType().equals(Material.COBBLED_DEEPSLATE)) {
						int level = Basics.Mining.getLevel(player);
						if (Basics.Mining.isActive(level)) {
							Material get = Basics.getMining(block.getType());
							if (!get.equals(Material.AIR)) {
								block.getWorld().dropItem(block.getLocation(), new ItemStack(get, 1 + (level / 10)));
							}
						}
						int exp = Basics.getMiningExp(block.getType());
						if (exp > 0) {
							Basics.Mining.addEXP(player, exp);
						}
						block.breakNaturally(item);
					}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Events(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Random r = new Random();
		Block block = e.getBlock();
		Location loc = block.getLocation();
		Material block_type = block.getType();
		if (ConfigStore.getPlayerStatus(player)) {
			e.setCancelled(true);
			return;
		}
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item == null || EnchantsType.getEnchantType(item.getType()) == null)
			item = player.getInventory().getItemInOffHand();
		EnchantsType tool = EnchantsType.getEnchantType(item.getType());
		if (tool != null)
			if (tool.equals(EnchantsType.Pickaxe)) {
				if (Basics.isMining(block_type)) {
					int level = Basics.Mining.getLevel(player);
					if (!item.containsEnchantment(Enchantment.SILK_TOUCH))
						if (Basics.Mining.isActive(level)) {
							Material get = Basics.getMining(block_type);
							if (!get.equals(Material.AIR)) {
								block.getWorld().dropItem(block.getLocation(), new ItemStack(get, 1 + (level / 10)));
							}
						}
					int exp = Basics.getMiningExp(block_type);
					if (exp > 0) {
						Basics.Mining.addEXP(player, exp);
					}
				}
			} else if (tool.equals(EnchantsType.Axe)) {
				if (Basics.isChopping(block_type)) {
					int level = Basics.Chopping.getLevel(player);
					Location confirm_loc = loc.clone();
					int exp = 0;
					while (Basics.isChopping(confirm_loc.getBlock().getType())) {
						for (int x = -3; x <= 3; x++)
							for (int z = -3; z <= 3; z++) {
								Block b = confirm_loc.clone().add(x, 0, z).getBlock();
								if (Basics.isChopping(b.getType())) {
									for (ItemStack i : Basics.getChopping(b.getType())) {
										int amount = i.getDurability() + r.nextInt(i.getAmount());
										if (amount > 0) {
											if (ConfigStore.isBasicsDuration(player, Basics.Chopping, 3))
												amount += 1;
											if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
												int ench_level = item
														.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
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
									Material confirm = b.getLocation().add(0, -1, 0).getBlock().getType();
									if (confirm.equals(Material.GRASS_BLOCK) || confirm.equals(Material.DIRT)
											|| confirm.equals(Material.COARSE_DIRT) || confirm.equals(Material.PODZOL)
											|| confirm.equals(Material.ROOTED_DIRT))
										if (Basics.Chopping.isActive(level))
											if (Basics.isChoppingSappling(b_type)) {
												b.setType(Basics.getChoppingSappling(b_type));
											} else if (b_type.equals(Material.MUSHROOM_STEM))
												if (r.nextBoolean()) {
													b.setType(Material.RED_MUSHROOM);
												} else
													b.setType(Material.BROWN_MUSHROOM);
									exp += Basics.getChoppingExp(b_type);
								}
							}
						confirm_loc.add(0, 1, 0);
					}
					player.playSound(player, Sound.BLOCK_WOOD_BREAK, 2f, 1f);
					if (exp > 0) {
						Basics.Chopping.addEXP(player, exp);
					}
				}
			} else if (tool.equals(EnchantsType.Shovel)) {
				if (Basics.isDigging(block_type)) {
					int level = Basics.Digging.getLevel(player);
					double add = 0;
					if (ConfigStore.isBasicsDuration(player, Basics.Digging, 2))
						add += Basics.Digging.getChance(level).doubleValue();
					int count = ConfigStore.getBasicsNumber(player, Basics.Digging, 3);
					if (ConfigStore.isBasicsDuration(player, Basics.Digging, 1)) {
						int range = level / 10 + 1;
						for (int x = -range; x <= range; x++)
							for (int z = -range; z <= range; z++) {
								Block b = loc.clone().add(x, 0, z).getBlock();
								if (Basics.isDigging(b.getType()))
									if (count > 0 || Basics.Digging.isActive(level)) {
										for (DiggingItems diggs : Basics.getDigging(b.getType())) {
											if (diggs.canGet(add))
												b.getWorld().dropItem(b.getLocation(), diggs.getItemStack());
										}
										if (count > 0)
											ConfigStore.setBasicsNumber(player, Basics.Digging, 3, count - 1);
									}
							}
					} else if (count > 0 || Basics.Digging.isActive(level)) {
						for (DiggingItems diggs : Basics.getDigging(block_type)) {
							if (diggs.canGet(add))
								block.getWorld().dropItem(block.getLocation(), diggs.getItemStack());
						}
						if (count > 0)
							ConfigStore.setBasicsNumber(player, Basics.Digging, 3, count - 1);
					}

					int exp = Basics.getDiggingExp(block_type);
					if (exp > 0) {
						Basics.Digging.addEXP(player, exp);
					}
				}
			}

	}
}
