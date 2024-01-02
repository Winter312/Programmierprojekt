package CodeInvaders_Game.Levels;
import CodeInvaders_Game.Enemies.TypeBasic;
import CodeInvaders_Game.GameScreen.NormalBlocks;
import CodeInvaders_Game.GameScreen.Player;
import CodeInvaders_Game.Steering.EnemyBulletSteering;
import java.awt.*;
import java.util.ArrayList;

public class FirstLevel implements SuperFirstLevel{
    private Player player;
    private ArrayList<TypeBasic> enemys = new ArrayList<>();
    private EnemyBulletSteering bulletSteering;
    private NormalBlocks normalBlocks;

    /**
     * Initialisiert das Level mit dem Spieler, der Geschosssteuerung und den Blöcken. Fügt Gegner hinzu.
     * @param player
     * @param bulletSteering
     * @param normalBlocks
     */
    public FirstLevel(Player player, EnemyBulletSteering bulletSteering, NormalBlocks normalBlocks){
        this.player = player;
        this.bulletSteering = bulletSteering;
        this.normalBlocks = normalBlocks;
        addEnemies();
    }

    /**
     * Zeichnet die Gegner, Geschosse und andere Elemente des Levels.
     * @param graphic
     */
    @Override
    public void draw(Graphics2D graphic) {
        if (enemys == null){
            return;
        }
        for (int i = 0 ; i < enemys.size(); i++){
            if (!enemys.get(i).isDeath()) {
                enemys.get(i).draw(graphic);
            }
            bulletSteering.draw(graphic);
        }
    }

    /**
     * Aktualisiert den Zustand des Levels, einschließlich der Bewegung und Aktionen der Gegner sowie der Geschosse.
     * @param delta
     * @param block
     */
    @Override
    public void update(double delta, NormalBlocks block) {
        if (enemys == null){
            return;
        }
        for (int i = 0 ; i < enemys.size(); i++){
            enemys.get(i).update(delta,player,block);
        }
        for (int i = 0; i < enemys.size();i++){
            enemys.get(i).collide(i, player, block,enemys);
        }
        DirectionChange(delta);
        bulletSteering.update(delta,block,player);
    }

    /**
     * Überprüft und steuert die Richtungsänderung der Gegner.
     * @param delta
     */
    @Override
    public void DirectionChange(double delta) {
        if (enemys == null){
            return;
        }
        for (int i = 0 ; i < enemys.size(); i++){
            if (enemys.get(i).isOutofBounds()){
                ChangeDirectionForAllEnemies(delta);
            }
        }
    }

    /**
     * Ändert die Richtung aller Gegner im Level.
     * @param delta
     */
    @Override
    public void ChangeDirectionForAllEnemies(double delta) {
        for (int i = 0 ; i < enemys.size(); i++){
            enemys.get(i).DirectionChange(delta);
        }
    }

    /**
     * Überprüft, ob das Spiel vorbei ist (basierend auf der Gesundheit des Spielers).
     * @return
     */
    @Override
    public boolean IsGameOver() {
        return player.getHealth() <= 0;
    }

    /**
     * Überprüft, ob das Level abgeschlossen ist (basierend darauf, ob Gegner noch vorhanden sind).
     * @return
     */
    @Override
    public boolean IsComplete() {
        return enemys.isEmpty();
    }

    /**
     * Setzt den Status des Spielers auf "nicht lebendig".
     */
    public void SetPlayerDead(){
        player.setAlive(false);
    }

    /**
     * Setzen das Level und die Spielzustände zurück. Für Level 1
     */
    @Override
    public void reset() {
        player.reset();
        enemys.clear();
        addEnemies();
        bulletSteering.reset();
        normalBlocks.reset();
        player.setAlive(true);
    }

    /**
     * Setzen das Level und die Spielzustände zurück. Für Level 2
     */
    public void secondReset(){
        player.reset();
        enemys.clear();
        addEnemies();
        bulletSteering.reset();
        normalBlocks.resetSecond();
        player.setAlive(true);
    }

    /**
     * Setzen das Level und die Spielzustände zurück. Für Level 3
     */
    public void lastReset(){
        player.reset();
        enemys.clear();
        addEnemies();
        bulletSteering.reset();
        normalBlocks.resetLast();
        player.setAlive(true);
    }

    /**
     * Fügt Gegner zum Level hinzu.
     */
    public void addEnemies(){
        for (int y = 0; y < 5; y++){
            for (int x = 0; x < 10; x++){
                TypeBasic e = new TypeBasic(150 +(x * 40), 50 + (y * 40), 1, 3, bulletSteering );
                enemys.add(e);
            }
        }
    }
}
