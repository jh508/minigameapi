package org.coreplex.state;

import org.coreplex.arena.Arena;
import org.coreplex.game.GameResult;

public class EndingState implements GameState{

    private final GameResult result;
    private int tickCounter = 0;

    public EndingState(GameResult result)
    {
        this.result = result;
    }

    @Override
    public void onEnter(Arena arena) {
        arena.getGame().onEnd(arena, result);
    }

    @Override
    public void onTick(Arena arena) {
        tickCounter++;
        if (tickCounter >= arena.getConfig().getPostGameSeconds() * 20) {
            arena.setState(new WaitingState());
        }
    }

    @Override
    public void onExit(Arena arena) {
        arena.resetForNextRound();
    }

    @Override
    public GamePhase getPhase() {
        return GamePhase.ENDING;
    }

    @Override
    public String getName() {
        return "Ending";
    }
}
