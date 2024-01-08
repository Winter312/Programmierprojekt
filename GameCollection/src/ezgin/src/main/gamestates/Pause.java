package ezgin.src.main.gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import ezgin.src.main.GameController;
import ezgin.src.main.enums.GameState;
import ezgin.src.main.ui.buttons.DefaultButton;
import ezgin.src.main.ui.buttons.MiniButton;
import ezgin.src.main.ui.buttons.SuperButton;

import static ezgin.src.utils.Constants.ImageConstants.*;
import static ezgin.src.utils.Constants.UIConstants.MENU_TILE_SIZE;

/**
 * Klasse, die den Pause-GameState repräsentiert
 */
public class Pause {

    private static Pause instance; // Singleton-Instanz
    private final BufferedImage background; // Hintergrundbild
    private final HashMap<MiniButton, BufferedImage> buttons = new HashMap<>();

    private Pause() {
        background = BI_PAUSE_BACKGROUND;
        buttons.put(new MiniButton(MENU_TILE_SIZE, 6 * MENU_TILE_SIZE, "Back_Pause"), BI_PAUSE_BUTTONS[0]);
        buttons.put(new MiniButton(MENU_TILE_SIZE, 9 * MENU_TILE_SIZE, "Settings_Pause"), BI_PAUSE_BUTTONS[1]);
        buttons.put(new MiniButton(MENU_TILE_SIZE, 12 * MENU_TILE_SIZE, "GameOver_Pause"), BI_PAUSE_BUTTONS[2]);
    }

    /**
     * Gibt die Singleton-Instanz zurück
     *
     * @return die Singleton-Instanz
     */
    public static Pause getInstance() {
        if (instance == null) {
            instance = new Pause();
        }
        return instance;
    }

    /**
     * führt die Aktionen aus, die beim Bewegen der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        for (DefaultButton button : buttons.keySet()) {
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
            for (DefaultButton button : buttons.keySet()) {
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
            for (DefaultButton button : buttons.keySet()) {
                if (button.getRectangle().contains(e.getPoint()) && button.isPressed()) {
                    switch (button.getText()) {
                        case "Back_Pause":
                            GameState.setGameState(GameState.IN_GAME);
                            break;
                        case "Settings_Pause":
                            GameState.setGameState(GameState.SETTINGS);
                            break;
                        case "GameOver_Pause":
                            GameState.setGameState(GameState.GAME_OVER);
                            break;
                    }
                    button.playSound();
                }
                button.setHover(false);
                button.setPressed(false);
            }
        }
    }

    /**
     * führt die Aktionen aus, die beim Drücken einer Taste ausgeführt werden sollen
     *
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            buttons.keySet().stream().filter(button -> button.getText().contains("Back")).forEach(button -> {
                button.setHover(true);
                button.setPressed(true);
            });
        }
    }

    /**
     * führt die Aktionen aus, die beim Loslassen einer Taste ausgeführt werden sollen
     *
     * @param e KeyEvent
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (buttons.keySet().stream().anyMatch(SuperButton::isPressed)) {
                GameState.setGameState(GameState.IN_GAME);
                GameController.getInstance().getSfxClips().values().forEach(clip -> {
                    if (clip.getFramePosition() > 0) {
                        clip.start();
                    }
                });
                buttons.keySet().forEach(button -> {
                    button.setHover(false);
                    button.setPressed(false);
                });
            }
            if (InGame.getInstance().getButtons() != null) {
                for (int i = 0; i < InGame.getInstance().getButtons().length; i++) {
                    InGame.getInstance().updateButtonState(i, false);
                }
            }
        }
    }


    // GETTER UND SETTER


    public BufferedImage getBackground() {
        return background;
    }

    public HashMap<MiniButton, BufferedImage> getButtons() {
        return buttons;
    }

    public static void setInstance(Pause instance) {
        Pause.instance = instance;
    }
}
