package jan.game.source;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Coin extends JLabel{

    private static final long serialVersionUID = 1L;

    /**
     * Konstruktor
     * @param centerPos
     */
    public Coin(Point centerPos) {
        
        ImageIcon image = new ImageIcon(ObjectCollection.class.getResource("images/coin.png"));
        image.setImage(image.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        this.setIcon(image);
        this.setForeground(Color.white);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setBackground(Color.green);
        this.setOpaque(true);
        this.setBounds(centerPos.x - getSize().width/2, centerPos.y - getSize().height/2 - 28, 35, 35);

    }
    
    /**
     * Setzt Position relative zum Zentrum des Objektes
     * @param newPoint
     */
    @Override
    public void setLocation(Point newPoint) {

        setLocation(newPoint.x, newPoint.y);
    }

    /**
     * Gibt die Position relative zum Zentrum des Objektes züruck
     * @return Point
     */
    @Override
    public Point getLocation() {

        Point pos = super.getLocation();
        pos.x += getWidth() / 2;
        pos.y += getHeight() / 2 + 30;
        return pos;
    }

    /**
     * Setzt Position relative zum Zentrum des Objektes
     * @param x (int)
     * @param y (int)
     */
    @Override
    public void setLocation(int x, int y) {

        super.setLocation(x - getWidth() / 2, y - getHeight() / 2 - 28);
    }
}
