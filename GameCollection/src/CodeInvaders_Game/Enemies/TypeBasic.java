package CodeInvaders_Game.Enemies;

import CodeInvaders_Game.EnemyBullet.EnemyBullet;
import CodeInvaders_Game.GameDisplay.Display;
import CodeInvaders_Game.GameScreen.GScreen;
import CodeInvaders_Game.GameScreen.NormalBlocks;
import CodeInvaders_Game.GameScreen.Player;
import CodeInvaders_Game.Sprite.Animation;
import CodeInvaders_Game.Steering.EnemyBulletSteering;
import CodeInvaders_Game.Timer.timer;
import CodeInvaders_Game.Sounds.sound;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TypeBasic extends EnemyType{
    private double speed = 0.9d;
    private boolean isDeath;
    private Rectangle rect;
    private Animation enemySprite;
    private timer shootTimer;

    private sound explosion;
    private int shootingTime;
    private EnemyBulletSteering bulletSteering;

    /**
     * Konstruktor: Initialisiert einen Basisgegner mit bestimmten Eigenschaften und Animationen.
     * @param xPos
     * @param yPos
     * @param rows
     * @param columns
     * @param bulletSteering
     */
    public TypeBasic(double xPos, double yPos, int rows, int columns, EnemyBulletSteering bulletSteering ){
        super(bulletSteering);
        enemySprite = new Animation(xPos, yPos, rows, columns, 300, "/CodeInvaders_Game/Images/InvadersTwo.png");
        enemySprite.setWidth(50);
        enemySprite.setHeight(50);
        enemySprite.setLimit(2);
        isDeath = false;
        this.setRect(new Rectangle((int) enemySprite.getxPos(), (int) enemySprite.getyPos(), enemySprite.getWidth(), enemySprite.getHeight()));
        enemySprite.setLoop(true);
        this.bulletSteering = bulletSteering;
        shootTimer = new timer();
        shootingTime = new Random().nextInt(12000);

        explosion = new sound("/CodeInvaders_Game/SoundEffects/explosion.wav");
    }

    /**
     * Zeichnet den Gegner auf dem Bildschirm.
     * @param graphic
     */
    public void draw(Graphics2D graphic) {
        if (!enemySprite.IfAniIsDestroyed()) {
            enemySprite.draw(graphic);
        }
    }

    /**
     * Aktualisiert den Zustand des Gegners in jedem Frame.
     * @param delta
     * @param player
     * @param block
     */
    public void update(double delta, Player player, NormalBlocks block) {
        enemySprite.update(delta);
        enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
        this.getRect().x = (int) enemySprite.getxPos();
        if (enemySprite.IfAniIsDestroyed()){
            isDeath = true;
        }
        if (shootTimer.timerEvent(shootingTime) && player.isAlive()){
            getBulletSteering().addBullet(new EnemyBullet(getRect().x, getRect().y, player));
            shootingTime = new Random().nextInt(12000);
        }
    }

    /**
     * Ändert die Bewegungsrichtung des Gegners.
     * @param delta
     */
    public void DirectionChange(double delta) {
        speed *= -1;
        enemySprite.setxPos(enemySprite.getxPos()- (delta * speed));
        this.getRect().x = (int) enemySprite.getxPos();

        enemySprite.setyPos(enemySprite.getyPos() + (delta * 1));
        this.getRect().y = (int) enemySprite.getyPos();
    }

    /**
     * Verarbeitet die Todesanimation des Gegners.
     * @return
     */
    public boolean deathScene() {
        if (!enemySprite.isPlay()){
            return false;
        }
        if (enemySprite.IfAniIsDestroyed()){
            if (!explosion.isPlaying()){
                explosion.play();
            }
            return true;
        }
        return false;
    }

    /**
     * Überprüft, ob der Gegner mit einem anderen Objekt kollidiert.
     * @param i
     * @param player
     * @param block
     * @param enemy
     * @return
     */
    public boolean collide(int i, Player player, NormalBlocks block, ArrayList<TypeBasic> enemy) {
        if (enemySprite.isPlay()){
            if (enemy.get(i).deathScene()){
                enemy.remove(i);
            }
            return false;
        }

        for (int w = 0; w < player.playerWeapon.weapon.size(); w++){
            if (enemy != null && player.playerWeapon.weapon.get(w).collisionRect(((TypeBasic) enemy.get(i)).getRect())){
                enemySprite.resetLimit();
                enemySprite.setAniSpeed(60);
                enemySprite.setPlay(true, true);
                GScreen.score += 10;
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüft, ob der Gegner gestorben ist.
     * @return
     */
    public boolean isDeath(){
        return isDeath;
    }

    /**
     * Überprüft, ob der Gegner die Bildschirmgrenzen überschritten hat.
     * @return
     */
    public boolean isOutofBounds() {
        if (rect.x > 0 && rect.x < Display.WIDTH - rect.width){
            return false;
        }
        return true;
    }


    /**
     * Getter für das Rechteck des Gegners.
      * @return
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Setter für das Rechteck des Gegners.
     * @param rect
     */
    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Getter für die BulletSteering-Instanz des Gegners.
     * @return
     */
    public EnemyBulletSteering getBulletSteering(){
        return bulletSteering;
    }
}
