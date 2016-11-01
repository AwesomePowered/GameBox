package me.nikl.gamebox.games;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by niklas on 10/28/16.
 *
 * abstract MinesweeperGame class implementing IGame
 */
public abstract class AGame implements IGame{
	private Player[] players;
	protected int numPlayers;
	private IGameManager gameManager;
	
	public AGame(Player[] players, IGameManager gameManager){
		this.gameManager = gameManager;
		this.players = players;
		this.numPlayers = players.length;
	}
	
	public AGame(UUID[] players, IGameManager gameManager){
		this.gameManager = gameManager;
		this.players = new Player[players.length];
		for(int i = 0; i<players.length;i++){
			this.players[i] = Bukkit.getPlayer(players[i]);
		}
		this.numPlayers = players.length;
	}
	
	
	public AGame(UUID player, IGameManager gameManager){
		this.gameManager = gameManager;
		this.numPlayers = 1;
		this.players = new Player[1];
		this.players[0] =  Bukkit.getPlayer(player);
	}
	
	public AGame(Player player, IGameManager gameManager){
		this(player.getUniqueId(), gameManager);
	}
	
	@Override
	public Player[] getPlayers(){
		return players;
	}
}
