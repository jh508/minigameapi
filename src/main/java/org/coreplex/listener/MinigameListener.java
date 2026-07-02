package org.coreplex.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.coreplex.arena.ArenaManager;

import java.util.UUID;

public class MinigameListener implements Listener {

    private final ArenaManager manager;

    public MinigameListener(ArenaManager manager)
    {
        this.manager = manager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        manager.getArenaForPlayer(event.getPlayer().getUniqueId())
                .ifPresent(arena -> { arena.getGame().onBlockBreak(arena, event.getPlayer(), event); });
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        manager.getArenaForPlayer(event.getPlayer().getUniqueId())
                .ifPresent(arena -> { arena.getGame().onBlockPlace(arena, event.getPlayer(), event); });
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        manager.getArenaForPlayer(player.getUniqueId())
                .ifPresent(arena -> arena.getGame().onPlayerDamage(arena, player, event));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        manager.getArenaForPlayer(player.getUniqueId()).ifPresent(arena -> arena.getGame().onPlayerDeath(arena, player, event));
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        manager.getArenaForPlayer(player.getUniqueId())
                .ifPresent(arena -> event.setRespawnLocation(arena.getConfig().getSpectatorSpawn()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        UUID uuid = event.getPlayer().getUniqueId();

        manager.getArenaForPlayer(uuid)
                .ifPresent(arena -> {
                    arena.getGame().onPlayerLeave(arena, event.getPlayer());
                    manager.leaveArena(uuid);
                });
    }

}
