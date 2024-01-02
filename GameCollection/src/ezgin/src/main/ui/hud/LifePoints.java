package ezgin.src.main.ui.hud;

import ezgin.src.main.entities.SuperLivingEntity;
import ezgin.src.main.entities.livingentities.Enemy;
import ezgin.src.main.entities.livingentities.Player;

import static ezgin.src.main.enums.Item.LIFE_STONE;
import static ezgin.src.utils.Constants.GameConstants.TILE_SIZE;
import static ezgin.src.utils.Constants.UIConstants.MENU_TILE_SIZE;

/**
 * Klasse für die Lebenspunkte
 */
public class LifePoints {

    private SuperLivingEntity superLivingEntity;
    private float screenX, screenY;
    private float currentHearts;
    private int fullHearts;
    private int halfHearts;
    private int emptyHearts;
    private int heartSize;

    public LifePoints(SuperLivingEntity superLivingEntity) {
        setSuperLivingEntity(superLivingEntity);
        if (getSuperLivingEntity() instanceof Player) {
            setHeartSize((int) (MENU_TILE_SIZE * 0.75));
        } else {
            setHeartSize(TILE_SIZE / 6);
        }
    }

    /**
     * aktualisiert die Lebenspunkte
     */
    public void update() {
        setCurrentHearts(getSuperLivingEntity().getCurrentLifePoints() / (getSuperLivingEntity().getMaxLifePoints() / (getSuperLivingEntity() instanceof Player && LIFE_STONE.isAcquired() ? 10f : 5f)));
        setFullHearts(getCurrentHearts() > 0 ? (int) getCurrentHearts() : 0);
        setHalfHearts(getCurrentHearts() - getFullHearts() >= 0.5 || getFullHearts() > 0 && getFullHearts() < 1 ? 1 : 0);
        setEmptyHearts(getSuperLivingEntity().getMaxLifePoints() / (getSuperLivingEntity().getMaxLifePoints() / (getSuperLivingEntity() instanceof Player && LIFE_STONE.isAcquired() ? 10 : 5)) - getFullHearts() - getHalfHearts());

        if (getSuperLivingEntity() instanceof Enemy) {
            setScreenX(getSuperLivingEntity().getScreenX());
            setScreenY(getSuperLivingEntity().getScreenY() - TILE_SIZE / 4f);
        }
    }


    // GETTER UND SETTER


    public float getCurrentHearts() {
        return currentHearts;
    }

    public int getFullHearts() {
        return fullHearts;
    }

    public int getHalfHearts() {
        return halfHearts;
    }

    public int getEmptyHearts() {
        return emptyHearts;
    }

    public int getHeartSize() {
        return heartSize;
    }

    public float getScreenX() {
        return screenX;
    }

    public float getScreenY() {
        return screenY;
    }

    public void setScreenX(float screenX) {
        this.screenX = screenX;
    }

    public void setScreenY(float screenY) {
        this.screenY = screenY;
    }

    public void setCurrentHearts(float currentHearts) {
        this.currentHearts = currentHearts;
    }

    public void setFullHearts(int fullHearts) {
        this.fullHearts = fullHearts;
    }

    public void setHalfHearts(int halfHearts) {
        this.halfHearts = halfHearts;
    }

    public void setEmptyHearts(int emptyHearts) {
        this.emptyHearts = emptyHearts;
    }

    public void setHeartSize(int heartSize) {
        this.heartSize = heartSize;
    }

    public void setSuperLivingEntity(SuperLivingEntity superLivingEntity) {
        this.superLivingEntity = superLivingEntity;
    }

    public SuperLivingEntity getSuperLivingEntity() {
        return superLivingEntity;
    }
}