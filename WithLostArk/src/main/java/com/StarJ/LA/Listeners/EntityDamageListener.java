package com.StarJ.LA.Listeners;

import java.util.List;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import com.StarJ.LA.Core;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.Basics;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.EnchantsType;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.Stats;
import com.StarJ.LA.Systems.Runnable.ActionBarRunnable;

public class EntityDamageListener implements Listener {
	@EventHandler
	public void Events(EntityDamageEvent e) {
		if (e.getEntityType().equals(EntityType.ARMOR_STAND)) {
			if (e.getCause().equals(DamageCause.BLOCK_EXPLOSION) || e.getCause().equals(DamageCause.ENTITY_EXPLOSION))
				e.setCancelled(true);
		} else if (e.getEntityType().equals(EntityType.VILLAGER)) {
			Entity et = e.getEntity();
			if (et.hasMetadata("key"))
				for (MetadataValue value : et.getMetadata("key"))
					if (value.getOwningPlugin().equals(Core.getCore())) {
						if (e.getCause().equals(DamageCause.VOID)) {
							HashMapStore.removeStores(et.getLocation());
							et.remove();
						} else {
							e.setCancelled(true);
						}
					}
		} else if (e.getEntityType().equals(EntityType.PLAYER)) {
			Player player = (Player) e.getEntity();
			if (!(e instanceof EntityDamageByEntityEvent) && ConfigStore.getPlayerStatus(player)) {
				Jobs job = ConfigStore.getJob(player);
				double max = job != null ? job.getMaxHealth(player) : 20;
				double health = HashMapStore.getHealth(player);
				health -= e.getDamage();

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
				e.setDamage(0.01d);
				ActionBarRunnable.run(player);
				for (String key : HashMapStore.getAttackedList(player)) {
					Skills skill = Skills.valueof(key);
					if (skill != null)
						e.setCancelled(skill.Attacked(player));
				}
			}
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Events(EntityDamageByEntityEvent e) {
		Entity vic_e = e.getEntity();
		Entity att_e = e.getDamager();
		boolean critical = false;
		if (att_e.getType().equals(EntityType.PLAYER)) {
			Player att = (Player) att_e;
			if (ConfigStore.getPlayerStatus(att)) {
				ActionBarRunnable.run(att);
				Jobs job = ConfigStore.getJob(att);
				for (String key : HashMapStore.getAttackList(att)) {
					Skills skill = Skills.valueof(key);
					if (skill != null)
						e.setCancelled(skill.Attack(att));
				}
				if (job != null && !e.isCancelled())
					HashMapStore.setIdentity(att, HashMapStore.getIdentity(att) + job.getWeapon().getIdentity());
				critical = Stats.isCritical(att);
			} else {
				ItemStack main = att.getInventory().getItemInMainHand();
				if (main != null) {
					EnchantsType tool = EnchantsType.getEnchantType(main.getType());
					if (tool != null && tool.equals(EnchantsType.Sword)) {
						if (ConfigStore.isBasicsDuration(att, Basics.Hunting, 3)) {
							Location loc = vic_e.getLocation().clone().add(0, 1, 0);
							Random r = new Random();
							for (ItemStack i : Basics.getHunting(vic_e.getType())) {
								ItemStack drop = i.clone();
								int amount = i.getDurability() + r.nextInt(drop.getAmount());
								if (amount > 0) {
									drop.setAmount(amount);
									loc.getWorld().dropItemNaturally(loc, drop);
								}
							}
							e.setDamage(0.01d);
						}
					}
				}
			}
		}

		if (vic_e.getType().equals(EntityType.VILLAGER) && vic_e.hasMetadata("key")
				&& att_e.getType().equals(EntityType.PLAYER)) {
			Player att = (Player) att_e;
			for (MetadataValue value : vic_e.getMetadata("key"))
				if (value.getOwningPlugin().equals(Core.getCore()))
					if (att.getGameMode().equals(GameMode.CREATIVE)) {
						HashMapStore.removeStores(vic_e.getLocation());
						vic_e.remove();
					} else
						e.setCancelled(true);

		} else if (vic_e.getType().equals(EntityType.PLAYER)) {
			Player vic = (Player) vic_e;
			if (att_e.getType().equals(EntityType.PLAYER)) {
				Player att = (Player) att_e;
				if (ConfigStore.getPlayerStatus(att) && ConfigStore.getPlayerStatus(vic)) {
					if (!(ConfigStore.getPVP(att) && ConfigStore.getPVP(vic))) {
						e.setCancelled(true);
						return;
					} else
						for (String key : HashMapStore.getAttackedList(vic)) {
							Skills skill = Skills.valueof(key);
							if (skill != null)
								e.setCancelled(skill.Attacked(vic, att));
						}
				} else if (ConfigStore.getPlayerStatus(att) || ConfigStore.getPlayerStatus(vic)) {
					e.setCancelled(true);
					return;
				}
			} else if (ConfigStore.getPlayerStatus(vic))
				for (String key : HashMapStore.getAttackedList(vic)) {
					Skills skill = Skills.valueof(key);
					if (skill != null)
						e.setCancelled(skill.Attacked(vic));
				}
			if (ConfigStore.getPlayerStatus(vic)) {
				Jobs vic_job = ConfigStore.getJob(vic);
				double vic_max = vic_job != null ? vic_job.getMaxHealth(vic) : 20;
				double vic_health = HashMapStore.getHealth(vic);
				vic_health -= e.getDamage() * (critical ? 2 : 1);

				double vic_per = vic_health / vic_max * 100;
				if (vic_per <= 1 && vic_per > 0) {
					vic_per = 1;
				} else if (vic_per >= 99 && vic_per < 100) {
					vic_per = 99;
				}
				if (vic_per > 100) {
					vic_per = 100;
				}
				if (vic_per <= 0)
					if (vic_health > 0) {
						vic_per = 1;
					} else
						vic_per = 0;
				HashMapStore.setHealth(vic, vic_health);
				vic.setHealth(vic_per);
				e.setDamage(0.01d);
				ActionBarRunnable.run(vic);
			}
		}
		if (critical) {
			e.setDamage(e.getDamage() * 2);
			Location loc = vic_e.getLocation().clone().add(0, 1.5, 0);
			Effects.Directional.CRIT_MAGIC.spawnDirectional(loc, 25, 0.5, 0.5, 0.5, 0.1);
		}
	}

	@EventHandler
	public void Events(EntityRegainHealthEvent e) {
		if (e.getEntityType().equals(EntityType.PLAYER)) {
			Player player = (Player) e.getEntity();
			if (ConfigStore.getPlayerStatus(player)) {
				Jobs job = ConfigStore.getJob(player);
				double max = job != null ? job.getMaxHealth(player) : 20;
				double health = HashMapStore.getHealth(player);
				health += e.getAmount();
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
				player.setHealth(per);
				HashMapStore.setHealth(player, health);
				e.setCancelled(true);
				ActionBarRunnable.run(player);
			}
		}
	}

	@EventHandler
	public void Events(HangingBreakByEntityEvent e) {
		if (e.getCause().equals(RemoveCause.EXPLOSION))
			e.setCancelled(true);
	}

	@EventHandler
	public void Events(HangingBreakEvent e) {
		if (e.getCause().equals(RemoveCause.EXPLOSION))
			e.setCancelled(true);
	}

	@EventHandler
	public void Events(EntityDeathEvent e) {
		if (!e.getEntityType().equals(EntityType.PLAYER)) {
			LivingEntity le = e.getEntity();
			if (le.getKiller() != null) {
				Player player = le.getKiller();
				if (!ConfigStore.getPlayerStatus(player)) {
					ItemStack main = player.getInventory().getItemInMainHand();
					if (main != null) {
						EnchantsType tool = EnchantsType.getEnchantType(main.getType());
						if (tool != null) {
							if (tool.equals(EnchantsType.Sword)) {
								if (Basics.isHunting(le.getType())) {
									int level = Basics.Hunting.getLevel(player);
									List<ItemStack> list = e.getDrops();
									for (ItemStack item : list)
										if (Basics.Hunting.isActive(level))
											item.setAmount(item.getAmount() + 2 + new Random().nextInt(level / 5 + 1));
									Basics.Hunting.addEXP(player, Basics.getHuntingExp(le.getType()));
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void Events(EntityInteractEvent e) {
		Block b = e.getBlock();
		if (b != null)
			if (b.getType().equals(Material.FARMLAND))
				e.setCancelled(true);
	}
}
