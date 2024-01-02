package PixelPinesProtection.inputs;

import PixelPinesProtection.main.Game;
import PixelPinesProtection.main.GameStates;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * MyMouseListener verarbeitet Mausklicks und Mausbewegungen im Spiel.
 * Es implementiert MouseListener und MouseMotionListener, um auf verschiedene Mausereignisse zu reagieren.
 */
public class MyMouseListener implements MouseListener, MouseMotionListener {
    private Game game;
    public MyMouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {

            switch (GameStates.gameState) {
                case MENU:
                    game.getMenu().mouseClicked(e.getX(), e.getY());
                    break;
                case PLAYING:
                    game.getPlaying().mouseClicked(e.getX(), e.getY());
                    break;
                case HELP:
                    game.getHelp().mouseClicked(e.getX(), e.getY());
                    break;
                case GAME_OVER:
                    game.getGameover().mouseClicked(e.getX(),e.getY());
                    break;
                case GAME_WON:
                    game.getGamewon().mouseClicked(e.getX(), e.getY());
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mousePressed(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mousePressed(e.getX(), e.getY());
                break;
            case HELP:
                game.getHelp().mousePressed(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameover().mousePressed(e.getX(),e.getY());
                break;
            case GAME_WON:
                game.getGamewon().mousePressed(e.getX(), e.getY());
                break;
            default:
                break;

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mouseReleased(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseReleased(e.getX(), e.getY());
                break;
            case HELP:
                game.getHelp().mouseReleased(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameover().mouseReleased(e.getX(),e.getY());
                break;
            case GAME_WON:
                game.getGamewon().mouseReleased(e.getX(), e.getY());
                break;
            default:
                break;

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mouseMoved(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseMoved(e.getX(), e.getY());
                break;
            case HELP:
                game.getHelp().mouseMoved(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameover().mouseMoved(e.getX(),e.getY());
                break;
            case GAME_WON:
                game.getGamewon().mouseMoved(e.getX(), e.getY());
                break;
            default:
                break;

        }
    }
}
