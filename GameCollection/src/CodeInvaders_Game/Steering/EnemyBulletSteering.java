package CodeInvaders_Game.Steering;
import CodeInvaders_Game.EnemyBullet.EnemyWeapon;
import CodeInvaders_Game.Explosion.Manager;
import CodeInvaders_Game.GameScreen.NormalBlocks;
import CodeInvaders_Game.GameScreen.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class EnemyBulletSteering {
    /**
     *  Eine Liste von EnemyWeapon-Objekten, die die verschiedenen feindlichen Waffen im Spiel darstellt.
     */
    private List<EnemyWeapon> weaponType = new ArrayList<>();

    /**
     * Fügt ein neues feindliches Waffenobjekt zur Liste hinzu.
     * @param weapons
     */
    public void addBullet(EnemyWeapon weapons){
        this.weaponType.add(weapons);
    }

    /**
     * Zeichnet alle feindlichen Waffen auf dem Bildschirm.
     * @param graphic
     */
    public void draw (Graphics2D graphic){
        for (EnemyWeapon enemyWeapon : weaponType){
                enemyWeapon.draw(graphic);
        }
    }

    /**
     * Aktualisiert den Zustand jeder Waffe in der Liste.
     * Überprüft Kollisionen zwischen den Waffen und dem Spieler.
     * Erzeugt eine Explosion und entfernt die Waffe bei einer Kollision.
     * Ruft die hit-Methode des Spielers auf, wenn es zu einer Kollision kommt.
     * @param delta
     * @param block
     * @param player
     */
    public void update(double delta, NormalBlocks block, Player player){
        for (int i = 0; i < weaponType.size(); i++) {
            weaponType.get(i).update(delta, block, player);
            if (weaponType.get(i).collision(player.getRect())){
                Manager.createPExplosion(weaponType.get(i).getXPos(),weaponType.get(i).getYPos());
                weaponType.remove(i);
                player.hit();
            }
        }
    }

    /**
     * Setzt die Liste der feindlichen Waffen zurück, indem sie geleert wird.
     */
    public void reset() {
        weaponType.clear();
    }
}
