package ezgin.src.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import static ezgin.src.utils.Constants.GameConstants.*;
import static ezgin.src.utils.Constants.EntitySpriteConstants.*;

/**
 * Klasse für das Laden von Bildern
 */
public class Load {

    /**
     * lädt ein Bild
     *
     * @param fileName Name des Bildes
     * @return BufferedImage (Bild)
     */
    public static BufferedImage getImageFile(String fileName) {
        BufferedImage img = null;
        InputStream is = Load.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    /**
     * lädt die Sprites eines Entities
     *
     * @param name Name der Entity-Datei
     * @return return BufferedImage[][] (2D-Array)
     */
    public static BufferedImage[][] getEntitySprites(String name) {
        BufferedImage image = getImageFile("ezgin/res/ingame/entities/" + name);
        BufferedImage[][] imageArr = new BufferedImage[getSpriteCountHeight(name)][getSpriteCountWidth(name)];
        for (int i = 0; i < imageArr.length; i++) {
            for (int j = 0; j < imageArr[i].length; j++) {
                imageArr[i][j] = image.getSubimage(j * getEntityWidth(name) / SCALE, i * getEntityHeight(name) / SCALE, getEntityWidth(name) / SCALE, getEntityHeight(name) / SCALE);
            }
        }
        return imageArr;
    }

    /**
     * lädt die Level-Kollisionsdaten
     *
     * @param img Level-Daten-Datei
     * @return int[][] (2D-Array)
     */
    public static int[][] getLevelData(BufferedImage img) {
        int[][] lvlWall = new int[getDefaultWorldWidth()][getDefaultWorldHeight()];
        for (int i = 0; i < getDefaultWorldWidth(); i++) {
            for (int j = 0; j < getDefaultWorldHeight(); j++) {
                Color color = new Color(img.getRGB(i, j));
                lvlWall[i][j] = color.getRed();
            }
        }
        return lvlWall;
    }

    /**
     * lädt die Bilder der UI
     *
     * @param imageName Name des Bildes
     * @return BufferedImage[]
     */
    public static BufferedImage[] getUiImages(String imageName) {
        BufferedImage image = getImageFile("ezgin/res/ingame/ui/" + imageName);
        BufferedImage[] imageArr = new BufferedImage[image.getWidth() / 32];
        for (int i = 0; i < imageArr.length; i++) {
            imageArr[i] = image.getSubimage(i * 32, 0, 32, 32);
        }
        return imageArr;
    }

    /**
     * lädt die Audiodatei
     *
     * @param audioName Name der Audiodatei
     * @return Clip
     */
    public static Clip getAudioClip(String audioName) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(Load.class.getResource("/ezgin/res/audios/" + audioName))));
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * lädt die Schriftart
     *
     * @return Font
     */
    public static Font getFont() {
        try {
            Font load = Font.createFont(0, new java.io.File("GameCollection/src/ezgin/res/VCRosdNEUE.ttf"));
            return load.deriveFont(6f * SCALE);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * liest aus einem Textfile die Texte der Stelen
     *
     * @return String[][][] (3D-Array)
     */
    public static String[][][] getLevelText() {
        // Liste von Level
        ArrayList<ArrayList<ArrayList<String>>> levels = new ArrayList<>();
        // Liste von Absätzen
        ArrayList<ArrayList<String>> currentLevel = new ArrayList<>();
        // Liste von Zeilen
        ArrayList<String> currentParagraph = new ArrayList<>();

        InputStream is = Load.class.getResourceAsStream("/ezgin/res/ingame/levelText.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            // Lese die Textdatei Zeile für Zeile
            while ((line = reader.readLine()) != null) {
                // Wenn eine neue Zeile mit \p beginnt, füge den aktuellen Absatz dem aktuellen Level hinzu
                if (line.startsWith("\\p") || line.startsWith("\\l")) {
                    line = line.replace("\\p", "");
                    currentLevel.add(currentParagraph);
                    currentParagraph = new ArrayList<>();
                }
                // Wenn eine neue Zeile mit \l beginnt, füge den aktuellen Level der Liste der Level hinzu
                if (line.startsWith("\\l")) {
                    line = line.replace("\\l", "");
                    levels.add(currentLevel);
                    currentLevel = new ArrayList<>();
                }
                // Wenn die Zeile nicht leer ist, füge sie dem aktuellen Absatz hinzu
                if (!line.isEmpty()) {
                    line = line.replace("\\n", "");
                    currentParagraph.add(line);
                }
            }
            // Füge den letzten Absatz und das letzte Level hinzu
            currentLevel.add(currentParagraph);
            levels.add(currentLevel);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Konvertiere die verschachtelte Liste in ein 3D-Array
        String[][][] levelsArray = new String[levels.size()][][];
        for (int i = 0; i < levels.size(); i++) {
            ArrayList<ArrayList<String>> level = levels.get(i);
            levelsArray[i] = new String[level.size()][];
            for (int j = 0; j < level.size(); j++) {
                levelsArray[i][j] = level.get(j).toArray(new String[0]);
            }
        }
        return levelsArray;
    }

    /**
     * liest aus einem Textfile die Texte der Credits
     *
     * @return String[]
     */
    public static String[] getCredits() {
        ArrayList<String> credits = new ArrayList<>();

        InputStream is = Load.class.getResourceAsStream("/ezgin/res/credits/credits.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            // Lese die Textdatei Zeile für Zeile
            while ((line = reader.readLine()) != null) {
                credits.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Konvertiere die Liste in ein Array
        return credits.toArray(new String[0]);
    }
}