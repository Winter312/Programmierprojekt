package main;

/**
 * Game-Klasse
 * ist die Hauptklasse des Spiels, erstellt ein GamePanel und startet den Game-Controller
 */
public class Game {

    public Game() {
        GamePanel.getInstance();
        GameController.getInstance().startGameThread();
    }
}
