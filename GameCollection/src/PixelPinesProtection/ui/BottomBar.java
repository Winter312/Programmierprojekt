package PixelPinesProtection.ui;

import PixelPinesProtection.helperMethods.Constants;
import PixelPinesProtection.objects.Tower;
import PixelPinesProtection.scenes.Playing;
import static PixelPinesProtection.main.GameStates.*;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * Klasse BottomBar repräsentiert die untere Informationsleiste im Spiel.
 */
public class BottomBar {
    private int x, y, width, height;
    private MyButton bMenu;
    private MyButton[] towerButtons;
    private MyButton sellTower, upgradeTower;
    private Playing playing;
    private Tower selectedTower;
    private Tower displayedTower;
    private DecimalFormat formatter;

    private int gold = 100;
    private boolean showTowerCost;
    private int towerCostType;

    private int lives = 10;

    public BottomBar(int x, int y, int width, int height, Playing playing) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.playing = playing;
        initButtons();
        formatter = new DecimalFormat("0.0");
    }

    /**
     * Setzt die Werte in der BottomBar zurück.
     */
    public void resetEverything() {
        lives = 10;
        towerCostType = 0;
        showTowerCost = false;
        gold = 100;
        selectedTower = null;
        displayedTower = null;
    }

    /**
     * Initialisiert die Schaltflächen in der BottomBar.
     */
    private void initButtons() {
        // Schaltflächen und deren Positionen werden definiert
        bMenu = new MyButton("Pause", 2, 650, 100, 30);

        // Initialisiert die Turm-Schaltflächen
        towerButtons = new MyButton[3];
        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);
        }

        // Verkaufs- und Upgrade-Schaltflächen
        sellTower = new MyButton("Sell", 490, 680, 50, 20);
        upgradeTower = new MyButton("Upgrade", 570, 680, 50, 20);
    }

    /**
     * Entfernt ein Leben und überprüft, ob das Spiel vorbei ist.
     */
    public void removeLife() {
        lives--;
        if (lives <= 0) {
            playing.getWaveManager().setCurrentLevel(1);
            SetGameState(GAME_OVER);
        }
    }

    /**
     * Zeichnet die Schaltflächen in der BottomBar.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    private void drawButtons(Graphics g) {
        bMenu.draw(g);

        for (MyButton b : towerButtons) {
            g.setColor(Color.gray);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);

            drawButtonFeedback(g, b);
        }
    }

    /**
     * Zeichnet die gesamte BottomBar.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    public void draw(Graphics g) {
        // Hintergrund
        g.setColor(new Color(188, 200, 20, 195));
        g.fillRect(x, y, width, height);

        // Schaltflächen, Türme und Informationen
        drawButtons(g);
        drawDisplayedTower(g);
        drawWaveInfo(g);
        drawLevelInfo(g);
        drawGoldAmount(g);
        if (showTowerCost) drawTowerCost(g);

        g.setColor(Color.BLACK);
        g.drawString("Lives: " + lives, 110, 740);
    }

    /**
     * Zeichnet die Kosteninformationen eines ausgewählten Turms.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    private void drawTowerCost(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(280,650,120,50);
        g.setColor(Color.BLACK);
        g.drawRect(280,650,120,50);

        g.drawString("" + getTowerCostName(), 285, 670);
        g.drawString("Cost: " + getTowerCostCost() + "g", 285, 690);

        // Anzeige einer Warnung, falls nicht genug Gold vorhanden ist
        if (isTowerCostMoreThanCurrentCost()) {
            g.setColor(Color.red);
            g.drawString("Not enough Gold!", 285, 715);
        }
    }

    /**
     * Zeichnet Informationen zum aktuellen Level.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    private void drawLevelInfo(Graphics g) {
        int currentLevel = playing.getWaveManager().getCurrentLevel();
        g.drawString("Level: " + currentLevel, 110, 765);
    }

    /**
     * Zeichnet die aktuelle Goldmenge.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    private void drawGoldAmount(Graphics g) {
        g.drawString("Gold: " + gold, 110, 715);
    }

    /**
     * Zeichnet Informationen zu Wellen, Feinden und verbleibender Zeit.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    private void drawWaveInfo(Graphics g) {
        g.setColor(Color.BLACK);
        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWavesLeftInfo(g);
    }

    private void drawWavesLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex();
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Waves " + (current+1) + " / " + size, 525,740);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
        g.drawString("Enemies Left: " + remaining,525,760);

    }

    private void drawWaveTimerInfo(Graphics g) {
        if (playing.getWaveManager().isTimerStarted()) {
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = formatter.format(timeLeft);
            g.drawString("Time Left : " + formattedText,525,720);
        }
    }

    /**
     * Zeichnet Informationen und Optionen für den aktuell ausgewählten Turm.
     * @param g Das Graphics-Objekt, mit dem gezeichnet wird.
     */
    private void drawDisplayedTower(Graphics g) {
        if(displayedTower != null) {
            // Zeichnet den Hintergrund und Rahmen für die Turminformationen
            g.setColor(Color.gray);
            g.fillRect(410, 645, 220, 60);
            g.setColor(Color.BLACK);
            g.drawRect(410, 645, 220, 60);
            g.drawRect(420, 650, 50, 50);

            // Zeichnet das Bild des Turms
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 420, 650, 50, 50, null);

            // Zeichnet die Turm-Informationen
            g.setColor(Color.BLACK);
            g.drawString("" + Constants.Towers.getName(displayedTower.getTowerType()), 490, 660);
            g.drawString("DMG: " + displayedTower.getDmg(), 490, 675);
            g.drawString("Cooldown: " + (int) displayedTower.getCooldown(), 552, 675);
            g.drawString("Lvl: " + displayedTower.getTier(), 600, 660);

            // Zeichnet den Rand und die Reichweite des ausgewählten Turms
            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);

            // Zeichnet die Schaltflächen "Verkaufen" und "Aufrüsten"
            sellTower.draw(g);
            drawButtonFeedback(g, sellTower);
            if (displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
                upgradeTower.draw(g);
                drawButtonFeedback(g, upgradeTower);
            }

            // Zeigt Informationen beim Überfahren der Schaltflächen mit der Maus an
            if (sellTower.isMouseOver()) {
                g.setColor(Color.red);
                g.drawString("Sell for " + getSellAmount(displayedTower) + "g", 410, 720);
            } else if (upgradeTower.isMouseOver() && gold >= getUpgradeAmount(displayedTower)) {
                g.setColor(Color.blue);
                g.drawString("Upgrade for " + getUpgradeAmount(displayedTower) + "g", 410, 720);
            }
        }
    }


    private int getUpgradeAmount(Tower displayedTower) {
        return (int) (Constants.Towers.getTowerCost(displayedTower.getTowerType()) * 0.6f);
    }

    private int getSellAmount(Tower displayedTower) {
        int upgradeCost = (int) ((displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower) * 0.5f);
        return Constants.Towers.getTowerCost(displayedTower.getTowerType()) /2 + upgradeCost;
    }

    /**
     * zeichnet einen Kreis der die Range des Turmes anzeigt
     * @param g
     */
    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(displayedTower.getX() + 16 - ((int) displayedTower.getRange()*2/2),
                displayedTower.getY() + 16 - ((int) displayedTower.getRange()*2/2),
                (int) displayedTower.getRange()*2,(int) displayedTower.getRange()*2);
    }

    /**
     * zeichnet blauen Rand um ausgewählten Tower
     * @param g
     */
    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(Color.CYAN);
        g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
    }

    /**
     * Zeichnet den ausgewählten Tower in der Bottombar
     * @param t
     */
    public void displayTower(Tower t) {
        displayedTower = t;
    }

    /**
     * Zeichnet ein Rechteck mit Infos über den ausgewählten Tower
     * @param g
     * @param b
     */
    private void drawButtonFeedback(Graphics g, MyButton b) {
        //Mouseover
        if (b.isMouseOver()) {
            g.setColor(Color.white);
        } else {
            g.setColor(Color.BLACK);
        }
        //Border
        g.drawRect(b.x, b.y, b.width, b.height);

        //Mousepressed
        if (b.isMousePressed()) {
            g.drawRect(b.x+1, b.y+1, b.width-2, b.height -2);
            g.drawRect(b.x+2, b.y+2, b.width-4, b.height-4);
        }
    }

    private void sellTowerClicked() {
        playing.removeTower(displayedTower);
        gold += getSellAmount(displayedTower);
        displayedTower = null;
    }

    private void upgradeTowerClicked() {
        playing.upgradeTower(displayedTower);
        gold -= getUpgradeAmount(displayedTower);
    }


    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
        } else {

            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTowerClicked();
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
                    upgradeTowerClicked();
                    return;
                }
            }

            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x,y)) {
                    if (!isEnoughGold(b.getId()))
                        return;

                    selectedTower = new Tower(0,0,-1,b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }


    private boolean isEnoughGold(int towerType) {
        return gold >= Constants.Towers.getTowerCost(towerType);
    }


    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        showTowerCost = false;
        sellTower.setMouseOver(false);
        upgradeTower.setMouseOver(false);
        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else {

            if (displayedTower!= null) {
                if (sellTower.getBounds().contains(x,y)) {
                    sellTower.setMouseOver(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x,y)&& displayedTower.getTier() < 3){
                    upgradeTower.setMouseOver(true);
                    return;
                }
            }

            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    showTowerCost = true;
                    towerCostType = b.getId();
                    return;
                }
            }
        }

    }


    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else {

            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x,y)) {
                    sellTower.setMousePressed(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x,y)&& displayedTower.getTier() < 3){
                    upgradeTower.setMousePressed(true);
                    return;
                }
            }

            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
        sellTower.resetBooleans();
        upgradeTower.resetBooleans();
    }

    public void payForTower(int towerType) {
        this.gold -= Constants.Towers.getTowerCost(towerType);
    }

    public void addGold(int reward) {
        this.gold += reward;
    }

    private boolean isTowerCostMoreThanCurrentCost() {
        return getTowerCostCost() > gold;
    }

    private int getTowerCostCost() {
        return Constants.Towers.getTowerCost(towerCostType);
    }

    private String getTowerCostName() {
        return Constants.Towers.getName(towerCostType);
    }

    public int getLives() {
        return lives;
    }


}
