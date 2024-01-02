package CodeInvaders_Game.EnemyBullet;

import CodeInvaders_Game.GameScreen.NormalBlocks;
import CodeInvaders_Game.GameScreen.Player;

import java.awt.*;

public abstract class EnemyWeapon {
    public abstract void draw(Graphics2D graphic);

    public abstract void update(double delta, NormalBlocks block, Player player);
    public abstract boolean collision(Rectangle rect);
    protected abstract void wallCollide(NormalBlocks blocks);
    protected abstract void isOutofBound();

    public abstract int getXPos();
    public abstract int getYPos();
}
