package main.ui.buttons;

import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.TILE_SIZE_DEFAULT;
import static utils.Constants.ImageConstants.BI_BUTTON;
import static utils.Constants.ImageConstants.BI_SELECTED;
import static utils.Constants.UIConstants.BUTTON_HEIGHT;
import static utils.Constants.UIConstants.BUTTON_WIDTH;

/**
 * Klasse für den Standard-Button
 */
public class DefaultButton extends SuperButton {

    // Bilder für Button und Selektion
    private BufferedImage image;
    private BufferedImage buttonHover;
    private BufferedImage buttonPressed;

    public DefaultButton(int x, int y, String text) {
        super(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, text);
        setImage(BI_BUTTON);
        setButtonHover(BI_SELECTED.getSubimage(0, 0, TILE_SIZE_DEFAULT * 8 + 10, TILE_SIZE_DEFAULT * 2 + 10));
        setButtonPressed(BI_SELECTED.getSubimage(TILE_SIZE_DEFAULT * 8 + 10, 0, TILE_SIZE_DEFAULT * 8 + 10, TILE_SIZE_DEFAULT * 2 + 10));
    }


    // GETTER UND SETTER


    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getButtonHover() {
        return buttonHover;
    }

    public BufferedImage getButtonPressed() {
        return buttonPressed;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setButtonHover(BufferedImage buttonHover) {
        this.buttonHover = buttonHover;
    }

    public void setButtonPressed(BufferedImage buttonPressed) {
        this.buttonPressed = buttonPressed;
    }
}
