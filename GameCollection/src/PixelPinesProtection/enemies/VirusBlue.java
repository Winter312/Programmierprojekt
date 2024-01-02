package enemies;
import managers.EnemyManager;
import scenes.Playing;

import static helperMethods.Constants.Enemies.VBLUE;

public class VirusBlue extends Enemy {

    public VirusBlue(Playing playing, float x, float y, int id, EnemyManager em) {
        super(playing, x, y, id, VBLUE, em);

    }
}
