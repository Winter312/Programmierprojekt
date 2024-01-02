package PixelPinesProtection.scenes;

import java.awt.*;

/**
 * Interface für Szenen im Spiel.
 * Definiert grundlegende Methoden, die jede Szene implementieren sollte.
 */
public interface SceneMethods {

    /**
     * Methode zum Zeichnen der Szene.
     * @param g Grafikobjekt, das für das Zeichnen verwendet wird.
     */
    public void render(Graphics g);

    /**
     * Methode, die aufgerufen wird, wenn in der Szene mit der Maus geklickt wird.
     * @param x X-Koordinate des Mausklicks.
     * @param y Y-Koordinate des Mausklicks.
     */
    public void mouseClicked(int x, int y);

    /**
     * Methode, die aufgerufen wird, wenn die Maus in der Szene bewegt wird.
     * @param x X-Koordinate der Mausposition.
     * @param y Y-Koordinate der Mausposition.
     */
    public void mouseMoved(int x, int y);

    /**
     * Methode, die aufgerufen wird, wenn eine Maustaste in der Szene gedrückt wird.
     * @param x X-Koordinate der Mausposition beim Drücken.
     * @param y Y-Koordinate der Mausposition beim Drücken.
     */
    public void mousePressed(int x, int y);

    /**
     * Methode, die aufgerufen wird, wenn eine Maustaste in der Szene losgelassen wird.
     * @param x X-Koordinate der Mausposition beim Loslassen.
     * @param y Y-Koordinate der Mausposition beim Loslassen.
     */
    public void mouseReleased(int x, int y);
}
