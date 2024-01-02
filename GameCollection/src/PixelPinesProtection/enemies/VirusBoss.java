package enemies;

import managers.EnemyManager;
import scenes.Playing;

import static helperMethods.Constants.Enemies.VBOSS;

public class VirusBoss extends Enemy{

    public VirusBoss(Playing playing, float x, float y, int id, EnemyManager em) {
        super(playing, x, y, id, VBOSS, em);
    }
}
