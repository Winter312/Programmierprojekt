package CodeInvaders_Game.GameScreen;
import CodeInvaders_Game.GameDisplay.Display;
import CodeInvaders_Game.Levels.FirstLevel;
import CodeInvaders_Game.States.StateMachine;
import CodeInvaders_Game.States.SupStatMachine;
import java.awt.*;
import CodeInvaders_Game.Steering.EnemyBulletSteering;


public class GScreen extends SupStatMachine {
    private boolean noNextLevel = false;
    private boolean lastLevel = false;
    private Player player;
    private NormalBlocks normalBlocks;
    private FirstLevel level;
    private EnemyBulletSteering bulletSteering;
    public static int score = 0;
    private Font gameScreen = new Font("Arial", Font.PLAIN, 48);

    /**
     * Konstruktor der Klasse GScreen.
     * Diese Methode initialisiert eine neue Instanz der GScreen-Klasse.
     * @param stateMachine
     */
    public GScreen(StateMachine stateMachine){
        super(stateMachine);
        bulletSteering = new EnemyBulletSteering();
        normalBlocks = new NormalBlocks();
        player = new Player(375, 525,50 ,50, normalBlocks);
        level = new FirstLevel(player,bulletSteering, normalBlocks);
    }

    /**
     * Update klasse um Spiel aktuell zu halten
     * @param delta
     */
    @Override
    public void update(double delta) {
        player.update(delta);
        level.update(delta, normalBlocks);
        }

    /**
     * Zeichnet Score und Leben sowie den Rest des Spiels
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.drawString("Punkte: " + score,20,20);
        g.setColor(Color.white);
        g.drawString("Leben: " + player.getHealth(),20,40);
        player.draw(g);
        normalBlocks.draw(g);
        level.draw(g);
    // Überprüft ob das Spiel vorbei ist, wenn ja dann wird ein Game Over Screen eingeführt und spiel wird zurückgesetzt
        if (level.IsGameOver()){
            score = 0;
            level.SetPlayerDead();
            g.setColor(Color.red);
            g.setFont(gameScreen);
            String gameOver = "GAME OVER!";
            int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
            g.drawString(gameOver, (Display.WIDTH /10) + (gameOverWidth/2), Display.HEIGHT/2);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Dürcke R zum Neustarten", (Display.WIDTH /9) + (gameOverWidth/2), 350);
            g.drawString("Drücke M fürs Menü",(Display.WIDTH /7) + (gameOverWidth/2), 400);
            if (player.GetrPressed() && !player.isAlive()){
                level.reset();
            }
            if (player.GetMPressed() && !player.isAlive() ){
                level.reset();

                getStateMachine().setState((byte)0);
            }
        }
        // Das selbe wie Game Over nur hier hat man das Level geschafft und kommt ins nächste
        if (level.IsComplete()){
            g.setColor(Color.green);
            g.setFont(gameScreen);
            String gameComplete = "LEVEL COMPLETE!";
            int gameOverWidth = g.getFontMetrics().stringWidth(gameComplete);
            g.drawString(gameComplete, (Display.WIDTH /14) + (gameOverWidth/3), 200);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Dürcke R zum Neustarten", (Display.WIDTH /12) + (gameOverWidth/2), 250);
            g.drawString("Drücke M fürs Menü",(Display.WIDTH /10) + (gameOverWidth/2), 300);
            if (!noNextLevel) {
                g.drawString("Drücke N fürs nächste Level", (Display.WIDTH / 14) + (gameOverWidth / 2), 350);
            }
            if (player.GetrPressed()){
                level.reset();
                score = 0;
            }

            if (player.GetMPressed()){
                level.reset();
                score = 0;
                getStateMachine().setState((byte)0);
            }
            if (player.GetNPressed() && !lastLevel && !noNextLevel){
                lastLevel = true;
                level.secondReset();

            }
            if (player.GetNPressed() && lastLevel && !noNextLevel){
                noNextLevel = true;
                level.lastReset();
            }
        }
    }

    /**
     * Canvas für die Keys
     * @param canvas
     */
    @Override
    public void init(Canvas canvas) {
        canvas.addKeyListener(player); // In dieser Zeile wird dem Canvas ein KeyListener hinzugefügt
    }
}
