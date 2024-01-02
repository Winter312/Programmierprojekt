package CodeInvaders_Game.Levels;

import CodeInvaders_Game.GameScreen.NormalBlocks;

import java.awt.*;

public interface SuperFirstLevel {
    void draw(Graphics2D graphic);
    void update (double delta, NormalBlocks block);
    void DirectionChange(double delta);
    void ChangeDirectionForAllEnemies(double delta);
    boolean IsGameOver();

    boolean IsComplete();
    void reset();
}
