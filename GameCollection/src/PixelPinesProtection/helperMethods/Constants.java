package helperMethods;
/**
 * Die Klasse Constants enthält Konstanten und Hilfsmethoden für Projektile, Gegner und Türme.
 */
public class Constants {

    public static class Projectiles {
        public static final int ROCKET = 0;
        public static final int FIRESLIM = 1;
        public static final int FIREFAT = 2;

        public static float getSpeed(int type) {
            switch (type) {
                case ROCKET:
                    return 3f;
                case FIRESLIM:
                    return 8f;
                case FIREFAT:
                    return 4f;
            }
            return 0f;
        }
    }

    public static class Enemies {

        public static final int VRED = 0;
        public static final int VBLUE = 1;
        public static final int VYELLOW = 2;
        public static final int VPINK = 3;
        public static final int VBOSS = 4;


        public static int getReward(int enemyType) {
            return 2;
        }

        public static float getSpeed(int enemyType) {
            switch (enemyType) {
                case VRED:
                    return 0.8f;
                case VBLUE:
                    return 0.7f;
                case VYELLOW:
                    return 0.6f;
                case VPINK:
                    return 0.5f;
                case VBOSS:
                    return 0.5f;
            }
            return 0;
        }

        public static int getStartHealth(int enemyType) {
            switch (enemyType) {
                case VRED:
                    return 70;
                case VBLUE:
                    return 100;
                case VYELLOW:
                    return 150;
                case VPINK:
                    return 300;
                case VBOSS:
                    return 3000;
            }
            return 0;
        }
    }

    public static class Towers {
        public static final int ROCKETLAUNCHER = 0;
        public static final int GREEN = 1;
        public static final int RED = 2;
        public static int getTowerCost(int towerType) {
            switch (towerType) {
                case ROCKETLAUNCHER:
                    return 60;
                case GREEN:
                    return 30;
                case RED:
                    return 40;
            }
            return 0;
        }

        public static String getName(int towerType) {
            switch (towerType) {
                case Projectiles.ROCKET:
                    return "Rocketlauncher";
                case GREEN:
                    return "Green Gun";
                case RED:
                    return "Red Gun";
            }
            return "";
        }
        public static int getStartDmg(int towerType) {
            switch (towerType) {
                case ROCKETLAUNCHER:
                    return 16;
                case GREEN:
                    return 15;
                case RED:
                    return 10;
            }
            return 0;
        }
        public static float getDefaultRange(int towerType) {
            switch (towerType) {
                case ROCKETLAUNCHER:
                    return 115;
                case GREEN:
                    return 130;
                case RED:
                    return 110;
            }
            return 0;
        }
        public static float getDefaultCd(int towerType) {
            switch (towerType) {
                case ROCKETLAUNCHER:
                    return 90;
                case GREEN:
                    return 50;
                case RED:
                    return 26;
            }
            return 0;
        }
    }
}
