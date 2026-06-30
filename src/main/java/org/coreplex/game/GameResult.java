package org.coreplex.game;

import java.util.*;

public class GameResult {
    private final List<UUID> winners;
    private final Map<UUID, Integer> placements;
    private final boolean draw;
    private final boolean timeout;

    private GameResult(List<UUID> winners, Map<UUID, Integer> placements, boolean draw, boolean timeout) {
        this.winners = List.copyOf(winners);
        this.placements = Map.copyOf(placements);
        this.draw = draw;
        this.timeout = timeout;
    }

    public static GameResult soloWinner(UUID player) {
        return new GameResult(List.of(player), Map.of(player, 1), false, false);
    }

    public static GameResult teamWinners(Collection<UUID> teamMembers) {
        return new GameResult(List.copyOf(teamMembers), Map.of(), false, false);
    }

    public static GameResult draw() {
        return new GameResult(List.of(), Map.of(), true, false);
    }

    public static GameResult ranked(List<UUID> orderedByPlacement) {
        Map<UUID, Integer> placements = new HashMap<>();
        for (int i = 0; i < orderedByPlacement.size(); i++) {
            placements.put(orderedByPlacement.get(i), i + 1);
        }
        return new GameResult(orderedByPlacement, placements, false, false);
    }

    public OptionalInt getPlacement(UUID player) {
        Integer p = placements.get(player);
        return p == null ? OptionalInt.empty() : OptionalInt.of(p);
    }

    public static GameResult timeout() {
        return new GameResult(List.of(), Map.of(), false, true);
    }

    public List<UUID> getWinners() { return winners; }
    public boolean isDraw() { return draw; }
    public boolean isTimeout() { return timeout; }
}
