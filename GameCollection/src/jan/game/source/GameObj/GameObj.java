package jan.game.source.GameObj;
import java.awt.Point;

import javax.swing.JLabel;

import jan.game.source.Vector2;

public class GameObj extends BaseObj {

    /**
     * Konstruktor mit Text
     */
    public GameObj(Vector2 position, Behaves behavior, Point collisionArea, JLabel text,int score) {
        
        super(position, behavior, collisionArea, text, score);
    }

    /**
     * Wird jeden frame aufgerufen
     */
    @Override
    public void update() {
       
        act();      
    }
}
