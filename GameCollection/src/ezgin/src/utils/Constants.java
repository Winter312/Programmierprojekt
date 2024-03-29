package ezgin.src.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.sound.sampled.Clip;

import ezgin.src.main.entities.EntityHandler;
import ezgin.src.main.enums.GameState;

import static ezgin.src.main.enums.Level.LEVEL_THREE;
import static ezgin.src.main.enums.Level.getCurrentLevel;
import static ezgin.src.main.enums.TutorialState.getCurrentTutorialState;
import static ezgin.src.utils.Constants.GameConstants.*;
import static ezgin.src.utils.Constants.ImageConstants.*;
import static ezgin.src.utils.Load.getImageFile;

/**
 * Klasse für die Konstanten
 */
public class Constants {

    /**
     * Klasse für die Konstanten des Bildschirms, der Welt und des Spiels
     */
    public static class GameConstants {

        // Bildschirmeinstellungen
        public static final int TILE_SIZE_DEFAULT = 32; // 32x32
        public static final int SCALE = 6;
        public static final int TILE_SIZE = TILE_SIZE_DEFAULT * SCALE; // 128x128
        public static final int MAX_SCREEN_COL = 9;
        public static final int MAX_SCREEN_ROW = 5;
        public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 1152 pixel
        public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 640 pixel
        public static final int SCREEN_X_DEFAULT = (SCREEN_WIDTH - TILE_SIZE) / 2;
        public static final int SCREEN_Y_DEFAULT = (SCREEN_HEIGHT - TILE_SIZE) / 2;

        // Welteinstellungen
        public static int getWorldWidth() {
            return getMaxWorldCol() * TILE_SIZE;
        }

        public static int getWorldHeight() {
            return getMaxWorldRow() * TILE_SIZE;
        }

        public static int getDefaultWorldWidth() {
            return getMaxWorldCol() * TILE_SIZE_DEFAULT;
        }

        public static int getDefaultWorldHeight() {
            return getMaxWorldRow() * TILE_SIZE_DEFAULT;
        }

        /**
         * gibt die maximale Anzahl an Spalten der Welt zurück
         *
         * @return Anzahl der Spalten
         */
        public static int getMaxWorldCol() {
            return switch (getCurrentLevel()) {
                case LEVEL_ZERO -> 9;
                case LEVEL_ONE -> 50;
                case LEVEL_TWO -> 80;
                case LEVEL_THREE -> 60;
                case LEVEL_CASTLE -> 40;
            };
        }

        /**
         * gibt die maximale Anzahl an Reihen der Welt zurück
         *
         * @return Anzahl der Reihen
         */
        public static int getMaxWorldRow() {
            return switch (getCurrentLevel()) {
                case LEVEL_ZERO -> 30;
                case LEVEL_ONE -> 25;
                case LEVEL_TWO -> 32;
                case LEVEL_THREE, LEVEL_CASTLE -> 40;
            };
        }

        // Spieleinstellungen
        public static final int FPS = 144;
        public static final int UPS = 144;

        // Musik
        public static final Clip MUSIC_MENU = Load.getAudioClip("menu.wav");
        public static final Clip MUSIC_GAME = Load.getAudioClip("inGame.wav");
        public static final Clip MUSIC_CASTLE = Load.getAudioClip("castle.wav");
    }

    /**
     * Klasse für die Spawn-Konstanten
     */
    public static class SpawnConstants {

        /**
         * gibt die Anzahl der Entitäten, abhängig vom Level zurück
         *
         * @param name Name der Entität
         * @return Anzahl der Entitäten
         */
        public static int getEntityCount(String name) {
            switch (name) {
                case PA_CHECKPOINT:
                case PA_STELA:
                    return switch (getCurrentLevel()) {
                        case LEVEL_ZERO, LEVEL_THREE, LEVEL_ONE, LEVEL_TWO -> 3;
                        default -> 0;
                    };
                case PA_CHEST:
                    return switch (getCurrentLevel()) {
                        case LEVEL_ZERO -> 1;
                        case LEVEL_ONE, LEVEL_THREE -> 2;
                        case LEVEL_TWO -> 3;
                        case LEVEL_CASTLE -> 0;
                    };
                case PA_ENEMY:
                    return switch (getCurrentLevel()) {
                        case LEVEL_ZERO -> 1;
                        case LEVEL_ONE -> 5;
                        case LEVEL_TWO -> 10;
                        case LEVEL_THREE -> 15;
                        case LEVEL_CASTLE -> 30;
                    };
                case PA_DOOR:
                    return switch (getCurrentLevel()) {
                        case LEVEL_THREE -> 2;
                        case LEVEL_CASTLE -> 1;
                        default -> 0;
                    };
                case PA_LUMINA:
                    return switch (getCurrentLevel()) {
                        case LEVEL_ONE, LEVEL_TWO, LEVEL_THREE -> 1;
                        default -> 0;
                    };
                default:
                    return 0;
            }
        }

