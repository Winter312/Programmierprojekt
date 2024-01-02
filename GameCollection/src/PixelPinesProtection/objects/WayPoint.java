package objects;

/**
 * Klasse für Wegpunkte im Spiel.
 * Wegpunkte definieren den Pfad, den die Feinde folgen.
 */
public class WayPoint {
    public float x, y; // Koordinaten des Wegpunkts

    /**
     * Konstruktor zum Erstellen eines Wegpunkts.
     * @param x X-Koordinate des Wegpunkts
     * @param y Y-Koordinate des Wegpunkts
     */
    public WayPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
