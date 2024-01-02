package jan.game.source.GameObj;
import java.awt.Point;

import javax.swing.JLabel;

import jan.game.source.Game_Controller;
import jan.game.source.Score;
import jan.game.source.Vector2;

public abstract class BaseObj extends Collider implements Runnable {
        
        private Behaves behavior;
        
        public final Transform transform;
        
        private JLabel text;
        
        private int score;
        
        /**
         * Konstruktor
         * @param position
         * @param behavior
         * @param collisionArea
         * @param text
         * @param score
         */
        public BaseObj(Vector2 position, Behaves behavior, Point collisionArea, JLabel text, int score) {
            
            super(collisionArea);
            this.text = text;
            this.score = score;
            transform = new Transform(position);
            this.behavior = behavior;
            Game_Controller.addRunnable(this);
        }

        //Run Methode von Runnable 
        @Override
        public final void run() {
            
            update();
            boolean collided = testForCollision(transform.getPosition(), true);
            text.setLocation((int)transform.getPosition().x, (int)transform.getPosition().y);

            
            if (collided) {
                
                Score.changeScoreBy(score);
                Game_Controller.removeObj(this);
                Game_Controller.removeRunnable(this);

            }
        }
        
        public abstract void update();
                
        
        public final void act() {
            
            behavior.act(this);
        }
        
        public final void interact() {
            
            behavior.act(this);
        }
        
        /**
         * Gibt das JLabel züruck
         * @return JLabel
         */
        public JLabel getLabel() {
            
            return text;
        }
        
        public void reset() {
            
            Game_Controller.removeRunnable(this);
        }
}
