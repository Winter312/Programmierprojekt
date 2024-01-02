package ezgin.src.main.entities;

import java.awt.Rectangle;
import java.util.Arrays;

import ezgin.src.main.entities.livingentities.Enemy;
import ezgin.src.main.entities.livingentities.Player;
import ezgin.src.main.enums.Item;
import ezgin.src.main.gamestates.InGame;
import ezgin.src.main.ui.hud.LifePoints;

import static ezgin.src.utils.Constants.EntitySpriteConstants.*;
import static ezgin.src.utils.Constants.EntitySpriteConstants.ATTACK_DOWN;
import static ezgin.src.utils.Constants.GameConstants.SCALE;

/**
 * Klasse für die lebenden Entitäten
 */
public class SuperLivingEntity extends SuperEntity {

    // Aktions-Status der lebenden Entität
    private boolean up, left, down, right;
    private boolean attacking, attackAnimation;

    private int startAni; // Hilfsvariable für die Animation


    // Status der lebenden Entität
    private int maxLifePoints, currentLifePoints;
    private int attackDamage;
    private float defaultSpeed, runSpeed;
    private int shield;

    // Lebenspunkte und Heilung
    private LifePoints lifePoints;
    private long lastTimeHealed;

    // Angriffs-Range
    private Rectangle range;
    private int rangeOffsetX, rangeOffsetY;

    // Sperren
    private int movementLock;
    private boolean lock;

    public SuperLivingEntity(int id, float worldX, float worldY, String spritePath) {
        super(id, worldX, worldY, spritePath);
        setWidth(getEntityWidth(spritePath)/2);
        setHeight(getEntityHeight(spritePath)/2);
        setAniSpeed(16);

        // initialisiert die Lebenspunkte
        setLifePoints(new LifePoints(this));

        // initialisiert die HitBox
        setHitBoxOffsetX(10 * SCALE / 2);
        setHitBoxOffsetY((49 - 20) * SCALE / 2);
        setHitBox(new Rectangle(getHitBoxOffsetX(), getHitBoxOffsetY(), 44 * SCALE / 2, (15 + 20) * SCALE / 2));
    }

    public void updateWorldValuesAndAnimation() {
        updateAnimationTick();
        setStartAni(getSpriteRow());
        if (noAttackAnimation()) {
            updateMove();
        } else {
            updateAttack();
        }
    }

    /**
     * aktualisiert die Position und die Animation des Spielers bei Bewegung
     */
    private void updateMove() {
        if (isLeft() && !isRight()) {
            setSpriteRow(RUN_LEFT);
            setWorldX(getWorldX() - getSpeed());
        } else if (isRight() && !isLeft()) {
            setSpriteRow(RUN_RIGHT);
            setWorldX(getWorldX() + getSpeed());
        } else if (isUp() && !isDown()) {
            setSpriteRow(RUN_UP);
            setWorldY(getWorldY() - getSpeed());
        } else if (isDown() && !isUp()) {
            setSpriteRow(RUN_DOWN);
            setWorldY(getWorldY() + getSpeed());
        } else {
            resetValues();
        }
    }

    /**
     * aktualisiert die Animation des Angriffs
     */
    public void updateAttack() {
        if (this instanceof Player) {
            if (getSpriteRow() == RUN_LEFT) {
                setSpriteRow(ATTACK_LEFT);
            } else if (getSpriteRow() == RUN_RIGHT) {
                setSpriteRow(ATTACK_RIGHT);
            } else if (getSpriteRow() == RUN_UP) {
                setSpriteRow(ATTACK_UP);
            } else if (getSpriteRow() == RUN_DOWN) {
                setSpriteRow(ATTACK_DOWN);
            }
            if (getStartAni() != getSpriteRow()) {
                resetValues();
            }
        }
    }

    /**
     * aktualisiert die Animation des Spielers
     */
    @Override
    public void updateAnimationTick() {
        setAniTick(getAniTick() + 1);
        if (getAniTick() > getAniSpeed()) {
            setAniTick(0);
            setSpriteCol(getSpriteCol() + 1);
            if (getSpriteCol() >= getSpriteCount(getId(), getSpriteRow())) {
                if (isAlive()) {
                    setAttackAnimation(false);
                    setSpriteCol(1);
                } else {
                    setSpriteCol(getSpriteCount(getId(), DEATH) - 1);
                }
            }
        }
    }

