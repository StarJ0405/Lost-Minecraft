package com.StarJ.LA.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import com.StarJ.LA.Items.CookingItem;
import com.StarJ.LA.Items.FishBoxItem;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.GUIStores;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;

public class InventoryListener implements Listener {

	@EventHandler
	public void Events(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		Inventory inv = e.getClickedInventory();
		if (ConfigStore.getPlayerStatus(player)) {
			e.setCancelled(true);
			int slot = e.getSlot();
			if (slot >= 13 && slot <= 16) {
				int num = slot - 13;
				ItemStack[] items = ConfigStore.getConsumeItems(player);
				ItemStack item = items[num];
				if (item != null) {
					Items i = Items.valueOf(item);
					if (i != null)
						if (i instanceof CookingItem) {
							if (CookingItem.use(player, item)) {
								item.setAmount(item.getAmount() - 1);
								if (item.getAmount() <= 0) {
									item = null;
								}
								items[num] = item;
								ConfigStore.setConsumeItems(player, items);
								if (item != null) {
									inv.setItem(slot, item);
								} else
									inv.setItem(slot, ConfigStore.getEmpty());
								player.playSound(player, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 2f, 1f);
								Effects.Directional.HEART.spawnDirectional(player.getEyeLocation(), 5, 0.1, 0.1, 0.1, 1);
								Jobs job = ConfigStore.getJob(player);
								double max = job != null ? job.getMaxHealth(player) : 20;
								double health = HashMapStore.getHealth(player);
								double per = health / max * 100;
								if (per <= 1 && per > 0) {
									per = 1;
								} else if (per >= 99 && per < 100) {
									per = 99;
								}
								if (per > 100) {
									per = 100;
								}
								if (per <= 0)
									if (health > 0) {
										per = 1;
									} else
										per = 0;
								HashMapStore.setHealth(player, health);
								player.setHealth(per);
								ActionBarRunnable.run(player);
								player.closeInventory();
							}
						}
				}
			}
		}
		if (inv != null) {
			String title = e.getView().getTitle();
			if (title != null) {
				GUIStores store = GUIStores.getGUI(title);
				if (store != null) {
					e.setCancelled(
							store.Click(player, e.getClick(), e.getSlot(), e.getRawSlot(), e.getCurrentItem(), inv));
				} else if (title.equalsIgnoreCase("Shulker Box")) {
					if (e.getAction().equals(InventoryAction.HOTBAR_SWAP)
							&& !(player.getInventory().getItemInMainHand() != null && player.getInventory()
									.getItemInMainHand().getType().name().contains("SHULKER_BOX"))
							&& player.getInventory().getItemInOffHand() != null
							&& player.getInventory().getItemInOffHand().getType().name().contains("SHULKER_BOX")) {
						ItemStack off = player.getInventory().getItemInOffHand();
						e.setCancelled(true);
						player.getInventory().setItemInOffHand(off);
					} else if ((e.getRawSlot() - 54) == player.getInventory().getHeldItemSlot()
							&& player.getInventory().getItemInMainHand() != null
							&& player.getInventory().getItemInMainHand().getType().name().contains("SHULKER_BOX"))
						e.setCancelled(true);

				} else if (title.equalsIgnoreCase(ChatColor.AQUA + "어항")) {
					if (e.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
						player.updateInventory();
						e.setCancelled(true);
						player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
						return;
					}
					ItemStack cursor = e.getCursor();
					ItemStack clicked = e.getCurrentItem();
					if (cursor != null && !cursor.getType().equals(Material.AIR)) {
						if (clicked != null && !clicked.getType().equals(Material.AIR)) {
							Material type = clicked.getType();
							if (type.equals(Material.SALMON) || type.equals(Material.COD)
									|| type.equals(Material.PUFFERFISH) || type.equals(Material.TROPICAL_FISH)) {
								e.setCancelled(false);
							} else
								e.setCancelled(true);
						} else
							e.setCancelled(false);
					} else {
						if (clicked != null && !clicked.getType().equals(Material.AIR)) {
							Material type = clicked.getType();
							if (type.equals(Material.SALMON) || type.equals(Material.COD)
									|| type.equals(Material.PUFFERFISH) || type.equals(Material.TROPICAL_FISH)) {
								e.setCancelled(false);
							} else
								e.setCancelled(true);
						} else
							e.setCancelled(true);
					}

				}

			}
		}
	}

