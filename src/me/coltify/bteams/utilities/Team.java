package me.coltify.bteams.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Team {

	private String teamName, teamLeader;
	private List<String> members, managers;
	private UFile teamFile;
	private Location hq;
	
	/**
	 * Get an existing team.
	 * @param teamName - Name of the team you want to get.
	 */
	public Team(String teamName) {
		if (!TeamManager.isTeam(teamName)) {
			System.out.println("[bTeams] Cannot get team '" + teamName + "' as it doesn't exist.");
			return;
		}
		teamFile = new UFile("plugins/bTeams/Teams/" + teamName + ".yml/");
		
		this.teamName = teamName;
		teamLeader = teamFile.getString("Leader");
		
		members = new ArrayList<String>();
		managers = new ArrayList<String>();
		
		// check if the team has any members 
		if (teamFile.getConfigurationSection("Members") != null) {
			// if so loop through the members and add them to the members list
			for (String member : teamFile.getConfigurationSection("Members").getKeys(false)) {
				members.add(member);
				// check if a member is a manager and add them to the managers list
				if (teamFile.getConfigurationSection("Members").getBoolean(member + ".Manager")) {
					managers.add(member);
				}
			}
		}
	}
	
	/**
	 * Create a new team.
	 * @param teamName - Name of the team.
	 * @param teamLeader - Leader of the team.
	 */
	public Team(String teamName, String teamLeader) {
		if (TeamManager.isTeam(teamName)) {
			System.out.println("[bTeams] Cannot create team '" + teamName + "' because a team with that name already exists.");
			return;
		}
		teamFile = new UFile("plugins/bTeams/Teams/" + teamName + ".yml/");
		teamFile.set("Leader", teamLeader);
		
		this.teamLeader = teamLeader;
		this.teamName = teamName;
		
		members = new ArrayList<String>();
		managers = new ArrayList<String>();
	}
	
	public void setLeader(String newLeader) {
		addMember(newLeader);
		teamFile.set("Leader", newLeader);
	}
	
	public void addMember(String member) {
		members.add(member);
		teamFile.getConfigurationSection("Members").set(member + ".Manager", false);
	}
	
	public boolean isMember(String member) {
		return members.contains(member);
	}
	
	public boolean isManager(String member) {
		if (!isMember(member)) {
			return false;
		}
		return teamFile.getConfigurationSection("Members").getBoolean(member + ".Manager");
	}
	
	public boolean isLeader(String leader) {
		return teamLeader.equalsIgnoreCase(leader);
	}
	
	public void kickMember(String member) {
		if (!isMember(member)) {
			return;
		}
		members.remove(member);
		teamFile.getConfigurationSection("Members").set(member, null);
	}
	
	public void promoteMember(String member) {
		if (!isMember(member)) {
			return;
		}
		teamFile.getConfigurationSection("Members").set(member + ".Manager", true);
	}
	
	public void disband() {
		teamFile.delete();
	}
	
	public boolean hasHQ() {
		return hq != null;
	}
	
	public Location getHQ() {
		if (!hasHQ()) {
			return null;
		}
		return hq;
	}
	
	public void setHQ(Location hq) {
		this.hq = hq;
		teamFile.set("HQ.World", hq.getWorld().getName());
		teamFile.set("HQ.X", hq.getX());
		teamFile.set("HQ.Y", hq.getY());
		teamFile.set("HQ.Z", hq.getZ());
		teamFile.set("HQ.YAW", hq.getYaw());
		teamFile.set("HQ.PITCH", hq.getPitch());
	}
	
	public List<Player> getOnlineMembers() {
		List<Player> onlineMembers = new ArrayList<Player>();
		for (String memberName : getMembers()) {
			if (Bukkit.getPlayer(memberName) != null) {
				onlineMembers.add(Bukkit.getPlayer(memberName));
			}
		}
		return onlineMembers;
	}
	
	public void alertTeam(String message) {
		for (Player player : getOnlineMembers()) {
			player.sendMessage(message);
		}
		if (Bukkit.getPlayer(teamLeader) == null) {
			return;
		}
		Bukkit.getPlayer(teamLeader).sendMessage(message);
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public String getTeamLeader() {
		return teamLeader;
	}
	
	public List<String> getManagers() {
		return managers;
	}
	
	public List<String> getMembers() {
		return members;
	}
	
	public UFile getTeamFile() {
		return teamFile;
	}
}
