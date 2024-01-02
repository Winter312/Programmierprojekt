package enemies;
import managers.EnemyManager;
import scenes.Playing;

import static helperMethods.Constants.Enemies.VRED;
public class VirusRed extends Enemy{


    public VirusRed(Playing playing, float x, float y, int id, EnemyManager em) {
        super(playing, x, y, id, VRED, em);
    }
}
