package jan.game.source.GUI;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MenuButton extends JButton implements MouseListener {

    private static final long serialVersionUID = 1L;

    /**
     * Konstruktor
     * @param difficulty
     */
    public MenuButton(String text, Point centerPos, Point size) {

        this.setText(text);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setOpaque(true);
        this.setBounds(centerPos.x - getSize().width / 2, centerPos.y - getSize().height / 2 - 28, size.x, size.y);
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }
}
