package com.StarJ.LA.Systems;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Effects {

	public static void spawnRedStone(Location loc, int R, int G, int B, float size, int count, double offsetX,
			double offsetY, double offsetZ) {
		DustOptions option = new DustOptions(Color.fromRGB(R, G, B), size);
		loc.getWorld().spawnParticle(Particle.REDSTONE, loc, count, offsetX, offsetY, offsetZ, option);
	}

	public static void spawnRedStone(Player target, Location loc, int R, int G, int B, float size, int count,
			double offsetX, double offsetY, double offsetZ) {
		DustOptions option = new DustOptions(Color.fromRGB(R, G, B), size);
		target.spawnParticle(Particle.REDSTONE, loc, count, offsetX, offsetY, offsetZ, option);
	}

	public static void spawnSpellMob(Location loc, int R, int G, int B, boolean ambient) {
		if (R > 255)
			R = R % 255;
		if (G > 255)
			G = G % 255;
		if (B > 255)
			B = B % 255;
		loc.getWorld().spawnParticle(ambient ? Particle.SPELL_MOB_AMBIENT : Particle.SPELL_MOB, loc, 0, R / 255D,
				G / 255D, B / 255D, 1);
	}

	public static void spawnSpellMob(Player target, Location loc, int R, int G, int B, boolean ambient) {
		if (R > 255)
			R = R % 255;
		if (G > 255)
			G = G % 255;
		if (B > 255)
			B = B % 255;
		target.spawnParticle(ambient ? Particle.SPELL_MOB_AMBIENT : Particle.SPELL_MOB, loc, 0, R / 255D, G / 255D,
				B / 255D, 1);
	}

	public static void spawnNote(Location loc, double note) {
		if (note > 24)
			note = note % 24;
		loc.getWorld().spawnParticle(Particle.NOTE, loc, 0, note / 24D, 0, 0, 1);
	}

	public static void spawnNote(Player target, Location loc, double note) {
		if (note > 24)
			note = note % 24;
		target.spawnParticle(Particle.NOTE, loc, 0, note / 24D, 0, 0, 1);
	}

	public static void spawnItemCrack(Location loc, int count, Material type) {
		loc.getWorld().spawnParticle(Particle.ITEM_CRACK, loc, count, new ItemStack(type));
	}

	public static void spawnItemCrack(Player target, Location loc, int count, Material type) {
		target.spawnParticle(Particle.ITEM_CRACK, loc, count, new ItemStack(type));
	}

	public static void spawnBlockCrack(Location loc, int count, Material type) {
		if (type.isBlock())
			loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, count, type.createBlockData());
	}

	public static void spawnBlockCrack(Player target, Location loc, int count, Material type) {
		if (type.isBlock())
			target.spawnParticle(Particle.BLOCK_CRACK, loc, count, type.createBlockData());
	}

	public static void spawnBlockDust(Location loc, int count, Material type) {
		if (type.isBlock())
			loc.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, count, type.createBlockData());
	}

	public static void spawnBlockDust(Player target, Location loc, int count, Material type) {
		if (type.isBlock())
			target.spawnParticle(Particle.BLOCK_DUST, loc, count, type.createBlockData());
	}

	public static void spawnFallingDust(Location loc, int count, Material type) {
		if (type.isBlock())
			loc.getWorld().spawnParticle(Particle.FALLING_DUST, loc, count, type.createBlockData());
	}

	public static void spawnFallingDust(Player target, Location loc, int count, Material type) {
		if (type.isBlock())
			target.spawnParticle(Particle.FALLING_DUST, loc, count, type.createBlockData());
	}

	public static void sendActionBar(String msg) {
		for (Player target : Bukkit.getOnlinePlayers())
			target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
	}

	public static void sendActionBar(Player target, String msg) {
		target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
	}

	public static enum Directional {
		EXPLOSION_NORMAL(Particle.EXPLOSION_NORMAL), FIREWORKS_SPARK(Particle.FIREWORKS_SPARK),
		WATER_BUBBLE(Particle.WATER_BUBBLE), WATER_WAKE(Particle.WATER_WAKE), CRIT(Particle.CRIT),
		CRIT_MAGIC(Particle.CRIT_MAGIC), SMOKE_NORMAL(Particle.SMOKE_NORMAL), SMOKE_LARGE(Particle.SMOKE_LARGE),
		PORTAL(Particle.PORTAL), ENCHANTMENT_TABLE(Particle.ENCHANTMENT_TABLE), FLAME(Particle.FLAME),
		CLOUD(Particle.CLOUD), DRAGON_BREATH(Particle.DRAGON_BREATH), END_ROD(Particle.END_ROD),
		DAMAGE_INDICATOR(Particle.DAMAGE_INDICATOR), TOTEM(Particle.TOTEM), SPIT(Particle.SPIT),
		SQUID_INK(Particle.SQUID_INK), BUBBLE_POP(Particle.BUBBLE_POP), BUBBLE_COLUMN_UP(Particle.BUBBLE_COLUMN_UP),
		NAUTILUS(Particle.NAUTILUS), CAMPFIRE_COSY_SMOKE(Particle.CAMPFIRE_COSY_SMOKE),
		CAMPFIRE_SIGNAL_SMOKE(Particle.CAMPFIRE_SIGNAL_SMOKE), SOUL_FIRE_FLAME(Particle.SOUL_FIRE_FLAME),
		SOUL(Particle.SOUL), REVERSE_PORTAL(Particle.REVERSE_PORTAL), SWEEP_ATTACK(Particle.SWEEP_ATTACK)
		//
		;

		final Particle particle;

		private Directional(Particle p) {
			this.particle = p;
		}

		public Particle getParticle() {
			return particle;
		}

		public void spawnDirectional(Location loc, int count, double offsetX, double offsetY, double offsetZ,
				double speed) {
			loc.getWorld().spawnParticle(particle, loc, count, offsetX, offsetY, offsetZ, speed);
		}

		public void spawnDirectional(Player target, Location loc, int count, double offsetX, double offsetY,
				double offsetZ, double speed) {
			target.spawnParticle(particle, loc, count, offsetX, offsetY, offsetZ, speed);
		}

	}

	public static abstract class Infos {
		private final Vector rel;
		protected final OfflinePlayer target;

		public Infos(Vector rel) {
			this(rel, null);
		}

		public Infos(Vector rel, OfflinePlayer target) {
			this.rel = rel;
			this.target = target;
		}

		protected Location getAddedLocation(Location pre) {
			return pre.clone().add(rel);
		}

		public abstract void spawn(Location pre);
	}

	public static class RedStoneInfos extends Infos {
		private final int R;
		private final int G;
		private final int B;
		private final float size;
		private final int count;
		private final double offsetX;
		private final double offsetY;
		private final double offsetZ;

		public RedStoneInfos(Vector rel, int R, int G, int B, float size, int count, double offsetX, double offsetY,
				double offsetZ) {
			this(rel, R, G, B, size, count, offsetX, offsetY, offsetZ, null);
		}

		public RedStoneInfos(Vector rel, int R, int G, int B, float size, int count, double offsetX, double offsetY,
				double offsetZ, OfflinePlayer target) {
			super(rel, target);
			this.R = R;
			this.G = G;
			this.B = B;
			this.size = size;
			this.count = count;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.offsetZ = offsetZ;
		}

		@Override
		public void spawn(Location pre) {
			Location loc = getAddedLocation(pre);
			if (target != null) {
				if (target.isOnline())
					spawnRedStone(target.getPlayer(), loc, R, G, B, size, count, offsetX, offsetY, offsetZ);
			} else
				spawnRedStone(loc, R, G, B, size, count, offsetX, offsetY, offsetZ);
		}
	}

	public static class SpellMobInfos extends Infos {
		private final int R;
		private final int G;
		private final int B;
		private final boolean ambient;

		public SpellMobInfos(Vector rel, int R, int G, int B, boolean ambient) {
			this(rel, R, G, B, ambient, null);
		}

		public SpellMobInfos(Vector rel, int R, int G, int B, boolean ambient, OfflinePlayer target) {
			super(rel, target);
			this.R = R;
			this.G = G;
			this.B = B;
			this.ambient = ambient;
		}

		@Override
		public void spawn(Location pre) {
			Location loc = getAddedLocation(pre);
			if (target != null) {
				if (target.isOnline())
					spawnSpellMob(target.getPlayer(), loc, R, G, B, ambient);
			} else
				spawnSpellMob(loc, R, G, B, ambient);
		}

	}

	public static class NoteInfos extends Infos {
		private final double note;

		public NoteInfos(Vector rel, double note) {
			this(rel, note, null);
		}

		public NoteInfos(Vector rel, double note, OfflinePlayer target) {
			super(rel, target);
			this.note = note;
		}

		@Override
		public void spawn(Location pre) {
			Location loc = getAddedLocation(pre);
			if (target != null) {
				if (target.isOnline())
					spawnNote(target.getPlayer(), loc, note);
			} else
				spawnNote(loc, note);
		}
	}

	public static class ItemCrackInfos extends Infos {
		private final int count;
		private final Material type;

		public ItemCrackInfos(Vector rel, int count, Material type) {
			this(rel, count, type, null);
		}

		public ItemCrackInfos(Vector rel, int count, Material type, OfflinePlayer target) {
			super(rel, target);
			this.count = count;
			this.type = type;
		}

		@Override
		public void spawn(Location pre) {
			Location loc = getAddedLocation(pre);
			if (target != null) {
				if (target.isOnline())
					spawnItemCrack(target.getPlayer(), loc, count, type);
			} else
				spawnItemCrack(loc, count, type);
		}
	}

	public static class BlockCrackInfos extends Infos {
		private final int count;
		private final Material type;

		public BlockCrackInfos(Vector rel, int count, Material type) {
			this(rel, count, type, null);
		}

		public BlockCrackInfos(Vector rel, int count, Material type, OfflinePlayer target) {
			super(rel, target);
			this.count = count;
			this.type = type;
		}

		@Override
		public void spawn(Location pre) {
			Location loc = getAddedLocation(pre);
			if (target != null) {
				if (target.isOnline())
					spawnBlockCrack(target.getPlayer(), loc, count, type);
			} else
				spawnBlockCrack(loc, count, type);
		}
	}

	public static class BlockDustInfos extends Infos {
		private final int count;
		private final Material type;

		public BlockDustInfos(Vector rel, int count, Material type) {
			this(rel, count, type, null);
		}

		public BlockDustInfos(Vector rel, int count, Material type, OfflinePlayer target) {
			super(rel, target);
			this.count = count;
			this.type = type;
		}

		@Override
		public void spawn(Location pre) {
			Location loc = getAddedLocation(pre);
			if (target != null) {
				if (target.isOnline())
					spawnBlockDust(target.getPlayer(), loc, count, type);
			} else
				spawnBlockDust(loc, count, type);
		}
	}

	public static class FallingDustInfos extends Infos {
		private final int count;
		private final Material type;

		public FallingDustInfos(Vector rel, int count, Material type) {
			this(rel, count, type, null);
		}

		public FallingDustInfos(Vector rel, int count, Material type, OfflinePlayer target) {
			super(rel, target);
			this.count = count;
			this.type = type;
		}

		@Override
		public void spawn(Location pre) {
			Location loc = getAddedLocation(pre);
			if (target != null) {
				if (target.isOnline())
					spawnFallingDust(target.getPlayer(), loc, count, type);
			} else
				spawnFallingDust(loc, count, type);
		}
	}

	public static class DirectionalInfos extends Infos {
		private final Directional type;
		private final int count;
		private final double offsetX;
		private final double offsetY;
		private final double offsetZ;
		private final double speed;

		public DirectionalInfos(Vector rel, Directional type, int count, double offsetX, double offsetY, double offsetZ,
				double speed) {
			this(rel, type, count, offsetX, offsetY, offsetZ, speed, null);
		}

		public DirectionalInfos(Vector rel, Directional type, int count, double offsetX, double offsetY, double offsetZ,
				double speed, OfflinePlayer target) {
			super(rel, target);
			this.type = type;
			this.count = count;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.offsetZ = offsetZ;
			this.speed = speed;
		}

		@Override
		public void spawn(Location pre) {
			Location loc = getAddedLocation(pre);
			if (target != null) {
				if (target.isOnline())
					type.spawnDirectional(target.getPlayer(), loc, count, offsetX, offsetY, offsetZ, speed);
			} else
				type.spawnDirectional(loc, count, offsetX, offsetY, offsetZ, speed);
		}
	}

	public static Vector getRel(Vector dir, double front, double height, double right) {
		double x = dir.getX() * front - dir.getZ() * right;
		double y = height;
		double z = dir.getZ() * front + dir.getX() * right;
		return new Vector(x, y, z);
	}
}
