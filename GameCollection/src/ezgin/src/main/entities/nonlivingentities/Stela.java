package main.entities.nonlivingentities;

import main.entities.EntityHandler;
import main.entities.SuperEntity;

import java.awt.*;

import static utils.Constants.GameConstants.*;
import static utils.Constants.ImageConstants.PA_STELA;
import static utils.Constants.SpawnConstants.getStelaSpawn;

/**
 * Klasse für Stelen
 */
public class Stela extends SuperEntity {

    public Stela(int id) {
        super(id, getStelaSpawn(id)[0], getStelaSpawn(id)[1], PA_STELA);

        // initialisiert die HitBox
        setHitBoxOffsetY(getHeight());
        setHitBox(new Rectangle(getHitBoxOffsetX(), getHitBoxOffsetY(), getWidth() / 2, getHeight() / 3));
    }

    /**
     * Aktualisiert das Stelen-Objekt
     */
    public void update() {
        updateScreenValues(); // aktualisiert die Position der Stele auf dem Bildschirm
        setPos(getScreenY() + TILE_SIZE * 2); // aktualisiert in der DrawOrder die Position der Stele

        // aktualisiert den Status der Stele
        if (EntityHandler.getInstance().getCheckPoints()[getSpriteRow()].isActive()) {
            updateAnimationTick();
        }
    }
}