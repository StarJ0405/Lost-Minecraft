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
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;

public class AdrenalineItem extends PotionItems {
	private static HashMap<UUID, BukkitTask> tasks = new HashMap<UUID, BukkitTask>();
	private static HashMap<UUID, Double> powers = new HashMap<UUID, Double>();

	public AdrenalineItem(String key, ChatColor color, double power) {
		super(key, Material.SUGAR, color, "피해증가 : ", "%", power);
		this.lore.add(ChatColor.WHITE + "지속 시간 : " + getDuration() / 20 + "초");
	}

	public static void End(Player player) {
		UUID uuid = player.getUniqueId();
		if (tasks.containsKey(uuid))
			tasks.get(uuid).cancel();
		powers.remove(uuid);
	}

	public static double getPower(Player player) {
		UUID uuid = player.getUniqueId();
		if (tasks.containsKey(uuid) && !tasks.get(uuid).isCancelled() && powers.containsKey(uuid)) {
			return 1.0d + powers.get(uuid) / 100.0;
		}
		return 1.0D;
	}

	public int getDuration() {
		return 20 * 15;
	}

	@Override
	public boolean Use(Player player, ItemStack item) {
		Items i = Items.valueOf(item);
		if (i != null && i instanceof AdrenalineItem)
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
								player.sendMessage(ChatColor.RED + "아드레날린 지속시간이 끝났습니다.");
								End(player);
							}
						}
						this.cancel();
					}
				}.runTaskLater(Core.getCore(), getDuration()));
				powers.put(uuid, getValue(item));
				player.closeInventory();
				player.playSound(player, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 2f, 1f);
				Effects.Directional.CRIMSON_SPORE.spawnDirectional(player, player.getEyeLocation(), 10, 0.1, 0.1, 0.1,
						0);
				return true;
			}
		return false;
	}

}
