package CodeInvaders_Game.Enemies;

import CodeInvaders_Game.GameScreen.NormalBlocks;
import CodeInvaders_Game.GameScreen.Player;
import CodeInvaders_Game.Steering.EnemyBulletSteering;

import java.awt.*;
import java.util.ArrayList;

public abstract class EnemyType {
    private EnemyBulletSteering bulletSteering;

    public EnemyType(EnemyBulletSteering bulletSteering){
        this.bulletSteering = bulletSteering;
    }

    public abstract void draw(Graphics2D g);
    public abstract void update(double delta, Player player, NormalBlocks blocks);
    public abstract void DirectionChange(double delta);

    public abstract boolean deathScene();
    public abstract boolean collide(int i, Player player, NormalBlocks blocks, ArrayList<TypeBasic> enemys);
    public abstract boolean isOutofBounds();

    public EnemyBulletSteering getBulletSteering() {
        return bulletSteering;
    }
}
