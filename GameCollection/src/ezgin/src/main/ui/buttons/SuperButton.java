package main.ui.buttons;

import main.GameController;

import javax.sound.sampled.Clip;
import java.awt.*;

import static utils.Load.getAudioClip;

/**
 * Superklasse für alle Buttons
 */
public class SuperButton {

    // Position und Größe des Buttons
    private int x, y, width, height;
    private int defaultX, defaultY;
    private Rectangle rectangle; // HitBox des Buttons

    private String text; // Text des Buttons
    private boolean hover, pressed; // Variablen zum Anzeigen und setzen des Zustands
    private Clip clip; // Sound des Buttons

    public SuperButton(int x, int y, int width, int height, String text) {
        setDefaultX(x);
        setDefaultY(y);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setRectangle(new Rectangle(x, y, width, height));
        setText(text);

        setClip(getAudioClip(text.equals("Play_Menu") ? "start.wav" : "transition_states.wav"));

        GameController.getInstance().getSfxClips().put(text + (this instanceof Switch ? "Switch" : this instanceof MiniButton ? "Mini" : ""), getClip());
    }

    /**
     * spielt den Sound des Buttons ab
     */
    public void playSound() {
        getClip().setFramePosition(0);
        getClip().start();
    }


    // GETTER UND SETTER


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDefaultX() {
        return defaultX;
    }

    public int getDefaultY() {
        return defaultY;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public String getText() {
        return text;
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public void setDefaultX(int defaultX) {
        this.defaultX = defaultX;
    }

    public void setDefaultY(int defaultY) {
        this.defaultY = defaultY;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setText(String text) {
        this.text = text;
    }
}