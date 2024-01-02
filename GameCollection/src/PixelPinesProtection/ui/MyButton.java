package PixelPinesProtection.ui;

import java.awt.*;

/**
 *  benutzerdefinierte Schaltflächen-Komponenten, wird in Benutzeroberfläche verwendet
 */
public class MyButton {
    // Koordinaten und Dimensionen der Schaltfläche
    public int x, y, width, height, id;
    private String text;

    // Rechteckige Grenze der Schaltfläche
    private Rectangle bounds;
    // Zustände für Mausinteraktionen
    private boolean mouseOver, mousePressed;

    /**
     * Konstruktor für Schaltflächen ohne ID.
     */
    public MyButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initBounds();
    }

    /**
     * Konstruktor für Schaltflächen mit ID.
     */
    public MyButton(String text, int x, int y, int width, int height, int id) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        initBounds();
    }

    /**
     * Initialisiert die rechteckige Grenze der Schaltfläche basierend auf ihren Koordinaten und Dimensionen.
     */
    public void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Zeichnet die Schaltfläche, inklusive Körper, Rahmen und Text.
     */
    public void draw(Graphics g) {
        drawBody(g); // Zeichnet den Körper der Schaltfläche
        drawBorder(g); // Zeichnet den Rand der Schaltfläche
        drawText(g); // Zeichnet den Text auf der Schaltfläche
    }

    /**
     * Zeichnet den Rahmen der Schaltfläche.
     */
    private void drawBorder(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
        if (mousePressed) {
            // Zeichnet einen dickeren Rand, wenn die Schaltfläche gedrückt wird
            g.drawRect(x + 1, y + 1, width - 2, height - 2);
            g.drawRect(x + 2, y + 2, width - 4, height - 4);
        }
    }

    /**
     * Zeichnet den Körper der Schaltfläche.
     */
    private void drawBody(Graphics g) {
        if (mouseOver) {
            g.setColor(Color.gray); // Farbänderung bei Mausüberlagerung
        } else {
            g.setColor(Color.white);
        }
        g.fillRect(x, y, width, height);
    }

    /**
     * Zeichnet den Text auf der Schaltfläche.
     */
    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 3);
    }

    /**
     * Setzt die Mausinteraktionsbooleans zurück.
     */
    public void resetBooleans() {
        this.mouseOver = false;
        this.mousePressed = false;
    }

    // Getter und Setter für Mausinteraktionen und Eigenschaften der Schaltfläche
    public void setMousePressed(boolean mousePressed) { this.mousePressed = mousePressed; }
    public void setMouseOver(boolean mouseOver) { this.mouseOver = mouseOver; }
    public boolean isMouseOver() { return mouseOver; }
    public boolean isMousePressed() { return mousePressed; }
    public Rectangle getBounds() { return bounds; }
    public int getId() { return id; }
}
