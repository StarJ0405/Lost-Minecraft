package com.StarJ.LA.Items.Potioning;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.PotionItems;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;

public class AwakeningItem extends PotionItems {

	public AwakeningItem(String key, ChatColor color, double power) {
		super(key, Material.SUGAR, color, "아이덴티티 회복 : ", "%", power);
	}

	@Override
	public boolean Use(Player player, ItemStack item) {
		Items i = Items.valueOf(item);
		if (i != null && i instanceof AwakeningItem)
			if (!player.hasCooldown(this.type)) {
				Jobs job = ConfigStore.getJob(player);
				if (job != null) {
					if (!player.getGameMode().equals(GameMode.CREATIVE))
						player.setCooldown(this.type, getCooldown());
					player.closeInventory();
					double identity = HashMapStore.getIdentity(player);
					identity += getValue(item) / 100.0 * job.getMaxIdentity();
					HashMapStore.setIdentity(player, identity);
					player.playSound(player, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 2f, 1f);
					Effects.Directional.CRIMSON_SPORE.spawnDirectional(player, player.getEyeLocation(), 10, 0.1, 0.1,
							0.1, 0);
					return true;
				}
			}
		return false;
	}

}
