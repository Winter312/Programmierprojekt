package ezgin.src.main.entities.nonlivingentities;

import java.awt.Rectangle;

import ezgin.src.main.entities.EntityHandler;
import ezgin.src.main.entities.SuperEntity;

import static ezgin.src.utils.Constants.GameConstants.TILE_SIZE;
import static ezgin.src.utils.Constants.ImageConstants.PA_CHECKPOINT;
import static ezgin.src.utils.Constants.SpawnConstants.getCheckPointSpawn;

/**
 * Klasse für Checkpoints
 */
public class CheckPoint extends SuperEntity {

    public CheckPoint(int id) {
        super(id, getCheckPointSpawn(id)[0], getCheckPointSpawn(id)[1], PA_CHECKPOINT);

        // legt die Position des Checkpoints in der DrawOrder fest
        setPos(Long.MIN_VALUE);

        // initialisiert die HitBox
        setHitBoxOffsetX(TILE_SIZE + TILE_SIZE / 4);
        setHitBoxOffsetY(TILE_SIZE + TILE_SIZE / 4);
        setHitBox(new Rectangle((int) (getWorldX() + TILE_SIZE), (int) (getWorldY() + TILE_SIZE), TILE_SIZE / 2, TILE_SIZE / 2));
    }

    /**
     * Aktualisiert das Checkpoint-Objekt
     */
    public void update() {
        updateScreenValues(); // aktualisiert die Position des Checkpoints auf dem Bildschirm

        // aktualisiert den Status des Checkpoints
        if (!isActive() && EntityHandler.getInstance().getPlayer().getHitBox().intersects(getHitBox())) {
            getClips()[0].start();
            setActive(true);
        }
        if (isActive()) {
            updateAnimationTick();
        }
    }
}