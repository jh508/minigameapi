package org.coreplex.api;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.coreplex.arena.Arena;
import org.coreplex.game.GameResult;

import java.util.Optional;

/**
 * Represents a single minigame implementation (e.g. Skywars, Spleef).
 * <p>
 * An {@link Arena} delegates its lifecycle and gameplay events to the {@link Minigame}
 * registered on it, allowing the core API to drive arena state while leaving
 * game-specific rules and behavior to the implementation.
 */
public interface Minigame {

    /**
     * @return the unique, machine-readable identifier for this minigame (e.g. {@code "skywars"})
     */
    String getId();

    /**
     * @return the human-readable name shown to players, e.g. in chat and logs
     */
    String getDisplayName();

    /**
     * @return the minimum number of players required before an arena running this
     *         minigame will begin its start countdown
     */
    int getMinPlayers();

    /**
     * @return the maximum number of players an arena running this minigame can hold
     */
    int getMaxPlayers();


    /**
     * Called once when the pre-match countdown begins.
     *
     * @param arena   the arena entering its countdown
     * @param seconds the total countdown duration, in seconds
     */
    default void onCountdownStart(Arena arena, int seconds) {}

    /**
     * Called once per second while the pre-match countdown is running.
     *
     * @param arena       the arena counting down
     * @param secondsLeft the number of seconds remaining before the match starts
     */
    default void onCountdownTick(Arena arena, int secondsLeft) {}

    /**
     * Called once when the match itself begins, immediately after the countdown
     * finishes and the arena transitions into its in-game phase.
     *
     * @param arena the arena starting its match
     */
    default void onStart(Arena arena) {}

    /**
     * Called every tick while the arena is in its in-game phase, before
     * {@link #checkWinCondition(Arena)} is evaluated. Use this for per-tick
     * gameplay logic such as border shrinking or scoreboard updates.
     *
     * @param arena the active arena
     */
    default void onTick(Arena arena) {}

    /**
     * Called once when the match ends, after {@link #checkWinCondition(Arena)} has
     * produced a result or the match has otherwise been concluded (e.g. timeout).
     *
     * @param arena  the arena the match was played in
     * @param result the outcome of the match
     */
    void onEnd(Arena arena, GameResult result);

    /**
     * Called every tick while the arena is in progress to check whether the match
     * should end. Implementations should return an empty {@link Optional} while the
     * match is still ongoing.
     *
     * @param arena the active arena
     * @return the {@link GameResult} if the match has been decided, otherwise empty
     */
    Optional<GameResult> checkWinCondition(Arena arena);

    /**
     * Called when a player in the arena takes damage, allowing the minigame to
     * modify or cancel the damage (e.g. disabling fall damage, friendly fire rules).
     *
     * @param arena  the active arena
     * @param victim the player taking damage
     * @param event  the underlying Bukkit damage event
     */
    default void onPlayerDamage(Arena arena, Player victim, EntityDamageEvent event) {}

    /**
     *
     * @param arena the active arena
     * @param victim the player who died
     * @param event the player death event
     */
    default void onPlayerDeath(Arena arena, Player victim, PlayerDeathEvent event) {}

    /**
     *
     * @param arena the active arena
     * @param player the player who respawned
     * @param event the player respawn event
     */
    default void onPlayerRespawn(Arena arena, Player player, PlayerRespawnEvent event) {}

    /**
     * Called when a player breaks a block inside the arena, allowing the minigame
     * to allow, cancel, or otherwise react to the break.
     *
     * @param arena the active arena
     * @param player the player breaking the block
     * @param e the underlying Bukkit block break event
     */
    default void onBlockBreak(Arena arena, Player player, BlockBreakEvent e) {}

    /**
     * Called when a player places a block inside the arena, allowing the minigame
     * to allow, cancel, or otherwise react to the placement.
     *
     * @param arena the active arena
     * @param player the player placing the block
     * @param e the underlying Bukkit block place event
     */
    default void onBlockPlace(Arena arena, Player player, BlockPlaceEvent e) {}

    /**
     * Called when a player joins the arena, whether as an active participant
     * or as a spectator if the match is already in progress.
     *
     * @param arena  the arena being joined
     * @param player the joining player
     */
    default void onPlayerJoin(Arena arena, Player player) {}

    /**
     * Called when a player leaves the arena, either voluntarily or by disconnecting.
     *
     * @param arena  the arena being left
     * @param player the leaving player
     */
    default void onPlayerLeave(Arena arena, Player player) {}

    /**
     * Called when a player is eliminated from an in-progress match and moved to
     * spectator status.
     *
     * @param arena  the active arena
     * @param player the eliminated player
     */
    default void onPlayerEliminated(Arena arena, Player player) {}
}
