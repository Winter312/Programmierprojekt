package CodeInvaders_Game.GameDisplay;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import CodeInvaders_Game.States.StateMachine;


public class Display extends Canvas implements Runnable {

    /**
     * Hier wird das Spiel ausgeführt
     * @param args
     */
    public static void main(String[] args){
        Display display = new Display();
        JFrame frame = new JFrame();
        frame.add(display);
        frame.pack();
        frame.setTitle("Code Invaders");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                running = false;
                frame.dispose();
            }
        });
        frame.setResizable(false);
        frame.setVisible(true);
        display.start();
        try {
            thread.join();
        } catch (InterruptedException e) {e.printStackTrace();}

    }
    private static boolean running = false;

    private static Thread thread;

    public synchronized void start(){
        if (running){
            return;

        }
        running =true;
        thread = new Thread(this);
        thread.start();


    }

    public synchronized void stop(){
        if (!running){
            return;
        }
        running = false;
        
        try {
            thread.join();
        } catch (InterruptedException e) {e.printStackTrace();}
    }
    public static int WIDTH = 800, HEIGHT = 600;
    public int fps;
    public static StateMachine state;
    /**
     * Display erstellt das Fenster mit Länge und Breite
     */
    public Display(){
        this.setSize(WIDTH, HEIGHT);
        this.setFocusable(true);
        state = new StateMachine(this);
        state.setState((byte) 0);
    }
    /**
     * Die GameLoop um auf einen Konstante Frame Per Second zu kommen damit das Spiel "flüssig" läuft
     */
    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        int frames = 0;

        this.createBufferStrategy(3);
        BufferStrategy bs = this.getBufferStrategy();

        while(running){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);
            frames++;
            if (System.currentTimeMillis() - timer > 1000){
                timer = timer + 1000;
                fps = frames;
                frames = 0;
                System.out.println("FPS: " + fps);
            }
            update(delta);
            draw(bs);


            try {
                Thread.sleep(((lastLoopTime - System.nanoTime()) + OPTIMAL_TIME) / 1000000);
            }catch (Exception e){}
        }
    }


    /**
     * Diese Methode ist verantwortlich für das Zeichnen der grafischen Komponenten auf dem Bildschirm.
     * @param bs
     */
    public void draw (BufferStrategy bs){
        do {
            do {
                Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                g.setColor(Color.BLACK);
                g.fillRect(0,0,WIDTH,HEIGHT);
                state.draw(g);
                g.dispose();
            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());
    }

    public void update(double delta){
        state.update(delta);
    }
}
