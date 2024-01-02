package CodeInvaders_Game.GameScreen;

import CodeInvaders_Game.GameDisplay.Display;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Player implements KeyListener {

    private final double speed = 6.0;
    private BufferedImage pSprite;
    private int health;
    private boolean alive;
    private boolean rPressed;

    private boolean mPressed;
    private boolean nPressed;
    private Rectangle rect;
    private double xPos, yPos, startxPos, startyPos;
    private int width, height;
    private NormalBlocks block;
    private boolean left = false, right = false, shoot = false;
    public PlayerWeapon playerWeapon;

    /**
     * Die Klasse verwaltet Spielerattribute wie Gesundheit, Position und Bewegung sowie die Interaktion mit Waffen.
     * @param xPos
     * @param yPos
     * @param width
     * @param height
     * @param block
     */
    public Player(double xPos, double yPos, int width, int height, NormalBlocks block){
        this.xPos = xPos;
        this.yPos = yPos;
        this.startxPos = xPos;
        this.startyPos = yPos;
        this.width = width;
        this.height = height;
        this.health = 3;
        this.alive = true;

        rect = new Rectangle((int)xPos, (int)yPos +15, width, height - 10);

        try {
            URL url = this.getClass().getResource("/CodeInvaders_Game/Images/Player.png");
            pSprite = ImageIO.read(url);
        }catch (IOException e){};
        this.block = block;
        playerWeapon = new PlayerWeapon();
    }

    /**
     * Zeichnet den Spieler und seine Waffen auf den Bildschirm.
     * @param graphic
     */
    public void draw(Graphics2D graphic){
        if (alive){
            graphic.drawImage(pSprite, (int)xPos, (int)yPos,width,height,null);
            playerWeapon.draw(graphic);
        }

    }

    /**
     * Aktualisiert die Position und den Zustand des Spielers basierend auf Tastatureingaben und Spiellogik.
     * @param delta
     */
    public void update(double delta){
        if (right && !left && xPos < Display.WIDTH-width){
            xPos += speed * delta;
            rect.x = (int) xPos;
        }
        if (!right && left && xPos > 10){
            xPos -= speed * delta;
            rect.x = (int) xPos;
        }
        playerWeapon.update(delta, block);

        if (shoot && isAlive()){
            playerWeapon.shoot(xPos, yPos, 5, 5);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Behandelt Tastendrücke und -freigaben zur Steuerung des Spielers.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                right = true;
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                left = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            shoot = true;
        }
        if (key == KeyEvent.VK_R){
            SetrPressed(true);
        }

        if (key == KeyEvent.VK_M){
            SetMPressed(true);
        }
        if (key == KeyEvent.VK_N){
            SetNPressed(true);
        }
    }
    /**
     * Behandelt Tastendrücke und -freigaben zur Steuerung des Spielers.
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                right = false;
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                left = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            shoot = false;
        }
        if (key == KeyEvent.VK_R ){
            SetrPressed(false);
        }

        if (key == KeyEvent.VK_M){
            SetMPressed(false);
        }
        if (key == KeyEvent.VK_N){
            SetNPressed(false);
        }

    }

    /**
     * Verringert die Gesundheit des Spielers bei einem Treffer.
     */
    public void hit(){
        if (isAlive()) {
            setHealth(getHealth() - 1);
        }
    }

    /**
     * Getter und Setter für die Gesundheit des Spielers.
     * @return
     */
    public int getHealth(){
        return health;
    }

    public int setHealth(int health){
        this.health = health;
        return health;
    }

    public Rectangle getRect(){
        return rect;
    }

    /**
     * Setzt den Spielerstatus auf den Anfangszustand zurück.
     */
    public void reset() {
        health = 3;
        left = false;
        right = false;
        shoot = false;
        xPos = startxPos;
        yPos = startyPos;
        rect.x  = (int) xPos;
        rect.y = (int) yPos + 25;
    }

    /**
     * Getter und Setter für den Status, ob der Spieler lebendig ist.
     * @return
     */
    public boolean isAlive() {
        return alive;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Steuert den Zustand der 'R'-Taste.
     * @param bool
     */
    public void SetrPressed(boolean bool){
        this.rPressed = bool;
    }
    public boolean GetrPressed(){
        return rPressed;
    }

    /**
     * Steuern den Zustand der 'M' und 'N'-Tasten.
     * @return
     */
    public boolean GetMPressed(){
        return mPressed;
    }

    public void SetMPressed(boolean bool){
        this.mPressed = bool;
    }

    public boolean GetNPressed(){
        return nPressed;
    }

    public void SetNPressed(boolean bool){
        this.nPressed = bool;
    }
}