	@EventHandler
	public void Events(InventoryDragEvent e) {
		Inventory inv = e.getInventory();
		Player player = (Player) e.getWhoClicked();
		if (inv != null) {
			String title = e.getView().getTitle();
			if (title != null) {
				GUIStores store = GUIStores.getGUI(title);
				if (store != null)
					e.setCancelled(store.Drag(player, e.getInventorySlots(), e.getRawSlots()));
			}
			if (ConfigStore.getPlayerStatus(player))
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void Events(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		Inventory inv = e.getInventory();
		if (inv != null) {
			String title = e.getView().getTitle();
			if (title != null) {
				GUIStores store = GUIStores.getGUI(title);
				if (store != null) {
					store.Close(player, inv);
				} else if (title.equalsIgnoreCase("Shulker Box")) {
					ItemStack item = player.getInventory().getItemInMainHand();
					boolean main = true;
					if (!(player.getInventory().getItemInMainHand() != null
							&& player.getInventory().getItemInMainHand().getType().name().contains("SHULKER_BOX"))
							&& player.getInventory().getItemInOffHand() != null
							&& player.getInventory().getItemInOffHand().getType().name().contains("SHULKER_BOX")) {
						item = player.getInventory().getItemInOffHand();
						main = false;
					}
					BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
					ShulkerBox box = (ShulkerBox) meta.getBlockState();
					box.getInventory().setContents(inv.getContents());
					meta.setBlockState(box);
					box.update();
					item.setItemMeta(meta);
					if (main) {
						player.getInventory().setItemInMainHand(item);
					} else
						player.getInventory().setItemInOffHand(item);
				} else if (title.equalsIgnoreCase(ChatColor.AQUA + "어항")) {
					ItemStack item = player.getInventory().getItemInMainHand();
					Items main_i = Items.valueOf(item);
					Items off_i = Items.valueOf(player.getInventory().getItemInOffHand());
					boolean main = true;
					if (!(main_i != null && main_i instanceof FishBoxItem) && off_i != null
							&& off_i instanceof FishBoxItem) {
						item = player.getInventory().getItemInOffHand();
						main = false;
					}
					BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
					ShulkerBox box = (ShulkerBox) meta.getBlockState();
					box.getInventory().setContents(inv.getContents());
					meta.setBlockState(box);
					box.update();
					item.setItemMeta(meta);
					if (main) {
						player.getInventory().setItemInMainHand(item);
					} else
						player.getInventory().setItemInOffHand(item);
				}
			}
		}
	}

	@EventHandler
	public void Events(PrepareAnvilEvent e) {
		Inventory inv = e.getInventory();
		ItemStack one = inv.getItem(0);
		ItemStack two = inv.getItem(1);
		if (one != null && two != null)
			if (!one.getType().equals(Material.ENCHANTED_BOOK)) {
				ItemStack result = e.getResult();
				if (result != null) {
					Map<Enchantment, Integer> o_map = one.getEnchantments();
					Map<Enchantment, Integer> r_map = result.getEnchantments();
					if (o_map != null && r_map != null)
						for (Enchantment ench : r_map.keySet())
							if (!(o_map.containsKey(ench) && r_map.get(ench) == o_map.get(ench)))
								e.setResult(new ItemStack(Material.AIR));
				}
			} else if (two.getType().equals(Material.ENCHANTED_BOOK)) {
				e.getInventory().setRepairCost(2);
				e.getInventory().setRepairCostAmount(2);
				if (one.hasItemMeta() && two.hasItemMeta()) {
					EnchantmentStorageMeta o_meta = (EnchantmentStorageMeta) one.getItemMeta();
					EnchantmentStorageMeta t_meta = (EnchantmentStorageMeta) two.getItemMeta();
					ItemStack result = one.clone();
					EnchantmentStorageMeta r_meta = (EnchantmentStorageMeta) result.getItemMeta();

					Map<Enchantment, Integer> o_map = o_meta.getStoredEnchants();
					Map<Enchantment, Integer> t_map = t_meta.getStoredEnchants();
					HashMap<Enchantment, Integer> r_map = new HashMap<Enchantment, Integer>();
					for (Enchantment ench : t_map.keySet())
						if (o_map.containsKey(ench) && t_map.containsKey(ench)) {
							int o_level = o_map.get(ench);
							int t_level = t_map.get(ench);
//							r_map.put(ench, o_level == t_level ? o_level + 1 : (o_level > t_level ? o_level : t_level));
							r_map.put(ench, o_level + t_level);
						} else
							r_map.put(ench, t_map.get(ench));
					List<String> lore = new ArrayList<String>();
					for (Enchantment ench : r_map.keySet()) {
						int level = r_map.get(ench);
						r_meta.addStoredEnchant(ench, level, true);
						if (level > 255) {
							lore.add(ChatColor.GRAY + ench.getKey().getKey() + " 레벨 : " + level);
						}
					}
					r_meta.setLore(lore);
					result.setItemMeta(r_meta);
					e.setResult(result);
				}
			}

	}
}