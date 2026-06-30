package org.coreplex.state;

import org.coreplex.arena.Arena;
import org.coreplex.game.GameResult;

public class InGameState implements GameState{

    private int tickCounter = 0;

    @Override
    public void onEnter(Arena arena) {
        arena.getGame().onStart(arena);
    }

    @Override
    public void onTick(Arena arena) {
        tickCounter++;

        if(tickCounter % 20 == 0)
        {
            int secondsElapsed = tickCounter / 20;
            if(secondsElapsed >= arena.getConfig().getMaxMatchSeconds())
            {
                arena.end(GameResult.timeout());
                return;
            }
        }

    }

    @Override
    public void onExit(Arena arena) {

    }

    @Override
    public GamePhase getPhase() {
        return GamePhase.IN_GAME;
    }

    @Override
    public String getName() {
        return "In Game";
    }
}
