package PixelPinesProtection.enemies;

import PixelPinesProtection.helperMethods.Constants.Enemies;
import PixelPinesProtection.managers.EnemyManager;
import PixelPinesProtection.scenes.Playing;

public class VirusRed extends Enemy{


    public VirusRed(Playing playing, float x, float y, int id, EnemyManager em) {
        super(playing, x, y, id, Enemies.VRED, em);
    }
}
