package me.coltify.bteams.utilities;

import java.io.IOException;
import java.io.InputStream;

import me.coltify.bteams.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class MessageManager {

	private UFile file;
	private static FileConfiguration messages;
	
	public MessageManager() {
		file = new UFile("plugins/bTeams/messages.yml/");
		messages = YamlConfiguration.loadConfiguration(file.toFile());
	}
	
	public static String getMessage(String str) {
		if (messages.isString(str.toUpperCase())) {
			return messages.getString(str.toUpperCase()).replace("&", "§");
		}
		InputStream in = Main.getInstance().getResource("messages.yml");
		@SuppressWarnings("deprecation")
		FileConfiguration config = YamlConfiguration.loadConfiguration(in);
		if (config.isString(str)) {
			messages.set(str, config.getString(str));
			try {
				messages.save("plugins/bTeams/messages.yml/");
			} catch (IOException e) {
				System.out.println("[bTeams] Failed to save messages.yml");
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("[bTeams] Failed to save close input stream.");
				e.printStackTrace();
			}
			return messages.getString(str.toUpperCase()).replace("&", "§");
		}
		return "";
	}
	
	public static void sendMessage(Player player, String str) {;
		for (String nl : str.split("<nl>")) {
			player.sendMessage(nl);
		}
	}

	public UFile getFile() {
		return file;
	}
}
