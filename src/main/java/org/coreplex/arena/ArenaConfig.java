package org.coreplex.arena;

import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

import java.util.List;

public class ArenaConfig {

    private final String arenaId;
    private final String gameId;
    private final String worldName;
    private final List<Location> spawnPoints;
    private final Location lobbyLocation;
    private final Location spectatorSpawn;
    private final int minPlayers;
    private final int maxPlayers;
    private final int countdownSeconds;
    private final int maxMatchSeconds;
    private final int postGameSeconds;
    private final BoundingBox bounds;

    public ArenaConfig(String arenaId, String gameId, String worldName, List<Location> spawnPoints, Location lobbyLocation, Location spectatorSpawn, int minPlayers, int maxPlayers, int countdownSeconds, int maxMatchSeconds, int postGameSeconds, BoundingBox bounds) {
        this.arenaId = arenaId;
        this.gameId = gameId;
        this.worldName = worldName;
        this.spawnPoints = spawnPoints;
        this.lobbyLocation = lobbyLocation;
        this.spectatorSpawn = spectatorSpawn;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.countdownSeconds = countdownSeconds;
        this.maxMatchSeconds = maxMatchSeconds;
        this.postGameSeconds = postGameSeconds;
        this.bounds = bounds;
    }

    public String getArenaId() { return arenaId; }
    public String getGameId() { return gameId; }
    public String getWorldName() { return worldName; }
    public List<Location> getSpawnPoints() { return spawnPoints; }
    public Location getLobbyLocation() { return lobbyLocation; }
    public Location getSpectatorSpawn() { return spectatorSpawn; }
    public int getMinPlayers() { return minPlayers; }
    public int getMaxPlayers() { return maxPlayers; }
    public int getCountdownSeconds() { return countdownSeconds; }
    public int getMaxMatchSeconds() { return maxMatchSeconds; }
    public int getPostGameSeconds() { return postGameSeconds; }
    public BoundingBox getBounds() { return bounds; }

}
