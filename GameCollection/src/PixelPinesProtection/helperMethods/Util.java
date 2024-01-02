package helperMethods;

public class Util {

    /**
     * Berechnet die euklidische Entfernung (Hypotenuse) zwischen zwei Punkten.
     *
     * @param x1 X-Koordinate des ersten Punktes.
     * @param y1 Y-Koordinate des ersten Punktes.
     * @param x2 X-Koordinate des zweiten Punktes.
     * @param y2 Y-Koordinate des zweiten Punktes.
     * @return Die euklidische Entfernung als ganzzahliger Wert.
     */
    public static int getHypoDistance(float x1, float y1, float x2, float y2) {

        float xDiff = Math.abs(x1 - x2);
        float yDiff = Math.abs(y1 - y2);

        return (int) Math.hypot(xDiff, yDiff);


    }

}
