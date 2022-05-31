package com.StarJ.LA.Systems;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import com.StarJ.LA.Items.FishItem;
import com.StarJ.LA.Items.FishingrodItem;

public class Fishes {
	private final static List<Fishes> list = new ArrayList<Fishes>();
	// All
	public static Fishes kingsalmon = new Fishes("kingsalmon", "왕연어", Biomes.values(), Size.Biggest, 0.5, 0.5);
	public static Fishes morhua = new Fishes(" morhua", "대서양대구", Biomes.values(), Size.Biggest, 0.5, 0.5);
	public static Fishes miraletus = new Fishes("miraletus", "거울가오리", Biomes.values(), Size.Middle, 0.5, 0.5);
	public static Fishes porphyeus = new Fishes("porphyeus", "검복", Biomes.values(), Size.Middle, 0.5, 0.5);
	public static Fishes berryi = new Fishes("berryi", "귀오징어불이", Biomes.values(), Size.Small, 0.5, 0.5);
	public static Fishes immaculatus = new Fishes("immaculatus", "거북복", Biomes.values(), Size.Small, 0.5, 0.5);
	public static Fishes vertens = new Fishes("vertens", "꽃모자갈퀴손해파리", Biomes.values(), Size.Smallest, 0.5, 0.5);
	public static Fishes constricta = new Fishes("constricta", "가리맛조개", Biomes.values(), Size.Smallest, 0.5, 0.5);
	public static Fishes saltator = new Fishes("saltator", "무희나선꼬리해파리", Biomes.values(), Size.Biggest, 0.5, 0.5);
	public static Fishes japonicus = new Fishes("japonicus", "납작소라", Biomes.values(), Size.Biggest, 0.5, 0.5);
	public static Fishes chinesis = new Fishes("chinesis", "한치꼴뚜기", Biomes.values(), Size.Biggest, 0.5, 0.5);
	public static Fishes sculpin = new Fishes("sculpin", "개구리꺽정이", Biomes.values(), Size.Big, 0.5, 0.5);
	public static Fishes pachycephalus = new Fishes("pachycephalus", "개볼락", Biomes.values(), Size.Big, 0.5, 0.5);
	public static Fishes robustus = new Fishes("robustus", "개서대", Biomes.values(), Size.Big, 0.5, 0.5);
	public static Fishes sole = new Fishes("sole", "개서대속", Biomes.values(), Size.Middle, 0.5, 0.5);
	public static Fishes coerulsecens = new Fishes("coerulsecens", "평면해파리", Biomes.values(), Size.Middle, 0.5, 0.5);
	public static Fishes striatus = new Fishes("striatus", "노란줄가리비", Biomes.values(), Size.Middle, 0.5, 0.5);
	public static Fishes pacificus = new Fishes("pacificus", "살오징어", Biomes.values(), Size.Small, 0.5, 0.5);
	public static Fishes goby = new Fishes("goby", "개소갱속", Biomes.values(), Size.Small, 0.5, 0.5);
	public static Fishes monoceros = new Fishes("monoceros", "객주리", Biomes.values(), Size.Small, 0.5, 0.5);
	public static Fishes cavasius = new Fishes("cavasius", "갠지스동자개", Biomes.values(), Size.Smallest, 0.5, 0.5);
	public static Fishes cinereus = new Fishes("cinereus", "갯장어", Biomes.values(), Size.Smallest, 0.5, 0.5);
	public static Fishes scapha = new Fishes("scapha", "거룻배가자미", Biomes.values(), Size.Smallest, 0.5, 0.5);
	// SNOWY - Biggest
	public static Fishes formosa = new Fishes("formosa", "꽃우산해파리", new Biomes[] { Biomes.Snowy }, Size.Biggest, 0.1, 1);
	public static Fishes neritodies = new Fishes("neritodies", "각시고동", new Biomes[] { Biomes.Snowy }, Size.Biggest, 0.1,
			0.75);
	public static Fishes morsei = new Fishes("morsei", "귀오징어", new Biomes[] { Biomes.Snowy }, Size.Biggest, 0.2, 1);
	public static Fishes jeoni = new Fishes("jeoni", "됭경모치", new Biomes[] { Biomes.Snowy }, Size.Biggest, 0.2, 0.75);
	public static Fishes stellaris = new Fishes("stellaris", "가는꼬리쥐치", new Biomes[] { Biomes.Snowy }, Size.Biggest, 0.3,
			0.75);
	public static Fishes pelamis = new Fishes("pelamis", "가다랑어", new Biomes[] { Biomes.Snowy }, Size.Biggest, 0.4,
			0.75);
	public static Fishes maruadsi = new Fishes("maruadsi", "가라지", new Biomes[] { Biomes.Snowy }, Size.Big, 0.5, 0.5);
	public static Fishes sp = new Fishes("sp", "푸른우산관해파리", new Biomes[] { Biomes.Snowy }, Size.Big, 0.5, 0.5);
	public static Fishes schlegelli = new Fishes("schlegelli", "가래상어", new Biomes[] { Biomes.Snowy }, Size.Middle, 0.5,
			0.5);
	public static Fishes granosa = new Fishes("granosa", "꼬막", new Biomes[] { Biomes.Snowy }, Size.Middle, 0.5, 0.5);
	public static Fishes sockeyesalmon = new Fishes("sockeyesalmon", "홍연어", new Biomes[] { Biomes.Snowy }, Size.Small,
			0.5, 0.5);
	public static Fishes bleekeri = new Fishes("bleekeri", "화살꼴뚜기", new Biomes[] { Biomes.Snowy }, Size.Small, 0.5,
			0.5);
	public static Fishes aquaman = new Fishes("aquaman", "아쿠아맨", new Biomes[] { Biomes.Snowy }, Size.Smallest, 0.5, 0);
	public static Fishes coioides = new Fishes("coioides", "갈색둥근바리", new Biomes[] { Biomes.Snowy }, Size.Smallest, 0.5,
			0.5);
	// COLD - BIG
	public static Fishes tetraphylla = new Fishes("tetraphylla", "네잎백합해파리", new Biomes[] { Biomes.Cold }, Size.Biggest,
			0.5, 0.5);
	public static Fishes equula = new Fishes("equula", "갈전갱이", new Biomes[] { Biomes.Cold }, Size.Biggest, 0.5, 0.5);
	public static Fishes echo = new Fishes("echo", "각시수염고둥", new Biomes[] { Biomes.Cold }, Size.Big, 0.1, 1);
	public static Fishes rhombus = new Fishes("rhombus", "날개오징어", new Biomes[] { Biomes.Cold }, Size.Big, 0.1, 0.75);
	public static Fishes digrammus = new Fishes("digrammus", "가면놀래기", new Biomes[] { Biomes.Cold }, Size.Big, 0.1,
			0.75);
	public static Fishes haematocheilus = new Fishes("haematocheilus", "가숭어", new Biomes[] { Biomes.Cold }, Size.Big,
			0.2, 1);
	public static Fishes nadeshnyi = new Fishes("nadeshnyi", "가시가자미", new Biomes[] { Biomes.Cold }, Size.Big, 0.2,
			0.75);
	public static Fishes auriga = new Fishes("auriga", "가시나비고기", new Biomes[] { Biomes.Cold }, Size.Big, 0.3, 0.75);
	public static Fishes gracills = new Fishes("gracills", "가시납지리", new Biomes[] { Biomes.Cold }, Size.Middle, 0.4,
			0.75);
	public static Fishes merra = new Fishes("merra", "갈점바리", new Biomes[] { Biomes.Cold }, Size.Middle, 0.5, 0.5);
	public static Fishes saury = new Fishes("saury", "꽁치", new Biomes[] { Biomes.Cold }, Size.Small, 0.5, 0.5);
	public static Fishes anchovy = new Fishes("lepturus", "멸치", new Biomes[] { Biomes.Cold }, Size.Small, 0.5, 0.5);
	public static Fishes lepturus = new Fishes("lepturus", "갈치", new Biomes[] { Biomes.Cold }, Size.Smallest, 0.5, 0.5);

