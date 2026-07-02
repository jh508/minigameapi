package org.coreplex.arena;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.coreplex.state.GamePhase;

import java.util.*;

public class ArenaManager {
    private final Map<UUID, Arena> playerArenas = new HashMap<>();
    private final Map<String, Arena> arenas = new HashMap<>();

    public void registerArena(Arena arena)
    {
        arenas.put(arena.getId(), arena);
    }

    public Optional<Arena> getArenaById(String id)
    {
        return Optional.ofNullable(arenas.get(id));
    }

    public Optional<Arena> getArenaForPlayer(UUID uuid)
    {
        return Optional.ofNullable(playerArenas.get(uuid));
    }

    public boolean joinArena(Arena arena, UUID playerId) {
        if (arena.getTotalPlayerCount() >= arena.getConfig().getMaxPlayers()) {
            return false;
        }

        if (arena.getPhase() == GamePhase.IN_GAME || arena.getPhase() == GamePhase.ENDING) {
            arena.addSpectator(playerId);
            Player player = Bukkit.getPlayer(playerId);
            if (player != null) arena.getSpectatorManager().makeSpectator(player, arena.getAlivePlayers(), arena.getSpectators());
        } else {
            arena.addPlayer(playerId);
        }

        playerArenas.put(playerId, arena);
        return true;
    }

    public void leaveArena(UUID playerId) {
        Arena arena = playerArenas.remove(playerId);
        if (arena != null) {
            if (arena.isSpectator(playerId)) {
                Player player = Bukkit.getPlayer(playerId);
                if (player != null) arena.getSpectatorManager().removeSpectator(player, arena.getAllPlayers());
            }
            arena.removePlayerFromArena(playerId);
            arena.getTeamManager().ifPresent(tm -> tm.removePlayer(playerId));
        }
    }

    public Collection<Arena> getArenas()
    {
        return Collections.unmodifiableCollection(arenas.values());
    }

    public Collection<Arena> getAvailableArenas() {
        List<Arena> available = new ArrayList<>();
        for (Arena arena : arenas.values()) {
            boolean joinablePhase = arena.getPhase() == GamePhase.WAITING
                    || arena.getPhase() == GamePhase.STARTING;
            boolean hasSpace = arena.getTotalPlayerCount() < arena.getConfig().getMaxPlayers();

            if (joinablePhase && hasSpace) {
                available.add(arena);
            }
        }
        return available;
    }



}
