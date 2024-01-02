package ezgin.src.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ezgin.src.inputs.KeyboardInputs;
import ezgin.src.inputs.MouseInputs;
import ezgin.src.main.entities.EntityHandler;
import ezgin.src.main.entities.SuperEntity;
import ezgin.src.main.entities.SuperLivingEntity;
import ezgin.src.main.entities.livingentities.Enemy;
import ezgin.src.main.entities.livingentities.Player;
import ezgin.src.main.entities.nonlivingentities.CheckPoint;
import ezgin.src.main.entities.nonlivingentities.Door;
import ezgin.src.main.entities.nonlivingentities.Stela;
import ezgin.src.main.enums.Item;
import ezgin.src.main.enums.TutorialState;
import ezgin.src.main.gamestates.Credits;
import ezgin.src.main.gamestates.GameOver;
import ezgin.src.main.gamestates.InGame;
import ezgin.src.main.gamestates.Menu;
import ezgin.src.main.gamestates.Pause;
import ezgin.src.main.gamestates.Settings;
import ezgin.src.main.gamestates.Win;
import ezgin.src.main.ui.buttons.DefaultButton;
import ezgin.src.main.ui.buttons.MiniButton;
import ezgin.src.main.ui.buttons.SuperButton;
import ezgin.src.main.ui.buttons.Switch;
import ezgin.src.main.ui.hud.LifePoints;
import ezgin.src.utils.Load;

import static ezgin.src.main.enums.GameState.*;
import static ezgin.src.main.enums.Level.*;
import static ezgin.src.main.enums.TutorialState.*;
import static ezgin.src.utils.Constants.EntitySpriteConstants.*;
import static ezgin.src.utils.Constants.GameConstants.*;
import static ezgin.src.utils.Constants.UIConstants.*;
import static ezgin.src.utils.Constants.UIConstants.MENU_TILE_SIZE;

/**
 * GamePanel-Klasse ist für die Darstellung des Spiels zuständig
 * Singleton-Klasse
 */
public class GamePanel extends JPanel {

    private static GamePanel instance; // Singleton-Instanz der Klasse

    // GameState-Objekte
    private InGame inGame = InGame.getInstance();
    private Menu menu = Menu.getInstance();
    private Credits credits = Credits.getInstance();
    private Settings settings = Settings.getInstance();
    private Pause pause = Pause.getInstance();
    private GameOver gameOver = GameOver.getInstance();
    private Win win = Win.getInstance();

    private FontMetrics metrics; // FontMetrics für die Schrift
    private int frames; // Anzahl der Frames pro Sekunde
    private BufferedImage[] heart; // Bilder für die Herzen

    private GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // setzt die Größe des GamePanels
        this.setDoubleBuffered(true); // aktiviert DoubleBuffering (verhindert Flackern)
        this.addKeyListener(KeyboardInputs.getInstance()); // fügt dem GamePanel den KeyHandler hinzu
        this.addMouseListener(MouseInputs.getInstance()); // fügt dem GamePanel den MouseHandler hinzu
        this.addMouseMotionListener(MouseInputs.getInstance()); // fügt dem GamePanel den MouseHandler hinzu
        this.setFocusable(true); // setzt den Fokus auf das GamePanel
        this.setFocusTraversalKeysEnabled(false); // deaktiviert die FocusTraversalKeys
        this.setFont(Load.getFont()); // lädt die Schriftart

        JFrame frame = new JFrame(); // erstellt ein neues JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // die Größe des JFrames ist nicht veränderbar
        frame.setTitle(getTitle()); // Titel des JFrames
        frame.add(this); // fügt dem JFrame das GamePanel hinzu, ähnlich wie ein Bild, dass in einen
                         // Rahmen gelegt wird
        frame.pack(); // passt größe des JFrames an die des GamePanels an
        frame.setLocationRelativeTo(null); // JFrame wird in der Mitte des Bildschirms platziert
        frame.setVisible(true); // JFrame wird sichtbar gemacht

