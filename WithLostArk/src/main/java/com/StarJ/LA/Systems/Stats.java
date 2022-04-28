package com.StarJ.LA.Systems;

import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import com.StarJ.LA.Items.JewerlyItems;

public enum Stats {
	Speed("신속") {
		@Override
		public double getStatPercent(double stat) {
			return 1 - (stat > 1800 ? 0.45 : stat * 0.00025);
		}
	},
	Specialization("특화") {
		@Override
		public double getStatPercent(double stat) {
			return 1 + (stat > 1800 ? 0.9 : stat * 0.0005);
		}
	},
	Critical("치명") {
		@Override
		public double getStatPercent(double stat) {
			return stat > 1800 ? 0.9 : stat * 0.0005;
		}
	},
	Enduration("인내") {
		@Override
		public double getStatPercent(double stat) {
			return 1 - (stat > 1800 ? 0.9 : stat * 0.0005);
		}
	}
	//
	;

	private final String displayname;

	private Stats(String displayname) {
		this.displayname = displayname;
	}

	public String getDisplayname() {
		return displayname;
	}

	public double getStatPercent(Player player) {
		return getStatPercent(getStat(player));
	}

	public abstract double getStatPercent(double stat);

	public double getStat(Player player) {
		return getStat(player, ConfigStore.getJob(player));
	}

	public double getStat(Player player, Jobs job) {
		double sum = 0.0d;
		if (job != null) {
			List<JewerlyItems> list = ConfigStore.getJewerlyItems(player, job);
			for (JewerlyItems jewerly : list)
				if (jewerly.getStat() != null && jewerly.getStat().equals(this))
					sum += jewerly.getRank().getStat();
		}
		return sum;
	}

	public static double getAllStats(Player player) {
		return getAllStats(player, ConfigStore.getJob(player));
	}

	public static double getAllStats(Player player, Jobs job) {
		double sum = 0;
		for (Stats stat : values())
			sum += stat.getStat(player, job);
		return sum;
	}

	public static boolean isCritical(Player player) {
		return (Stats.Critical.getStat(player) + 0.01) > new Random().nextDouble();
	}
}
