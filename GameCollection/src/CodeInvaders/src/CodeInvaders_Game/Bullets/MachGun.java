package CodeInvaders_Game.Bullets;

import CodeInvaders_Game.GameDisplay.Display;
import CodeInvaders_Game.GameScreen.NormalBlocks;

import java.awt.*;

public class MachGun extends WeaponType {

    private Rectangle Bulletrect;
    private final double speed = 2.5d;

    /**
     * Konstruktor: Initialisiert eine neue MachGun-Instanz mit spezifizierten Koordinaten und Größe.
     * @param xPos
     * @param yPos
     * @param width
     * @param height
     */
    public MachGun(double xPos, double yPos, int width, int height){
        this.setxPos(xPos);
        this.setyPos(yPos);
        this.setWidth(width);
        this.setHeight(height);
        this.Bulletrect = new Rectangle((int)getxPos(),(int)getyPos(),getWidth(),getHeight());
    }

    /**
     * Zeichnet die MachGun-Munition auf dem Bildschirm.
     * @param grahpic
     */
    @Override
    public void draw(Graphics2D grahpic) {
        if (Bulletrect == null){
            return;
        }
        grahpic.setColor(Color.GREEN);
        grahpic.fill(Bulletrect);
    }

    /**
     * Aktualisiert den Zustand der MachGun-Munition in jedem Frame.
     * @param delta
     * @param blocks
     */
    @Override
    public void update(double delta, NormalBlocks blocks) {
        if (Bulletrect == null) {
            return;
        }
        this.setyPos(getyPos() - (delta * speed));
        Bulletrect.y = (int) this.getyPos();
        wallCollision(blocks);
        isOutOfBlock();
    }

    /**
     * Überprüft die Kollision der Munition mit einem anderen Rechteck.
     * @param rect
     * @return
     */
    @Override
    public boolean collisionRect(Rectangle rect) {
        if(this.Bulletrect == null){
            return false;
        }
        if (Bulletrect.intersects(rect)){
            this.Bulletrect = null;
            return true;
        }
        return false;
    }

    /**
     * Überprüft, ob die Munition zerstört werden sollte.
     * @return
     */
    @Override
    public boolean destroy() {
        if (Bulletrect == null){
            return true;
        }

        return false;
    }

    /**
     * Verwaltet die Kollision der Munition mit Wänden.
     * @param block
     */
    @Override
    protected void wallCollision(NormalBlocks block) {

        for(int i = 0; i < block.wall.size(); i++){
            if (Bulletrect.intersects(block.wall.get(i))){
                block.wall.remove(i);
                Bulletrect = null;
                return;
            }
        }
    }

    /**
     * Überprüft, ob die Munition außerhalb des Spielbereichs ist und zerstört sie gegebenenfalls.
     */
    @Override
    protected void isOutOfBlock() {
        if (this.Bulletrect == null){
            return;
        }
        if (Bulletrect.y < 0 || Bulletrect.y > Display.HEIGHT || Bulletrect.x < 0 || Bulletrect.x > Display.WIDTH){
            Bulletrect = null;
        }
    }
}
