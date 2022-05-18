package com.StarJ.LA.Items.Potioning;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.PotionItems;
import com.StarJ.LA.Systems.Effects;

public class InvisibleRobeItem extends PotionItems {

	public InvisibleRobeItem(String key, ChatColor color, double duration) {
		super(key, Material.ELYTRA, color, "지속 시간 : ", "", duration);
	}

	@Override
	public boolean Use(Player player, ItemStack item) {
		Items i = Items.valueOf(item);
		if (i != null && i instanceof InvisibleRobeItem)
			if (!player.hasCooldown(this.type)) {
				if (!player.getGameMode().equals(GameMode.CREATIVE))
					player.setCooldown(this.type, getCooldown());
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (int) (getValue(item) * 20), 0,
						false, true, false));
				player.closeInventory();
				player.playSound(player, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 2f, 1f);
				Effects.Directional.CRIMSON_SPORE.spawnDirectional(player, player.getEyeLocation(), 10, 0.1, 0.1, 0.1,
						0);
				return true;
			}
		return false;
	}

}