        /**
         * gibt die Spawn-Position des Spielers zurück
         *
         * @return Spawn-Position des Spielers
         */
        public static float[] getPlayerSpawn() {
            return switch (getCurrentLevel()) {
                case LEVEL_ZERO -> new float[]{1.5f * TILE_SIZE, 27.5f * TILE_SIZE};
                case LEVEL_ONE -> new float[]{1.5f * TILE_SIZE, 22.5f * TILE_SIZE};
                case LEVEL_TWO -> new float[]{39.5f * TILE_SIZE, 28.5f * TILE_SIZE};
                case LEVEL_THREE -> new float[]{58f * TILE_SIZE, 38f * TILE_SIZE};
                default -> new float[]{19.5f * TILE_SIZE, 38.5f * TILE_SIZE};
            };
        }

        /**
         * gibt die Spawn-Positionen der Gegner zurück
         *
         * @return Spawn-Positionen der Gegner
         */
        public static float[][] getEnemySpawn() {
            return switch (getCurrentLevel()) {
                case LEVEL_ZERO -> new float[][]{{4f * TILE_SIZE, 6f * TILE_SIZE}};
                case LEVEL_ONE ->
                        new float[][]{{15 * TILE_SIZE, 13 * TILE_SIZE}, {33 * TILE_SIZE, 13 * TILE_SIZE}, {36 * TILE_SIZE, 15 * TILE_SIZE}, {43 * TILE_SIZE, 14 * TILE_SIZE}, {47 * TILE_SIZE, 14 * TILE_SIZE}};
                case LEVEL_TWO ->
                        new float[][]{{6 * TILE_SIZE, 21 * TILE_SIZE}, {70 * TILE_SIZE, 25 * TILE_SIZE}, {7 * TILE_SIZE, 11 * TILE_SIZE}, {72 * TILE_SIZE, 16 * TILE_SIZE}, {19 * TILE_SIZE, 18 * TILE_SIZE}, {60 * TILE_SIZE, 17 * TILE_SIZE}, {71 * TILE_SIZE, 4 * TILE_SIZE}, {8 * TILE_SIZE, 5 * TILE_SIZE}, {49 * TILE_SIZE, 7 * TILE_SIZE}, {30 * TILE_SIZE, 7 * TILE_SIZE}};
                case LEVEL_THREE ->
                        new float[][]{{50 * TILE_SIZE, 16 * TILE_SIZE}, {49 * TILE_SIZE, 24 * TILE_SIZE}, {45 * TILE_SIZE, 17 * TILE_SIZE}, {41 * TILE_SIZE, 10 * TILE_SIZE}, {34 * TILE_SIZE, 12 * TILE_SIZE}, {35 * TILE_SIZE, 17 * TILE_SIZE}, {24 * TILE_SIZE, 22 * TILE_SIZE}, {22 * TILE_SIZE, 15 * TILE_SIZE}, {22.7f * TILE_SIZE, 5 * TILE_SIZE}, {25 * TILE_SIZE, 18 * TILE_SIZE}, {17 * TILE_SIZE, 35 * TILE_SIZE}, {9 * TILE_SIZE, 31 * TILE_SIZE}, {8 * TILE_SIZE, 28 * TILE_SIZE}, {15 * TILE_SIZE, 21 * TILE_SIZE}, {21 * TILE_SIZE, 35 * TILE_SIZE}};
                default ->
                        new float[][]{{9 * TILE_SIZE, 31 * TILE_SIZE}, {14 * TILE_SIZE, 31 * TILE_SIZE}, {19 * TILE_SIZE, 31 * TILE_SIZE}, {25 * TILE_SIZE, 31 * TILE_SIZE}, {30 * TILE_SIZE, 31 * TILE_SIZE}, {26 * TILE_SIZE, 22 * TILE_SIZE}, {27 * TILE_SIZE, 22 * TILE_SIZE}, {16 * TILE_SIZE, 22 * TILE_SIZE}, {19 * TILE_SIZE, 22 * TILE_SIZE}, {4 * TILE_SIZE, 18 * TILE_SIZE}, {6 * TILE_SIZE, 21 * TILE_SIZE}, {8 * TILE_SIZE, 18 * TILE_SIZE}, {33 * TILE_SIZE, 13 * TILE_SIZE}, {35 * TILE_SIZE, 14 * TILE_SIZE}, {18 * TILE_SIZE, 7 * TILE_SIZE},
                                {22 * TILE_SIZE, 5 * TILE_SIZE}, {23 * TILE_SIZE, 6 * TILE_SIZE}, {26 * TILE_SIZE, 6 * TILE_SIZE}, {22 * TILE_SIZE, 8 * TILE_SIZE}, {19 * TILE_SIZE, 8 * TILE_SIZE}, {23 * TILE_SIZE, 4 * TILE_SIZE}, {20 * TILE_SIZE, 9 * TILE_SIZE}, {26 * TILE_SIZE, 4 * TILE_SIZE}, {8 * TILE_SIZE, 33 * TILE_SIZE}, {16 * TILE_SIZE, 30 * TILE_SIZE}, {28 * TILE_SIZE, 34 * TILE_SIZE}, {31 * TILE_SIZE, 28 * TILE_SIZE}, {21 * TILE_SIZE, 28 * TILE_SIZE}, {14 * TILE_SIZE, 28 * TILE_SIZE}, {12 * TILE_SIZE, 17 * TILE_SIZE}};
            };
        }

