package objects;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Klasse für Projektile, die von den Türmen abgefeuert werden.
 */
public class Projectile {
    private Point2D.Float pos;  // Die Position des Projektils
    private int id, projectileType, dmg;  // ID, Typ und Schaden des Projektils
    private float xSpeed, ySpeed, rotation;  // Geschwindigkeit in X- und Y-Richtung, sowie Rotationswinkel
    private boolean active = true;  // Status des Projektils (aktiv/inaktiv)
    public Rectangle bounds;  // Hitbox des Projektils

    /**
     * Konstruktor für ein Projektil.
     * @param x Startposition X
     * @param y Startposition Y
     * @param xSpeed Geschwindigkeit in X-Richtung
     * @param ySpeed Geschwindigkeit in Y-Richtung
     * @param dmg Schaden des Projektils
     * @param rotation Rotationswinkel
     * @param id ID des Projektils
     * @param projectileType Typ des Projektils
     */
    public Projectile(float x, float y, float xSpeed, float ySpeed, int dmg, float rotation, int id, int projectileType) {
        pos = new Point2D.Float(x + 16, y + 16);  // Zentrierung des Projektils
        this.id = id;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.dmg = dmg;
        this.rotation = rotation;
        this.projectileType = projectileType;
        this.bounds = new Rectangle((int) pos.x, (int) pos.y, 32, 32);  // Initialisierung der Hitbox
    }

    /**
     * Aktualisiert die Position des Projektils basierend auf seiner Geschwindigkeit.
     */
    public void move() {
        pos.x += xSpeed;
        pos.y += ySpeed;
        bounds.x = (int) pos.x - 16;  // Anpassung der Hitbox-Position
        bounds.y = (int) pos.y - 16;
    }

    public Point2D.Float getPos() {
        return pos;
    }

    public void setPos(Point2D.Float pos) {
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(int projectileType) {
        this.projectileType = projectileType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public int getDmg() {
        return dmg;
    }

    public float getRotation() {
        return rotation;
    }
}
