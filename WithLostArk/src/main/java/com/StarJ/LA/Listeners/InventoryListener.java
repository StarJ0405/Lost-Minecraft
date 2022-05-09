package com.StarJ.LA.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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

import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.GUIStores;

public class InventoryListener implements Listener {

	@EventHandler
	public void Events(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		Inventory inv = e.getClickedInventory();
		if (ConfigStore.getPlayerStatus(player)) {
			e.setCancelled(true);
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