        /**
         * gibt die Spawn-Positionen der CheckPoints zurück
         *
         * @param checkPoint CheckPoint-ID
         * @return Spawn-Positionen der CheckPoints
         */
        public static float[] getCheckPointSpawn(int checkPoint) {
            switch (getCurrentLevel()) {
                case LEVEL_ZERO:
                    return switch (checkPoint) {
                        default -> new float[]{TILE_SIZE, 20 * TILE_SIZE};
                        case 1 -> new float[]{TILE_SIZE, TILE_SIZE};
                        case 2 -> new float[]{5 * TILE_SIZE, TILE_SIZE};
                    };
                case LEVEL_ONE:
                default:
                    return switch (checkPoint) {
                        default -> new float[]{5 * TILE_SIZE, 7 * TILE_SIZE};
                        case 1 -> new float[]{18 * TILE_SIZE, 10 * TILE_SIZE};
                        case 2 -> new float[]{44 * TILE_SIZE, 9 * TILE_SIZE};
                    };
                case LEVEL_TWO:
                    return switch (checkPoint) {
                        default -> new float[]{18 * TILE_SIZE, 9 * TILE_SIZE};
                        case 1 -> new float[]{39 * TILE_SIZE, TILE_SIZE};
                        case 2 -> new float[]{75 * TILE_SIZE, 16 * TILE_SIZE};
                    };
                case LEVEL_THREE:
                    return switch (checkPoint) {
                        default -> new float[]{53 * TILE_SIZE, 24 * TILE_SIZE};
                        case 1 -> new float[]{TILE_SIZE, 31.25f * TILE_SIZE};
                        case 2 -> new float[]{32 * TILE_SIZE, 2.25f * TILE_SIZE};
                    };
            }
        }

