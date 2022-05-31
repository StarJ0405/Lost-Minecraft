package com.StarJ.LA.Listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Items.Potioning.AbsorptionItem;
import com.StarJ.LA.Items.Potioning.AdrenalineItem;
import com.StarJ.LA.Items.Potioning.CorrosionGranadeItem;
import com.StarJ.LA.Items.Potioning.DarkGranadeItem;
import com.StarJ.LA.Items.Potioning.SpeedRobeItem;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;
import com.StarJ.LA.Systems.Runnable.BuffRunnable;
import com.StarJ.LA.Systems.Runnable.ComboCoolRunnable;
import com.StarJ.LA.Systems.Runnable.SkillCoolRunnable;

public class PlayerDeathListener implements Listener {
	@EventHandler
	public void Events(PlayerDeathEvent e) {
		Player player = e.getEntity();
		Jobs job = ConfigStore.getJob(player);
		if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE)) {
			Location loc = player.getLocation();
			World world = loc.getWorld();
			e.setKeepInventory(true);
			Random r = new Random();
			List<ItemStack> drops = new ArrayList<ItemStack>();
			ItemStack[] items = player.getInventory().getContents();
			if (!ConfigStore.getPlayerStatus(player))
				for (int c = 0; c < items.length; c++) {
					ItemStack i = items[c];
					if (i != null && !i.getType().equals(Material.AIR)) {
						if (r.nextDouble() < 0.3d) {
							if (r.nextDouble() < 0.5d)
								drops.add(i);
							items[c] = new ItemStack(Material.AIR);
						}
					}
				}
			int level = player.getLevel();
			if (level > 0 && r.nextDouble() < 0.3d) {
				level = r.nextInt(level);
			}
			player.setLevel(level);
			player.getInventory().setContents(items);
			if (job != null && ConfigStore.getPlayerStatus(player)) {
				List<ItemStack> jobitems = job.getJobitems(player);
				for (int c = 0; c < 9; c++)
					if (c < jobitems.size())
						player.getInventory().setItem(c, jobitems.get(c));
			}
			for (ItemStack drop : drops)
				world.dropItemNaturally(loc, drop);
		}
		if (job != null && ConfigStore.getPlayerStatus(player)) {
			for (Skills skill : job.getSkills(player)) {
				ComboCoolRunnable.EndCombo(player, skill);
				SkillCoolRunnable.end(player, skill);
				skill.End(player, 0);
			}
		}
		HashMapStore.setHealth(player, job != null ? job.getMaxHealth(player) : 20);
		HashMapStore.cancelActionbar(player);
		ActionBarRunnable.run(player);
		for (String key : HashMapStore.getAttackedList(player)) {
			Skills skill = Skills.valueof(key);
			if (skill != null)
				BuffRunnable.cancel(player, skill);
		}
		for (String key : HashMapStore.getAttackList(player)) {
			Skills skill = Skills.valueof(key);
			if (skill != null)
				BuffRunnable.cancel(player, skill);
		}

		AdrenalineItem.End(player);
		CorrosionGranadeItem.End(player);
		DarkGranadeItem.End(player);
		SpeedRobeItem.End(player);
		AbsorptionItem.End(player);
	}

	@EventHandler
	public void Events(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		HashMapStore.cancelActionbar(player);
		Location loc = player.getWorld().getSpawnLocation();
		e.setRespawnLocation(loc);
		player.teleport(loc);
	}
}
