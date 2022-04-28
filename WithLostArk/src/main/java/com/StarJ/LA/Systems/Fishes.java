package com.StarJ.LA.Systems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.StarJ.LA.Items.FishingrodItem;

public class Fishes {
	private final static List<Fishes> list = new ArrayList<Fishes>();
	// SNOWY
	// // Trash
	public static Fishes formosa = new Fishes("formosa", "꽃우산해파리", Material.PUFFERFISH, new Biomes[] { Biomes.Snowy },
			Cycle.values(), Weather.Always, Size.Smallest, 0.258, new Length(2.58, 9.59), 163, Rarity.Trash);
	public static Fishes neritodies = new Fishes("neritodies", "각시고동", Material.MUSHROOM_STEW,
			new Biomes[] { Biomes.Snowy }, Cycle.values(), Weather.Always, Size.Small, 0.676, new Length(12.03, 15.59),
			175, Rarity.Trash);
	public static Fishes morsei = new Fishes("morsei", "귀오징어", Material.INK_SAC, new Biomes[] { Biomes.Snowy },
			Cycle.values(), Weather.Always, Size.Small, 0.541, new Length(10.25, 18.55), 164, Rarity.Trash);
	// // Common
	public static Fishes jeoni = new Fishes("jeoni", "됭경모치", Material.SALMON, new Biomes[] { Biomes.Snowy },
			Cycle.values(), Weather.Always, Size.Smallest, 0.814, new Length(3.76, 9.27), 453, Rarity.Common);
	public static Fishes stellaris = new Fishes("stellaris", "가는꼬리쥐치", Material.SALMON, new Biomes[] { Biomes.Snowy },
			Cycle.values(), Weather.Always, Size.Small, 0.100, new Length(11.87, 18.82), 201, Rarity.Common);
	public static Fishes pelamis = new Fishes("pelamis", "가다랑어", Material.SALMON, new Biomes[] { Biomes.Snowy },
			Cycle.values(), Weather.Always, Size.Middle, 0.042, new Length(22.76, 45.44), 348, Rarity.Common);
	public static Fishes maruadsi = new Fishes("maruadsi", "가라지", Material.SALMON, new Biomes[] { Biomes.Snowy },
			Cycle.values(), Weather.Always, Size.Big, 0.611, new Length(66.50, 91.00), 423, Rarity.Common);
	public static Fishes schlegelli = new Fishes("schlegelli", "가래상어", Material.SALMON, new Biomes[] { Biomes.Snowy },
			Cycle.values(), Weather.Always, Size.Biggst, 0.296, new Length(154.73, 429.59), 489, Rarity.Common);
	// // Uncommon

	// // Rare

	// // Epic

	/*
	 * 
	 */
	// COLD
	// // Trash
	public static Fishes tetraphylla = new Fishes("tetraphylla", "네잎백합해파리", Material.PUFFERFISH,
			new Biomes[] { Biomes.Cold }, Cycle.values(), Weather.Always, Size.Small, 0.495, new Length(13.12, 16.26),
			114, Rarity.Trash);
	public static Fishes echo = new Fishes("echo", "각시수염고둥", Material.MUSHROOM_STEW, new Biomes[] { Biomes.Cold },
			Cycle.values(), Weather.Always, Size.Smallest, 0.057, new Length(2.1, 3.43), 152, Rarity.Trash);
	public static Fishes rhombus = new Fishes("rhombus", "날개오징어", Material.INK_SAC, new Biomes[] { Biomes.Cold },
			Cycle.values(), Weather.Always, Size.Middle, 0.782, new Length(28.82, 47.50), 110, Rarity.Trash);
	// // Common
	public static Fishes digrammus = new Fishes("digrammus", "가면놀래기", Material.SALMON, new Biomes[] { Biomes.Cold },
			Cycle.values(), Weather.Always, Size.Smallest, 0.793, new Length(3.23, 7.46), 290, Rarity.Common);
	public static Fishes haematocheilus = new Fishes("haematocheilus", "가숭어", Material.SALMON,
			new Biomes[] { Biomes.Cold }, Cycle.values(), Weather.Always, Size.Small, 0.161, new Length(10.90, 17.78),
			226, Rarity.Common);
	public static Fishes nadeshnyi = new Fishes("nadeshnyi", "가시가자미", Material.SALMON, new Biomes[] { Biomes.Cold },
			Cycle.values(), Weather.Always, Size.Middle, 0.201, new Length(20.39, 47.07), 489, Rarity.Common);
	public static Fishes auriga = new Fishes("auriga", "가시나비고기", Material.SALMON, new Biomes[] { Biomes.Cold },
			Cycle.values(), Weather.Always, Size.Big, 0.449, new Length(55.40, 88.05), 468, Rarity.Common);
	public static Fishes gracills = new Fishes("gracills", "가시납지리", Material.SALMON, new Biomes[] { Biomes.Cold },
			Cycle.values(), Weather.Always, Size.Biggst, 0.850, new Length(438.65, 482.06), 428, Rarity.Common);
	// // Uncommon