        /**
         * gibt die Spawn-Positionen der Truhen zurück
         *
         * @param chest Truhen-ID
         * @return Spawn-Positionen der Truhen
         */
        public static float[] getChestSpawn(int chest) {
            return switch (getCurrentLevel()) {
                case LEVEL_ZERO -> new float[]{7 * TILE_SIZE, 27 * TILE_SIZE};
                default -> switch (chest) {
                    default -> new float[]{TILE_SIZE / 4f, 15 * TILE_SIZE};
                    case 1 -> new float[]{27 * TILE_SIZE, 0};
                };
                case LEVEL_TWO -> switch (chest) {
                    default -> new float[]{60 * TILE_SIZE, 9 * TILE_SIZE};
                    case 1 -> new float[]{11.8f * TILE_SIZE, 8 * TILE_SIZE};
                    case 2 -> new float[]{TILE_SIZE, 8 * TILE_SIZE};
                };
                case LEVEL_THREE -> switch (chest) {
                    default -> new float[]{5.5f * TILE_SIZE, 2 * TILE_SIZE};
                    case 1 -> new float[]{56 * TILE_SIZE, 2 * TILE_SIZE};
                };
            };
        }

        /**
         * gibt die Spawn-Positionen der Stelen zurück
         *
         * @param stela Stelen-ID
         * @return Spawn-Positionen der Stelen
         */
        public static float[] getStelaSpawn(int stela) {
            return switch (getCurrentLevel()) {
                case LEVEL_ZERO -> switch (stela) {
                    default -> new float[]{4 * TILE_SIZE, 18 * TILE_SIZE};
                    case 1 -> new float[]{0, 0};
                    case 2 -> new float[]{8 * TILE_SIZE, 0};
                };
                default -> switch (stela) {
                    default -> new float[]{6 * TILE_SIZE, 4 * TILE_SIZE};
                    case 1 -> new float[]{17 * TILE_SIZE, 9 * TILE_SIZE};
                    case 2 -> new float[]{43 * TILE_SIZE, 8 * TILE_SIZE};
                };
                case LEVEL_TWO -> switch (stela) {
                    default -> new float[]{19 * TILE_SIZE, 10 * TILE_SIZE};
                    case 1 -> new float[]{38 * TILE_SIZE, 0};
                    case 2 -> new float[]{78 * TILE_SIZE, 15 * TILE_SIZE};
                };
                case LEVEL_THREE -> switch (stela) {
                    default -> new float[]{56 * TILE_SIZE, 22 * TILE_SIZE};
                    case 1 -> new float[]{4 * TILE_SIZE, 28 * TILE_SIZE};
                    case 2 -> new float[]{29 * TILE_SIZE, TILE_SIZE};
                };
            };
        }

        /**
         * gibt die Spawn-Positionen der Türen zurück
         *
         * @param door Tür-ID
         * @return Spawn-Positionen der Türen
         */
        public static float[] getDoorSpawn(int door) {
            switch (getCurrentLevel()) {
                case LEVEL_THREE -> {
                    if (door == 1) {
                        return new float[]{4.5f * TILE_SIZE, 20 * TILE_SIZE};
                    }
                    return new float[]{50f * TILE_SIZE, TILE_SIZE};
                }
                case LEVEL_CASTLE -> {
                    return new float[]{4.5f * TILE_SIZE, 2 * TILE_SIZE};
                }
                default -> {
                    return new float[]{0, 0};
                }
            }
        }

        /**
         * gibt die freizuschaltenden Pixel der Tür zurück
         *
         * @param door Tür-ID
         * @return freizuschaltende Pixel der Tür
         */
        public static int[] getDoorPos(int door) {
            if (getCurrentLevel() == LEVEL_THREE) {
                if (door == 1) {
                    return new int[]{176, 698};
                }
                return new int[]{1632, 95};
            }
            return new int[]{176, 127};
        }

        /**
         * gibt die Spawn-Positionen der Lumina-Statue zurück
         *
         * @return Spawn-Positionen der Lumina-Statue
         */
        public static float[] getLuminaSpawn() {
            return switch (getCurrentLevel()) {
                case LEVEL_ONE -> new float[]{19 * TILE_SIZE, 17 * TILE_SIZE};
                case LEVEL_TWO -> new float[]{27 * TILE_SIZE, 12 * TILE_SIZE};
                default -> new float[]{32 * TILE_SIZE, 32 * TILE_SIZE};
            };
        }
    }

    /**
     * Klasse für die Sprites der Entities
     */
    public static class EntitySpriteConstants {
        public static final int RUN_UP = 0;
        public static final int RUN_LEFT = 1;
        public static final int RUN_DOWN = 2;
        public static final int RUN_RIGHT = 3;
        public static final int ATTACK_UP = 4;
        public static final int ATTACK_LEFT = 5;
        public static final int ATTACK_DOWN = 6;
        public static final int ATTACK_RIGHT = 7;
        public static final int DEATH = 8;

