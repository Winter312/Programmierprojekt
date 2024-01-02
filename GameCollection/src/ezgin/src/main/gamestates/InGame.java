package main.gamestates;

import main.GameController;
import main.entities.*;
import main.entities.livingentities.Enemy;
import main.entities.nonlivingentities.CheckPoint;
import main.entities.nonlivingentities.Chest;
import main.enums.Level;
import main.enums.TutorialState;
import main.ui.buttons.DefaultButton;
import main.ui.buttons.MiniButton;

import javax.sound.sampled.DataLine;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

import static main.enums.GameState.*;
import static main.enums.Level.*;
import static main.enums.TutorialState.*;
import static utils.Constants.GameConstants.*;
import static utils.Constants.GameConstants.SCREEN_HEIGHT;
import static utils.Constants.ImageConstants.*;
import static utils.Constants.UIConstants.*;
import static utils.Load.getLevelData;

/**
 * Klasse, die den InGame-GameState repräsentiert
 */
public class InGame {

    private static InGame instance; // Singleton-Instanz
    private EntityHandler entityHandler; // EntityHandler-Instanz
    private ArrayList<SuperEntity> arr; // Liste, die die Reihenfolge der Entities festlegt, die gezeichnet werden sollen
    private DefaultButton[] buttons; // Array, das die Buttons des Tutorials enthält
    private boolean jumpToNextLevel; // Level-Wechsel-Boolean
    private long timeSinceLastLevelChange; // Zeit seit dem letzten Level-Wechsel

    // Variablen für die Kollisionsabfrage
    private static int[][] lvlWall;
    private static boolean collision = false;

    // Bildschirm-Koordinaten
    private static int screenX = SCREEN_X_DEFAULT;
    private static int screenY = SCREEN_Y_DEFAULT;

    private boolean inTransition = false;
    private boolean fadingIn = true;
    private float alpha = 0.0f;

    private InGame() {
        setEntityHandler(EntityHandler.getInstance());
        initInGame();
        GameController.getInstance().getMusicClips().put("InGame", MUSIC_GAME);
        GameController.getInstance().getMusicClips().put("Castle", MUSIC_CASTLE);

    }

    /**
     * Gibt die Singleton-Instanz zurück
     *
     * @return Singleton-Instanz
     */
    public static InGame getInstance() {
        if (instance == null) {
            setInstance(new InGame());
        }
        return instance;
    }

    /**
     * Aktualisiert den InGame-GameState
     */
    public void update() {
        if (isInTransition() && isFadingIn()) {
            EntityHandler.getInstance().getPlayer().resetMovement();
            return;
        }
        if (getCurrentTutorialState() == NONE || getCurrentTutorialState() == GAME_BEGIN) {
            getEntityHandler().update(); // aktualisiert den EntityHandler
            updateProgress(); // aktualisiert den Fortschritt
            getArr().sort((o1, o2) -> Float.compare(o1.getPos(), o2.getPos())); // sortiert die DrawOrder nach der Y-Position
        } else {
            getEntityHandler().getPlayer().setAttacking(false);
            getEntityHandler().getPlayer().setAttackAnimation(false);
        }
        if (getCurrentLevel() == LEVEL_ZERO) {
            checkTutorialTriggers();
        }
    }

    /**
     * initialisiert den InGame-GameState
     */
    public void initInGame() {
        setLvlWall(getLevelData(getCurrentLevel().getMapFiles()[2]));
        setArr(new ArrayList<>());
        getArr().add(getEntityHandler().getPlayer());
        getArr().addAll(Arrays.asList(getEntityHandler().getEnemies()));
        getArr().addAll(Arrays.asList(getEntityHandler().getChests()));
        getArr().addAll(Arrays.asList(getEntityHandler().getStelas()));
        getArr().addAll(Arrays.asList(getEntityHandler().getCheckPoints()));
        getArr().addAll(Arrays.asList(getEntityHandler().getDoors()));
        getArr().addAll(Arrays.asList(getEntityHandler().getLuminas()));
        if (getCurrentLevel() == LEVEL_ZERO) {
            initTutorialButtons();
        } else {
            setButtons(null);
        }
    }

