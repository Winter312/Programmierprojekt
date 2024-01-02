package PixelPinesProtection.enemies;
import PixelPinesProtection.managers.EnemyManager;
import PixelPinesProtection.scenes.Playing;

import static PixelPinesProtection.helperMethods.Constants.Enemies.VBLUE;

public class VirusBlue extends Enemy {

    public VirusBlue(Playing playing, float x, float y, int id, EnemyManager em) {
        super(playing, x, y, id, VBLUE, em);

    }
}
