package managers;

import enemies.*;
import helperMethods.*;
import objects.WayPoint;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static helperMethods.Constants.Enemies.*;

/**
 * Diese Klasse verwaltet die Feinde (Enemies) im Spiel.
 */
public class EnemyManager {

    private Playing playing; // Referenz auf das Playing-Objekt, das das Spiel darstellt.
    private BufferedImage[] enemyImgs; // Array von Bildern für die verschiedenen Feinde.
    private ArrayList<Enemy> enemies = new ArrayList<>(); // Liste aller Feinde im Spiel.
    private ArrayList<WayPoint> waypoints = new ArrayList<>(); // Wegpunkte, die Feinde folgen.
    private int HPBarWidth = 20; // Breite der Gesundheitsbalken der Feinde.

    /**
     * Konstruktor des EnemyManagers.
     *
     * @param playing Referenz auf das Playing-Objekt.
     */
    public EnemyManager(Playing playing) {
        this.playing = playing;
        enemyImgs = new BufferedImage[5]; // Initialisiert das Array für fünf verschiedene Feindtypen.
        loadEnemyImgs(); // Lädt die Bilder der Feinde.
        initializeWaypoints(); // Initialisiert die Wegpunkte.
    }

    /**
     * Lädt die Bilder für verschiedene Feindtypen.
     */
    private void loadEnemyImgs() {
        // Lädt die Bilder der verschiedenen Feindtypen aus den Ressourcen.
        enemyImgs[0] = LoadSave.getVRed();
        enemyImgs[1] = LoadSave.getVBlue();
        enemyImgs[2] = LoadSave.getVYellow();
        enemyImgs[3] = LoadSave.getVPink();
        enemyImgs[4] = LoadSave.getVBoss();
    }

    /**
     * Aktualisiert den Zustand aller Feinde.
     */
    public void update() {
        // Bewegt jeden lebenden Feind zum nächsten Wegpunkt.
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                e.moveTowardWaypoint(waypoints);
            }
        }
    }

    /**
     * Initialisiert die Wegpunkte, denen die Feinde folgen.
     */
    private void initializeWaypoints() {
        // Hinzufügen von Wegpunkten zu der ArrayList.
        waypoints.add(new WayPoint(13*16, 17*16));
        waypoints.add(new WayPoint(13*16, 25*16));
        waypoints.add(new WayPoint(21*16, 25*16));
        waypoints.add(new WayPoint(21*16, 12*16));
        waypoints.add(new WayPoint(30*16, 12*16));
        waypoints.add(new WayPoint(30*16, 25*16));
        waypoints.add(new WayPoint(40*16, 25*16));
    }

    /**
     * Fügt einen neuen Feind zum Spiel hinzu.
     *
     * @param playing Das Playing-Objekt, das das Spiel darstellt.
     * @param x Die X-Position des Feindes.
     * @param y Die Y-Position des Feindes.
     * @param enemyType Der Typ des Feindes.
     */
    public void addEnemy(Playing playing, int x, int y, int enemyType) {
        // Erstellt einen neuen Feind basierend auf dem Typ und fügt ihn zur Liste hinzu.
        switch (enemyType) {
            case VRED:
                enemies.add(new VirusRed(playing, x, y, 0, this));
                break;
            case VBLUE:
                enemies.add(new VirusBlue(playing,x, y, 0,this));
                break;
            case VYELLOW:
                enemies.add(new VirusYellow(playing,x, y, 0,this));
                break;
            case VPINK:
                enemies.add(new VirusPink(playing,x, y, 0,this));
                break;
            case VBOSS:
                enemies.add(new VirusBoss(playing,x, y, 0,this));
                break;
        }
    }

    /**
     * Zeichnet alle Feinde und deren Gesundheitsbalken.
     *
     * @param g Das Graphics-Objekt für das Zeichnen.
     */
    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                drawEnemy(e, g);
                drawHealthBar(e, g);
            }
        }
    }
    /**
     * Zeichnet den Gesundheitsbalken eines Feindes.
     *
     * @param e Der Feind, dessen Gesundheitsbalken gezeichnet werden soll.
     * @param g Das Graphics-Objekt für das Zeichnen.
     */
    private void drawHealthBar(Enemy e, Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) e.getX() - 1, (int) e.getY() - 10, getNewHPBarWidth(e), 3);
    }

    /**
     * Berechnet die neue Breite des Gesundheitsbalkens basierend auf dem aktuellen Gesundheitszustand des Feindes.
     *
     * @param e Der Feind, dessen Gesundheitsbalken aktualisiert wird.
     * @return Die neue Breite des Gesundheitsbalkens.
     */
    private int getNewHPBarWidth(Enemy e) {
        return (int) (HPBarWidth * e.getHealthBarFloat());
    }

    /**
     * Zeichnet einen Feind auf dem Bildschirm.
     *
     * @param e Der zu zeichnende Feind.
     * @param g Das Graphics-Objekt für das Zeichnen.
     */
    private void drawEnemy(Enemy e, Graphics g) {
        g.drawImage(enemyImgs[e.getEnemyType()], (int)e.getX() - 2, (int)e.getY() - 3, 22, 22, null);
    }

    /**
     * Gibt die Liste aller Feinde zurück.
     *
     * @return Die Liste aller Feinde.
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Berechnet die Anzahl der noch lebenden Feinde.
     *
     * @return Die Anzahl der lebenden Feinde.
     */
    public int getAmountOfAliveEnemies() {
        int size = 0;
        for (Enemy e : enemies) {
            if (e.isAlive())
                size++;
        }
        return size;
    }

    /**
     * Belohnt den Spieler, wenn ein Feind besiegt wird.
     *
     * @param enemyType Der Typ des besiegten Feindes.
     */
    public void rewardPlayer(int enemyType) {
        playing.rewardPlayer(enemyType);
    }

    /**
     * Setzt die Liste der Feinde zurück (leert sie).
     */
    public void reset() {
        enemies.clear();
    }
}