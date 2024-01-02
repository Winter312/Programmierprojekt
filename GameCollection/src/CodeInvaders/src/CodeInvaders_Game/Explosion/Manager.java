package CodeInvaders_Game.Explosion;

import java.awt.*;
import java.util.ArrayList;

public class Manager {
    private static ArrayList<PExplosionType> explosions = new ArrayList<PExplosionType>();

    /**
     * Zeichnet alle Explosionen auf dem Bildschirm.
     * @param graphic
     */
    public void draw (Graphics2D graphic){
        for (int i = 0; i < explosions.size(); i++){
            explosions.get(i).draw(graphic);
        }
    }


    /**
     * Aktualisiert den Zustand aller Explosionen in jedem Frame.
      * @param delta
     */
    public void update(double delta){
        for (int i = 0; i < explosions.size(); i++){
            explosions.get(i).update(delta);
            if (explosions.get(i).destroy()){
                explosions.remove(i);
            }
        }
    }

    /**
     * Erstellt eine neue Explosion an einer bestimmten Position.
     * @param xPos
     * @param yPos
     */
    public static void createPExplosion(int xPos, double yPos){
        PExplosionType ExType = new PExplosion(xPos, yPos);
        explosions.add(ExType);
    }
}
