package PixelPinesProtection.main;

import PixelPinesProtection.scenes.*;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;

/**
 * Hauptklasse des Spiels, die das Fenster und den Spielablauf steuert.
 * Sie erbt von JFrame und implementiert Runnable, um das Spiel in einem eigenen Thread laufen zu lassen.
 */
public class Game extends JFrame implements Runnable {
    private GameScreen gameScreen;
    private Thread gameThread;

    // Ziel-Frames pro Sekunde und Updates pro Sekunde
    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;

    // Klassen für verschiedene Spielzustände
    private Render render;
    private Menu menu;
    private Playing playing;
    private Help help;
    private GameOver gameover;
    private GameWon gamewon;

    /**
     * Konstruktor der Game-Klasse. Initialisiert die Klassen und das Spiel-Fenster.
     */
    public Game() {
        initClasses();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                Game.running = false;
                Game.instance.dispose();
                playing.getMusic().stop();
            }
        });
        setResizable(false);
        setTitle("Pixel Pines Defense!");
        add(gameScreen);
        pack();
        setVisible(true);
    }

    /**
     * Initialisiert alle notwendigen Klassen für das Spiel.
     */
    private void initClasses() {
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        help = new Help(this);
        gameover = new GameOver(this);
        gamewon = new GameWon(this);
    }

    /**
     * Startet den Spiel-Thread.
     */
    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Aktualisiert das Spiel basierend auf dem aktuellen Spielzustand.
     */
    private void updateGame() {
        switch(GameStates.gameState) {
            case PLAYING:
                if (!playing.getMusic().isRunning()) {
                    playing.getMusic().start();
                    playing.getMusic().loop(Clip.LOOP_CONTINUOUSLY);
                }
                playing.update();
                break;
            default:
                playing.getMusic().stop();
                if (GameStates.gameState == GameStates.GAME_WON || GameStates.gameState == GameStates.GAME_OVER) {
                    playing.getMusic().setFramePosition(0);
                }
                break;
        }
    }
    
    public static Game instance;

    public static void main(String[] args) {
        instance = new Game();
        instance.gameScreen.initInputs();
        instance.start();
    }

    public static boolean running;
    
    /**
     * Die Haupt-Loop des Spiels, die für das Rendern und Aktualisieren des Spiels verantwortlich ist.
     */
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        long now;

        while (running) {
            now = System.nanoTime();

            // Rendern
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }

            // Aktualisieren
            if (now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            // FPS und UPS überwachen
            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    // Getter für verschiedene Spielzustände
    public Render getRender() {
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Help getHelp() {
        return help;
    }

    public GameOver getGameover() {
        return gameover;
    }

    public GameWon getGamewon() {
        return gamewon;
    }
}
