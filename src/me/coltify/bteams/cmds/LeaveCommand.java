package me.coltify.bteams.cmds;

import me.coltify.bteams.api.PlayerLeaveTeamEvent;
import me.coltify.bteams.api.PlayerLeaveTeamEvent.Reason;
import me.coltify.bteams.cmdmanager.TeamCommand;
import me.coltify.bteams.cmdmanager.TeamCommandInfo;
import me.coltify.bteams.utilities.Team;
import me.coltify.bteams.utilities.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@TeamCommandInfo(aliases = { "leave" }, description = "Leave your team.", usage = "")
public class LeaveCommand extends TeamCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		if (!TeamManager.hasTeam(player)) {
			player.sendMessage("§7You are not in a team.");
			return;
		}
		Team team = TeamManager.getPlayerTeam(player);
		if (team.isLeader(player.getName())) {
			player.sendMessage("§7To leave your team you must select a new leader.");
			player.sendMessage("§7Do §b/team setleader <member> §7to select a new team leader.");
			return;
		}
		PlayerLeaveTeamEvent event = new PlayerLeaveTeamEvent(player.getName(), team, Reason.PERSONAL);
		Bukkit.getServer().getPluginManager().callEvent(event);
		
		if (!event.isCancelled()) {
			player.sendMessage("§7You have left team §b" + team.getTeamName());
			team.kickMember(player.getName());
			team.alertTeam("§b" + player.getName() + "§7 has left the team.");
		}
	}
}
