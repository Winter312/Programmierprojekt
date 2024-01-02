package jan.game.source.GameObj;
import java.awt.MouseInfo;
import java.awt.Point;

import jan.game.source.Game_Controller;
import jan.game.source.Vector2;


public abstract class Collider{

    private final Vector2 mouseSize = new Vector2(9, 17); //Die 'hitbox' grosse der Mouse
    
    private Point collisionArea; //Die 'hitbox' grosse des Objektes
        
    /**
     * Konstruktor
     * @param collisionArea
     */
    public Collider(Point collisionArea) {
        
        this.collisionArea = collisionArea;
    }

    /**
     * Testen ob eine collision stattfindet
     * @param pos Position des Objektes
     * @param collisionEnabeld
     * @return boolean Ob collision vorliegt oder nicht
     */
    public final boolean testForCollision(Vector2 pos, boolean collisionEnabeld) {
       
        if (!collisionEnabeld) {
            
            return false;
        }
        
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        mousePos.x -= Game_Controller.getLocationOnScreen().x;
        mousePos.y -= Game_Controller.getLocationOnScreen().y;
        
        if (mousePos.x > pos.x + collisionArea.x/2 || mousePos.x + mouseSize.x < pos.x - collisionArea.x/2) {
            
            return false;
            
        }
        
        if (mousePos.y >  pos.y + collisionArea.y/2 || mousePos.y + mouseSize.y < pos.y - collisionArea.y/2) {
            
            return false;
        }
        
        return true;
    }
    
    /**
     * Setzt die 
     * @param collisionArea 'hitbox' grosse des Objektes
     */
    public void setCollisionArea(Point collisionArea) {
        
        this.collisionArea = collisionArea;
    }
}
