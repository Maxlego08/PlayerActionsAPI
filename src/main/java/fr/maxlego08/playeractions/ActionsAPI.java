package fr.maxlego08.playeractions;


import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Utility class for loading actions from a list of command strings.
 */
public class ActionsAPI {

    private final static Map<ActionType, Class<? extends Action>> actions;

    static {
        actions = new HashMap<>();
        for (ActionTypes actionType : ActionTypes.values()) {
            actions.put(actionType, actionType.getDefaultAction());
        }
    }

    /**
     * Instantiates an action with the specified class and arguments.
     *
     * @param actionClass The class to instantiate.
     * @param delay The delay in ticks.
     * @param string The string argument.
     * @return An instance of the specified class.
     * @throws Exception if instantiation fails.
     */
    private static Action instantiateAction(Class<? extends Action> actionClass, int delay, String string) throws Exception {

        Constructor<?>[] constructors = actionClass.getConstructors();

        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            if (parameterTypes.length == 2) {
                if (parameterTypes[0] == int.class && parameterTypes[1] == String.class) {
                    return (Action) constructor.newInstance(delay, string);
                } else if (parameterTypes[0] == String.class && parameterTypes[1] == int.class) {
                    return (Action) constructor.newInstance(string, delay);
                }
            }

            if (parameterTypes.length == 1) {
                return (Action) constructor.newInstance(delay);
            }
        }

        throw new IllegalArgumentException("No suitable constructor found for the given parameters.");
    }

    /**
     * Registers a custom ActionType, can be use to override default actions.
     *
     * @param plugin the plugin registering the action
     * @param actionType the action type to register
     * @param action the action class to register
     */
    public static void registerAction(JavaPlugin plugin, ActionType actionType, Class<? extends Action> action) {
        if(actions.containsKey(actionType)) {
            plugin.getLogger().info("Action type " + actionType.getIdentifier() + " has been overrided.");
        }
        actions.put(actionType, action);
    }

    /**
     * Registers a custom ActionType
     *
     * @param plugin the plugin registering the action
     * @param actionType the action type to register
     */
    public static void registerAction(JavaPlugin plugin, ActionType actionType) {
        registerAction(plugin, actionType, actionType.getDefaultAction());
    }

    /**
     * Retrieves the action class corresponding to the given ActionType.
     *
     * @param actionType the action type
     * @return the action class
     */
    public static Class<? extends Action> getAction(ActionType actionType) {
        return getAction(actionType, false);
    }

    /**
     * Retrieves the action class corresponding to the given ActionType.
     *
     * @param actionType the action type
     * @param defaulted whether to use the default action
     * @return the action class
     */
    public static Class<? extends Action> getAction(ActionType actionType, boolean defaulted) {
        return defaulted ? actionType.getDefaultAction() : actions.get(actionType);
    }

    /**
     * Retrieves the ActionType corresponding to the given identifier.
     *
     * @param identifier the identifier of the action type
     * @return an Optional containing the ActionType if found, otherwise empty
     */
    public static Optional<ActionType> getByIdentifier(String identifier) {
        return actions.keySet().stream()
                .filter(actionType -> actionType.getIdentifier().equalsIgnoreCase(identifier))
                .findFirst();
    }

    /**
     * Loads a array of actions from a list of command strings.
     *
     * @param commands the list of command strings
     * @return the list of loaded actions
     */
    public static List<Action> loadActions(String... commands) {
        return ActionsAPI.loadActions(Arrays.asList(commands));
    }

    /**
     * Loads a list of actions from a list of command strings.
     *
     * @param commands the list of command strings
     * @return the list of loaded actions
     */
    public static List<Action> loadActions(List<String> commands) {

        List<Action> actions = new ArrayList<>();

        commands.forEach(command -> {

            String[] arguments = command.split(" ");
            if (arguments.length < 2) return;

            String identifier = arguments[0];
            Optional<ActionType> optional = ActionsAPI.getByIdentifier(identifier);

            if (!optional.isPresent()) {
                throw new IllegalArgumentException("No action type found for identifier " + identifier + ", maybe you forgot to register it?");
            };

            ActionType actionType = optional.get();

            int delay = 0;
            String content = command.replace(identifier + " ", "");

            if (content.contains("<delay:")) {
                int startIndex = content.indexOf("<delay:") + 7;
                int endIndex = content.indexOf(">", startIndex);
                delay = Integer.parseInt(content.substring(startIndex, endIndex));
                content = content.substring(endIndex + 1).trim();
            }

            Class<? extends Action> actionClzz = ActionsAPI.actions.get(actionType);
            Action action;
            try {
                action = ActionsAPI.instantiateAction(actionClzz, delay, content);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            actions.add(action);
        });

        return actions;
    }
}

