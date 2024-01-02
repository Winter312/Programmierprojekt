package jan.game.source.GUI;

import jan.game.source.Difficulty;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jan.game.source.Game_Controller;
import jan.game.source.Audio.AudioManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

public class MainMenu extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private final JPanel settingsPanel;
    private final JPanel tutorialPanel;
    private final JProgressBar progressBar;

    /**
     * Konstruktor
     */
    public MainMenu() {
        setBackground(new Color(34, 34, 34));
        setLayout(null);
        
        JButton btnNewButton = new JButton("PLAY");
        btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
        btnNewButton.setBounds(375, 180, 150, 40);
        add(btnNewButton);
        btnNewButton.setBorderPainted(false);
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setBackground(new Color(34, 34, 34));
        btnNewButton.addActionListener(e -> Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.STARTGAME));

        
        JButton btnNewButton_1 = new JButton("HOW TO PLAY");
        btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
        btnNewButton_1.setForeground(new Color(255, 255, 255));
        btnNewButton_1.setBackground(new Color(34, 34, 34));
        btnNewButton_1.setBorderPainted(false);
        btnNewButton_1.setBounds(350, 280, 200, 40);
        btnNewButton_1.addActionListener(e -> Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.TUTORIAL));
        add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("SETTINGS");
        btnNewButton_2.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
        btnNewButton_2.setForeground(new Color(255, 255, 255));
        btnNewButton_2.setBackground(new Color(34, 34, 34));
        btnNewButton_2.setBorderPainted(false);
        btnNewButton_2.setBounds(375, 380, 150, 40);
        btnNewButton_2.addActionListener(e -> Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.SETTINGS));
        add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("EXIT");
        btnNewButton_3.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
        btnNewButton_3.setBorderPainted(false);
        btnNewButton_3.setForeground(new Color(252, 255, 255));
        btnNewButton_3.setBackground(new Color(34, 34, 34));
        btnNewButton_3.setBounds(375, 480, 150, 40);
        btnNewButton_3.addActionListener(e -> Game_Controller.getGame_C_Ref().fireEvent(Game_Controller.ACTION.QUIT));
        add(btnNewButton_3);
        
        tutorialPanel = new JPanel();
        
        settingsPanel = new JPanel();
        settingsPanel.setVisible(false);
        settingsPanel.setBounds(60, 200, 280, 280);
        add(settingsPanel);
        settingsPanel.setLayout(null);
        settingsPanel.setBackground(new Color(191, 191, 191));
        
        JButton btnNewButton_4_1 = new JButton("x");
        btnNewButton_4_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSettingsPanel(false);
            }
        });
        btnNewButton_4_1.setBounds(0, 0, 35, 35);
        settingsPanel.add(btnNewButton_4_1);
        tutorialPanel.setBackground(new Color(191, 191, 191));
        tutorialPanel.setBounds(562, 200, 280, 280);
        
        JLabel lblSettings = new JLabel("SETTINGS");
        lblSettings.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblSettings.setBounds(75, 6, 150, 40);
        settingsPanel.add(lblSettings);
        lblSettings.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblNewLabel_1 = new JLabel("DIFFICULTY");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(75, 155, 150, 40);
        settingsPanel.add(lblNewLabel_1);
        lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        
        JComboBox<Difficulty> comboBox = new JComboBox<Difficulty>();
        comboBox.setBounds(75, 207, 150, 40);
        settingsPanel.add(comboBox);
        comboBox.setModel(new DefaultComboBoxModel<Difficulty>(Difficulty.values()));
        comboBox.setSelectedIndex(1);
        comboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Difficulty difficulty = (Difficulty) comboBox.getSelectedItem();
                Game_Controller.setDifficulty(difficulty);
            }
        });
        
        JLabel lblNewLabel_1_1_1 = new JLabel("MUSIC");
        lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        lblNewLabel_1_1_1.setBounds(10, 80, 150, 40);
        settingsPanel.add(lblNewLabel_1_1_1);
         
         JSlider slider = new JSlider();
         slider.setBounds(80, 80, 200, 40);
         settingsPanel.add(slider);
         slider.setPaintTrack(false);
         slider.addChangeListener(new ChangeListener() {
             
             @Override
             public void stateChanged(ChangeEvent e) {
                 progressBar.setValue(slider.getValue());
                 AudioManager.setVolume(slider.getValue() / (double)slider.getMaximum());
             }
         });
         slider.setBackground(new Color(0, 0, 0));
         slider.setForeground(new Color(0, 0, 0));
         
         progressBar = new JProgressBar();
         progressBar.setValue(50);
         progressBar.setBounds(94, 80, 180, 40);
         settingsPanel.add(progressBar);
        add(tutorialPanel);
        tutorialPanel.setLayout(null);
        
        JButton btnNewButton_4 = new JButton("x");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTutorialPanel(false);
            }
        });
        btnNewButton_4.setBounds(0, 0, 35, 35);
        tutorialPanel.add(btnNewButton_4);
        
        JLabel lblNewLabel = new JLabel("HOW TO PLAY");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(75, 6, 150, 40);
        tutorialPanel.add(lblNewLabel);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JTextArea txtrControllsMovementMouse = new JTextArea();
        txtrControllsMovementMouse.setEditable(false);
        txtrControllsMovementMouse.setText("  -Controls-\n\n  Movement: Mouse\n  Pause: Right Click or ESC\n\n  -Gameplay-\n\n  Avoid the red objects, collect the coins.\n\n  Coin collected: +10 points.\n  Red object touched: -10 points.\n\n  After 2.5 minutes the game is over,\n  and your total score is displayed.\n");
        txtrControllsMovementMouse.setBounds(9, 47, 264, 227);
        tutorialPanel.add(txtrControllsMovementMouse);

    }
    
    /**
     * Ob die Einstellungen angezeigt werden sollen
     * @param show
     */
    public void showSettingsPanel(boolean show) {
        
        settingsPanel.setVisible(show);
    }
    
    /**
     * Ob das Tutorial angezeigt werden sollen
     */
    public void showTutorialPanel(boolean show) {
        
        tutorialPanel.setVisible(show);
    }
}
