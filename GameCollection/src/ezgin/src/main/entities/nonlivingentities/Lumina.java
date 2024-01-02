package ezgin.src.main.entities.nonlivingentities;

import java.awt.Rectangle;

import ezgin.src.main.entities.SuperEntity;

import static ezgin.src.main.enums.Level.LEVEL_THREE;
import static ezgin.src.main.enums.Level.getCurrentLevel;
import static ezgin.src.utils.Constants.GameConstants.TILE_SIZE;
import static ezgin.src.utils.Constants.ImageConstants.PA_LUMINA;
import static ezgin.src.utils.Constants.SpawnConstants.getLuminaSpawn;

public class Lumina extends SuperEntity {
    public Lumina(int id) {
        super(id, getLuminaSpawn()[0], getLuminaSpawn()[1], PA_LUMINA);

        // initialisiert die HitBox
        setHitBoxOffsetX(-TILE_SIZE);
        setHitBoxOffsetY((int) ((getCurrentLevel() == LEVEL_THREE ? 5f : 4) * TILE_SIZE));
        setHitBox(new Rectangle(getHitBoxOffsetX(), getHitBoxOffsetY(), 5 * TILE_SIZE, TILE_SIZE));
    }

    /**
     * Aktualisiert das Lumina-Objekt
     */
    public void update() {
        updateScreenValues(); // aktualisiert die Position der Lumina auf dem Bildschirm
        setPos(getScreenY() + TILE_SIZE * 2); // aktualisiert in der DrawOrder die Position der Lumina

        // aktualisiert den Status der Lumina
        if (isActive()) {
            updateAnimationTick();
        } else {
            resetValues();
        }
    }
}
