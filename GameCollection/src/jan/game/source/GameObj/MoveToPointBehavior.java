package jan.game.source.GameObj;

import jan.game.source.Vector2;

public class MoveToPointBehavior implements Behaves {

    private Vector2 destination;
    
    private double speed;
    
    /**
     * Konstruktor
     * @param destination
     * @param speed
     */
    public MoveToPointBehavior(Vector2 destination, double speed) {
        
        this.destination = destination;
        this.speed = speed;
    }

    /**
     * Bewege dich zum Punkt 'destination' mit Geschwindigkeit 'speed'
     */
    @Override
    public void act(BaseObj input) {

        input.transform.move(Vector2.normalize(Vector2.sub(destination, input.transform.getPosition())).mul(speed));
    }

    /**
     * Interagiere nicht
     */
    @Override
    public void interact(BaseObj input) { }
    
}
