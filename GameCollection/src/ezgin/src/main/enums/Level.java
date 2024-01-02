package main.enums;

import utils.Load;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static utils.Constants.ImageConstants.*;

/**
 * Enum für die Level
 * speichert die Daten, sowie Fortschritte aller Level
 */
public enum Level {
    LEVEL_ZERO("Das Erwachen (Tutorial)", new int[3], new int[1], new int[1], Load.getLevelText()[0], BI_LEVEL_ZERO),
    LEVEL_ONE("Das Vermächtnis von Eternavia", new int[3], new int[2], new int[5], Load.getLevelText()[1], BI_LEVEL_ONE),
    LEVEL_TWO("Das Zeitalter der Schatten", new int[3], new int[3], new int[10], Load.getLevelText()[2], BI_LEVEL_TWO),
    LEVEL_THREE("Die Hoffnung der Celestari", new int[3], new int[2], new int[10], Load.getLevelText()[3], BI_LEVEL_THREE),
    LEVEL_CASTLE("Die Festung der KI", new int[0], new int[0], new int[30], Load.getLevelText()[3], BI_LEVEL_CASTLE);

    private static Level currentLevel = LEVEL_ZERO;
    private static Level previousLevel = LEVEL_ZERO;
    private String title;
    private int[] reachedCheckPoints;
    private int[] reachedChests;
    private int[] enemiesKilled;
    private String[][] stelaText;
    private BufferedImage[] mapFiles;

    Level(String title, int[] reachedCheckPoints, int[] reachedChests, int[] enemiesKilled, String[][] stelaText, BufferedImage[] mapFiles) {
        setTitle(title);
        setReachedCheckPoints(reachedCheckPoints);
        setReachedChests(reachedChests);
        setEnemiesKilled(enemiesKilled);
        setStelaText(stelaText);
        setMapFiles(mapFiles);
    }

    /**
     * wechselt zum nächsten Level und speichert das vorherige Level
     */
    public static void nextLevel() {
        switch (getCurrentLevel()) {
            case LEVEL_ZERO:
                setCurrentLevel(LEVEL_ONE);
                break;
            case LEVEL_ONE:
                setPreviousLevel(LEVEL_ONE);
                setCurrentLevel(LEVEL_TWO);
                break;
            case LEVEL_TWO:
                setPreviousLevel(LEVEL_TWO);
                setCurrentLevel(LEVEL_THREE);
                break;
            case LEVEL_THREE:
                setPreviousLevel(LEVEL_THREE);
                setCurrentLevel(LEVEL_CASTLE);
                break;
        }
    }

    /**
     * setzt alle Fortschritte zurück und wechselt zum Tutorial
     */
    public static void reset() {
        for (Level level : Level.values()) {
            Arrays.fill(level.getReachedCheckPoints(), 0);
            Arrays.fill(level.getReachedChests(), 0);
            Arrays.fill(level.getEnemiesKilled(), 0);
        }
        setCurrentLevel(LEVEL_ZERO);
        setPreviousLevel(LEVEL_ZERO);
    }


    // GETTER UND SETTER


    public static int getAllReachedCheckPoints() {
        return Arrays.stream(LEVEL_ONE.getReachedCheckPoints()).sum() + Arrays.stream(LEVEL_TWO.getReachedCheckPoints()).sum() + Arrays.stream(LEVEL_THREE.getReachedCheckPoints()).sum();
    }

    public static int getAllReachedChests() {
        return Arrays.stream(LEVEL_ONE.getReachedChests()).sum() + Arrays.stream(LEVEL_TWO.getReachedChests()).sum() + Arrays.stream(LEVEL_THREE.getReachedChests()).sum();
    }

    public static int getAllEnemiesKilled() {
        return Arrays.stream(LEVEL_ONE.getEnemiesKilled()).sum() + Arrays.stream(LEVEL_TWO.getEnemiesKilled()).sum() + Arrays.stream(LEVEL_THREE.getEnemiesKilled()).sum() + Arrays.stream(LEVEL_CASTLE.getEnemiesKilled()).sum();
    }

    public static int getCheckPointsSum() {
        return LEVEL_ONE.getReachedCheckPoints().length + LEVEL_TWO.getReachedCheckPoints().length + LEVEL_THREE.getReachedCheckPoints().length;
    }

    public static int getChestsSum() {
        return LEVEL_ONE.getReachedChests().length + LEVEL_TWO.getReachedChests().length + LEVEL_THREE.getReachedChests().length;
    }

    public static int getEnemiesSum() {
        return LEVEL_ONE.getEnemiesKilled().length + LEVEL_TWO.getEnemiesKilled().length + LEVEL_THREE.getEnemiesKilled().length + LEVEL_CASTLE.getEnemiesKilled().length;
    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }

    public BufferedImage[] getMapFiles() {
        return mapFiles;
    }

    public void setReachedCheckPoints(int i) {
        getReachedCheckPoints()[i] = 1;
    }

    public void setReachedChests(int i) {
        getReachedChests()[i] = 1;
    }

    public void setEnemiesKilled(int i) {
        getEnemiesKilled()[i] = 1;
    }

    public int[] getReachedCheckPoints() {
        return reachedCheckPoints;
    }

    public int[] getEnemiesKilled() {
        return enemiesKilled;
    }

    public static Level getPreviousLevel() {
        return previousLevel;
    }

    public String[][] getStelaText() {
        return stelaText;
    }

    public static void setCurrentLevel(Level currentLevel) {
        Level.currentLevel = currentLevel;
    }

    public static void setPreviousLevel(Level previousLevel) {
        Level.previousLevel = previousLevel;
    }

    public String getTitle() {
        return title;
    }

    public int[] getReachedChests() {
        return reachedChests;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReachedCheckPoints(int[] reachedCheckPoints) {
        this.reachedCheckPoints = reachedCheckPoints;
    }

    public void setReachedChests(int[] reachedChests) {
        this.reachedChests = reachedChests;
    }

    public void setEnemiesKilled(int[] enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    public void setStelaText(String[][] stelaText) {
        this.stelaText = stelaText;
    }

    public void setMapFiles(BufferedImage[] mapFiles) {
        this.mapFiles = mapFiles;
    }
}