	// // Rare

	// // Epic
	/*
	 * 
	 */
	// Temperate
	// // Trash
	public static Fishes rastoni = new Fishes("rastoni", "라스톤입방해파리", Material.PUFFERFISH,
			new Biomes[] { Biomes.Temperate }, Cycle.values(), Weather.Always, Size.Middle, 0.726,
			new Length(22.93, 30.45), 182, Rarity.Trash);
	public static Fishes cuminigi = new Fishes("cuminigi", "갈색띠매물고동", Material.MUSHROOM_STEW,
			new Biomes[] { Biomes.Temperate }, Cycle.values(), Weather.Always, Size.Big, 0.876,
			new Length(51.30, 94.84), 197, Rarity.Trash);
	public static Fishes lucidas = new Fishes("lucidas", "입술무늬갑오징어", Material.INK_SAC,
			new Biomes[] { Biomes.Temperate }, Cycle.values(), Weather.Always, Size.Biggst, 0.612,
			new Length(135.91, 216.90), 126, Rarity.Trash);
	// // Common
	public static Fishes japonica = new Fishes("japonica", "가시달강어", Material.SALMON, new Biomes[] { Biomes.Temperate },
			Cycle.values(), Weather.Always, Size.Smallest, 0.334, new Length(5.37, 8.72), 210, Rarity.Common);

	public static Fishes thomsoni = new Fishes("thomsoni", "가시발새우", Material.SALMON, new Biomes[] { Biomes.Temperate },
			Cycle.values(), Weather.Always, Size.Small, 0.113, new Length(10.29, 14.92), 340, Rarity.Common);
	public static Fishes holocanthus = new Fishes("holocanthus", "가시복", Material.SALMON,
			new Biomes[] { Biomes.Temperate }, Cycle.values(), Weather.Always, Size.Middle, 0.228,
			new Length(39.72, 47.59), 229, Rarity.Common);
	public static Fishes spinifer = new Fishes("spinifer", "가시실붉돔", Material.SALMON, new Biomes[] { Biomes.Temperate },
			Cycle.values(), Weather.Always, Size.Big, 0.795, new Length(52.76, 56.11), 464, Rarity.Common);
	public static Fishes langsdorfii = new Fishes("langsdorfii", "가시양태", Material.SALMON,
			new Biomes[] { Biomes.Temperate }, Cycle.values(), Weather.Always, Size.Biggst, 0.268,
			new Length(225.34, 446.99), 423, Rarity.Common);
	// // Uncommon

	// // Rare

