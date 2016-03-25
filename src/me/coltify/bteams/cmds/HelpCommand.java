package me.coltify.bteams.cmds;

import me.coltify.bteams.cmdmanager.TeamCommand;
import me.coltify.bteams.cmdmanager.TeamCommandInfo;
import me.coltify.bteams.cmdmanager.TeamCommandManager;
import me.coltify.bteams.utilities.MessageManager;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

@TeamCommandInfo(aliases = { "help" }, description = "Show the help page.", usage = "")
public class HelpCommand extends TeamCommand {
	
	@Override
	public void onCommand(Player player, String[] args) {
		for (TeamCommand cm : TeamCommandManager.cmds) {
			TeamCommandInfo info = (TeamCommandInfo) cm.getClass().getAnnotation(TeamCommandInfo.class);
			String command = StringUtils.join(info.aliases(), "/");
			String usage = info.usage();
			String description = info.description();
			MessageManager.sendMessage(player, MessageManager.getMessage("HELP-COMMAND").replace("<command>", command).replace("<usage>", usage).replace("<description>", description));
		}
	}
}
