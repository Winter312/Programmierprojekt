package PixelPinesProtection.enemies;

import PixelPinesProtection.helperMethods.Constants;
import PixelPinesProtection.managers.EnemyManager;
import PixelPinesProtection.objects.WayPoint;
import PixelPinesProtection.scenes.Playing;

import java.awt.*;
import java.util.ArrayList;

import static PixelPinesProtection.helperMethods.Constants.Enemies.getSpeed;

public abstract class Enemy {
    protected Playing playing;

    protected EnemyManager enemyManager;
    protected float x, y;
    protected Rectangle bounds;
    protected int health;
    protected int maxHealth;
    protected int id;
    protected int enemyType;
    protected int currentWayPointIndex;
    protected ArrayList<WayPoint> waypoints = new ArrayList<>();
    protected boolean alive;

    /**
     * Konstruktor zum Erstellen von Enemy Objekt
     * @param x
     * @param y
     * @param id
     * @param enemyType
     */
    public Enemy(Playing playing, float x, float y, int id, int enemyType, EnemyManager enemyManager) {
        this.playing = playing;
        this.enemyManager = enemyManager;
        this.x = x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        bounds = new Rectangle((int) (x), (int) y, 22, 22);
        this.currentWayPointIndex = 0;
        setStartHealth();
        alive = true;
    }

    private void setStartHealth() {
        health = Constants.Enemies.getStartHealth(enemyType);
        maxHealth = health;
    }

    /**
     * Schädigt den Gegner und überprüft, ob er getötet wurde.
     */
    public void hurt(int dmg) {
        this.health -= dmg;
        if (health <= 0) {
            alive = false;
            enemyManager.rewardPlayer(enemyType);
        }
    }

    /**
     * Bewegt den Gegner und aktualisiert die Hitbox.
     */
    public void move(float x, float y) {
        this.x += x;
        this.y += y;

        updateHitbox();
    }

    private void updateHitbox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    /**
     * Bewegt den Gegner in Richtung des aktuellen Wegpunkts.
     */
    public void moveTowardWaypoint(ArrayList<WayPoint> waypoints) {
        WayPoint currentWaypoint = waypoints.get(currentWayPointIndex);

        float enemyX = this.getX();
        float enemyY = this.getY();

        // Bewegt den Gegner auf der X-Achse zum Wegpunkt
        if (Math.abs(currentWaypoint.x - enemyX) < getSpeed(this.getEnemyType())) {
            this.move(currentWaypoint.x - enemyX, 0);
        } else if (currentWaypoint.x > enemyX) {
            this.move(getSpeed(this.getEnemyType()), 0);
        } else {
            this.move(-getSpeed(this.getEnemyType()), 0);
        }

        // Bewegt den Gegner auf der Y-Achse zum Wegpunkt
        if (Math.abs(currentWaypoint.y - enemyY) < getSpeed(this.getEnemyType())) {
            this.move(0, currentWaypoint.y - enemyY);
        } else if (currentWaypoint.y > enemyY) {
            this.move(0, getSpeed(this.getEnemyType()));
        } else {
            this.move(0, -getSpeed(this.getEnemyType()));
        }
        // Überprüft, ob der letzte Wegpunkt erreicht wurde und entfernt das Leben des Spielers
        if (Math.abs(enemyX - currentWaypoint.x) < 0.1 && Math.abs(enemyY - currentWaypoint.y) < 0.1) {
            if (currentWayPointIndex < waypoints.size() - 1) {
                currentWayPointIndex++;
            } else {
                alive = false;
                health = 0;
                playing.removeLife();
            }
        }
    }


    public float getHealthBarFloat() {
        return health/(float) maxHealth;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public boolean isAlive() {
        return alive;
    }


}
