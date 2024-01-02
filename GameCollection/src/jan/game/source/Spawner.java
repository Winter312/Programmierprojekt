package jan.game.source;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.Random;

import javax.swing.JLabel;

import jan.game.source.GUI.GameText;
import jan.game.source.GameObj.GameObj;
import jan.game.source.GameObj.MoveToDirectionBehavior;

public class Spawner implements Runnable {

    Difficulty difficulty;
    
    double longestWait, shortestWait;
    double time;
    double waitTime;
    double startTime;
    
    
    /**
     * Konstruktor
     * @param difficulty
     */
    public Spawner(Difficulty difficulty) {
        
        this.difficulty = difficulty;
        
        switch (difficulty) {
        
        
        case EASY: 
            
            longestWait = 700;
            shortestWait = 500;
            
            break;
            
        case NORMAL: 
            
            longestWait = 600;
            shortestWait = 300;
            
            break;
        
            
        case HARD: 
            
            longestWait = 400;
            shortestWait = 100;
            
            break;
  
        }
        
        time = 0;
        startTime = System.currentTimeMillis();
        waitTime = new Random().nextDouble(shortestWait, longestWait);
        Game_Controller.addRunnable(this);

    }
    
    //Run Methode von Runnable 
    @Override
    public void run() {
        
        if (time >= waitTime) {
            
            startTime = System.currentTimeMillis();
            time = 0;
            waitTime = new Random().nextDouble(shortestWait, longestWait);
            spawn();
            return;
        }

        time = System.currentTimeMillis() - startTime;
    }
    
    /**
     * Kreiert ein neues GameObj 
     */
    public static void spawn() {
        
        Vector2 dir = new Vector2();
        
        Vector2 startPos = new Vector2();
        
        Random random = new Random();
        dir.x = random .nextInt(0, Game_Controller.windowSize.x+1);
        dir.y = random .nextInt(0, Game_Controller.windowSize.y+1);

        
        int i = random.nextInt(0, 2);
        int j = random.nextInt(0, 2);
        
        double random1 = Math.random()*6;
        
        Object text = null;
        
        int score = -10;
                        
        if (random1 < 3) {
            
            text = new GameText(ObjectCollection.getText(), startPos.toPoint(), new Font("Serif", Font.PLAIN, 18), Color.white, Color.red);
         }
        else if (random1 < 5) {
            
            text = new GameText(ObjectCollection.getImage(), startPos.toPoint(), Color.white, Color.red);
        }
        else if (random1 < 6) {
            
            text = new Coin(startPos.toPoint());
            score = 10;
        }
        
        GameObj obj = new GameObj(startPos, new MoveToDirectionBehavior(dir, random.nextDouble(0.1, 2.8)), new Point(100, 50), (JLabel)text, score);
        Game_Controller.addObj(obj);

        
        Dimension dim = ((JLabel)text).getSize();

        if (i == 0) {
            
            startPos.y = Game_Controller.windowSize.y * j;
            startPos.y += (j * 2-1) * dim.getHeight()/2;
            
            startPos.x = random.nextDouble(-dim.getWidth(),Game_Controller.windowSize.x + dim.getWidth());
            
        } else {
            
            startPos.x = Game_Controller.windowSize.x * j;
            startPos.x += (j * 2-1) * dim.getWidth()/2;
            
            startPos.y = random.nextDouble(-dim.getHeight(), Game_Controller.windowSize.y + dim.getHeight());
            
        }
        
        dir.x = Vector2.sub(dir, startPos).x;
        dir.y = Vector2.sub(dir, startPos).y;

        
        obj.transform.setPosition(startPos);
        obj.setCollisionArea(new Point((int)(dim.width*0.9), (int)(dim.height*0.9)));
        ((JLabel)text).setLocation(startPos.toPoint());
               
    }
   
}
