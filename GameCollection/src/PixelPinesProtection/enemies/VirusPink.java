package PixelPinesProtection.enemies;

import PixelPinesProtection.helperMethods.Constants.Enemies;
import PixelPinesProtection.managers.EnemyManager;
import PixelPinesProtection.scenes.Playing;

public class VirusPink extends Enemy {
    public VirusPink(Playing playing, float x, float y, int id, EnemyManager em) {
        super(playing, x, y, id, Enemies.VPINK, em);

    }
}
