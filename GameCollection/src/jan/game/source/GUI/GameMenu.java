package jan.game.source.GUI;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import javax.swing.SwingConstants;

import jan.game.source.Game_Controller;
import jan.game.source.Game_Controller.ACTION;
import jan.game.source.Game_Controller.GAMESTATE;
import jan.game.source.Score;
import jan.game.source.Timer;

public class GameMenu extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel timerText; 
    private JPanel pausePanel;
    
    MouseAdapter mouseAdapter = new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {

            if (e.getButton() == MouseEvent.BUTTON3 && Game_Controller.getGame_C_Ref().state != GAMESTATE.PAUSED) {

                showPausePanel(true);      
                Game_Controller.getGame_C_Ref().fireEvent(ACTION.PAUSE_TOGGLE);       
            }
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            
              super.mouseExited(e);
            
              if (Game_Controller.getGame_C_Ref().state != GAMESTATE.PAUSED) {
                  
                showPausePanel(true);
                Game_Controller.getGame_C_Ref().fireEvent(ACTION.PAUSE_TOGGLE);
              }   
          }
    };
    
    KeyAdapter keyAdapter = new KeyAdapter() {
        
        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE && Game_Controller.getGame_C_Ref().state != GAMESTATE.PAUSED) {

                showPausePanel(true);      
                Game_Controller.getGame_C_Ref().fireEvent(ACTION.PAUSE_TOGGLE);       
            }
        }
    };
    
    /**
     * Konstruktor
     */
    public GameMenu() {
        setBackground(new Color(34, 34, 34));
        setLayout(null);
        
        timerText = new JLabel("Timer : ");
        timerText.setHorizontalAlignment(SwingConstants.CENTER);
        timerText.setForeground(new Color(255, 255, 255));
        timerText.setBounds(350, 6, 200, 16);
        Timer.onTimerChange = new Consumer<String>() {
            
            @Override
            public void accept(String t) {
                timerText.setText(t);

            }
        };
        
        add(timerText,JLayeredPane.POPUP_LAYER);
        
        JLabel scoreText = new JLabel("Score : " + Score.getScore());
        scoreText.setHorizontalAlignment(SwingConstants.RIGHT);
        scoreText.setForeground(new Color(255, 255, 255));
        scoreText.setBounds(752, 6, 142, 16);
        add(scoreText, JLayeredPane.POPUP_LAYER);
        Score.addTimerConsumer(new Consumer<Integer>() {
            
            @Override
            public void accept(Integer s) {
                scoreText.setText(s.toString());
            }
        });
        
        pausePanel = new JPanel();
        pausePanel.setVisible(false);
       
        pausePanel.setBackground(new Color(191, 191, 191));
        pausePanel.setBounds(0, 0, 100, 107);
        add(pausePanel,JLayeredPane.POPUP_LAYER);
        pausePanel.setLayout(null);
        
        JButton btnNewButton = new JButton("CONTINUE");
        btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        btnNewButton.setBorderPainted(false);
        btnNewButton.setBounds(0, 6, 100, 30);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPausePanel(false);
                if (Game_Controller.getGame_C_Ref().state == GAMESTATE.PAUSED)
                    Game_Controller.getGame_C_Ref().fireEvent(ACTION.PAUSE_TOGGLE);
            }
        });
        pausePanel.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("RETRY");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPausePanel(false);
                    Game_Controller.getGame_C_Ref().fireEvent(ACTION.RESTART);
            }
        });
        btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        btnNewButton_1.setBorderPainted(false);
        btnNewButton_1.setBounds(0, 38, 100, 30);
        pausePanel.add(btnNewButton_1);
        
        JButton btnNewButton_1_1 = new JButton("EXIT");
        btnNewButton_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        btnNewButton_1_1.setBorderPainted(false);
        btnNewButton_1_1.setBounds(0, 71, 100, 30);
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPausePanel(false);

                    Game_Controller.getGame_C_Ref().fireEvent(ACTION.MENU);              
            }
        });
        pausePanel.add(btnNewButton_1_1);
        
        this.addMouseListener(mouseAdapter);
        this.addKeyListener(keyAdapter);
        
    }
    
    /**
     * Klemmt den Wert zwischen einer oberen und einer unteren Grenze
     * @param val
     * @param min 
     * @param max
     * @return int 
     */
    public static int clamp(int val, int min, int max) {
        
        return Math.max(min, Math.min(max, val));
    }
    
    
    /**
     * Ob das Pause Menu angezeigt werden sollen
     * @param show
     */
    public void showPausePanel(boolean show) {
        
        pausePanel.setVisible(show);  
        Point mousePos = getMousePosition();
        
        Point size = new Point(Game_Controller.windowSize.x, Game_Controller.windowSize.y-30);
        
        if (mousePos == null) {
            
            mousePos = MouseInfo.getPointerInfo().getLocation();
            mousePos.x -= Game_Controller.getLocationOnScreen().x; 
            mousePos.y -= Game_Controller.getLocationOnScreen().y; 
            
            mousePos.x = clamp(mousePos.x, 0, size.x);
            mousePos.y = clamp(mousePos.y-30, 0, size.y);
        }
        
        if (mousePos.x > size.x - pausePanel.getWidth()) {
            
            mousePos.x = mousePos.x - pausePanel.getWidth();
        }
        
        if (mousePos.y > size.y - pausePanel.getHeight()) {
            
            mousePos.y = mousePos.y - pausePanel.getHeight();
        }
                
        pausePanel.setLocation(mousePos); 
    }        
}
