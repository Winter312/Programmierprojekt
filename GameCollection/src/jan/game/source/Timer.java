package jan.game.source;
import java.util.function.Consumer;

public class Timer implements Runnable {

    private final int startTime;
    
    private double currentTime;
    
    private boolean started;
    
    private boolean paused;
        
    public static Consumer<String> onTimerChange;
    
    /**
     * Konstruktor
     * @param startTime
     */
    public Timer(int startTime) {
        
        this.startTime = startTime+1;
        currentTime = this.startTime;
        started = false;
        paused = false;
        Game_Controller.addRunnable(this);
    }
    
    /**
     * Pausiert den Timer
     */
    public void pause() {
        
        paused = true;
    }
    
    
    /**
     * Lässt den Timer weiterlaufen
     */
    public void resume() {
        
        paused = false;
    }
    
    /**
     * Startet den Timer
     */
    public void start() {
        
        started = true;
    }

    //Run Methode von Runnable 
    @Override
    public void run() {

        if (!started && paused) {
            
            return;
        }
        
        if (currentTime < 0) {
            
            Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.END);
            return;
        }
        
        currentTime -= Game_Controller.getGame_C_Ref().deltaTime/1000.0;
        
        onTimerChange.accept(timeToString((int)currentTime));
        
    }
    
    /**
     * Konvertier die anzhal an Sekunden in ein String mit Minuten und Sekunden
     * @return String
     */
    public String timeToString(int seconds) {
        
        int s = seconds % 60;
        int min = seconds / 60;
        
        String timeText = min + ":";
    
        if (s < 10) {
            timeText += "0" + s;
        } else {
            timeText += s;
        }
        

        return timeText;
    }
    
    /**
     * Gibt die verbleibende zeit zurück
     * @return double
     */
    public double getTime() {
        
        return currentTime;
    }
    
    /**
     * Setzt den Timer auf den Startwert züruck
     */
    public void reset() {
        
        currentTime = startTime;
    }
    
}
