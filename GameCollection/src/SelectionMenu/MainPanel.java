package SelectionMenu;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MainPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Create the panel.
     */
    public MainPanel() {
        setBackground(new Color(52, 52, 52));
        
        setLayout(new GridLayout(3, 3, 0, 0));
        
        JLabel lblNewLabel = new JLabel("GAME COLLECTION");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblNewLabel);
        
        JButton btnNewButton = new JButton("Game 1");
        btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        btnNewButton.setBackground(Color.GRAY);
        add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (!GameFrame.gameRunning) {
                    
                    GameFrame.gameRunning = false;
                    PixelPinesProtection.main.Game.main(null);
                    GameFrame.instance.setVisible(false);
                }

            }
        });

        
        JButton btnNewButton_1 = new JButton("Game 2");
        btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        btnNewButton_1.setBackground(Color.GRAY);
        add(btnNewButton_1);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 if (!GameFrame.gameRunning) {
                     
                     GameFrame.gameRunning = false;
                     Fatjon.Javamory.source.JavamoryFrame.main(null);
                     GameFrame.instance.setVisible(false);
                 }
            }
        });
        
        
        
        JTextArea txtrcreditsGame = new JTextArea();
        txtrcreditsGame.setBackground(new Color(52, 52, 52));
        txtrcreditsGame.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        txtrcreditsGame.setText("  -Credits-\n\n  Game 1 by ...\n  Game 2 by ...\n  Game 3 by ...\n  Game 4 by ...\n  Game 5 by ...\n  Game 6 by ...\n\n  v1.0\n");
        add(txtrcreditsGame);
        
        
        
        
        JButton btnNewButton_3 = new JButton("Game 3");
        btnNewButton_3.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        btnNewButton_3.setBackground(Color.GRAY);
        add(btnNewButton_3);
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 if (!GameFrame.gameRunning) {
                     
                     GameFrame.gameRunning = false;
                     jan.game.source.Main.main(null);
                     GameFrame.instance.setVisible(false);
                 }           
            }
        });
        
        JButton btnNewButton_2 = new JButton("Game 4");
        btnNewButton_2.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        btnNewButton_2.setBackground(Color.GRAY);
        add(btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 if (!GameFrame.gameRunning) {
                     
                     GameFrame.gameRunning = false;
                     CodeInvaders_Game.GameDisplay.Display.main(null);
                     GameFrame.instance.setVisible(false);
                 }              
            }
        });
        
        
        
        
        JButton btnNewButton_4 = new JButton("EXIT");
        btnNewButton_4.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        btnNewButton_4.setBackground(Color.GRAY);
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    GameFrame.instance.quit();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        add(btnNewButton_4);
        
        
        
        
        JButton btnNewButton_5 = new JButton("Game 5");
        btnNewButton_5.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        btnNewButton_5.setBackground(Color.GRAY);
        add(btnNewButton_5);
        btnNewButton_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 if (!GameFrame.gameRunning) {
                     
                     GameFrame.gameRunning = false;
                     jan.game.source.Main.main(null);
                     GameFrame.instance.setVisible(false);
                 }         
            }
        });
        
        JButton btnNewButton_6 = new JButton("Game 6");
        btnNewButton_6.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        btnNewButton_6.setBackground(Color.GRAY);
        add(btnNewButton_6);
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 if (!GameFrame.gameRunning) {
                     
                     GameFrame.gameRunning = false;
                     jan.game.source.Main.main(null);
                     GameFrame.instance.setVisible(false);
                 }         
            }
        });
        

    }

}
