package CodeInvaders_Game.States;

import CodeInvaders_Game.GameScreen.GScreen;
import CodeInvaders_Game.Menu.menuScreen;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.util.ArrayList;
public class StateMachine {

    private ArrayList<SupStatMachine> states = new ArrayList<SupStatMachine>();
    private Canvas canvas;
    private byte selectState = 0;

    /**
     * Initialisiert die StateMachine mit einem gegebenen Canvas.
     * Erstellt Instanzen von GScreen (Spielbildschirm) und menuScreen (Menübildschirm) und fügt sie der Liste der Zustände hinzu.
     * @param canvas
     */
    public StateMachine(Canvas canvas) {
        SupStatMachine game = new GScreen(this);
        SupStatMachine menu = new menuScreen(this);
        states.add(menu);
        states.add(game);

        this.canvas = canvas;
    }

    /**
     * Zeichnet den aktuellen Spielzustand auf den Canvas.
     * @param g
     */
    public void draw(Graphics2D g) {
        states.get(selectState).draw(g);
    }

    /**
     * Aktualisiert den aktuellen Spielzustand.
     * @param delta
     */
    public void update(double delta) {
        states.get(selectState).update(delta);
    }

    /**
     * Wechselt den aktuellen Spielzustand und initialisiert den neuen Zustand.
     * Entfernt alle KeyListener vom Canvas und fügt den für den neuen Zustand erforderlichen KeyListener hinzu.
     * @param i
     */
    public void setState(byte i) {
        for (int r = 0; r < canvas.getKeyListeners().length; r++)
            canvas.removeKeyListener(canvas.getKeyListeners()[r]);
        selectState = i;
        states.get(selectState).init(canvas);
    }
}


