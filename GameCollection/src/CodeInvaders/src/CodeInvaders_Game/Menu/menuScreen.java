package CodeInvaders_Game.Menu;

import CodeInvaders_Game.GameDisplay.Display;
import CodeInvaders_Game.States.StateMachine;
import CodeInvaders_Game.States.SupStatMachine;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class menuScreen extends SupStatMachine implements KeyListener {

    private Font titleFont = new Font("Arial", Font.PLAIN, 64);
    private Font startFont = new Font("Arial", Font.PLAIN, 64);
    private String title = "Code Invaders";
    private String start = "Drück Enter: ";

    /**
     * Initialisiert die Menübildschirm-Klasse mit der übergebenen StateMachine.
     * @param stateMachine
     */
    public menuScreen(StateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    public void update(double delta) {

    }

    /**
     * Zeichnet den Titel und den Starttext des Spiels auf den Bildschirm. Verwendet dabei verschiedene Farben und Schriftarten.
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {
        g.setFont(titleFont);
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.setColor(Color.yellow);
        g.drawString(title, (Display.WIDTH /2 - titleWidth/2) -2, Display.HEIGHT/2 - 123);
        g.setColor(Color.GREEN);
        g.drawString(title, Display.WIDTH /2 - titleWidth/2, Display.HEIGHT/2 - 125);

        g.setFont(startFont);
        g.setColor(Color.white);
        int startWidth = g.getFontMetrics().stringWidth(start);
        g.drawString(start, (Display.WIDTH /2) - (startWidth/2), (Display.HEIGHT/2) + 75);

    }

    /**
     * Initialisiert das Menü, indem es den KeyListener zum Canvas hinzufügt, um Tastatureingaben zu verarbeiten.
     * @param canvas
     */
    @Override
    public void init(Canvas canvas) {
        canvas.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Verarbeitet Tastendrücke. Wenn die Eingabetaste gedrückt wird, wechselt das Spiel zum Spielzustand.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            getStateMachine().setState((byte)1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
