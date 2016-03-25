package me.coltify.bteams.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class TeamManager {
	
	public static Team getTeam(String teamName) {
		if (!isTeam(teamName)) {
			return null;
		}
		return new Team(teamName);
	}
	
	public static boolean isTeam(String teamName) {
		for (Team team : getTeams()) {
			if (team.getTeamName().equalsIgnoreCase(teamName)) {
				return true;
			}
		}
		return false;
	}

	public static List<Team> getTeams() {
		List<Team> teams = new ArrayList<Team>();
		File folder = new File("plugins/bTeams/Teams");
		if (!folder.exists()) {
			System.out.println("[bTeams] Teams directory not found. Creating a new one..");
			folder.mkdir();
		}
		if (folder.exists()) {
			File[] files = folder.listFiles();

			for (File file : files) {
				String name = file.getName();
				teams.add(new Team(name.replace(".yml", "")));
			}
		}
		
		return teams;
	}
	
	public static boolean hasTeam(Player player) {
		for (Team team : getTeams()) {
			if (team.getMembers().contains(player.getName()) || team.getTeamLeader().equalsIgnoreCase(player.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasTeam(String player) {
		for (Team team : getTeams()) {
			if (team.getMembers().contains(player) || team.getTeamLeader().equalsIgnoreCase(player)) {
				return true;
			}
		}
		return false;
	}
	
	public static Team getPlayerTeam(Player player) {
		for (Team team : getTeams()) {
			if (team.getMembers().contains(player.getName()) || team.getTeamLeader().equalsIgnoreCase(player.getName())) {
				return team;
			}
		}
		return null;
	}
	
	public static Team getPlayerTeam(String player) {
		for (Team team : getTeams()) {
			if (team.getMembers().contains(player) || team.getTeamLeader().equalsIgnoreCase(player)) {
				return team;
			}
		}
		return null;
	}
	
	private static String getOnlineColor(String player) {
		return (Bukkit.getPlayer(player) == null ? "§7" : "§b");
	}
	
	public static void showInfo(Player player, Team team) {
		String leader = (Bukkit.getPlayer(team.getTeamLeader()) == null ? "§7" : "§b") + team.getTeamLeader();
		player.sendMessage("§7Team §b" + team.getTeamName());
		player.sendMessage("§7Leader: " + leader);
		
		if (!team.getMembers().isEmpty()) {
			StringBuilder members = new StringBuilder();
			StringBuilder managers = new StringBuilder();
			boolean foundManager = false;
			boolean foundMember = false;
			for (String member : team.getMembers()) {
				if (team.isManager(member)) {
					foundManager = true;
					managers.append(getOnlineColor(member) + member + "§7, ");
				} else {
					foundMember = true;
					members.append(getOnlineColor(member) + member + "§7, ");
				}
			}
			if (!foundManager) {
				player.sendMessage("§7Managers: None");
			} else {
				player.sendMessage("§7Managers: " + managers.toString().substring(0, managers.length() - ", ".length()));
			}
			if (!foundMember) {
				player.sendMessage("§7Members: None");
			} else {
				player.sendMessage("§7Members: " + members.toString().substring(0, members.length() - ", ".length()));
			}
		} else {
			player.sendMessage("§7Managers: None");
			player.sendMessage("§7Members: None");	
		}
	}
}
