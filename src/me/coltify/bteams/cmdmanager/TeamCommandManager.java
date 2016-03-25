package me.coltify.bteams.cmdmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import me.coltify.bteams.Main;
import me.coltify.bteams.cmds.CreateCommand;
import me.coltify.bteams.cmds.DisbandCommand;
import me.coltify.bteams.cmds.HelpCommand;
import me.coltify.bteams.cmds.InfoCommand;
import me.coltify.bteams.cmds.JoinCommand;
import me.coltify.bteams.cmds.KickCommand;
import me.coltify.bteams.cmds.LeaveCommand;
import me.coltify.bteams.cmds.PromoteCommand;
import me.coltify.bteams.cmds.SethqCommand;
import me.coltify.bteams.utilities.Team;
import me.coltify.bteams.utilities.TeamManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommandManager implements CommandExecutor {

	public static ArrayList<TeamCommand> cmds;

	public TeamCommandManager() {
		Main.registerCommand("team", this);
		cmds = new ArrayList<TeamCommand>();
		cmds.add(new HelpCommand());
		cmds.add(new CreateCommand());
		cmds.add(new DisbandCommand());
		cmds.add(new JoinCommand());
		cmds.add(new LeaveCommand());
		cmds.add(new PromoteCommand());
		cmds.add(new SethqCommand());
		cmds.add(new KickCommand());
		cmds.add(new InfoCommand());
		
		int index = 0;
		for (@SuppressWarnings("unused") Team team : TeamManager.getTeams()) {
			index++;
		}
		System.out.println("[bTeams] Loaded " + index + " Teams.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Sorry! only players can use this command.");
			return true;
		}
		Player player = (Player) sender;
		if (args.length == 0) {
			player.sendMessage("§7Unknown Command. Type §b/team help §7for help.");
			return true;
		}
		TeamCommand command = null;
		for (TeamCommand cm : cmds) {
			TeamCommandInfo info = (TeamCommandInfo) cm.getClass().getAnnotation(TeamCommandInfo.class);
			for (String aliases : info.aliases()) {
				if (aliases.equals(args[0])) {
					command = cm;
					break;
				}
			}
		}
		if (command == null) {
			player.sendMessage("§7Unknown Command. Type §b/team help §7for help.");
			return true;
		}
		Vector<String> a = new Vector<String>(Arrays.asList(args));
		a.remove(0);
		args = (String[]) a.toArray(new String[a.size()]);

		command.onCommand(player, args);
		return false;
	}
}
