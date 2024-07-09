package fr.maxlego08.playeractions.actions;

import fr.maxlego08.playeractions.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents an action that dispatches a command as a player after a specified delay.
 */
public class PlayerCommand extends Action {

    private final String command;

    /**
     * Constructs a new PlayerCommand action with the specified delay and command.
     *
     * @param delay   the delay in ticks before the action is executed
     * @param command the command to dispatch as the player
     */
    public PlayerCommand(int delay, String command) {
        super(delay);
        this.command = command;
    }

    /**
     * Returns a runnable that dispatches the command as the player.
     *
     * @param plugin the plugin instance
     * @param player the player to dispatch the command as
     * @return a runnable that dispatches the command as the player
     */
    @Override
    protected Runnable getRunnableAction(Plugin plugin, Player player) {
        return () -> Bukkit.dispatchCommand(player, parse(player, this.command));
    }
}

