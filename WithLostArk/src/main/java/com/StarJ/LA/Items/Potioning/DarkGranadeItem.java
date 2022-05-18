package com.StarJ.LA.Items.Potioning;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.StarJ.LA.Core;
import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.PotionItems;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;

public class DarkGranadeItem extends PotionItems {
	private static HashMap<UUID, BukkitTask> tasks = new HashMap<UUID, BukkitTask>();

	public DarkGranadeItem(String key, ChatColor color, double duration) {
		super(key, Material.SLIME_BALL, color, "지속시간 : ", "", duration);
		lore.add(ChatColor.WHITE + "방어 감소율 : " + getPower() * 100 + "%");
	}

	public static double getPower() {
		return 0.2d;
	}

	public static double getMulti(LivingEntity le) {
		UUID uuid = le.getUniqueId();
		return tasks.containsKey(uuid) ? !tasks.get(uuid).isCancelled() ? 1 + getPower() : 1.0d : 1.0;
	}

	public static void End(LivingEntity le) {
		UUID uuid = le.getUniqueId();
		if (tasks.containsKey(uuid)) {
			BukkitTask task = tasks.get(uuid);
			if (!task.isCancelled()) {
				task.cancel();
				tasks.remove(uuid);
			}
		}
	}

	@Override
	public boolean Use(Player player, ItemStack item) {
		Items i = Items.valueOf(item);
		if (i != null && i instanceof DarkGranadeItem)
			if (!player.hasCooldown(this.type)) {
				if (!player.getGameMode().equals(GameMode.CREATIVE))
					player.setCooldown(this.type, getCooldown());
				int duration = (int) getValue(item);
				player.closeInventory();
				player.playSound(player, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 2f, 1f);
				Effects.Directional.CRIMSON_SPORE.spawnDirectional(player, player.getEyeLocation(), 10, 0.1, 0.1, 0.1,
						0);
				Location loc = player.getEyeLocation();
				WorldServer server = ((CraftWorld) loc.getWorld()).getHandle();
				ThrownDarkGranade et = new ThrownDarkGranade(EntityTypes.aP, server, duration, player);
				et.a(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				server.addFreshEntity(et, SpawnReason.CUSTOM);
				et.getBukkitEntity().setVelocity(loc.getDirection().multiply(0.5));
				return true;
			}
		return false;
	}

//	@SuppressWarnings("unchecked")
//	public static void RegisterEntity() {
//		String name = "thrown_tornado_granade".toLowerCase(); // 이름 아이디
//		EntityTypes.b<EntityPotion> b = EntityPotion::new;
//		MinecraftKey minecraftKey = MinecraftKey.a(name);
//		Map<Object, Type<?>> typeMap = (Map<Object, Type<?>>) DataConverterRegistry.a()
//				.getSchema(DataFixUtils.makeKey(SharedConstants.b().b().c())).findChoiceType(DataConverterTypes.q)
//				.types();
//		typeMap.put(minecraftKey.toString(),
//				typeMap.get("minecraft:" + MinecraftKey.a(EntityTypes.aP.j().a()).a().split("/")[1]));
//		EntityTypes.Builder<net.minecraft.world.entity.Entity> entity = EntityTypes.Builder.a(b, EnumCreatureType.b);
//		// a : monster b : creature
//		// c : ambinet d : axolotls
//		// e : undergound_water_creature
//		// f : water_creature | g : water_abient
//		// h : misc
//		// https://github.com/iSach/UltraCosmetics/blob/7f8bbfd2a540559888b89dae7eee4dec482ab7c9/v1_18_R2/src/main/java/be/isach/ultracosmetics/v1_18_R2/customentities/CustomEntities.java#L75-L104
//		Class<MappedRegistry> registryClass = MappedRegistry.class;
//		try {
//			Field intrusiveHolderCache = registryClass.getDeclaredField(ObfuscatedFields.INTRUSIVE_HOLDER_CACHE);
//			intrusiveHolderCache.setAccessible(true);
//			intrusiveHolderCache.set(Registry.ENTITY_TYPE,
//					new IdentityHashMap<EntityType<?>, Holder.Reference<EntityType<?>>>());
//			Field frozen = registryClass.getDeclaredField(ObfuscatedFields.FROZEN);
//			frozen.setAccessible(true);
//			frozen.set(Registry.ENTITY_TYPE, false);
//		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//			e.printStackTrace();
//			return;
//		}
//		IRegistry.a(IRegistry.W, name, entity.a(name));
//	}

	public class ThrownDarkGranade extends EntityPotion {
		private final int duration;
		private final OfflinePlayer off;

		public ThrownDarkGranade(EntityTypes<? extends EntityPotion> entitytypes, World world, int duration,
				OfflinePlayer off) {
			super(entitytypes, world);
			this.duration = duration;
			this.off = off;
		}

		protected void a(MovingObjectPosition movingobjectposition) {
			AxisAlignedBB axisalignedbb = cw().c(1.5D, 2.0D, 1.5D);
			List<EntityLiving> list1 = this.s.a(EntityLiving.class, axisalignedbb);
			if (off.isOnline())
				if (!list1.isEmpty()) {
					Player att = off.getPlayer();
					Iterator<EntityLiving> iterator = list1.iterator();
					while (iterator.hasNext()) {
						EntityLiving entityliving = iterator.next();
						if (entityliving.eX()) {
							Entity et = entityliving.getBukkitEntity();
							if (et instanceof Player) {
								Player vic = (Player) et;
								if (!vic.getUniqueId().equals(att.getUniqueId()) && ConfigStore.getPlayerStatus(att)
										&& ConfigStore.getPVP(att) && ConfigStore.getPVP(vic)) {
									End(vic);
									tasks.put(vic.getUniqueId(),
											new DarkRunnable(att, vic, duration).runTaskTimer(Core.getCore(), 0, 20));
								}
							} else if (et instanceof LivingEntity) {
								LivingEntity vic = (LivingEntity) et;
								End(vic);
								tasks.put(vic.getUniqueId(),
										new DarkRunnable(att, vic, duration).runTaskTimer(Core.getCore(), 0, 20));
							}
						}
					}
				}
			super.a(movingobjectposition);
		}
	}

	private class DarkRunnable extends BukkitRunnable {
		private final OfflinePlayer off_att;
		private final LivingEntity vic;
		private int age;

		public DarkRunnable(Player att, LivingEntity vic, int duration) {
			this.off_att = att;
			this.vic = vic;
			this.age = duration;
		}

		@Override
		public void run() {
			if (off_att.isOnline() && this.age > 0) {
				Player att = off_att.getPlayer();
				if (ConfigStore.getPlayerStatus(att) && vic instanceof OfflinePlayer) {
					OfflinePlayer off_vic = (OfflinePlayer) vic;
					if (!off_vic.isOnline() && ConfigStore.getPlayerStatus(off_vic.getPlayer())) {
						this.cancel();
						End(vic);
						return;
					}
				} else {
					if (vic.isDead()) {
						this.cancel();
						End(vic);
						return;
					}
				}
				Effects.spawnRedStone(vic.getEyeLocation(), 255, 0, 255, 2, 10, 0.1, 0.1, 0.1);
				this.age--;
			} else {
				this.cancel();
				End(vic);
				return;
			}
		}

	}
}
