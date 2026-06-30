package org.coreplex.state;

import org.coreplex.arena.Arena;

public interface GameState {

    void onEnter(Arena arena);
    void onTick(Arena arena);
    void onExit(Arena arena);
    GamePhase getPhase();
    String getName();

    default void onPlayerJoin(Arena arena) {}
    default void onPlayerLeave(Arena arena) {}

}
