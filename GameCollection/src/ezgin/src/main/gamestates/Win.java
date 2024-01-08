package ezgin.src.main.gamestates;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import ezgin.src.main.enums.GameState;
import ezgin.src.main.ui.buttons.DefaultButton;

import static ezgin.src.utils.Constants.GameConstants.SCREEN_WIDTH;
import static ezgin.src.utils.Constants.ImageConstants.BI_GAME_OVER_BACKGROUND;
import static ezgin.src.utils.Constants.UIConstants.BUTTON_WIDTH;
import static ezgin.src.utils.Constants.UIConstants.MENU_TILE_SIZE;

/**
 * Klasse, die den Win-GameState repräsentiert
 */
public class Win {

    private static Win instance; // Singleton-Instanz
    private final BufferedImage background = BI_GAME_OVER_BACKGROUND; // Hintergrundbild
    private final DefaultButton[] buttons = new DefaultButton[2];

    private Win() {
        buttons[0] = new DefaultButton(SCREEN_WIDTH / 2 - MENU_TILE_SIZE / 4 - BUTTON_WIDTH, 11 * MENU_TILE_SIZE, "Menu_Win");
        buttons[1] = new DefaultButton(SCREEN_WIDTH / 2 + MENU_TILE_SIZE / 4, 11 * MENU_TILE_SIZE, "Credits_Win");
    }

    /**
     * Gibt die Singleton-Instanz zurück
     *
     * @return die Singleton-Instanz
     */
    public static Win getInstance() {
        if (instance == null) {
            instance = new Win();
        }
        return instance;
    }

    /**
     * führt die Aktionen aus, die beim Bewegen der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        for (DefaultButton defaultButton : buttons) {
            defaultButton.setHover(defaultButton.getRectangle().contains(e.getPoint()));
        }
    }

    /**
     * führt die Aktionen aus, die beim Drücken der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (DefaultButton defaultButton : buttons) {
                if (defaultButton.getRectangle().contains(e.getPoint())) {
                    defaultButton.setPressed(true);
                }
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
            for (DefaultButton button : buttons) {
                if (button.getRectangle().contains(e.getPoint()) && button.isPressed()) {
                    button.playSound();
                    switch (button.getText()) {
                        case "Menu_Win":
                            GameState.setGameState(GameState.MENU);
                            InGame.getInstance().reset();
                            break;
                        case "Credits_Win":
                            GameState.setGameState(GameState.CREDITS);
                            break;
                    }
                }
                button.setHover(false);
                button.setPressed(false);
            }
        }
    }


    // GETTER UND SETTER


    public BufferedImage getBackground() {
        return background;
    }

    public DefaultButton[] getButtons() {
        return buttons;
    }

    public static void setInstance(Win instance) {
        Win.instance = instance;
    }
}