        setInGame(InGame.getInstance());
        setMenu(Menu.getInstance());
        setCredits(Credits.getInstance());
        setSettings(Settings.getInstance());
        setPause(Pause.getInstance());
        setGameOver(GameOver.getInstance());
        setWin(Win.getInstance());
        setHeart(Load.getUiImages("heart.png"));
    }

    /**
     * gibt die Instanz der Klasse zurück
     *
     * @return Instanz der Klasse
     */
    public static GamePanel getInstance() {
        if (instance == null) {
            setInstance(new GamePanel());
        }
        return instance;
    }

    /**
     * zeichnet Informationen
     *
     * @param g Graphics
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // ruft die paintComponent-Methode der Superklasse auf
        g.setColor(Color.WHITE);
        setMetrics(getFontMetrics(getFont())); // lädt die FontMetrics
        switch (getCurrentState()) {
        case MENU:
            drawMenu(g);
            break;
        case SETTINGS:
            if (getPreviousState() == MENU) {
                drawMenu(g);
            } else if (getPreviousState() == PAUSE) {
                drawInGame(g);
            }
            drawSettings(g);
            break;
        case CREDITS:
            if (getPreviousState() == MENU) {
                drawMenu(g);
            } else if (getPreviousState() == WIN) {
                drawInGame(g);
            }
            drawCredits(g);
            break;
        case IN_GAME:
        case PAUSE:
        case GAME_OVER:
        case WIN:
            drawInGame(g);
            break;
        }

        // Zeichnen des Überblendeffekts
        if (InGame.getInstance().isInTransition()) {
            InGame.getInstance().updateTransition(); // Aktualisiert den Alpha-Wert
            Color fadeColor = new Color(0, 0, 0, InGame.getInstance().getAlpha()); // Schwarz mit Alpha-Wert
            g.setColor(fadeColor);
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT); // Überlagert den Bildschirm
        }

        // zeichnet die FPS
        if (getCurrentLevel() != LEVEL_ZERO || getCurrentState() != IN_GAME) {
            drawFPS(g);
        }
    }

    /**
     * zeichnet die FPS-Anzeige
     *
     * @param g Graphics des GamePanels
     */
    private void drawFPS(Graphics g) {
        g.setFont(getFont().deriveFont(5f * SCALE));
        setMetrics(g.getFontMetrics());
        int stringWidth = getMetrics().stringWidth("FPS: " + getFrames());

        // zeichnet den Hintergrund
        if (getCurrentState() == IN_GAME) {
            g.setColor(BACKGROUND_COLOR);
            g.fillRoundRect(SCREEN_WIDTH - MENU_TILE_SIZE - stringWidth, MENU_TILE_SIZE / 2,
                    MENU_TILE_SIZE / 2 + stringWidth, (int) (MENU_TILE_SIZE * 1.25f), MENU_TILE_SIZE / 2,
                    MENU_TILE_SIZE / 2);
            g.setColor(Color.WHITE);
            g.drawRoundRect(SCREEN_WIDTH - MENU_TILE_SIZE - stringWidth, MENU_TILE_SIZE / 2,
                    MENU_TILE_SIZE / 2 + stringWidth, (int) (MENU_TILE_SIZE * 1.25f), MENU_TILE_SIZE / 2,
                    MENU_TILE_SIZE / 2);
        }

        // zeichnet die FPS
        g.drawString("FPS: " + getFrames(), (int) (SCREEN_WIDTH - MENU_TILE_SIZE * 0.75f - stringWidth),
                (int) (MENU_TILE_SIZE * 0.875f + getMetrics().getAscent()));
        g.setFont(getFont().deriveFont(6f * SCALE));
        setMetrics(g.getFontMetrics());
    }

    /**
     * zeichnet das Spiel, das Pause-Menü, das GameOver-Menü und das Win-Menü
     *
     * @param g Graphics des GamePanels
     */
    private void drawInGame(Graphics g) {
        // zeichnet die Map (Layer 1)
        g.drawImage(getCurrentLevel().getMapFiles()[0], (int) -InGame.updateScreenX(), (int) -InGame.updateScreenY(),
                getWorldWidth(), getWorldHeight(), null);

        // zeichnet die von der DrawOrder sortierten Entities
        for (int i = 0; i < getInGame().getArr().size(); i++) {
            g.drawImage(
                    getInGame().getArr().get(i).getSuperEntity()[getInGame().getArr().get(i).getSpriteRow()][getInGame()
                            .getArr().get(i).getSpriteCol()],
                    (int) getInGame().getArr().get(i).getScreenX(), (int) getInGame().getArr().get(i).getScreenY(),
                    getInGame().getArr().get(i).getWidth(), getInGame().getArr().get(i).getHeight(), null);
        }

        // zeichnet die Map (Layer 2)
        g.drawImage(getCurrentLevel().getMapFiles()[1], (int) -InGame.updateScreenX(), (int) -InGame.updateScreenY(),
                getWorldWidth(), getWorldHeight(), null);

        // zeichnet zu im Level Zero die Anleitung
        if (getCurrentTutorialState() != NONE && getCurrentLevel() == LEVEL_ZERO) {
            g.setColor(new Color(0, 0, 0, 75));
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            g.setColor(Color.WHITE);
            // zeichnet den Hintergrund
            if (getCurrentTutorialState() != FIRST_PART && getCurrentTutorialState() != SECOND_PART
                    && getCurrentTutorialState() != FIRST_STELA && getCurrentTutorialState() != FIRST_ENEMY_KILL) {
                g.setColor(BACKGROUND_COLOR);
                g.fillRoundRect(getTutorialX(), TUTORIAL_FIELD_Y, TUTORIAL_WIDTH, TUTORIAL_FIELD_HEIGHT,
                        MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
                g.setColor(Color.WHITE);
                g.drawRoundRect(getTutorialX(), TUTORIAL_FIELD_Y, TUTORIAL_WIDTH, TUTORIAL_FIELD_HEIGHT,
                        MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
            }
            g.setColor(BACKGROUND_COLOR);
            g.fillRoundRect(getTutorialX(), getTutorialTextY(), TUTORIAL_WIDTH, TUTORIAL_TEXT_HEIGHT,
                    MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
            g.setColor(Color.WHITE);
            g.drawRoundRect(getTutorialX(), getTutorialTextY(), TUTORIAL_WIDTH, TUTORIAL_TEXT_HEIGHT,
                    MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
            g.setFont(getFont().deriveFont(5f * SCALE));
            setMetrics(g.getFontMetrics());

            // zeichnet die Tastenbelegung zu Beginn des Spiels
            if (getCurrentTutorialState() == GAME_BEGIN) {
                for (DefaultButton button : getInGame().getButtons()) {
                    if (button.getText().contains("Enter") || button.getText().contains("Tab")
                            || button.getText().contains("\\+")) {
                        break;
                    }
                    drawButtons(g, button);
                }
                g.drawLine(getTutorialX() + MENU_TILE_SIZE * 8, MENU_TILE_SIZE * 6, getTutorialX() + MENU_TILE_SIZE * 8,
                        (int) (MENU_TILE_SIZE * 7.25f));
                g.drawLine(getTutorialX() + MENU_TILE_SIZE * 6, (int) (MENU_TILE_SIZE * 10.75f),
                        getTutorialX() + MENU_TILE_SIZE * 6, MENU_TILE_SIZE * 12);
                g.drawLine(getTutorialX() + MENU_TILE_SIZE * 8, (int) (MENU_TILE_SIZE * 10.75f),
                        getTutorialX() + MENU_TILE_SIZE * 8, MENU_TILE_SIZE * 12);
                g.drawLine(getTutorialX() + MENU_TILE_SIZE * 10, (int) (MENU_TILE_SIZE * 10.75f),
                        getTutorialX() + MENU_TILE_SIZE * 10, MENU_TILE_SIZE * 12);
                g.drawLine(getTutorialX() + MENU_TILE_SIZE * 3 / 2, MENU_TILE_SIZE * 4,
                        getTutorialX() + MENU_TILE_SIZE * 3 / 2, (int) (MENU_TILE_SIZE * 5.25f));
                g.drawLine(getTutorialX() + MENU_TILE_SIZE * 5 / 2, (int) (MENU_TILE_SIZE * 10.75f),
                        getTutorialX() + MENU_TILE_SIZE * 5 / 2, MENU_TILE_SIZE * 12);
                g.drawString("Oben", getTutorialX() + MENU_TILE_SIZE * 8 - getMetrics().stringWidth("Oben") / 2,
                        MENU_TILE_SIZE * 6 - getMetrics().getDescent());
                g.drawString("Links", getTutorialX() + MENU_TILE_SIZE * 6 - getMetrics().stringWidth("Links") / 2,
                        MENU_TILE_SIZE * 12 + getMetrics().getHeight());
                g.drawString("Unten", getTutorialX() + MENU_TILE_SIZE * 8 - getMetrics().stringWidth("Unten") / 2,
                        MENU_TILE_SIZE * 12 + getMetrics().getHeight());
                g.drawString("Rechts", getTutorialX() + MENU_TILE_SIZE * 10 - getMetrics().stringWidth("Rechts") / 2,
                        MENU_TILE_SIZE * 12 + getMetrics().getHeight());
                g.drawString("Pause", getTutorialX() + MENU_TILE_SIZE * 3 / 2 - getMetrics().stringWidth("Pause") / 2,
                        MENU_TILE_SIZE * 4 - getMetrics().getDescent());
                g.drawString("Sprinten",
                        getTutorialX() + MENU_TILE_SIZE * 5 / 2 - getMetrics().stringWidth("Sprinten") / 2,
                        MENU_TILE_SIZE * 12 + getMetrics().getHeight());
            } else if (getCurrentTutorialState() == FIRST_CHEST || getCurrentTutorialState() == FIRST_CHECKPOINT
                    || getCurrentTutorialState() == FIRST_HIT || getCurrentTutorialState() == FIRST_ENEMY
                    || getCurrentTutorialState() == FIRST_LEVEL_END) {
                if (getCurrentTutorialState() == FIRST_HIT || getCurrentTutorialState() == FIRST_ENEMY) {
                    String s = getInGame().getButtons()[9].getText().substring(0,
                            getInGame().getButtons()[9].getText().indexOf("_"));
                    drawButtons(g, getInGame().getButtons()[9]);
                    g.drawLine(getTutorialCenterX() - MENU_TILE_SIZE * 7 / 8, MENU_TILE_SIZE * 6,
                            getTutorialCenterX() - MENU_TILE_SIZE * 7 / 8, MENU_TILE_SIZE * 8);
                    g.drawString(s, getTutorialCenterX() - getMetrics().stringWidth(s) / 2, MENU_TILE_SIZE * 5);
                } else {
                    DefaultButton button;
                    if (getCurrentTutorialState() == FIRST_CHEST || getCurrentTutorialState() == FIRST_CHECKPOINT) {
                        button = getInGame().getButtons()[7];
                    } else {
                        button = getInGame().getButtons()[8];
                    }
                    drawButtons(g, button);
                    g.drawString(button.getText().substring(0, button.getText().indexOf("_")),
                            getTutorialCenterX() - getMetrics()
                                    .stringWidth(button.getText().substring(0, button.getText().indexOf("_"))) / 2,
                            MENU_TILE_SIZE * 5);
                    g.drawLine(getTutorialCenterX(), MENU_TILE_SIZE * 6, getTutorialCenterX(),
                            (int) (MENU_TILE_SIZE * 7.25f));
                }
                g.setFont(getFont().deriveFont(4f * SCALE));
                setMetrics(g.getFontMetrics());
                g.drawString(getTutorialText(), getTutorialCenterX() - getMetrics().stringWidth(getTutorialText()) / 2,
                        MENU_TILE_SIZE * 6 - getMetrics().getDescent());
            }

            // zeichnet die Anleitung
            if (getCurrentTutorialState() != GAME_BEGIN) {
                g.setFont(getFont().deriveFont(4f * SCALE));
                setMetrics(g.getFontMetrics());
                g.drawString("Zum Fortfahren die Leertaste drücken",
                        getTutorialX() + TUTORIAL_WIDTH / 2
                                - getMetrics().stringWidth("Zum Fortfahren die Leertaste drücken") / 2,
                        getTutorialTextY() + TUTORIAL_TEXT_HEIGHT - getMetrics().getHeight());
            }
            g.setFont(getFont().deriveFont(5.5f * SCALE));
            setMetrics(g.getFontMetrics());
            float i = getCurrentTutorialState() == GAME_BEGIN ? 3.25f
                    : getCurrentTutorialState() == SECOND_PART ? 2f : 1.25f;
            for (String line : getCurrentTutorialState().getComment().split("\n")) {
                g.drawString(line, getTutorialX() + TUTORIAL_WIDTH / 2 - getMetrics().stringWidth(line) / 2,
                        (int) (getTutorialTextY() + i * getMetrics().getHeight()));
                i++;
            }
            g.setFont(getFont().deriveFont(6f * SCALE));
            setMetrics(g.getFontMetrics());
        }

        // zeichnet das HUD
        if (getCurrentState() != GAME_OVER && getCurrentState() != WIN
                && EntityHandler.getInstance().getPlayer().isAlive()) {
            // zeichnet die Lebenspunkte der lebenden Entities
            for (SuperLivingEntity superLivingEntity : getInGame().getEntityHandler().getSuperLivingEntities()) {
                LifePoints lifePoints = superLivingEntity.getLifePoints();
                if (lifePoints.getCurrentHearts() > 0 || superLivingEntity.getSpriteRow() == DEATH
                        && superLivingEntity.getSpriteCol() != getSpriteCount(superLivingEntity.getId(), DEATH) - 1) {
                    // zeichnet den Hintergrund
                    g.setColor(BACKGROUND_COLOR);
                    g.fillRoundRect((int) (lifePoints.getScreenX() + MENU_TILE_SIZE / 2),
                            (int) (lifePoints.getScreenY() + MENU_TILE_SIZE / 2),
                            (int) ((lifePoints.getFullHearts() + lifePoints.getHalfHearts()
                                    + lifePoints.getEmptyHearts()) * lifePoints.getHeartSize()
                                    + (superLivingEntity instanceof Player ? MENU_TILE_SIZE / 2
                                            : MENU_TILE_SIZE * 0.3125f)),
                            (superLivingEntity instanceof Player ? (int) (MENU_TILE_SIZE * 1.25f)
                                    : (int) ((MENU_TILE_SIZE) * 1.125f)),
                            MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
                    g.setColor(Color.WHITE);
                    g.drawRoundRect((int) (lifePoints.getScreenX() + MENU_TILE_SIZE / 2),
                            (int) (lifePoints.getScreenY() + MENU_TILE_SIZE / 2),
                            (int) ((lifePoints.getFullHearts() + lifePoints.getHalfHearts()
                                    + lifePoints.getEmptyHearts()) * lifePoints.getHeartSize()
                                    + (superLivingEntity instanceof Player ? MENU_TILE_SIZE / 2
                                            : MENU_TILE_SIZE * 0.3125f)),
                            (superLivingEntity instanceof Player ? (int) (MENU_TILE_SIZE * 1.25f)
                                    : (int) ((MENU_TILE_SIZE) * 1.125f)),
                            MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);

                    // Zeichnen der vollen Herzen
                    for (int i = 1; i <= lifePoints.getFullHearts(); i++) {
                        g.drawImage(getHeart()[0], (int) (lifePoints.getScreenX() + i * lifePoints.getHeartSize()),
                                (int) (lifePoints.getScreenY() + MENU_TILE_SIZE * 0.75f), lifePoints.getHeartSize(),
                                lifePoints.getHeartSize(), null);
                    }
                    // Überprüfen und Zeichnen eines halbleeren Herzens, wenn notwendig
                    if (lifePoints.getHalfHearts() == 1) {
                        g.drawImage(getHeart()[1],
                                (int) (lifePoints.getScreenX()
                                        + (lifePoints.getFullHearts() + 1) * lifePoints.getHeartSize()),
                                (int) (lifePoints.getScreenY() + MENU_TILE_SIZE * 0.75f), lifePoints.getHeartSize(),
                                lifePoints.getHeartSize(), null);
                    }
                    // Zeichnen der leeren Herzen
                    for (int i = 1; i <= lifePoints.getEmptyHearts(); i++) {
                        g.drawImage(getHeart()[2],
                                (int) (lifePoints.getScreenX()
                                        + (lifePoints.getFullHearts() + lifePoints.getHalfHearts() + i)
                                                * lifePoints.getHeartSize()),
                                (int) (lifePoints.getScreenY() + MENU_TILE_SIZE * 0.75f), lifePoints.getHeartSize(),
                                lifePoints.getHeartSize(), null);
                    }
                }
            }

            // zeichnet die Tür- und Luminatexte
            drawEntityTexts(g, getInGame().getEntityHandler().getLuminas());
            drawEntityTexts(g, getInGame().getEntityHandler().getDoors());

            // zeichnet die Anzeige, wenn ein Item eingesammelt wurde
            if (getInGame().getEntityHandler().getPlayer().getInventory().getLastItemCollected() != null) {
                Item item = getInGame().getEntityHandler().getPlayer().getInventory().getLastItemCollected();
                if (System.currentTimeMillis()
                        - getInGame().getEntityHandler().getPlayer().getInventory().getLastItemCollectedTime() < 5000) {
                    // zeichnet den Hintergrund
                    int itemCollectedWidth = (int) (MENU_TILE_SIZE * 3f + getMetrics().stringWidth(item.getName()));
                    int itemCollectedX = SCREEN_WIDTH / 2 - itemCollectedWidth / 2;
                    int itemCollectedY = (int) (MENU_TILE_SIZE / 2
                            + (System.currentTimeMillis() - getInGame().getTimeSinceLastLevelChange() < 5000
                                    || getCurrentLevel() == LEVEL_THREE
                                            && Arrays.stream(EntityHandler.getInstance().getCheckPoints())
                                                    .allMatch(CheckPoint::isActive)
                                    || getCurrentLevel() == LEVEL_CASTLE && Arrays
                                            .stream(EntityHandler.getInstance().getEnemies()).noneMatch(Enemy::isAlive)
                                                    ? MENU_TILE_SIZE * 2.25f
                                                    : 0));
                    g.setColor(BACKGROUND_COLOR);
                    g.fillRoundRect(itemCollectedX, itemCollectedY, itemCollectedWidth, MENU_TILE_SIZE * 2,
                            MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
                    g.setColor(Color.WHITE);
                    g.drawRoundRect(itemCollectedX, itemCollectedY, itemCollectedWidth, MENU_TILE_SIZE * 2,
                            MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);

                    // zeichnet das Item
                    int imgX = itemCollectedX + MENU_TILE_SIZE / 2;
                    g.drawImage(item.getImg(), imgX, itemCollectedY + MENU_TILE_SIZE / 2, MENU_TILE_SIZE,
                            MENU_TILE_SIZE, null);
                    // zeichnet den Text
                    String inventarTitel = item.getName();
                    g.drawString(inventarTitel,
                            imgX + (MENU_TILE_SIZE + itemCollectedWidth - getMetrics().stringWidth(inventarTitel)) / 2,
                            itemCollectedY + getMetrics().getHeight());
                    g.drawString("eingesammelt!",
                            imgX + (MENU_TILE_SIZE + itemCollectedWidth - getMetrics().stringWidth("eingesammelt")) / 2,
                            itemCollectedY + getMetrics().getHeight() + getMetrics().getHeight());
                }
            }

            // zeichnet das Inventar
            if (getInGame().getEntityHandler().getPlayer().getInventory().isShowInventory()
                    && getCurrentTutorialState() == NONE) {
                // zeichnet den Hintergrund
                g.setColor(BACKGROUND_COLOR);
                g.fillRoundRect(INVENTORY_X, INVENTORY_Y, INVENTORY_WIDTH, INVENTORY_HEIGHT, MENU_TILE_SIZE / 2,
                        MENU_TILE_SIZE / 2);
                g.setColor(Color.WHITE);
                g.drawRoundRect(INVENTORY_X, INVENTORY_Y, INVENTORY_WIDTH, INVENTORY_HEIGHT, MENU_TILE_SIZE / 2,
                        MENU_TILE_SIZE / 2);

                // Startpunkt für das erste Item
                int currentY = INVENTORY_Y + ITEM_SPACING / 2;

                // zeichnet die Items
                for (Item item : Item.values()) {
                    if (item.isAcquired()) {
                        g.drawImage(item.getImg(), IMG_X, currentY, IMG_WIDTH, IMG_HEIGHT, null);
                        // Anzeige der Item-Beschreibung, wenn die Maus über dem Item ist
                        if (new Rectangle(IMG_X, currentY, IMG_WIDTH, IMG_HEIGHT).contains(
                                getInGame().getEntityHandler().getPlayer().getInventory().getMousePosition())) {
                            // Overlay-Positionierung
                            g.setFont(getFont().deriveFont(5f * SCALE));
                            setMetrics(g.getFontMetrics());
                            int overlayWidth = 4 * SCALE + getMetrics().stringWidth(item.getName());
                            g.setFont(getFont().deriveFont(4f * SCALE));
                            setMetrics(g.getFontMetrics());
                            for (String line : item.getDescription().split("\n")) {
                                overlayWidth = Math.max(overlayWidth, 4 * SCALE + getMetrics().stringWidth(line));
                            }
                            g.setFont(getFont().deriveFont(5f * SCALE));
                            setMetrics(g.getFontMetrics());

                            int overlayHeight = MENU_TILE_SIZE * 4;
                            int overlayX = INVENTORY_X - overlayWidth - MENU_TILE_SIZE / 4;
                            int overlayY = currentY + IMG_HEIGHT / 2 - overlayHeight / 2;
                            g.setColor(BACKGROUND_COLOR);
                            g.fillRoundRect(overlayX, overlayY, overlayWidth, overlayHeight, MENU_TILE_SIZE / 2,
                                    MENU_TILE_SIZE / 2);
                            g.setColor(Color.WHITE);
                            g.drawRoundRect(overlayX, overlayY, overlayWidth, overlayHeight, MENU_TILE_SIZE / 2,
                                    MENU_TILE_SIZE / 2);
                            // Item-Name und Beschreibung
                            g.drawString(item.getName(), overlayX + 2 * SCALE, overlayY + getMetrics().getHeight());
                            overlayY += getMetrics().getHeight() + getMetrics().getDescent();
                            g.drawLine(overlayX + 2 * SCALE, overlayY,
                                    overlayX + getMetrics().stringWidth(item.getName()) + 2 * SCALE, overlayY);

                            // Zeichnen der Beschreibung
                            boolean buffs = false;
                            g.setFont(getFont().deriveFont(4f * SCALE));
                            setMetrics(g.getFontMetrics());
                            for (String line : item.getDescription().split("\n")) {
                                if (line.startsWith("+") && !buffs) {
                                    overlayY += getMetrics().getHeight() + getMetrics().getDescent();
                                    buffs = true;
                                    g.setColor(new Color(200, 255, 200));
                                } else {
                                    overlayY += getMetrics().getHeight();
                                }
                                g.drawString(line, overlayX + 2 * SCALE, overlayY);
                            }
                            g.setFont(getFont().deriveFont(6f * SCALE));
                            setMetrics(g.getFontMetrics());
                            g.setColor(Color.WHITE);
                        }
                        currentY += IMG_HEIGHT + ITEM_SPACING; // Update der Y-Position für das nächste Item
                    }
                }

                // CheckPoint anzeige, zeichnet die erreichten CheckPoints
                if (getCurrentLevel() != LEVEL_CASTLE) {
                    g.setColor(BACKGROUND_COLOR);
                    g.fillRoundRect(MENU_TILE_SIZE / 2, (int) (MENU_TILE_SIZE * 5.75f), MENU_TILE_SIZE * 3,
                            (int) (MENU_TILE_SIZE * 8.5f), MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
                    g.setColor(Color.WHITE);
                    g.drawRoundRect(MENU_TILE_SIZE / 2, (int) (MENU_TILE_SIZE * 5.75f), MENU_TILE_SIZE * 3,
                            (int) (MENU_TILE_SIZE * 8.5f), MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
                    Graphics2D g2d = (Graphics2D) g;
                    for (CheckPoint checkPoint : getInGame().getEntityHandler().getCheckPoints()) {
                        float alpha = checkPoint.isActive() ? 1f : 0.5f;
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                        g2d.drawImage(checkPoint.getSuperEntity()[checkPoint.getSpriteRow()][checkPoint.getSpriteCol()],
                                (int) (MENU_TILE_SIZE * 0.75f),
                                (int) (MENU_TILE_SIZE * 5.75 + checkPoint.getId() * MENU_TILE_SIZE * 3),
                                (int) (MENU_TILE_SIZE * 2.5 / 3 * 4), (int) (MENU_TILE_SIZE * 2.5), null);
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    }
                }

                // zeichnet die Statuswert-Anzeige
                int statusWidth = (int) (MENU_TILE_SIZE * 6.125f);
                int statusHeight = (int) (MENU_TILE_SIZE * 3f);
                int statusX = SCREEN_WIDTH - statusWidth - MENU_TILE_SIZE / 2;
                int statusY = SCREEN_HEIGHT - statusHeight - MENU_TILE_SIZE / 2;
                g.setColor(BACKGROUND_COLOR);
                g.fillRoundRect(statusX, statusY, statusWidth, statusHeight, MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
                g.setColor(Color.WHITE);
                g.drawRoundRect(statusX, statusY, statusWidth, statusHeight, MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);

                g.setFont(getFont().deriveFont(3.9f * SCALE));
                setMetrics(g.getFontMetrics());
                int textX = statusX + MENU_TILE_SIZE / 4;
                int textY = statusY + MENU_TILE_SIZE / 4 + getMetrics().getAscent();
                g.drawString(getInGame().getEntityHandler().getPlayer().getAttackDamage() + " Angriffsschaden", textX,
                        textY);
                textY += getMetrics().getHeight();
                g.drawString(getInGame().getEntityHandler().getPlayer().getRange().width / 2 + " Reichweite", textX,
                        textY);
                textY += getMetrics().getHeight();
                g.drawString(getInGame().getEntityHandler().getPlayer().getMaxLifePoints() + " max. Lebenspunkte",
                        textX, textY);
                textY += getMetrics().getHeight();
                g.drawString(getInGame().getEntityHandler().getPlayer().getShield() + " Schildkraft", textX, textY);
                textY += getMetrics().getHeight();
                g.drawString(getInGame().getEntityHandler().getPlayer().getRunSpeed() + " Lauftempo", textX, textY);
                g.setFont(getFont().deriveFont(6f * SCALE));
                setMetrics(g.getFontMetrics());
            }

            // zeichnet den Skip-Button
            if (getCurrentLevel() == LEVEL_ZERO) {
                // zeichnet den Hintergrund
                g.setColor(BACKGROUND_COLOR);
                g.fillRoundRect(SCREEN_WIDTH - MENU_TILE_SIZE * 9, MENU_TILE_SIZE / 2, (int) (MENU_TILE_SIZE * 8.5f),
                        MENU_TILE_SIZE * 4 / 3, MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
                g.setColor(Color.WHITE);
                g.drawRoundRect(SCREEN_WIDTH - MENU_TILE_SIZE * 9, MENU_TILE_SIZE / 2, (int) (MENU_TILE_SIZE * 8.5f),
                        MENU_TILE_SIZE * 4 / 3, MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);

                g.setFont(getFont().deriveFont(5f * SCALE));
                setMetrics(g.getFontMetrics());
                g.drawString("Tutorial überspringen",
                        (int) (SCREEN_WIDTH - MENU_TILE_SIZE * 1.875f
                                - getMetrics().stringWidth("Tutorial überspringen")),
                        (int) (MENU_TILE_SIZE * 1.125f + getMetrics().getAscent() / 2));
                drawButtons(g, getInGame().getButtons()[5]);
                g.setFont(getFont().deriveFont(6f * SCALE));
                setMetrics(g.getFontMetrics());
            }

            // zeichnet das Textfeld, wenn der Spieler eine Stela berührt
            for (Stela stela : getInGame().getEntityHandler().getStelas()) {
                if (stela.isActive()
                        && EntityHandler.getInstance().getPlayer().getHitBox().intersects(stela.getHitBox())) {
                    // anpassen der Schrift
                    g.setFont(getFont().deriveFont(5f * SCALE));
                    setMetrics(g.getFontMetrics());

                    // zeichnet den Hintergrund
                    int fieldWidth = Arrays.stream(getCurrentLevel().getStelaText()[stela.getSpriteRow()])
                            .mapToInt(getMetrics()::stringWidth).max().orElse(0) + getMetrics().stringWidth("    ");
                    int fieldHeight = getCurrentLevel().getStelaText()[stela.getSpriteRow()].length
                            * getMetrics().getHeight() + getMetrics().getDescent() * 3;

                    int fieldY = (int) (stela.getScreenY() + stela.getHeight() - TILE_SIZE - fieldHeight);
                    if (fieldY < 0) {
                        fieldY = MENU_TILE_SIZE / 2;
                    }

                    int stelaCenter = (int) (stela.getScreenX() + stela.getWidth() / 4);
                    int fieldX;
                    if (stelaCenter - fieldWidth / 2 < 0) {
                        fieldX = MENU_TILE_SIZE / 2;
                    } else if (stelaCenter + fieldWidth / 2 > SCREEN_WIDTH) {
                        fieldX = SCREEN_WIDTH - fieldWidth - MENU_TILE_SIZE / 2;
                    } else {
                        fieldX = stelaCenter - fieldWidth / 2;
                    }

                    g.setColor(BACKGROUND_COLOR);
                    g.fillRoundRect(fieldX, fieldY, fieldWidth, fieldHeight, MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
                    g.setColor(Color.WHITE);
                    g.drawRoundRect(fieldX, fieldY, fieldWidth, fieldHeight, MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);

                    // zeichnet den Text
                    int textY = fieldY + getMetrics().getDescent();
                    int row = 1;
                    for (String line : getCurrentLevel().getStelaText()[stela.getSpriteRow()]) {
                        int textX = fieldX + fieldWidth / 2 - getMetrics().stringWidth(line) / 2;
                        g.drawString(line, textX, textY + getMetrics().getHeight() * row);
                        row++;
                    }
                    g.setFont(getFont().deriveFont(6f * SCALE));
                    setMetrics(g.getFontMetrics());
                }
            }

            // zeichnet die Titel der Level
            if (System.currentTimeMillis() - getInGame().getTimeSinceLastLevelChange() < 5000) {
                // zeichnet den Hintergrund
                g.setColor(BACKGROUND_COLOR);
                Rectangle rectangle = new Rectangle(
                        SCREEN_WIDTH / 2 - MENU_TILE_SIZE / 2
                                - getMetrics().stringWidth(getCurrentLevel().getTitle()) / 2,
                        MENU_TILE_SIZE / 2, MENU_TILE_SIZE + getMetrics().stringWidth(getCurrentLevel().getTitle()),
                        MENU_TILE_SIZE * 2);
                g.fillRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, MENU_TILE_SIZE / 2,
                        MENU_TILE_SIZE / 2);
                g.setColor(Color.WHITE);
                g.drawRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, MENU_TILE_SIZE / 2,
                        MENU_TILE_SIZE / 2);

                // zeichnet den Titel
                g.drawString(getCurrentLevel().getTitle(),
                        SCREEN_WIDTH / 2 - MENU_TILE_SIZE * 6 + (MENU_TILE_SIZE * 12) / 2
                                - getMetrics().stringWidth(getCurrentLevel().getTitle()) / 2,
                        MENU_TILE_SIZE * 2 - getMetrics().getAscent() / 2);
            }

            // zeichnet die Anweisung den Eingang bzw. Ausgang zur Festung zu finden
            if (getCurrentLevel() == LEVEL_THREE
                    && Arrays.stream(EntityHandler.getInstance().getCheckPoints()).allMatch(CheckPoint::isActive)
                    || getCurrentLevel() == LEVEL_CASTLE
                            && Arrays.stream(EntityHandler.getInstance().getEnemies()).noneMatch(Enemy::isAlive)) {
                // zeichnet den Hintergrund
                g.setColor(BACKGROUND_COLOR);
                g.fillRoundRect(
                        SCREEN_WIDTH / 2 - (getMetrics().stringWidth(getLevelEndText()) + MENU_TILE_SIZE / 2) / 2,
                        MENU_TILE_SIZE / 2, getMetrics().stringWidth(getLevelEndText()) + MENU_TILE_SIZE / 2,
                        MENU_TILE_SIZE * 2, MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
                g.setColor(Color.WHITE);
                g.drawRoundRect(
                        SCREEN_WIDTH / 2 - (getMetrics().stringWidth(getLevelEndText()) + MENU_TILE_SIZE / 2) / 2,
                        MENU_TILE_SIZE / 2, getMetrics().stringWidth(getLevelEndText()) + MENU_TILE_SIZE / 2,
                        MENU_TILE_SIZE * 2, MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);

                // zeichnet den Text
                g.setFont(getFont().deriveFont(5f * SCALE));
                setMetrics(g.getFontMetrics());
                g.drawString(getLevelEndText(), SCREEN_WIDTH / 2 - getMetrics().stringWidth(getLevelEndText()) / 2,
                        MENU_TILE_SIZE * 2 - getMetrics().getAscent() / 2);
                g.setFont(getFont().deriveFont(6f * SCALE));
                setMetrics(g.getFontMetrics());
            }
        }

        // zeichnet das Pause-Menü
        if (getCurrentState() == PAUSE || (getCurrentState() == SETTINGS && getPreviousState() == PAUSE)) {
            // zeichnet den Hintergrund des Pause-Menüs
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            g.drawImage(getPause().getBackground(), 0, MENU_TILE_SIZE * 5, MENU_TILE_SIZE * 4, MENU_TILE_SIZE * 10,
                    null);
            g.setColor(Color.WHITE);

            // zeichnet die Buttons
            for (MiniButton button : getPause().getButtons().keySet()) {
                g.drawImage(button.getImage(), button.getX(), button.getY(), button.getWidth(), button.getHeight(),
                        null);
                drawSelector(g, button, 0);
                g.drawImage(getPause().getButtons().get(button), button.getX() + SCALE, button.getY() + SCALE,
                        button.getWidth() - SCALE * 2, button.getHeight() - SCALE * 2, null);
            }
        }

        // zeichnet das GameOver-Menü
        if (getCurrentState() == GAME_OVER) {
            // zeichnet den Hintergrund des GameOver-Menüs
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            g.drawImage(getGameOver().getBackground(), MENU_TILE_SIZE * 10, MENU_TILE_SIZE * 5, MENU_TILE_SIZE * 16,
                    MENU_TILE_SIZE * 9, null);
            g.setColor(Color.WHITE);

            g.setFont(getFont().deriveFont(18f * SCALE));
            setMetrics(g.getFontMetrics());
            g.drawString("GAME OVER", SCREEN_WIDTH / 2 - getMetrics().stringWidth("GAME OVER") / 2,
                    MENU_TILE_SIZE * 9 - getMetrics().getHeight() / 2);
            g.setFont(getFont().deriveFont(5f * SCALE));
            setMetrics(g.getFontMetrics());

            // zeichnet die erreichten checkpoints, kills und eingesammelten Truhen
            // nebeneinander aller level
            g.drawString("Checkpoints: " + getAllReachedCheckPoints(),
                    SCREEN_WIDTH / 2 - getMetrics().stringWidth("Checkpoints: " + getAllReachedCheckPoints()) / 2,
                    MENU_TILE_SIZE * 37 / 4 - getMetrics().getHeight() / 2);
            g.drawString("Kills: " + getAllEnemiesKilled(),
                    SCREEN_WIDTH / 2 - getMetrics().stringWidth("Kills: " + getAllEnemiesKilled()) / 2,
                    MENU_TILE_SIZE * 10 - getMetrics().getHeight() / 2);
            g.drawString("Chests: " + getAllReachedChests(),
                    SCREEN_WIDTH / 2 - getMetrics().stringWidth("Chests: " + getAllReachedChests()) / 2,
                    MENU_TILE_SIZE * 43 / 4 - getMetrics().getHeight() / 2);
            g.setFont(getFont().deriveFont(6f * SCALE));
            setMetrics(g.getFontMetrics());

            // zeichnet den Button
            drawButtons(g, getGameOver().getButton());
        }

        // zeichnet das Win-Menü
        if (getCurrentState() == WIN) {
            // zeichnet den Hintergrund des Win-Menüs
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            g.drawImage(getWin().getBackground(), MENU_TILE_SIZE * 9, MENU_TILE_SIZE * 5, MENU_TILE_SIZE * 18,
                    MENU_TILE_SIZE * 9, null);
            g.setColor(Color.WHITE);

            g.setFont(getFont().deriveFont(18f * SCALE));
            setMetrics(g.getFontMetrics());
            g.drawString("YOU WIN", SCREEN_WIDTH / 2 - getMetrics().stringWidth("YOU WIN") / 2,
                    MENU_TILE_SIZE * 9 - getMetrics().getHeight() / 2);
            g.setFont(getFont().deriveFont(5f * SCALE));
            setMetrics(g.getFontMetrics());

            // zeichnet die erreichten checkpoints, kills und eingesammelten Truhen
            // nebeneinander aller level
            g.drawString("Checkpoints: " + getAllReachedCheckPoints() + " / " + getCheckPointsSum(),
                    SCREEN_WIDTH / 2 - getMetrics().stringWidth(
                            "Checkpoints: " + getAllReachedCheckPoints() + " / " + getCheckPointsSum()) / 2,
                    MENU_TILE_SIZE * 37 / 4 - getMetrics().getHeight() / 2);
            g.drawString("Kills: " + getAllEnemiesKilled() + " / " + getEnemiesSum(),
                    SCREEN_WIDTH / 2
                            - getMetrics().stringWidth("Kills: " + getAllEnemiesKilled() + " / " + getEnemiesSum()) / 2,
                    MENU_TILE_SIZE * 10 - getMetrics().getHeight() / 2);
            g.drawString("Chests: " + getAllReachedChests() + " / " + getChestsSum(),
                    SCREEN_WIDTH / 2
                            - getMetrics().stringWidth("Chests: " + getAllReachedChests() + " / " + getChestsSum()) / 2,
                    MENU_TILE_SIZE * 43 / 4 - getMetrics().getHeight() / 2);
            g.setFont(getFont().deriveFont(6f * SCALE));
            setMetrics(g.getFontMetrics());

            // zeichnet die Buttons
            for (DefaultButton defaultButton : getWin().getButtons()) {
                drawButtons(g, defaultButton);
            }
        }
    }

    /**
     * zeichnet die Texte der SuperEntities
     *
     * @param g             Graphics des GamePanels
     * @param superEntities SuperEntities, deren Texte gezeichnet werden sollen
     */
    private void drawEntityTexts(Graphics g, SuperEntity[] superEntities) {
        for (SuperEntity superEntity : superEntities) {
            if (superEntity instanceof Door && superEntity.isActive()) {
                continue;
            }
            if (EntityHandler.getInstance().getPlayer().getHitBox().intersects(superEntity.getHitBox())) {
                g.setFont(getFont().deriveFont(5f * SCALE));
                setMetrics(g.getFontMetrics());
                String[] superEntityText = superEntity instanceof Door ? getDoorText(superEntity.getId())
                        : getLuminaText();
                int backgroundWidth = MENU_TILE_SIZE / 2 + getMetrics().stringWidth(superEntityText[1]);
                int backGroundHeight = getMetrics().getHeight() * 2 + MENU_TILE_SIZE;
                int backgroundX = (int) (superEntity.getScreenX() + (superEntity.getWidth() - backgroundWidth) / 2f);

                // zeichnet den Hintergrund
                g.setColor(BACKGROUND_COLOR);
                g.fillRoundRect(backgroundX, (int) (superEntity.getScreenY()), backgroundWidth, backGroundHeight,
                        MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);
                g.setColor(Color.WHITE);
                g.drawRoundRect(backgroundX, (int) (superEntity.getScreenY()), backgroundWidth, backGroundHeight,
                        MENU_TILE_SIZE / 2, MENU_TILE_SIZE / 2);

                // zeichnet den Text
                int textX = backgroundX + backgroundWidth / 2;
                int textY = (int) ((int) superEntity.getScreenY()
                        + getMetrics().getHeight() * (superEntity instanceof Door ? 1 : 1.5f));
                for (String line : superEntityText) {
                    if (superEntity instanceof Door || !line.equals(superEntityText[0])) {
                        g.setFont(getFont().deriveFont(5f * SCALE));
                        setMetrics(g.getFontMetrics());
                    } else {
                        g.setFont(getFont().deriveFont(7f * SCALE));
                        setMetrics(g.getFontMetrics());
                    }
                    g.drawString(line, textX - getMetrics().stringWidth(line) / 2, textY);
                    textY += getMetrics().getHeight();
                }
                g.setFont(getFont().deriveFont(6f * SCALE));
                setMetrics(g.getFontMetrics());
            }
        }
    }

    /**
     * zeichnet das Settings-Menü
     *
     * @param g Graphics des GamePanels
     */
    private void drawSettings(Graphics g) {
        // zeichnet den Hintergrund
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        g.setColor(Color.WHITE);

        // zeichnet den Mini-Hintergrund des Settings-Menüs
        getSettings().initValues();
        g.drawImage(getSettings().getBackground(), settingsValues()[0], settingsValues()[1], SETTINGS_WIDTH,
                SETTINGS_HEIGHT, null);

        // zeichnet die Buttons
        for (SuperButton superButton : getSettings().getButtons()) {
            if (superButton instanceof DefaultButton) {
                DefaultButton button = (DefaultButton) superButton;
                drawButtons(g, button);
            } else if (superButton instanceof Switch) {
                Switch button = (Switch) superButton;
                if (button.isShow()) {
                    g.drawImage(button.getBackground(), button.getX() - MENU_TILE_SIZE / 2,
                            button.getY() - MENU_TILE_SIZE / 4, button.getWidth() + MENU_TILE_SIZE,
                            button.getHeight() + MENU_TILE_SIZE / 2, null);
                    g.drawImage(button.getImages()[button.getState() ? 1 : 0], button.getX(), button.getY(),
                            button.getWidth(), button.getHeight(), null);
                }
            }
        }
    }

    /**
     * zeichnet das Hauptmenü
     *
     * @param g Graphics des GamePanels
     */
    private void drawMenu(Graphics g) {
        // zeichnet den Hintergrund
        g.drawImage(getMenu().getBackground(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);

        // zeichnet den Titel
        g.setFont(getFont().deriveFont(10f * SCALE));
        g.drawString(getTitle(), (int) (MENU_TILE_SIZE * 1.5f), MENU_TILE_SIZE * 2 + getMetrics().getHeight());

        // zeichnet die Buttons
        g.setFont(getFont().deriveFont(6f * SCALE));
        for (DefaultButton button : getMenu().getButtons()) {
            drawButtons(g, button);
        }
    }

    /**
     * zeichnet das Credits-Menü
     *
     * @param g Graphics des GamePanels
     */
    private void drawCredits(Graphics g) {
        // zeichnet den Hintergrund
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        g.setColor(Color.WHITE);

        // zeichnet den Mini-Hintergrund des Credits-Menüs
        g.drawImage(getCredits().getBackground(), CREDITS_VALUES[0], CREDITS_VALUES[1], CREDITS_WIDTH, CREDITS_HEIGHT,
                null);

        // zeichnet den Text
        int currentY = CREDITS_VALUES[1] + MENU_TILE_SIZE / 2 + getMetrics().getAscent();
        for (int i = 0; i < getCredits().getCredits().length; i++) {
            if (getCredits().getCredits()[i].startsWith("  ")) {
                g.setFont(getFont().deriveFont(5f * SCALE));
                setMetrics(g.getFontMetrics());
            } else if (getCredits().getCredits()[i - 1].startsWith("  ")) {
                currentY += getMetrics().getAscent();
                g.setFont(getFont().deriveFont(4f * SCALE));
                setMetrics(g.getFontMetrics());
            } else {
                g.setFont(getFont().deriveFont(4f * SCALE));
                setMetrics(g.getFontMetrics());
            }

            int textX = CREDITS_VALUES[0] + CREDITS_WIDTH / 2
                    - getMetrics().stringWidth(getCredits().getCredits()[i]) / 2;
            int textWidth = getMetrics().stringWidth(getCredits().getCredits()[i]);
            g.drawString(getCredits().getCredits()[i], textX, currentY);

            if (getCredits().getCredits()[i].startsWith("  ")) {
                g.fillRect(textX, currentY + getMetrics().getDescent(), textWidth + getMetrics().stringWidth("  "),
                        getMetrics().getLeading());
            } else if (getCredits().getCredits()[i].startsWith("https://")
                    || getCredits().getCredits()[i - 1].startsWith("https://")
                            && !getCredits().getCredits()[i].startsWith("  ")) {
                g.drawLine(textX, currentY + getMetrics().getLeading(), textX + textWidth,
                        currentY + getMetrics().getLeading());
            }

            currentY += getMetrics().getHeight();
        }

        g.setFont(getFont().deriveFont(6f * SCALE));
        setMetrics(g.getFontMetrics());

        // zeichnet den Button
        drawButtons(g, getCredits().getButton());
    }

    /**
     * zeichnet die Buttons
     *
     * @param g      Graphics des GamePanels
     * @param button Button der gezeichnet werden soll
     */
    private void drawButtons(Graphics g, DefaultButton button) {
        int x = 0;
        if (getCurrentTutorialState() != NONE && getCurrentLevel() == LEVEL_ZERO) {
            x = getCurrentState() != IN_GAME && !Arrays.asList(getInGame().getButtons()).contains(button) ? 0
                    : TutorialState.getCurrentTutorialState() != NONE
                            ? button.getText().contains("+") ? 0
                                    : button.getText().contains("Tab") || button.getText().contains("Enter")
                                            || button.getText().contains("Maus") ? getTutorialCenterX() : getTutorialX()
                            : 0;
        }
        g.drawImage(button.getImage(), x + button.getX(), button.getY(), button.getWidth(), button.getHeight(), null);
        if (!button.getText().contains("Shift") && !button.getText().contains("Enter")
                && !button.getText().contains("Tab") && !button.getText().contains("Maus")) {
            g.drawString(button.getText().substring(0, button.getText().indexOf("_")), x + button.getX()
                    + button.getWidth() / 2
                    - getMetrics().stringWidth(button.getText().substring(0, button.getText().indexOf("_"))) / 2,
                    button.getY() + button.getHeight() / 2 + getMetrics().getAscent() / 2);
        }
        drawSelector(g, button, x);
    }

    /**
     * zeichnet den Selector eines Buttons
     *
     * @param g      Graphics des GamePanels
     * @param button Button dessen Selector gezeichnet werden soll
     * @param x      Verschiebung des Buttons in X-Richtung
     */
    private void drawSelector(Graphics g, DefaultButton button, int x) {
        int width = button.getText().contains("Maus") ? button.getWidth() / 2 : button.getWidth();
        int height = button.getText().contains("Maus") ? button.getHeight() / 2 : button.getHeight();

        if (button.isHover() && !button.isPressed()) {
            g.drawImage(button.getButtonHover(), x + button.getX() - SCALE, button.getY() - SCALE, width + SCALE * 2,
                    height + SCALE * 2, null);
        } else if (button.isHover() && button.isPressed()) {
            g.drawImage(button.getButtonPressed(), x + button.getX() - SCALE, button.getY() - SCALE, width + SCALE * 2,
                    height + SCALE * 2, null);
        }
    }

    // GETTER UND SETTER

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public static void setInstance(GamePanel instance) {
        GamePanel.instance = instance;
    }

    public InGame getInGame() {
        return inGame;
    }

    public Menu getMenu() {
        return menu;
    }

    public Credits getCredits() {
        return credits;
    }

    public Settings getSettings() {
        return settings;
    }

    public Pause getPause() {
        return pause;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public Win getWin() {
        return win;
    }

    public FontMetrics getMetrics() {
        return metrics;
    }

    public void setMetrics(FontMetrics metrics) {
        this.metrics = metrics;
    }

    public int getFrames() {
        return frames;
    }

    public BufferedImage[] getHeart() {
        return heart;
    }

    public String getTitle() {
        return "Eternavia: Luminas Erbe";
    }

    public void setHeart(BufferedImage[] heart) {
        this.heart = heart;
    }

    public void setInGame(InGame inGame) {
        this.inGame = inGame;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setPause(Pause pause) {
        this.pause = pause;
    }

    public void setGameOver(GameOver gameOver) {
        this.gameOver = gameOver;
    }

    public void setWin(Win win) {
        this.win = win;
    }
}