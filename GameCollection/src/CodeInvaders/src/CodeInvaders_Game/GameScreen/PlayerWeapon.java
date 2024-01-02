package CodeInvaders_Game.GameScreen;

import CodeInvaders_Game.Bullets.MachGun;
import CodeInvaders_Game.Bullets.WeaponType;
import CodeInvaders_Game.Explosion.Manager;
import CodeInvaders_Game.Timer.*;
import java.awt.*;
import java.util.ArrayList;
import CodeInvaders_Game.Sounds.sound;
public class PlayerWeapon {
    private timer timer;
    private Manager manager;
    private sound shootSounds;
    public ArrayList<WeaponType> weapon = new ArrayList<WeaponType>();

    /**
     * Zeichnet die aktiven Waffen und die durch den Manager verwalteten Explosionseffekte auf den Bildschirm.
     * @param graphic
     */
    public void draw(Graphics2D graphic){
        manager.draw(graphic);
        for(int i = 0; i < weapon.size(); i++){
            weapon.get(i).draw(graphic);
        }
    }

    /**
     * Initialisiert den Manager, den Timer und lädt den Schusssound.
     */
    public PlayerWeapon(){
       manager = new Manager();
       timer = new timer();
       shootSounds = new sound("/CodeInvaders_Game/SoundEffects/shoot.wav");
    }

    /**
     * Aktualisiert den Zustand der Waffen und die Explosionseffekte.
     * Überprüft, ob eine Waffe zerstört werden soll, und löst ggf. eine Explosion aus.
     * @param delta
     * @param block
     */
    public void update(double delta, NormalBlocks block){
        manager.update(delta);
        for(int i = 0; i < weapon.size(); i++){
            weapon.get(i).update(delta,block);
            if (weapon.get(i).destroy()){
                Manager.createPExplosion(weapon.get(i).getxPos(),weapon.get(i).getyPos());
                weapon.remove(i);
            }
        }
    }

    /**
     * Steuert das Schießen der Waffen basierend auf der Position des Spielers und einem Zeitintervall.
     * Fügt neue Waffen der Liste hinzu, wenn geschossen wird und spielt den Schusssound ab.
     * @param xPos
     * @param yPos
     * @param width
     * @param height
     */
    public void shoot(double xPos, double yPos, int width, int height){
        if (xPos >= 325 && xPos <= 450d && timer.timerEvent(300)){
            if (shootSounds.isPlaying()) {
                shootSounds.stop();
            }
            shootSounds.play();
            weapon.add(new MachGun(xPos + 22, yPos + 15, width, height));
        }
        if (timer.timerEvent(600)) {
            if (shootSounds.isPlaying()) {
                shootSounds.stop();
            }
            shootSounds.play();
            weapon.add(new MachGun(xPos + 22, yPos + 15, width, height));
        }
    }
}
