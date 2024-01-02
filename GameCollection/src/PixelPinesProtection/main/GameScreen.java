package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

import javax.swing.JPanel;
import java.awt.*;

/**
 * GameScreen-Klasse, die das Hauptzeichnungsfeld für das Spiel darstellt.
 * Sie erbt von JPanel und ist für die Darstellung der Spielinhalte zuständig.
 */
public class GameScreen extends JPanel {
    private Game game;
    private Dimension size;

    // Listener für Maus- und Tastatureingaben
    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    /**
     * Konstruktor der GameScreen-Klasse.
     *
     * @param game Referenz auf das Hauptspielobjekt.
     */
    public GameScreen(Game game) {
        this.game = game;
        setPanelSize(); // Setzt die Größe des Panels
    }

    /**
     * Initialisiert die Eingabeverarbeitung für Maus und Tastatur.
     */
    public void initInputs() {
        myMouseListener = new MyMouseListener(game);
        keyboardListener = new KeyboardListener(game);

        addMouseListener(myMouseListener); // Mausklicks
        addMouseMotionListener(myMouseListener); // Mausbewegungen
        addKeyListener(keyboardListener); // Tastatureingaben

        requestFocus(); // Fordert den Fokus an, um Tastatureingaben zu empfangen
    }

    /**
     * Setzt die Größe des Panels.
     */
    private void setPanelSize() {
        size = new Dimension(640, 770); // Definiert die Dimension
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    /**
     * Überschreibt die paintComponent-Methode von JPanel.
     * Diese Methode wird automatisch aufgerufen, um das Panel zu zeichnen.
     *
     * @param g Das Graphics-Objekt, das für das Zeichnen verwendet wird.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Ruft die paintComponent-Methode der Superklasse auf
        game.getRender().render(g); // Delegiert das Rendering an die Render-Klasse
    }
}
