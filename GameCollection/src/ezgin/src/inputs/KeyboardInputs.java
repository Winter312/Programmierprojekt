package inputs;

import main.enums.GameState;
import main.gamestates.InGame;
import main.gamestates.Pause;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasse für die Tastatureingaben
 */
public class KeyboardInputs implements KeyListener {

    // Singleton-Instanz
    private static KeyboardInputs instance;

    /**
     * gibt die Instanz der Klasse zurück
     * @return Instanz der Klasse
     */
    public static KeyboardInputs getInstance() {
        if (instance == null) {
            instance = new KeyboardInputs();
        }
        return instance;
    }
    /**
     * wird ausgeführt, wenn eine Taste gedrückt und wieder losgelassen wird
     * @param e KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * wird ausgeführt, wenn eine Taste gedrückt wird
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.getCurrentState()) {
            case MENU:
                break;
            case IN_GAME:
                InGame.getInstance().keyPressed(e);
                break;
            case PAUSE:
                Pause.getInstance().keyPressed(e);
                break;
        }
    }

    /**
     * wird ausgeführt, wenn eine Taste losgelassen wird
     * @param e KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.getCurrentState()) {
            case MENU:
                break;
            case IN_GAME:
                InGame.getInstance().keyReleased(e);
                break;
            case PAUSE:
                Pause.getInstance().keyReleased(e);
                break;
        }
    }
}
