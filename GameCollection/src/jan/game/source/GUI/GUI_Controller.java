package jan.game.source.GUI;

import javax.swing.*;

import jan.game.source.Game_Controller;
import jan.game.source.Audio.AudioManager;
import jan.game.source.GameObj.BaseObj;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GUI_Controller {

    private final JFrame window; // Das Fenster in dem alles passiert

    // einzelne Fenster/Komponenten des Spiels
    private final StartMenu mStartMenu;
    private final MainMenu mMainMenu;
    private final GameMenu mGame_Panel;
    private final EndMenu mEndMenu;

    private final Game_Controller mGame_C_Ref; // Referenz zum Gamecontroller

    private boolean mGraphicsRunning = true; // läuft der Gameloop für repaint
    private long mFramerate = Game_Controller.mTickrate;
    
    private ArrayList<BaseObj> paneObjects = new ArrayList<>();


    /**
     * Konstruktor
     */
    public GUI_Controller(Game_Controller gameController) {
        mGame_C_Ref = gameController;

        window = new JFrame();
        //Beim Schließen des fensters wird die Anwendung beendet
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setSize(Game_Controller.windowSize.x, Game_Controller.windowSize.y);


        //obere Fensterleiste ja/nein
        window.setUndecorated(false);

        //Das Fenster wird sichtbar
        window.setVisible(true);
        
        window.setResizable(false);

        // Objekte der Pannels werden angelegt
        
        mMainMenu = new MainMenu();
        mGame_Panel = new GameMenu();
        mEndMenu = new EndMenu();
        mStartMenu = new StartMenu();

        //Panels werden hinzugefügt und skaliert
        
        Container cp = window.getLayeredPane();     
        cp.add(mStartMenu);
        mStartMenu.setBounds(0, 0, window.getWidth(), window.getHeight());
        cp.add(mMainMenu);
        mMainMenu.setBounds(0, 0, window.getWidth(), window.getHeight());
        cp.add(mGame_Panel);
        mGame_Panel.setBounds(0, 0, window.getWidth(), window.getHeight());
        cp.add(mEndMenu);
        mEndMenu.setBounds(0, 0, window.getWidth(), window.getHeight());

        window.setFocusable(true);
        window.setAutoRequestFocus(true);
    }
    
    /**
     * Gibt das JFrame zurück
     * @return JFrame
     */
    public JFrame getWindow() {
        
        return window;
    }

    /**
     * Veränderung des dargestellten Fensters z.B Hauptmenü -> Spiel
     * zum wechsel changeState() des Game_Controllers verwenden
     * @param state
     */
    public void changeWindowConfig(Game_Controller.GAMESTATE state) {
        if (state != Game_Controller.GAMESTATE.PAUSED) {
            for (Component element : window.getLayeredPane().getComponents()) {
                               
                element.setVisible(false);
            }
            for (BaseObj obj : paneObjects) {
                
                obj.getLabel().setVisible(true);
            }
        }

        switch (state) {
            case MENU -> {
                mMainMenu.setVisible(true);
                mMainMenu.showSettingsPanel(false);
                mMainMenu.showTutorialPanel(false);
                mMainMenu.requestFocus();
                }
            case RUNNING -> { 
                mGame_Panel.showPausePanel(false);
                mGame_Panel.setVisible(true);
                mGame_Panel.requestFocus();
                
            }
            case PAUSED -> {}
            case START -> {
                mStartMenu.setVisible(true);
                mStartMenu.requestFocus();
                }
            case TUTORIAL -> { 
                mMainMenu.setVisible(true);           
                mMainMenu.showTutorialPanel(true);
                mMainMenu.requestFocus();
            }
            case SETTINGS -> {   
                mMainMenu.setVisible(true);
                mMainMenu.showSettingsPanel(true);
                mMainMenu.requestFocus();
            }
            case GAMEOVER -> {    
                clearPaneObjects();
                mEndMenu.setVisible(true);
                mEndMenu.requestFocus();
                
            }
        }
        window.repaint();
    }
    

    /**
     * Fügt ein BaseObj zur Pane hinzu 
     */
    public void addToPane(BaseObj obj) {
        
        paneObjects.add(obj);
        mGame_Panel.add(obj.getLabel(), JLayeredPane.DEFAULT_LAYER);
    }
    
    /**
     * Entfernt ein BaseObj von der Pane
     */
    public void removeFromPane(BaseObj obj) {
        
        paneObjects.remove(obj);
        mGame_Panel.remove(obj.getLabel());
    }
    
    /**
     * Entfernt alle BaseObj aus der Pane
     */
    public void clearPaneObjects() {
        
        for (BaseObj obj : paneObjects) {
            
            obj.reset();
            mGame_Panel.remove(obj.getLabel());
        }
        
        paneObjects.clear();  
    }

    /**
     * das Fenster wird geschlossen
     * @throws IOException 
     */
    public void quit() throws IOException {
        AudioManager.end();
        mGraphicsRunning = false;
        window.dispose();
    }
}
