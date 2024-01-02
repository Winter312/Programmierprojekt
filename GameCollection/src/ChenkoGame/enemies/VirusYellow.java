package enemies;
import managers.EnemyManager;
import scenes.Playing;

import static helperMethods.Constants.Enemies.VYELLOW;
public class VirusYellow extends Enemy {
    public VirusYellow(Playing playing, float x, float y, int id, EnemyManager em) {
        super(playing, x, y, id, VYELLOW, em);

    }
}
