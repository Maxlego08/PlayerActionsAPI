package fr.maxlego08.playeractions;

import fr.maxlego08.playeractions.actions.Broadcast;
import fr.maxlego08.playeractions.actions.Chat;
import fr.maxlego08.playeractions.actions.Close;
import fr.maxlego08.playeractions.actions.ConsoleCommand;
import fr.maxlego08.playeractions.actions.Message;
import fr.maxlego08.playeractions.actions.PlayerCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Utility class for loading actions from a list of command strings.
 */
public class ActionsAPI {

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
            Optional<ActionType> optional = ActionType.getByIdentifier(identifier);

            if (!optional.isPresent()) return;

            ActionType actionType = optional.get();

            int delay = 0;
            String content = command.replace(identifier + " ", "");

            if (content.contains("<delay:")) {
                int startIndex = content.indexOf("<delay:") + 7;
                int endIndex = content.indexOf(">", startIndex);
                delay = Integer.parseInt(content.substring(startIndex, endIndex));
                content = content.substring(endIndex + 1).trim();
            }

            Action action = null;
            switch (actionType) {
                case CONSOLE:
                    action = new ConsoleCommand(delay, content);
                    break;
                case PLAYER:
                    action = new PlayerCommand(delay, content);
                    break;
                case MESSAGE:
                    action = new Message(delay, content);
                    break;
                case BROADCAST:
                    action = new Broadcast(delay, content);
                    break;
                case CHAT:
                    action = new Chat(delay, content);
                    break;
                case CLOSE:
                    action = new Close(delay);
                    break;
            }

            if (action != null) {
                actions.add(action);
            }
        });

        return actions;
    }
}

