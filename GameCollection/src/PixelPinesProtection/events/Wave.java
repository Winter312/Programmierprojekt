package PixelPinesProtection.events;

import java.util.ArrayList;

/**
 * Die Klasse Wave repräsentiert eine einzelne Welle von Gegnern
 */
public class Wave {
    // Liste der Gegner in dieser Welle, wobei jeder Eintrag den Typ eines Gegners repräsentiert.
    private ArrayList<Integer> enemyList;

    /**
     * Konstruktor für eine Welle.
     * @param enemyList Eine Liste von Integer-Werten, die die Typen der Gegner in dieser Welle darstellen.
     */
    public Wave(ArrayList<Integer> enemyList) {
        this.enemyList = enemyList;
    }

    /**
     * Gibt die Liste der Gegner dieser Welle zurück.
     * @return Eine Liste von Integer-Werten, die die Typen der Gegner darstellen.
     */
    public ArrayList<Integer> getEnemyList() {
        return enemyList;
    }
}