    /**
     * initialisiert die Buttons für das Tutorial
     */
    private void initTutorialButtons() {
        setButtons(new DefaultButton[10]);
        getButtons()[0] = new MiniButton(MENU_TILE_SIZE * 7, MENU_TILE_SIZE * 7, "W_InGame");
        getButtons()[1] = new MiniButton(MENU_TILE_SIZE * 5, MENU_TILE_SIZE * 9, "A_InGame");
        getButtons()[2] = new MiniButton(MENU_TILE_SIZE * 7, MENU_TILE_SIZE * 9, "S_InGame");
        getButtons()[3] = new MiniButton(MENU_TILE_SIZE * 9, MENU_TILE_SIZE * 9, "D_InGame");
        getButtons()[4] = new MiniButton(MENU_TILE_SIZE / 2, MENU_TILE_SIZE * 5, "Esc_InGame");
        getButtons()[5] = new MiniButton((int) (SCREEN_WIDTH - MENU_TILE_SIZE * 1.75f), (int) (MENU_TILE_SIZE * 0.6875f), "+_InGame");
        getButtons()[6] = new DefaultButton(MENU_TILE_SIZE / 2, MENU_TILE_SIZE * 9, "Shift_InGame");
        getButtons()[7] = new DefaultButton(-MENU_TILE_SIZE * 2, MENU_TILE_SIZE * 7, "Tab_InGame");
        getButtons()[8] = new DefaultButton(-MENU_TILE_SIZE * 2, MENU_TILE_SIZE * 7, "Enter_InGame");
        getButtons()[9] = new MiniButton(-MENU_TILE_SIZE * 7 / 4, MENU_TILE_SIZE * 7, "Linke Maustaste_InGame");

        for (int i = 5; i < 9; i++) {
            getButtons()[i].setWidth(getButtons()[i].getWidth() / 2);
            if (buttons[i] instanceof MiniButton) {
                getButtons()[i].setHeight(getButtons()[i].getHeight() / 2);
            } else {
                getButtons()[i].setImage(getButtons()[i].getText().contains("Tab") ? BI_TAB : getButtons()[i].getText().contains("Shift") ? BI_SHIFT : BI_ENTER);
            }
        }
        getButtons()[9].setWidth(MENU_TILE_SIZE * 7 / 2);
        getButtons()[9].setHeight(MENU_TILE_SIZE * 10 / 2);
        getButtons()[9].setImage(BI_MOUSE);
    }

    /**
     * Gibt die X-Koordinate des Bildschirms auf der Welt zurück
     *
     * @return X-Koordinate des Bildschirms auf der Welt
     */
    public static float updateScreenX() {
        float screenX = EntityHandler.getInstance().getPlayer().isAlive() ? EntityHandler.getInstance().getPlayer().getWorldX() - getScreenX() : EntityHandler.getInstance().getEnemies()[EntityHandler.getInstance().getPlayer().getLastEnemyMadeDamageID()].getWorldX() - SCREEN_X_DEFAULT;
        if (screenX < 0) {
            screenX = 0;
        } else if (screenX > getWorldWidth() - SCREEN_WIDTH) {
            screenX = getWorldWidth() - SCREEN_WIDTH;
        }
        return screenX;
    }

    /**
     * Gibt die Y-Koordinate des Bildschirms auf der Welt zurück
     *
     * @return Y-Koordinate des Bildschirms auf der Welt
     */
    public static float updateScreenY() {
        float screenY = EntityHandler.getInstance().getPlayer().isAlive() ? EntityHandler.getInstance().getPlayer().getWorldY() - getScreenY() : EntityHandler.getInstance().getEnemies()[EntityHandler.getInstance().getPlayer().getLastEnemyMadeDamageID()].getWorldY() - SCREEN_Y_DEFAULT;
        if (screenY < 0) {
            screenY = 0;
        } else if (screenY > getWorldHeight() - SCREEN_HEIGHT) {
            screenY = getWorldHeight() - SCREEN_HEIGHT;
        }
        return screenY;
    }

