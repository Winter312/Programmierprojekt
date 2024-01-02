package jan.game.source;
import java.util.Random;
import javax.swing.ImageIcon;

public class ObjectCollection {

    /**
     * Gibt einen der 46 text zufällig züruck
     * @return String
     */
    public static String getText() {
        
        int random = new Random().nextInt(47);
        
        switch (random) {
        
        case 0: {
            
            return "ERROR";
        }
        case 1: {
            
            return "YOU WON";
        }
        case 2: {
            
            return "Allow NotAVirus to acces Wallet";
        }
        case 3: {
            
            return "30/02/24";
        }
        case 4: {
            
            return "FREE VPN";
        }
        case 5: {
            
            return "Connect to public WIFI?";
        }
        case 6: {
            
            return "UPDATE NOW";
        }
        case 7: {
            
            return "NotaZIPbomb.zip";
        }
        case 8: {
            
            return "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        }
        case 9: {
            
            return "3 FPS";
        }
        case 10: {
            
            return "IndexOutOfBoundsException";
        }
        case 11: {
            
            return "IllegalArgumentException";
        }
        case 12: {
            
            return "G00GLE";
        }
        case 13: {
            
            return ":(";
        }
        case 14: {
            
            return "x??gb?wqjh?";
        }
        case 15: {
            
            return "accept all cookies?";
        }
        case 16: {
            
            return "ALT F4";
        }
        case 17: {
            
            return "Share Location With YourBestFrend?";
        }
        case 18: {
            
            return "Add in 3s";
        }
        case 19: {
            
            return "int x = 3.14;";
        }
        case 20: {
            
            return "if (a = 1)";
        }
        case 21: {
            
            return "if (stringA == stringB)";
        }
        case 22: {
            
            return "array[-1];";
        }
        case 23: {
            
            return "for (int i=1; i > 0; i++)";
        }
        case 24: {
            
            return "while (true)";
        }
        case 25: {
            
            return "NULL";
        }
        case 26: {
            
            return "byte b = 468;";
        }
        case 27: {
            
            return "j = i++;";
        }
        case 28: {
            
            return "if (n == 1.765)";
        }
        case 29: {
            
            return "private static void main() {";
        }
        case 30: {
            
            return "public static final int PI = 3;";
        }
        case 31: {
            
            return "if (n / 2 == 2.5)";
        }
        case 32: {
            
            return "GAME OVER";
        }
        case 33: {
            
            return "DOWNLOAD NOW";
        }
        case 34: {
            
            return "I AM WATCHING YOU";
        }
        case 35: {
            
            return "Allow apps acces to your camera";
        }
        case 36: {
            
            return "Debugung Required";
        }
        case 37: {
            
            return "classA extends classB, classC";
        }
        case 38: {
            
            return "     ";
        }
        case 39: {
            
            return "Battery: 3%";
        }
        case 40: {
            
            return "Error Code 543";
        }
        case 41: {
            
            return "int i = null;";
        }
        case 42: {
            
            return "int i = null;";
        }
        case 43: {
            
            return "long array[][] = new long[678708][908755];";
        }
        case 44: {
            
            return "MyCoolApp is not responding";
        }
        case 45: {
            
            return "ERR_INTERNET_DISCONNECTED";
        }
        case 46: {
            
            return "Internet";
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + random);
        }
        
    }
        
    
    /**
     * Gibt einen der 24 Bilder zufällig züruck
     * @return ImageIcon
     */
    public static ImageIcon getImage() {
        
        Random random = new Random();
        
        String path = "../images/";
        
        switch (random.nextInt(25)) {
        
        case 0: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "wifi.png"));
        }
        case 1: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "nowifi.png"));
        }
        case 2: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "brief.png"));
        }
        case 3: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "totenkopf.png"));
        }
        case 4: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "x.png"));
        }
        case 5: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "virus.png"));
        }
        case 6: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "play.png"));
        }
        case 7: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "cookie.png"));
        }
        case 8: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "file.png"));
        }
        case 9: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "nofile.png"));
        }
        case 10: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "directory.png"));
        }
        case 11: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "bug.png"));
        }
        case 12: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "chip.png"));
        }
        case 13: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "cloud.png"));
        }
        case 14: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "computer.png"));
        }
        case 15: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "error.png"));
        }
        case 16: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "hacker.png"));
        }
        case 17: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "maleware.png"));
        }
        case 18: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "nointernet.png"));
        }
        case 19: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "ram.png"));
        }
        case 20: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "screenShare.png"));
        }
        case 21: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "stop.png"));
        }
        case 22: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "mouse.png"));
        }
        case 23: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "news.png"));
        }
        case 24: {
            
            return new ImageIcon(ObjectCollection.class.getResource(path + "www.png"));
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + random);
        }
    }
}
