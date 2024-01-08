package jan.game.source;

import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


import jan.game.source.Audio.AudioClip;
import jan.game.source.Audio.AudioManager;
import jan.game.source.GUI.GUI_Controller;
import jan.game.source.GameObj.BaseObj;

public class Game_Controller implements Runnable {

    private static GUI_Controller mGUI_C_Ref;
    private static Game_Controller mGame_C_Ref;
    public Thread gameThread;
    private boolean mRunning = true;
    public static final int mTickrate = 1000 / 60; // Tickrate in 60 pro Sekunde

    private static List<Runnable> runnables = new ArrayList<>();

    public long deltaTime;

    private static Difficulty difficulty = Difficulty.NORMAL;
    public static Consumer<Difficulty> onDifficultyChange;

    public static Point windowSize = new Point(900, 700);

    /**
     * Gameloop für Spielelogik
     */
    @Override
    public void run() {

        long startTime = System.currentTimeMillis(), endTime;

        while (mRunning) {

            endTime = startTime;
            startTime = System.currentTimeMillis();

            //
            deltaTime = startTime - endTime;

            if (state != GAMESTATE.PAUSED) {

                update();
            }
            if (mTickrate - deltaTime > 0) {

                try {
                    Thread.sleep(mTickrate - deltaTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        try {
            mGUI_C_Ref.quit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fügt ein Runnable zum Gameloop hinzu
     */
    public static void addRunnable(Runnable runnable) {

        runnables.add(runnable);
    }

    /**
     * Entfernt ein Runnable vom Gameloop
     */
    public static void removeRunnable(Runnable runnable) {

        runnables.remove(runnable);
    }

    /**
     * Wird jeden frame aufgerufen
     */
    public void update() {

        List<Runnable> runnables2 = List.copyOf(runnables);
        for (Runnable runnable : runnables2) {

            runnable.run();
        }
    }

    // in welchen Zuständen kann das Programm sein
    public enum GAMESTATE {
        START, MENU, TUTORIAL, SETTINGS, RUNNING, PAUSED, GAMEOVER
    }

    public GAMESTATE state; // aktueller Zustand

    // welche Aktionen können auftreten
    public enum ACTION {
        STARTAPP, QUIT, STARTGAME, PAUSE_TOGGLE, MENU, TUTORIAL, SETTINGS, RESTART, END
    }

    /**
     * Konstruktor
     * 
     * @throws InterruptedException
     */
    public Game_Controller() {

        mGame_C_Ref = this;

        AudioClip ac = new AudioClip();

        ac.setFile("music/music.wav");

        AudioManager.addMusicClip(ac);

        ac.play();

        mGUI_C_Ref = new GUI_Controller(this);

        setDifficulty(Difficulty.NORMAL);

        // wechseln zum Menü
        fireEvent(ACTION.STARTAPP);

        // starten des Gameloop
        gameThread = new Thread(this);
        gameThread.start();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    Spawner spawner;
    Timer timer;

    /**
     * der aktuelle Zustand des Spieles wird geändert ruft changeWindowConfig() auf
     * 
     * @param newState
     */
    public void changeState(GAMESTATE newState) {

        state = newState;

        switch (state) {

        case GAMEOVER:

            removeRunnable(spawner);
            removeRunnable(timer);

            break;

        default:
            break;

        }

        mGUI_C_Ref.changeWindowConfig(newState);

    }

    /**
     * Gibt den Game_Controller züruck
     * 
     * @return Game_Controller
     */
    public static Game_Controller getGame_C_Ref() {
        return mGame_C_Ref;
    }

    /**
     * Ein Event wird ausgelöst
     * 
     * @param a
     */
    public void fireEvent(ACTION a) {
        switch (a) {
        case STARTAPP -> changeState(GAMESTATE.START);
        case STARTGAME -> {

            changeState(GAMESTATE.RUNNING);
            spawner = new Spawner(difficulty);
            Score.setScore(0);
            timer = new Timer(150);
            timer.start();
        }
        case QUIT -> mRunning = false;
        case PAUSE_TOGGLE -> {

            if (state == GAMESTATE.PAUSED) {

                changeState(GAMESTATE.RUNNING);

            } else if (state == GAMESTATE.RUNNING) {

                changeState(GAMESTATE.PAUSED);
            }
        }
        case MENU -> changeState(GAMESTATE.MENU);
        case TUTORIAL -> changeState(GAMESTATE.TUTORIAL);
        case SETTINGS -> changeState(GAMESTATE.SETTINGS);
        case RESTART -> {
            mGUI_C_Ref.clearPaneObjects();
            timer.reset();
            Score.setScore(0);
            changeState(GAMESTATE.RUNNING);
        }
        case END -> changeState(GAMESTATE.GAMEOVER);
        }
    }

    /**
     * Gibt die Bildschirmposition des Fensters züruck
     * 
     * @return Point
     * @throws IllegalComponentStateException
     */
    public static Point getLocationOnScreen() {

        return mGUI_C_Ref.getWindow().getLocationOnScreen();
    }

    /**
     * Setzt den Schwierigkeitsgrad
     * 
     * @param newDifficulty
     */
    public static void setDifficulty(Difficulty newDifficulty) {

        difficulty = newDifficulty;
        onDifficultyChange.accept(newDifficulty);
    }

    /**
     * Fügt ein BaseObj zur Pane hinzu
     */
    public static void addObj(BaseObj obj) {

        mGUI_C_Ref.addToPane(obj);
    }

    /**
     * Entfernt ein BaseObj von der Pane
     */
    public static void removeObj(BaseObj obj) {

        mGUI_C_Ref.removeFromPane(obj);
    }

    /**
     * Entfernt alle BaseObj aus der Pane
     */
    public static void clearObjects() {

        mGUI_C_Ref.clearPaneObjects();
    }
}
