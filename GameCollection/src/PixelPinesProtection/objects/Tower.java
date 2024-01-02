package objects;

import helperMethods.Constants;
import static helperMethods.Constants.Towers.*;

/**
 * Klasse für die Turmobjekte im Spiel.
 */
public class Tower {

    private int x, y, id, towerType, cdTick, dmg; // Position, ID, Typ, Cooldown-Tick und Schaden des Turms
    private float range, cooldown; // Reichweite und Cooldown-Zeit des Turms
    private int tier; // Stufe oder Level des Turms

    /**
     * Konstruktor zum Erstellen eines Turms.
     * @param x X-Position des Turms
     * @param y Y-Position des Turms
     * @param id ID des Turms
     * @param towerType Typ des Turms (z.B. Rocketlauncher, Green Gun etc.)
     */
    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        this.tier = 1; // Turm beginnt auf Stufe 1
        setDefaultDmg();
        setDefaultRange();
        setDefaultCd();
    }

    /**
     * Aktualisiert den Turm, indem der Cooldown-Tick inkrementiert wird.
     */
    public void update() {
        cdTick++;
    }

    /**
     * Verbessert den Turm, indem Schaden, Reichweite und Cooldown angepasst werden.
     */
    public void upgradeTower() {
        this.tier++;

        switch (towerType) {
            case ROCKETLAUNCHER:
                dmg += 4;
                range += 8;
                cooldown -= 5;
                break;
            case GREEN:
                dmg += 5;
                range += 5;
                cooldown -=6;
                break;
            case RED:
                dmg += 3;
                range += 4;
                cooldown -=2;
                break;
        }
    }

    /**
     * Überprüft, ob der Cooldown des Turms abgelaufen ist.
     * @return True, wenn der Cooldown abgelaufen ist, sonst False.
     */
    public boolean isCooldownOver() {
        return cdTick >= cooldown;
    }

    /**
     * Setzt den Cooldown-Tick des Turms zurück.
     */
    public void resetCooldown() {
        cdTick = 0;
    }

    // Setzt Standardwerte für Cooldown, Reichweite und Schaden basierend auf dem Turmtyp.
    private void setDefaultCd() { cooldown = Constants.Towers.getDefaultCd(towerType); }
    private void setDefaultRange() { range = Constants.Towers.getDefaultRange(towerType); }
    private void setDefaultDmg() { dmg = Constants.Towers.getStartDmg(towerType); }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getTowerType() {
        return towerType;
    }

    public int getDmg() {
        return dmg;
    }

    public float getRange() {
        return range;
    }

    public float getCooldown() {
        return cooldown;
    }

    public int getTier() {
        return tier;
    }


}
