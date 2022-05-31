package com.StarJ.LA.Systems;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.StarJ.LA.Items.JewerlyItems;

public enum Stats {
	Speed("신속") {
		@Override
		public double getStatPercent(Player player) {
			double stat = getStat(player);
			return 1 - Math.min(0.45d, stat * 0.00025 + getImportantStat(player));
		}
	},
	Specialization("특화") {
		@Override
		public double getStatPercent(Player player) {
			double stat = getStat(player);
			return 1 + Math.min(0.9d, stat * 0.0005 + getImportantStat(player));
		}
	},
	Critical("치명") {
		@Override
		public double getStatPercent(Player player) {
			double stat = getStat(player);
			return Math.min(0.9d, stat * 0.0005 + getImportantStat(player));
		}
	},
	Enduration("인내") {
		@Override
		public double getStatPercent(Player player) {
			double stat = getStat(player);
			Jobs job = ConfigStore.getJob(player);
			return 1 - Math.min(0.9d,
					stat * 0.0005 + getImportantStat(player) + (job != null ? job.getReduceDamage(player) : 0));
		}
	}
	//
	;

	private final HashMap<UUID, Double> add = new HashMap<UUID, Double>();
	private final String displayname;

	private Stats(String displayname) {
		this.displayname = displayname;
	}

	public String getDisplayname() {
		return displayname;
	}

	public abstract double getStatPercent(Player player);

	public void setImportantStat(Player player, double stat) {
		add.put(player.getUniqueId(), stat);
	}

	public double getImportantStat(Player player) {
		return add.containsKey(player.getUniqueId()) ? add.get(player.getUniqueId()) : 0d;
	}

	public void removeImportantStat(Player player) {
		add.remove(player.getUniqueId());
	}

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
		player.sendMessage(Stats.Critical.getStatPercent(player) + 0.01 + "");
		return (Stats.Critical.getStatPercent(player) + 0.01) > new Random().nextDouble();
	}
}
