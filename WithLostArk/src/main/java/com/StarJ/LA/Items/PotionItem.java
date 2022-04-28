package com.StarJ.LA.Items;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionItem extends Items implements Buyable, Consumeable {

	public PotionItem(String key, Material type, ChatColor color) {
		super(key, type, color);
	}

	@Override
	public ItemStack getItemStack(int count) {
		ItemStack i = super.getItemStack(count);
		Random r = new Random();
		PotionMeta meta = (PotionMeta) i.getItemMeta();
		meta.setColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
		Name name = Name.getRandom();
		meta.setDisplayName(ChatColor.DARK_RED + name.getName());
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "효과 : " + name.getName());
		lore.add(ChatColor.GREEN + "레벨 : " + Name.getRandomLevel());
		lore.add(ChatColor.GREEN + "지속시간(초) : " + Name.getRandomDuration());
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public BigInteger getMoney(Player player) {
		return BigInteger.valueOf(500);
	}

	public enum Name {
		absorption("흡수", PotionEffectType.ABSORPTION), bad_omen("불행", PotionEffectType.BAD_OMEN),
		blindess("실명", PotionEffectType.BLINDNESS), conduit_power("전달체의 힘", PotionEffectType.CONDUIT_POWER),
		dolphins_grace("돌고래의 가호", PotionEffectType.DOLPHINS_GRACE),
		fire_resistance("화염 저항", PotionEffectType.FIRE_RESISTANCE), glowing("발광", PotionEffectType.GLOWING),
		haste("성급함", PotionEffectType.FAST_DIGGING), health_boost("생명력 강화", PotionEffectType.HEALTH_BOOST),
		hero_of_the_village("마을의 영웅", PotionEffectType.HERO_OF_THE_VILLAGE), hunger("배고픔", PotionEffectType.HUNGER),
		instant_dmaage("즉시 피해", PotionEffectType.HARM), instant_health("즉시 치유", PotionEffectType.HEAL),
		invisibility("투명", PotionEffectType.INVISIBILITY), jump_boost("점프 강화", PotionEffectType.JUMP),
		levitation("공중 부양", PotionEffectType.LEVITATION), luck("행운", PotionEffectType.LUCK),
		mining_fatigue("채굴 피로", PotionEffectType.SLOW_DIGGING), nausea("멀미", PotionEffectType.CONFUSION),
		night_vision("야간 투시", PotionEffectType.NIGHT_VISION), posion("독", PotionEffectType.POISON),
		regenration("재생", PotionEffectType.REGENERATION), resistance("저항", PotionEffectType.DAMAGE_RESISTANCE),
		saturation("포화", PotionEffectType.SATURATION), slow_falling("느린 낙하", PotionEffectType.SLOW_FALLING),
		slowness("속도 감소", PotionEffectType.SLOW), speed("속도 증가", PotionEffectType.SPEED),
		strength("힘", PotionEffectType.INCREASE_DAMAGE), unluck("불운", PotionEffectType.UNLUCK),
		water_breathing("수중 호흡", PotionEffectType.WATER_BREATHING), weakness("나약함", PotionEffectType.WEAKNESS),
		wither("시듦", PotionEffectType.WITHER),
		//
		;

		private final String name;
		private final PotionEffectType type;

		private Name(String name, PotionEffectType type) {
			this.name = name;
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public PotionEffectType getType() {
			return type;
		}

		public static Name getRandom() {
			Random r = new Random();
			Name[] names = values();
			return names[r.nextInt(names.length)];
		}

		public static int getRandomLevel() {
			return new Random().nextInt(5);
		}

		public static int getRandomDuration() {
			return 15 * (1 + new Random().nextInt(20));
		}

		public static Name formType(PotionEffectType type) {
			for (Name n : values())
				if (n.getType().equals(type))
					return n;
			return null;
		}

		public static Name fromName(String name) {
			for (Name n : values())
				if (n.getName().equalsIgnoreCase(name))
					return n;
			return null;
		}
	}

	@Override
	public ItemStack getInfoItemStack(Player player, ItemStack i) {
		ItemStack item = Buyable.super.getInfoItemStack(player, super.getItemStack(i.getAmount()));
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		lore.add(0, ChatColor.GRAY + "효과 : 랜덤");
		lore.add(1, ChatColor.GRAY + "지속시간 : 랜덤");
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public void Comsume(Player player, ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
			List<String> lores = item.getItemMeta().getLore();
			Name type = null;
			int level = 0;
			int duration = 0;
			for (String lore : lores)
				try {
					if (lore.contains("효과 : ")) {
						String[] sp = lore.split(" : ");
						if (sp.length == 2) {
							type = Name.fromName(sp[1]);
						}
					} else if (lore.contains("레벨 : ")) {
						String[] sp = lore.split(" : ");
						if (sp.length == 2) {
							level = Integer.parseInt(sp[1]);
						}
					} else if (lore.contains("지속시간(초) : ")) {
						String[] sp = lore.split(" : ");
						if (sp.length == 2) {
							duration = Integer.parseInt(sp[1]);
						}
					}
				} catch (IllegalArgumentException ex) {

				}
			if (type != null && duration > 0 && level >= 0)
				player.addPotionEffect(new PotionEffect(type.getType(), duration * 20, level));
		}
	}

	@Override
	public boolean isConsumeCancel() {
		return false;
	}

}
