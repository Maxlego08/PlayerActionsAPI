package fr.maxlego08.playeractions.actions;

import fr.maxlego08.playeractions.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents an action that executes a console command after a specified delay.
 */
public class ConsoleCommand extends Action {

    private final String command;

    /**
     * Constructs a new ConsoleCommand action with the specified delay and command.
     *
     * @param delay   the delay in ticks before the action is executed
     * @param command the console command to execute
     */
    public ConsoleCommand(int delay, String command) {
        super(delay);
        this.command = command;
    }

    /**
     * Returns a runnable that executes the console command.
     *
     * @param plugin the plugin instance
     * @param player the player to use for parsing placeholders in the command
     * @return a runnable that executes the console command
     */
    @Override
    protected Runnable getRunnableAction(Plugin plugin, Player player) {
        return () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parse(player, this.command));
    }
}

