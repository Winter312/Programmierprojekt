package CodeInvaders_Game.Bullets;

import CodeInvaders_Game.GameScreen.NormalBlocks;

import java.awt.*;

public abstract class WeaponType {
    protected double xPos, yPos;
    protected int width, height;

    public abstract void draw(Graphics2D grahpic);
    public abstract void update(double delta, NormalBlocks blocks);
    public abstract boolean collisionRect(Rectangle rect);
    public abstract boolean destroy();
    protected abstract void wallCollision(NormalBlocks block);
    protected abstract void isOutOfBlock();

    public int getxPos(){
        return (int) xPos;
    }
    public void setxPos(double xPos){
        this.xPos = xPos;
    }
    public double getyPos() {
        return yPos;
    }
    public void setyPos(double yPos){
        this.yPos = yPos;
    }

    public int getWidth(){
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }
}
