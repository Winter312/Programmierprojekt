package main.entities.nonlivingentities;

import main.entities.EntityHandler;
import main.entities.SuperEntity;
import main.entities.livingentities.Enemy;
import main.gamestates.InGame;

import java.awt.*;
import java.util.Arrays;

import static main.enums.Item.KEY;
import static main.enums.Level.*;
import static utils.Constants.GameConstants.TILE_SIZE;
import static utils.Constants.ImageConstants.getDoor;
import static utils.Constants.SpawnConstants.getDoorPos;
import static utils.Constants.SpawnConstants.getDoorSpawn;

/**
 * Klasse für Türen
 */
public class Door extends SuperEntity {

    public Door(int id) {
        super(id, getDoorSpawn(id)[0], getDoorSpawn(id)[1], getDoor(id));

        setHitBoxOffsetX(TILE_SIZE);
        setHitBoxOffsetY(2 * TILE_SIZE);
        setHitBox(new Rectangle(getHitBoxOffsetX(), getHitBoxOffsetY(), TILE_SIZE, TILE_SIZE / 2));
    }

    /**
     * Aktualisiert das Door-Objekt
     */
    public void update() {
        updateScreenValues(); // aktualisiert die Position der Tür auf dem Bildschirm
        setPos(getScreenY() + getHeight() / 3f); // aktualisiert in der DrawOrder die Position der Tür

        // aktualisiert den Status der Tür
        if (!isActive() && EntityHandler.getInstance().getPlayer().getHitBox().intersects(getHitBox())) {
            if (getCurrentLevel() == LEVEL_CASTLE && Arrays.stream(EntityHandler.getInstance().getEnemies()).noneMatch(Enemy::isAlive) || getCurrentLevel() == LEVEL_THREE && (KEY.isAcquired() && getId() == 1 || Arrays.stream(EntityHandler.getInstance().getCheckPoints()).allMatch(CheckPoint::isActive) && getId() == 0)) {
                KEY.reset();
                for (int i = getDoorPos(getId())[0]; i < getDoorPos(getId())[0] + 32; i++) {
                    InGame.getLvlWall()[i][getDoorPos(getId())[1]] = 255;
                }
                getClips()[0].start();
                setSpriteCol(1);
                setActive(true);
            }
        }
        if (getId() == 0 && isActive() && EntityHandler.getInstance().getPlayer().getHitBox().y + EntityHandler.getInstance().getPlayer().getHitBox().height < getHitBox().y - getHitBox().height / 2 && EntityHandler.getInstance().getPlayer().getScreenX() + EntityHandler.getInstance().getPlayer().getWidth() / 2f > getHitBox().x && EntityHandler.getInstance().getPlayer().getScreenX() + EntityHandler.getInstance().getPlayer().getWidth() / 2f < getHitBox().x + getHitBox().width) {
            InGame.getInstance().setJumpToNextLevel(true);
        }
    }
}
