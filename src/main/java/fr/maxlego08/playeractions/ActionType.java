package fr.maxlego08.playeractions;

import java.util.Arrays;
import java.util.Optional;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enumeration representing different types of actions.
 */
public enum ActionType {

    /**
     * Execute a command from the console.
     */
    CONSOLE("[console]"),

    /**
     * Execute a command from the player.
     */
    PLAYER("[player]"),

    /**
     * Send a message to the menu viewer.
     */
    MESSAGE("[message]"),

    /**
     * Broadcast a message to the server.
     */
    BROADCAST("[broadcast]"),

    /**
     * Send a chat message as the player performing the action.
     */
    CHAT("[chat]"),

    /**
     * Close the viewers open menu.
     */
    CLOSE("[close]");

    private final String identifier;

    ActionType(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Retrieves the ActionType corresponding to the given identifier.
     *
     * @param identifier the identifier of the action type
     * @return an Optional containing the ActionType if found, otherwise empty
     */
    public static Optional<ActionType> getByIdentifier(String identifier) {
        return Arrays.stream(values())
                .filter(actionType -> actionType.identifier.equalsIgnoreCase(identifier))
                .findFirst();
    }
}

