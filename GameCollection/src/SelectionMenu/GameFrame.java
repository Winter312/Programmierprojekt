package SelectionMenu;

import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;


public class GameFrame extends JFrame {

    public static GameFrame instance;
    
    static boolean gameRunning = false;
    public static MainPanel mainPanel;
    
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
                    mainPanel = new MainPanel();
                    instance.getLayeredPane().add(mainPanel);
                    mainPanel.setBounds(0, 0, instance.getWidth(), instance.getHeight()-28);
                    instance.setFocusable(true);
                    instance.setTitle("Game Collection");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public static void startGame(Runnable runnable) {
        
        if (GameFrame.gameRunning) return;
        
        
        GameFrame.instance.setVisible(false);
        gameRunning = true;
        GameFrame.instance.dispose();
        Thread thread = new Thread(() -> {
            
            Thread thread2 = new Thread(() -> {
                
                runnable.run();
                
            });
            
            thread2.start();
            try {
                thread2.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            gameRunning = false;
            SwingUtilities.invokeLater(() ->  GameFrame.instance.setVisible(true));
        });
        
        thread.start();    
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
