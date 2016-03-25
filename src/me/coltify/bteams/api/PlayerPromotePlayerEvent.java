package me.coltify.bteams.api;

import me.coltify.bteams.utilities.Team;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerPromotePlayerEvent extends PlayerEvent implements Cancellable {

	private HandlerList handlerList = new HandlerList();
	private boolean cancelled = false;
	private Team team;
	private String target;
	
	public PlayerPromotePlayerEvent(Player player, String target, Team team) {
		super(player);
		this.team = team;
		this.target = target;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
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
