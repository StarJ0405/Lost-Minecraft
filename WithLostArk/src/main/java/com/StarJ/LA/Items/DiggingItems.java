package com.StarJ.LA.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class DiggingItems extends Items {
	private final static List<DiggingItems> list = new ArrayList<DiggingItems>();
	//
	private final double chance;

	public DiggingItems(String key, Material type, ChatColor color, double chance) {
		super("BASICS_DIGGING", key, type, color);
		this.chance = chance;
		list.add(this);
	}

	public double getChance() {
		return chance;
	}

	public boolean canGet(double add) {
		return new Random().nextDouble() < (chance + add);
	}

	public static List<DiggingItems> getDiggingItems() {
		return list;
	}
}
