package CodeInvaders_Game.GameScreen;
import java.awt.*;
import java.util.ArrayList;

public class NormalBlocks {



    public static ArrayList<Rectangle> wall = new ArrayList<Rectangle>();

    /**
     * hier werden die Blöcke gesetzt um sich vor den Schüssen der gegner zu schützen
     */
    public NormalBlocks(){
        normalBlocks(75, 450);
        normalBlocks(275, 450);
        normalBlocks(475, 450);
        normalBlocks(675, 450);

    }

    /**
     * Zeichnet die einzelnen Pixel der Schutzwand
     * @param graphic
     */
    public void draw(Graphics2D graphic){
        graphic.setColor(Color.GREEN);
        for(int i = 0; i < wall.size(); i++){
            graphic.fill(wall.get(i));
        }
    }

    /**
     * Hier werden diese Blöcke erstellt mithilfe einer For-Schleife
     * @param xPos
     * @param yPos
     */
    public void normalBlocks (int xPos, int yPos){
        int wallWidth = 3;
        int x = 0;
        int y = 0;

        for (int i = 0; i < 13; i++){
            if (14 + (i * 2) + wallWidth < 22 + wallWidth){

               rows(14 + (i * 2) + wallWidth, xPos - (i * 3), yPos + (i*3));
               x = ( i * 3) + 3;
            } else {
                rows(22 + wallWidth , xPos - x, yPos + (i*3));
                y = (i * 3);
            }
        }

        // Linke Seite
        for (int i = 0; i < 5; i++){
            rows(8 + wallWidth - i, xPos - x, (yPos + y) + (i*3));
        }

        // Rechte Seite
        for (int i = 0; i < 5; i++){
            rows(8 + wallWidth - i, ((xPos - x) + (14*3)) + (i*3) , (yPos + y) + (i*3));
        }

    }


    /**
     *  Eine Reihe der Schutzwand an der Stelle i
     * @param rows
     * @param xPos
     * @param yPos
     */
    public void rows (int rows, int xPos, int yPos){
        for (int i = 0; i < rows; i++){
            Rectangle block = new Rectangle(xPos + (i * 3), yPos, 10, 10);
            wall.add(block);

        }
    }


    /**
     * Setzt die Blöcke zurück
     */
    public void reset(){
        wall.clear();
        normalBlocks(75, 450);
        normalBlocks(275, 450);
        normalBlocks(475, 450);
        normalBlocks(675, 450);
    }
    /**
     * Setzt die Blöcke für das zweite Level zurück
     */
    public void resetSecond(){
        wall.clear();
        normalBlocks(250, 450);
        normalBlocks(500, 450);
    }
    /**
     * Fürs Letzte Level gibt es keine Blöcke
     */
    public void resetLast(){
        wall. clear();
    }
}

