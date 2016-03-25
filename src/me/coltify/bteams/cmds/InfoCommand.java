package me.coltify.bteams.cmds;

import me.coltify.bteams.cmdmanager.TeamCommand;
import me.coltify.bteams.cmdmanager.TeamCommandInfo;
import me.coltify.bteams.utilities.MessageManager;
import me.coltify.bteams.utilities.Team;
import me.coltify.bteams.utilities.TeamManager;

import org.bukkit.entity.Player;

@TeamCommandInfo(aliases = { "info", "i" }, description = "Inspect a team.", usage = "<team/player>")
public class InfoCommand extends TeamCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		if (args.length == 0) {
			if (!TeamManager.hasTeam(player)) {
				MessageManager.sendMessage(player, MessageManager.getMessage("NOT-IN-TEAM"));
				return;
			} 
			TeamManager.showInfo(player, TeamManager.getPlayerTeam(player));
			return;
		}
		String entry = args[0];
		boolean hasResult = false;
		if (TeamManager.isTeam(entry)) {
			TeamManager.showInfo(player, new Team(entry));
			hasResult = true;
		} 
		if (TeamManager.hasTeam(entry)) {
			TeamManager.showInfo(player, TeamManager.getPlayerTeam(entry));
			hasResult = true;
		}
		if (!hasResult) {
			MessageManager.sendMessage(player, MessageManager.getMessage("INVALID-TEAM-OR-PLAYER-SPECIFIED"));
		}
	}
}
