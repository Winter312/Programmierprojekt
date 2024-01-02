package ezgin.src.main.gamestates;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import ezgin.src.main.enums.GameState;
import ezgin.src.main.ui.buttons.DefaultButton;
import ezgin.src.utils.Load;

import static ezgin.src.utils.Constants.ImageConstants.BI_CREDITS_BACKGROUND;
import static ezgin.src.utils.Constants.UIConstants.*;

/**
 * Klasse, die den Credits-GameState repräsentiert
 */
public class Credits {

    private static Credits instance;
    private BufferedImage background;
    private String[] credits;
    private DefaultButton button;

    private Credits() {
        setBackground(BI_CREDITS_BACKGROUND);
        setCredits(Load.getCredits());
        setButton(new DefaultButton(CREDITS_VALUES[0] + CREDITS_WIDTH / 2 - BUTTON_WIDTH / 2, CREDITS_VALUES[1] + CREDITS_HEIGHT - BUTTON_HEIGHT - MENU_TILE_SIZE / 2, "Back_Credits"));
    }

    /**
     * Gibt die Singleton-Instanz zurück
     *
     * @return die Singleton-Instanz
     */
    public static Credits getInstance() {
        if (instance == null) {
            setInstance(new Credits());
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
                GameState.setGameState(GameState.getPreviousState());
                getButton().playSound();
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

    public String[] getCredits() {
        return credits;
    }

    public static void setInstance(Credits instance) {
        Credits.instance = instance;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public void setCredits(String[] credits) {
        this.credits = credits;
    }

    public void setButton(DefaultButton button) {
        this.button = button;
    }
}
