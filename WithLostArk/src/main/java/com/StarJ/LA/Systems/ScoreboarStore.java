package com.StarJ.LA.Systems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreboarStore {
	private static ScoreboardManager sbm = Bukkit.getScoreboardManager();
	private static Scoreboard sb = createScoreboard();
	private static Team team;

	private static Scoreboard createScoreboard() {
		Scoreboard sb = sbm.getNewScoreboard();
		Objective obj = sb.registerNewObjective("health", "health", ChatColor.RED + "♥");
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		team = sb.registerNewTeam("main");
		team.setCanSeeFriendlyInvisibles(true);
		return sb;
	}

	public static void join(Player player) {
		player.setScoreboard(sb);
	}

	public static void confirmTeam(Player player) {
		String key = player.getName();
		if (ConfigStore.getPlayerStatus(player)) {
			if (ConfigStore.getPVP(player)) {
				if (team.hasEntry(key))
					team.removeEntry(key);
			} else if (!team.hasEntry(key))
				team.addEntry(key);
		} else if (!team.hasEntry(key))
			team.addEntry(key);
	}

}
