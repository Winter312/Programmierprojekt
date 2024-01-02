package jan.game.source.GameObj;

import jan.game.source.Vector2;

public class MoveToDirectionBehavior implements Behaves {

    private Vector2 dir;
    
    private double speed;
    
    /**
     * Konstruktor
     * @param dir
     * @param speed
     */
    public MoveToDirectionBehavior(Vector2 dir, double speed) {
        
        this.dir = dir;
        this.speed = speed;
    }

    /**
     * Bewege dich Richtung 'dir' mit Geschwindigkeit 'speed'
     */
    @Override
    public void act(BaseObj input) {

        input.transform.move(Vector2.normalize(dir).mul(speed));
    }

    /**
     * Interagiere nicht
     */
    @Override
    public void interact(BaseObj input) { }
}
