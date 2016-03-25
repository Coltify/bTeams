package me.coltify.bteams;

import java.io.File;

import me.coltify.bteams.cmdmanager.TeamCommandManager;
import me.coltify.bteams.utilities.Settings;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Callum
 * @version 1.0
 */
public class Main extends JavaPlugin {

	public static Main instance;
	
	public void onEnable() {
		instance = this;
		
		File folder = new File("plugins/bTeams/Teams");
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		new TeamCommandManager();
		
		Settings.canMKM = getConfig().getBoolean("Can-Managers-Kick-Managers");
	}
	
	public void onDisable() {
		instance = null;
	}
	
	public static void registerCommand(String label, CommandExecutor executor) {
		instance.getCommand(label).setExecutor(executor);
	}
	
	public static void registerListener(Listener listener) {
		instance.getServer().getPluginManager().registerEvents(listener, instance);
	}
	
	public static Main getInstance() {
		return instance;
	}
}
