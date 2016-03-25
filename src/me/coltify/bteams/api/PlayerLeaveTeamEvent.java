package me.coltify.bteams.api;

import me.coltify.bteams.utilities.Team;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeaveTeamEvent extends Event implements Cancellable {

	private HandlerList handlerList = new HandlerList();
	private Reason reason;
	private Team team;
	private boolean cancelled = false;
	private String target;
	
	public PlayerLeaveTeamEvent(String target, Team team, Reason reason) {
		this.target = target;
		this.team = team;
	}
	
	public String getTarget() {
		return target;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public Team getTeam() {
		return team;
	}
	
	public Reason getReason() {
		return reason;
	}
	
	public HandlerList getHandlers() {
		return handlerList;
	}
	
	public enum Reason {
		KICKED, PERSONAL;
	}
}
