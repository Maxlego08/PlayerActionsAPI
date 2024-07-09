package fr.maxlego08.playeractions.actions;

import fr.maxlego08.playeractions.Action;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents an action that sends a message to a player after a specified delay.
 */
public class Message extends Action {

    private final String message;

    /**
     * Constructs a new Message action with the specified delay and message.
     *
     * @param delay   the delay in ticks before the action is executed
     * @param message the message to send to the player
     */
    public Message(int delay, String message) {
        super(delay);
        this.message = message;
    }

    /**
     * Returns a runnable that sends the message to the player.
     *
     * @param plugin the plugin instance
     * @param player the player to send the message to
     * @return a runnable that sends the message to the player
     */
    @Override
    protected Runnable getRunnableAction(Plugin plugin, Player player) {
        return () -> player.sendMessage(color(parse(player, this.message)));
    }
}