	// // Epic
	/*
	 * 
	 */
	// WARM 20
	// // Trash
	public static Fishes aurita = new Fishes("aurita", "보름달물해파리", Material.PUFFERFISH, new Biomes[] { Biomes.Warm },
			Cycle.values(), Weather.Always, Size.Big, 0.136, new Length(77.29, 88.75), 134, Rarity.Trash);
	public static Fishes chinensis = new Fishes("chinensis", "개량조개", Material.MUSHROOM_STEW,
			new Biomes[] { Biomes.Warm }, Cycle.values(), Weather.Always, Size.Middle, 0.998, new Length(29.48, 31.43),
			193, Rarity.Trash);
	public static Fishes esculenta = new Fishes("esculenta", "철갑오징어", Material.INK_SAC, new Biomes[] { Biomes.Warm },
			Cycle.values(), Weather.Always, Size.Middle, 0.815, new Length(26.29, 37.77), 112, Rarity.Trash);
	// // Common
	public static Fishes lucifer = new Fishes("lucifer", "가시줄상어", Material.SALMON, new Biomes[] { Biomes.Warm },
			Cycle.values(), Weather.Always, Size.Smallest, 0.830, new Length(6.92, 969), 418, Rarity.Common);
	public static Fishes gayigayi = new Fishes("gayigayi", "가이민대구", Material.SALMON, new Biomes[] { Biomes.Warm },
			Cycle.values(), Weather.Always, Size.Small, 0.658, new Length(16.03, 17.23), 371, Rarity.Common);
	public static Fishes californicus = new Fishes("californicus", "가주넙치", Material.SALMON,
			new Biomes[] { Biomes.Warm }, Cycle.values(), Weather.Always, Size.Middle, 0.852, new Length(41.03, 42.39),
			301, Rarity.Common);
	public static Fishes aspera = new Fishes("aspera", "각시가자미", Material.SALMON, new Biomes[] { Biomes.Warm },
			Cycle.values(), Weather.Always, Size.Big, 0.579, new Length(84.12, 89.15), 210, Rarity.Common);
	public static Fishes hirundinacea = new Fishes("hirundinacea", "각시돔", Material.SALMON, new Biomes[] { Biomes.Warm },
			Cycle.values(), Weather.Always, Size.Biggst, 0.244, new Length(385.76, 420.26), 204, Rarity.Common);
	// // Uncommon

	// // Rare

	// // Epic
	/*
	 * 
	 */
	// AQUASTIC
	// // Trash
	public static Fishes nozakii = new Fishes("nozakii", "유령해파리", Material.PUFFERFISH, new Biomes[] { Biomes.Aquastic },
			Cycle.values(), Weather.Always, Size.Biggst, 0.575, new Length(118.07, 307.60), 141, Rarity.Trash);
	public static Fishes perplexus = new Fishes("perplexus", "긴고둥", Material.MUSHROOM_STEW,
			new Biomes[] { Biomes.Aquastic }, Cycle.values(), Weather.Always, Size.Middle, 0.941,
			new Length(23.08, 31.08), 178, Rarity.Trash);
	public static Fishes fangsiao = new Fishes("fangsiao", "쭈꾸미", Material.INK_SAC, new Biomes[] { Biomes.Aquastic },
			Cycle.values(), Weather.Always, Size.Smallest, 0.749, new Length(5.49, 7.70), 193, Rarity.Trash);
	// // Common
	public static Fishes uyekii = new Fishes("uyekii", "각시붕어", Material.SALMON, new Biomes[] { Biomes.Aquastic },
			Cycle.values(), Weather.Always, Size.Smallest, 0.749, new Length(5.49, 7.70), 193, Rarity.Common);
	public static Fishes zebrias = new Fishes("zebrias", "각시서대속", Material.SALMON, new Biomes[] { Biomes.Aquastic },
			Cycle.values(), Weather.Always, Size.Small, 0.749, new Length(5.49, 7.70), 193, Rarity.Common);
	public static Fishes kitaharai = new Fishes("kitaharai", "갈가자미", Material.SALMON, new Biomes[] { Biomes.Aquastic },
			Cycle.values(), Weather.Always, Size.Middle, 0.749, new Length(5.49, 7.70), 193, Rarity.Common);
	public static Fishes muroadsi = new Fishes("muroadsi", "갈고등어", Material.SALMON, new Biomes[] { Biomes.Aquastic },
			Cycle.values(), Weather.Always, Size.Big, 0.749, new Length(5.49, 7.70), 193, Rarity.Common);
	public static Fishes tile = new Fishes("tile", "갈래세줄가는돔", Material.SALMON, new Biomes[] { Biomes.Aquastic },
			Cycle.values(), Weather.Always, Size.Biggst, 0.749, new Length(5.49, 7.70), 193, Rarity.Common);
	// // Uncommon

