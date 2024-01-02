package ezgin.src.main.entities;


import java.awt.geom.Point2D;

import ezgin.src.main.entities.livingentities.Enemy;
import ezgin.src.main.entities.livingentities.Player;
import ezgin.src.main.entities.nonlivingentities.CheckPoint;
import ezgin.src.main.entities.nonlivingentities.Chest;
import ezgin.src.main.entities.nonlivingentities.Door;
import ezgin.src.main.entities.nonlivingentities.Lumina;
import ezgin.src.main.entities.nonlivingentities.Stela;
import ezgin.src.main.gamestates.InGame;

import static ezgin.src.utils.Constants.EntitySpriteConstants.RUN_UP;
import static ezgin.src.utils.Constants.GameConstants.*;
import static ezgin.src.utils.Constants.GameConstants.SCREEN_Y_DEFAULT;
import static ezgin.src.utils.Constants.ImageConstants.*;
import static ezgin.src.utils.Constants.SpawnConstants.getEntityCount;

/**
 * Klasse für die Verwaltung der Entities
 */
public class EntityHandler {
    

    private static EntityHandler instance; // Singleton-Instanz

    // Entities
    private Player player;
    private Door[] door;
    private Enemy[] enemies;
    private Chest[] chests;
    private CheckPoint[] checkPoints;
    private Stela[] stelas;
    private Lumina[] luminas;

    private EntityHandler() {
        setPlayer(new Player());
        initEntities();
    }

    /**
     * Gibt die Singleton-Instanz zurück
     *
     * @return Singleton-Instanz
     */
    public static EntityHandler getInstance() {
        if (instance == null) {
            setInstance(new EntityHandler());
        }
        return instance;
    }

    /**
     * Updated alle Entities
     */
    public void update() {
        checkPlayerEnemyCollision();
        for (Stela stela : getStelas()) {
            checkPlayerEntityCollision(stela);
        }
        for (Lumina lumina : getLuminas()) {
            checkPlayerEntityCollision(lumina);
        }
        getPlayer().update();
        for (Enemy enemy : getEnemies()) {
            enemy.update();
        }
        for (Chest chest : getChests()) {
            chest.update();
        }
        for (CheckPoint checkPoint : getCheckPoints()) {
            checkPoint.update();
        }
        for (Stela stela : getStelas()) {
            stela.update();
        }
        for (Door door : getDoors()) {
            door.update();
        }
        for (Lumina lumina : getLuminas()) {
            lumina.update();
        }
    }

    /**
     * Initialisiert die Entities
     */
    private void initEntities() {
        // initialisiert die Enemies
        setEnemies(new Enemy[getEntityCount(PA_ENEMY)]);
        for (int i = 0; i < getEnemies().length; i++) {
            getEnemies()[i] = new Enemy(i);
        }
        // initialisiert die Truhen
        setChests(new Chest[getEntityCount(PA_CHEST)]);
        for (int i = 0; i < getChests().length; i++) {
            getChests()[i] = new Chest(i);
        }
        // initialisiert die Checkpoints
        setCheckPoints(new CheckPoint[getEntityCount(PA_CHECKPOINT)]);
        for (int i = 0; i < getCheckPoints().length; i++) {
            getCheckPoints()[i] = new CheckPoint(i);
        }
        // initialisiert die Stelen
        setStelas(new Stela[getEntityCount(PA_STELA)]);
        for (int i = 0; i < getStelas().length; i++) {
            getStelas()[i] = new Stela(i);
        }
        // initialisiert die Tür
        setDoor(new Door[getEntityCount(PA_DOOR)]);
        for (int i = 0; i < getDoors().length; i++) {
            getDoors()[i] = new Door(i);
        }
        // initialisiert das Lumina-Objekt
        setLuminas(new Lumina[getEntityCount(PA_LUMINA)]);
        for (int i = 0; i < getLuminas().length; i++) {
            getLuminas()[i] = new Lumina(i);
        }
    }

    /**
     * Setzt alle Entities zurück
     */
    public void reset() {
        setPlayer(new Player());
        initEntities();
    }

    /**
     * initialisiert die Entities für das nächste Level
     */
    public void nextLevel() {
        getPlayer().defaultPos();
        if (getPlayer().getCurrentLifePoints() < getPlayer().getMaxLifePoints() - 10) {
            getPlayer().setCurrentLifePoints(getPlayer().getCurrentLifePoints() + 10);
        } else {
            getPlayer().setCurrentLifePoints(getPlayer().getMaxLifePoints());
        }
        initEntities();
    }

