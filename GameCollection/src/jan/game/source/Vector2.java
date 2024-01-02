package jan.game.source;
import java.awt.Point;

public class Vector2 {

    public double x;
    public double y;
    
    /**
     * Konstruktor ohne Parameter, x und y werden auf 0 gesetzt
     */
    public Vector2() 
    {
        x = 0;
        y = 0;
    }

    /**
     * Konstruktor mit x und y
     * @param x (double)
     * @param y (double)
     */
    public Vector2(double x, double y) {
        
        this.x = x;
        this.y = y;
    }
    
    /**
     * Konstruktor mit Vektor
     * @param vec
     */
    public Vector2(Vector2 vec) {
        
        this.x = vec.x;
        this.y = vec.y;
    }
    
    /**
     * Konstruktor mit Point
     * @param point 
     */
    public Vector2(Point point) {
        
        this.x = point.x;
        this.y = point.y;
    }
    
    /**
     * Berechnet die Länge des Vektors
     * @return double 
     */
    public double lenght() {
        
        return Math.sqrt(x*x + y*y);
   }
    
    /**
     * Multiplikation mit einer Konstanten
     * @param value
     * @return Vector2 
     */
    public Vector2 mul(double value) {
        
        x *= value;
        y *= value;
        return this;
    }
    
    /**
     * Berechnet die Distanz zu einem anderen Vektor
     * @param vec
     * @return double 
     */
    public double distanceTo(Vector2 vec) {
        
        Vector2 dis = sub(vec, this);
        return dis.lenght();
        
    }
    
    /**
     * Wandeln diesen Vektor in ein Point Objekt um
     * @return Point 
     */
    public Point toPoint() {
        
        Point point = new Point();
        point.x = (int)x;
        point.y = (int)y;
        return point;
    }
    
    /**
     * To String Methode
     * @return String 
     */
    @Override
    public String toString() {
        
       return "( " + x + ", " + y + " )";
    }
    
    //Static
    
    /**
     * Subtraktion von 2 Vektoren (a + b)
     * @return Vector2 
     */
    public static Vector2 add(Vector2 a, Vector2 b) {
        
        Vector2 c = new Vector2();
        c.x = a.x + b.x;
        c.y = a.y + b.y;
        
        return c;
    }
    
    /**
     * Subtraktion von 2 Vektoren (a - b)
     * @return Vector2 
     */
    public static Vector2 sub(Vector2 a, Vector2 b) {
        
        Vector2 c = new Vector2();
        c.x = a.x - b.x;
        c.y = a.y - b.y;
        
        return c;
    }
    
    /**
     * Berechnet die Distanz zwichen 2 Vektoren
     * @return double
     */
    public static double distance(Vector2 a, Vector2 b) {
        
        Vector2 dis = sub(b, a);
        return dis.lenght();
        
    }
    
    /**
     * Normalisiert Ein Vektor
     * @return Vector2
     */
    public static Vector2 normalize(Vector2 value) {
        
        Vector2 normalized = new Vector2();
        normalized.x = value.x / value.lenght();
        normalized.y = value.y / value.lenght();
       
        return normalized;     
    }

    
}
