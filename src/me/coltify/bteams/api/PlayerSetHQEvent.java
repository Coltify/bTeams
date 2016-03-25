package me.coltify.bteams.api;

import me.coltify.bteams.utilities.Team;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSetHQEvent extends PlayerEvent implements Cancellable {

	private HandlerList handlerList = new HandlerList();
	private boolean cancelled = false;
	private Team team;
	private Location newHQ;
	
	public PlayerSetHQEvent(Player player, Location newHQ, Team team) {
		super(player);
		this.team = team;
		this.newHQ = newHQ;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public void setNewHQ(Location newHQ) {
		this.newHQ = newHQ;
	}
	
	public Location getNewHQ() {
		return newHQ;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public HandlerList getHandlers() {
		return handlerList;
	}
}