	public static Fishes nigra = new Fishes("nigra", "감돌고기", new Biomes[] { Biomes.Cold }, Size.Smallest, 0.5, 0.5);
	// Temperate - MIDDLE
	public static Fishes rastoni = new Fishes("rastoni", "라스톤입방해파리", new Biomes[] { Biomes.Temperate }, Size.Biggest,
			0.5, 0.5);
	public static Fishes cuminigi = new Fishes("cuminigi", "갈색띠매물고동", new Biomes[] { Biomes.Temperate }, Size.Big, 0.5,
			0.5);
	public static Fishes lucidas = new Fishes("lucidas", "입술무늬갑오징어", new Biomes[] { Biomes.Temperate }, Size.Middle,
			0.1, 1);
	public static Fishes japonica = new Fishes("japonica", "가시달강어", new Biomes[] { Biomes.Temperate }, Size.Middle, 0.1,
			0.75);
	public static Fishes thomsoni = new Fishes("thomsoni", "가시발새우", new Biomes[] { Biomes.Temperate }, Size.Middle, 0.2,
			1);
	public static Fishes holocanthus = new Fishes("holocanthus", "가시복", new Biomes[] { Biomes.Temperate }, Size.Middle,
			0.2, 0.75);
	public static Fishes spinifer = new Fishes("spinifer", "가시실붉돔", new Biomes[] { Biomes.Temperate }, Size.Middle, 0.3,
			0.75);
	public static Fishes langsdorfii = new Fishes("langsdorfii", "가시양태", new Biomes[] { Biomes.Temperate }, Size.Middle,
			04, 0.75);
	public static Fishes scomber = new Fishes("scomber", "고등어", new Biomes[] { Biomes.Temperate }, Size.Small, 0.5,
			0.5);
	public static Fishes sinensis = new Fishes("sinensis", "철갑상어", new Biomes[] { Biomes.Temperate }, Size.Smallest,
			0.5, 0.5);
	// WARM - SMALL
	public static Fishes aurita = new Fishes("aurita", "보름달물해파리", new Biomes[] { Biomes.Warm }, Size.Biggest, 0.5, 0.5);
	public static Fishes quinquecirrha = new Fishes("quinquecirrha", "커튼원양해파리", new Biomes[] { Biomes.Warm },
			Size.Biggest, 0.5, 0.5);
	public static Fishes chinensis = new Fishes("chinensis", "개량조개", new Biomes[] { Biomes.Warm }, Size.Big, 0.5, 0.5);
	public static Fishes aduncus = new Fishes("aduncus", "날개뿔고둥", new Biomes[] { Biomes.Warm }, Size.Big, 0.5, 0.5);
	public static Fishes esculenta = new Fishes("esculenta", "철갑오징어", new Biomes[] { Biomes.Warm }, Size.Middle, 0.5,
			0.5);
	public static Fishes lessonjana = new Fishes("lessonjana", "흰꼴뚜기", new Biomes[] { Biomes.Warm }, Size.Middle, 0.5,
			0.5);
	public static Fishes lucifer = new Fishes("lucifer", "가시줄상어", new Biomes[] { Biomes.Warm }, Size.Small, 0.1, 1);
	public static Fishes gayigayi = new Fishes("gayigayi", "가이민대구", new Biomes[] { Biomes.Warm }, Size.Small, 0.1,
			0.75);
	public static Fishes californicus = new Fishes("californicus", "가주넙치", new Biomes[] { Biomes.Warm }, Size.Small,
			0.2, 1);
	public static Fishes aspera = new Fishes("aspera", "각시가자미", new Biomes[] { Biomes.Warm }, Size.Small, 0.2, 0.75);
	public static Fishes hirundinacea = new Fishes("hirundinacea", "각시돔", new Biomes[] { Biomes.Warm }, Size.Small, 0.3,
			0.75);
	public static Fishes mola = new Fishes("mola", "개복치", new Biomes[] { Biomes.Warm }, Size.Small, 0.4, 0.75);
	public static Fishes doctor = new Fishes("doctor", "닥터피쉬", new Biomes[] { Biomes.Warm }, Size.Smallest, 0.5, 0.5);
	public static Fishes schleglii = new Fishes("schleglii", "감성돔", new Biomes[] { Biomes.Warm }, Size.Smallest, 0.5,
			0.5);
	// AQUASTIC - SMALLEST
	public static Fishes nozakii = new Fishes("nozakii", "유령해파리", new Biomes[] { Biomes.Aquastic }, Size.Biggest, 0.5,
			0.5);
	public static Fishes perplexus = new Fishes("perplexus", "긴고둥", new Biomes[] { Biomes.Aquastic }, Size.Big, 0.5,
			0.5);
	public static Fishes fangsiao = new Fishes("fangsiao", "쭈꾸미", new Biomes[] { Biomes.Aquastic }, Size.Middle, 0.5,
			0.5);
	public static Fishes uyekii = new Fishes("uyekii", "각시붕어", new Biomes[] { Biomes.Aquastic }, Size.Small, 0.5, 0.5);
	public static Fishes zebrias = new Fishes("zebrias", "각시서대속", new Biomes[] { Biomes.Aquastic }, Size.Smallest, 0.1,
			1);
	public static Fishes kitaharai = new Fishes("kitaharai", "갈가자미", new Biomes[] { Biomes.Aquastic }, Size.Smallest,
			0.1, 0.75);
	public static Fishes muroadsi = new Fishes("muroadsi", "갈고등어", new Biomes[] { Biomes.Aquastic }, Size.Smallest, 0.2,
			1);
	public static Fishes tile = new Fishes("tile", "갈래세줄가는돔", new Biomes[] { Biomes.Aquastic }, Size.Smallest, 0.2,
			0.75);
	public static Fishes cheilopogon = new Fishes("cheilopogon", "날치", new Biomes[] { Biomes.Aquastic }, Size.Smallest,
			0.3, 0.75);
	public static Fishes paralichthys = new Fishes("paralichthys", "광어", new Biomes[] { Biomes.Aquastic },
			Size.Smallest, 0.4, 0.75);
	public static Fishes kamoharai = new Fishes("kamoharai", "강남상어", new Biomes[] { Biomes.Neutral }, Size.Small, 0.5,
			0.5);
	public static Fishes punctatus = new Fishes("punctatus", "강담돔", new Biomes[] { Biomes.Neutral }, Size.Middle, 0.5,
			0.5);
	public static Fishes sp__ = new Fishes("sp__a", "강담복속", new Biomes[] { Biomes.Neutral }, Size.Big, 0.5, 0.5);
	public static Fishes stellatus = new Fishes("stellatus", "강도다리", new Biomes[] { Biomes.Neutral }, Size.Biggest, 0.5,
			0.5);

