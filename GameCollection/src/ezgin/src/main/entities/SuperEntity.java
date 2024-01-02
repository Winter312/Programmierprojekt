package ezgin.src.main.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import ezgin.src.main.GameController;
import ezgin.src.main.entities.nonlivingentities.Lumina;
import ezgin.src.main.entities.nonlivingentities.Stela;
import ezgin.src.main.gamestates.InGame;

import static ezgin.src.utils.Constants.EntitySpriteConstants.*;
import static ezgin.src.utils.Load.getEntitySprites;

/**
 * Klasse für die Entitäten
 */
public class SuperEntity {

    private int id; // ID des Entities
    private float pos; // y-Position der Subklasse in der DrawOrder
    private float worldX, worldY, prevWorldX, prevWorldY; // Position des Entities in der Welt
    private float speed; // Geschwindigkeit des Entities

    // Richtung und Animation des Entities
    private BufferedImage[][] superEntity;
    private int spriteRow, spriteCol;
    private int aniTick, aniSpeed;

    // Kollisionsbereich
    private Rectangle hitBox;
    private int hitBoxOffsetX, hitBoxOffsetY;

    // Position des Entities auf dem Bildschirm
    private float screenX, screenY;
    private int width, height;

    private boolean active; // Status des Entities
    private Clip[] clips; // Sound des Entities

    public SuperEntity(int id, float worldX, float worldY, String spritePath) {
        setWorldX(worldX);
        setWorldY(worldY);
        setWidth(getEntityWidth(spritePath));
        setHeight(getEntityHeight(spritePath));
        setSuperEntity(getEntitySprites(spritePath));
        setActive(false);
        setSpriteCol(0);
        setAniSpeed(40);
        setId(id);
        setClips(getEntityClip(spritePath));
        setSpriteRow(this instanceof Stela ? id : 0);
        for (int i = 0; i < getClips().length; i++) {
            if (this instanceof Lumina) {
                GameController.getInstance().getMusicClips().put("Lumina", getClips()[i]);
            } else {
                GameController.getInstance().getSfxClips().put(spritePath + "_" + getId() + "_" + i, getClips()[i]);
            }
        }
    }

    /**
     * aktualisiert die Position des Entities und ihrer HitBox auf dem Bildschirm
     */
    public void updateScreenValues() {
        setScreenX(getWorldX() - InGame.updateScreenX());
        setScreenY(getWorldY() - InGame.updateScreenY());
        updateHitBox();
    }

    /**
     * aktualisiert die HitBox des Entities
     */
    public void updateHitBox() {
        getHitBox().setLocation((int) getScreenX() + getHitBoxOffsetX(), (int) getScreenY() + getHitBoxOffsetY());
    }

    /**
     * aktualisiert die Animation des Entities
     */
    public void updateAnimationTick() {
        setAniTick(getAniTick() + 1);
        if (getAniTick() > getAniSpeed()) {
            setAniTick(0);
            setSpriteCol(getSpriteCol() + 1);
            if (getSpriteCol() >= getSuperEntity()[getSpriteRow()].length) {
                setSpriteCol(1);
            }
        }
    }

    /**
     * setzt die Position und Animation des Entities zurück
     */
    public void resetValues() {
        setSpriteCol(0);
        setAniTick(16);
    }

    /**
     * stoppt den Clip des Entities
     */
    public void stopClip() {
        for (Clip clip : getClips()) {
            if (clip.getFramePosition() >= clip.getFrameLength()) {
                clip.stop();
                clip.setFramePosition(0);
            }
        }
    }


    // GETTER UND SETTER


    public float getPos() {
        return pos;
    }

    public void setPos(float pos) {
        this.pos = pos;
    }

    public float getWorldX() {
        return worldX;
    }

    public void setWorldX(float worldX) {
        this.worldX = worldX;
    }

    public float getWorldY() {
        return worldY;
    }

    public void setWorldY(float worldY) {
        this.worldY = worldY;
    }

    public float getPrevWorldX() {
        return prevWorldX;
    }

    public void setPrevWorldX(float prevWorldX) {
        this.prevWorldX = prevWorldX;
    }

    public float getPrevWorldY() {
        return prevWorldY;
    }

    public void setPrevWorldY(float prevWorldY) {
        this.prevWorldY = prevWorldY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public BufferedImage[][] getSuperEntity() {
        return superEntity;
    }

    public void setSuperEntity(BufferedImage[][] superEntity) {
        this.superEntity = superEntity;
    }

    public int getSpriteRow() {
        return spriteRow;
    }

    public void setSpriteRow(int spriteRow) {
        this.spriteRow = spriteRow;
    }

    public int getSpriteCol() {
        return spriteCol;
    }

    public void setSpriteCol(int spriteCol) {
        this.spriteCol = spriteCol;
    }

    public int getAniTick() {
        return aniTick;
    }

    public void setAniTick(int aniTick) {
        this.aniTick = aniTick;
    }

    public int getAniSpeed() {
        return aniSpeed;
    }

    public void setAniSpeed(int aniSpeed) {
        this.aniSpeed = aniSpeed;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public int getHitBoxOffsetX() {
        return hitBoxOffsetX;
    }

    public void setHitBoxOffsetX(int hitBoxOffsetX) {
        this.hitBoxOffsetX = hitBoxOffsetX;
    }

    public int getHitBoxOffsetY() {
        return hitBoxOffsetY;
    }

    public void setHitBoxOffsetY(int hitBoxOffsetY) {
        this.hitBoxOffsetY = hitBoxOffsetY;
    }

    public float getScreenX() {
        return screenX;
    }

    public void setScreenX(float screenX) {
        this.screenX = screenX;
    }

    public float getScreenY() {
        return screenY;
    }

    public void setScreenY(float screenY) {
        this.screenY = screenY;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Clip[] getClips() {
        return clips;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClips(Clip[] clips) {
        this.clips = clips;
    }
}