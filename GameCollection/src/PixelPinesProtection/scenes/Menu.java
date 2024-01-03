package PixelPinesProtection.scenes;

import PixelPinesProtection.main.Game;
import PixelPinesProtection.ui.MyButton;

import java.awt.*;
import static PixelPinesProtection.main.GameStates.*;

/**
 * Die Menu-Klasse repräsentiert das Hauptmenü des Spiels.
 */
public class Menu extends GameScene implements SceneMethods {

    private MyButton bPlaying, bHelp, bQuit;

    /**
     * Konstruktor für die Menu-Klasse.
     * @param game Das Hauptspielobjekt, das diese Szene steuert.
     */
    public Menu(Game game) {
        super(game);
        initButtons();
    }

    /**
     * Initialisiert die Schaltflächen im Menü.
     */
    private void initButtons() {
        int w = 150;  // Breite der Schaltflächen
        int h = w / 3;  // Höhe der Schaltflächen
        int x = 640 / 2 - w / 2;  // Zentrieren der Schaltflächen auf der X-Achse
        int y = 150;  // Startposition auf der Y-Achse
        int yOffset = 100;  // Abstand zwischen den Schaltflächen

        // Erstellen der Schaltflächen mit Text, Position und Größe
        bPlaying = new MyButton("Play", x, y, w, h);
        bHelp = new MyButton("Help", x, y + yOffset, w, h);
        bQuit = new MyButton("Quit", x, y + yOffset * 2, w, h);
    }

    /**
     * Zeichnet das Menü.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    @Override
    public void render(Graphics g) {
        drawButtons(g);
    }

    /**
     * Verarbeitet Mausklick-Ereignisse.
     * @param x Die X-Koordinate des Mausklicks.
     * @param y Die Y-Koordinate des Mausklicks.
     */
    @Override
    public void mouseClicked(int x, int y) {
        if (bPlaying.getBounds().contains(x, y)) {
            SetGameState(PLAYING);
        } else if (bHelp.getBounds().contains(x, y)) {
            SetGameState(HELP);
        } else if (bQuit.getBounds().contains(x, y)) {
            Game.running = false;
            Game.instance.dispose();
        }
    }

    /**
     * Verarbeitet Mausbewegungs-Ereignisse.
     * @param x Die X-Koordinate der Maus.
     * @param y Die Y-Koordinate der Maus.
     */
    @Override
    public void mouseMoved(int x, int y) {
        bPlaying.setMouseOver(false);
        bHelp.setMouseOver(false);
        bQuit.setMouseOver(false);

        // Aktualisiert den MouseOver-Status jeder Schaltfläche
        if (bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMouseOver(true);
        } else if (bHelp.getBounds().contains(x, y)) {
            bHelp.setMouseOver(true);
        } else if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMouseOver(true);
        }
    }

    /**
     * Verarbeitet Mausdruck-Ereignisse.
     * @param x Die X-Koordinate der Maus.
     * @param y Die Y-Koordinate der Maus.
     */
    @Override
    public void mousePressed(int x, int y) {
        // Setzt den MousePressed-Status entsprechend der gedrückten Schaltfläche
        if (bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMousePressed(true);
        } else if (bHelp.getBounds().contains(x, y)) {
            bHelp.setMousePressed(true);
        } else if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMousePressed(true);
        }
    }

    /**
     * Verarbeitet Mausfreigabe-Ereignisse.
     * @param x Die X-Koordinate der Maus.
     * @param y Die Y-Koordinate der Maus.
     */
    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    /**
     * Setzt den Zustand aller Schaltflächen zurück.
     */
    private void resetButtons() {
        bPlaying.resetBooleans();
        bHelp.resetBooleans();
        bQuit.resetBooleans();
    }

    /**
     * Zeichnet die Schaltflächen auf dem Bildschirm.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    private void drawButtons(Graphics g) {
        bPlaying.draw(g);
        bHelp.draw(g);
        bQuit.draw(g);
    }
}
