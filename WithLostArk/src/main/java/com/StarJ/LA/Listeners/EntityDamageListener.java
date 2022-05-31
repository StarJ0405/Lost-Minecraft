package com.StarJ.LA.Listeners;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
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
import org.bukkit.scheduler.BukkitRunnable;

import com.StarJ.LA.Core;
import com.StarJ.LA.Items.CookingIngredient;
import com.StarJ.LA.Items.FishItem;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.Potioning.AdrenalineItem;
import com.StarJ.LA.Items.Potioning.DarkGranadeItem;
import com.StarJ.LA.Skills.Skills;
import com.StarJ.LA.Systems.Basics;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.EnchantsType;
import com.StarJ.LA.Systems.HashMapStore;
import com.StarJ.LA.Systems.Jobs;
import com.StarJ.LA.Systems.ShopStores;
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
						} else if (!value.asString().equals(ShopStores.Training.name())
								&& !(e.getCause().equals(DamageCause.ENTITY_ATTACK)
										|| e.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK)))
							e.setCancelled(true);

					}
		} else if (e.getEntityType().equals(EntityType.PLAYER)) {
			Player vic = (Player) e.getEntity();
			if (!(e instanceof EntityDamageByEntityEvent) && ConfigStore.getPlayerStatus(vic)) {
				Jobs job = ConfigStore.getJob(vic);
				double health = HashMapStore.getHealth(vic);
				health -= HashMapStore.damageAbsorption(vic,
						e.getDamage() * DarkGranadeItem.getMulti(vic) * Stats.Enduration.getStatPercent(vic));
				HashMapStore.setHealth(vic, health);
				confirmHealthPercent(job, vic, health, HashMapStore.getAllAbsorption(vic));
				e.setDamage(0.01d);
				ActionBarRunnable.run(vic);
				for (String key : HashMapStore.getAttackedList(vic)) {
					Skills skill = Skills.valueof(key);
					if (skill != null)
						e.setCancelled(skill.Attacked(vic));
				}
			}
		} else if (e.getEntity() instanceof LivingEntity) {
			LivingEntity vic = (LivingEntity) e.getEntity();
			double damage = e.getDamage() * DarkGranadeItem.getMulti(vic);
			e.setDamage(damage);
		}

	}

	public static void confirmHealthPercent(Jobs job, Player vic, double health, double abp) {
		double max_health = job != null ? job.getMaxHealth(vic) : 20;
		double per_health = health / max_health * 100;
		if (per_health <= 1 && per_health > 0) {
			per_health = 1;
		} else if (per_health >= 99 && per_health < 100) {
			per_health = 99;
		}
		if (per_health > 100) {
			per_health = 100;
		}
		if (per_health <= 0)
			if (health > 0) {
				per_health = 1;
			} else
				per_health = 0;
		double per_abp = abp / max_health * 100;
		if (per_abp <= 1 && per_abp > 0) {
			per_abp = 1;
		}
		if (per_abp <= 0)
			if (abp > 0) {
				per_abp = 1;
			} else
				per_abp = 0;

		final double changed_abp = per_abp;
		final double changed_health = per_health;
		new BukkitRunnable() {
			@Override
			public void run() {
				vic.setAbsorptionAmount(changed_abp);
				vic.setHealth(changed_health);
			}
		}.runTaskLater(Core.getCore(), 1);

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Events(EntityDamageByEntityEvent e) {
		Entity vic_e = e.getEntity();
		Entity att_e = e.getDamager();
		boolean critical = false;
		double damage_multi = 1.0d;
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
				if (att.getAttackCooldown() == 1.0f)
					if (job != null && !e.isCancelled())
						if (vic_e instanceof Player) {
							Player vic = (Player) vic_e;
							if (ConfigStore.getPlayerStatus(vic) && ConfigStore.getPVP(vic))
								HashMapStore.setIdentity(att,
										HashMapStore.getIdentity(att) + job.getWeapon().getIdentity());
						} else
							HashMapStore.setIdentity(att,
									HashMapStore.getIdentity(att) + job.getWeapon().getIdentity());
				critical = Stats.isCritical(att);
				damage_multi *= AdrenalineItem.getPower(att);
//				AttackType type = AttackType.getAttackType(vic_e, att);
//				if (vic_e instanceof LivingEntity)
//					if (type.equals(AttackType.HEAD)) {
//						Effects.spawnNote(att, ((LivingEntity) vic_e).getEyeLocation(), 0);
//					} else if (type.equals(AttackType.BACK)) {
//						Effects.spawnNote(att, ((LivingEntity) vic_e).getEyeLocation(), 10d);
//					} else
//						Effects.spawnNote(att, ((LivingEntity) vic_e).getEyeLocation(), 5d);
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
			if (ConfigStore.getPlayerStatus(att)) {
				for (MetadataValue value : vic_e.getMetadata("key"))
					if (value.getOwningPlugin().equals(Core.getCore())) {
						if (value.asString().equals(ShopStores.Training.name())) {
							double damage = HashMapStore.getMeasureDamage(att);
							if (damage <= 0)
								HashMapStore.setMeasureStartTime(att);
							HashMapStore.setMeasureEndTime(att);
							damage += e.getDamage() * (critical ? 2 : 1) * damage_multi
									* DarkGranadeItem.getMulti((LivingEntity) vic_e);
							HashMapStore.setMeasureDamage(att, damage);
							HashMapStore.setMeasureDamageCount(att, HashMapStore.getMeasureDamageCount(att) + 1);
							if (critical)
								HashMapStore.setMeasureCriticalCount(att,
										HashMapStore.getMeasureCriticalCount(att) + 1);
							vic_e.playEffect(EntityEffect.HURT);
							e.setDamage(0.01);
						}
					}
			} else
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
				double vic_health = HashMapStore.getHealth(vic);
				vic_health -= HashMapStore.damageAbsorption(vic, e.getDamage() * (critical ? 2 : 1) * damage_multi
						* DarkGranadeItem.getMulti(vic) * Stats.Enduration.getStatPercent(vic));
				HashMapStore.setHealth(vic, vic_health);
				confirmHealthPercent(vic_job, vic, vic_health, HashMapStore.getAllAbsorption(vic));
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
				double health = HashMapStore.getHealth(player);
				health += e.getAmount();
				HashMapStore.setHealth(player, health);
				confirmHealthPercent(job, player, health, HashMapStore.getAllAbsorption(player));
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

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Events(EntityDeathEvent e) {
		if (!e.getEntityType().equals(EntityType.PLAYER)) {
			LivingEntity le = e.getEntity();
			List<ItemStack> list = e.getDrops();
			list.clear();
			if (!(le instanceof Ageable) || ((Ageable) le).isAdult())
				for (ItemStack item : Basics.getHunting(e.getEntityType())) {
					ItemStack add = item.clone();
					int amount = item.getDurability() + new Random().nextInt(item.getAmount());
					if (amount > 0) {
						if (le.getFireTicks() > 0) {
							Items i = Items.valueOf(add);
							if (i != null && i instanceof CookingIngredient) {
								CookingIngredient ci = (CookingIngredient) i;
								if (ci.getCookedFood() != null)
									add = ci.getCookedFood().getItemStack();
							}
						}
						add.setAmount(amount);
						add.setDurability((short) 0);
						list.add(add);
					}
				}
			if (le.getType().equals(EntityType.COD) || le.getType().equals(EntityType.SALMON)
					|| le.getType().equals(EntityType.PUFFERFISH) || le.getType().equals(EntityType.TROPICAL_FISH)) {
				List<FishItem> fishes = FishItem.getNormals();
				Material type = FishItem.getMaterial(le.getType());
				Collections.shuffle(fishes);
				for (FishItem fish : fishes)
					if (fish.getType().equals(type)) {
						ItemStack item = fish.getItemStack();
						if (le.getFireTicks() > 0)
							item = FishItem.getCook(item);
						list.add(item);
						break;
					}
			}
			if (le.getKiller() != null && le.getKiller() instanceof Player) {
				if (Basics.isHunting(le.getType())) {
					Player player = le.getKiller();
					if (!ConfigStore.getPlayerStatus(player)) {
						ItemStack main = player.getInventory().getItemInMainHand();
						if (main != null) {
							if (main.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
								int loot = main.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
								for (ItemStack item : list)
									item.setAmount(item.getAmount() + loot);
							}
							EnchantsType tool = EnchantsType.getEnchantType(main.getType());
							if (tool != null) {
								if (tool.equals(EnchantsType.Sword)) {
									int level = Basics.Hunting.getLevel(player);
									if (!(le instanceof Ageable) || ((Ageable) le).isAdult()) {
										for (ItemStack item : list)
											if (Basics.Hunting.isActive(level))
												item.setAmount(
														item.getAmount() + 2 + new Random().nextInt(level / 10 + 2));
										Basics.Hunting.addEXP(player, Basics.getHuntingExp(le.getType()));
									}
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
