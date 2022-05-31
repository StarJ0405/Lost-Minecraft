package com.StarJ.LA.Items.Potioning;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.StarJ.LA.Core;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.PotionItems;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.Jobs;

public class SpeedRobeItem extends PotionItems {
	private static HashMap<UUID, BukkitTask> tasks = new HashMap<UUID, BukkitTask>();

	public SpeedRobeItem(String key, ChatColor color, double duration) {
		super(key, Material.PHANTOM_MEMBRANE, color, "지속 시간 : ", "", duration);
		this.lore.add(ChatColor.WHITE + "속도 증가량 : " + Math.round((getPower()) * 100f) + "%");
	}

	public static void End(Player player) {
		UUID uuid = player.getUniqueId();
		if (tasks.containsKey(uuid))
			tasks.get(uuid).cancel();
	}

	private static float getPower() {
		return 0.2f;
	}

	public static float getPower(Player player) {
		UUID uuid = player.getUniqueId();
		return tasks.containsKey(uuid) && !tasks.get(uuid).isCancelled() ? getPower() : 0f;
	}

	@Override
	public boolean Use(Player player, ItemStack item) {
		Items i = Items.valueOf(item);
		if (i != null && i instanceof SpeedRobeItem)
			if (!player.hasCooldown(this.type)) {
				Jobs job = ConfigStore.getJob(player);
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
								player.sendMessage(ChatColor.RED + "신속 로브 지속시간이 끝났습니다.");
								End(player);
								player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)
										.setBaseValue(job.getWalkspeed(player));
							}
						}
						this.cancel();
					}
				}.runTaskLater(Core.getCore(), (int) (getValue(item) * 20)));
				player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(job.getWalkspeed(player));
				player.closeInventory();
				player.playSound(player, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 2f, 1f);
				Effects.Directional.CRIMSON_SPORE.spawnDirectional(player, player.getEyeLocation(), 10, 0.1, 0.1, 0.1,
						0);
				return true;
			}
		return false;
	}

}
