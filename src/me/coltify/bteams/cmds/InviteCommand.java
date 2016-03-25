package me.coltify.bteams.cmds;

import me.coltify.bteams.cmdmanager.TeamCommand;
import me.coltify.bteams.cmdmanager.TeamCommandInfo;
import me.coltify.bteams.utilities.InvitationManager;
import me.coltify.bteams.utilities.MessageManager;
import me.coltify.bteams.utilities.Team;
import me.coltify.bteams.utilities.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@TeamCommandInfo(aliases = { "invite" }, description = "Invite a players to your team.", usage = "<player>")
public class InviteCommand extends TeamCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		if (args.length == 0) {
			MessageManager.sendMessage(player, MessageManager.getMessage("PLAYER-NOT-SPECIFIED"));
			return;
		}
		if (!TeamManager.hasTeam(player)) {
			MessageManager.sendMessage(player, MessageManager.getMessage("NOT-IN-TEAM"));
			return;
		}
		Team team = TeamManager.getPlayerTeam(player);
		if (!team.isManager(player.getName()) || !team.isLeader(player.getName())) {
			MessageManager.sendMessage(player, MessageManager.getMessage("CANT-INVITE-TO-TEAM"));
			return;
		}
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			MessageManager.sendMessage(player, MessageManager.getMessage("PLAYER-NOT-ONLINE").replace("<player>", args[0]));
			return;
		}
		if (!InvitationManager.canInvite(player, team)) {
			MessageManager.sendMessage(player, MessageManager.getMessage("INVITE-COOLDOWN").replace("<player>", target.getName()));
			return;
		}
	}
}
