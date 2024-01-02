package scenes;

import main.Game;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.*;

/**
 * Diese Klasse stellt die Hilfeseite im Spiel dar.
 * Hier werden Anweisungen und Spielinformationen angezeigt.
 */
public class Help extends GameScene implements SceneMethods {
    private final String[] helpText;
    private MyButton bMenu;

    /**
     * Konstruktor der Help-Klasse.
     * @param game Das Hauptspielobjekt, das diese Szene steuert.
     */
    public Help(Game game) {
        super(game);
        // Button, um zum Hauptmenü zurückzukehren
        bMenu = new MyButton("Menu", 640 / 2 - 150 / 2, 600, 150, 150 / 3);

        // Array von String, das den Hilfetext enthält
        helpText = new String[]{
                "HOW TO PLAY THE GAME",
                "The goal of the game is to survive all waves and ",
                "defend against all computer viruses!",
                "-Select towers and place them with the left mouse button",
                "-Discard your selection with escape",
                "Upgrade your towers up to two times!",
                "A wave ends once all computer viruses are defeated!",
                "The next Wave will start automatically!",
                "Don't let them reach the end of the road!",
                "If too many reach it, its GAME OVER!"
        };
    }

    /**
     * Zeichnet die Hilfe-Szene.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    @Override
    public void render(Graphics g) {
        int panelWidth = 640;
        int startY = 100;
        int lineSpacing = 25;
        int firstLineSpacing = 45;

        // Zeichnet den Menü-Button
        bMenu.draw(g);

        g.setFont(new Font("LucidaSans", Font.BOLD, 15));
        FontMetrics fm = g.getFontMetrics();

        // Durchläuft alle Hilfetexte und zeichnet sie auf dem Bildschirm
        for (int i = 0; i < helpText.length; i++) {
            String line = helpText[i];
            int textWidth = fm.stringWidth(line);
            int x = (panelWidth - textWidth) / 2; // Zentriert den Text

            g.drawString(line, x, startY);

            // Vergrößert den Abstand nach der ersten Zeile
            startY += (i == 0) ? firstLineSpacing : lineSpacing;
        }
    }

    /**
     * Verarbeitet Mausklick-Ereignisse.
     * @param x Die X-Koordinate des Mausklicks.
     * @param y Die Y-Koordinate des Mausklicks.
     */
    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        }
    }

    /**
     * Verarbeitet Mausbewegungs-Ereignisse.
     * @param x Die X-Koordinate der Maus.
     * @param y Die Y-Koordinate der Maus.
     */
    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }

    /**
     * Verarbeitet Mausdruck-Ereignisse.
     * @param x Die X-Koordinate der Maus.
     * @param y Die Y-Koordinate der Maus.
     */
    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        }
    }

    /**
     * Verarbeitet Mausfreigabe-Ereignisse.
     * @param x Die X-Koordinate der Maus.
     * @param y Die Y-Koordinate der Maus.
     */
    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
    }
}
