package fr.maxlego08.playeractions.actions;

import fr.maxlego08.playeractions.Action;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents an action that closes a player's inventory after a specified delay.
 */
public class Close extends Action {

    /**
     * Constructs a new Close action with the specified delay.
     *
     * @param delay the delay in ticks before the action is executed
     */
    public Close(int delay) {
        super(delay);
    }

    /**
     * Returns a runnable that closes the player's inventory.
     *
     * @param plugin the plugin instance
     * @param player the player whose inventory will be closed
     * @return a runnable that closes the player's inventory
     */
    @Override
    protected Runnable getRunnableAction(Plugin plugin, Player player) {
        return player::closeInventory;
    }
}

