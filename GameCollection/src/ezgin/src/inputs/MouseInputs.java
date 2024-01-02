package inputs;

import main.enums.GameState;
import main.gamestates.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Klasse für die Maus-Eingaben
 */
public class MouseInputs implements MouseListener, MouseMotionListener {

    // Singleton-Instanz
    private static MouseInputs instance;

    /**
     * gibt die Instanz der Klasse zurück
     * @return Instanz der Klasse
     */
    public static MouseInputs getInstance() {
        if (instance == null) {
            instance = new MouseInputs();
        }
        return instance;
    }

    /**
     * wird ausgeführt, wenn die Maus geklickt und wieder losgelassen wird
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * wird ausgeführt, wenn die Maus geklickt wird
     * @param e MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.getCurrentState()) {
            case MENU:
                Menu.getInstance().mousePressed(e);
                break;
            case IN_GAME:
                InGame.getInstance().mousePressed(e);
                break;
            case SETTINGS:
                Settings.getInstance().mousePressed(e);
                break;
            case CREDITS:
                Credits.getInstance().mousePressed(e);
                break;
            case PAUSE:
                Pause.getInstance().mousePressed(e);
                break;
            case GAME_OVER:
                GameOver.getInstance().mousePressed(e);
                break;
            case WIN:
                Win.getInstance().mousePressed(e);
                break;
        }
    }

    /**
     * wird ausgeführt, wenn die Maus losgelassen wird
     * @param e MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.getCurrentState()) {
            case MENU:
                Menu.getInstance().mouseReleased(e);
                break;
            case IN_GAME:
                InGame.getInstance().mouseReleased(e);
                break;
            case CREDITS:
                Credits.getInstance().mouseReleased(e);
                break;
            case SETTINGS:
                Settings.getInstance().mouseReleased(e);
                break;
            case PAUSE:
                Pause.getInstance().mouseReleased(e);
                break;
            case GAME_OVER:
                GameOver.getInstance().mouseReleased(e);
                break;
            case WIN:
                Win.getInstance().mouseReleased(e);
                break;
        }
    }

    /**
     * wird ausgeführt, wenn die Maus in das Fenster kommt
     * @param e MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * wird ausgeführt, wenn die Maus das Fenster verlässt
     * @param e MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * wird ausgeführt, wenn die Maus bewegt wird und eine Taste geklickt ist
     * @param e MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * wird ausgeführt, wenn die Maus bewegt wird
     * @param e MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.getCurrentState()) {
            case MENU:
                Menu.getInstance().mouseMoved(e);
                break;
            case SETTINGS:
                Settings.getInstance().mouseMoved(e);
                break;
            case CREDITS:
                Credits.getInstance().mouseMoved(e);
                break;
            case IN_GAME:
                InGame.getInstance().mouseMoved(e);
                break;
            case PAUSE:
                Pause.getInstance().mouseMoved(e);
                break;
            case GAME_OVER:
                GameOver.getInstance().mouseMoved(e);
                break;
            case WIN:
                Win.getInstance().mouseMoved(e);
                break;
        }
    }
}
