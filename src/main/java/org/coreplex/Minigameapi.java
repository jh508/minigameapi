package org.coreplex;

import org.bukkit.plugin.java.JavaPlugin;
import org.coreplex.arena.Arena;
import org.coreplex.arena.ArenaManager;
import org.coreplex.listener.MinigameListener;

public final class Minigameapi extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
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

    public ArenaManager getArenaManager()
    {
        return arenaManager;
    }
}
