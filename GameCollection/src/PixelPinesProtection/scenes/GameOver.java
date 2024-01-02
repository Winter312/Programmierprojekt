package scenes;

import main.Game;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.*;

/**
 * Diese Klasse repräsentiert die Game-Over-Szene des Spiels.
 */
public class GameOver extends GameScene implements SceneMethods {

    private MyButton bReplay, bMenu;
    private Game game;

    /**
     * Konstruktor für die Game-Over-Szene.
     * @param game Das Hauptspielobjekt, das diese Szene steuert.
     */
    public GameOver(Game game) {
        super(game);
        this.game = game;
        initButtons();
    }

    /**
     * Initialisiert die Buttons für die Game-Over-Szene.
     */
    private void initButtons() {
        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 250;
        int yOffset = 100;

        bMenu = new MyButton("Menu", x, y, w, h);
        bReplay = new MyButton("Replay", x, y + yOffset, w, h);
    }

    /**
     * Zeichnet die Game-Over-Szene.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    @Override
    public void render(Graphics g) {
        g.setFont(new Font("LucidaSans", Font.BOLD, 50));
        g.setColor(Color.red);
        g.drawString("GAME OVER!", 160, 120);

        g.setFont(new Font("LucidaSans", Font.BOLD, 15));

        bMenu.draw(g);
        bReplay.draw(g);
    }

    /**
     * Startet das Spiel erneut, wenn der "Replay"-Button gedrückt wird.
     */
    private void replayGame() {
        resetAll();
        SetGameState(PLAYING);
    }

    /**
     * Setzt das Spiel zurück, wenn ein neues Spiel gestartet wird.
     */
    private void resetAll() {
        game.getPlaying().resetEverything();
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
            resetAll();
        } else if (bReplay.getBounds().contains(x, y)) {
            replayGame();
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
        bReplay.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if (bReplay.getBounds().contains(x, y)) {
            bReplay.setMouseOver(true);
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
        } else if (bReplay.getBounds().contains(x, y)) {
            bReplay.setMousePressed(true);
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
        bReplay.resetBooleans();
    }
}