        /**
         * gibt die Anzahl der Sprites der Entität zurück
         *
         * @param id        ID der Entität
         * @param spriteRow Reihe der Sprites
         * @return Anzahl der Sprites der aktuellen spriteRow
         */
        public static int getSpriteCount(int id, int spriteRow) {
            if (id == -1) {
                return getPlayerSpriteCount(spriteRow);
            }
            return getEnemySpriteCount(spriteRow);
        }

        /**
         * gibt die Anzahl der Spieler-Sprites der spriteRow zurück
         *
         * @param spriteRow Reihe der Sprites
         * @return Anzahl der Sprites
         */
        public static int getPlayerSpriteCount(int spriteRow) {
            return switch (spriteRow) {
                case RUN_UP, RUN_LEFT, RUN_DOWN, RUN_RIGHT -> 9;
                case ATTACK_UP, ATTACK_LEFT, ATTACK_DOWN, ATTACK_RIGHT, DEATH -> 6;
                default -> 0;
            };
        }

        /**
         * gibt die Anzahl der Gegner-Sprites der spriteRow zurück
         *
         * @param spriteRow Reihe der Sprites
         * @return Anzahl der Sprites
         */
        private static int getEnemySpriteCount(int spriteRow) {
            return switch (spriteRow) {
                case RUN_UP, RUN_LEFT, RUN_DOWN, RUN_RIGHT -> 9;
                case ATTACK_UP, ATTACK_LEFT, ATTACK_DOWN, ATTACK_RIGHT -> 8;
                case DEATH -> 6;
                default -> 0;
            };
        }

        /**
         * Gibt die Anzahl der Sprites in der Breite zurück
         *
         * @param name Name der Entität
         * @return Anzahl der Sprites in der Breite
         */
        public static int getSpriteCountWidth(String name) {
            if (name.contains("player") || name.equals(PA_ENEMY)) {
                return 9;
            }
            return switch (name) {
                case PA_CHECKPOINT, PA_STELA -> 5;
                case PA_LUMINA -> 4;
                default -> 2;
            };
        }

        /**
         * Gibt die Anzahl der Sprites in der Höhe zurück
         *
         * @param name Name der Entität
         * @return Anzahl der Sprites in der Höhe
         */
        public static int getSpriteCountHeight(String name) {
            if (name.contains("player") || name.equals(PA_ENEMY)) {
                return 9;
            } else if (name.equals(PA_STELA)) {
                return 3;
            }
            return 1;
        }

        /**
         * Gibt die Breite des Sprites zurück
         *
         * @param name Name der Entität
         * @return Breite des Sprites
         */
        public static int getEntityWidth(String name) {
            if (name.contains("player") || name.equals(PA_ENEMY)) {
                return 64 * SCALE;
            }
            return switch (name) {
                case PA_CHECKPOINT -> 4 * TILE_SIZE;
                case PA_DOOR, PA_DOOR_2, PA_LUMINA -> 3 * TILE_SIZE;
                case PA_STELA, PA_CHEST -> 2 * TILE_SIZE;
                default -> TILE_SIZE;
            };
        }

        /**
         * Gibt die Höhe des Sprites zurück
         *
         * @param name Name der Entität
         * @return Höhe des Sprites
         */
        public static int getEntityHeight(String name) {
            if (name.contains("player") || name.equals(PA_ENEMY)) {
                return 64 * SCALE;
            }
            return switch (name) {
                case PA_CHECKPOINT, PA_STELA, PA_LUMINA -> 3 * TILE_SIZE;
                case PA_CHEST, PA_DOOR, PA_DOOR_2 -> 2 * TILE_SIZE;
                default -> TILE_SIZE;
            };
        }

        /**
         * Gibt die Sound-Clips der Entität zurück
         *
         * @param name Name der Entität
         * @return Sound-Clips der Entität
         */
        public static Clip[] getEntityClip(String name) {
            return switch (name) {
                case PA_CHECKPOINT -> new Clip[]{Load.getAudioClip("checkpoint.wav")};
                case PA_STELA -> new Clip[]{Load.getAudioClip("stela.wav")};
                case PA_LUMINA -> new Clip[]{Load.getAudioClip("lumina.wav")};
                default -> new Clip[]{Load.getAudioClip("chest1.wav"), Load.getAudioClip("chest2.wav")};
            };
        }
    }

