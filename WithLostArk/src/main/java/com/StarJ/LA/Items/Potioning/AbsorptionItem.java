package com.StarJ.LA.Items.Potioning;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.StarJ.LA.Core;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.PotionItems;
import com.StarJ.LA.Listeners.EntityDamageListener;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;

public class AbsorptionItem extends PotionItems {
	private static HashMap<UUID, BukkitTask> tasks = new HashMap<UUID, BukkitTask>();

	public AbsorptionItem(String key, ChatColor color, double power) {
		super(key, Material.SUGAR, color, "보호막 : ", "", power);
		this.lore.add(ChatColor.GREEN + "지속 시간 : " + getDuration() / 20 + "초");
	}

	public static void End(Player player) {
		UUID uuid = player.getUniqueId();
		if (tasks.containsKey(uuid))
			tasks.get(uuid).cancel();
	}

	public int getDuration() {
		return 20 * 20;
	}

	@Override
	public boolean Use(Player player, ItemStack item) {
		Items i = Items.valueOf(item);
		if (i != null && i instanceof AbsorptionItem)
			if (!player.hasCooldown(this.type)) {
				if (!player.getGameMode().equals(GameMode.CREATIVE))
					player.setCooldown(this.type, getCooldown());
				End(player);
				UUID uuid = player.getUniqueId();
				tasks.put(uuid, new BukkitRunnable() {
					OfflinePlayer off = player;

					@Override
					public void run() {
						if (off.isOnline()) {
							Player player = off.getPlayer();
							if (ConfigStore.getPlayerStatus(player)) {
								HashMapStore.setAbsorption(player, key, 0);
								EntityDamageListener.confirmHealthPercent(ConfigStore.getJob(player), player,
										HashMapStore.getHealth(player), HashMapStore.getAllAbsorption(player));
								ActionBarRunnable.run(player);
								End(player);
							}
						}
						this.cancel();
					}
				}.runTaskLater(Core.getCore(), getDuration()));
				HashMapStore.setAbsorption(player, key, getValue(item));
				EntityDamageListener.confirmHealthPercent(ConfigStore.getJob(player), player,
						HashMapStore.getHealth(player), HashMapStore.getAllAbsorption(player));
				ActionBarRunnable.run(player);
				player.closeInventory();
				player.playSound(player, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 2f, 1f);
				Effects.Directional.CRIMSON_SPORE.spawnDirectional(player, player.getEyeLocation(), 10, 0.1, 0.1, 0.1,
						0);
				return true;
			}
		return false;
	}

}
