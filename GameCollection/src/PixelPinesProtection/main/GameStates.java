package main;

/**
 * Enum für die Verwaltung verschiedener Spielzustände.
 */
public enum GameStates {

    // Definition der verschiedenen möglichen Zustände des Spiels
    PLAYING,    // Zustand, wenn das Spiel aktiv gespielt wird
    MENU,       // Zustand für das Hauptmenü
    HELP,       // Zustand für den Hilfe-Bildschirm
    GAME_OVER,  // Zustand für das Spielende (Verloren)
    GAME_WON;   // Zustand für das Spielende (Gewonnen)

    // Aktueller Zustand des Spiels, standardmäßig auf MENU gesetzt
    public static GameStates gameState = MENU;

    /**
     * Setzt den aktuellen Spielzustand.
     *
     * @param state Der neue Zustand, der gesetzt werden soll.
     */
    public static void SetGameState(GameStates state) {
        gameState = state;
    }
}
