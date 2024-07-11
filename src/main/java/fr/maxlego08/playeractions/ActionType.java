package fr.maxlego08.playeractions;

public interface ActionType {

    String getIdentifier();

    Class<? extends Action> getAction();

}
