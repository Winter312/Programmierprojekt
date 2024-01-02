package main.gamestates;

import main.ui.buttons.DefaultButton;
import main.enums.GameState;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.ImageConstants.BI_GAME_OVER_BACKGROUND;
import static utils.Constants.UIConstants.MENU_TILE_SIZE;

/**
 * Klasse, die das GameOver-Menü repräsentiert
 */
public class GameOver {

    private static GameOver instance; // Singleton-Instanz
    private BufferedImage background; // Hintergrundbild
    private DefaultButton button; // Button

    private GameOver() {
        setBackground(BI_GAME_OVER_BACKGROUND);
        setButton(new DefaultButton(14 * MENU_TILE_SIZE, 11 * MENU_TILE_SIZE, "Menu_GameOver"));
    }

    /**
     * Gibt die Singleton-Instanz zurück
     *
     * @return die Singleton-Instanz
     */
    public static GameOver getInstance() {
        if (instance == null) {
            setInstance(new GameOver());
        }
        return instance;
    }

    /**
     * führt die Aktionen aus, die beim Bewegen der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        getButton().setHover(getButton().getRectangle().contains(e.getPoint()));
    }

    /**
     * führt die Aktionen aus, die beim Drücken der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (getButton().getRectangle().contains(e.getPoint())) {
                getButton().setPressed(true);
            }
        }
    }

    /**
     * führt die Aktionen aus, die beim Loslassen der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (getButton().getRectangle().contains(e.getPoint()) && getButton().isPressed()) {
                getButton().playSound();
                GameState.setGameState(GameState.MENU);
                InGame.getInstance().reset();
            }
            getButton().setHover(false);
            getButton().setPressed(false);
        }
    }


    // GETTER UND SETTER


    public BufferedImage getBackground() {
        return background;
    }

    public DefaultButton getButton() {
        return button;
    }

    public static void setInstance(GameOver instance) {
        GameOver.instance = instance;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public void setButton(DefaultButton button) {
        this.button = button;
    }
}
