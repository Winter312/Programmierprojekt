package ezgin.src.main.gamestates;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import ezgin.src.main.GameController;
import ezgin.src.main.GamePanel;
import ezgin.src.main.enums.GameState;
import ezgin.src.main.ui.buttons.DefaultButton;

import static ezgin.src.utils.Constants.GameConstants.MUSIC_MENU;
import static ezgin.src.utils.Constants.ImageConstants.BI_MENU_BACKGROUND;
import static ezgin.src.utils.Constants.UIConstants.MENU_TILE_SIZE;
/**
 * Klasse, die den Menu-GameState repräsentiert
 */
public class Menu {

    private static Menu instance; // Singleton-Instanz
    private BufferedImage background; // Hintergrundbild
    private DefaultButton[] buttons; // Buttons

    private Menu() {
        setBackground(BI_MENU_BACKGROUND);
        setButtons(new DefaultButton[4]);
        getButtons()[0] = new DefaultButton(MENU_TILE_SIZE, MENU_TILE_SIZE * 6, "Play_Menu");
        getButtons()[1] = new DefaultButton(MENU_TILE_SIZE, MENU_TILE_SIZE * 9, "Settings_Menu");
        getButtons()[2] = new DefaultButton(MENU_TILE_SIZE, MENU_TILE_SIZE * 12, "Credits_Menu");
        getButtons()[3] = new DefaultButton(MENU_TILE_SIZE, MENU_TILE_SIZE * 15, "Exit_Menu");
        GameController.getInstance().getMusicClips().put("Menu", MUSIC_MENU);
    }

    /**
     * Gibt die Singleton-Instanz zurück
     *
     * @return die Singleton-Instanz
     */
    public static Menu getInstance() {
        if (instance == null) {
            setInstance(new Menu());
        }
        return instance;
    }

    /**
     * führt die Aktionen aus, die beim Bewegen der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        for (DefaultButton button : getButtons()) {
            button.setHover(button.getRectangle().contains(e.getPoint()));
        }
    }

    /**
     * führt die Aktionen aus, die beim Drücken der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (DefaultButton button : getButtons()) {
                if (button.getRectangle().contains(e.getPoint())) {
                    button.setPressed(true);
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
            for (DefaultButton button : getButtons()) {
                if (button.getRectangle().contains(e.getPoint()) && button.isPressed()) {
                    switch (button.getText()) {
                        case "Play_Menu":
                            GameState.setGameState(GameState.IN_GAME);
                            InGame.getInstance().setTimeSinceLastLevelChange(System.currentTimeMillis());
                            break;
                        case "Settings_Menu":
                            GameState.setGameState(GameState.SETTINGS);
                            break;
                        case "Credits_Menu":
                            GameState.setGameState(GameState.CREDITS);
                            break;
                        case "Exit_Menu":
                            try {
                                GamePanel.getInstance().quit();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            break;
                    }
                    button.playSound();
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

    public static void setInstance(Menu instance) {
        Menu.instance = instance;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public void setButtons(DefaultButton[] buttons) {
        this.buttons = buttons;
    }
}
