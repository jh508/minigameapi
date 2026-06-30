package org.coreplex;

import org.bukkit.plugin.java.JavaPlugin;
import org.coreplex.api.Minigame;
import org.coreplex.arena.Arena;
import org.coreplex.arena.ArenaConfig;
import org.coreplex.arena.ArenaManager;
import org.coreplex.listener.MinigameListener;

import java.util.HashMap;
import java.util.Map;

public final class MiniGameAPI extends JavaPlugin {

    private static MiniGameAPI instance;
    private ArenaManager arenaManager;
    private final Map<String, Minigame> registeredGames = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        arenaManager = new ArenaManager();

        getServer().getPluginManager().registerEvents(new MinigameListener(arenaManager), this);

        getServer().getScheduler().runTaskTimer(this, () -> {
            for (Arena arena : arenaManager.getArenas()) {
                arena.tick();
            }
        }, 0L, 1L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerGame(Minigame game)
    {
        registeredGames.put(game.getId(), game);
    }

    public void createArena(String arenaId, String gameId, ArenaConfig config)
    {
        Minigame game = registeredGames.get(gameId);

        if(game == null) throw new IllegalArgumentException("No game registered with id: " + gameId);
        arenaManager.registerArena(new Arena(arenaId, game, config));
    }

    public static MiniGameAPI getInstance() { return instance; }

    public ArenaManager getArenaManager() { return arenaManager; }
}
