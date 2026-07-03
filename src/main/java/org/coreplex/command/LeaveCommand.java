package org.coreplex.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.coreplex.arena.Arena;
import org.coreplex.arena.ArenaManager;

import java.util.Optional;

public class LeaveCommand implements CommandExecutor {

    private final ArenaManager arenaManager;

    public LeaveCommand(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Optional<Arena> arena = arenaManager.getArenaForPlayer(player.getUniqueId());

        if (arena.isEmpty()) {
            player.sendMessage("Arena not found.");
            return true;
        }

        arenaManager.leaveArena(player.getUniqueId());
        player.sendMessage("Left arena: " + arena.get().getId());

        return true;
    }
}