package PixelPinesProtection.managers;

import PixelPinesProtection.enemies.Enemy;
import PixelPinesProtection.helperMethods.Constants;
import PixelPinesProtection.helperMethods.LoadSave;
import PixelPinesProtection.objects.Projectile;
import PixelPinesProtection.objects.Tower;
import PixelPinesProtection.scenes.Playing;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static PixelPinesProtection.helperMethods.Constants.Projectiles.*;
import static PixelPinesProtection.helperMethods.Constants.Towers.*;

public class ProjectileManager {
    private Playing playing;
    private ArrayList<Projectile> projectiles= new ArrayList<>();
    private BufferedImage[] projImgs;
    private int projID = 0;
    public ProjectileManager(Playing playing) {
        this.playing = playing;

        loadPorjectileImgs();
    }

    /**
     * berechnet die benötigten Bewegungen der Projektile und erzeugt diese Projektile und fügt sie der
     * ArrayList projectiles hinzu.
     * @param t
     * @param e
     */
    public void newProjectile(Tower t, Enemy e) {
        int type = getProjType(t);

        int xDist = (int) ((t.getX() + (float) e.getBounds().width/2)- (e.getX()+ (float) e.getBounds().width /2));
        int yDist = (int) ((t.getY() + (float) e.getBounds().height/2)- (e.getY()+ (float) e.getBounds().height /2));
        int totDist = Math.abs(xDist) + Math.abs(yDist);

        float xPer = (float) Math.abs(xDist) / totDist;

        float xSpeed = xPer * Constants.Projectiles.getSpeed(type);
        float ySpeed = Constants.Projectiles.getSpeed(type) - xSpeed;

        if (t.getX() > e.getX())
            xSpeed *= -1;
        if (t.getY() > e.getY())
            ySpeed *= -1;

        float angle = (float) Math.atan2(yDist, xDist);
        float rotate = (float) Math.toDegrees(angle);

        projectiles.add(new Projectile(t.getX(), t.getY() , xSpeed, ySpeed, t.getDmg(), rotate, projID++, type));

    }

    /**
     * Zeichnet alle aktiven Projektile auf dem Bildschirm.
     * @param g Das Graphics-Objekt zum Zeichnen.
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).isActive()) {
                //draw hitbox proj
//                g.drawRect(p.bounds.x, p.bounds.y,p.bounds.width,p.bounds.height);

                AffineTransform backup = g2d.getTransform();
                AffineTransform trans = new AffineTransform();
                trans.translate(projectiles.get(i).getPos().x, projectiles.get(i).getPos().y);
                trans.rotate(Math.toRadians(projectiles.get(i).getRotation() - 90));

                g2d.setTransform(trans);
                g2d.drawImage(projImgs[projectiles.get(i).getProjectileType()],-16,-16, 32, 32, null);

                g2d.setTransform(backup);
            }
        }
    }

    /**
     * Aktualisiert die Zustände der Projektile (Bewegung, Kollisionen usw.).
     */
    public void update() {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.move();
                if (isProjHittingEnemy(p)) {
                    p.setActive(false);
                    if (p.getProjectileType() == ROCKET) {
                        aoeOnEnemies(p);
                    }
                } else if (isProjOutsideBounds(p)) {
                    p.setActive(false);
                }

            }
        }
    }



    /**
     * Führt eine Flächenschaden-Attacke (Area of Effect, AoE) auf Gegnern in der Nähe des getroffenen Punktes durch.
     * @param p Das Projektil, das den AoE-Angriff ausführt.
     */
    private void aoeOnEnemies(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                float radius = 55.0f;  // Der Radius des AoE-Angriffs.
                float xDist = Math.abs(p.getPos().x - e.getX());  // X-Distanz zwischen Projektil und Feind.
                float yDist = Math.abs(p.getPos().y - e.getY());  // Y-Distanz zwischen Projektil und Feind.
                float realDist = (float) Math.hypot(xDist, yDist);  // Tatsächliche Distanz zwischen Projektil und Feind.

                // Fügt Schaden zu, wenn der Feind innerhalb des Radius ist.
                if (realDist <= radius) {
                    e.hurt(p.getDmg());
                }
            }
        }
    }

    /**
     * Prüft, ob ein Projektil einen Feind trifft.
     * @param p Das zu überprüfende Projektil.
     * @return Gibt 'true' zurück, wenn das Projektil einen Feind trifft.
     */
    private boolean isProjHittingEnemy(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                // Überprüft, ob die Hitbox des Projektils die des Feindes schneidet.
                if (e.getBounds().intersects(p.bounds)) {
                    e.hurt(p.getDmg());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Überprüft, ob ein Projektil außerhalb der Grenzen des Spielfelds ist.
     * @param p Das zu überprüfende Projektil.
     * @return Gibt 'true' zurück, wenn das Projektil außerhalb der Grenzen ist.
     */
    private boolean isProjOutsideBounds(Projectile p) {
        // Überprüft, ob sich das Projektil innerhalb der Bildschirmgrenzen befindet.
        return !(p.getPos().x >= 0) || !(p.getPos().x <= 640) || !(p.getPos().y >= 0) || !(p.getPos().y <= 640);
    }

    /**
     * Lädt die Bilder für die verschiedenen Projektiltypen.
     */
    private void loadPorjectileImgs() {
        projImgs = new BufferedImage[3];
        // Lädt die Bilder für jeden Projektiltyp.
        projImgs[0] = LoadSave.getRocket();
        projImgs[1] = LoadSave.getFireSlim();
        projImgs[2] = LoadSave.getFireFat();
    }

    /**
     * Bestimmt den Typ des Projektils basierend auf dem Turmtyp.
     * @param t Der Turm, für den der Projektiltyp bestimmt wird.
     * @return Die Projektil-ID.
     */
    private int getProjType(Tower t) {
        // Bestimmt den Projektiltyp basierend auf dem Typ des Turms.
        switch (t.getTowerType()) {
            case ROCKETLAUNCHER:
                return ROCKET;
            case GREEN:
                return FIRESLIM;
            case RED:
                return FIREFAT;
        }
        return 0;
    }

    /**
     * Setzt die Liste der Projektile zurück.
     */
    public void reset() {
        projectiles.clear();
        projID = 0;
    }
}
