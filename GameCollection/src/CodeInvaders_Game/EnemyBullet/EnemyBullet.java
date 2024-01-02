package CodeInvaders_Game.EnemyBullet;

import CodeInvaders_Game.GameDisplay.Display;
import CodeInvaders_Game.GameScreen.NormalBlocks;
import CodeInvaders_Game.GameScreen.Player;
import java.awt.*;

public class EnemyBullet extends EnemyWeapon{
    private Player player;
    private Rectangle bullet;
    private double speed = 2.5;
    private int xPos, yPos;


    /**
     * Konstruktor: Initialisiert eine neue feindliche Kugel mit bestimmten Koordinaten und dem Spieler.
      * @param xPos
     * @param yPos
     * @param player
     */
    public EnemyBullet(double xPos, double yPos, Player player){
        this.player = player;
        bullet = new Rectangle((int) xPos, (int) yPos, 5, 5);
        setxPos((int) xPos);
        setyPos((int) yPos);
    }

    /**
     * Zeichnet die feindliche Kugel auf dem Bildschirm.
     * @param graphic
     */
    @Override
    public void draw(Graphics2D graphic) {
        if (bullet == null){
            return;
        }
        graphic.setColor(Color.RED);
        graphic.fill(bullet);
    }

    /**
     * Aktualisiert den Zustand der feindlichen Kugel in jedem Frame.
     * @param delta
     * @param block
     * @param player
     */
    @Override
    public void update(double delta, NormalBlocks block, Player player) {
        if (bullet == null){
            return;
        }
        setyPos((int)(getyPos() + (delta * speed)));
        bullet.y = getyPos();

        isOutofBound();
        wallCollide(block);
    }

    /**
     * Überprüft die Kollision der Kugel mit einem anderen Rechteck.
     * @param rect
     * @return
     */
    @Override
    public boolean collision(Rectangle rect) {
        if(bullet != null && bullet.intersects(rect)){
            return true;
        }
        return false;
    }

    /**
     * Überprüft die Kollision der Kugel mit einem anderen Rechteck.
     * @param blocks
     */
    @Override
    protected void wallCollide(NormalBlocks blocks) {
        if (bullet == null){
            return;
        }
        for (int w = 0; w < blocks.wall.size(); w++){
            if (bullet.intersects(blocks.wall.get(w))){
                blocks.wall.remove(w);
                bullet = null;
                break;
            }
        }
    }

    /**
     * Überprüft die Kollision der Kugel mit einem anderen Rechteck.
     */
    @Override
    protected void isOutofBound() {
        if (bullet != null && (bullet.y < 0 || bullet.y > Display.HEIGHT || bullet.x < 0 || bullet.x > Display.WIDTH)) {
            bullet = null;
        }
    }

    /**
     * Getter für die X-Position der Kugel.
     * @return
     */
    @Override
    public int getXPos() {
        return getxPos();
    }

    /**
     * Getter für die Y-Position der Kugel.
     * @return
     */
    @Override
    public int getYPos() {
        return getyPos();
    }

    /**
     * Getter für die X-Position.
      */

    public int getxPos() {
        return xPos;
    }

    /**
     * Setter für die X-Position.
     * @param xPos
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Getter für die Y-Position.
     * @return
     */

    public int getyPos() {
        return yPos;
    }

    /**
     * Setter für die Y-Position.
     * @param yPos
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}
