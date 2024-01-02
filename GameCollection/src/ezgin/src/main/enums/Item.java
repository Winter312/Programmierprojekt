package main.enums;

import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.GameConstants.TILE_SIZE;
import static utils.Constants.ImageConstants.*;

/**
 * Enum für die Items
 * speichert den Namen, die Beschreibung und das Bild des Items
 */
public enum Item {
    DAGGER("Luminas Klinge", "Geschmiedet in den Tagen von Eternavia,\nsymbolisiert Angriff und Mut.\nTrägt das Licht Luminas in sich.\n+3 Angriffsschaden\n+" + (TILE_SIZE + 40 * SCALE / 2) / 2 + " Reichweite", BI_DAGGER),
    LIFE_STONE("Lebensstein der Celestari", "Ein uraltes Symbol der Celestari,\ndurchdrungen von heilenden\nEnergien und einer mystischen\nAura.\n+50 Lebenspunkte", BI_RUNE_STONE),
    SPEED_STONE("Luminas Schnelligkeitssegen", "Eine Rune, gesegnet\nmit Luminas Licht,verleiht\ndem Träger die Geschwindigkeit\nund Anmut der Schutzgöttin.\n+" + 0.25 * SCALE + " Lauftempo", BI_RUNE_STONE),
    SHIELD("Schild der Vergessenen", "Ein mystisches Schild,\ngeformt aus den Erinnerungen der\nvergessenen Celestari-Zivilisation.\nUnzerbrechlich und magisch.\n+2 Schildkraft", BI_SHIELD),
    KEY("Schlüssel der Vergangenheit", "Ein antiker Schlüssel aus\nder Blütezeit von Eternavia,\nöffnet geheimnisvolle\nTüren zu verborgenen\nPfaden", BI_KEY),
    CRYSTAL("Kristall der Hoffnung", "Ein strahlender Kristall, Symbol\nder Hoffnung der Celestari.\nTrägt die pure Macht der\nvergangenen Tage.\n+" + TILE_SIZE / 2 + " Reichweite", BI_CRYSTAL),
    OBSIDIAN("Obsidian der Prophezeiung", "Dunkler Obsidian, durchzogen\nvon der Kraft alter Prophe-\nzeiungen und der Magie von\nEternavia.\n+2 Angriffsschaden", BI_OBSIDIAN);

    private String name;
    private String description;
    private BufferedImage img;
    private boolean acquired;

    Item(String name, String description, BufferedImage img) {
        setName(name);
        setDescription(description);
        setImg(img);
    }


    // GETTER UND SETTER


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BufferedImage getImg() {
        return img;
    }

    public boolean isAcquired() {
        return acquired;
    }

    public void acquire() {
        this.acquired = true;
    }

    public void reset() {
        this.acquired = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
}