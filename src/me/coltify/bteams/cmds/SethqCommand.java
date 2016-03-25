package me.coltify.bteams.cmds;

import me.coltify.bteams.api.PlayerSetHQEvent;
import me.coltify.bteams.cmdmanager.TeamCommand;
import me.coltify.bteams.cmdmanager.TeamCommandInfo;
import me.coltify.bteams.utilities.Team;
import me.coltify.bteams.utilities.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@TeamCommandInfo(aliases = { "sethq" }, description = "Set the team HQ.", usage = "")
public class SethqCommand extends TeamCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		if (!TeamManager.hasTeam(player)) {
			player.sendMessage("§7You are not in a team.");
			return;
		}
		Team team = TeamManager.getPlayerTeam(player);
		if (!team.isManager(player.getName()) || !team.isLeader(player.getName())) {
			player.sendMessage("§7Only team managers or the team leader can set the HQ.");
			return;
		}
		Location newHQ = player.getLocation();
		
		PlayerSetHQEvent event = new PlayerSetHQEvent(player, newHQ, team);
		Bukkit.getServer().getPluginManager().callEvent(event);
		
		if (!event.isCancelled()) {
			team.setHQ(event.getNewHQ());
			team.alertTeam("§b" + player.getName() + "§7 has updated the team HQ location.");
		}
	}
}
