package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.StarJ.LA.Items.JewerlyItems.Rank;
import com.StarJ.LA.Systems.Stats;

public abstract class Items {
	private final static List<Items> items = new ArrayList<Items>();
	//
	public final static MoneyItem money = new MoneyItem("수표", Material.PAPER, ChatColor.GREEN);
	public final static ExpItem exp = new ExpItem("경험치_도박", Material.EXPERIENCE_BOTTLE, ChatColor.DARK_PURPLE);
	public final static EnchantItem ench = new EnchantItem("인챈트_책", Material.ENCHANTED_BOOK, ChatColor.DARK_PURPLE);
	public final static PotionItem potion = new PotionItem("랜덤_포션", Material.POTION, ChatColor.AQUA);
	public final static ToolItem tool = new ToolItem("랜덤_도구", Material.GOLDEN_PICKAXE, ChatColor.AQUA);
	public final static EquipItem equip = new EquipItem("랜덤_전투", Material.NETHERITE_SWORD, ChatColor.AQUA);
	public final static ExpendablesItem expendables = new ExpendablesItem("랜덤_소모품", Material.ARROW, ChatColor.AQUA);
	public final static AnimalItem animal = new AnimalItem("랜덤_동물_스폰알", Material.PIG_SPAWN_EGG, ChatColor.AQUA);
	public final static FishingrodItem rod = new FishingrodItem("일반_낚시대", Material.FISHING_ROD, ChatColor.GREEN);
	public final static FlyItem fly = new FlyItem("날기용_폭죽", Material.FIREWORK_ROCKET, ChatColor.GREEN);
	public final static InventoryItem inv = new InventoryItem("인벤토리_확장", Material.PINK_SHULKER_BOX,
			ChatColor.LIGHT_PURPLE);
	// ARMORS
	public final static ArmorItems helmet = new ArmorItems("투구", Material.IRON_HELMET, ChatColor.AQUA, 0.03,
			EquipmentSlot.HEAD);
	public final static ArmorItems chestplate = new ArmorItems("흉갑", Material.IRON_CHESTPLATE, ChatColor.AQUA, 0.08,
			EquipmentSlot.CHEST);
	public final static ArmorItems leggings = new ArmorItems("레깅스", Material.IRON_LEGGINGS, ChatColor.AQUA, 0.06,
			EquipmentSlot.LEGS);
	public final static ArmorItems boots = new ArmorItems("바지", Material.IRON_BOOTS, ChatColor.AQUA, 0.03,
			EquipmentSlot.FEET);
	// ZERO Jewerly
	public final static JewerlyItems zero = new JewerlyItems("빈_보석", Material.WHITE_DYE, ChatColor.WHITE, null,
			Rank.One);
	// ONE Jewerly
	public final static JewerlyItems speed_one = new JewerlyItems("신속의_보석", Material.LIGHT_BLUE_DYE, ChatColor.AQUA,
			Stats.Speed, Rank.One);
	public final static JewerlyItems spec_one = new JewerlyItems("특화의_보석", Material.RED_DYE, ChatColor.RED,
			Stats.Specialization, Rank.One);
	public final static JewerlyItems cri_one = new JewerlyItems("치명의_보석", Material.YELLOW_DYE, ChatColor.YELLOW,
			Stats.Critical, Rank.One);
	public final static JewerlyItems endu_one = new JewerlyItems("인내의_보석", Material.ORANGE_DYE, ChatColor.GOLD,
			Stats.Enduration, Rank.One);
	// TWO Jewerly
	public final static JewerlyItems speed_two = new JewerlyItems("위대한_신속의_보석", Material.LIGHT_BLUE_DYE, ChatColor.AQUA,
			Stats.Speed, Rank.Two);
	public final static JewerlyItems spec_two = new JewerlyItems("위대한_특화의_보석", Material.RED_DYE, ChatColor.RED,
			Stats.Specialization, Rank.Two);
	public final static JewerlyItems cri_two = new JewerlyItems("위대한_치명의_보석", Material.YELLOW_DYE, ChatColor.YELLOW,
			Stats.Critical, Rank.Two);
	public final static JewerlyItems endu_two = new JewerlyItems("위대한_인내의_보석", Material.ORANGE_DYE, ChatColor.GOLD,
			Stats.Enduration, Rank.Two);
	// THREE Jewerly
	public final static JewerlyItems speed_three = new JewerlyItems("경이로운_신속의_보석", Material.LIGHT_BLUE_DYE,
			ChatColor.AQUA, Stats.Speed, Rank.Three);
	public final static JewerlyItems spec_three = new JewerlyItems("경이로운_특화의_보석", Material.RED_DYE, ChatColor.RED,
			Stats.Specialization, Rank.Three);
	public final static JewerlyItems cri_three = new JewerlyItems("경이로운_치명의_보석", Material.YELLOW_DYE, ChatColor.YELLOW,
			Stats.Critical, Rank.Three);
	public final static JewerlyItems endu_three = new JewerlyItems("경이로운_인내의_보석", Material.ORANGE_DYE, ChatColor.GOLD,
			Stats.Enduration, Rank.Three);
	// WEAPON
	public final static WeaponItems reaper = new WeaponItems("단검", Material.IRON_SWORD, ChatColor.DARK_RED, 2.5, 3, 10,
			"월식: 카덴차", "페르소나");
	//
	protected final String loc;
	protected final String key;
	protected final Material type;
	protected final ItemStack item;
	protected final ItemMeta meta;
	protected final List<String> lore = new ArrayList<String>();

	public Items(String key, Material type, ChatColor color) {
		this("OSH", key, type, color);
	}

	public Items(String loc, String key, Material type, ChatColor color) {
		this.loc = loc;
		this.key = key;
		this.type = type;
		this.item = new ItemStack(type);
		this.meta = item.getItemMeta();
		meta.setLocalizedName(key);
		meta.setDisplayName(color + key.replaceAll("_", " "));
		items.add(this);
	}

	public int getMaxCount() {
		return type.getMaxStackSize();
	}

	public String getKey() {
		return key;
	}

	public String getKeyName() {
		return loc + ":" + key;
	}

	public ItemStack getItemStack() {
		return getItemStack(1);
	}

	public ItemStack getItemStack(int count) {
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.setAmount(count);
		return item.clone();
	}

	public static List<Items> values() {
		return items;
	}

	public static Items valueOf(String key) {
		if (key != null)
			for (Items item : values())
				if (item.getKey().equalsIgnoreCase(key) || item.getKeyName().equalsIgnoreCase(key))
					return item;
		return null;
	}

	public static Items valueOf(ItemStack i) {
		if (i != null && i.hasItemMeta() && i.getItemMeta().hasLocalizedName())
			return valueOf(i.getItemMeta().getLocalizedName());
		return null;
	}
}
