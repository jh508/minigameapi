package org.coreplex.spectator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.coreplex.MiniGameAPI;

import java.util.Collection;
import java.util.UUID;

public class SpectatorManager {

    public void makeSpectator(Player player, Collection<UUID> alivePlayers, Collection<UUID> spectators)
    {
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setGameMode(GameMode.ADVENTURE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));

        for(UUID alive : alivePlayers)
        {
            Player alivePlayer = Bukkit.getPlayer(alive);
            if (alivePlayer == null) continue;
            alivePlayer.hidePlayer(MiniGameAPI.getInstance(), player);
        }

        for (UUID uuid : spectators) {
            Player spectator = Bukkit.getPlayer(uuid);
            if (spectator == null) continue;
            player.showPlayer(MiniGameAPI.getInstance(), spectator);
            spectator.showPlayer(MiniGameAPI.getInstance(), player);
        }

    }

    public void removeSpectator(Player player, Collection<UUID> allPlayers)
    {
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setGameMode(GameMode.SURVIVAL);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);

        for (UUID uuid : allPlayers) {
            Player alive = Bukkit.getPlayer(uuid);
            if (alive != null) alive.showPlayer(player);
        }
    }


}
