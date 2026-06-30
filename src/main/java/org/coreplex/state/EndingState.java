package org.coreplex.state;

import org.coreplex.arena.Arena;
import org.coreplex.game.GameResult;

public class EndingState implements GameState{
    public EndingState(GameResult result) {
    }

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
        return null;
    }

    @Override
    public String getName() {
        return "";
    }
}
