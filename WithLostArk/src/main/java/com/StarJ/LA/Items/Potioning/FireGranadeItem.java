package com.StarJ.LA.Items.Potioning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Items.Items;
import com.StarJ.LA.Items.PotionItems;
import com.StarJ.LA.Systems.ConfigStore;
import com.StarJ.LA.Systems.Effects;
import com.StarJ.LA.Systems.HashMapStore;
import com.mojang.math.Vector3fa;

import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPosition;

public class FireGranadeItem extends PotionItems {
	public FireGranadeItem(String key, ChatColor color, double power) {
		super(key, Material.SLIME_BALL, color, "초당피해량 : ", "", power);
		lore.add(ChatColor.WHITE + "장판 지속 시간 : " + getDuration() / 20);
	}

	public int getDuration() {
		return 10 * 20;
	}

	@Override
	public boolean Use(Player player, ItemStack item) {
		Items i = Items.valueOf(item);
		if (i != null && i instanceof FireGranadeItem)
			if (!player.hasCooldown(this.type)) {
				if (!player.getGameMode().equals(GameMode.CREATIVE))
					player.setCooldown(this.type, getCooldown());
				double power = getValue(item);
				player.closeInventory();
				player.playSound(player, Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 2f, 1f);
				Effects.Directional.CRIMSON_SPORE.spawnDirectional(player, player.getEyeLocation(), 10, 0.1, 0.1, 0.1,
						0);
				Location loc = player.getEyeLocation();
				WorldServer server = ((CraftWorld) loc.getWorld()).getHandle();
				ThrownFireGranade et = new ThrownFireGranade(EntityTypes.aP, server, power, player);
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

	public class ThrownFireGranade extends EntityPotion {
		private final double power;
		private final OfflinePlayer off;

		public ThrownFireGranade(EntityTypes<? extends EntityPotion> entitytypes, World world, double power,
				OfflinePlayer off) {
			super(entitytypes, world);
			this.power = power;
			this.off = off;
		}

		protected void a(MovingObjectPosition movingobjectposition) {
			if (off.isOnline()) {
				Player att = off.getPlayer();
				FireAreaEffectCloud eaec = new FireAreaEffectCloud(this.s, dc(), de(), di(), power, att);
				eaec.a(9.0F);
				eaec.b(-1.0F);
				eaec.b((int) (getDuration()));
				eaec.c(5);
				eaec.d(5);
				eaec.av = 5;
				eaec.c(-eaec.h() / eaec.n());
				eaec.a(new ParticleParamRedstone(new Vector3fa(255f, 0f, 0f), 1));
				this.s.b((Entity) eaec);
			}
			super.a(movingobjectposition);
		}
	}

	public class FireAreaEffectCloud extends EntityAreaEffectCloud {
		private final double power;
		private final OfflinePlayer off;

		public FireAreaEffectCloud(World world, double d0, double d1, double d2, double power, OfflinePlayer off) {
			super(world, d0, d1, d2);
			this.power = power;
			this.off = off;
		}

		@Override
		public void k() {
			this.az = 0;
			super.k();
			if (off.isOnline()) {
				if (this.S % 20 == 0) {
					Player att = off.getPlayer();
					if (ConfigStore.getPlayerStatus(att)) {
						List<EntityLiving> list1 = this.s.a(EntityLiving.class, cw());
						float f = h();
						if (!list1.isEmpty()) {
							Iterator<EntityLiving> iterator1 = list1.iterator();
							List<LivingEntity> list = new ArrayList<LivingEntity>();
							while (iterator1.hasNext()) {
								EntityLiving entityliving = iterator1.next();
								if (entityliving.eX()) {
									double d6 = entityliving.dc() - dc();
									double d7 = entityliving.di() - di();
									double d8 = d6 * d6 + d7 * d7;
									if (d8 <= (f * f))
										list.add((LivingEntity) entityliving.getBukkitEntity());
								}
							}
							double identity = HashMapStore.getIdentity(att);
							for (LivingEntity le : list)
								if (!le.getUniqueId().equals(att.getUniqueId())) {
									int ndt = le.getNoDamageTicks();
									le.setNoDamageTicks(0);
									le.damage(power * 0.5, att);
									le.setNoDamageTicks(ndt);
								}
							HashMapStore.setIdentity(att, identity);
						}
					}
				}
			} else
				ah();
		}
	}

}
