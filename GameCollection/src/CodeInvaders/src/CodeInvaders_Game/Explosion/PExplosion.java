package CodeInvaders_Game.Explosion;

import java.awt.*;
import java.util.Random;

public class PExplosion implements PExplosionType{
    private double[] xPos, yPos, xPosVol, yPosVol, angle, energy;

    /**
     * Konstruktor: Erstellt eine neue Explosion mit einer Anfangsposition.
     * @param xPos
     * @param yPos
     */
    public PExplosion(double xPos, double yPos){
        int index = 12;
        this.xPos = new double[index];
        this.yPos = new double[index];
        this.xPosVol = new double[index];
        this.yPosVol = new double[index];
        this.angle = new double[index];
        this.energy = new double[index];

        for (int i = 0; i < index; i++){
            this.xPos[i] = xPos;
            this.yPos[i] = yPos;
            this.xPosVol[i] = Math.random() * 1;
            this.yPosVol[i] = Math.random() * 1;
            this.energy[i] = Math.random();

            Random r = new Random();
            angle[i] = r.nextInt(6) + 1;
        }
    }
    /**
     * Zeichnet die Explosionsteilchen auf dem Bildschirm.
     * @param graphic
     */
    @Override
    public void draw(Graphics2D graphic) {
        for (int i = 0; i < xPos.length; i++){
            if (energy[i] >= 0.00){
                graphic.setColor(new Color(0.8f,0.067f,0.533f, (float) energy[i]));
            } else {
                graphic.setColor(new Color(0.8f,0.067f,0.533f, 1));
            }
            graphic.fillRect((int) xPos[i], (int) yPos[i],3,3);
        }
    }

    /**
     * Aktualisiert den Zustand der Explosionsteilchen in jedem Frame.
     * @param delta
     */
    @Override
    public void update(double delta) {
        for (int i = 0; i < xPos.length; i++){
            energy[i] -= 0.01;
            xPos[i] += xPosVol[i] * Math.cos(angle[i]);
            yPos[i] += yPosVol[i] * Math.cos(angle[i]);
        }
    }

    /**
     * Überprüft, ob die Explosion zerstört werden sollte.
     * @return
     */
    @Override
    public boolean destroy() {
        int destroy = 0;
        for (int i = 0; i < xPos.length; i++){
            if (energy[i] < 0.00){
                destroy++;
            } else {
                break;
            }
        }

        if (destroy == energy.length){
            return true;
        }
        return false;
    }
}
