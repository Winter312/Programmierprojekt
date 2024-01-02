package jan.game.source.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import jan.game.source.Game_Controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Component;

public class StartMenu extends JPanel {

    private static final long serialVersionUID = 1L;
    
    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            
            Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.MENU);
        };
    };
        
        
    KeyAdapter keyAdapter = new KeyAdapter() {    
        @Override     
        public void keyPressed(KeyEvent e) {
            
            Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.MENU);     
        };     
    };

    /**
     * Konstruktor
     */
    public StartMenu() {
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                
                Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.MENU);
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.MENU);
            }
        });
        
        setBackground(new Color(34, 34, 34));
        setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("Press Any Key...");
        lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNewLabel_1.setBounds(0, 630, 900, 16);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNewLabel_1.setForeground(new Color(127, 127, 127));
        add(lblNewLabel_1);
        
        JLabel lblNewLabel = new JLabel("ARMY OF DEATH");
        lblNewLabel.setBounds(0, 80, 900, 54);
        add(lblNewLabel);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 45));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

    }
}