    /**
     * Klasse für die Ressourcen
     */
    public static class ImageConstants {

        // Pfad zu den Bildern der Entities

        /**
         * gibt den Pfad des Bildes des Spielers zurück
         *
         * @param state Zustand des Spielers
         * @return Pfad des Bildes des Spielers
         */
        public static String getPlayerPath(int state) {
            return switch (state) {
                default -> "player_default.png";
                case 1 -> "player_dagger.png";
                case 2 -> "player_shield.png";
                case 3 -> "player_dagger_shield.png";
            };
        }

        public static final String PA_ENEMY = "enemy.png";
        public static final String PA_CHECKPOINT = "checkpoint.png";
        public static final String PA_STELA = "stela.png";
        public static final String PA_CHEST = "chest.png";
        public static final String PA_DOOR = "door.png";
        public static final String PA_DOOR_2 = "door_2.png";
        public static final String PA_LUMINA = "lumina.png";

        /**
         * gibt den Pfad des Bildes der Tür zurück
         *
         * @param id Tür-ID
         * @return Pfad des Bildes der Tür
         */
        public static String getDoor(int id) {
            return switch (id) {
                case 0 -> PA_DOOR_2;
                case 1 -> PA_DOOR;
                default -> "";
            };
        }

        // Bilder der Welt (Layer 1 und 2 und LevelData)
        public static final BufferedImage[] BI_LEVEL_ZERO = {getImageFile("ezgin/res/ingame/maps/level_0_layer_1.png"), getImageFile("ezgin/res/ingame/maps/level_0_layer_2.png"), getImageFile("ezgin/res/ingame/maps/level_0_data.png")};
        public static final BufferedImage[] BI_LEVEL_ONE = {getImageFile("ezgin/res/ingame/maps/level_1_layer_1.png"), getImageFile("ezgin/res/ingame/maps/level_1_layer_2.png"), getImageFile("ezgin/res/ingame/maps/level_1_data.png")};
        public static final BufferedImage[] BI_LEVEL_TWO = {getImageFile("ezgin/res/ingame/maps/level_2_layer_1.png"), getImageFile("ezgin/res/ingame/maps/level_2_layer_2.png"), getImageFile("ezgin/res/ingame/maps/level_2_data.png")};
        public static final BufferedImage[] BI_LEVEL_THREE = {getImageFile("ezgin/res/ingame/maps/level_3_layer_1.png"), getImageFile("ezgin/res/ingame/maps/level_3_layer_2.png"), getImageFile("ezgin/res/ingame/maps/level_3_data.png")};
        public static final BufferedImage[] BI_LEVEL_CASTLE = {getImageFile("ezgin/res/ingame/maps/level_castle_layer_1.png"), getImageFile("ezgin/res/ingame/maps/level_castle_layer_2.png"), getImageFile("ezgin/res/ingame/maps/level_castle_data.png")};


        // Bilder der Items
        public static final BufferedImage BI_DAGGER = getImageFile("ezgin/res/ingame/ui/dagger.png");
        public static final BufferedImage BI_RUNE_STONE = getImageFile("ezgin/res/ingame/ui/runestone.png");
        public static final BufferedImage BI_SHIELD = getImageFile("ezgin/res/ingame/ui/shield.png");
        public static final BufferedImage BI_KEY = getImageFile("ezgin/res/ingame/ui/key.png");
        public static final BufferedImage BI_CRYSTAL = getImageFile("ezgin/res/ingame/ui/crystal.png");
        public static final BufferedImage BI_OBSIDIAN = getImageFile("ezgin/res/ingame/ui/obsidian.png");

