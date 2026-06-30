package org.coreplex.api;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.coreplex.arena.Arena;
import org.coreplex.game.GameResult;
import org.coreplex.state.GamePhase;

import java.util.Optional;

public interface Minigame {
    String getId();
    String getDisplayName();
    int getMinPlayers();
    int getMaxPlayers();
    GamePhase getGamePhase();

    void onPlayerJoin(Arena arena, Player player);
    void onPlayerLeave(Arena arena, Player player);
    void onPlayerEliminated(Arena arena, Player player);
    void onEnd(Arena arena, GameResult result);

    void onTick(Arena arena);
    Optional<GameResult> checkWinCondition(Arena arena);

    default void onCountdownStart(Arena arena, int seconds) {}
    default void onCountdownTick(Arena arena, int secondsLeft) {}
    default void onPlayerDamage(Arena arena, Player victim, EntityDamageEvent e) {}
    default void onBlockBreak(Arena arena, Player player, BlockBreakEvent e) {}
    default void onBlockPlace(Arena arena, Player player, BlockPlaceEvent e) {}
}
