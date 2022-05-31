package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponItems extends Items {
	private final static List<WeaponItems> items = new ArrayList<WeaponItems>();
	//
	private final double attackspeed;
	private final double damage;
	private final double identity;

	public WeaponItems(String key, Material type, ChatColor color, double attackspeed, double damage, double identity,
			String Q, String F) {
		super("WEAPON", key, type, color);
		this.attackspeed = attackspeed;
		this.damage = damage;
		this.identity = identity;
		this.lore.add(ChatColor.WHITE + "각성기(Q) - " + Q);
		this.lore.add(ChatColor.WHITE + "아이덴티티(F) - " + F);
		items.add(this);
	}

	@Override
	public ItemStack getItemStack() {
		return getItemStack(0);
	}

	public static double getPercent() {
		return 0.05;
	}

	public double getIdentity() {
		return identity;
	}

	@Override
	public int getMaxCount() {
		return -1;
	}

	@Override
	public ItemStack getItemStack(int count) {
		ItemStack i = super.getItemStack(1);
		ItemMeta meta = i.getItemMeta();
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
				"GENERIC_ATTACK_SPEED", attackspeed, Operation.ADD_NUMBER, EquipmentSlot.HAND));
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),
				"GENERIC_ATTACK_DAMAGE", damage, Operation.ADD_NUMBER, EquipmentSlot.HAND));
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),
				"GENERIC_ATTACK_DAMAGE", count * getPercent(), Operation.ADD_SCALAR, EquipmentSlot.HAND));
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "강화 레벨 : " + count);
		lore.addAll(this.lore);
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}

	public static List<WeaponItems> getWeaponItems() {
		return items;
	}

	public static WeaponItems valueOf(String key) {
		if (key != null)
			for (WeaponItems item : getWeaponItems())
				if (item.getKey().equalsIgnoreCase(key) || item.getKeyName().equalsIgnoreCase(key))
					return item;
		return null;
	}

	public static WeaponItems valueOf(ItemStack i) {
		if (i != null && i.hasItemMeta() && i.getItemMeta().hasLocalizedName())
			return valueOf(i.getItemMeta().getLocalizedName());
		return null;
	}
}