        // Bilder der Buttons
        public static final BufferedImage BI_BUTTON = getImageFile("ezgin/res/buttons/button.png");
        public static final BufferedImage BI_MINI_BUTTON = getImageFile("ezgin/res/buttons/mini_button.png");
        public static final BufferedImage BI_SWITCH = getImageFile("ezgin/res/buttons/switch.png");
        public static final BufferedImage BI_SELECTED = getImageFile("ezgin/res/buttons/selection.png");
        public static final BufferedImage BI_MINI_SELECTED = getImageFile("ezgin/res/buttons/mini_selection.png");
        public static final BufferedImage[] BI_PAUSE_BUTTONS = {getImageFile("ezgin/res/pause/play.png"), getImageFile("ezgin/res/pause/settings.png"), getImageFile("ezgin/res/pause/menu.png")};

        // Bilder der Tasten
        public static final BufferedImage BI_MOUSE = getImageFile("ezgin/res/ingame/ui/mouse.png");
        public static final BufferedImage BI_ENTER = getImageFile("ezgin/res/ingame/ui/enter.png");
        public static final BufferedImage BI_TAB = getImageFile("ezgin/res/ingame/ui/tab.png");
        public static final BufferedImage BI_SHIFT = getImageFile("ezgin/res/ingame/ui/shift.png");

        // Hintergrundbilder
        public static final BufferedImage BI_MENU_BACKGROUND = getImageFile("ezgin/res/menu/background.png");
        public static final BufferedImage BI_SETTINGS_BACKGROUND = getImageFile("ezgin/res/settings/background.png");
        public static final BufferedImage BI_CREDITS_BACKGROUND = getImageFile("ezgin/res/credits/background.png");
        public static final BufferedImage BI_PAUSE_BACKGROUND = getImageFile("ezgin/res/pause/background.png");
        public static final BufferedImage BI_GAME_OVER_BACKGROUND = getImageFile("ezgin/res/game_over/background.png");
        public static final BufferedImage BI_SWITCH_BACKGROUND = getImageFile("ezgin/res/buttons/switch_background.png");
    }

    /**
     * Klasse für die UI-Konstanten
     */
    public static class UIConstants {
        // Scaling der UI
        public static final int MENU_TILE_SIZE = 8 * SCALE;

        // Konstanten für die Buttons
        public static final int BUTTON_WIDTH = MENU_TILE_SIZE * 8;
        public static final int BUTTON_HEIGHT = MENU_TILE_SIZE * 2;
        public static final int SETTINGS_WIDTH = MENU_TILE_SIZE * 10;
        public static final int SETTINGS_HEIGHT = MENU_TILE_SIZE * 11;
        public static final int CREDITS_WIDTH = MENU_TILE_SIZE * 24;
        public static final int CREDITS_HEIGHT = MENU_TILE_SIZE * 15;

        /**
         * gibt die Position der Buttons des Einstellungsmenüs zurück
         *
         * @return Position der Buttons des Einstellungsmenüs
         */
        public static int[] settingsValues() {
            if (GameState.getPreviousState() == GameState.PAUSE) {
                return new int[]{5 * MENU_TILE_SIZE, (int) (4.5 * MENU_TILE_SIZE)};
            }
            return new int[]{10 * MENU_TILE_SIZE, 6 * MENU_TILE_SIZE};
        }

        public final static int[] CREDITS_VALUES = new int[]{7 * MENU_TILE_SIZE, SCREEN_HEIGHT / 2 - CREDITS_HEIGHT / 2};

        // Konstanten für die Inventar-Positionierung
        public static final int INVENTORY_WIDTH = (int) (MENU_TILE_SIZE * 2.25f);
        public static final int INVENTORY_HEIGHT = MENU_TILE_SIZE * 12;
        public static final int INVENTORY_Y = SCREEN_HEIGHT / 2 - INVENTORY_HEIGHT / 2 - MENU_TILE_SIZE;
        public static final int INVENTORY_X = SCREEN_WIDTH - INVENTORY_WIDTH - MENU_TILE_SIZE / 2;

        // Konstanten für die Item-Positionierung
        public static final int IMG_WIDTH = (int) (MENU_TILE_SIZE * 1.5);
        public static final int IMG_HEIGHT = (int) (MENU_TILE_SIZE * 1.5);
        public static final int IMG_X = INVENTORY_X + (INVENTORY_WIDTH - IMG_WIDTH) / 2;
        public static final int ITEM_SPACING = MENU_TILE_SIZE / 2;


        // Konstanten für das Tutorial

