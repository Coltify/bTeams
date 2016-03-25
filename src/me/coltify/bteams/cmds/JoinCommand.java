package me.coltify.bteams.cmds;

import me.coltify.bteams.api.PlayerJoinTeamEvent;
import me.coltify.bteams.cmdmanager.TeamCommand;
import me.coltify.bteams.cmdmanager.TeamCommandInfo;
import me.coltify.bteams.utilities.InvitationManager;
import me.coltify.bteams.utilities.MessageManager;
import me.coltify.bteams.utilities.Team;
import me.coltify.bteams.utilities.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@TeamCommandInfo(aliases = { "join" }, description = "Join a team.", usage = "<team>")
public class JoinCommand extends TeamCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		if (args.length == 0) {
			MessageManager.sendMessage(player, MessageManager.getMessage("TEAM-NOT-SPECIFIED"));
			return;
		}
		String teamName = args[0];
		if (TeamManager.hasTeam(player)) {
			MessageManager.sendMessage(player, MessageManager.getMessage("CANT-JOIN-TEAM"));
			return;
		}
		if (!TeamManager.isTeam(teamName)) {
			MessageManager.sendMessage(player, MessageManager.getMessage("TEAM-DOESNT-EXIST"));
			return;
		}
		Team team = new Team(teamName);
		if (!InvitationManager.isInvited(player, team)) {
			MessageManager.sendMessage(player, MessageManager.getMessage("NOT-INVITED"));
			return;
		}
		PlayerJoinTeamEvent event = new PlayerJoinTeamEvent(player, team);
		Bukkit.getServer().getPluginManager().callEvent(event);
		
		if (!event.isCancelled()) {
			team.addMember(player.getName());
			team.alertTeam(MessageManager.getMessage("PLAYER-JOIN-TEAM-ALERT").replace("<player>", player.getName()));
		}
	}
}
