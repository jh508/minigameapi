package org.coreplex.logger;

import org.coreplex.MiniGameAPI;
import org.coreplex.api.Minigame;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom logger for minigames.
 * This logger uses common java Logger.
 */

public final class MiniGameLogger {

    private MiniGameLogger() {}

    private static Logger logger() {
        return MiniGameAPI.getInstance().getLogger();
    }

    /**
     * Prints info information
     *
     * @param game the current minigame instance
     * @param message info message
     */
    public static void info(Minigame game, String message) {
        logger().info("[%s] %s".formatted(game.getDisplayName(), message));
    }

    /**
     * Prints error message
     *
     * @param game the current minigame instance
     * @param message error message
     */
    public static void error(Minigame game, String message) {
        logger().severe("[%s] %s".formatted(game.getDisplayName(), message));
    }

    /**
     * Prints error message with exception
     *
     * @param message  error message
     * @param throwable exception
     */
    public static void error(String message, Throwable throwable) {
        logger().log(Level.SEVERE, message, throwable);
    }

    /**
     * Prints warning message
     *
     * @param game the current minigame instance
     * @param message error message
     */
    public static void warning(Minigame game, String message) {
        logger().warning("[%s] %s".formatted(game.getDisplayName(), message));
    }
}