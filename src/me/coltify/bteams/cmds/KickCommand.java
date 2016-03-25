package me.coltify.bteams.cmds;

import me.coltify.bteams.api.PlayerLeaveTeamEvent;
import me.coltify.bteams.api.PlayerLeaveTeamEvent.Reason;
import me.coltify.bteams.cmdmanager.TeamCommand;
import me.coltify.bteams.cmdmanager.TeamCommandInfo;
import me.coltify.bteams.utilities.MessageManager;
import me.coltify.bteams.utilities.Settings;
import me.coltify.bteams.utilities.Team;
import me.coltify.bteams.utilities.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@TeamCommandInfo(aliases = { "kick", "k" }, description = "Kick a player from your team.", usage = "<player>")
public class KickCommand extends TeamCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		if (args.length == 0) {
			MessageManager.sendMessage(player, MessageManager.getMessage("PLAYER-NOT-SPECIFIED"));
			return;
		}
		String target = args[0];
		if (!TeamManager.hasTeam(player)) {
			MessageManager.sendMessage(player, MessageManager.getMessage("NOT-IN-TEAM"));
			return;
		}
		Team team = TeamManager.getPlayerTeam(player);
		if (!team.isMember(target)) {
			player.sendMessage("§b" + target + "§7 is not a member of team §b" + team.getTeamName() + "§7.");
			return;
		}
		if (!team.isManager(player.getName()) || !team.isLeader(player.getName())) {
			player.sendMessage("§7Only managers or the team leader can kick members.");
			return;
		}
		if (!Settings.canMKM) {
			if (team.isManager(target)) {
				player.sendMessage("§7Only the team leader can kick team managers.");
				return;
			}
		}
		PlayerLeaveTeamEvent event = new PlayerLeaveTeamEvent(target, team, Reason.KICKED);
		Bukkit.getServer().getPluginManager().callEvent(event);
		
		if (!event.isCancelled()) {
			if (Bukkit.getPlayer(target) != null) {
				Bukkit.getPlayer(target).sendMessage("§7You were kicked from team §b" + team.getTeamName() + "§7 by §b" + player.getName() + "§7.");
			}
			team.kickMember(target);
			team.alertTeam("§b" + target + "§7 has was kicked from the team by §b" + player.getName() + "§7.");
		}
	}
}