    /**
     * aktualisiert die HitBox sowie die Range
     */
    @Override
    public void updateHitBox() {
        getHitBox().setLocation((int) getScreenX() + getHitBoxOffsetX(), (int) getScreenY() + getHitBoxOffsetY());
        getRange().setLocation((int) (getScreenX() + getRangeOffsetX()), (int) (getScreenY() + getRangeOffsetY()));
    }

    /**
     * überprüft nach Kollisionen
     */
    public void checkCollision() {
        if (!isAttacking()) {
            InGame.checkSolid(this);
        }
    }

    /**
     * überprüft einen Angriff
     */
    public void attemptAttack() {
        if (this instanceof Player) {
            if (Item.DAGGER.isAcquired()) {
                setAttacking(true);
                setAttackAnimation(true);
            }
        } else {
            setAttacking(getRange().intersects(EntityHandler.getInstance().getPlayer().getHitBox()) && EntityHandler.getInstance().getPlayer().isAlive() && InGame.notSolid(this, EntityHandler.getInstance().getPlayer()));
        }
    }

    /**
     * heilt den Entity, wenn er nicht angegriffen wird
     */
    public void heal() {
        if (Arrays.stream(EntityHandler.getInstance().getEnemies()).noneMatch(Enemy::isAttacking)) {
            if (getLastTimeHealed() < System.currentTimeMillis() - 1500) {
                if (getCurrentLifePoints() < getMaxLifePoints()) {
                    setCurrentLifePoints(getCurrentLifePoints() + 5);
                    if (getCurrentLifePoints() > getMaxLifePoints()) {
                        setCurrentLifePoints(getMaxLifePoints());
                    }
                    setLastTimeHealed(System.currentTimeMillis());
                }
            }
        }
    }

    /**
     * setzt die Bewegung zurück
     */
    public void resetMovement() {
        setLeft(false);
        setRight(false);
        setUp(false);
        setDown(false);
        setSpeed(getDefaultSpeed());
        setAttacking(false);
        setAttackAnimation(false);
        resetValues();
    }


    // GETTER UND SETTER


    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int getMaxLifePoints() {
        return maxLifePoints;
    }

    public void setMaxLifePoints(int maxLifePoints) {
        this.maxLifePoints = maxLifePoints;
    }

    public int getCurrentLifePoints() {
        return currentLifePoints;
    }

    public void setCurrentLifePoints(int currentLifePoints) {
        this.currentLifePoints = currentLifePoints;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public float getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(float defaultSpeed) {
        setSpeed(defaultSpeed);
        this.defaultSpeed = defaultSpeed;
    }

    public float getRunSpeed() {
        return runSpeed;
    }

    public void setRunSpeed(float runSpeed) {
        this.runSpeed = runSpeed;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public Rectangle getRange() {
        return range;
    }

    public void setRange(Rectangle range) {
        this.range = range;
    }

    public int getRangeOffsetX() {
        return rangeOffsetX;
    }

    public void setRangeOffsetX(int rangeOffsetX) {
        this.rangeOffsetX = rangeOffsetX;
    }

    public int getRangeOffsetY() {
        return rangeOffsetY;
    }

    public void setRangeOffsetY(int rangeOffsetY) {
        this.rangeOffsetY = rangeOffsetY;
    }

    public int getMovementLock() {
        return movementLock;
    }

    public void setMovementLock(int movementLock) {
        this.movementLock = movementLock;
    }

    public void setAttackAnimation(boolean attackAnimation) {
        this.attackAnimation = attackAnimation;
    }

    public LifePoints getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(LifePoints lifePoints) {
        this.lifePoints = lifePoints;
    }

    public boolean isAlive() {
        return getCurrentLifePoints() > 0;
    }

    public boolean noAttackAnimation() {
        return !attackAnimation;
    }

    public long getLastTimeHealed() {
        return lastTimeHealed;
    }

    public void setLastTimeHealed(long lastTimeHealed) {
        this.lastTimeHealed = lastTimeHealed;
    }

    public boolean notLocked() {
        return !lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public int getStartAni() {
        return startAni;
    }

    public void setStartAni(int startAni) {
        this.startAni = startAni;
    }
}
