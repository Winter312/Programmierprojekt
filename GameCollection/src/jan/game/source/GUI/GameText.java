package jan.game.source.GUI;
import java.awt.*;
import javax.swing.*;

public class GameText extends JLabel {

        
    private static final long serialVersionUID = 1L;


    /**
     * Konstruktor mit Text
     */
    public GameText(String text, Point centerPos, Font font, Color color, Color colorB) {
        
        this.setText(text);
        this.setForeground(color);
        this.setFont(font);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setBackground(colorB);
        this.setOpaque(true);
        this.setSize(this.getPreferredSize());
        this.setBounds(centerPos.x - getSize().width/2, centerPos.y - getSize().height/2 - 28, 100, 100);
        this.setSize(this.getPreferredSize());

    }
    
    /**
     * Konstruktor mit Bild
     */
    public GameText(ImageIcon image, Point centerPos, Color color, Color colorB) {
        
        image.setImage(image.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        this.setIcon(image);
        this.setForeground(color);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setBackground(colorB);
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
                
        super.setLocation(x - getWidth()/2, y - getHeight()/2 - 28);
    }
}
