package com.StarJ.LA.Systems;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.StarJ.LA.Systems.Fishes.Biomes;

public class AdvancementStore {
	private static HashMap<String, Integer> caught = new HashMap<String, Integer>();
	private static HashMap<String, HashMap<Fishes, Integer>> caught_fishes = new HashMap<String, HashMap<Fishes, Integer>>();
	private static HashMap<String, HashMap<Biomes, Integer>> caught_biomes = new HashMap<String, HashMap<Biomes, Integer>>();

	public static void setCaught(Player player, int count) {
		String key = player.getUniqueId().toString();
		if (CustomAdvancements.fishing.isDone(count))
			if (CustomAdvancements.fishing.has(player)) {
				CustomAdvancements.fishing.grant(player);
			} else
				CustomAdvancements.fishing.grantAndReward(player);
		caught.put(key, count);
	}

	public static int getCaught(Player player) {
		String key = player.getUniqueId().toString();
		if (!caught.containsKey(key))
			caught.put(key, 0);
		return caught.get(key);
	}

	public static void setCaughtFishes(Player player, Fishes fish, int count) {
		String key = player.getUniqueId().toString();
		if (!caught_fishes.containsKey(key))
			caught_fishes.put(key, new HashMap<Fishes, Integer>());
		HashMap<Fishes, Integer> hs = caught_fishes.get(key);
		if (fish.getAdvancement() != null && fish.getAdvancement().isDone(count))
			if (fish.getAdvancement().has(player)) {
				fish.getAdvancement().grant(player);
			} else
				fish.getAdvancement().grantAndReward(player);
		hs.put(fish, count);
		caught_fishes.put(key, hs);
	}

	public static int getCaughtFishes(Player player, Fishes fish) {
		String key = player.getUniqueId().toString();
		if (!caught_fishes.containsKey(key))
			caught_fishes.put(key, new HashMap<Fishes, Integer>());
		HashMap<Fishes, Integer> hs = caught_fishes.get(key);
		if (!hs.containsKey(fish))
			hs.put(fish, 0);
		return hs.get(fish);
	}

	public static void setCaughtBiomes(Player player, Biomes biome, int count) {
		String key = player.getUniqueId().toString();
		if (!caught_biomes.containsKey(key))
			caught_biomes.put(key, new HashMap<Biomes, Integer>());
		HashMap<Biomes, Integer> hs = caught_biomes.get(key);
		if (biome.getAdvancement() != null && biome.getAdvancement().isDone(count))
			if (biome.getAdvancement().has(player)) {
				biome.getAdvancement().grant(player);
			} else
				biome.getAdvancement().grantAndReward(player);
		hs.put(biome, count);
		caught_biomes.put(key, hs);
	}

	public static int getCaughtBiomes(Player player, Biomes biome) {
		String key = player.getUniqueId().toString();
		if (!caught_biomes.containsKey(key))
			caught_biomes.put(key, new HashMap<Biomes, Integer>());
		HashMap<Biomes, Integer> hs = caught_biomes.get(key);
		if (!hs.containsKey(biome))
			hs.put(biome, 0);
		return hs.get(biome);
	}

	public static void confirmBiomes(Player player) {
		int count = 0;
		for (Biomes biome : Biomes.values())
			if (getCaughtBiomes(player, biome) > 0)
				count++;
		if (CustomAdvancements.fishing_biome.isDone(count))
			if (CustomAdvancements.fishing_biome.has(player)) {
				CustomAdvancements.fishing_biome.grant(player);
			} else
				CustomAdvancements.fishing_biome.grantAndReward(player);
	}

	public static void confirmBiomesType(Player player, Biomes biome) {
		int count = 0;
		for (Fishes fish : biome.getFishes())
			if (getCaughtFishes(player, fish) > 0)
				count++;
		if (biome.getAdvancement_type() != null && biome.getAdvancement_type().isDone(count))
			if (biome.getAdvancement().has(player)) {
				biome.getAdvancement().grant(player);
			} else
				biome.getAdvancement().grantAndReward(player);
	}
}