    /**
     * bewegt den Bildschirm zu einem bestimmten Punkt
     *
     * @param target Punkt, zu dem der Bildschirm bewegt werden soll
     */
    public static void moveScreen(Point2D.Float target) {
        if (Math.abs(getScreenX() - target.x) < 3f && Math.abs(getScreenY() - target.y) < 3f) {
            return;
        }
        if (getScreenY() < target.y) {
            setScreenY((int) (getScreenY() + 3f));
        } else if (getScreenY() > target.y) {
            setScreenY((int) (getScreenY() - 3f));
        }
        if (getScreenX() < target.x) {
            setScreenX((int) (getScreenX() + 3f));
        } else if (getScreenX() > target.x) {
            setScreenX((int) (getScreenX() - 3f));
        }
    }

    /**
     * überprüft, ob ein Tutorial ausgelöst werden soll
     */
    private void checkTutorialTriggers() {
        // aktiviert das Tutorial, wenn der Spieler sich in der Nähe des Startpunktes befindet
        if (getEntityHandler().getPlayer().getWorldX() + getEntityHandler().getPlayer().getWidth() / 2f < TILE_SIZE * 4 && getEntityHandler().getPlayer().getWorldY() + getEntityHandler().getPlayer().getHeight() / 2f > TILE_SIZE * 25) {
            setCurrentTutorialState(GAME_BEGIN);
        } else if (getCurrentTutorialState() == GAME_BEGIN) {
            setCurrentTutorialState(NONE);
        }

        // aktiviert das Tutorial, wenn der Spieler sich in der Nähe der ersten Truhe befindet
        if (FIRST_CHEST.getDone() == 0) {
            if (getEntityHandler().getPlayer().getWorldX() > TILE_SIZE * 5 && getEntityHandler().getPlayer().getWorldY() > TILE_SIZE * 26) {
                setCurrentTutorialState(FIRST_CHEST);
                FIRST_CHEST.setDone(1);
            }
        }

        // aktiviert das Tutorial, wenn der Spieler den Dolch aufnimmt
        if (FIRST_HIT.getDone() == 0 && getEntityHandler().getChests()[0].isActive()) {
            if (getEntityHandler().getPlayer().getHitBox().intersects(getEntityHandler().getChests()[0].getHitBox())) {
                setCurrentTutorialState(FIRST_HIT);
                FIRST_HIT.setDone(1);
            }
        } else if (FIRST_PART.getDone() == 0 && FIRST_HIT.getDone() == 0) {
            if (getEntityHandler().getPlayer().getWorldX() < TILE_SIZE * 6 && getEntityHandler().getPlayer().getWorldY() + getEntityHandler().getPlayer().getHitBoxOffsetY() < TILE_SIZE * 26) {
                setCurrentTutorialState(FIRST_PART);
                FIRST_PART.setDone(1);
            }
        } else if (FIRST_PART.getDone() == 1) {
            Point2D.Float target = new Point2D.Float(getEntityHandler().getPlayer().getScreenX() + getEntityHandler().getPlayer().getWidth() / 2f, TILE_SIZE * 27 - updateScreenY());
            if (!getEntityHandler().getPlayer().posReached(target)) {
                getEntityHandler().getPlayer().moveToPos(target);
            } else {
                getEntityHandler().getPlayer().resetMovement();
                FIRST_PART.setDone(0);
            }
        }

        // aktiviert das Tutorial, wenn der Spieler sich in der Nähe des ersten Checkpoints befindet
        if (FIRST_CHECKPOINT.getDone() == 0) {
            if (getEntityHandler().getPlayer().getWorldX() > TILE_SIZE * 4 && getEntityHandler().getPlayer().getWorldX() < TILE_SIZE * 6 && getEntityHandler().getPlayer().getWorldY() > TILE_SIZE * 22 && getEntityHandler().getPlayer().getWorldY() < TILE_SIZE * 23) {
                setCurrentTutorialState(FIRST_CHECKPOINT);
                FIRST_CHECKPOINT.setDone(1);
            }
        }

        // aktiviert das Tutorial, wenn der Spieler sich in der Nähe der ersten Stela befindet
        if (FIRST_STELA.getDone() == 0 && getEntityHandler().getCheckPoints()[0].isActive()) {
            if (getEntityHandler().getPlayer().getWorldX() > TILE_SIZE * 3 && getEntityHandler().getPlayer().getWorldX() < TILE_SIZE * 4 && getEntityHandler().getPlayer().getWorldY() > TILE_SIZE * 20 && getEntityHandler().getPlayer().getWorldY() < TILE_SIZE * 23) {
                setCurrentTutorialState(FIRST_STELA);
                FIRST_STELA.setDone(1);
            }
        }

        // aktiviert das Tutorial, wenn der Spieler früher als vorgesehen den Startpunkt verlässt
        if (SECOND_PART.getDone() == 0 && (FIRST_CHEST.getDone() == 0 || FIRST_CHECKPOINT.getDone() == 0 || FIRST_STELA.getDone() == 0 || FIRST_HIT.getDone() == 0)) {
            if (getEntityHandler().getPlayer().getWorldX() > 6 * TILE_SIZE && getEntityHandler().getPlayer().getWorldY() + getEntityHandler().getPlayer().getHitBoxOffsetY() < 25 * TILE_SIZE) {
                setCurrentTutorialState(SECOND_PART);
                SECOND_PART.setDone(1);
            }
        } else if (SECOND_PART.getDone() == 1) {
            Point2D.Float target = new Point2D.Float(getEntityHandler().getPlayer().getScreenX() + getEntityHandler().getPlayer().getWidth() / 2f, TILE_SIZE * 26 - updateScreenY());
            if (!getEntityHandler().getPlayer().posReached(target)) {
                getEntityHandler().getPlayer().moveToPos(target);
            } else {
                getEntityHandler().getPlayer().resetMovement();
                SECOND_PART.setDone(0);
            }
        }

        // aktiviert das Tutorial, wenn der Spieler sich in der Nähe des ersten Gegners befindet
        if (FIRST_ENEMY.getDone() == 0) {
            if (getEntityHandler().getPlayer().getRange().intersects(getEntityHandler().getEnemies()[0].getRange())) {
                setCurrentTutorialState(FIRST_ENEMY);
                FIRST_ENEMY.setDone(1);
            }
        }

        // aktiviert das Tutorial, wenn der Spieler den ersten Gegner besiegt
        if (FIRST_ENEMY_KILL.getDone() == 0) {
            if (getCurrentLevel().getEnemiesKilled()[0] == 1) {
                setCurrentTutorialState(FIRST_ENEMY_KILL);
                FIRST_ENEMY_KILL.setDone(1);
            }
        }

        // aktiviert das Tutorial, wenn der Spieler alle Checkpoints aktiviert hat
        if (FIRST_LEVEL_END.getDone() == 0) {
            if (Arrays.stream(getCurrentLevel().getReachedCheckPoints()).allMatch(i -> i == 1)) {
                setCurrentTutorialState(FIRST_LEVEL_END);
                FIRST_LEVEL_END.setDone(1);
            }
        }
    }

