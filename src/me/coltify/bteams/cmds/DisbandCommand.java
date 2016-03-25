package me.coltify.bteams.cmds;

import me.coltify.bteams.api.TeamDisbandEvent;
import me.coltify.bteams.cmdmanager.TeamCommand;
import me.coltify.bteams.cmdmanager.TeamCommandInfo;
import me.coltify.bteams.utilities.MessageManager;
import me.coltify.bteams.utilities.Team;
import me.coltify.bteams.utilities.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@TeamCommandInfo(aliases = { "disband" }, description = "Disband your team.", usage = "")
public class DisbandCommand extends TeamCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		if (!TeamManager.hasTeam(player)) {
			MessageManager.sendMessage(player, MessageManager.getMessage("NOT-IN-TEAM"));
			return;
		}
		Team team = TeamManager.getPlayerTeam(player); 
		if (!team.isLeader(player.getName())) {
			MessageManager.sendMessage(player, MessageManager.getMessage("CANT-DISBAND-TEAM"));
			return;
		}
		TeamDisbandEvent event = new TeamDisbandEvent(team);
		Bukkit.getServer().getPluginManager().callEvent(event);
		
		if (!event.isCancelled()) {
			MessageManager.sendMessage(player, MessageManager.getMessage("DISBANDED-TEAM"));
			for (Player member : team.getOnlineMembers()) {
				MessageManager.sendMessage(member, MessageManager.getMessage("TEAM-DISBAND-ALERT"));
			}
			team.disband();
		}
	}
}
