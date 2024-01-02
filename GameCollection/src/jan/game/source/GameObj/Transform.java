package jan.game.source.GameObj;

import jan.game.source.Vector2;

public class Transform {

    private Vector2 position;
      
    /**
     * Konstruktor mit Vektor
     * @param position
     */
    public Transform (Vector2 position) {
        
        this.position = position;
    }
    
    
    /**
     * Gibt die Position zurück
     * @return Vector2
     */
    public final Vector2 getPosition() {
        
        return position;
    }
    
    /**
     * Setzt die Position zurück
     */
    public final void setPosition(Vector2 pos) {
        
        position.x = pos.x;
        position.y = pos.y;
    }
    
    
    /**
     * Verschieben das Objekt in die gegebene Richtung
     */
    public final void move(Vector2 dir) {
        
        position = Vector2.add(position, dir);
    }
}
