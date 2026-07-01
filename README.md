# MinigameAPI

A lightweight [Paper](https://papermc.io/) plugin API for building multiple Minecraft minigames (Skywars, Spleef, TNT Run, etc.) on top of a shared arena/lifecycle framework, leaving you to focus on writing just the gameplay.

Implement a small `Minigame` interface with your game's rules, register it, and the API handles arenas, player join/leave, countdowns, match timeouts, spectating, and win-condition polling for you.

## Features

- **Arena lifecycle state machine** — `WAITING → STARTING → IN_GAME → ENDING → WAITING`, driven by a per-tick update loop
- **Arena management** — register arenas per game, join/leave handling, capacity checks, and lookup by player or arena id
- **Single & Network server support** — Supports registration of multiple games and arenas on a single server, leaving you to run as many game instances as you desire
- **Spectator system** — players who are eliminated or join a running match are switched to spectator mode (flight, adventure mode, invisibility) with visibility correctly hidden/shown between spectators and live players
- **Team system** — named teams with colors, size limits, and random-assignment helpers
- **Game results** — solo winner, team winners, ranked placements, draws, and timeouts, with per-player placement lookup
- **Event hooks** — block break/place, player damage, join/leave, elimination, countdown ticks, and per-tick updates are forwarded to your `Minigame` implementation
- **`/join <arenaId>`** command included out of the box

## How it works

At the core is the `Minigame` interface — this is what you implement for each game mode:

```java
public interface Minigame {
    String getId();
    String getDisplayName();
    int getMinPlayers();
    int getMaxPlayers();

    void onEnd(Arena arena, GameResult result);
    Optional<GameResult> checkWinCondition(Arena arena);

    default void onCountdownStart(Arena arena, int seconds) {}
    default void onCountdownTick(Arena arena, int secondsLeft) {}
    default void onPlayerDamage(Arena arena, Player victim, EntityDamageEvent e) {}
    default void onBlockBreak(Arena arena, Player player, BlockBreakEvent e) {}
    default void onBlockPlace(Arena arena, Player player, BlockPlaceEvent e) {}
    default void onStart(Arena arena) {}
    default void onPlayerJoin(Arena arena, Player player) {}
    default void onPlayerLeave(Arena arena, Player player) {}
    default void onPlayerEliminated(Arena arena, Player player) {}
    default void onTick(Arena arena) {}
}
```

An `Arena` binds one `Minigame` instance to an `ArenaConfig` (world, spawn points, lobby/spectator locations, min/max players, countdown length, match timeout, post-game delay, bounds). The `ArenaManager` tracks all arenas and which arena each player is currently in.

Every tick, each `Arena` advances its current `GameState`:

| Phase      | Behavior |
|------------|----------|
| `WAITING`  | Waits for `minPlayers`, then transitions to `STARTING` |
| `STARTING` | Runs the countdown, firing `onCountdownTick`, then transitions to `IN_GAME` |
| `IN_GAME`  | Calls `onTick` and `checkWinCondition` every tick; ends on a win condition or match timeout |
| `ENDING`   | Calls `onEnd` with the `GameResult`, waits out the post-game delay, then resets back to `WAITING` |

Registering a game and creating an arena:

```java
MiniGameAPI.getInstance().registerGame(new SkywarsGame());
MiniGameAPI.getInstance().createArena("skywars_1", "skywars", arenaConfig);
```

Players join with `/join <arenaId>`. If the arena is mid-match, joining players are added as spectators instead of participants.

## Requirements

- Java 25
- Paper API `26.2.build` or newer (Minecraft 1.21, `api-version: 1.21`)
- Maven

## Building

```bash
mvn clean package
```

The shaded jar is produced under `target/` and can be dropped into a Paper server's `plugins/` folder.

## Project layout

```
src/main/java/org/coreplex/
├── MiniGameAPI.java        # Plugin entrypoint, game registry, arena creation
├── api/Minigame.java        # Interface every minigame implements
├── arena/                   # Arena, ArenaConfig, ArenaManager
├── command/JoinCommand.java  # /join <arenaId>
├── game/GameResult.java      # Win/draw/timeout results
├── listener/                 # Forwards Bukkit events to the active Minigame
├── spectator/SpectatorManager.java
├── state/                    # GameState implementations (Waiting/Starting/InGame/Ending)
└── team/                     # Team, TeamManager
```

## Status

Early-stage / work in progress — core arena lifecycle, teams, and spectating are implemented; there is no persistence, arena configuration loading (e.g. from YAML), or bundled example minigame yet. I do not suggest building real games upon this yet.
