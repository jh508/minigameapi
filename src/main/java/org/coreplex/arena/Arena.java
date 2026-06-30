package org.coreplex.arena;

import org.bukkit.util.BoundingBox;
import org.coreplex.api.Minigame;
import org.coreplex.state.EndingState;
import org.coreplex.state.GamePhase;
import org.coreplex.state.GameState;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class Arena {

    private final String id;
    private final Minigame game;
    private final ArenaConfig config;

    private GameState state;
    private final Set<UUID> players = new LinkedHashSet<>();
    private final Set<UUID> spectators = new LinkedHashSet<>();

    public Arena(String id, Minigame game, ArenaConfig config) {
        this.id = id;
        this.game = game;
        this.config = config;
    }

    // ────────────────────────────────────────────── Lifecycle ──────────────────────────────────────────────

    public void tick() {
        state.onTick(this);
        if (state.getPhase() == GamePhase.IN_GAME) {
            game.onTick(this);
            game.checkWinCondition(this).ifPresent(result -> setState(new EndingState(result)));
        }
    }

    public void setState(GameState newState) {
        if (this.state != null) this.state.onExit(this);
        GameState old = this.state;
        this.state = newState;
        newState.onEnter(this);
    }

    public void addPlayer(UUID uuid)
    {
        players.add(uuid);
    }

    public void removePlayer(UUID uuid)
    {
        players.remove(uuid);
    }

    public void setSpectator(UUID uuid)
    {
        spectators.add(uuid);
    }

    public void removeSpectator(UUID uuid)
    {
        spectators.remove(uuid);
    }

    public Minigame getGame() { return game; }
}