	// // Rare

	// // Epic
	/*
	 * 
	 */
	// CAVE
	// // Trash
	public static Fishes sp = new Fishes("sp", "푸른우산관해파리", Material.PUFFERFISH, new Biomes[] { Biomes.Cave },
			Cycle.values(), Weather.Always, Size.Middle, 0.020, new Length(27.85, 36.17), 117, Rarity.Trash);
	public static Fishes granosa = new Fishes("granosa", "꼬막", Material.MUSHROOM_STEW, new Biomes[] { Biomes.Cave },
			Cycle.values(), Weather.Always, Size.Small, 0.373, new Length(13.79, 15.80), 101, Rarity.Trash);
	public static Fishes bleekeri = new Fishes("bleekeri", "화살꼴뚜기", Material.INK_SAC, new Biomes[] { Biomes.Cave },
			Cycle.values(), Weather.Always, Size.Small, 0.621, new Length(11.30, 19.77), 109, Rarity.Trash);
	// // Common
	public static Fishes coioides = new Fishes("coioides", "갈색둥근바리", Material.SALMON, new Biomes[] { Biomes.Cave },
			Cycle.values(), Weather.Always, Size.Smallest, 0.571, new Length(3.59, 6.93), 443, Rarity.Common);
	public static Fishes equula = new Fishes("equula", "갈전갱이", Material.SALMON, new Biomes[] { Biomes.Cave },
			Cycle.values(), Weather.Always, Size.Small, 0.718, new Length(11.18, 11.81), 210, Rarity.Common);
	public static Fishes merra = new Fishes("merra", "갈점바리", Material.SALMON, new Biomes[] { Biomes.Cave },
			Cycle.values(), Weather.Always, Size.Middle, 0.665, new Length(21.91, 41.99), 494, Rarity.Common);
	public static Fishes lepturus = new Fishes("lepturus", "갈치", Material.SALMON, new Biomes[] { Biomes.Cave },
			Cycle.values(), Weather.Always, Size.Big, 0.134, new Length(57.96, 85.52), 225, Rarity.Common);
	public static Fishes nigra = new Fishes("nigra", "감돌고기", Material.SALMON, new Biomes[] { Biomes.Cave },
			Cycle.values(), Weather.Always, Size.Biggst, 0.491, new Length(385.59, 487.19), 109, Rarity.Common);
	// // Uncommon

	// // Rare

