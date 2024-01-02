package main.enums;

/**
 * Enum für die verschiedenen Spielzustände
 */
public enum GameState {
    MENU,
    IN_GAME,
    SETTINGS,
    PAUSE,
    GAME_OVER,
    WIN,
    CREDITS;

    private static GameState currentState = MENU;
    private static GameState previousState = MENU;


    // GETTER UND SETTER


    public static void setGameState(GameState state) {
        setPreviousState(getCurrentState());
        setCurrentState(state);
    }

    public static GameState getCurrentState() {
        return currentState;
    }

    public static GameState getPreviousState() {
        return previousState;
    }

    public static void setCurrentState(GameState currentState) {
        GameState.currentState = currentState;
    }

    public static void setPreviousState(GameState previousState) {
        GameState.previousState = previousState;
    }
}
