package fr.maxlego08.playeractions.actions;

import fr.maxlego08.playeractions.Action;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents an action that sends a chat message from a player after a specified delay.
 */
public class Chat extends Action {

    private final String message;

    /**
     * Constructs a new Chat action with the specified delay and message.
     *
     * @param delay   the delay in ticks before the action is executed
     * @param message the chat message to be sent
     */
    public Chat(int delay, String message) {
        super(delay);
        this.message = message;
    }

    /**
     * Returns a runnable that sends a chat message from the player.
     *
     * @param plugin the plugin instance
     * @param player the player who will send the chat message
     * @return a runnable that sends a chat message from the player
     */
    @Override
    protected Runnable getRunnableAction(Plugin plugin, Player player) {
        return () -> player.chat(parse(player, this.message));
    }
}
