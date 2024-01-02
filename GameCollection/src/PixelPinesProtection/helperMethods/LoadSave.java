package helperMethods;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Methoden zum Laden von Bild- und Audiodateien
 */
public class LoadSave {

    public static BufferedImage getMap1() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("map1.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
    public static BufferedImage getVRed() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("virusRed.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static BufferedImage getVBlue() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("virusBlue.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
    public static BufferedImage getVYellow() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("virusYellow.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
    public static BufferedImage getVPink() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("virusPink.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
    public static BufferedImage getVBoss() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("virusBoss.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static BufferedImage getRocketTow() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("rocketTower.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static BufferedImage getredTow() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("redTower.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static BufferedImage getGreenTow() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("greenTower.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static BufferedImage getRocket() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("rocket.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static BufferedImage getFireSlim() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("fireslim.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static BufferedImage getFireFat() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("firefat.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    /**
     * lädt die hitbox.png in ein array um zu prüfen wo türme platziert werden können.
     * @return gibt das tile array in int zurück
     */
    public static int[][] getLevelData() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("hitbox.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int[][] lvlWall = new int[img.getWidth()][img.getHeight()];
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color color = new Color(img.getRGB(i, j));
                    lvlWall[i][j] = color.getRed();
                }
            }
            return lvlWall;
    }

    public static Clip getAudioClip() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(LoadSave.class.getResource("/music.wav"))));
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
