package main;

import main.entities.EntityHandler;
import main.entities.SuperEntity;
import main.gamestates.InGame;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.util.Arrays;
import java.util.HashMap;

import static main.enums.GameState.*;
import static main.enums.Level.LEVEL_CASTLE;
import static main.enums.Level.getCurrentLevel;
import static utils.Constants.GameConstants.*;

/**
 * GameController-Klasse
 * ist für die Steuerung des Spiels zuständig
 * Singleton-Klasse
 */
public class GameController implements Runnable {

    private static GameController instance; // Singleton-Instanz
    private Thread gameThread;

    // HashMaps für die Soundeffekte und die Musik
    private HashMap<String, Clip> sfxClips;
    private HashMap<String, Clip> musicClips;

    private boolean musicMuted, sfxMuted; // boolean für die Stummschaltung der Musik und der Soundeffekte

    private GameController() {
        setSfxClips(new HashMap<>());
        setMusicClips(new HashMap<>());
    }

    /**
     * gibt die Instanz der Klasse zurück
     *
     * @return Instanz der Klasse
     */
    public static GameController getInstance() {
        if (instance == null) {
            setInstance(new GameController());
        }
        return instance;
    }

    /**
     * aktualisiert das Spiel
     */
    public void update() {
        if (!isMusicMuted()) {
            setVolumeForMusic(0.5f);
        } else {
            setVolumeForMusic(0f);
        }
        if (!isSfxMuted()) {
            setVolumeForSfx(1f);
        } else {
            setVolumeForSfx(0f);
        }
        switch (getCurrentState()) {
            case MENU:
                if (getMusicClips().get("InGame").isRunning() || getMusicClips().get("Castle").isRunning()) {
                    getMusicClips().get("InGame").stop();
                    getMusicClips().get("InGame").setFramePosition(0);
                    getMusicClips().get("Castle").stop();
                    getMusicClips().get("Castle").setFramePosition(0);
                }
                if (!getMusicClips().get("Menu").isRunning()) {
                    getMusicClips().get("Menu").setFramePosition(0);
                    getMusicClips().get("Menu").loop(Clip.LOOP_CONTINUOUSLY);
                }
                break;
            case IN_GAME:
                if (getMusicClips().get("Menu").isRunning()) {
                    getMusicClips().get("Menu").stop();
                    if (getCurrentLevel() != LEVEL_CASTLE) {
                        getMusicClips().get("InGame").setFramePosition(0);
                    }
                }
                if (getCurrentLevel() == LEVEL_CASTLE) {
                    if (getMusicClips().get("InGame").isRunning()) {
                        getMusicClips().get("InGame").stop();
                    }
                    if (!getMusicClips().get("Castle").isRunning()) {
                        getMusicClips().get("Castle").setFramePosition(0);
                        getMusicClips().get("Castle").loop(Clip.LOOP_CONTINUOUSLY);
                    }
                }
                if (Arrays.stream(EntityHandler.getInstance().getLuminas()).anyMatch(SuperEntity::isActive) || getCurrentLevel() == LEVEL_CASTLE) {
                    getMusicClips().get("InGame").stop();
                } else if (Arrays.stream(EntityHandler.getInstance().getLuminas()).noneMatch(SuperEntity::isActive) && !getMusicClips().get("InGame").isRunning()) {
                    getMusicClips().get("InGame").loop(Clip.LOOP_CONTINUOUSLY);
                }
            case GAME_OVER:
                InGame.getInstance().update();
                break;
        }
    }

    /**
     * startet den Thread
     */
    public void startGameThread() {
        setGameThread(new Thread(this)); // erstellt einen neuen Thread
        getGameThread().start(); // startet den Thread
    }

    /**
     * Hier wird die GameLoop ausgeführt, welche für eine flüssige und konsistente Spielerfahrung sorgt,
     * indem sie die Bildwiederholrate stabilisiert und gleichzeitig
     * Spiellogik, Benutzereingaben und Grafikdarstellung effizient koordiniert.
     * Wird ausgeführt, wenn der Thread gestartet wird.
     */
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;

        long previousTime = System.nanoTime();

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (getGameThread() != null) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                deltaU--;
            }
            if (deltaF >= 1) {
                GamePanel.getInstance().repaint();
                frames++;
                deltaF--;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                GamePanel.getInstance().setFrames(frames);
                frames = 0;
            }
        }
    }


    // GETTER UND SETTER


    public void setVolumeForSfx(float volume) {
        setVolumeForClips(getSfxClips(), volume);
    }

    public void setVolumeForMusic(float volume) {
        setVolumeForClips(getMusicClips(), volume);
    }

    private void setVolumeForClips(HashMap<String, Clip> clips, float volume) {
        for (Clip clip : clips.values()) {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }
        }
    }

    public static void setInstance(GameController instance) {
        GameController.instance = instance;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }

    public HashMap<String, Clip> getSfxClips() {
        return sfxClips;
    }

    public void setSfxClips(HashMap<String, Clip> sfxClips) {
        this.sfxClips = sfxClips;
    }

    public HashMap<String, Clip> getMusicClips() {
        return musicClips;
    }

    public void setMusicClips(HashMap<String, Clip> musicClips) {
        this.musicClips = musicClips;
    }

    public boolean isMusicMuted() {
        return musicMuted;
    }

    public void setMusicMuted(boolean musicMuted) {
        this.musicMuted = musicMuted;
    }

    public boolean isSfxMuted() {
        return sfxMuted;
    }

    public void setSfxMuted(boolean sfxMuted) {
        this.sfxMuted = sfxMuted;
    }
}
