package fr.maxlego08.playeractions;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * The Action class represents an action that can be executed with an optional delay.
 * Subclasses should provide the specific action to be performed by implementing the getRunnableAction method.
 */
public abstract class Action {

    private final int delay;

    /**
     * Constructs a new Action with the specified delay.
     *
     * @param delay The delay in ticks before the action is executed.
     */
    public Action(int delay) {
        this.delay = delay;
    }

    /**
     * Returns a Runnable representing the specific action to be performed.
     * Subclasses must implement this method to define the action.
     *
     * @param plugin The plugin instance.
     * @param player The player for whom the action is performed.
     * @return A Runnable representing the action.
     */
    protected abstract Runnable getRunnableAction(Plugin plugin, Player player);

    /**
     * Executes the action. If a delay is specified, the action is scheduled to run after the delay.
     *
     * @param plugin The plugin instance.
     * @param player The player for whom the action is performed.
     */
    public void execute(Plugin plugin, Player player) {
        Runnable runnable = getRunnableAction(plugin, player);
        if (this.delay == 0) {
            runnable.run();
        } else {
            plugin.getServer().getScheduler().runTaskLater(plugin, runnable, this.delay);
        }
    }

    /**
     * Gets the delay before the action is executed.
     *
     * @return The delay in ticks.
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Parses a string, replacing placeholders with the player's name and additional arguments.
     *
     * @param player    The player whose name will replace the %player% placeholder.
     * @param string    The string containing placeholders.
     * @param arguments The arguments to replace placeholders in pairs.
     * @return The parsed string with placeholders replaced.
     */
    protected String parse(Player player, String string, Object... arguments) {
        string = string.replace("%player%", player.getName());
        long count = string.chars().filter(ch -> ch == '%').count();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && count >= 2) {
            string = PlaceholderAPI.setPlaceholders(player, string);
        }
        return parse(string, arguments);
    }

    /**
     * Parses a string, replacing placeholders with the specified arguments.
     *
     * @param string    The string containing placeholders.
     * @param arguments The arguments to replace placeholders in pairs.
     * @return The parsed string with placeholders replaced.
     */
    protected String parse(String string, Object... arguments) {
        if (arguments.length % 2 != 0) {
            System.err.println("Invalid number of arguments. Arguments must be in pairs.");
        } else {
            for (int index = 0; index < arguments.length; index += 2) {
                String from = arguments[index].toString();
                String to = arguments[index + 1].toString();
                string = string.replace(from, to);
            }
        }
        return string;
    }

    /**
     * Translates color codes in a string.
     *
     * @param message The message containing color codes.
     * @return The message with color codes translated.
     */
    protected String color(String message) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
    }
}
