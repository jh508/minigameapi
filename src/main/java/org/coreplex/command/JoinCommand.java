package org.coreplex.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.coreplex.arena.Arena;
import org.coreplex.arena.ArenaManager;

public class JoinCommand implements CommandExecutor {

    private final ArenaManager arenaManager;

    public JoinCommand(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("Usage: /join <arenaId>");
            return true;
        }

        String arenaId = args[0];
        Arena arena = arenaManager.getArenaById(arenaId).orElse(null);

        if (arena == null) {
            player.sendMessage("Arena not found.");
            return true;
        }

        boolean joined = arenaManager.joinArena(arena, player.getUniqueId());
        if (!joined) {
            player.sendMessage("Could not join arena — it may be full.");
        } else {
            player.sendMessage("Joined arena: " + arenaId);
        }

        return true;
    }
}