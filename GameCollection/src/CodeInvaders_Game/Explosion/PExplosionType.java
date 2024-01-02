package CodeInvaders_Game.Explosion;

import java.awt.*;

public interface PExplosionType {
    public void draw(Graphics2D graphic);
    public void update(double delta);
    public boolean destroy();
}