	private final String key;
	private final String name;
	private final Biomes[] biomes;
	private final Size size;
	private final double man_chance;
	private final Length length;
	private final long price;
	private final CustomAdvancements advancement;
	private final HashMap<Rarity, HashMap<NormalType, FishItem>> items;
	private final HashMap<Rarity, HashMap<FIshCookedType, FishItem>> cooks;

	public Fishes(String key, String name, Biomes[] biomes, Size size, double man_chance, double length_percent) {
		this(key, name, biomes, size, man_chance, length_percent, null);
	}

	public Fishes(String key, String name, Biomes[] biomes, Size size, double man_chance, double length_percent,
			CustomAdvancements advancement) {
		this.key = key;
		this.name = name;
		this.biomes = biomes;
		this.size = size;
		this.man_chance = man_chance;
		this.length = new Length(size, length_percent);
		this.price = 250;
		this.advancement = advancement;
		list.add(this);
		for (Biomes biome : biomes)
			biome.addFishes(this);
		this.items = new HashMap<Rarity, HashMap<NormalType, FishItem>>();
		this.cooks = new HashMap<Rarity, HashMap<FIshCookedType, FishItem>>();
		for (Rarity r : Rarity.values()) {
			HashMap<NormalType, FishItem> r_items = new HashMap<NormalType, FishItem>();
			HashMap<FIshCookedType, FishItem> r_cooks = new HashMap<FIshCookedType, FishItem>();
			for (NormalType type : NormalType.values())
				r_items.put(type, new FishItem(this.key, ChatColor.stripColor(r.prefix + "_" + name).replace(' ', '_'),
						r.getColor(), name, size, man_chance, length, price, r, type));
			for (FIshCookedType type : FIshCookedType.values())
				r_cooks.put(type, new FishItem(this.key, ChatColor.stripColor(r.prefix + "_" + name).replace(' ', '_'),
						r.getColor(), name, size, man_chance, length, price, r, type));
			items.put(r, r_items);
			cooks.put(r, r_cooks);
		}
	}

