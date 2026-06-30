package org.coreplex.arena;

import org.bukkit.Bukkit;
import org.coreplex.api.Minigame;
import org.coreplex.game.GameResult;
import org.coreplex.state.*;

import java.util.*;

public class Arena {

    private final String id;
    private final Minigame game;
    private final ArenaConfig config;

    private GameState state;
    private final Set<UUID> allPlayers = new LinkedHashSet<>();
    private final Set<UUID> alivePlayers = new LinkedHashSet<>();
    private final Set<UUID> spectators = new LinkedHashSet<>();

    private int tickCount = 0;

    public Arena(String id, Minigame game, ArenaConfig config) {
        this.id = id;
        this.game = game;
        this.config = config;
        setState(new WaitingState());
    }

    // ────────────────────────────────────────────── Lifecycle ──────────────────────────────────────────────

    public void start()
    {
        setState(new StartingState());
    }

    public void cancelStart() {
        if (state.getPhase() == GamePhase.STARTING) {
            setState(new WaitingState());
        }
    }

    public void tick() {
        state.onTick(this);
        if (state.getPhase() == GamePhase.IN_GAME) {
            game.onTick(this);
            game.checkWinCondition(this).ifPresent(result -> setState(new EndingState(result)));
        }
        tickCount++;
    }

    public void setState(GameState newState) {
        if (this.state != null) this.state.onExit(this);

        this.state = newState;
        newState.onEnter(this);
    }

    public void eliminatePlayer(UUID uuid) {
        game.onPlayerEliminated(this, Bukkit.getPlayer(uuid));
        removeAlivePlayer(uuid);
        addSpectator(uuid);
    }

    public void end(GameResult result)
    {
        setState(new EndingState(result));
    }


    public String getId() { return id; }

    public ArenaConfig getConfig() { return config; }

    public void addPlayer(UUID uuid)
    {
        allPlayers.add(uuid);
        alivePlayers.add(uuid);
        state.onPlayerJoin(this);
    }

    public GameState getState() { return state; }

    public GamePhase getPhase() { return getState().getPhase(); }

    public Set<UUID> getAlivePlayers() { return Collections.unmodifiableSet(alivePlayers); }

    public Set<UUID> getSpectators() { return Collections.unmodifiableSet(spectators); }

    public Set<UUID> getAllPlayers() { return Collections.unmodifiableSet(allPlayers); }

    public int getTotalPlayerCount() { return allPlayers.size(); }

    public void removePlayerFromArena(UUID uuid)
    {
        alivePlayers.remove(uuid);
        removeSpectator(uuid);
        allPlayers.remove(uuid);
        state.onPlayerLeave(this);
    }

    public void removeAlivePlayer(UUID uuid)
    {
        alivePlayers.remove(uuid);
    }

    public void addSpectator(UUID uuid)
    {
        allPlayers.add(uuid);
        spectators.add(uuid);
    }

    public void removeSpectator(UUID uuid)
    {
        spectators.remove(uuid);
    }

    public void resetForNextRound() {
        alivePlayers.clear();
        alivePlayers.addAll(allPlayers);
        spectators.clear();
    }

    public Minigame getGame() { return game; }

    public int getTickCount() { return tickCount; }

    public boolean isInGame(UUID uuid) { return alivePlayers.contains(uuid); }
    public boolean isSpectator(UUID uuid) { return spectators.contains(uuid); }


}
