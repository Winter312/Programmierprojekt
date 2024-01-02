package main.ui.buttons;

import static utils.Constants.GameConstants.TILE_SIZE_DEFAULT;
import static utils.Constants.ImageConstants.BI_MINI_BUTTON;
import static utils.Constants.ImageConstants.BI_MINI_SELECTED;
import static utils.Constants.UIConstants.MENU_TILE_SIZE;

/**
 * Klasse für einen kleinen Button
 */
public class MiniButton extends DefaultButton {
    public MiniButton(int x, int y, String text) {
        super(x, y, text);
        setWidth(MENU_TILE_SIZE * 2);
        setHeight(MENU_TILE_SIZE * 2);
        getRectangle().setSize(getWidth(), getHeight());
        setImage(BI_MINI_BUTTON);
        setButtonHover(BI_MINI_SELECTED.getSubimage(0, 0, TILE_SIZE_DEFAULT * 2 + 10, TILE_SIZE_DEFAULT * 2 + 10));
        setButtonPressed(BI_MINI_SELECTED.getSubimage(TILE_SIZE_DEFAULT * 2 + 10, 0, TILE_SIZE_DEFAULT * 2 + 10, TILE_SIZE_DEFAULT * 2 + 10));
    }
}