    /**
     * überprüft ob der Spieler mit einem Gegner kollidiert
     */
    private void checkPlayerEnemyCollision() {
        for (SuperLivingEntity superLivingEntity : getSuperLivingEntities()) {
            if (superLivingEntity.isAlive()) {
                if (superLivingEntity instanceof Enemy) {
                    if (superLivingEntity.isAttacking() && superLivingEntity.noAttackAnimation()) {
                        superLivingEntity.resetValues();
                        getPlayer().setCurrentLifePoints(getPlayer().getCurrentLifePoints() - superLivingEntity.getAttackDamage() + getPlayer().getShield());
                        superLivingEntity.setAttackAnimation(true);
                        getPlayer().setLastEnemyMadeDamageID(superLivingEntity.getId());
                    }
                } else if (superLivingEntity instanceof Player) {
                    if (superLivingEntity.isAttacking() && superLivingEntity.noAttackAnimation()) {
                        for (Enemy enemy : getEnemies()) {
                            if (enemy.isAttacking() && superLivingEntity.getRange().intersects(enemy.getHitBox())) {
                                enemy.setCurrentLifePoints(enemy.getCurrentLifePoints() - superLivingEntity.getAttackDamage());
                            }
                        }
                        superLivingEntity.setAttacking(false);
                    }
                }
            }
        }
    }

    /**
     * überprüft, ob ein Event zwischen dem Spieler und einem Entity stattfindet
     *
     * @param superEntity Entity
     */
    private void checkPlayerEntityCollision(SuperEntity superEntity) {
        if (getPlayer().getHitBox().intersects(superEntity.getHitBox()) && superEntity.getClips()[0].getFramePosition() < superEntity.getClips()[0].getFrameLength() && (!(superEntity instanceof Stela) || getCheckPoints()[superEntity.getId()].isActive())) {
            Point2D.Float playerTarget = new Point2D.Float(superEntity.getHitBox().x + superEntity.getHitBox().width / 2f, superEntity.getHitBox().y + getPlayer().getHitBox().height / 2f);
            if (getPlayer().posReached(playerTarget)) {
                getPlayer().resetMovement();
                superEntity.setActive(true);
                superEntity.getClips()[0].start();
                getPlayer().setSpriteRow(RUN_UP);
            } else {
                getPlayer().moveToPos(playerTarget);
            }
            InGame.moveScreen(new Point2D.Float(SCREEN_X_DEFAULT, SCREEN_Y_DEFAULT - superEntity.getScreenY() + superEntity.getHeight()));
        } else if (!getPlayer().getHitBox().intersects(superEntity.getHitBox()) && superEntity.isActive()) {
            superEntity.getClips()[0].setFramePosition(0);
            InGame.moveScreen(new Point2D.Float(SCREEN_X_DEFAULT, SCREEN_Y_DEFAULT));
            if (InGame.getScreenX() == SCREEN_X_DEFAULT && InGame.getScreenY() == SCREEN_Y_DEFAULT) {
                superEntity.setActive(false);
            }
        }
    }


    // GETTER UND SETTER


    public SuperLivingEntity[] getSuperLivingEntities() {
        SuperLivingEntity[] superLivingEntities = new SuperLivingEntity[getEnemies().length + 1];
        System.arraycopy(getEnemies(), 0, superLivingEntities, 0, getEnemies().length);
        superLivingEntities[getEnemies().length] = getPlayer();
        return superLivingEntities;
    }

    public Chest[] getChests() {
        return chests;
    }

    public Player getPlayer() {
        return player;
    }

    public CheckPoint[] getCheckPoints() {
        return checkPoints;
    }

    public Stela[] getStelas() {
        return stelas;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public static void setInstance(EntityHandler instance) {
        EntityHandler.instance = instance;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEnemies(Enemy[] enemies) {
        this.enemies = enemies;
    }

    public void setChests(Chest[] chests) {
        this.chests = chests;
    }

    public Door[] getDoors() {
        return door;
    }

    public void setDoor(Door[] door) {
        this.door = door;
    }

    public void setCheckPoints(CheckPoint[] checkPoints) {
        this.checkPoints = checkPoints;
    }

    public void setStelas(Stela[] stelas) {
        this.stelas = stelas;
    }

    public Lumina[] getLuminas() {
        return luminas;
    }

    public void setLuminas(Lumina[] luminas) {
        this.luminas = luminas;
    }
}