package ezgin.src.main.entities.nonlivingentities;

import java.awt.Rectangle;

import javax.sound.sampled.Clip;

import ezgin.src.main.entities.EntityHandler;
import ezgin.src.main.entities.SuperEntity;


import static ezgin.src.utils.Constants.GameConstants.TILE_SIZE;
import static ezgin.src.utils.Constants.ImageConstants.PA_CHEST;
import static ezgin.src.utils.Constants.SpawnConstants.getChestSpawn;
/**
 * Klasse für Truhen
 */
public class Chest extends SuperEntity {

    public Chest(int id) {
        super(id, getChestSpawn(id)[0], getChestSpawn(id)[1], PA_CHEST);

        // initialisiert die HitBox
        setHitBoxOffsetX(TILE_SIZE / 3);
        setHitBoxOffsetY(getHeight());
        setHitBox(new Rectangle(getHitBoxOffsetX(), getHitBoxOffsetY(), getWidth() / 6, getHeight() / 8));
    }

    /**
     * Aktualisiert das Truhen-Objekt
     */
    public void update() {
        updateScreenValues(); // aktualisiert die Position der Truhe auf dem Bildschirm
        setPos(getScreenY() + TILE_SIZE); // aktualisiert in der DrawOrder die Position der Truhe

        // aktualisiert den Status der Truhe
        if (!isActive() && EntityHandler.getInstance().getPlayer().getHitBox().intersects(getHitBox())) {
            EntityHandler.getInstance().getPlayer().getInventory().setItem(getId());
            for (Clip clip : getClips()) {
                clip.start();
            }
            setSpriteCol(1);
            setActive(true);
        }
        stopClip();
    }
}