	// // Epic
	/*
	 * 
	 */
	// NEUTRAL
	// // Trash
	public static Fishes quinquecirrha = new Fishes("quinquecirrha", "커튼원양해파리", Material.PUFFERFISH,
			new Biomes[] { Biomes.Neutral }, Cycle.values(), Weather.Always, Size.Small, 0.281,
			new Length(11.65, 14.49), 146, Rarity.Trash);
	public static Fishes aduncus = new Fishes("aduncus", "날개뿔고둥", Material.MUSHROOM_STEW,
			new Biomes[] { Biomes.Neutral }, Cycle.values(), Weather.Always, Size.Big, 0.079, new Length(84.10, 87.16),
			195, Rarity.Trash);
	public static Fishes lessonjana = new Fishes("lessonjana", "흰꼴뚜기", Material.INK_SAC,
			new Biomes[] { Biomes.Neutral }, Cycle.values(), Weather.Always, Size.Smallest, 0.741,
			new Length(2.74, 6.23), 128, Rarity.Trash);
	// // Common
	public static Fishes schleglii = new Fishes("schleglii", "감성돔", Material.SALMON, new Biomes[] { Biomes.Neutral },
			Cycle.values(), Weather.Always, Size.Smallest, 0.121, new Length(5.66, 6.33), 385, Rarity.Common);
	public static Fishes kamoharai = new Fishes("kamoharai", "강남상어", Material.SALMON, new Biomes[] { Biomes.Neutral },
			Cycle.values(), Weather.Always, Size.Small, 0.763, new Length(12.11, 19.06), 260, Rarity.Common);
	public static Fishes punctatus = new Fishes("punctatus", "강담돔", Material.SALMON, new Biomes[] { Biomes.Neutral },
			Cycle.values(), Weather.Always, Size.Middle, 0.261, new Length(33.92, 46.05), 410, Rarity.Common);
	public static Fishes sp__ = new Fishes("sp__", "강담복속", Material.SALMON, new Biomes[] { Biomes.Neutral },
			Cycle.values(), Weather.Always, Size.Big, 0.672, new Length(61.68, 86.07), 271, Rarity.Common);
	public static Fishes stellatus = new Fishes("stellatus", "강도다리", Material.SALMON, new Biomes[] { Biomes.Neutral },
			Cycle.values(), Weather.Always, Size.Biggst, 0.498, new Length(331.99, 461.08), 241, Rarity.Common);
	// // Uncommon

	// // Rare

	// // Epic
	/*
	 * 
	 */
	// NETHER
	// // Trash
	public static Fishes saltator = new Fishes("saltator", "무희나선꼬리해파리", Material.PUFFERFISH,
			new Biomes[] { Biomes.Nether }, Cycle.values(), Weather.Always, Size.Big, 0.281, new Length(51.48, 94.76),
			182, Rarity.Trash);
	public static Fishes japonicus = new Fishes("japonicus", "납작소라", Material.MUSHROOM_STEW,
			new Biomes[] { Biomes.Nether }, Cycle.values(), Weather.Always, Size.Middle, 0.913,
			new Length(41.73, 48.66), 161, Rarity.Trash);
	public static Fishes chinesis = new Fishes("chinesis", "한치꼴뚜기", Material.INK_SAC, new Biomes[] { Biomes.Nether },
			Cycle.values(), Weather.Always, Size.Middle, 0.808, new Length(22.14, 38.24), 182, Rarity.Trash);
	// // Common
	public static Fishes sculpin = new Fishes("sculpin", "개구리꺽정이", Material.SALMON, new Biomes[] { Biomes.Nether },
			Cycle.values(), Weather.Always, Size.Small, 0.833, new Length(3.56, 8.38), 251, Rarity.Common);
	public static Fishes mola = new Fishes("mola", "개복치", Material.SALMON, new Biomes[] { Biomes.Nether },
			Cycle.values(), Weather.Always, Size.Small, 0.826, new Length(15.20, 18.57), 298, Rarity.Common);
	public static Fishes pachycephalus = new Fishes("pachycephalus", "개볼락", Material.SALMON,
			new Biomes[] { Biomes.Nether }, Cycle.values(), Weather.Always, Size.Middle, 0.421,
			new Length(34.58, 49.11), 379, Rarity.Common);
	public static Fishes robustus = new Fishes("robustus", "개서대", Material.SALMON, new Biomes[] { Biomes.Nether },
			Cycle.values(), Weather.Always, Size.Big, 0.354, new Length(50.12, 68.72), 278, Rarity.Common);
	public static Fishes sole = new Fishes("sole", "개서대속", Material.SALMON, new Biomes[] { Biomes.Nether },
			Cycle.values(), Weather.Always, Size.Biggst, 0.808, new Length(), 182, Rarity.Common);
	// // Uncommon

	// // Rare

