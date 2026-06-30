package org.coreplex.state;

import org.coreplex.arena.Arena;

public class StartingState implements GameState{

    private int secondsLeft;
    private int tickCounter = 0;

    @Override
    public void onEnter(Arena arena) {
        secondsLeft = arena.getConfig().getCountdownSeconds();
        arena.getGame().onCountdownStart(arena, secondsLeft);
    }

    @Override
    public void onTick(Arena arena) {
        tickCounter++;

        if(tickCounter % 20 == 0)
        {
            secondsLeft--;

            if (secondsLeft <= 0) {
                arena.setState(new InGameState());
            } else {
                arena.getGame().onCountdownTick(arena, secondsLeft);
            }
        }

    }

    @Override
    public void onExit(Arena arena) {

    }

    @Override
    public GamePhase getPhase() {
        return GamePhase.STARTING;
    }

    @Override
    public String getName() {
        return "Starting";
    }
}
