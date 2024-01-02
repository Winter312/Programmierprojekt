package enemies;
import managers.EnemyManager;
import scenes.Playing;

import static helperMethods.Constants.Enemies.VPINK;
public class VirusPink extends Enemy {
    public VirusPink(Playing playing, float x, float y, int id, EnemyManager em) {
        super(playing, x, y, id, VPINK, em);

    }
}
