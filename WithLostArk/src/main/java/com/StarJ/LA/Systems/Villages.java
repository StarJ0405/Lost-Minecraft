package com.StarJ.LA.Systems;

import org.bukkit.Location;

public class Villages {
	private final String name;
	private final Location loc;

	public Villages(String name, Location loc) {
		this.loc = loc;
		this.name = name;
	}

	public Location getLocation() {
		return this.loc;
	}

	public String getName() {
		return this.name;
	}
}
