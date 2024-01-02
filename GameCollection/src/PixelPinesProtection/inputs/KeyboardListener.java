package PixelPinesProtection.inputs;


import PixelPinesProtection.main.Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static PixelPinesProtection.main.GameStates.*;

/**
 * KeyboardListener verarbeitet Tastatureingaben und reagiert auf bestimmte Tastendrücke.
 * Es ist ein KeyListener, der Tastatureingaben im Spiel überwacht.
 */
public class KeyboardListener implements KeyListener {
    private Game game;
    public KeyboardListener(Game game) {
        this.game = game;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == PLAYING) {
            game.getPlaying().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
