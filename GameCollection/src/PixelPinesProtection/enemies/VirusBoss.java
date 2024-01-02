package PixelPinesProtection.enemies;

import PixelPinesProtection.managers.EnemyManager;
import PixelPinesProtection.scenes.Playing;

import static PixelPinesProtection.helperMethods.Constants.Enemies.VBOSS;

public class VirusBoss extends Enemy{

    public VirusBoss(Playing playing, float x, float y, int id, EnemyManager em) {
        super(playing, x, y, id, VBOSS, em);
    }
}
