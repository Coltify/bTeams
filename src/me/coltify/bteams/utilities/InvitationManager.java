package me.coltify.bteams.utilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.coltify.bteams.Main;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InvitationManager {
	
	private static HashMap<String, List<String>> map = new HashMap<String, List<String>>();
	
	public static boolean invite(Player player, Team team) {	
		boolean should = false;
		if (getCurrentInvitations(team) != null) {
			if (!getCurrentInvitations(team).contains(player.getName())) {
				should = true;
			} else {
				should = false;
			}
		} else {
			should = true;
		}
		if (should) {
			if (getCurrentInvitations(team) == null) {
				map.put(team.getTeamName(), Arrays.asList(player.getName()));
			} else {
				List<String> list = getCurrentInvitations(team);
				list.add(player.getName());
				map.put(team.getTeamName(), list);
			}
			startTimer(player.getName(), team);
		}
		return should;
	}
	
	/**
	 * @param player - Player to invite to team.
	 * @param team - The Team inviting the player.
	 * @return if a Team can invite a player to join their team.
	 */
	public static boolean canInvite(Player player, Team team) {
		if (getCurrentInvitations(team) == null) {
			return false;
		}
		return !getCurrentInvitations(team).contains(player.getName());
	}
 	 
	public static boolean isInvited(Player player, Team team) {
		return !canInvite(player, team);
	}
	
	private static void startTimer(String player, Team team) {
		new BukkitRunnable() {
			
			int i = 60;
			
			@Override
			public void run() {
				if (getCurrentInvitations(team) == null) {
					cancel();
				} else {
					if (!getCurrentInvitations(team).contains(player)) {
						cancel();
					} else {
						if (i == 0) {
							map.get(team.getTeamName()).remove(player);
							cancel();
						} else {
							i--;
						}
					}
				}
			}
		}.runTaskTimer(Main.getInstance(), 20, 20);
	}
	
	/**
	 * @param team
	 * @return a list of players the team has invited in the last 60 seconds. 
	 */
	private static List<String> getCurrentInvitations(Team team) {
		if (!map.containsKey(team)) {
			return null;
		}
		return map.get(team.getTeamName());
	}
}
