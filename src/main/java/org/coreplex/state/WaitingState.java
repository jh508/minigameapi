package org.coreplex.state;

import org.coreplex.arena.Arena;

public class WaitingState implements GameState {

    @Override
    public void onEnter(Arena arena) {

    }

    @Override
    public void onTick(Arena arena) {

    }

    @Override
    public void onExit(Arena arena) {

    }

    @Override
    public GamePhase getPhase() {
        return GamePhase.WAITING;
    }

    @Override
    public String getName() {
        return "Waiting";
    }

    @Override
    public void onPlayerJoin(Arena arena) {
        if (arena.getTotalPlayerCount() >= arena.getConfig().getMinPlayers()) {
            arena.start();
        }
    }

    @Override
    public void onPlayerLeave(Arena arena) {
        if (arena.getTotalPlayerCount() < arena.getConfig().getMinPlayers()) {
            arena.cancelStart();
        }
    }
}
