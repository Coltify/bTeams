package me.coltify.bteams.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamCreateEvent extends Event implements Cancellable {

	private HandlerList handlerList = new HandlerList();
	private boolean cancelled = false;
	private String teamName;
	
	public TeamCreateEvent(String teamName) {
		this.teamName = teamName;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamName(String newName) {
		this.teamName = newName;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public HandlerList getHandlers() {
		return handlerList;
	}
}
