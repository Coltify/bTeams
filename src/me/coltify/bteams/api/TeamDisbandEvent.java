package me.coltify.bteams.api;

import me.coltify.bteams.utilities.Team;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamDisbandEvent extends Event implements Cancellable {

	private HandlerList handlerList = new HandlerList();
	private boolean cancelled = false;
	private Team team;
	
	public TeamDisbandEvent(Team team) {
		this.team = team;
	}
	
	public Team getTeam() {
		return team;
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
