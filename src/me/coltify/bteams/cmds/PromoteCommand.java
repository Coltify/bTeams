package me.coltify.bteams.cmds;

import me.coltify.bteams.api.PlayerPromotePlayerEvent;
import me.coltify.bteams.cmdmanager.TeamCommand;
import me.coltify.bteams.cmdmanager.TeamCommandInfo;
import me.coltify.bteams.utilities.Team;
import me.coltify.bteams.utilities.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@TeamCommandInfo(aliases = { "promote" }, description = "Show the help page.", usage = "<player>")
public class PromoteCommand extends TeamCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage("§7Member not specified.");
			return;
		}
		String target = args[0];
		if (!TeamManager.hasTeam(player)) {
			player.sendMessage("§7You are not in a team.");
			return;
		}
		Team team = TeamManager.getPlayerTeam(player);
		if (!team.isMember(target)) {
			player.sendMessage("§b" + target + "§7 is not a member of team §b" + team.getTeamName() + "§7.");
			return;
		}
		if (!team.isManager(player.getName()) || !team.isLeader(player.getName())) {
			player.sendMessage("§7Only managers or the team leader can promote members.");
			return;
		}
		if (team.isManager(target)) {
			player.sendMessage("§b" + target + "§7 is already a team manager.");
			return;
		}
		PlayerPromotePlayerEvent event = new PlayerPromotePlayerEvent(player, target, team);
		Bukkit.getServer().getPluginManager().callEvent(event);
		
		if (!event.isCancelled()) {
			team.promoteMember(target);
			team.alertTeam("§b" + player.getName() + "§7 promoted §b" + target + "§7 to a team manager.");
			if (Bukkit.getPlayer(target) != null) {
				Bukkit.getPlayer(target).sendMessage("§7As a team manager you can now:");
				Bukkit.getPlayer(target).sendMessage("§71. §bKick members from the team.");
				Bukkit.getPlayer(target).sendMessage("§72. §bToggle friendly fire.");
				Bukkit.getPlayer(target).sendMessage("§73. §bSet the team's HQ.");
			}
		}
	}
}