	public enum NormalType {
		COD("대구형", Material.COD, 1, 0.4), SALMON("연어형", Material.SALMON, 1.2, 0.35),
		PUFFERFISH("복어형", Material.PUFFERFISH, 2, 0.15), TROPICAL_FISH("열대어형", Material.TROPICAL_FISH, 2.5, 0.1)
		//
		;

		private final String prefix;
		private final Material type;
		private final double base;
		private final double chance;

		private NormalType(String prefix, Material type, double base, double chance) {
			this.prefix = prefix;
			this.type = type;
			this.base = base;
			this.chance = chance;
		}

		public String getPrefix() {
			return prefix;
		}

		public Material getType() {
			return type;
		}

		public double getBase() {
			return base;
		}

		public double getChance() {
			return chance;
		}

		public static NormalType getRandomNormalType() {
			double now = new Random().nextDouble();
			double chance = 0.0d;
			for (NormalType type : values()) {
				chance += type.getChance();
				if (now < chance)
					return type;
			}
			return NormalType.COD;
		}
	}

	public enum FIshCookedType {
		COOKED_COD(NormalType.COD.getPrefix(), Material.COOKED_COD, Material.COD, 1.5),
		COOKED_SALMON(NormalType.SALMON.getPrefix(), Material.COOKED_SALMON, Material.SALMON, 1.8)
		//
		;

