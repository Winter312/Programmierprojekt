package scenes;

import enemies.Enemy;
import helperMethods.Constants;
import helperMethods.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.Tower;
import ui.BottomBar;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Spielszene, die das Hauptspielgeschehen steuert.
 */
public class Playing extends GameScene implements SceneMethods {
    private BottomBar bottombar;
    private int mouseX, mouseY;
    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private ProjectileManager projManager;
    private WaveManager waveManager;
    private Tower selectedTower;
    private int[][] hitbox = LoadSave.getLevelData();
    private int goldTick;
    private Clip music = LoadSave.getAudioClip();


    public Playing(Game game) {
        super(game);
        bottombar = new BottomBar(0,640,640,130, this);
        enemyManager = new EnemyManager(this);
        towerManager = new TowerManager(this);
        projManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
    }

    /**
     * Aktualisiert das Spielgeschehen, einschließlich Wellenmanagement,
     * Gegnerbewegungen und Projektilupdates.
     */
    public void update() {
        updateTick();
        updateWaveManager();

        // Gold-Erzeugung basierend auf Zeitintervallen
        goldTick++;
        if (goldTick % (60 * 3) == 0) {
            bottombar.addGold(1);
        }

        // Prüft, ob alle Gegner besiegt sind und startet ggf. eine neue Welle
        if (isAllEnemiesDead()) {
            if (isThereMoreWaves()) {
                waveManager.startWaveTimer();
                if (isWaveTimerOver()) {
                    waveManager.increaseWaveIndex();
                    enemyManager.getEnemies().clear();
                    waveManager.resetEnemyIndex();
                }
            }
        }
        if (isTimeForNewEnemy()) {
            spawnEnemy();
        }
        enemyManager.update();
        towerManager.update();
        projManager.update();
    }

    /**
     * Überprüft, ob alle Gegner in der aktuellen Welle besiegt wurden.
     * @return true, wenn alle Gegner tot sind, sonst false.
     */
    private boolean isAllEnemiesDead() {
        if (waveManager.isThereMoreEnemiesInWave()) {
            return false;
        }
        for (Enemy e : enemyManager.getEnemies()) {
            if (e.isAlive())
                return false;
        }
        return true;
    }

    /**
     * Erzeugt einen neuen Gegner, basierend auf der aktuellen Welle.
     */
    private void spawnEnemy() {
        enemyManager.addEnemy(this, -4 * 20, 17 * 16, waveManager.getNextEnemy());
    }

    /**
     * Überprüft, ob es Zeit ist, einen neuen Gegner zu erzeugen.
     * @return true, wenn es Zeit für einen neuen Gegner ist, sonst false.
     */
    private boolean isTimeForNewEnemy() {
        if (getWaveManager().isTimeForNewEnemy()) {
            return getWaveManager().isThereMoreEnemiesInWave();
        }
        return false;
    }

    /**
     * Aktualisiert den Wellenmanager.
     */
    private void updateWaveManager() {
        this.getWaveManager().update();
    }

    public void setSelectedTower (Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        bottombar.draw(g);
        enemyManager.draw(g);
        towerManager.draw(g);
        projManager.draw(g);

        drawSelectedTower(g);
        drawHighlight(g);
    }

    /**
     * Zeichnet ein weißes Rechteck über den gehoverten Tile, wenn es sich um einen
     * gültigen Platzierungspunkt handelt, ansonsten wird es rot.
     * @param g Das Graphics-Objekt für das Zeichnen.
     */
    private void drawHighlight(Graphics g) {
        if (hitbox[mouseX/32][mouseY/32] != 0) {
            g.setColor(Color.white);
            g.drawRect(mouseX, mouseY, 32, 32);
        } else {
            g.setColor(Color.red);
            g.drawRect(mouseX, mouseY, 32, 32);
        }
    }

    /**
     * Zeichnet den ausgewählten Turm neben der Maus, wenn ein Turm zur Platzierung
     * ausgewählt wurde.
     * @param g Das Graphics-Objekt für das Zeichnen.
     */
    private void drawSelectedTower(Graphics g) {
        if (selectedTower != null)
            g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX-2, mouseY-1,35, 35, null);
    }

    /**
     * Zeichnet die Hintergrundkarte des Spiels.
     * @param g Das Graphics-Objekt für das Zeichnen.
     */
    private void drawLevel(Graphics g) {
        BufferedImage img = LoadSave.getMap1();
        g.drawImage(img,0,0,null);
    }



    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            bottombar.mouseClicked(x, y);
        }
        else {
            if(selectedTower != null) {
                if (getTowerAt(mouseX, mouseY) == null){
                towerManager.addTower(selectedTower, mouseX, mouseY);
                    removeGold(selectedTower.getTowerType());

                    selectedTower=null;

                }
            } else {
                //get tower if exists on xy
                Tower t = getTowerAt(mouseX,mouseY);
                    bottombar.displayTower(t);
            }
        }
    }

    private void removeGold(int towerType) {
        bottombar.payForTower(towerType);
    }

    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x,y);
    }
    public void upgradeTower(Tower displayedTower) {
        towerManager.upgradeTower(displayedTower);
    }
    public void removeTower(Tower displayedTower) {
        towerManager.removeTower(displayedTower);
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            selectedTower = null;
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            bottombar.mouseMoved(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;

        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640) {
            bottombar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
            bottombar.mouseReleased(x, y);
    }

    public void rewardPlayer(int enemyType) {
        bottombar.addGold(Constants.Enemies.getReward(enemyType));
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }


    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void shootenemy(Tower t, Enemy e) {
        projManager.newProjectile(t,e);
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }
    private boolean isWaveTimerOver() {
        return waveManager.isWaveTimeOver();
    }

    private boolean isThereMoreWaves() {
        return waveManager.isThereMoreWaves();
    }


    public void removeLife() {
        bottombar.removeLife();
    }

    public void resetEverything() {
        bottombar.resetEverything();

        enemyManager.reset();
        towerManager.reset();
        projManager.reset();
        waveManager.reset();

        mouseX = 0;
        mouseY = 0;
        selectedTower = null;
        goldTick = 0;
    }

    public Clip getMusic() {
        return music;
    }
}
