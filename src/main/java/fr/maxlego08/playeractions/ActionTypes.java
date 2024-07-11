package fr.maxlego08.playeractions;

import fr.maxlego08.playeractions.actions.*;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enumeration representing different types of actions.
 */
public enum ActionTypes implements ActionType {

    /**
     * Execute a command from the console.
     */
    CONSOLE("[console]", ConsoleCommand.class),

    /**
     * Execute a command from the player.
     */
    PLAYER("[player]", PlayerCommand.class),

    /**
     * Send a message to the menu viewer.
     */
    MESSAGE("[message]", Message.class),

    /**
     * Broadcast a message to the server.
     */
    BROADCAST("[broadcast]", Broadcast.class),

    /**
     * Send a chat message as the player performing the action.
     */
    CHAT("[chat]", Chat.class),

    /**
     * Close the viewers open menu.
     */
    CLOSE("[close]", Close.class);

    private final String identifier;
    private final Class<? extends Action> action;

    ActionTypes(String identifier, Class<? extends Action> action) {
        this.identifier = identifier;
        this.action = action;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Class<? extends Action> getAction() {
        return this.action;
    }
}