		private final String prefix;
		private final Material type;
		private final Material raw;
		private final double base;

		private FIshCookedType(String prefix, Material type, Material raw, double base) {
			this.prefix = prefix;
			this.type = type;
			this.raw = raw;
			this.base = base;
		}

		public String getPrefix() {
			return prefix;
		}

		public Material getType() {
			return type;
		}

		public double getBase() {
			return base;
		}

		public static FIshCookedType getCookedType(NormalType type) {
			return getCookedType(type.getType());
		}

		public static FIshCookedType getCookedType(Material raw) {
			for (FIshCookedType type : values())
				if (type.raw.equals(raw))
					return type;
			return FIshCookedType.COOKED_COD;
		}
	}

	protected Material[] cook_type = new Material[] { Material.COOKED_COD, Material.COOKED_SALMON };

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public Biomes[] getBiomes() {
		return biomes;
	}

	public Size getSize() {
		return size;
	}

	public double getManChance() {
		return man_chance;
	}

	public Length getLength() {
		return length;
	}

	public long getPrice() {
		return price;
	}

	public CustomAdvancements getAdvancement() {
		return advancement;
	}

	public ItemStack getItemStack(double money_multi, HashMap<Rarity, BigDecimal> ench,
			HashMap<Rarity, BigDecimal> abil, List<Rarity> remove, HashMap<Rarity, BigDecimal> remove_multi) {
		return items.get(Rarity.getRandomRarity(ench, abil, remove, remove_multi)).get(NormalType.getRandomNormalType())
				.getItemStack(Double.valueOf(money_multi));
	}

