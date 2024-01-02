package PixelPinesProtection.scenes;
import PixelPinesProtection.main.Game;

/**
 * Basisklasse für alle Spielszenen.
 * Enthält grundlegende Eigenschaften und Methoden, die von spezifischen Szenen genutzt werden.
 */
public class GameScene {

    private Game game; // Referenz auf das Hauptspielobjekt
    protected int animationIndex; // Index für die Animation
    protected int ANIMATION_SPEED = 25; // Geschwindigkeit der Animation
    protected int tick; // Zähler für die Aktualisierungen

    /**
     * Konstruktor der Klasse GameScene.
     * @param game Referenz auf das Hauptspielobjekt
     */
    public GameScene(Game game) {
        this.game = game;
    }

    /**
     * Gibt das Spielobjekt zurück.
     * @return Referenz auf das Spielobjekt.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Aktualisiert den Tick-Zähler für Animationen.
     * Erhöht den animationIndex bei Erreichen der ANIMATION_SPEED-Grenze.
     */
    protected void updateTick() {
        tick++;
        if (tick >= ANIMATION_SPEED) {
            tick = 0;
            animationIndex++;
            if (animationIndex >= 4) {
                animationIndex = 0;
            }
        }
    }
}