    /**
     * überprüft, ob das SuperLivingEntity mit einem soliden Tile kollidiert
     * benutzt dafür die HitBox des SuperLivingEntity.
     * wenn ja, wird das SuperLivingEntity zurückgesetzt
     *
     * @param superLiving superLiving
     */
    public static void checkSolid(SuperLivingEntity superLiving) {
        // Koordinaten der HitBox des Entities in Default-Tile-Koordinaten
        float x1 = (superLiving.getWorldX() + superLiving.getHitBoxOffsetX()) / SCALE;
        float y1 = (superLiving.getWorldY() + superLiving.getHitBoxOffsetY()) / SCALE;
        float x2 = (float) ((superLiving.getWorldX() + superLiving.getHitBoxOffsetX() + superLiving.getHitBox().getWidth())) / SCALE;
        float y2 = (float) ((superLiving.getWorldY() + superLiving.getHitBoxOffsetY() + superLiving.getHitBox().getHeight())) / SCALE;

        // überprüft, ob das SuperEntity innerhalb der Welt ist
        if (x1 > 0 && y1 > 0 && x2 < (float) getDefaultWorldWidth() && y2 < (float) getDefaultWorldHeight()) {
            // überprüft, ob das SuperEntity mit einem soliden Tile kollidiert
            for (int i = (int) x1; i <= x2; i++) {
                for (int j = (int) y1; j <= y2; j++) {
                    if (getLvlWall()[i][j] == 0) {
                        setCollision(true);
                        break;
                    }
                }
            }
        } else {
            setCollision(true);
        }
        if (isCollision()) {
            superLiving.resetValues();
            superLiving.setWorldX(superLiving.getPrevWorldX());
            superLiving.setWorldY(superLiving.getPrevWorldY());
            setCollision(false);
        } else {
            superLiving.setPrevWorldX(superLiving.getWorldX());
            superLiving.setPrevWorldY(superLiving.getWorldY());
        }
    }

