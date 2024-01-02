package main.ui.buttons;

import java.awt.image.BufferedImage;

import static utils.Constants.ImageConstants.BI_SWITCH;
import static utils.Constants.ImageConstants.BI_SWITCH_BACKGROUND;

/**
 * Klasse für den Schalter
 */
public class Switch extends SuperButton {

    // Bilder für den Schalter
    private BufferedImage background;
    private BufferedImage[] images;

    private boolean show, state = true; // Attribute zum Anzeigen und setzen des Zustands

    public Switch(int x, int y, int width, int height, String text) {
        super(x, y, width, height, text);
        setBackground(BI_SWITCH_BACKGROUND);
        setImages(new BufferedImage[]{BI_SWITCH.getSubimage(0, 0, 64, 32), BI_SWITCH.getSubimage(64, 0, 64, 32)});
    }


    // GETTER UND SETTER


    public BufferedImage[] getImages() {
        return images;
    }

    public BufferedImage getBackground() {
        return background;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public void setImages(BufferedImage[] images) {
        this.images = images;
    }
}
