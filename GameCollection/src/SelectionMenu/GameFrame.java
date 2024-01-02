package SelectionMenu;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jan.game.source.Game_Controller;
import jan.game.source.Audio.AudioManager;

public class GameFrame extends JFrame {

    public static GameFrame instance;
    
    public static boolean gameRunning = false;

    
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    instance = new GameFrame();
                    instance.setVisible(true);
                    MainPanel mainPanel = new MainPanel();
                    instance.getLayeredPane().add(mainPanel);
                    mainPanel.setBounds(0, 0, instance.getWidth(), instance.getHeight()-28);
                    instance.setFocusable(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 430);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setResizable(false);
        setUndecorated(false);
        

        setContentPane(contentPane);
    }

    
    /**
     * das Fenster wird geschlossen
     * @throws IOException 
     */
    public void quit() throws IOException {
        this.dispose();
    
    }
    
    
}
