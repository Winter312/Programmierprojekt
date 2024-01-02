package managers;

import enemies.Enemy;
import helperMethods.LoadSave;
import helperMethods.Util;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount = 0;
    private int[][] hitbox = LoadSave.getLevelData();

    public TowerManager(Playing playing) {
        this.playing = playing;
        towerImgs = new BufferedImage[3];
        loadTowerImgs();
    }

    // Lädt die Bilder für die verschiedenen Turmarten.
    private void loadTowerImgs() {
        towerImgs[0] = LoadSave.getRocketTow();
        towerImgs[1] = LoadSave.getGreenTow();
        towerImgs[2] = LoadSave.getredTow();
    }

    // Zeichnet die Türme auf dem Spielfeld.
    public void draw(Graphics g) {
        for (Tower t : towers) {
            g.drawImage(towerImgs[t.getTowerType()], t.getX() - 2, t.getY() - 1, 35, 35, null);
        }
    }

    // Gibt den Turm an der angegebenen Position zurück, falls vorhanden.
    public Tower getTowerAt(int x, int y) {
        for (Tower t : towers) {
            if (t.getX() == x && t.getY() == y) {
                return t;
            }
        }
        return null;
    }

    // Fügt einen neuen Turm hinzu, wenn das Feld dafür vorgesehen ist.
    public void addTower(Tower selectedTower, int xPos, int yPos) {
        if (hitbox[xPos / 32][yPos / 32] != 0)
            towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
    }

    // Entfernt einen Turm vom Spielfeld.
    public void removeTower(Tower displayedTower) {
        towers.removeIf(t -> t.getId() == displayedTower.getId());
    }

    // Führt ein Upgrade eines Turms durch.
    public void upgradeTower(Tower displayedTower) {
        towers.stream()
                .filter(t -> t.getId() == displayedTower.getId())
                .forEach(Tower::upgradeTower);
    }

    // Aktualisiert alle Türme und führt Angriffe auf Gegner in Reichweite aus.
    public void update() {
        for (Tower t : towers) {
            t.update();
            attackEnemyIfInRange(t);
        }
    }

    // Greift einen Feind an, wenn er sich in Reichweite befindet.
    private void attackEnemyIfInRange(Tower t) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive() && isEnemyInRange(t, e) && t.isCooldownOver()) {
                playing.shootenemy(t, e);
                t.resetCooldown();
                break; // Beendet die Schleife nach dem Angriff auf den ersten in Reichweite befindlichen Feind
            }
        }
    }

    // Überprüft, ob ein Feind in Reichweite eines Turms ist.
    private boolean isEnemyInRange(Tower t, Enemy e) {
        int range = Util.getHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());
        return range < t.getRange();
    }

    // Getter für die Bilder der Türme.
    public BufferedImage[] getTowerImgs() {
        return towerImgs;
    }

    // Setzt den Zustand des TowerManagers zurück.
    public void reset() {
        towers.clear();
        towerAmount = 0;
    }
}
