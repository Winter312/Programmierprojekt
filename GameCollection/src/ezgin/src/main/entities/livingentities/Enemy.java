package ezgin.src.main.entities.livingentities;

import java.awt.Rectangle;
import java.util.Random;

import ezgin.src.main.entities.EntityHandler;
import ezgin.src.main.entities.SuperLivingEntity;

import static ezgin.src.utils.Constants.EntitySpriteConstants.*;
import static ezgin.src.utils.Constants.GameConstants.*;
import static ezgin.src.utils.Constants.ImageConstants.PA_ENEMY;
import static ezgin.src.utils.Constants.SpawnConstants.getEnemySpawn;

/**
 * Klasse für die Gegner
 */
public class Enemy extends SuperLivingEntity {

    public Enemy(int id) {
        super(id, getEnemySpawn()[id][0], getEnemySpawn()[id][1], PA_ENEMY);

        // legt Standardwerte für den Enemy fest
        setCurrentLifePoints(10);
        setMaxLifePoints(10);
        setDefaultSpeed(0.2f * SCALE);
        setAttackDamage(new Random().nextInt(3) + 5);

        // initialisiert die Range
        setRangeOffsetX(-2 * TILE_SIZE);
        setRangeOffsetY(-2 * TILE_SIZE);
        setRange(new Rectangle(getRangeOffsetX(), getRangeOffsetY(), 5 * TILE_SIZE, 5 * TILE_SIZE));
    }

    /**
     * aktualisiert den Gegner
     */
    public void update() {
        updateScreenValues();
        if (isAlive()) {
            setPos(getScreenY());
            move();
            updateWorldValuesAndAnimation();
            attemptAttack();
            checkCollision();
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
     * bestimmt die Richtung der Bewegung
     */
    public void move() {
        setMovementLock(getMovementLock() + 1);
        if (getMovementLock() >= UPS && !isAttacking()) {
            setMovementLock(0);
            int i = new Random().nextInt(8);
            switch (i) {
                case 0:
                    setUp(true);
                    setDown(false);
                    break;
                case 1:
                    setLeft(true);
                    setRight(false);
                    break;
                case 2:
                    setUp(false);
                    setDown(true);
                    break;
                case 3:
                    setLeft(false);
                    setRight(true);
                    break;
                default:
                    resetMovement();
                    break;
            }
        }
    }

    /**
     * aktualisiert die Animation des Angriffs
     */
    @Override
    public void updateAttack() {
        float diffX = (EntityHandler.getInstance().getPlayer().getWorldX() + EntityHandler.getInstance().getPlayer().getWidth() / 2f) - (getWorldX() + getWidth() / 2f);
        float diffY = (EntityHandler.getInstance().getPlayer().getWorldY() + EntityHandler.getInstance().getPlayer().getHeight() / 2f) - (getWorldY() + getHeight() / 2f);
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX > 0) {
                setSpriteRow(ATTACK_RIGHT);
            } else {
                setSpriteRow(ATTACK_LEFT);
            }
        } else {
            if (diffY > 0) {
                setSpriteRow(ATTACK_DOWN);
            } else {
                setSpriteRow(ATTACK_UP);
            }
        }
    }
}