        public static final int TUTORIAL_WIDTH = (int) (MENU_TILE_SIZE * 11.5f);
        public static final int TUTORIAL_FIELD_HEIGHT = SCREEN_HEIGHT - MENU_TILE_SIZE * 9;
        public static final int TUTORIAL_FIELD_Y = MENU_TILE_SIZE * 3;
        public static final int TUTORIAL_TEXT_HEIGHT = MENU_TILE_SIZE * 5;

        /**
         * gibt die Y-Position des Textes des Tutorials zurück
         *
         * @return Y-Position des Textes
         */
        public static int getTutorialTextY() {
            return switch (getCurrentTutorialState()) {
                case FIRST_PART, SECOND_PART, FIRST_STELA -> (int) (MENU_TILE_SIZE * 2.5f);
                default -> TUTORIAL_FIELD_Y + TUTORIAL_FIELD_HEIGHT + MENU_TILE_SIZE / 2;
            };
        }

        /**
         * gibt die X-Position des Textes des Tutorials zurück
         *
         * @return X-Position des Textes
         */
        public static int getTutorialX() {
            return switch (getCurrentTutorialState()) {
                case FIRST_CHEST, FIRST_HIT, FIRST_ENEMY, FIRST_LEVEL_END -> MENU_TILE_SIZE / 2;
                case FIRST_STELA ->
                        (int) EntityHandler.getInstance().getStelas()[0].getScreenX() + EntityHandler.getInstance().getStelas()[0].getWidth() / 2;
                case FIRST_ENEMY_KILL -> SCREEN_WIDTH / 2 - TUTORIAL_WIDTH / 2;
                default -> SCREEN_WIDTH - (int) (MENU_TILE_SIZE * 11.5f) - MENU_TILE_SIZE / 2;
            };
        }

        /**
         * gibt die Mitte des Tutorial-Feldes in X-Richtung zurück
         *
         * @return Mitte des Tutorial-Feldes in X-Richtung
         */
        public static int getTutorialCenterX() {
            return getTutorialX() + TUTORIAL_WIDTH / 2;
        }

        public static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 175);

        // Texte für die Entitäten und Level

        /**
         * gibt den Text für die Tür zurück
         *
         * @param door Tür-ID
         * @return Text für die Tür
         */
        public static String[] getDoorText(int door) {
            if (door == 0) {
                if (getCurrentLevel() == LEVEL_THREE) {
                    return new String[]{"Die Tür ist verschlossen.", "Aktiviere alle CheckPoints um in", "die Festung der KI zu gelangen!"};
                }
                return new String[]{"Die Tür ist verschlossen.", "Besiege alle Gegner um die", "Prophezeiung zu erfüllen!"};
            }
            return new String[]{"Die Tür ist verschlossen.", "Für diese Tür brauchst du", "einen Schlüssel."};
        }

        /**
         * gibt den Text für die Lumina-Statue zurück
         *
         * @return Text für die Lumina-Statue
         */
        public static String[] getLuminaText() {
            return switch (getCurrentLevel()) {
                case LEVEL_ONE -> new String[]{"Statue der Lumina", "Schutzgöttin der Celestari"};
                case LEVEL_TWO ->
                        new String[]{"Lumina in den Schatten", "Stilles Mahnmal einer Welt unter der Herrschaft der KI"};
                default -> new String[]{"Heilige Statue von Lumina", "Symbol der Hoffnung und Erleuchtung"};
            };
        }

        /**
         * gibt den Text für das Ende der Level zurück
         *
         * @return Text für das Ende der Level
         */
        public static String getLevelEndText() {
            if (Objects.requireNonNull(getCurrentLevel()) == LEVEL_THREE) {
                return "Finde den Eingang zur Festung";
            }
            return "Finde den Ausgang aus der Festung";
        }

        /**
         * gibt den Text für den aktuellen Tutorial-State zurück
         *
         * @return Text für den aktuellen Tutorial-State
         */
        public static String getTutorialText() {
            return switch (getCurrentTutorialState()) {
                case FIRST_CHEST -> "Lerne deine Items näher kennen";
                case FIRST_HIT, FIRST_ENEMY -> "Führt einen Angriff aus";
                case FIRST_CHECKPOINT -> "Ansicht über CheckPoints";
                default -> "Sprung ins nächste Level";
            };
        }
    }
}