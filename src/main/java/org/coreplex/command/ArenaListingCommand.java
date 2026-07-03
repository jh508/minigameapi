package org.coreplex.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.coreplex.arena.Arena;
import org.coreplex.arena.ArenaManager;

public class ArenaListingCommand implements CommandExecutor {

    private final ArenaManager arenaManager;

    public ArenaListingCommand(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (arenaManager.getArenas().isEmpty()) {
            sender.sendMessage("No arenas registered.");
            return true;
        }

        arenaManager.getArenas().forEach(arena -> {
            sender.sendMessage(String.format("%s [%s] - %s - %d players",
                    arena.getId(),
                    arena.getGame().getDisplayName(),
                    arena.getPhase(),
                    arena.getTotalPlayerCount()));
        });

        return true;
    }

}
