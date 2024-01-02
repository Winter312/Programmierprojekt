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

public class MainPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Create the panel.
     */
    public MainPanel() {
        
        setLayout(new GridLayout(3, 3, 0, 0));
        
        JLabel lblNewLabel = new JLabel("GAME COLLECTION");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblNewLabel);
        
        JButton btnNewButton = new JButton("Game 1");
        add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Game 2");
        add(btnNewButton_1);
        
        JTextArea txtrcreditsGame = new JTextArea();
        txtrcreditsGame.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        txtrcreditsGame.setText("  -Credits-\n\n  Game 1 by ...\n  Game 2 by ...\n  Game 3 by ...\n  Game 4 by ...\n  Game 5 by ...\n  Game 6 by ...\n\n  v1.0\n");
        add(txtrcreditsGame);
        
        JButton btnNewButton_3 = new JButton("New button");
        add(btnNewButton_3);
        
        JButton btnNewButton_2 = new JButton("New button");
        add(btnNewButton_2);
        
        JButton btnNewButton_4 = new JButton("EXIT");
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
        
        JButton btnNewButton_5 = new JButton("New button");
        add(btnNewButton_5);
        
        JButton btnNewButton_6 = new JButton("New button");
        add(btnNewButton_6);
        

    }

}