	// // Epic
	/*
	 * 
	 */
	// END
	// // Trash
	public static Fishes coerulsecens = new Fishes("coerulsecens", "평면해파리", Material.PUFFERFISH,
			new Biomes[] { Biomes.End }, Cycle.values(), Weather.Always, Size.Biggst, 0.281, new Length(255.88, 431.77),
			138, Rarity.Trash);
	public static Fishes striatus = new Fishes("striatus", "노란줄가리비", Material.MUSHROOM_STEW,
			new Biomes[] { Biomes.End }, Cycle.values(), Weather.Always, Size.Small, 0.196, new Length(10.29, 14.16),
			142, Rarity.Trash);
	public static Fishes pacificus = new Fishes("pacificus", "살오징어", Material.INK_SAC, new Biomes[] { Biomes.End },
			Cycle.values(), Weather.Always, Size.Big, 0.376, new Length(71.05, 88.09), 160, Rarity.Trash);
	// // Common
	public static Fishes goby = new Fishes("goby", "개소갱속", Material.SALMON, new Biomes[] { Biomes.End }, Cycle.values(),
			Weather.Always, Size.Small, 0.916, new Length(5.85, 7.09), 367, Rarity.Common);
	public static Fishes monoceros = new Fishes("monoceros", "객주리", Material.SALMON, new Biomes[] { Biomes.End },
			Cycle.values(), Weather.Always, Size.Smallest, 0.209, new Length(10.56, 11.41), 452, Rarity.Common);
	public static Fishes cavasius = new Fishes("cavasius", "갠지스동자개", Material.SALMON, new Biomes[] { Biomes.End },
			Cycle.values(), Weather.Always, Size.Middle, 0.990, new Length(36.86, 39.27), 250, Rarity.Common);
	public static Fishes cinereus = new Fishes("cinereus", "갯장어", Material.SALMON, new Biomes[] { Biomes.End },
			Cycle.values(), Weather.Always, Size.Big, 0.667, new Length(69.11, 71.11), 404, Rarity.Common);
	public static Fishes scapha = new Fishes("scapha", "거룻배가자미", Material.SALMON, new Biomes[] { Biomes.End },
			Cycle.values(), Weather.Always, Size.Biggst, 0.149, new Length(427.52, 472.56), 381, Rarity.Common);
	// // Uncommon

	// // Rare

	// // Epic

	// All
	// // Trash
	public static Fishes vertens = new Fishes("vertens", "꽃모자갈퀴손해파리", Material.PUFFERFISH, Biomes.values(),
			Cycle.values(), Weather.Always, Size.Smallest, 0.281, new Length(3.76, 6.98), 112, Rarity.Trash);
	public static Fishes constricta = new Fishes("constricta", "가리맛조개", Material.MUSHROOM_STEW, Biomes.values(),
			Cycle.values(), Weather.Always, Size.Small, 0.471, new Length(15.46, 16.04), 189, Rarity.Trash);
	public static Fishes berryi = new Fishes("berryi", "귀오징어불이", Material.INK_SAC, Biomes.values(), Cycle.values(),
			Weather.Always, Size.Biggst, 0.900, new Length(318.82, 390.87), 164, Rarity.Trash);
	// // Common
	public static Fishes immaculatus = new Fishes("immaculatus", "거북복", Material.SALMON, Biomes.values(),
			Cycle.values(), Weather.Always, Size.Smallest, 0.032, new Length(4.04, 6.06), 277, Rarity.Common);
	public static Fishes miraletus = new Fishes("miraletus", "거울가오리", Material.SALMON, Biomes.values(), Cycle.values(),
			Weather.Always, Size.Small, 0.875, new Length(14.24, 15.80), 455, Rarity.Common);
	public static Fishes porphyeus = new Fishes("porphyeus", "검복", Material.SALMON, Biomes.values(), Cycle.values(),
			Weather.Always, Size.Middle, 0.306, new Length(22.83, 38.50), 414, Rarity.Common);
	public static Fishes rubescens = new Fishes("rubescens", "검은새다래", Material.SALMON, Biomes.values(), Cycle.values(),
			Weather.Always, Size.Big, 0.758, new Length(53.77, 73.05), 484, Rarity.Common);
	public static Fishes crassilabris = new Fishes("crassilabris", "검은점촉수", Material.SALMON, Biomes.values(),
			Cycle.values(), Weather.Always, Size.Biggst, 0.790, new Length(215.58, 295.70), 366, Rarity.Common);
	// // Uncommon

