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

public class ArmorItems extends Items {
	private static final List<ArmorItems> list = new ArrayList<ArmorItems>();
	private final double percent;
	private final EquipmentSlot slot;

	public ArmorItems(String key, Material type, ChatColor color, double percent, EquipmentSlot slot) {
		super("ARMORS", key, type, color);
		this.percent = percent;
		this.slot = slot;
		list.add(this);
	}

	public double getPercent() {
		return percent;
	}

	@Override
	public ItemStack getItemStack() {
		return super.getItemStack(0);
	}

	@Override
	public int getMaxCount() {
		return -1;
	}

	@Override
	public ItemStack getItemStack(int count) {
		ItemStack item = super.getItemStack(1);
		ItemMeta meta = item.getItemMeta();
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(UUID.randomUUID(),
				"GENERIC_MAX_HEALTH", getPercent() * count, Operation.ADD_SCALAR, slot));
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "강화 레벨 : " + count);
		lore.add(ChatColor.WHITE + "클릭시 보석창으로 이동");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static List<ArmorItems> getArmorItems() {
		return list;
	}
}
