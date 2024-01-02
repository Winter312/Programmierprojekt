package ezgin.src.main.ui.hud;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

import ezgin.src.main.entities.livingentities.Player;
import ezgin.src.main.enums.Item;
import ezgin.src.main.enums.Level;
import ezgin.src.utils.Load;

import static ezgin.src.main.enums.Level.*;
import static ezgin.src.utils.Constants.GameConstants.*;
import static ezgin.src.utils.Constants.ImageConstants.*;

/**
 * Klasse für das Inventar
 */
public class Inventory {

    // Spieler und Variablen für die Anzeige des Inventars
    private Player player;
    private Item lastItemCollected;
    private long lastItemCollectedTime;
    private boolean showInventory;
    private HashMap<Level, Item[]> levelItems;

    // Mausposition
    private Point mousePosition;

    public Inventory(Player player) {
        setPlayer(player);
        setShowInventory(false);
        setLastItemCollectedTime(0);
        setMousePosition(new Point(0, 0));

        // setzt die Items zurück
        for (Item item : Item.values()) {
            item.reset();
        }

        // Item für die einzelnen Level
        setLevelItems(new HashMap<>());
        getLevelItems().put(LEVEL_ZERO, new Item[]{Item.DAGGER});
        getLevelItems().put(LEVEL_ONE, new Item[]{Item.DAGGER, Item.LIFE_STONE});
        getLevelItems().put(LEVEL_TWO, new Item[]{Item.SPEED_STONE, Item.SHIELD, Item.KEY});
        getLevelItems().put(LEVEL_THREE, new Item[]{Item.CRYSTAL, Item.OBSIDIAN});
    }

    /**
     * setzt das Item, das eingesammelt wurde
     *
     * @param id ID des Item
     */
    public void setItem(int id) {
        Item item = getLevelItems().get(getCurrentLevel())[id];
        setLastItemCollected(item);
        setLastItemCollectedTime(System.currentTimeMillis());
        updatePlayerStats(item);
        item.acquire();
    }

    /**
     * aktualisiert die Werte des Spielers, wenn ein Item eingesammelt wurde
     *
     * @param item Item, das eingesammelt wurde
     */
    private void updatePlayerStats(Item item) {
        switch (item) {
            case DAGGER:
                getPlayer().setSuperEntity(Load.getEntitySprites(getPlayerPath(1)));
                getPlayer().setAttackDamage(getPlayer().getAttackDamage() + 3);
                getPlayer().setRangeOffsetX(getPlayer().getHitBoxOffsetX() - TILE_SIZE / 2);
                getPlayer().setRangeOffsetY(getPlayer().getHitBoxOffsetY() - TILE_SIZE / 2);
                getPlayer().setRange(new Rectangle(getPlayer().getRangeOffsetX(), getPlayer().getRangeOffsetY(), getPlayer().getHitBox().width + TILE_SIZE, getPlayer().getHitBox().height + TILE_SIZE));
                break;
            case LIFE_STONE:
                getPlayer().setMaxLifePoints(getPlayer().getMaxLifePoints() + 50);
                break;
            case SPEED_STONE:
                getPlayer().setRunSpeed(getPlayer().getRunSpeed() + .25f * SCALE);
                break;
            case SHIELD:
                getPlayer().setSuperEntity(Load.getEntitySprites(getPlayerPath(Item.DAGGER.isAcquired() ? 3 : 2)));
                getPlayer().setShield(getPlayer().getShield() + 2);
                break;
            case KEY:
                break;
            case CRYSTAL:
                getPlayer().setRangeOffsetX(getPlayer().getRangeOffsetX() - TILE_SIZE / 2);
                getPlayer().setRangeOffsetY(getPlayer().getRangeOffsetY() - TILE_SIZE / 2);
                getPlayer().setRange(new Rectangle(getPlayer().getRangeOffsetX(), getPlayer().getRangeOffsetY(), getPlayer().getRange().width + TILE_SIZE, getPlayer().getRange().width + TILE_SIZE));
                break;
            case OBSIDIAN:
                getPlayer().setAttackDamage(getPlayer().getAttackDamage() + 2);
                break;
        }
    }


    // GETTER UND SETTER


    public Item getLastItemCollected() {
        return lastItemCollected;
    }

    public long getLastItemCollectedTime() {
        return lastItemCollectedTime;
    }

    public boolean isShowInventory() {
        return showInventory;
    }

    public void setShowInventory(boolean showInventory) {
        this.showInventory = showInventory;
    }

    public Point getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(Point mousePosition) {
        this.mousePosition = mousePosition;
    }

    public Player getPlayer() {
        return player;
    }

    public void setLastItemCollected(Item lastItemCollected) {
        this.lastItemCollected = lastItemCollected;
    }

    public void setLastItemCollectedTime(long lastItemCollectedTime) {
        this.lastItemCollectedTime = lastItemCollectedTime;
    }

    public HashMap<Level, Item[]> getLevelItems() {
        return levelItems;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLevelItems(HashMap<Level, Item[]> levelItems) {
        this.levelItems = levelItems;
    }
}