	// // Rare

	// // Epic
	/*
	 * 
	 */
	private final String key;
	private final String name;
	private final Biomes[] biomes;
	private final Cycle[] cycles;
	private final Weather weather;
	private final Size size;
	private final double man_chance;
	private final Length length;
	private final long price;
	private final Rarity rarity;
	private final Material type;
	private final CustomAdvancements advancement;

	public Fishes(String key, String name, Material type, Biomes[] biomes, Cycle[] cycles, Weather weather, Size size,
			double man_chance, Length length, long price, Rarity rarity) {
		this.key = key;
		this.name = name;
		this.type = type;
		this.biomes = biomes;
		this.cycles = cycles;
		this.weather = weather;
		this.size = size;
		this.man_chance = man_chance;
		this.length = length;
		this.price = price;
		this.rarity = rarity;
		this.advancement = null;
		list.add(this);
		for (Biomes biome : biomes)
			biome.addFishes(this);
	}

	public Fishes(String key, String name, Material type, Biomes[] biomes, Cycle[] cycles, Weather weather, Size size,
			double man_chance, Length length, long price, Rarity rarity, CustomAdvancements advancement) {
		this.key = key;
		this.name = name;
		this.type = type;
		this.biomes = biomes;
		this.cycles = cycles;
		this.weather = weather;
		this.size = size;
		this.man_chance = man_chance;
		this.length = length;
		this.price = price;
		this.rarity = rarity;
		this.advancement = advancement;
		list.add(this);
		for (Biomes biome : biomes)
			biome.addFishes(this);
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public Material getType() {
		return type;
	}

	public Biomes[] getBiomes() {
		return biomes;
	}

	public Cycle[] getCycles() {
		return cycles;
	}

	public Weather getWeather() {
		return weather;
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

	public Rarity getRarity() {
		return rarity;
	}

	public CustomAdvancements getAdvancement() {
		return advancement;
	}

	public ItemStack getItemStack() {
		ItemStack i = new ItemStack(type);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(rarity.getColor() + name);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "크기 : " + size.getName());
		lore.add(ChatColor.WHITE + "희귀도 : " + rarity.getColor() + rarity.name());
		double len = length.getRandom();
		if (len > 0)
			lore.add(ChatColor.WHITE + "길이 : " + Math.ceil(len));
		double now_chance = new Random().nextDouble();
		if (man_chance > 0)
			lore.add(ChatColor.WHITE + "성별 : " + (now_chance < man_chance ? "수컷" : "암컷"));
		lore.add(ChatColor.WHITE + "가격 : "
				+ (long) (price * (1 + length.getPercent(len) * 3
						* (man_chance == 0.5 ? 1
								: (man_chance > 0.5 ? (now_chance < man_chance ? 2 - man_chance : 1)
										: (now_chance > man_chance ? 1 + man_chance : 1))))));

		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}

	public static Fishes getCaugthFish(Location loc, ItemStack rod) {
		World world = loc.getWorld();
		Weather now = Weather.getWeather(world.isClearWeather(), world.hasStorm(), world.isThundering());
		List<Fishes> canCaught = new ArrayList<Fishes>();
		int maxchance = 0;
		Size size = FishingrodItem.getSize(rod);
		for (Fishes fish : values())
			if (fish.weather.canCaught(now)) {
				boolean canCycle = false;
				for (Cycle cycle : fish.cycles)
					if (cycle.canCaught(world.getTime()))
						canCycle = true;
				if (canCycle && Biomes.canCaught(fish.biomes, loc.getBlock().getBiome())) {
					canCaught.add(fish);
					maxchance += fish.rarity.getChance() * (fish.size.equals(size) ? 2 : 1);
				}
			}
		if (canCaught.size() > 0) {
			Collections.sort(canCaught, new Compartors());
			int nowchance = 0;
			int ob_chance = new Random().nextInt(maxchance);
			for (Fishes fish : canCaught) {
				int r = fish.rarity.getChance() * (fish.size.equals(size) ? 2 : 1);
				if (nowchance <= ob_chance && (nowchance + r) > ob_chance) {
					return fish;
				} else
					nowchance += r;
			}
		}
		return null;
	}

	private static class Compartors implements Comparator<Fishes> {
		@Override
		public int compare(Fishes o1, Fishes o2) {
			return o2.rarity.getChance() - o1.rarity.getChance();
		}
	}

	private static enum Cycle {
		Dawn(22500, 300), Morning(0, 0), Day(0, 0), Midday(0, 0), Afternoon(0, 0), Dusk(0, 0), Night(0, 0),
		Midnight(0, 0)
		//
		;

		//
		private final long min;
		private final long max;

		private Cycle(long min, long max) {
			this.min = min;
			this.max = max;
		}

		public boolean canCaught(long now) {
			if (min == max) {
				return now == max;
			} else if (min < max) {
				return now >= min && now <= max;
			} else
				return now <= min || now >= max;
		}
	}

	public static class Length {
		private final double min;
		private final double max;

		public Length() {
			this.min = 0;
			this.max = 0;
		}

		public Length(double num) {
			this.min = num;
			this.max = num;
		}

		public Length(double min, double max) {
			this.min = min;
			this.max = max;
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
		Trash(ChatColor.GRAY, 96), Common(ChatColor.WHITE, 48), Uncommon(ChatColor.YELLOW, 24),
		Rare(ChatColor.AQUA, 12), Epic(ChatColor.LIGHT_PURPLE, 6), Treasure(ChatColor.AQUA, 3),
		Ominous(ChatColor.GOLD, 1),
		//
		;

		// 100~200
		// 200~500
		// 500~1000
		// 1000~2000
		// 2000~3000
		// 3000~4000
		// ?
		// ?
		private final ChatColor color;
		private final int chance;

		private Rarity(ChatColor color, int chance) {
			this.color = color;
			this.chance = chance;
		}

		public ChatColor getColor() {
			return color;
		}

		public int getChance() {
			return chance;
		}
	}

	public static enum Size {
		None("없음"), Smallest("초소형"), Small("소형"), Middle("중형"), Big("대형"), Biggst("초대형"),
		//
		;

		// 0
		// 2~10
		// 10~20
		// 20~50
		// 50~100
		// 100~500
		private final String name;

		private Size(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private enum Weather {
		Clear {
			@Override
			public boolean canCaught(Weather other) {
				return other.equals(Clear);
			}
		},
		Storm {
			@Override
			public boolean canCaught(Weather other) {
				return other.equals(Storm);
			}
		},
		Thunder {
			@Override
			public boolean canCaught(Weather other) {
				return other.equals(Thunder);
			}
		},
		Clear_Storm {
			@Override
			public boolean canCaught(Weather other) {
				return other.equals(Clear) || other.equals(Storm);
			}
		},
		Clear_Thunder {
			@Override
			public boolean canCaught(Weather other) {
				return other.equals(Clear) || other.equals(Thunder);
			}
		},
		Storm_Thunder {
			@Override
			public boolean canCaught(Weather other) {
				return other.equals(Storm) || other.equals(Thunder);
			}
		},
		Always {
			@Override
			public boolean canCaught(Weather other) {
				return other.equals(Clear) || other.equals(Storm) || other.equals(Thunder);
			}
		}
		//
		;

		public static Weather getWeather(boolean clear, boolean storm, boolean thunder) {
			if (clear) {
				return Clear;
			} else if (thunder) {
				return Thunder;
			} else
				return Storm;
		}

		public abstract boolean canCaught(Weather other);

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
