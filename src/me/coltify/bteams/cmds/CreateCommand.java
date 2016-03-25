package me.coltify.bteams.cmds;

import me.coltify.bteams.api.TeamCreateEvent;
import me.coltify.bteams.cmdmanager.TeamCommand;
import me.coltify.bteams.cmdmanager.TeamCommandInfo;
import me.coltify.bteams.utilities.MessageManager;
import me.coltify.bteams.utilities.Team;
import me.coltify.bteams.utilities.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@TeamCommandInfo(aliases = { "create", "c" }, description = "Create a team.", usage = "<name>")
public class CreateCommand extends TeamCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		if (args.length == 0) {
			MessageManager.sendMessage(player, MessageManager.getMessage("TEAM-NOT-SPECIFIED"));
			return;
		}
		String teamName = args[0];
		if (TeamManager.hasTeam(player)) {
			MessageManager.sendMessage(player, MessageManager.getMessage("CANT-CREATE-TEAM"));
			return;
		}
		if (!teamName.matches("^[a-zA-Z0-9]*$")) {
			MessageManager.sendMessage(player, MessageManager.getMessage("TEAM-NAME-ERROR"));
			return;
		}
		if (TeamManager.isTeam(teamName)) {
			MessageManager.sendMessage(player, MessageManager.getMessage("TEAM-NAME-TAKEN"));
			return;
		}
		TeamCreateEvent event = new TeamCreateEvent(teamName);
		Bukkit.getServer().getPluginManager().callEvent(event);
		
		if (!event.isCancelled()) {
			System.out.println("[bTeams] Creating team '" + event.getTeamName() +  " with leader '" + player.getName() + "'.");
			Team team = new Team(event.getTeamName(), player.getName());
			MessageManager.sendMessage(player, MessageManager.getMessage("CREATED-TEAM").replace("<team>", team.getTeamName()));
		}
	}
}
