package ezgin.src.main.entities.livingentities;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

import ezgin.src.main.entities.SuperLivingEntity;
import ezgin.src.main.gamestates.InGame;
import ezgin.src.main.ui.hud.Inventory;

import static ezgin.src.main.enums.Level.*;
import static ezgin.src.utils.Constants.GameConstants.*;
import static ezgin.src.utils.Constants.ImageConstants.*;
import static ezgin.src.utils.Constants.SpawnConstants.getPlayerSpawn;
import static ezgin.src.utils.Constants.EntitySpriteConstants.*;

/**
 * Klasse für den Spieler
 */
public class Player extends SuperLivingEntity {

    private Inventory inventory; // Inventar des Spielers
    private long timeSinceDeath; // Zeit seit dem Tod des Spielers
    private int lastEnemyMadeDamageID; // ID des letzten Gegners, der Schaden verursacht hat

    public Player() {
        super(-1, getPlayerSpawn()[0], getPlayerSpawn()[1], getPlayerPath(0));

        // legt Standardwerte für den Spieler fest
        setSpriteRow(getCurrentLevel() == LEVEL_ZERO ? RUN_RIGHT : RUN_UP);
        setAttackDamage(0);
        setMaxLifePoints(50);
        setCurrentLifePoints(getMaxLifePoints());
        setRunSpeed(0.75f * SCALE);
        setShield(0);
        setDefaultSpeed(0.5f * SCALE);
        setInventory(new Inventory(this));

        // initialisiert die Range
        setRangeOffsetX(0);
        setRangeOffsetY(0);
        setRange(new Rectangle(getRangeOffsetX(), getRangeOffsetY(), 0, 0));
    }

    /**
     * aktualisiert den Spieler
     */
    public void update() {
        updateScreenValues();
        if (isAlive()) {
            setPos(getScreenY());
            updateWorldValuesAndAnimation();
            heal();
            checkCollision();
            setTimeSinceDeath(System.currentTimeMillis());
        } else {
            if (notLocked()) {
                resetMovement();
                setSpriteRow(DEATH);
                setLock(true);
            }
            setPos(getScreenY() - TILE_SIZE / 3f);
            updateAnimationTick();
        }
        getLifePoints().update();
    }

    /**
     * aktualisiert die Position des Spielers auf dem Bildschirm
     */
    @Override
    public void updateScreenValues() {
        // Mitte des Screens bildet den Standardwert
        setScreenX(isAlive() ? InGame.getScreenX() : getWorldX() - InGame.updateScreenX());
        setScreenY(isAlive() ? InGame.getScreenY() : getWorldY() - InGame.updateScreenY());

        // bewegt den Spieler abhängig von seiner Position auf der Map
        if (getWorldX() < getScreenX()) {
            setScreenX(getWorldX());
        } else if (getWorldX() > getWorldWidth() - SCREEN_HEIGHT) {
            setScreenX(getWorldX() - InGame.updateScreenX());
        }
        if (getWorldY() < getScreenY()) {
            setScreenY(getWorldY());
        } else if (getWorldY() > getWorldHeight() - SCREEN_WIDTH) {
            setScreenY(getWorldY() - InGame.updateScreenY());
        }

        // aktualisiert die HitBox des Spielers
        updateHitBox();
    }

    /**
     * setzt die Position des Spielers zurück
     */
    public void defaultPos() {
        setWorldX(getPlayerSpawn()[0]);
        setWorldY(getPlayerSpawn()[1]);
        setSpriteRow(getCurrentLevel() == LEVEL_ZERO ? RUN_RIGHT : getCurrentLevel() == LEVEL_THREE ? RUN_LEFT : RUN_UP);
    }

    /**
     * bewegt den Spieler zum Ziel
     *
     * @param target Zielposition
     */
    public void moveToPos(Point2D.Float target) {
        setSpeed(getDefaultSpeed());
        float dx = target.x - (getHitBox().x + getHitBox().width / 2f);
        float dy = target.y - (getHitBox().y + getHitBox().height / 2f);

        if (Math.abs(dx) > getSpeed()) {
            setRight(dx > 0);
            setLeft(dx < 0);
        } else {
            setRight(false);
            setLeft(false);
        }
        if (Math.abs(dy) > getSpeed()) {
            setDown(dy > 0);
            setUp(dy < 0);
        } else {
            setUp(false);
            setDown(false);
        }
    }

    /**
     * überprüft, ob der Spieler die Position erreicht hat
     *
     * @param target Zielposition
     * @return true, wenn der Spieler die Position erreicht hat
     */
    public boolean posReached(Point2D.Float target) {
        float dx = target.x - (getHitBox().x + getHitBox().width / 2f);
        float dy = target.y - (getHitBox().y + getHitBox().height / 2f);
        if (Math.abs(dx) <= getSpeed() && Math.abs(dy) <= getSpeed()) {
            resetMovement();
            return true;
        }
        return false;
    }


    // GETTER UND SETTER


    public Inventory getInventory() {
        return inventory;
    }

    public long getTimeSinceDeath() {
        return timeSinceDeath;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setTimeSinceDeath(long timeSinceDeath) {
        this.timeSinceDeath = timeSinceDeath;
    }

    public int getLastEnemyMadeDamageID() {
        return lastEnemyMadeDamageID;
    }

    public void setLastEnemyMadeDamageID(int lastEnemyMadeDamageID) {
        this.lastEnemyMadeDamageID = lastEnemyMadeDamageID;
    }
}