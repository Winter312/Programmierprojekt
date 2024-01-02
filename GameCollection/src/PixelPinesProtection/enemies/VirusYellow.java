package PixelPinesProtection.enemies;

import PixelPinesProtection.helperMethods.Constants.Enemies;
import PixelPinesProtection.managers.EnemyManager;
import PixelPinesProtection.scenes.Playing;

public class VirusYellow extends Enemy {
    public VirusYellow(Playing playing, float x, float y, int id, EnemyManager em) {
        super(playing, x, y, id, Enemies.VYELLOW, em);

    }
}