	public static Rarity getRarity(ItemStack item) {
		try {
			if (item.hasItemMeta() && item.getItemMeta().hasLore())
				for (String lore : item.getItemMeta().getLore())
					if (lore.contains("희귀도 : "))
						return Rarity.valueOf(ChatColor.stripColor(lore.split(" : ")[1]));
		} catch (NullPointerException ex) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "물고기 레어도 오류 발생");
		}
		return null;
	}

	public static Fishes getCaugthFish(Location loc, ItemStack rod) {
		List<Fishes> canCaught = new ArrayList<Fishes>();
		Size size = FishingrodItem.getSize(rod);
		for (Fishes fish : values()) {
			if (Biomes.canCaught(fish.biomes, loc.getBlock().getBiome()) && (size == null || fish.size.equals(size)))
				canCaught.add(fish);
		}
		if (canCaught.size() > 0) {
			Collections.shuffle(canCaught);
			return canCaught.get(0);
		}
		return null;
	}

	public static class Length {
		private final double min;
		private final double max;

		public Length(Size size, double percent) {
			if (percent >= 3)
				percent = 3;
			this.min = size.getMainLength() * (1 - 0.3 * percent);
			this.max = size.getMainLength() * (1 + 0.3 * percent);
		}

		public double getMax() {
			return max;
		}

		public double getMin() {
			return min;
		}

		public double getRandom() {
			return (max > min) ? (min + new Random().nextInt((int) ((max - min) * 100)) / 100.0)
					: (max == min ? min : 0);
		}

		public double getPercent(double length) {
			return (max - min > 0) ? ((length - min) / (max - min)) : 0;
		}
	}

	public enum Rarity {
		Trash(ChatColor.GRAY, "썩어버린", 19200, 1), Common(ChatColor.WHITE, "흔한", 9600, 2),
		Uncommon(ChatColor.YELLOW, "비범한", 4800, 5), Rare(ChatColor.AQUA, "희귀한", 1200, 10),
		Epic(ChatColor.LIGHT_PURPLE, "환상의", 600, 25), Treasure(ChatColor.RED, "보물의", 300, 50),
		God(ChatColor.GOLD, "신의", 100, 100),
		//
		;

		private final int chance;
		private final ChatColor color;
		private final String prefix;
		private final int multi;

		private Rarity(ChatColor color, String prefix, int chance, int money) {
			this.color = color;
			this.prefix = prefix;
			this.chance = chance;
			this.multi = money;
		}

		public String getPrefix() {
			return color + prefix;
		}

		public BigDecimal getChance() {
			return BigDecimal.valueOf(chance);
		}

		public int getMulti() {
			return multi;
		}

		public ChatColor getColor() {
			return color;
		}

		public static Rarity getRandomRarity(HashMap<Rarity, BigDecimal> ench, HashMap<Rarity, BigDecimal> abil,
				List<Rarity> rest, HashMap<Rarity, BigDecimal> remove_multi) {
			int num = 0;
			Rarity[] rars = Rarity.values().clone();
			for (Rarity r : rest)
				for (int c = 0; c < rars.length; c++)
					if (rars[c] != null && rars[c].equals(r))
						rars[c] = null;

			for (Rarity r : rars)
				if (r != null) {
					num += r.getChance()
							.multiply(BigDecimal.ONE.add((ench.containsKey(r) ? ench.get(r) : BigDecimal.ZERO)
									.multiply((remove_multi.containsKey(r) ? remove_multi.get(r) : BigDecimal.ONE))))
							.multiply(BigDecimal.ONE.add((abil.containsKey(r) ? abil.get(r) : BigDecimal.ZERO)
									.multiply((remove_multi.containsKey(r) ? remove_multi.get(r) : BigDecimal.ONE))))
							.intValue();

				}
			int rand = new Random().nextInt(num);
			int chance = 0;
			for (Rarity r : rars)
				if (r != null) {
					chance += r.getChance()
							.multiply(BigDecimal.ONE.add((ench.containsKey(r) ? ench.get(r) : BigDecimal.ZERO)
									.multiply((remove_multi.containsKey(r) ? remove_multi.get(r) : BigDecimal.ONE))))
							.multiply(BigDecimal.ONE.add((abil.containsKey(r) ? abil.get(r) : BigDecimal.ZERO)
									.multiply((remove_multi.containsKey(r) ? remove_multi.get(r) : BigDecimal.ONE))))
							.intValue();
					if (rand < chance)
						return r;
				}

			return Rarity.Trash;
		}
	}

	public static enum Size {
//		None("없음",1),
		Smallest("초소형", 10), Small("소형", 30), Middle("중형", 60), Big("대형", 100), Biggest("초대형", 200),
		//
		;

		// 0
		// 2~10
		// 10~20
		// 20~50
		// 50~100
		// 100~500
		private final String name;
		private final double main_length;

		private Size(String name, double main_length) {
			this.name = name;
			this.main_length = main_length;
		}

		public String getName() {
			return name;
		}

		public double getMainLength() {
			return main_length;
		}
	}

	public static List<Fishes> values() {
		return list;
	}

	public enum Biomes {
		Snowy(CustomAdvancements.snowy_fish, CustomAdvancements.snowy_fish_type, Biome.SNOWY_PLAINS, Biome.ICE_SPIKES,
				Biome.SNOWY_TAIGA, Biome.SNOWY_BEACH, Biome.GROVE, Biome.SNOWY_SLOPES, Biome.JAGGED_PEAKS,
				Biome.FROZEN_PEAKS),
		Cold(CustomAdvancements.cold_fish, CustomAdvancements.cold_fish_type, Biome.WINDSWEPT_HILLS,
				Biome.WINDSWEPT_GRAVELLY_HILLS, Biome.WINDSWEPT_FOREST, Biome.TAIGA, Biome.OLD_GROWTH_PINE_TAIGA,
				Biome.OLD_GROWTH_SPRUCE_TAIGA, Biome.STONY_SHORE),
		Temperate(CustomAdvancements.temperate_fish, CustomAdvancements.temperate_fish_type, Biome.PLAINS,
				Biome.SUNFLOWER_PLAINS, Biome.FOREST, Biome.FLOWER_FOREST, Biome.BIRCH_FOREST,
				Biome.OLD_GROWTH_BIRCH_FOREST, Biome.DARK_FOREST, Biome.SWAMP, Biome.JUNGLE, Biome.SPARSE_JUNGLE,
				Biome.BAMBOO_JUNGLE, Biome.BEACH, Biome.MUSHROOM_FIELDS, Biome.MEADOW, Biome.STONY_PEAKS),
		Warm(CustomAdvancements.warm_fish, CustomAdvancements.warm_fish_type, Biome.DESERT, Biome.SAVANNA,
				Biome.SAVANNA_PLATEAU, Biome.WINDSWEPT_SAVANNA, Biome.BADLANDS, Biome.WOODED_BADLANDS,
				Biome.ERODED_BADLANDS),
		Aquastic(CustomAdvancements.aquastic_fish, CustomAdvancements.aquastic_fish_type, Biome.RIVER,
				Biome.FROZEN_RIVER, Biome.WARM_OCEAN, Biome.LUKEWARM_OCEAN, Biome.DEEP_LUKEWARM_OCEAN, Biome.OCEAN,
				Biome.DEEP_OCEAN, Biome.COLD_OCEAN, Biome.DEEP_COLD_OCEAN, Biome.FROZEN_OCEAN, Biome.DEEP_FROZEN_OCEAN),
		Cave(CustomAdvancements.cave_fish, CustomAdvancements.cave_fish_type, Biome.DRIPSTONE_CAVES, Biome.LUSH_CAVES),
		Neutral(Biome.THE_VOID),
		Nether(CustomAdvancements.nether_fish, CustomAdvancements.nether_fish_type, Biome.NETHER_WASTES,
				Biome.SOUL_SAND_VALLEY, Biome.CRIMSON_FOREST, Biome.WARPED_FOREST, Biome.BASALT_DELTAS),
		End(CustomAdvancements.end_fish, CustomAdvancements.end_fish_type, Biome.THE_END, Biome.SMALL_END_ISLANDS,
				Biome.END_MIDLANDS, Biome.END_HIGHLANDS, Biome.END_BARRENS),
		//
		;

		private final CustomAdvancements advancement;
		private final CustomAdvancements advancement_type;
		private final Biome[] biomes;
		private final List<Fishes> fishes = new ArrayList<Fishes>();

		private Biomes(Biome... biomes) {
			this.advancement = null;
			this.advancement_type = null;
			this.biomes = biomes;
		}

		private Biomes(CustomAdvancements advancement, CustomAdvancements advancement_type, Biome... biomes) {
			this.advancement = advancement;
			this.advancement_type = advancement_type;
			this.biomes = biomes;
		}

		public CustomAdvancements getAdvancement() {
			return advancement;
		}

		public CustomAdvancements getAdvancement_type() {
			return advancement_type;
		}

		public Biome[] getBiomes() {
			return biomes;
		}

		public void addFishes(Fishes fish) {
			fishes.add(fish);
		}

		public List<Fishes> getFishes() {
			return fishes;
		}

		public static Biomes getBiome(Biomes[] biomes, Biome biome) {
			for (Biomes bis : biomes)
				if (Arrays.asList(bis.getBiomes()).contains(biome))
					return bis;
			return null;
		}

		public static boolean canCaught(Biomes[] biomes, Biome biome) {
			for (Biomes bis : biomes)
				if (Arrays.asList(bis.getBiomes()).contains(biome))
					return true;
			return false;
		}

	}
}
