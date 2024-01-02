package jan.game.source.GUI;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.SwingConstants;

import jan.game.source.Difficulty;
import jan.game.source.Game_Controller;
import jan.game.source.Score;

public class EndMenu extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Konstruktor
     */
    public EndMenu() {
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                
                Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.MENU);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.MENU);
            }
        });
        
        setBackground(new Color(51, 112, 164));
        setLayout(null);
        
        JLabel lblNewLabel = new JLabel(":(");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 99));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(130, 156, 100, 200);
        add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("GAME OVER");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 65));
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setBounds(276, 214, 400, 100);
        add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel();
        lblNewLabel_2.setText("MODE : ");
        Game_Controller.onDifficultyChange = new Consumer<Difficulty>() {
            
            @Override
            public void accept(Difficulty d) {
                // TODO Auto-generated method stub
                lblNewLabel_2.setText("MODE : " + d.toString());

            }
        };
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setForeground(new Color(180, 180, 180));
        lblNewLabel_2.setBounds(375, 641, 150, 40);
        add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Your PC ran into a problem, press any button to restart.");
        lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        lblNewLabel_3.setForeground(Color.WHITE);
        lblNewLabel_3.setBounds(130, 352, 520, 48);
        add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Error code: 0x521");
        lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        lblNewLabel_4.setForeground(Color.WHITE);
        lblNewLabel_4.setBounds(130, 412, 106, 16);
        add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("Your score : 0");
        Score.addTimerConsumer(new Consumer<Integer>() {
            
            @Override
            public void accept(Integer s) {
                lblNewLabel_5.setText("Your score is : "+s.toString());

            }
        });
        lblNewLabel_5.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_5.setForeground(Color.WHITE);
        lblNewLabel_5.setBounds(327, 500, 238, 54);
        add(lblNewLabel_5);

    }
}
