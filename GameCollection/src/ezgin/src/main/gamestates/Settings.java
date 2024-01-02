package ezgin.src.main.gamestates;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import ezgin.src.main.GameController;
import ezgin.src.main.enums.GameState;
import ezgin.src.main.ui.buttons.DefaultButton;
import ezgin.src.main.ui.buttons.SuperButton;
import ezgin.src.main.ui.buttons.Switch;


import static ezgin.src.utils.Constants.ImageConstants.BI_SETTINGS_BACKGROUND;
import static ezgin.src.utils.Constants.UIConstants.MENU_TILE_SIZE;
import static ezgin.src.utils.Constants.UIConstants.settingsValues;
/**
 * Klasse, die den Settings-GameState repräsentiert
 */
public class Settings {

    private static Settings instance; // Singleton-Instanz
    private BufferedImage background; // Hintergrundbild
    private SuperButton[] buttons = new SuperButton[5];

    private Settings() {
        setBackground(BI_SETTINGS_BACKGROUND);
        getButtons()[0] = new DefaultButton(MENU_TILE_SIZE, (int) (0.5 * MENU_TILE_SIZE), "Music_Button");
        getButtons()[1] = new DefaultButton(MENU_TILE_SIZE, (int) (2.75 * MENU_TILE_SIZE), "Sound_Button");
        getButtons()[2] = new DefaultButton(MENU_TILE_SIZE, (int) (8.5 * MENU_TILE_SIZE), "Back_Settings");
        getButtons()[3] = new Switch((int) (getButtons()[0].getX() + getButtons()[0].getWidth() + MENU_TILE_SIZE * 1.5), getButtons()[0].getY() + MENU_TILE_SIZE / 4, MENU_TILE_SIZE * 3, (int) (MENU_TILE_SIZE * 1.5), "Music_Switch");
        getButtons()[4] = new Switch((int) (getButtons()[1].getX() + getButtons()[1].getWidth() + MENU_TILE_SIZE * 1.5), getButtons()[1].getY() + MENU_TILE_SIZE / 4, MENU_TILE_SIZE * 3, (int) (MENU_TILE_SIZE * 1.5), "Sound_Switch");
    }

    /**
     * Gibt die Singleton-Instanz zurück
     *
     * @return die Singleton-Instanz
     */
    public static Settings getInstance() {
        if (instance == null) {
            setInstance(new Settings());
        }
        return instance;
    }

    /**
     * initialisiert die Positionen der Buttons
     */
    public void initValues() {
        for (SuperButton button : getButtons()) {
            button.setX(button.getDefaultX() + settingsValues()[0]);
            button.setY(button.getDefaultY() + settingsValues()[1]);
            button.getRectangle().setLocation(button.getX(), button.getY());
        }

    }

    /**
     * führt die Aktionen aus, die beim Bewegen der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        for (SuperButton button : getButtons()) {
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
            for (SuperButton button : getButtons()) {
                if (button.isHover()) {
                    button.setPressed(true);
                    if (button instanceof Switch) {
                        if (((Switch) button).isShow()) {
                            ((Switch) button).setState(e.getX() >= button.getX() + button.getWidth() / 2);
                        }
                    }
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
            for (SuperButton button : getButtons()) {
                if (button.isHover() && button.isPressed()) {
                    switch (button.getText()) {
                        case "Back_Settings":
                            for (SuperButton button1 : getButtons()) {
                                if (button1 instanceof Switch) {
                                    ((Switch) button1).setShow(false);
                                }
                            }
                            button.setHover(false);
                            GameState.setGameState(GameState.getPreviousState());
                            break;
                        case "Music_Switch":
                            GameController.getInstance().setMusicMuted(!((Switch) button).getState());
                            break;
                        case "Sound_Switch":
                            GameController.getInstance().setSfxMuted(!((Switch) button).getState());
                            break;
                        case "Music_Button":
                        case "Sound_Button":
                            Arrays.stream(getButtons()).filter(button1 -> button1 instanceof Switch && button1.getText().substring(0, button1.getText().indexOf("_")).equals(button.getText().substring(0, button.getText().indexOf("_")))).forEach(button1 -> ((Switch) button1).setShow(!((Switch) button1).isShow()));
                            break;
                    }
                    button.playSound();
                }
                button.setPressed(false);
            }
        }
    }


    // GETTER UND SETTER


    public BufferedImage getBackground() {
        return background;
    }

    public SuperButton[] getButtons() {
        return buttons;
    }

    public static void setInstance(Settings instance) {
        Settings.instance = instance;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public void setButtons(SuperButton[] buttons) {
        this.buttons = buttons;
    }
}
