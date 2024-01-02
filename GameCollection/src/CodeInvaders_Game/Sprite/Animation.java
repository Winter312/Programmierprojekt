package CodeInvaders_Game.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import CodeInvaders_Game.Timer.timer;

import javax.imageio.ImageIO;

public class Animation {

    private ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
    private byte currentSprite;
    private boolean loop = false;
    private boolean play = false;
    private boolean destroyAnim = false;
    private CodeInvaders_Game.Timer.timer timer;
    private int AniSpeed;
    private int width, height;
    private int limit;


    private double xPos, yPos;

    /**
     * Lädt eine Sprite-Map und teilt sie in einzelne Frames auf, basierend auf der angegebenen Anzahl von Reihen und Spalten.
     * @param xPos
     * @param yPos
     * @param rows
     * @param columns
     * @param ASpeed
     * @param imgPath
     */
    public Animation(double xPos, double yPos, int rows, int columns, int ASpeed, String imgPath){
        this.AniSpeed = ASpeed;
        this.xPos = xPos;
        this.yPos = yPos;

        try {
            URL url = this.getClass().getResource(imgPath);
            BufferedImage pSprite = ImageIO.read(url);
            int spriteWidth= pSprite.getWidth() / columns;
            int spriteHeight = pSprite.getHeight() / rows;
            for (int y = 0; y < rows; y++){
                for (int x = 0; x < columns; x++){
                    AddSprites(pSprite, 0 +(x *spriteWidth), 0 + (y * spriteHeight) , spriteWidth, spriteHeight);
                }
            }

        }catch (IOException e){};

        timer = new timer();
        limit = sprites.size() - 1;
    }

    /**
     * Zeichnet den aktuellen Frame der Animation an der festgelegten Position.
     * @param graphic
     */
    public void draw(Graphics2D graphic) {
        if(IfAniIsDestroyed()) {
            System.out.println("destroyedd");
            return;
        }

        graphic.drawImage(sprites.get(currentSprite),(int) getxPos(), (int) getyPos(),width , height, null);
    }

    /**
     * Aktualisiert den aktuellen Frame der Animation basierend auf dem Timer und den Steuerungsvariablen.
     * @param delta
     */
    public void update(double delta){
        if(IfAniIsDestroyed()){
            return;
        }
        if (loop && !play){
            loopAni();
        }
        if (play && !loop){
            playAni();
        }
    }

    /**
     * Hilfsmethoden zur Steuerung des Ablaufs der Animation im Loop- oder Play-Modus.
     */
    private void loopAni(){
        if (timer.IfTimerReady(AniSpeed) && currentSprite == limit) {
            currentSprite = 0;
            timer.resetTimer();
        } else if (timer.timerEvent(AniSpeed) && currentSprite != limit) {
            currentSprite++;
        }
    }

    private void playAni(){
          if (timer.IfTimerReady(AniSpeed) && currentSprite == limit || IfAniIsDestroyed()) {
            sprites = null;
        } else if (timer.timerEvent(AniSpeed) && currentSprite != limit) {
            currentSprite++;
        }
          else if (timer.IfTimerReady(AniSpeed) && currentSprite != limit && !IfAniIsDestroyed()){
              play = false;
              currentSprite = 0;
          }
    }
    /**
     * Methoden zum Einstellen der Abspielmodi der Animation.
     * @return
     */
    public void setLoop(boolean loop){
        this.loop = loop;
    }

    /**
     * Überprüft, ob die Animation zerstört wurde.
     * @return
     */
    public boolean IfAniIsDestroyed(){
      if (sprites == null){
          return true;
      }
      return false;
      }

    /**
     * Fügt einen neuen Frame zur Animation hinzu.
     * @param spriteMap
     * @param xPos
     * @param yPos
     * @param width
     * @param height
     */
    public void AddSprites(BufferedImage spriteMap, int xPos, int yPos, int width, int height){
        sprites.add(spriteMap.getSubimage(xPos,yPos,width,height));
    }
    /**
     * Setter-Methoden zum Einstellen verschiedener Eigenschaften der Animation.
     */
    public void setPlay(boolean play, boolean destroyAnim){
        if(loop){
            loop = false;
        }
        this.play = play;
        this.destroyAnim = destroyAnim;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public void setLimit(int limit) {
        this.limit = limit;
        if (limit > 0){
            this.limit = limit - 1;
        } else {
            this.limit = limit;
        }
    }


    public void setAniSpeed(int aniSpeed) {
        AniSpeed = aniSpeed;
    }

    public void resetLimit(){
        limit = sprites.size() - 1;
    }
    public boolean isPlay() {
        return play;
    }
}
