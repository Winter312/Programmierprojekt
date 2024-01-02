package PixelPinesProtection.main;

import java.awt.*;

/**
 * Diese Klasse ist für das Rendern (Zeichnen) der verschiedenen Spielzustände verantwortlich.
 */
public class Render {

    private Game game; // Referenz auf das Game-Objekt

    /**
     * Konstruktor der Render-Klasse.
     *
     * @param game Das Game-Objekt, das gerendert werden soll.
     */
    public Render(Game game) {
        this.game = game;
    }

    /**
     * Render-Methode, die basierend auf dem aktuellen Spielzustand das entsprechende
     * Bildschirm-Element (Szene) rendert.
     *
     * @param g Das Graphics-Objekt, das zum Zeichnen verwendet wird.
     */
    public void render(Graphics g) {
        // Basierend auf dem aktuellen Spielzustand wird die entsprechende Szene gerendert
        switch(GameStates.gameState) {
            case MENU:
                game.getMenu().render(g);
                break;
            case PLAYING:
                game.getPlaying().render(g);
                break;
            case HELP:
                game.getHelp().render(g);
                break;
            case GAME_OVER:
                game.getGameover().render(g);
                break;
            case GAME_WON:
                game.getGamewon().render(g);
                break;
        }
    }
}