    /**
     * überprüft, ob zwischen dem Spieler und dem SuperEntity ein solides Tile ist
     * benutzt dafür den Bresenham-Algorithmus
     *
     * @param superLiving1 Spieler
     * @param superLiving2 SuperEntity
     * @return true, wenn kein solides Tile zwischen dem Spieler und dem SuperEntity ist
     */
    public static boolean notSolid(SuperLivingEntity superLiving1, SuperLivingEntity superLiving2) {
        int x1 = (int) ((superLiving1.getWorldX() + superLiving1.getHitBoxOffsetX() + superLiving1.getHitBox().width / 2) / SCALE);
        int y1 = (int) ((superLiving1.getWorldY() + superLiving1.getHitBoxOffsetY() + superLiving1.getHitBox().height / 2) / SCALE);
        int x2 = (int) ((superLiving2.getWorldX() + superLiving2.getHitBoxOffsetX() + superLiving2.getHitBox().width / 2) / SCALE);
        int y2 = (int) ((superLiving2.getWorldY() + superLiving2.getHitBoxOffsetY() + superLiving2.getHitBox().height / 2) / SCALE);

        int dx = Math.abs(x2 - x1); // Differenz der x-Koordinaten
        int dy = Math.abs(y2 - y1); // Differenz der y-Koordinaten
        int sx = x1 < x2 ? 1 : -1; // Vorzeichen der x-Koordinate
        int sy = y1 < y2 ? 1 : -1; // Vorzeichen der y-Koordinate
        int err = (dx > dy ? dx : -dy) / 2, e2; // Initialisierung des Fehlers

        while (true) {
            if (getLvlWall()[x1][y1] == 0) {
                return false; // Ein solides Tile wurde gefunden
            }
            if (x1 == x2 && y1 == y2) {
                break; // Ziel erreicht
            }

            e2 = err; // Fehler merken

            // Fehler aktualisieren
            if (e2 > -dx) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dy) {
                err += dx;
                y1 += sy;
            }
        }
        return true; // Kein solides Tile gefunden
    }

    /**
     * aktualisiert den Fortschritt des Spieles
     */
    private void updateProgress() {
        updateCheckPoints();
        updateEnemies();
        updateChests();
        checkLevel();
    }

    /**
     * aktualisiert die Checkpoints
     */
    private void updateCheckPoints() {
        CheckPoint[] checkPoints = getEntityHandler().getCheckPoints();
        for (int i = 0; i < checkPoints.length; i++) {
            if (checkPoints[i].isActive()) {
                getCurrentLevel().setReachedCheckPoints(i);
            }
        }
    }

    /**
     * aktualisiert die Gegner
     */
    private void updateEnemies() {
        Enemy[] enemies = getEntityHandler().getEnemies();
        for (int i = 0; i < enemies.length; i++) {
            if (!enemies[i].isAlive()) {
                getCurrentLevel().setEnemiesKilled(i);
            }
        }
    }

    /**
     * aktualisiert die Truhen
     */
    private void updateChests() {
        Chest[] chests = getEntityHandler().getChests();
        for (int i = 0; i < chests.length; i++) {
            if (chests[i].isActive()) {
                getCurrentLevel().setReachedChests(i);
            }
        }
    }

    /**
     * überprüft, ob das Level gewechselt werden soll
     */
    public void checkLevel() {
        if (!getEntityHandler().getPlayer().isAlive() && System.currentTimeMillis() - getEntityHandler().getPlayer().getTimeSinceDeath() > 5000) {
            setGameState(GAME_OVER);
        }
        if (Arrays.stream(getCurrentLevel().getReachedCheckPoints()).allMatch(i -> i == 1) && isJumpToNextLevel()) {
            if (getCurrentLevel() != LEVEL_CASTLE) {
                setInTransition(true);
                if (!isFadingIn()) {
                    nextLevel();
                    setTimeSinceLastLevelChange(System.currentTimeMillis());
                    setJumpToNextLevel(false);
                }
                if (getCurrentTutorialState() != NONE) {
                    setCurrentTutorialState(NONE);
                }
            } else {
                setGameState(WIN);
            }
        }
    }

    /**
     * wechselt in das nächste Level
     */
    private void nextLevel() {
        Level.nextLevel();
        setScreenX(SCREEN_X_DEFAULT);
        setScreenY(SCREEN_Y_DEFAULT);
        if (getPreviousLevel() != LEVEL_ZERO) {
            getEntityHandler().nextLevel();
        } else {
            getEntityHandler().reset();
        }
        initInGame();
    }

    /**
     * setzt den InGame-GameState zurück
     */
    public void reset() {
        Level.reset();
        getEntityHandler().reset();
        TutorialState.reset();
        setScreenX(SCREEN_X_DEFAULT);
        setScreenY(SCREEN_Y_DEFAULT);
        initInGame();
    }

    /**
     * setzt den Status eines Buttons
     *
     * @param i    Index des Buttons
     * @param bool Status des Buttons
     */
    public void updateButtonState(int i, boolean bool) {
        if (getButtons() != null) {
            getButtons()[i].setHover(bool);
            getButtons()[i].setPressed(bool);
        }
    }

    /**
     * führt den Übergang zwischen den Levels aus
     */
    public void updateTransition() {
        if (isFadingIn()) {
            setAlpha(getAlpha() + 0.01f); // Erhöht den Alpha-Wert
            if (getAlpha() >= 1.0f) {
                setAlpha(1.0f);
                setFadingIn(false); // Beginnt das Aufhellen
            }
        } else {
            setAlpha(getAlpha() - 0.01f); // Verringert den Alpha-Wert
            if (getAlpha() <= 0.0f) {
                setAlpha(0.0f);
                setInTransition(false); // Beendet den Übergang
                setFadingIn(true);
            }
        }
    }

    /**
     * führt die Aktionen aus, die beim Drücken der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            getEntityHandler().getPlayer().attemptAttack();
            updateButtonState(9, true);
        }
    }

    /**
     * führt die Aktionen aus, die beim Loslassen der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            updateButtonState(9, false);
        }
    }

    /**
     * führt die Aktionen aus, die beim Bewegen der Maus ausgeführt werden sollen
     *
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        getEntityHandler().getPlayer().getInventory().setMousePosition(e.getPoint());
    }

    /**
     * führt die Aktionen aus, die beim Drücken einer Taste ausgeführt werden sollen
     *
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        if (getEntityHandler().getPlayer().isAlive() && !isInTransition()) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                getEntityHandler().getPlayer().setUp(true);
                updateButtonState(0, true);
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                getEntityHandler().getPlayer().setLeft(true);
                updateButtonState(1, true);
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                getEntityHandler().getPlayer().setDown(true);
                updateButtonState(2, true);
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                getEntityHandler().getPlayer().setRight(true);
                updateButtonState(3, true);
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                GameController.getInstance().getSfxClips().values().forEach(DataLine::stop);
                if (Arrays.stream(getEntityHandler().getLuminas()).anyMatch(SuperEntity::isActive)) {
                    GameController.getInstance().getMusicClips().get("Lumina").stop();
                }

                setGameState(PAUSE);
                getEntityHandler().getPlayer().resetMovement();
                getEntityHandler().getPlayer().getInventory().setShowInventory(false);
                updateButtonState(4, true);
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                getEntityHandler().getPlayer().setSpeed(getEntityHandler().getPlayer().getRunSpeed());
                getEntityHandler().getPlayer().setAniSpeed(12);
                updateButtonState(6, true);
            }
            if (e.getKeyCode() == KeyEvent.VK_TAB) {
                getEntityHandler().getPlayer().getInventory().setShowInventory(true);
                updateButtonState(7, true);
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (getCurrentLevel() != LEVEL_THREE && getCurrentLevel() != LEVEL_CASTLE) {
                    setJumpToNextLevel(true);
                }
                updateButtonState(8, true);
            }
            if (e.getKeyCode() == KeyEvent.VK_PLUS) {
                updateButtonState(5, true);
            }
        }
    }

    /**
     * führt die Aktionen aus, die beim Loslassen einer Taste ausgeführt werden sollen
     *
     * @param e KeyEvent
     */
    public void keyReleased(KeyEvent e) {
        if (getEntityHandler().getPlayer().isAlive() && !isInTransition()) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                getEntityHandler().getPlayer().setUp(false);
                updateButtonState(0, false);
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                getEntityHandler().getPlayer().setLeft(false);
                updateButtonState(1, false);
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                getEntityHandler().getPlayer().setDown(false);
                updateButtonState(2, false);
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                getEntityHandler().getPlayer().setRight(false);
                updateButtonState(3, false);
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                updateButtonState(4, false);
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                getEntityHandler().getPlayer().setSpeed(getEntityHandler().getPlayer().getDefaultSpeed());
                getEntityHandler().getPlayer().setAniSpeed(16);
                updateButtonState(6, false);
            }
            if (e.getKeyCode() == KeyEvent.VK_TAB) {
                getEntityHandler().getPlayer().getInventory().setShowInventory(false);
                updateButtonState(7, false);
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                setJumpToNextLevel(false);
                updateButtonState(8, false);
            }
            if (getCurrentLevel() == LEVEL_ZERO) {
                if (e.getKeyCode() == KeyEvent.VK_PLUS) {
                    for (TutorialState tutorialState : TutorialState.values()) {
                        tutorialState.setDone(1);
                    }
                    setCurrentTutorialState(NONE);
                    LEVEL_ZERO.setReachedCheckPoints(0);
                    LEVEL_ZERO.setReachedCheckPoints(1);
                    LEVEL_ZERO.setReachedCheckPoints(2);
                    setJumpToNextLevel(true);
                    updateButtonState(5, false);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    setCurrentTutorialState(NONE);
                }
            }
        }
    }


    // GETTER UND SETTER


    public static void setInstance(InGame instance) {
        InGame.instance = instance;
    }

    public boolean isJumpToNextLevel() {
        return jumpToNextLevel;
    }

    public void setJumpToNextLevel(boolean jumpToNextLevel) {
        this.jumpToNextLevel = jumpToNextLevel;
    }

    public void setEntityHandler(EntityHandler entityHandler) {
        this.entityHandler = entityHandler;
    }

    public void setArr(ArrayList<SuperEntity> arr) {
        this.arr = arr;
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }

    public ArrayList<SuperEntity> getArr() {
        return arr;
    }

    public static int getScreenX() {
        return screenX;
    }

    public static int getScreenY() {
        return screenY;
    }

    public static void setScreenX(int screenX) {
        InGame.screenX = screenX;
    }

    public static void setScreenY(int screenY) {
        InGame.screenY = screenY;
    }

    public long getTimeSinceLastLevelChange() {
        return timeSinceLastLevelChange;
    }

    public void setTimeSinceLastLevelChange(long timeSinceLastLevelChange) {
        this.timeSinceLastLevelChange = timeSinceLastLevelChange;
    }

    public DefaultButton[] getButtons() {
        return buttons;
    }

    public void setButtons(DefaultButton[] buttons) {
        this.buttons = buttons;
    }

    public static int[][] getLvlWall() {
        return lvlWall;
    }

    public static void setLvlWall(int[][] lvlWall) {
        InGame.lvlWall = lvlWall;
    }

    public static boolean isCollision() {
        return collision;
    }

    public static void setCollision(boolean collision) {
        InGame.collision = collision;
    }

    public boolean isInTransition() {
        return inTransition;
    }

    public void setInTransition(boolean inTransition) {
        this.inTransition = inTransition;
    }

    public boolean isFadingIn() {
        return fadingIn;
    }

    public void setFadingIn(boolean fadingIn) {
        this.fadingIn = fadingIn;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}