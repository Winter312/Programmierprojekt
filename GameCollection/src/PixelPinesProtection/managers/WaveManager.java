package managers;

import scenes.Playing;
import events.Wave;

import java.util.ArrayList;
import java.util.Arrays;

import static main.GameStates.GAME_WON;
import static main.GameStates.SetGameState;

/**
 * Verwaltet die verschiedenen Wellen von Feinden in einem Level.
 */
public class WaveManager {
    Playing playing;
    private ArrayList<Wave> waves = new ArrayList<Wave>();
    private int enemySpawnTickLimit = 40;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int enemyIndex, waveIndex;
    private int waveTickLimit = 60* 5; // Zeitlimit für jede Welle in Ticks
    private int waveTick = 0;
    private int currentLevel = 1;
    private int maxLevel = 3;
    private boolean waveStartTimer, waveTickTimerOver;

    /**
     * Konstruktor für WaveManager.
     *
     * @param playing Referenz auf das Playing-Objekt.
     */
    public WaveManager(Playing playing) {
        this.playing = playing;
        createWavesForLevel(currentLevel);
    }

    /**
     * Aktualisiert den Status der Wellen und kontrolliert den Übergang zum nächsten Level.
     */
    public void update() {
        // Zählt den Timer für das Spawnen der Feinde hoch.
        if (enemySpawnTick < enemySpawnTickLimit) {
            enemySpawnTick++;
        }

        // Zählt den Wellen-Timer hoch und überprüft das Ende der Welle.
        if (waveStartTimer) {
            waveTick++;
            if (waveTick >= waveTickLimit) {
                waveTickTimerOver = true;
            }
        }

        // Überprüft, ob ein Übergang zum nächsten Level erforderlich ist.
        if (isTimeToNextLevel()) {
            nextLevel();
        }

        // Überprüft, ob das Spiel gewonnen wurde.
        if (currentLevel == maxLevel && !isThereMoreEnemiesInWave() && !isThereMoreWaves()) {
            if (playing.getEnemyManager().getAmountOfAliveEnemies() == 0) {
                currentLevel = 1;
                SetGameState(GAME_WON);
            }        }
    }

    /**
     * Überprüft, ob es Zeit für den nächsten Level ist.
     *
     * @return true, wenn kein Feind mehr in der Welle und keine weiteren Wellen vorhanden sind.
     */
    private boolean isTimeToNextLevel() {
        return !isThereMoreEnemiesInWave() && !isThereMoreWaves() && currentLevel < getMaxLevel();
    }

    public void resetEnemyIndex() {
        enemyIndex = 0;
    }

    /**
     * Erhöht den Index der aktuellen Welle und setzt die Wellen-Timer zurück.
     */
    public void increaseWaveIndex() {
        waveIndex++;
        waveTick = 0;
        waveTickTimerOver = false;
        waveStartTimer = false;
    }

    /**
     * Überprüft, ob die aktuelle Welle beendet ist.
     *
     * @return true, wenn die Welle beendet ist.
     */
    public boolean isWaveTimeOver() {
        return waveTickTimerOver;
    }

    public void startWaveTimer() {
        waveStartTimer = true;
    }

    /**
     * Gibt den Typ des nächsten zu spawnenden Feindes zurück.
     *
     * @return den Typ des nächsten Feindes.
     */
    public int getNextEnemy() {
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    /**
     * Erstellung der Wellen für verschiedene Levels
     * @param level
     */
    private void createWavesForLevel(int level) {
        waves.clear();
        switch (level) {
            case 1:
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0))));
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1))));
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1))));
                break;
            case 2:
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2))));
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2))));
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2))));
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2,2,2,3,3,3,2,2,2,2,2,2,3,3,3,2,2,2,2,2,2,3,3,3,2,2,2,2))));
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2,2,2,2,3,3,3,3,3,3,3,3,3,3,3))));
                break;
            case 3:
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3))));
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3))));
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3))));
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3))));
                waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3,3,3,3,3,3,4))));
                break;
        }
    }

    /**
     * Wechselt zum nächsten Level und setzt die entsprechenden Wellen auf.
     */
    public void nextLevel() {
        if (currentLevel < getMaxLevel()) {
            currentLevel++;
            createWavesForLevel(currentLevel);
        }       resetForNewLevel();
    }

    private void resetForNewLevel() {
        enemyIndex = 0;
        waveIndex = 0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        waveTick = 0;
        enemySpawnTick = enemySpawnTickLimit;
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewEnemy() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }

    public boolean isThereMoreEnemiesInWave() {
        return enemyIndex < waves.get(waveIndex).getEnemyList().size();
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public float getTimeLeft() {
        float TicksLeft = waveTickLimit - waveTick;
        return TicksLeft / 60.0f;
    }

    public boolean isTimerStarted() {
        return waveStartTimer;
    }

    public int getWaveIndex() {
        return waveIndex;
    }

    public void reset() {
        waves.clear();
        createWavesForLevel(currentLevel);
        enemyIndex = 0;
        waveIndex = 0;
        currentLevel = 1;


        waveStartTimer = false;
        waveTickTimerOver = false;
        waveTick = 0;
        enemySpawnTick = enemySpawnTickLimit;
    }
    public int getMaxLevel() {
        return maxLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}
