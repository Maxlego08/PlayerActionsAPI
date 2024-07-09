package fr.maxlego08.playeractions.actions;

import fr.maxlego08.playeractions.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * The Broadcast class represents an action that broadcasts a message to all players
 * with an optional delay.
 */
public class Broadcast extends Action {

    private final String message;

    /**
     * Constructs a new Broadcast action with the specified delay and message.
     *
     * @param delay   The delay in ticks before the message is broadcast.
     * @param message The message to broadcast.
     */
    public Broadcast(int delay, String message) {
        super(delay);
        this.message = message;
    }

    /**
     * Returns a Runnable representing the broadcast action.
     * The message is parsed for placeholders and color codes, and then broadcast to all players.
     *
     * @param plugin The plugin instance.
     * @param player The player for whom the placeholders in the message will be parsed.
     * @return A Runnable representing the broadcast action.
     */
    @Override
    protected Runnable getRunnableAction(Plugin plugin, Player player) {
        return () -> Bukkit.broadcastMessage(color(parse(player, this.message)));
    }
}
