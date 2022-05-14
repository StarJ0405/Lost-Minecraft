package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Torch;

import com.StarJ.LA.Items.CookingIngredient.IngredientType;
import com.StarJ.LA.Items.JewerlyItems.Rank;
import com.StarJ.LA.Items.Potioning.AdrenalineItem;
import com.StarJ.LA.Items.Potioning.AwakeningItem;
import com.StarJ.LA.Items.Potioning.TornadoGranadeItem;
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
	public final static FlyFireworkItem fly = new FlyFireworkItem("날기용_폭죽", Material.FIREWORK_ROCKET, ChatColor.GREEN);
	public final static InventoryItem inv = new InventoryItem("인벤토리_확장", Material.PINK_SHULKER_BOX,
			ChatColor.LIGHT_PURPLE);
	public final static FishBoxItem fishbox = new FishBoxItem("어항", ChatColor.AQUA);
	// ARMORS
	public final static ArmorItems helmet = new ArmorItems("투구", Material.IRON_HELMET, ChatColor.AQUA, 0.03,
			EquipmentSlot.HEAD);
	public final static ArmorItems chestplate = new ArmorItems("흉갑", Material.IRON_CHESTPLATE, ChatColor.AQUA, 0.08,
			EquipmentSlot.CHEST);
	public final static ArmorItems leggings = new ArmorItems("레깅스", Material.IRON_LEGGINGS, ChatColor.AQUA, 0.06,
			EquipmentSlot.LEGS);
	public final static ArmorItems boots = new ArmorItems("바지", Material.IRON_BOOTS, ChatColor.AQUA, 0.03,
			EquipmentSlot.FEET);
	// Jewerly
	public final static JewerlyItems jewerly_zero = new JewerlyItems("빈_보석", Material.WHITE_DYE, ChatColor.WHITE, null,
			Rank.One);

	public final static JewerlyItems speed_one = new JewerlyItems("신속의_보석", Material.LIGHT_BLUE_DYE, ChatColor.AQUA,
			Stats.Speed, Rank.One);
	public final static JewerlyItems spec_one = new JewerlyItems("특화의_보석", Material.RED_DYE, ChatColor.RED,
			Stats.Specialization, Rank.One);
	public final static JewerlyItems cri_one = new JewerlyItems("치명의_보석", Material.YELLOW_DYE, ChatColor.YELLOW,
			Stats.Critical, Rank.One);
	public final static JewerlyItems endu_one = new JewerlyItems("인내의_보석", Material.ORANGE_DYE, ChatColor.GOLD,
			Stats.Enduration, Rank.One);

	public final static JewerlyItems speed_two = new JewerlyItems("위대한_신속의_보석", Material.LIGHT_BLUE_DYE, ChatColor.AQUA,
			Stats.Speed, Rank.Two);
	public final static JewerlyItems spec_two = new JewerlyItems("위대한_특화의_보석", Material.RED_DYE, ChatColor.RED,
			Stats.Specialization, Rank.Two);
	public final static JewerlyItems cri_two = new JewerlyItems("위대한_치명의_보석", Material.YELLOW_DYE, ChatColor.YELLOW,
			Stats.Critical, Rank.Two);
	public final static JewerlyItems endu_two = new JewerlyItems("위대한_인내의_보석", Material.ORANGE_DYE, ChatColor.GOLD,
			Stats.Enduration, Rank.Two);

	public final static JewerlyItems speed_three = new JewerlyItems("경이로운_신속의_보석", Material.LIGHT_BLUE_DYE,
			ChatColor.AQUA, Stats.Speed, Rank.Three);
	public final static JewerlyItems spec_three = new JewerlyItems("경이로운_특화의_보석", Material.RED_DYE, ChatColor.RED,
			Stats.Specialization, Rank.Three);
	public final static JewerlyItems cri_three = new JewerlyItems("경이로운_치명의_보석", Material.YELLOW_DYE, ChatColor.YELLOW,
			Stats.Critical, Rank.Three);
	public final static JewerlyItems endu_three = new JewerlyItems("경이로운_인내의_보석", Material.ORANGE_DYE, ChatColor.GOLD,
			Stats.Enduration, Rank.Three);
	// COOKING
	public final static CookingItem cooking = new CookingItem("요리", ChatColor.LIGHT_PURPLE);

	public final static CookingIngredient beetroot = new CookingIngredient("비트", Material.BEETROOT, ChatColor.WHITE, 1,
			1 + 0, (short) 1, IngredientType.Farming);
	public final static CookingIngredient melon_slice = new CookingIngredient("수박_조각", Material.MELON_SLICE,
			ChatColor.WHITE, 1, 1 + 4, (short) 3, IngredientType.Farming);
	public final static CookingIngredient dried_kelp = new CookingIngredient("말린_켈프", Material.DRIED_KELP,
			ChatColor.WHITE, 1, IngredientType.Farming);
	public final static CookingIngredient sweet_berries = new CookingIngredient("달콤한_열매", Material.SWEET_BERRIES,
			ChatColor.WHITE, 1, 1 + 1, (short) 2, IngredientType.Farming);
	public final static CookingIngredient carrot = new CookingIngredient("당근", Material.CARROT, ChatColor.WHITE, 1,
			1 + 3, (short) 2, IngredientType.Farming);
	public final static CookingIngredient potato = new CookingIngredient("감자", Material.POTATO, ChatColor.WHITE, 1,
			1 + 4, (short) 1, IngredientType.Farming);

	public final static CookingIngredient baked_potato = new CookingIngredient("구운_감자", Material.BAKED_POTATO,
			ChatColor.WHITE, 2, IngredientType.Farming);
	public final static CookingIngredient cookie = new CookingIngredient("쿠키", Material.COOKIE, ChatColor.WHITE, 2,
			IngredientType.Farming);
	public final static CookingIngredient glow_berries = new CookingIngredient("발광_열매", Material.GLOW_BERRIES,
			ChatColor.WHITE, 2, IngredientType.Farming);

	public final static CookingIngredient poisonous_potato = new CookingIngredient("독이_있는_감자",
			Material.POISONOUS_POTATO, ChatColor.WHITE, 3, 1 + 1, IngredientType.Farming);
	public final static CookingIngredient apple = new CookingIngredient("사과", Material.APPLE, ChatColor.WHITE, 3, 1 + 1,
			IngredientType.Farming);
	public final static CookingIngredient bread = new CookingIngredient("빵", Material.BREAD, ChatColor.WHITE, 3,
			IngredientType.Farming);
	public final static CookingIngredient golden_apple = new CookingIngredient("황금_사과", Material.GOLDEN_APPLE,
			ChatColor.WHITE, 3, IngredientType.Farming);

	public final static CookingIngredient honey_bottle = new CookingIngredient("꿀이_든_병", Material.HONEY_BOTTLE,
			ChatColor.WHITE, 4, IngredientType.Farming);
	public final static CookingIngredient pumpkin_pie = new CookingIngredient("호박_파이", Material.PUMPKIN_PIE,
			ChatColor.WHITE, 5, IngredientType.Farming);
	public final static CookingIngredient beetroot_soup = new CookingIngredient("비트_수프", Material.BEETROOT_SOUP,
			ChatColor.WHITE, 24, IngredientType.Farming);
	public final static CookingIngredient mushroom_stew = new CookingIngredient("버섯_수프", Material.MUSHROOM_STEW,
			ChatColor.WHITE, 48, IngredientType.Farming);
	public final static CookingIngredient cake = new CookingIngredient("케이크", Material.CAKE, ChatColor.WHITE, 192,
			IngredientType.Farming);
	public final static CookingIngredient enchanted_golden_apple = new CookingIngredient("황금_사과",
			Material.ENCHANTED_GOLDEN_APPLE, ChatColor.WHITE, 300, IngredientType.Farming);

	public final static CookingIngredient cooked_axolotl_meat = new CookingIngredient("익힌_아홀로틀_고기",
			Material.COOKED_PORKCHOP, ChatColor.WHITE, 6, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient axolotl_meat = new CookingIngredient("아홀로틀_고기", Material.PORKCHOP,
			ChatColor.WHITE, 4, 1 + 0, (short) 1, IngredientType.Hunting, cooked_axolotl_meat);
	public final static CookingIngredient cooked_bat_meat = new CookingIngredient("익힌_박쥐_고기", Material.COOKED_MUTTON,
			ChatColor.WHITE, 4.5, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient bat_meat = new CookingIngredient("박쥐_고기", Material.MUTTON, ChatColor.WHITE, 3,
			1 + 0, (short) 1, IngredientType.Hunting, cooked_bat_meat);
	public final static CookingIngredient cooked_cat_meat = new CookingIngredient("익힌_고양이_고기", Material.COOKED_MUTTON,
			ChatColor.WHITE, 3, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cat_meat = new CookingIngredient("고양이_고기", Material.MUTTON, ChatColor.WHITE,
			2, 1 + 0, (short) 1, IngredientType.Hunting, cooked_cat_meat);
	public final static CookingIngredient cooked_chicken_meat = new CookingIngredient("익힌_닭_고기",
			Material.COOKED_CHICKEN, ChatColor.WHITE, 6, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient chicken_meat = new CookingIngredient("닭_고기", Material.CHICKEN,
			ChatColor.WHITE, 4, 1 + 0, (short) 1, IngredientType.Hunting, cooked_chicken_meat);
	public final static CookingIngredient cooked_cow_meat = new CookingIngredient("익힌_소_고기", Material.COOKED_BEEF,
			ChatColor.WHITE, 6, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cow_meat = new CookingIngredient("소_고기", Material.BEEF, ChatColor.WHITE, 4,
			1 + 0, (short) 1, IngredientType.Hunting, cooked_cow_meat);
	public final static CookingIngredient cooked_donkey_meat = new CookingIngredient("익힌_당나귀_고기", Material.COOKED_BEEF,
			ChatColor.WHITE, 9, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient donkey_meat = new CookingIngredient("당나귀_고기", Material.BEEF, ChatColor.WHITE,
			6, 1 + 0, (short) 1, IngredientType.Hunting, cooked_donkey_meat);
	public final static CookingIngredient cooked_fox_meat = new CookingIngredient("익힌_여우_고기", Material.COOKED_MUTTON,
			ChatColor.WHITE, 9, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient fox_meat = new CookingIngredient("여우_고기", Material.MUTTON, ChatColor.WHITE, 6,
			1 + 0, (short) 1, IngredientType.Hunting, cooked_fox_meat);
	public final static CookingIngredient cooked_glow_squid_meat = new CookingIngredient("익힌_발광_오징어_고기",
			Material.COOKED_MUTTON, ChatColor.WHITE, 15, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient glow_squid_meat = new CookingIngredient("발광_오징어_고기", Material.MUTTON,
			ChatColor.WHITE, 10, 1 + 0, (short) 1, IngredientType.Hunting, cooked_glow_squid_meat);
	public final static CookingIngredient cooked_horse_meat = new CookingIngredient("익힌_말_고기", Material.COOKED_BEEF,
			ChatColor.WHITE, 9, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient horse_meat = new CookingIngredient("말_고기", Material.BEEF, ChatColor.WHITE, 6,
			1 + 0, (short) 1, IngredientType.Hunting, cooked_horse_meat);
	public final static CookingIngredient cooked_mushrom_meat = new CookingIngredient("익힌_무시룸_고기", Material.COOKED_BEEF,
			ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient mushrom_meat = new CookingIngredient("무시룸_고기", Material.BEEF, ChatColor.WHITE,
			20, 1 + 0, (short) 1, IngredientType.Hunting, cooked_mushrom_meat);
	public final static CookingIngredient cooked_mule_meat = new CookingIngredient("익힌_노새_고기", Material.COOKED_BEEF,
			ChatColor.WHITE, 18, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient mule_meat = new CookingIngredient("노새_고기", Material.BEEF, ChatColor.WHITE, 12,
			1 + 0, (short) 1, IngredientType.Hunting, cooked_mule_meat);
	public final static CookingIngredient cooked_ocelot_meat = new CookingIngredient("익힌_오실롯_고기",
			Material.COOKED_MUTTON, ChatColor.WHITE, 4, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient ocelot_meat = new CookingIngredient("오실롯_고기", Material.MUTTON,
			ChatColor.WHITE, 6, 1 + 0, (short) 1, IngredientType.Hunting, cooked_ocelot_meat);
	public final static CookingIngredient cooked_parrot_meat = new CookingIngredient("익힌_앵무새_고기",
			Material.COOKED_CHICKEN, ChatColor.WHITE, 9, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient parrot_meat = new CookingIngredient("앵무새_고기", Material.CHICKEN,
			ChatColor.WHITE, 6, 1 + 0, (short) 1, IngredientType.Hunting, cooked_parrot_meat);
	public final static CookingIngredient cooked_pig_meat = new CookingIngredient("익힌_돼지_고기", Material.COOKED_PORKCHOP,
			ChatColor.WHITE, 9, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient pig_meat = new CookingIngredient("돼지_고기", Material.PORKCHOP, ChatColor.WHITE,
			6, 1 + 0, (short) 1, IngredientType.Hunting, cooked_pig_meat);
	public final static CookingIngredient cooked_rabbit_meat = new CookingIngredient("익힌_토끼_고기", Material.COOKED_RABBIT,
			ChatColor.WHITE, 9, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient rabbit_meat = new CookingIngredient("토끼_고기", Material.RABBIT, ChatColor.WHITE,
			6, 1 + 0, (short) 1, IngredientType.Hunting, cooked_rabbit_meat);
	public final static CookingIngredient cooked_sheep_meat = new CookingIngredient("익힌_양_고기", Material.COOKED_MUTTON,
			ChatColor.WHITE, 9, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient sheep_meat = new CookingIngredient("양_고기", Material.MUTTON, ChatColor.WHITE,
			6, 1 + 0, (short) 1, IngredientType.Hunting, cooked_sheep_meat);
	public final static CookingIngredient skeleton_horse_meat = new CookingIngredient("스켈레톤_말_고기",
			Material.ROTTEN_FLESH, ChatColor.WHITE, 10, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient snowman_orb = new CookingIngredient("스노우맨_구슬", Material.SNOWBALL,
			ChatColor.WHITE, 10, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cooked_squid_meat = new CookingIngredient("익힌_오징어_고기", Material.COOKED_MUTTON,
			ChatColor.WHITE, 15, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient squid_meat = new CookingIngredient("오징어_고기", Material.MUTTON, ChatColor.WHITE,
			10, 1 + 0, (short) 1, IngredientType.Hunting, cooked_squid_meat);
	public final static CookingIngredient cooked_strider_meat = new CookingIngredient("익힌_스트라이더_고기",
			Material.COOKED_PORKCHOP, ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient strider_meat = new CookingIngredient("스트라이더_고기", Material.PORKCHOP,
			ChatColor.WHITE, 20, 1 + 0, (short) 1, IngredientType.Hunting, cooked_strider_meat);
	public final static CookingIngredient cooked_turtle_meat = new CookingIngredient("익힌_거북_고기", Material.COOKED_MUTTON,
			ChatColor.WHITE, 15, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient turtle_meat = new CookingIngredient("거북_고기", Material.MUTTON, ChatColor.WHITE,
			10, 1 + 0, (short) 1, IngredientType.Hunting, cooked_turtle_meat);
	public final static CookingIngredient bee_meat = new CookingIngredient("벌_고기", Material.SPIDER_EYE, ChatColor.WHITE,
			10, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cave_spider_meat = new CookingIngredient("동굴_거미_고기", Material.SPIDER_EYE,
			ChatColor.WHITE, 10, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cooked_dolphin_meat = new CookingIngredient("익힌_돌고래_고기",
			Material.COOKED_MUTTON, ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient dolphin_meat = new CookingIngredient("돌고래_고기", Material.MUTTON,
			ChatColor.WHITE, 20, 1 + 0, (short) 1, IngredientType.Hunting, cooked_dolphin_meat);
	public final static CookingIngredient enderman_meat = new CookingIngredient("엔더맨_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 20, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cooked_goat_meat = new CookingIngredient("익힌_염소_고기", Material.COOKED_MUTTON,
			ChatColor.WHITE, 9, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient goat_meat = new CookingIngredient("염소_고기", Material.MUTTON, ChatColor.WHITE,
			6, 1 + 0, (short) 1, IngredientType.Hunting, cooked_goat_meat);
	public final static CookingIngredient irongolem_meat = new CookingIngredient("철골렘_고기", Material.IRON_INGOT,
			ChatColor.WHITE, 40, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cooked_llama_meat = new CookingIngredient("익힌_라마_고기", Material.COOKED_BEEF,
			ChatColor.WHITE, 9, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient llama_meat = new CookingIngredient("라마_고기", Material.BEEF, ChatColor.WHITE, 6,
			1 + 0, (short) 1, IngredientType.Hunting, cooked_llama_meat);
	public final static CookingIngredient cooked_panda_meat = new CookingIngredient("익힌_판다_고기",
			Material.COOKED_PORKCHOP, ChatColor.WHITE, 90, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient panda_meat = new CookingIngredient("판다_고기", Material.PORKCHOP,
			ChatColor.WHITE, 60, 1 + 0, (short) 1, IngredientType.Hunting, cooked_panda_meat);
	public final static CookingIngredient cooked_piglin_meat = new CookingIngredient("익힌_피글린_고기",
			Material.COOKED_PORKCHOP, ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient piglin_meat = new CookingIngredient("피글린_고기", Material.PORKCHOP,
			ChatColor.WHITE, 20, 1 + 0, (short) 1, IngredientType.Hunting, cooked_piglin_meat);
	public final static CookingIngredient cooked_polar_bear_meat = new CookingIngredient("익힌_북극곰_고기",
			Material.COOKED_PORKCHOP, ChatColor.WHITE, 45, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient polar_bear_meat = new CookingIngredient("북극곰_고기", Material.PORKCHOP,
			ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting, cooked_polar_bear_meat);
	public final static CookingIngredient spider_meat = new CookingIngredient("거미_고기", Material.SPIDER_EYE,
			ChatColor.WHITE, 15, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cooked_wolf_meat = new CookingIngredient("익힌_늑대_고기", Material.COOKED_BEEF,
			ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient wolf_meat = new CookingIngredient("늑대_고기", Material.BEEF, ChatColor.WHITE, 20,
			1 + 0, (short) 1, IngredientType.Hunting, cooked_wolf_meat);
	public final static CookingIngredient zombified_piglin_meat = new CookingIngredient("좀비화된_피글린_고기",
			Material.ROTTEN_FLESH, ChatColor.WHITE, 40, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient blaze_meat = new CookingIngredient("블레이즈_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 50, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient creeper_meat = new CookingIngredient("크리퍼_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient drowned_meat = new CookingIngredient("드라운드_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient elder_guardian_meat = new CookingIngredient("엘더_가디언_고기",
			Material.COOKED_MUTTON, ChatColor.WHITE, 75, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient endermite_meat = new CookingIngredient("엔더마이트_고기", Material.SPIDER_EYE,
			ChatColor.WHITE, 50, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient evoker_meat = new CookingIngredient("소환사_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 100, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient ghast_meat = new CookingIngredient("가스트_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 100, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient guardian_meat = new CookingIngredient("가디언_고기", Material.COOKED_MUTTON,
			ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cooked_hoglin_meat = new CookingIngredient("익힌_호글린_고기",
			Material.COOKED_PORKCHOP, ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient hoglin_meat = new CookingIngredient("호글린_고기", Material.PORKCHOP,
			ChatColor.WHITE, 20, 1 + 0, (short) 1, IngredientType.Hunting, cooked_hoglin_meat);
	public final static CookingIngredient husk_meat = new CookingIngredient("허스크_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient magmacube_meat = new CookingIngredient("마그마큐브_고기", Material.SPIDER_EYE,
			ChatColor.WHITE, 10, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cooked_phantom_meat = new CookingIngredient("익힌_팬텀_고기",
			Material.COOKED_CHICKEN, ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient phantom_meat = new CookingIngredient("팬텀_고기", Material.CHICKEN,
			ChatColor.WHITE, 20, 1 + 0, (short) 1, IngredientType.Hunting, cooked_phantom_meat);
	public final static CookingIngredient pillager_meat = new CookingIngredient("약탈자_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient cooked_ravager_meat = new CookingIngredient("익힌_파괴수_고기",
			Material.COOKED_PORKCHOP, ChatColor.WHITE, 180, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient ravager_meat = new CookingIngredient("파괴수_고기", Material.PORKCHOP,
			ChatColor.WHITE, 120, 1 + 0, (short) 1, IngredientType.Hunting, cooked_ravager_meat);
	public final static CookingIngredient shulker_meat = new CookingIngredient("셜커_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 40, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient sillverfish_meat = new CookingIngredient("좀벌레_고기", Material.SPIDER_EYE,
			ChatColor.WHITE, 40, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient skeleton_meat = new CookingIngredient("스켈레톤_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 20, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient slime_meat = new CookingIngredient("슬라임_고기", Material.SPIDER_EYE,
			ChatColor.WHITE, 10, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient stray_meat = new CookingIngredient("스트레이_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient vex_meat = new CookingIngredient("벡스_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 30, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient vindicator_meat = new CookingIngredient("변명자_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 60, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient witch_meat = new CookingIngredient("마녀_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 60, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient wither_skeleton_meat = new CookingIngredient("위더_스켈레톤_고기",
			Material.ROTTEN_FLESH, ChatColor.WHITE, 45, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient zoglin_meat = new CookingIngredient("조글린_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 45, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient zombie_meat = new CookingIngredient("좀비_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 20, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient enderdragon_meat = new CookingIngredient("엔더드래곤_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 2000, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient wither_meat = new CookingIngredient("위더_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 500, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient giant_meat = new CookingIngredient("거인_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 500, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient illusioner_meat = new CookingIngredient("환술사_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 100, 1 + 0, (short) 1, IngredientType.Hunting);
	public final static CookingIngredient zombie_horse_meat = new CookingIngredient("좀비_말_고기", Material.ROTTEN_FLESH,
			ChatColor.WHITE, 100, 1 + 0, (short) 1, IngredientType.Hunting);
	// POTION
	public final static AdrenalineItem adrenaline = new AdrenalineItem("아드레날린", ChatColor.DARK_PURPLE, 5);
	public final static AwakeningItem awakening = new AwakeningItem("각성_물약", ChatColor.DARK_PURPLE, 5);
	public final static TornadoGranadeItem tornado = new TornadoGranadeItem("회오리_폭탄", ChatColor.DARK_PURPLE, 500);
	//순간공격폭탄, 지속공격폭탄, 화상폭탄, 신속로브, 은신로브, 보호물약, 홀닥불, 성부, 만능, 암폭
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
		this("LOSTARK", key, type, color);
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

	public Material getType() {
		return type;
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
