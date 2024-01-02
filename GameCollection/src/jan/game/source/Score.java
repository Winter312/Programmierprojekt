package jan.game.source;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Score {

    private static int score = 0;
    
    private static List<Consumer<Integer>> consumers = new ArrayList<>();

    
    /**
     * Ändert den Score um eine Konstante und informiert alle Consumer über diese änderung
     */
    public static void changeScoreBy(int value) {
        
        score += value;
        consumers.forEach(c -> c.accept(score));;
    }
    
    /**
     * Setzt den Score und informiert alle Consumer über diese änderung
     */
    public static void setScore(int value) {
        
        score = value;
        consumers.forEach(c -> c.accept(score));;
    }
    
    /**
     * Gibt den Score züruck
     * @return int
     */
    public static int getScore() {
     
        return score;
    }
    
    /**
     * Fügt ein TimerConsumer hinzu
     */
    public static void addTimerConsumer(Consumer<Integer> consumer) {
        
        consumers.add(consumer);
    }
    
    /**
     * Entfernt ein TimerConsumer hinzu
     */
    public static void removeTimerConsumer(Consumer<Integer> consumer) {
        
        consumers.remove(consumer);
    }
    
}
